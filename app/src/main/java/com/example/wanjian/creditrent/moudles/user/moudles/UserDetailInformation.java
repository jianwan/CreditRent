package com.example.wanjian.creditrent.moudles.user.moudles;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.C;
import com.example.wanjian.creditrent.common.util.ACache;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wanjian on 2017/11/8.
 * 修改用户详细信息
 */

public class UserDetailInformation extends BaseActivity implements View.OnClickListener{

    CircleImageView userdetial_ci_avatar;

    ImageView user_dl_toolbar_back;

    TextView user_dl_toolbar_change,
            userdetial_tv_nickname,userdetial_tv_sex,userdetial_tv_credit,
            userdetial_tv_school,userdetial_ci_declaration;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_detailinformation);

        initView();
        showUserInfromation();
    }

    private void showUserInfromation() {
        String nickname= ACache.getDefault().getAsString(C.NICKNAME);
        String sex=ACache.getDefault().getAsString(C.SEX);
        String school=ACache.getDefault().getAsString(C.SCHOOL);
        String declaration=ACache.getDefault().getAsString(C.DECLARATION);

        if (nickname!=null&&!nickname.isEmpty()){
            userdetial_tv_nickname.setText(nickname);
        }
        if (sex!=null&&!sex.isEmpty()){
            userdetial_tv_sex.setText(sex);
        }
        if (school!=null&&!school.isEmpty()){
            userdetial_tv_school.setText(school);
        }
        if (declaration!=null&&!declaration.isEmpty()){
            userdetial_ci_declaration.setText(declaration);
        }

    }

    private void initView() {
        userdetial_ci_avatar=(CircleImageView)findViewById(R.id.userdetial_ci_avatar);

        user_dl_toolbar_back=(ImageView)findViewById(R.id.user_dl_toolbar_back);
        user_dl_toolbar_change=(TextView)findViewById(R.id.user_dl_toolbar_change);

        userdetial_tv_nickname=(TextView)findViewById(R.id.userdetial_tv_nickname);
        userdetial_tv_sex=(TextView)findViewById(R.id.userdetial_tv_sex);
        userdetial_tv_credit=(TextView)findViewById(R.id.userdetial_tv_credit);
        userdetial_tv_school=(TextView)findViewById(R.id.userdetial_tv_school);
        userdetial_ci_declaration=(TextView)findViewById(R.id.userdetial_ci_declaration);

        user_dl_toolbar_back.setOnClickListener(this);
        user_dl_toolbar_change.setOnClickListener(this);

    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_dl_toolbar_back:
                onBackPressed();
                break;
            case R.id.user_dl_toolbar_change:
                startIntentActivity(this,new UserDetailInformationChange());
                break;
            default:
                break;
        }
    }
}
