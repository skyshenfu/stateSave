package com.zty.statetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.savedstate.SavedStateRegistry;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //SavedStateProvider的Key
    public static final String STATE_BUNDLE_KEY="STATE_BUNDLE_KEY";
    //SavedStateProvider的内部管理的bundle的Key
    public static final String STATE_BUNDLE_NAME1_KEY="STATE_BUNDLE_NAME1_KEY";
    //这个number实际上要写入到savedInstanceState
    private int number=new Random().nextInt(99);

    Button vmSaveButton;
    EditText vmEditText;
    TextView randomTextView;
    private ViewModelWithSave mViewModel;

    private SavedStateRegistry.SavedStateProvider savedStateProvider=new SavedStateRegistry.SavedStateProvider() {
        @NonNull
        @Override
        public Bundle saveState() {
            Bundle bundle=new Bundle();
            bundle.putInt(STATE_BUNDLE_NAME1_KEY,number);
            return bundle;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vmSaveButton=findViewById(R.id.button);
        vmEditText=findViewById(R.id.vm_edit_text);
        randomTextView=findViewById(R.id.random_text_view);

        //一旦完成注册后
        //在Android P以上版本 onStop后 onSaveInstanceState会自动调用savedStateProvider的saveState方法
        getSavedStateRegistry().registerSavedStateProvider(STATE_BUNDLE_KEY,savedStateProvider);
        //这是个恢复方法
        getValueFromSavedInstanceState();
        //randomTextView显示的就是number的值，如果成功保存就会成功显示，
        randomTextView.setText("当前随机数"+number);

        //创建VM 在Activity包 >1.1.0  fragment包 >1.2.0时 ViewModelProvider会自动根据get（T）中T的构造函数
        //是否含有SavedStateHandle 来判断对应的VM是否需要坐持久化存储
        mViewModel=new ViewModelProvider(this).get(ViewModelWithSave.class);
        vmSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //手动调用一波
                mViewModel.saveName(vmEditText.getText().toString()+"保存");
            }
    });
        mViewModel.getName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                //打印出变化后LiveData的值
                Log.e("TAG", "onChanged: "+s);
            }
        });

    }

    private void getValueFromSavedInstanceState(){
        //拿到savedStateProvider saveState时候保存的bundle
        Bundle bundle=getSavedStateRegistry().consumeRestoredStateForKey(STATE_BUNDLE_KEY);
        if (bundle!=null){
           //拿到保存的值
           number= bundle.getInt(STATE_BUNDLE_NAME1_KEY);
        }
    }
}