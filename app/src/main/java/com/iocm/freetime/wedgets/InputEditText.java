package com.iocm.freetime.wedgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iocm.administrator.freetime.R;

/**
 * Created by liubo on 15/7/25.
 */
public class InputEditText extends RelativeLayout implements View.OnClickListener, TextWatcher{
    private static final String TAG = InputEditText.class.getName();

    private static final int TYPE_PASSWORD = 1;
    private static final int TYPE_PHONENUMBER = 0;
    private static final int TYPE_STRING = -1;

    private TextView mHintView;
    private EditText mEditText;
    private ImageView mDeleteView;


    private int type;
    private String mHintStr;

    public InputEditText(Context context) {
        this(context, null);
    }

    public InputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        View root = LayoutInflater.from(context).inflate(R.layout.input_edit_layout, this);

        mHintView = (TextView) root.findViewById(R.id.hint);
        mEditText = (EditText) root.findViewById(R.id.edit_text);
        mDeleteView = (ImageView) root.findViewById(R.id.delete);

        mDeleteView.setOnClickListener(this);
        mEditText.addTextChangedListener(this);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.InputEditText, 0, 0);
        try {
            mHintStr = a.getString(R.styleable.InputEditText_hint);
            type = a.getInt(R.styleable.InputEditText_inputType, TYPE_STRING);
        } finally {
            a.recycle();
        }

        switch (type) {
            case TYPE_STRING: {
                mEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            }
            case TYPE_PASSWORD:
                mEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
                break;
            case TYPE_PHONENUMBER:
                mEditText.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
        }

        mHintView.setText(mHintStr);
    }

    public InputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete: {
                clearText();
                break;
            }
        }
    }

    private void clearText() {
        mEditText.setText("");
    }

    public String getText() {
        return this.mEditText.getText().toString();
    }

    private String mBeforeText;
    private String mAfterText;

    private boolean isAnimating = false;

    private void move2Left() {
        if (isAnimating) {
            return;
        }
        AnimatorSet set = new AnimatorSet();
        if (mDeleteView.getVisibility() == GONE) {
            mDeleteView.setVisibility(VISIBLE);
        }
        set.playTogether(ObjectAnimator.ofFloat(mHintView, View.TRANSLATION_X, 0, -TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35f, getResources().getDisplayMetrics())),
                ObjectAnimator.ofFloat(mDeleteView, View.ALPHA, 0, 1.0f));
        set.setDuration(300);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimating = false;
            }
        });
        set.start();
    }

    private void resetView() {
        if (isAnimating) {
            return;
        }
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(mHintView, View.TRANSLATION_X, -TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35f, getResources().getDisplayMetrics()) , 0),
                ObjectAnimator.ofFloat(mDeleteView, View.ALPHA, 1.0f, 0));
        set.setDuration(300);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimating = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isAnimating = true;
            }
        });
        set.start();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mBeforeText = charSequence.toString();
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        mAfterText = mEditText.getText().toString();
        if (TextUtils.isEmpty(mBeforeText) && !TextUtils.isEmpty(mAfterText)) {
            move2Left();
        } else if (!TextUtils.isEmpty(mBeforeText) && TextUtils.isEmpty(mAfterText)) {
            resetView();
        }
    }


    public void setText(String text) {
        mEditText.setText(text);
    }
}
