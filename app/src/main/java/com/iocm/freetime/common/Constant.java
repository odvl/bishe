package com.iocm.freetime.common;

/**
 * Created by liubo on 15/6/14.
 */
public final class Constant {

    public static final String TAG = "liubo";
    //任务类型
    public static final class TaskType {

        //轻松一聚
        public static final class HappyTime {
            //段时间的聚会（打扑克，桌游）
            public static final String SHORT_TIME = "short_time";

            //长时间（一般是出去玩一天）
            public static final String LONG_TIME = "long_time";

        }

        //比赛竞技
        public static final class GameTime {

            //户外运动
            public static final String OUTDOOR_TIME = "outdoor_time";

            //室内运动
            public static final String INDOOR_TIME = "indoor_time";

        }
    }

    public static final class Key {
        public static final String Task = "task";
        public static final String UserName = "username";
        public static final String AppName = "yunxuan";
        public static final String UserInfo = "user_info";
        public static final String UserId = "id";
    }

    public static final class TASK {

        public static final int takeSomethingId = 1;
        public static final String takeSomethingStr = "帮忙带东西";

        public static final int findSomethingId = 2;
        public static final String findSomethingStr = "帮忙找东西";

        public static final String userName = "userName";
        public static final String type = "type";
        public static final String beginTime = "beginTime";
        public static final String endTime = "endTime";
        public static final String head = "head";
        public static final String body = "body";
        public static final String location = "location";
        public static final String mobile = "mobile";
    }

    public static final class Value {
        public static final int TaskCenterFragment = 0;
        public static final int MeFragment = 1;
    }

    public static final class RequestCode {
        public static final int RegisterCode = 1;
        public static final int SetLocationCode = 2;

    }

    public static final class ResultCode {
        public static final int ResultOk = 1;
    }

    public static final class SharedPreferences {
        public static final String Name = "free_time";
    }

    public static final class User {
        public static final String username = "username";
        public static final String password = "password";
        public static final String mobile = "mobile";
        public static final String login = "login";
        public static final String userId = "userId";
    }

    public static final class LeancloundTable {
        public static final class TaskTable {
            public static final String objectId = "objectId";
            public static final String tableName = "TaskTable"; //表名
            public static final String userId = "userId"; // 发布者的Id
            public static final String taskTitle = "taskTitle"; //任务名称
            public static final String taskDetail = "taskDetail"; //任务详情
            public static final String taskBeginTime = "beginTime"; //开始时间
            public static final String taskEndTime = "endTime"; //结束时间
            public static final String point = "point";
            public static final String taskMobile = "mobile"; //电话
            public static final String username = "username"; // 用户名
            public static final String joinedNum = "upvotes"; //参加人数
            public static final String build = "build"; //建筑物

        }
    }

}
