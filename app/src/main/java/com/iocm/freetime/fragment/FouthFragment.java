package com.iocm.freetime.fragment;

import android.app.Fragment;

public class FouthFragment extends Fragment {

}
//
//    private static final int REQUESTID = 3;
//    private static final int RESULTID = 4;
//    private ImageButton userPhoto;
//    private Button login_button = null;
//    private ImageButton changeUserName;
//    private TextView userName;
//    private Button userJoin;
//    private Button userReg;
//    private Button shop;
//    private Button userMsg;
//    private View settingLayout;
//    private Button logoff;
//    private Button login;
//    private LayoutInflater inflater;
//    private ViewGroup container;
//    private Handler handler = new Handler();
//    private SharedPreferences sharedPreferences;
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        UserLoginApp userLoginApp = (UserLoginApp) getActivity().getApplication();
//        settingLayout = inflater.inflate(R.layout.fragment_me, container, false);
//        sharedPreferences = getActivity().getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
//
//
//        initViews();
//        initDatas();
//        initListeners();
//
//        return settingLayout;
//    }
//
//    private void initListeners() {
//        userPhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "弹出框，显示相机还是调用本地相册", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        changeUserName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent data = new Intent();
//                data.setClass(getActivity(), ChangeUserNameActivity.class);
//                startActivityForResult(data, REQUESTID);
//
//            }
//        });
//
//        userJoin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(getActivity(), Join.class);
//                getActivity().startActivity(intent);
//            }
//        });
//
//
//        userReg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(getActivity(), Issue.class);
//                getActivity().startActivity(intent);
//            }
//        });
//
//        userMsg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(getActivity(), Msg.class);
//                getActivity().startActivity(intent);
//            }
//        });
//
//
//        //注销功能实现
//        logoff.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                UserLoginApp userLoginApp = (UserLoginApp) getActivity().getApplication();
//                userLoginApp.setUserName(null);
//                userLoginApp.setPassWord(null);
//                userLoginApp.setPhoneNumber(null);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.remove("USER_NAME");
//                editor.remove("PASSWORD");
//                editor.commit();
//                Intent logoutIntent = new Intent(getActivity(), MainActivity.class);
//                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(logoutIntent);
//            }
//        });
//
//
//        if (sharedPreferences.getString("USER_NAME", "") == null || sharedPreferences.getString("USER_NAME", "").equals("")) {
//            login_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent();
//                    intent.setClass(getActivity(), LoginActivity.class);
//                    startActivityForResult(intent, REQUESTID);
//                }
//            });
//        } else {
//
//            login_button.setText(sharedPreferences.getString("USER_NAME", ""));
//            userName.setText(sharedPreferences.getString("USER_NAME", ""));
//            UserLoginApp userLoginApp = (UserLoginApp) getActivity().getApplication();
//            userLoginApp.setUserName(sharedPreferences.getString("USER_NAME", ""));
//            login_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(getActivity(), "前往我的世界注销", Toast.LENGTH_SHORT).show();
//
//                }
//            });
//
//        }
//
//
//    }
//
//
//    private void initDatas() {
//
//    }
//
//    private void initViews() {
//
//        login_button = (Button) settingLayout.findViewById(R.id.login_button);
//        userPhoto = (ImageButton) settingLayout.findViewById(R.id.setting_layout_uesr_headImage_ImageView);
//        changeUserName = (ImageButton) settingLayout.findViewById(R.id.setting_layout_change_userName_ImageButton);
//        userName = (TextView) settingLayout.findViewById(R.id.setting_layout_userName_textView);
//        userJoin = (Button) settingLayout.findViewById(R.id.setting_layout_user_join_Button);
//        userReg = (Button) settingLayout.findViewById(R.id.setting_layout_user_register_Button);
//        shop = (Button) settingLayout.findViewById(R.id.setting_shop_Button);
//        userMsg = (Button) settingLayout.findViewById(R.id.setting_layout_user_msg_Button);
//        logoff = (Button) settingLayout.findViewById(R.id.btn_setlayout_logoff);
//    }
//
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUESTID && resultCode == RESULTID) {
//
//            login_button.setText(data.getStringExtra("USER_NAME"));
//            UserLoginApp userLoginApp = (UserLoginApp) getActivity().getApplication();
//            userName.setText(userLoginApp.getUserName());
//            login_button.setText(userLoginApp.getUserName());
//
//            login_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(getActivity(), "前往我的世界注销", Toast.LENGTH_SHORT).show();
//
//                }
//            });
//
//        }
//        if (requestCode == 5 && resultCode == 6) {
//
//            login_button.setText(data.getStringExtra("USERNAME"));
//
//            login_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(getActivity(), "前往我的世界注销", Toast.LENGTH_SHORT).show();
//
//                }
//            });
//
//        }
//    }
//
//}
