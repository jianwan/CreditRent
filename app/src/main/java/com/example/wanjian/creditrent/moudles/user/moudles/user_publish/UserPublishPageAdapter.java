package com.example.wanjian.creditrent.moudles.user.moudles.user_publish;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by wanjian on 2017/11/15.
 */

public class UserPublishPageAdapter extends FragmentStatePagerAdapter {

    int nNumOfTabs;

    public UserPublishPageAdapter(FragmentManager fm,int nNumOfTabs) {
        super(fm);
        this.nNumOfTabs=nNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                TabFragment1 tab1 = new TabFragment1();
                return tab1;
            case 1:
                TabFragment2 tab2 = new TabFragment2();
                return tab2;
            case 2:
                TabFragment3 tab3 = new TabFragment3();
                return tab3;
        }
        return null;
    }

    @Override
    public int getCount() {
        return nNumOfTabs;
    }

}
