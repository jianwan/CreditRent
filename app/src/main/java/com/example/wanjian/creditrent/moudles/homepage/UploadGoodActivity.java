package com.example.wanjian.creditrent.moudles.homepage;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wanjian on 2018/5/6.
 * TODO：待接口修改完成
 */

public class UploadGoodActivity extends BaseActivity implements View.OnClickListener{

    private EditText goodname,description,price;
    private Spinner goodtype;
    private RadioButton rent,sell;
    private ImageView pic;
    private TextView tv;

    private Button upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_homepager_uploadgoods);

        initViews();
        initSpinner();

    }

    private void initViews() {
        goodname = (EditText) findViewById(R.id.uploadgoods_et_goodname);
        description = (EditText) findViewById(R.id.uploadgoods_et_description);
        price = (EditText) findViewById(R.id.uploadgoods_et_price);
        rent = (RadioButton) findViewById(R.id.uploadgoods_rt_rent);
        sell = (RadioButton) findViewById(R.id.uploadgoods_rt_sell);
        pic = (ImageView) findViewById(R.id.uploadgoods_iv_pic);
        tv = (TextView) findViewById(R.id.uploadgoods_iv_tv);
        upload = (Button) findViewById(R.id.uploadgoods_button_upload);

        rent.setOnClickListener(this);
        sell.setOnClickListener(this);
        pic.setOnClickListener(this);
        tv.setOnClickListener(this);
        upload.setOnClickListener(this);

    }

    private void initSpinner() {
        //选择类型
        goodtype = (Spinner)findViewById(R.id.uploadgoods_spinner_goodtype);
        String[] mGoodTypeSelection = getResources().getStringArray(R.array.goodtype);
        ArrayAdapter<String> goodtypeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
                mGoodTypeSelection);
        //设置显示样式
        goodtypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        goodtype.setAdapter(goodtypeAdapter);
        goodtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.uploadgoods_toolbar_back:
                onBackPressed();
                break;
            case R.id.uploadgoods_iv_pic:
            case R.id.uploadgoods_iv_tv:
                uploadGoodsPics();
                break;

            case R.id.uploadgoods_button_upload:

                String goodnameString = goodname.getText().toString();
                String goodTypeString = goodtype.getSelectedItem().toString();
                String sellString = sell.getText().toString();
                String rentString = rent.getText().toString();
                String descriptionString = description.getText().toString();
                String priceString = price.getText().toString();

                int ershousell;
                double ershoumoney;
                double chuzumoney;

                if (rent.isChecked()){
                    ershousell = 0;
                    ershoumoney = 0.00;
                    chuzumoney = Double.parseDouble(priceString);
                }else if (sell.isChecked()){
                    ershousell = 1;
                    ershoumoney = Double.parseDouble(priceString);
                    chuzumoney = Double.parseDouble(priceString);
                }

                RetrofitNewSingleton.getInstance()
                        .uploadGoods(goodnameString,goodTypeString,ershousell,ershoumoney,descriptionString,chuzumoney)
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(String value) {

                            }

                            @Override
                            public void onError(Throwable e) {
                                RetrofitNewSingleton.disposeFailureInfo(e,getBaseContext());
                            }

                            @Override
                            public void onComplete() {
                                ToastUtil.show("物品上传已提交，等待审核完成即可上架~");
                            }
                        });
        }
    }

    //上传照片
    private void uploadGoodsPics() {

    }
}
