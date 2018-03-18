package com.example.wanjian.creditrent.moudles.user.moudles;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.C;
import com.example.wanjian.creditrent.common.util.ACache;
import com.example.wanjian.creditrent.common.util.ToastUtil;


/**
 * Created by wanjian on 2017/11/4.
 * 用户详细信息页
 */

public class UserDetailInformationChange extends BaseActivity  implements View.OnClickListener{

    private ImageView user_dl_toolbar_back;
    private TextView user_dl_toolbar_changeinformation,user_dl_toolbar_complete;

    private EditText user_dl_et_nickname,user_dl_et_declaration;
    Spinner spinnerSex,spinnerSchool;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_detailinformation_change);

        initView();

        //展现个人信息（从缓存中或者网络中获取）
        showUserInfromation();

        initSpanner();

    }


    private void initView() {
        user_dl_toolbar_back=(ImageView)findViewById(R.id.user_dl_toolbar_back);
        user_dl_toolbar_changeinformation=(TextView)findViewById(R.id.user_dl_toolbar_changeinformation);
        user_dl_toolbar_complete=(TextView)findViewById(R.id.user_dl_toolbar_complete);
        user_dl_et_nickname=(EditText)findViewById(R.id.user_dl_et_nickname);
        user_dl_et_declaration=(EditText) findViewById(R.id.user_dl_et_declaration);

        user_dl_toolbar_back.setOnClickListener(this);
        user_dl_toolbar_complete.setOnClickListener(this);
    }



    private void showUserInfromation() {
        String nickname= ACache.getDefault().getAsString(C.NICKNAME);
        String declaration=ACache.getDefault().getAsString(C.DECLARATION);

        if (nickname!=null&&!nickname.isEmpty()){
            user_dl_et_nickname.setText(nickname);
            user_dl_et_nickname.setTextColor(0xFF000000);
            user_dl_et_nickname.getTextColors();
        }
        if (declaration!=null&&!declaration.isEmpty()){
            user_dl_et_declaration.setText(declaration);
            user_dl_et_declaration.setTextColor(0xFF000000);
        }

    }


    private void initSpanner() {

        //性别选择
        spinnerSex = (Spinner)findViewById(R.id.user_dl_spinner_sex);
        String[] mSexSelection = getResources().getStringArray(R.array.sex);
        ArrayAdapter<String> sexAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
                mSexSelection);
        //设置显示样式
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSex.setAdapter(sexAdapter);
        spinnerSex.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //学校选择
        spinnerSchool = (Spinner) findViewById(R.id.user_dl_spinner_school);
        String[] mSchoolSpinner = getResources().getStringArray(R.array.schools);
        ArrayAdapter<String> schoolAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
                mSchoolSpinner);
        //设置显示样式
        schoolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSchool.setAdapter(schoolAdapter);
        spinnerSchool.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_dl_toolbar_back:
                onBackPressed();
                break;
            case R.id.user_dl_toolbar_complete:
                completeInformation();
                break;
            default:
                break;
        }
    }

    private void completeInformation() {
        String usernickname=user_dl_et_nickname.getText().toString();
        String declaration=user_dl_et_declaration.getText().toString();
        String sex=spinnerSex.getSelectedItem().toString();
        String school=spinnerSchool.getSelectedItem().toString();

        ACache.getDefault().put(C.DECLARATION,declaration);
        ACache.getDefault().put(C.NICKNAME,usernickname);
        ACache.getDefault().put(C.SEX,sex);
        ACache.getDefault().put(C.SCHOOL,school);

        updataUserInformation();

        Intent intent=new Intent(UserDetailInformationChange.this,UserDetailInformation.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        ToastUtil.show("已成功修改个人信息");

        startActivity(intent);

    }

    //TODO：等接口，修改个人信息
    private void updataUserInformation() {

    }

}
