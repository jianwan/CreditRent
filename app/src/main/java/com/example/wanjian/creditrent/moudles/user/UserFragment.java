package com.example.wanjian.creditrent.moudles.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseFragment;
import com.example.wanjian.creditrent.base.C;
import com.example.wanjian.creditrent.common.util.ACache;
import com.example.wanjian.creditrent.common.util.SharedPreferencesUtil;
import com.example.wanjian.creditrent.moudles.signup.view.impl.LoginActivity;
import com.example.wanjian.creditrent.moudles.user.moudles.UserBoughtActivity;
import com.example.wanjian.creditrent.moudles.user.moudles.user_collection.UserCollectedActivity;
import com.example.wanjian.creditrent.moudles.user.moudles.UserRentActivity;
import com.example.wanjian.creditrent.moudles.user.moudles.UserReturnActivity;
import com.example.wanjian.creditrent.moudles.user.moudles.UserSawActivity;
import com.example.wanjian.creditrent.moudles.user.moudles.UserSelledActivtiy;
import com.example.wanjian.creditrent.moudles.user.moudles.user_information.UserDetailInformation;
import com.example.wanjian.creditrent.moudles.user.moudles.user_publish.UserPublishedActivity;
import com.example.wanjian.creditrent.moudles.user.moudles.user_settings.view.impl.UserSettingsActivity;
import com.example.wanjian.creditrent.moudles.user.moudles.user_unpublished.UserUnpublishedActivity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wanjian on 2017/10/25.
 */

public class UserFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout relLay_unlogin,relLay_login,
            user_ralLay_published,user_ralLay_unpublished,user_relLay_bought,
            user_relLay_selled,user_relLay_collected,user_relLay_saw, user_relLay_settings;

    private Button unlogin_btn_login;
    private CircleImageView unlogin_iv_picture;

    private LinearLayout login_linear_userinformation;
    private CircleImageView login_ci_avatar;
    private TextView login_tv_nickname;
    private LinearLayout linear_rent,linear_return;




    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);


        initView(view);
        checkIsLoginin();

        return view;
    }


    @Override
    public void onResume() {
        checkIsLoginin();
        super.onResume();
    }

    private void checkIsLoginin() {

        String username= ACache.getDefault().getAsString(C.USER_NAME);
        String nickname=ACache.getDefault().getAsString(C.NICKNAME);

        if (username!=null&&!username.equals("")) {
            SharedPreferencesUtil.setIsLogin(true);
            relLay_unlogin.setVisibility(View.GONE);
            relLay_login.setVisibility(View.VISIBLE);
            login_tv_nickname.setText(nickname);

            //TODO 有bug
//            if (!ACache.getDefault().getAsString(C.AVATAR).isEmpty()&&ACache.getDefault().getAsString(C.AVATAR)!=null){
//                Glide.with(getContext())
//                        .load(ACache.getDefault().getAsString(C.AVATAR))
//                        .into(login_ci_avatar);
//            }

        } else {
            SharedPreferencesUtil.setIsLogin(false);
            relLay_unlogin.setVisibility(View.VISIBLE);
            relLay_login.setVisibility(View.GONE);
        }
    }


    private void initView(View v) {
        relLay_unlogin=v.findViewById(R.id.relLay_unlogin);
        relLay_login=v.findViewById(R.id.relLay_login);
        user_ralLay_published=v.findViewById(R.id.user_ralLay_published);
        user_ralLay_unpublished=v.findViewById(R.id.user_ralLay_unpublished);
        user_relLay_bought=v.findViewById(R.id.user_relLay_bought);
        user_relLay_selled=v.findViewById(R.id.user_relLay_selled);
        user_relLay_collected=v.findViewById(R.id.user_ralLay_collected);
        user_relLay_saw=v.findViewById(R.id.user_relLay_saw);
        user_relLay_settings=v.findViewById(R.id.user_relLay_settings);

        unlogin_btn_login=v.findViewById(R.id.unlogin_btn_login);
        unlogin_iv_picture=v.findViewById(R.id.unlogin_iv_picture);

        login_linear_userinformation=v.findViewById(R.id.login_linear_userinformation);
        login_ci_avatar=v.findViewById(R.id.login_ci_avatar);
        login_tv_nickname=v.findViewById(R.id.login_tv_nickname);
        linear_rent=v.findViewById(R.id.linear_rent);
        linear_return=v.findViewById(R.id.linear_return);

        unlogin_btn_login.setOnClickListener(this);
        login_linear_userinformation.setOnClickListener(this);
        user_ralLay_unpublished.setOnClickListener(this);
        login_ci_avatar.setOnClickListener(this);

        linear_rent.setOnClickListener(this);
        linear_return.setOnClickListener(this);

        user_ralLay_published.setOnClickListener(this);
        user_ralLay_unpublished.setOnClickListener(this);
        user_relLay_bought.setOnClickListener(this);
        user_relLay_selled.setOnClickListener(this);
        user_relLay_collected.setOnClickListener(this);
        user_relLay_saw.setOnClickListener(this);
        user_relLay_settings.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.unlogin_btn_login:
                startIntentActivity(this,new LoginActivity());
                break;
            case R.id.login_linear_userinformation:
                startIntentActivity(this,new UserDetailInformation());
                break;
            case R.id.login_ci_avatar:

                break;
            case R.id.linear_rent:
                startIntentActivity(this,new UserRentActivity());
                break;
            case R.id.linear_return:
                startIntentActivity(this,new UserReturnActivity());
                break;
            case R.id.user_ralLay_published:
                startIntentActivity(this,new UserPublishedActivity());
                break;
            case R.id.user_ralLay_unpublished:
                startIntentActivity(this,new UserUnpublishedActivity());
                break;
            case R.id.user_relLay_bought:
                startIntentActivity(this,new UserBoughtActivity());
                break;
            case R.id.user_relLay_selled:
                startIntentActivity(this,new UserSelledActivtiy());
                break;
            case R.id.user_ralLay_collected:
                startIntentActivity(this,new UserCollectedActivity());
                break;
            case R.id.user_relLay_saw:
                startIntentActivity(this,new UserSawActivity());
                break;
            case R.id.user_relLay_settings:
                startIntentActivity(this,new UserSettingsActivity());
                break;
            default:
                break;

        }
    }
}
