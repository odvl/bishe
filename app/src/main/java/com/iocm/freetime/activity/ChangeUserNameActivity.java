package com.iocm.freetime.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.iocm.administrator.freetime.R;
import com.iocm.freetime.common.Constant;

/**
 * Created by Administrator on 2015/2/2.
 */
public class ChangeUserNameActivity extends BaseActivity{

    private Button mSaveBtn;
    private Button mResetBtn;
    private EditText mUserNameEdt;
    private static final int RESULTID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);
        init();
        setupViews();
        initViews();
        initListeners();

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

    private void init() {
        AssetManager _assetManager = getAssets();



    }

    private void setupViews() {

    }

    private void initListeners() {
        mResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mUserNameEdt.getText().toString().trim();
                Intent intent  = new Intent();
                intent.putExtra(Constant.Key.UserName,name);
                setResult(2,intent);
                finish();
            }
        });
    }

    private void initViews() {
//        mSaveBtn = (Button) findViewById(R.id.btn_changeUN_mSaveBtn);
//        mResetBtn = (Button) findViewById(R.id.btn_changeUN_mResetBtnurn);
        mUserNameEdt = (EditText) findViewById(R.id.edt_ChangeUN_userNm);
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void leftClickListener() {

    }

    @Override
    public void rightClickListener() {

    }
}
