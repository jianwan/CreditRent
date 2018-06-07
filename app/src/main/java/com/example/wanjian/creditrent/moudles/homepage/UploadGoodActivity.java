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

import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseActivity;
import com.example.wanjian.creditrent.base.RetrofitNewSingleton;
import com.example.wanjian.creditrent.common.util.PLog;
import com.example.wanjian.creditrent.common.util.ToastUtil;
import com.jaeger.library.StatusBarUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wanjian on 2018/5/6.
 *
 */

public class UploadGoodActivity extends BaseActivity implements View.OnClickListener{

    private EditText goodname,description,price;
    private ImageView back;
    private Spinner goodtype;
    private RadioButton rent,sell;

    private Button upload;

    private String goodId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_homepager_uploadgoods);

        initViews();
        initSpinner();

    }

    private void initViews() {
        back = (ImageView) findViewById(R.id.uploadgoods_toolbar_back);

        goodname = (EditText) findViewById(R.id.uploadgoods_et_goodname);
        description = (EditText) findViewById(R.id.uploadgoods_et_description);
        price = (EditText) findViewById(R.id.uploadgoods_et_price);
        rent = (RadioButton) findViewById(R.id.uploadgoods_rt_rent);
        sell = (RadioButton) findViewById(R.id.uploadgoods_rt_sell);
        upload = (Button) findViewById(R.id.uploadgoods_button_upload);

        back.setOnClickListener(this);
        rent.setOnClickListener(this);
        sell.setOnClickListener(this);
        upload.setOnClickListener(this);

        StatusBarUtil.setColor(this,getResources().getColor(R.color.main_toolbar),40);
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
            case R.id.uploadgoods_button_upload:

                String goodnameString = goodname.getText().toString();
                String goodTypeString = goodtype.getSelectedItem().toString();
                String sellString = sell.getText().toString();
                String rentString = rent.getText().toString();
                String descriptionString = description.getText().toString();
                Double priceDouble = Double.parseDouble(price.getText().toString());

                Integer rentOrSell = 0 ;

                if (sell.isChecked()){
                    rentOrSell = 1;
                }else if (rent.isChecked()) {
                    rentOrSell = 0;
                }

                if (goodnameString != "" && goodTypeString != "" && descriptionString != "" &&
                        priceDouble.toString() != "" ){
                    if (sell.isChecked()||rent.isChecked()){

                        PLog.d("TAG",goodnameString+goodTypeString+rentOrSell+descriptionString+priceDouble);

                        RetrofitNewSingleton.getInstance()
                                .uploadGoods(goodnameString,goodTypeString,rentOrSell,descriptionString,priceDouble)
                                .subscribe(new Observer<UploadGoodsBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(UploadGoodsBean value) {
                                        goodId = value.getGoodsis();
                                        PLog.d("TAG","goodId"+goodId);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        RetrofitNewSingleton.disposeFailureInfo(e,getBaseContext());
                                    }

                                    @Override
                                    public void onComplete() {
                                        ToastUtil.show("物品信息已提交，请及时提交物品照片~");
                                        startIntentActivity(UploadGoodActivity.this,new UploadGoodPicActivity(),"goodid",goodId);
                                    }
                                });

                    }else {
                        ToastUtil.show("请选择出租or出售");
                    }
                }else {
                    ToastUtil.show("请填好相关信息~");
                }

                break;
        }
    }


}
