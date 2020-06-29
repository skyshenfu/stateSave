package com.zty.statetest;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.savedstate.SavedStateRegistry;

import java.util.Random;

public class DemoFragment extends Fragment {
    public static final String STATE_DEMO="STATE_DEMO";
    public static final String STATE_BUNDLE_NAME1_KEY="STATE_BUNDLE_NAME1_KEY";
    //这个number实际上要写入到savedInstanceState
    private int number=new Random().nextInt(200);


    private SavedStateRegistry.SavedStateProvider savedStateProvider=new SavedStateRegistry.SavedStateProvider() {
        @NonNull
        @Override
        public Bundle saveState() {
            Bundle bundle=new Bundle();
            bundle.putInt(STATE_BUNDLE_NAME1_KEY,number);
            return bundle;
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("TAG", "onCreateView:"+this.toString());
        return inflater.inflate(R.layout.fragment_demo,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getSavedStateRegistry().registerSavedStateProvider(STATE_DEMO,savedStateProvider);
        getValueFromSavedInstanceState();
        Log.e("TAG", "onViewCreated: "+number);

    }

    private void getValueFromSavedInstanceState(){
        //拿到savedStateProvider saveState时候保存的bundle
        Bundle bundle=getSavedStateRegistry().consumeRestoredStateForKey(STATE_DEMO);
        if (bundle!=null){
            //拿到保存的值
            number= bundle.getInt(STATE_BUNDLE_NAME1_KEY);
        }
    }
}
