package com.zty.statetest;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FragmentDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        if (savedInstanceState == null){
            Log.e("TAG", "无数据");
            getSupportFragmentManager().beginTransaction().add(R.id.holder_layout,new DemoFragment()).commit();
        }else {
            Log.e("TAG", "有数据，走恢复");

        }
    }
}
