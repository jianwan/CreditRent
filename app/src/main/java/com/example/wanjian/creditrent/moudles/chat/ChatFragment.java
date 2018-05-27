package com.example.wanjian.creditrent.moudles.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationsQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseFragment;
import com.example.wanjian.creditrent.common.util.PLog;
import com.example.wanjian.creditrent.common.util.SharedPreferencesUtil;

import java.util.List;

import cn.leancloud.chatkit.activity.LCIMConversationListFragment;


/**
 * Created by wanjian on 2017/10/25.
 */

public class ChatFragment extends BaseFragment {

    Button chat;
    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       if (SharedPreferencesUtil.getIsLogin()){
           View view = inflater.inflate(R.layout.fragment_chat, null);
//           initTabLayout();
           initConversations(view);
           return view;
       }else {
           View view = inflater.inflate(R.layout.fragment_chat_nologin, null);
           return view;
       }

    }

    private void initTabLayout() {
        String[] tabList = new String[]{"会话", "联系人"};
        final Fragment[] fragmentList = new Fragment[] {new LCIMConversationListFragment(),
                new ContactFragment()};
        // 以上这段代码为新建了一个 Fragment 数组，并且把 LCIMConversationListFragment 作为默认显示的第一个 Tab 页面

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < tabList.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabList[i]));
        }

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void initConversations(View view) {
        AVIMClient user = AVIMClient.getInstance("18875146594");
        user.open(new AVIMClientCallback(){
            @Override
            public void done(AVIMClient client,AVIMException e){
                if(e==null){
                    //登录成功
                    AVIMConversationsQuery query = client.getConversationsQuery();
                    query.findInBackground(new AVIMConversationQueryCallback(){
                        @Override
                        public void done(List<AVIMConversation> convs, AVIMException e){
                            if(e==null){
                                //convs就是获取到的conversation列表
                                //注意：按每个对话的最后更新日期（收到最后一条消息的时间）倒序排列
                                PLog.d("TAG","运行到了这里"+convs);
                                for (int i=0;i<convs.size();i++){
                                    PLog.d("TAG+convs",convs.get(i).getName());
                                }
                            }
                        }
                    });
                }
            }

        });
    }

}
