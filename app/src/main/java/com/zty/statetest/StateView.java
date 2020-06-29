package com.zty.statetest;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;

/**
 * View恢复状态的方式比较传统，由于没有Stated包加持 恢复方式比较坑
 * 必要条件：给使用View的地方设置ID
 * 延伸：如果View中含有自定义属性需要恢复状态则需要在onSaveInstanceState onRestoreInstanceState方法中额外处理
 */
import java.util.Random;

public class StateView extends androidx.appcompat.widget.AppCompatTextView {
    private int number=new Random().nextInt(2333);

    public StateView(Context context) {
        this(context,null);
    }

    public StateView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public StateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    public Parcelable onSaveInstanceState() {
        //给super.onSaveInstanceState() 增加持久化值
        SaveState state = new SaveState(super.onSaveInstanceState());
        state.setSavedState(number);

        Log.e("TAG", "onSaveInstanceState: "+number);
        return state;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        //取得持久化值
        SaveState savedState = (SaveState) state;
        super.onRestoreInstanceState(savedState.getParcelable());
        number = savedState.getSavedState();
        Log.e("TAG", "onRestoreInstanceState: "+number);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("TAG", "onSizeChanged: "+number);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("TAG", "draw: "+number);
    }

    static class SaveState implements Parcelable {

        private Integer mSavedState;//保存View中的状态变量

        private Parcelable mParcelable;//保存View的默认状态

        SaveState(Parcelable parcelable) {
            mParcelable = parcelable;
        }

        protected SaveState(Parcel in) {
            mSavedState = in.readInt();
        }

        public static final Creator<SaveState> CREATOR = new Creator<SaveState>() {
            @Override
            public SaveState createFromParcel(Parcel in) {
                return new SaveState(in);
            }

            @Override
            public SaveState[] newArray(int size) {
                return new SaveState[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(mSavedState);
        }

        public Integer getSavedState() {
            return mSavedState;
        }

        public void setSavedState(Integer mSavedState) {
            this.mSavedState = mSavedState;
        }

        public Parcelable getParcelable() {
            return mParcelable;
        }

        public void setParcelable(Parcelable mParcelable) {
            this.mParcelable = mParcelable;
        }
    }

}

