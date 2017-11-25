package com.example.wanjian.creditrent.moudles.homepage;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wanjian on 2017/11/24.
 */

public class SearchActivity extends BaseActivity implements View.OnClickListener{

    ImageView search_iv_back,search_iv_clear;
    EditText search_et_searchcontent;
    TextView search_tv_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_homepager_search);

        initViews();


        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //弹出软键盘
                showKeyBoard();
            }
        },300);



    }




    private void initViews() {
        search_iv_back=(ImageView)findViewById(R.id.search_iv_back);
        search_iv_clear=(ImageView)findViewById(R.id.search_iv_clear);
        search_et_searchcontent=(EditText)findViewById(R.id.search_et_searchcontent);
        search_tv_search=(TextView)findViewById(R.id.search_tv_search);


        search_iv_back.setOnClickListener(this);
        search_iv_clear.setOnClickListener(this);
        search_iv_clear.setVisibility(View.GONE);
        search_et_searchcontent.setOnClickListener(this);
        search_et_searchcontent.setInputType(InputType.TYPE_CLASS_TEXT);
        search_tv_search.setOnClickListener(this);

        //监听EditText的内容变化
        search_et_searchcontent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (search_et_searchcontent.getText().toString().isEmpty()){
                    search_iv_clear.setVisibility(View.GONE);
                }else {
                    search_iv_clear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_iv_back:
                hideKeyBoard();
                onBackPressed();
                break;
            case R.id.search_iv_clear:
                search_et_searchcontent.setText("");
                break;
            case R.id.search_tv_search:
                //TODO　搜索功能的实现

                break;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //点击空白处隐藏软键盘
        if (null!=this.getCurrentFocus()){
            hideKeyBoard();
        }
        return super.onTouchEvent(event);
    }



    //弹出软键盘
    private void showKeyBoard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(search_et_searchcontent, InputMethodManager.SHOW_IMPLICIT);
    }

    //隐藏软键盘
    private void hideKeyBoard() {
        InputMethodManager mInputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }


}
