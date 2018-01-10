package com.xxd.travelnavigation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.xxd.travelnavigation.R;
import com.xxd.travelnavigation.domain.TestBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xxd on 2018/1/9.
 */

public class MainActivity extends CommonActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("高级标题");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setOverflowIcon(getResources().getDrawable(R.mipmap.ic_launcher));
        toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_launcher));

        TestBean bean = new TestBean();
        bean.setCode(200);
        bean.setMessage("成功");
        List<String> list = new ArrayList<>();
        list.add("民主");
        list.add("自由");
        list.add("和谐");
        bean.setData(list);
        Gson gson = new Gson();
        String s = gson.toJson(bean);


        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> map = gson.fromJson(s, type);
        Logger.d(map);
        Object avc = map.get("avc");
        Object code = map.get("code");
        Logger.d(avc);
        Logger.d(code);

//        Logger.json(s);
    }
}
