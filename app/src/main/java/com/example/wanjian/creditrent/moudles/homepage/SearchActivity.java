package com.example.wanjian.creditrent.moudles.homepage;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.ToastUtil;
import com.example.wanjian.creditrent.moudles.chat.rentcar.RentcarAdapter;
import com.example.wanjian.creditrent.moudles.chat.rentcar.RentcarBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wanjian on 2017/11/24.
 * TODO:待完成
 */

public class SearchActivity extends BaseActivity implements View.OnClickListener{

    ImageView search_iv_back,search_iv_clear;
    EditText search_et_searchcontent;
    TextView search_tv_search;

    private RecyclerView recyclerview;
    private RentcarAdapter rentAdapter;
    private List<RentcarBean> rentcarBeen = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_homepager_search);

        initViews();

        initRecyclerview();



        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //弹出软键盘
                showKeyBoard();
            }
        },300);



    }

    private void initRecyclerview() {
        recyclerview = (RecyclerView) findViewById(R.id.search_recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerview.setLayoutManager(linearLayoutManager);
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
                if (!search_et_searchcontent.getText().toString().isEmpty() && search_et_searchcontent.getText().toString() != ""){
                    RetrofitNewSingleton.getInstance()
                            .searchGoods(1,"书")
                            .subscribe(new Observer<ArrayList<RentcarBean>>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(ArrayList<RentcarBean> value) {
                                    rentAdapter = new RentcarAdapter(R.layout.fragment_chat_rentcar_item,value);
                                    recyclerview.setAdapter(rentAdapter);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    RetrofitNewSingleton.disposeFailureInfo(e,getBaseContext());
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }else {
                    ToastUtil.show("请输入搜索关键字");
                }
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
