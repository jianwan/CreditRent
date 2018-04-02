package com.example.wanjian.creditrent.moudles.chat;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.LCChatProfileProvider;
import cn.leancloud.chatkit.LCChatProfilesCallBack;

/**
 * Created by wanjian on 2018/3/23.
 */

public class CustomUserProvider implements LCChatProfileProvider {


    private static CustomUserProvider customUserProvider;

    public synchronized static CustomUserProvider getInstance() {
        if (null == customUserProvider) {
            customUserProvider = new CustomUserProvider();
        }
        return customUserProvider;
    }

    private CustomUserProvider() {
    }

    public   List<LCChatKitUser> partUsers = new ArrayList<>();


//    static String userId = AVUser.getCurrentUser().getObjectId();
//    static String nickname = AVUser.getCurrentUser().getUsername();

//    private static String userId = LCChatKitUser;
//    private static String nickname = ACache.getDefault().getAsString(C.NICKNAME);

    //TODO 暂时未理解这个的作用
//    static {
//        partUsers.add(new LCChatKitUser(userId,
//                nickname, "http://www.avatarsdb.com/avatars/tom_and_jerry2.jpg"));
//        partUsers.add(new LCChatKitUser("Job",
//                "Job", "http://www.avatarsdb.com/avatars/tom_and_jerry2.jpg"));
//    }



    @Override
    public void fetchProfiles(List<String> list, LCChatProfilesCallBack callBack) {
        List<LCChatKitUser> userList = new ArrayList<LCChatKitUser>();
        for (String userId : list) {
            for (LCChatKitUser user : partUsers) {
                if (user.getUserId().equals(userId)) {
                    userList.add(user);
                    break;
                }
            }
        }
        callBack.done(userList, null);
    }

    public List<LCChatKitUser> getAllUsers() {
        return partUsers;
    }
}
