package com.iocm.freetime.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iocm.administrator.freetime.R;
import com.iocm.freetime.util.TLog;
import com.ozn.mylibrary.FlowLayout;

import java.util.Random;

/**
 * Created by liubo on 15/7/27.
 */
public class SearchTaskActivity extends BaseActivity{

    private static final String TAG = SearchTaskActivity.class.getName();

    String[] strings = new String[]{
            "android实习", "ios", "带饭上楼", "取快递", "帮忙刷卡", "洗衣服", "衣服"
    };

    private FlowLayout mFlowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_task);

        init();
        setupView();
    }

    @Override
    void initView() {

    }

    @Override
    void initListener() {

    }

    @Override
    void loadData() {

    }

    private void setupView() {

        mFlowLayout = (FlowLayout) findViewById(R.id.flowLayout);
        TextView textView = new TextView(this);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                ViewGroup.MarginLayoutParams.WRAP_CONTENT);
        textView.setText("校内");
        textView.setBackgroundResource(R.drawable.clikable_text_btn_bg);

        mFlowLayout.addView(textView, params);
        for (int i = 0; i < 20; i++) {
            TextView textView1 = new TextView(this);
            int random = new Random().nextInt(strings.length);
            textView1.setText(strings[random]);
            textView1.setTag(random);
            textView1.setOnClickListener(this);
            textView1.setBackgroundResource(R.drawable.clikable_text_btn_bg);
            mFlowLayout.addView(textView1, params);
        }

    }

    private void init() {

    }

    @Override
    public void onClick(View view) {
        int random = (int) view.getTag();
        String string = strings[random];

        TLog.i(TAG, "string" + string);
    }

    @Override
    public void leftClickListener() {

    }

    @Override
    public void rightClickListener() {

    }
}
