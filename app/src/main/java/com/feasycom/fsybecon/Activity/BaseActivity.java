package com.feasycom.fsybecon.Activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.feasycom.controler.FscBeaconApiImp;
import com.feasycom.controler.FscSppApiImp;
import com.feasycom.fsybecon.Controler.ActivityCollector;

/**
 * Copyright 2017 Shenzhen Feasycom Technology co.,Ltd
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //干掉 activity 切换特效
//        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
//        Log.d("activity", getClass().getSimpleName());
//        Log.i("onCreat", getClass().getSimpleName());
        ActivityCollector.addActivity(this);
//        Log.i("count", ActivityCollector.getCount() + "");
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Log.i("onPause", getClass().getSimpleName());
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Log.i("onStart", getClass().getSimpleName());
    }

    @Override
    protected void onStop() {
        super.onStop();
//        Log.i("onStop", getClass().getSimpleName());
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Log.i("onResume", getClass().getSimpleName());
        refreshFooter();
        refreshHeader();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Log.i("onDestory", getClass().getSimpleName());
        ActivityCollector.removeActivity(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            ActivityCollector.finishAllActivity();
        }
        return super.onKeyDown(keyCode, event);
    }


    public abstract void refreshFooter();

    public abstract void refreshHeader();

    public abstract void initView();

    public abstract void setClick();

    public abstract void aboutClick();

    public abstract void searchClick();


    public void finishActivity() {
//        Log.i("finish",this.getClass().getSimpleName());
        this.finish();
    }
}
