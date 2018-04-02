package com.example.wanjian.creditrent.moudles.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.wanjian.creditrent.R;
import com.example.wanjian.creditrent.base.BaseFragment;


/**
 * Created by wanjian on 2017/10/25.
 */

public class ChatFragment extends BaseFragment {

    Button chat;
    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, null);

        return view;
    }



}
