package com.iocm.freetime.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.iocm.administrator.yunxuan.R;
import com.iocm.freetime.base.RecyclerArray;
import com.iocm.freetime.base.TaskActivity;
import com.iocm.freetime.wedgets.CommonToolBar;

/**
 * Created by liubo on 15/7/19.
 */
public class MessageCenterActivity extends TaskActivity implements View.OnClickListener,
        CommonToolBar.OnCommonToolBarClickListener {
    private static final int TYPE_FROM_APPLY = 0;
    private static final int TYPE_TO_APPLY = 1;
    private CommonToolBar mToolbar;
    private RecyclerArray mItemArray;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_center);

        init();
        setupView();

        if (savedInstanceState == null) {
            final View _view = findViewById(R.id.message_center_content);
            _view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    _view.getViewTreeObserver().removeOnPreDrawListener(this);
                    loadActivityAnimation(_view);
                    return false;
                }
            });
        }
    }

    private void setupView() {
        mToolbar = (CommonToolBar) findViewById(R.id.toolbar);
        mToolbar.setOnCommonToolBarClickListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(new MessageCenterAdapter());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void init() {
        mItemArray = new RecyclerArray();
    }

    private void loadActivityAnimation(View _view) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(_view, "translationX", _view.getWidth(), 0),
                ObjectAnimator.ofFloat(_view, "alpha", 0, 1.0f));
        set.setDuration(300);
        set.start();

    }

    class MessageCenterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case TYPE_FROM_APPLY: {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_apply_layout, null);
                    return new MessageApplyViewHolder(view);
                }
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public int getItemCount() {
            return 2;
        }

        class MessageApplyViewHolder extends RecyclerView.ViewHolder {

            private Spinner mBtn;

            public MessageApplyViewHolder(View itemView) {
                super(itemView);
                mBtn = (Spinner) itemView.findViewById(R.id.message_control_btn);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MessageCenterActivity.this, R.array.btn_type, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mBtn.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void leftClickListener() {
        finish();

    }

    @Override
    public void rightClickListener() {

    }
}
