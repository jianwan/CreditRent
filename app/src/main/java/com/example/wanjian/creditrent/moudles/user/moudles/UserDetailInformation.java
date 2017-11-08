package com.example.wanjian.creditrent.moudles.user.moudles;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;

/**
 * Created by wanjian on 2017/11/8.
 * 修改用户详细信息
 */

public class UserDetailInformation extends BaseActivity implements View.OnClickListener{

    TextView change;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_detailinformation);
        change=(TextView)findViewById(R.id.user_dl_toolbar_change);
        change.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_dl_toolbar_change:
                startIntentActivity(this,new UserDetailInformationChange());
                break;
        }
    }
}
