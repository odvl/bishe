package com.iocm.freetime.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.iocm.administrator.freetime.R;
import com.iocm.freetime.util.CustomUtils;

/**
 * Created by liubo on 16/1/5.
 */
public class FeedBackActivity extends BaseActivity {

    private SeekBar face;
    private SeekBar function;
    private SeekBar exact;

    private EditText editText;
    private Button commit;

    private int faceValue;
    private int funValue;
    private int exValue;
    @Override
    void initView() {
        setContentView(R.layout.activity_feed_back);
        face = (SeekBar) findViewById(R.id.face);
        function = (SeekBar) findViewById(R.id.function);
        exact = (SeekBar) findViewById(R.id.exact);

        editText = (EditText) findViewById(R.id.input);
        commit = (Button) findViewById(R.id.commit);


    }

    @Override
    void initListener() {
        face.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                faceValue = seekBar.getProgress();
            }
        });

        function.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                funValue = seekBar.getProgress();
            }
        });

        exact.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                exValue = seekBar.getProgress();
            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitFeedBack();
            }


        });
    }

    private void commitFeedBack() {
        if (exValue == 0 && faceValue == 0 && funValue == 0 && TextUtils.isEmpty(editText.getText().toString().trim())) {
            return;
        }

        AVObject object = new AVObject("FeedBack");
        object.put("face", faceValue);
        object.put("exact", exValue);
        object.put("function", funValue);
        object.put("message", editText.getText().toString().trim());
        object.put("userId", AVUser.getCurrentUser().getObjectId());
        object.put("username", AVUser.getCurrentUser().getUsername());
        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null ){
                    CustomUtils.showToast(mContext, "感谢您的反馈！！");
                } else {
                    CustomUtils.showToast(mContext, "网络出错，请重试！");
                }
            }
        });
    }

    @Override
    void loadData() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void leftClickListener() {
        finish();

    }

    @Override
    public void rightClickListener() {

    }
}
