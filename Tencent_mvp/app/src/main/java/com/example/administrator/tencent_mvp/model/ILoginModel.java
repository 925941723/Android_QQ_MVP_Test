package com.example.administrator.tencent_mvp.model;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Administrator on 2015/12/10.
 */
public interface ILoginModel {
    public void login(Activity activity,OnLoginListener loginListener);
}
