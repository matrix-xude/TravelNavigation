package com.xxd.travelnavigation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;

/**
 * Created by xxd on 2018/1/11.
 * <p>
 * 需要使用公用Toolbar的activity继承此类，不需要公用头和自己特殊处理的布局直接继承CommonActivity
 * 此类只处理公共头的问题，其他公共问题写到CommonActivity中
 */

public class CommonTitleActivity extends CommonActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
