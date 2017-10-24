package com.example.wanjian.creditrent.base;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by wanjian on 2017/10/8.
 */

public class BaseFragment extends Fragment {

    public void startIntentActivity(Fragment fragment1, BaseActivity activity){
        Intent intent = new Intent();
        intent.setClass(fragment1.getContext(),activity.getClass());
        startActivity(intent);
    }

    public void startIntentActivity(Fragment fragment1, BaseActivity activity, String name, String value){
        Intent intent = new Intent();
        intent.putExtra(name, value);
        intent.setClass(fragment1.getContext(), activity.getClass());
        startActivity(intent);
    }



    public void startIntentActivity(Fragment fragment1, Fragment fragment2){
        Intent intent = new Intent();
        intent.setClass(fragment1.getContext(), fragment2.getClass());
        startActivity(intent);
    }

    public void startIntentActivity(Fragment fragment1, Fragment fragment2, String name, String value){
        Intent intent = new Intent();
        intent.putExtra(name, value);
        intent.setClass(fragment1.getContext(), fragment2.getClass());
        startActivity(intent);
    }


    public void startIntentActivity(Context context, BaseActivity activity){
        Intent intent = new Intent();
        intent.setClass(context,activity.getClass());
        startActivity(intent);
    }

    public void startIntentActivity(Context context, BaseActivity activity, String name, String value){
        Intent intent = new Intent();
        intent.putExtra(name, value);
        intent.setClass(context,activity.getClass());
        startActivity(intent);
    }
    public void startIntentActivity(Fragment fragment1, BaseActivity activity, String name, int value){
        Intent intent = new Intent();
        intent.putExtra(name, value);
        intent.setClass(fragment1.getContext(), activity.getClass());
        startActivity(intent);
    }
    public void startIntentActivity(Context context, BaseActivity activity, String name, int value){
        Intent intent = new Intent();
        intent.putExtra(name, value);
        intent.setClass(context,activity.getClass());
        startActivity(intent);
    }
    public void startIntentActivity(Context context, BaseActivity activity2, String name1,
                                    String value1, String name2, int value2
                                    , String name3, int value3){
        Intent intent = new Intent();
        intent.putExtra(name1, value1);
        intent.putExtra(name2, value2);
        intent.putExtra(name3, value3);
        intent.setClass(context, activity2.getClass());
        startActivity(intent);
    }

    public void startIntentActivity(Context context, BaseActivity activity, String name1, String value1, String name2, int value2, String name3, String value3){
        Intent intent = new Intent();
        intent.putExtra(name1, value1);
        intent.putExtra(name2, ""+value2);
        intent.putExtra(name3, value3);
        intent.setClass(context,activity.getClass());
        startActivity(intent);
    }

}
