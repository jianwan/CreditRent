package com.example.wanjian.creditrent.moudles.user.moudles.user_publish.change_good_information;

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
import com.example.wanjian.creditrent.moudles.homepage.recyclerview.GoodsDetailinformationBean;
import com.jaeger.library.StatusBarUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wanjian on 2018/5/6.
 * TODO:待完善
 */

public class ChangeGoodInformation extends BaseActivity implements View.OnClickListener{

    ArrayAdapter<String> goodtypeAdapter;

    private EditText goodname,description,price;
    private ImageView back;
    private Spinner goodtype;
    private RadioButton rent,sell;

    private Button upload;

    private String goodId;

    private String preGoodId;

    private int typePosition;

    String goodTypeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_homepager_uploadgoods);

        initViews();
        initSpinner();

        Bundle bundle = this.getIntent().getExtras();
        preGoodId = bundle.getString("preGoodId");

        if (!preGoodId.isEmpty()){
            RetrofitNewSingleton.getInstance()
                    .getGoodDetialInformation(preGoodId)
                    .subscribe(new Observer<GoodsDetailinformationBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(GoodsDetailinformationBean value) {
                            goodname.setText(value.getGoodsname());
                            description.setText(value.getGoods_description());
                            if (value.getErshousell().equals("0")){
                                rent.setChecked(true);
                            }else {
                                sell.setChecked(true);
                            }
                            price.setText(value.getChuzu_money());

                            //TODO: failed
                            String[] strings = new String[] {"图书","影像","数码产品","鞋靴箱包","衣物","出行","娱乐","其他"};
                            for (int i = 0;i<8;i++){
                                PLog.d("TAG1",strings[i]);
                                PLog.d("TAG2",value.getTypename().toString());
                                if (value.getTypename().toString().equals(strings[i]) ){
                                    goodtype.setSelection(i);
                                    PLog.d("TAG3",value.getTypename().toString());
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    private void initViews() {
        back = (ImageView) findViewById(R.id.uploadgoods_toolbar_back);

        goodname = (EditText) findViewById(R.id.uploadgoods_et_goodname);
        description = (EditText) findViewById(R.id.uploadgoods_et_description);
        price = (EditText) findViewById(R.id.uploadgoods_et_price);
        rent = (RadioButton) findViewById(R.id.uploadgoods_rt_rent);
        sell = (RadioButton) findViewById(R.id.uploadgoods_rt_sell);
        upload = (Button) findViewById(R.id.uploadgoods_button_upload);
        upload.setText("更新物品信息");

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
        goodtypeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
                mGoodTypeSelection);
        //设置显示样式
        goodtypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        goodtype.setAdapter(goodtypeAdapter);
        goodtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typePosition = position;
                Spinner spinner = (Spinner) parent;
                goodTypeString = (String) spinner.getItemAtPosition(position);
                PLog.d("TAG goodtype",goodTypeString);
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

                String sellString = sell.getText().toString();
                String rentString = rent.getText().toString();
                String descriptionString = description.getText().toString();
                String  priceString = price.getText().toString();

                Integer rentOrSell = 0 ;

                if (sell.isChecked()){
                    rentOrSell = 1;
                }else if (rent.isChecked()) {
                    rentOrSell = 0;
                }

                if (goodnameString != "" && goodTypeString != "" && descriptionString != "" &&
                        priceString != "" ){
                    if (sell.isChecked()||rent.isChecked()){
                        int id = Integer.parseInt(preGoodId);
                        PLog.d("TAG goodtype",goodTypeString);
                        RetrofitNewSingleton.getInstance()
                                .changeGoodInformation(id,goodnameString,goodTypeString,rentOrSell,descriptionString,priceString)
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
                                        onBackPressed();
                                        ToastUtil.show("物品信息已更新~");
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
