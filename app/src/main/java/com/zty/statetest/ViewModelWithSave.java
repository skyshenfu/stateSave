package com.zty.statetest;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public class ViewModelWithSave extends ViewModel {
    //所有要保存的LiveData都存在这里
    private SavedStateHandle mState;
    //LiveData对应的Key
    private static final String NAME_KEY = "NAME_KEY";

    public ViewModelWithSave(SavedStateHandle mState) {
        this.mState = mState;
    }
    public LiveData<String> getName(){
        //拿LiveData
        return  mState.getLiveData(NAME_KEY);
    }

    public void  saveName(String name){
        //存LiveData
        mState.set(NAME_KEY,name);
    }

}
