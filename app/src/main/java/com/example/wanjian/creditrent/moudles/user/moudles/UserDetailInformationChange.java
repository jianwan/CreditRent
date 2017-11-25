package com.example.wanjian.creditrent.moudles.user.moudles;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;

/**
 * Created by wanjian on 2017/11/4.
 * 用户详细信息页
 */

public class UserDetailInformationChange extends BaseActivity  implements View.OnClickListener{

    private ImageView user_dl_toolbar_back;
    private TextView user_dl_toolbar_changeinformation,user_dl_toolbar_complete;

    private EditText user_dl_et_truename,user_dl_et_nickname,user_dl_et_phone,user_dl_et_remark,user_dl_et_declaration;

    String school;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_detailinformation_change);

        initView();
        //学校选择
        Spinner spinner = (Spinner) findViewById(R.id.user_dl_spinner_school);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] schools = getResources().getStringArray(R.array.schools);
                school=schools[position];
                Toast.makeText(UserDetailInformationChange.this, "你点击的是:"+schools[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initView() {
        user_dl_toolbar_back=(ImageView)findViewById(R.id.user_dl_toolbar_back);
        user_dl_toolbar_changeinformation=(TextView)findViewById(R.id.user_dl_toolbar_changeinformation);
        user_dl_toolbar_complete=(TextView)findViewById(R.id.user_dl_toolbar_complete);
        user_dl_et_truename=(EditText)findViewById(R.id.user_dl_et_truename);
        user_dl_et_nickname=(EditText)findViewById(R.id.user_dl_et_nickname);
        user_dl_et_phone=(EditText)findViewById(R.id.user_dl_et_phone);
        user_dl_et_remark=(EditText)findViewById(R.id.user_dl_et_remark);
        user_dl_et_declaration=(EditText) findViewById(R.id.user_dl_et_declaration);

        user_dl_toolbar_back.setOnClickListener(this);
        user_dl_toolbar_complete.setOnClickListener(this);
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
        String username=user_dl_et_truename.getText().toString();
        String usernickname=user_dl_et_nickname.getText().toString();
        String phone=user_dl_et_phone.getText().toString();
        String remark=user_dl_et_remark.getText().toString();
        String declaration=user_dl_et_declaration.getText().toString();
    }
}
