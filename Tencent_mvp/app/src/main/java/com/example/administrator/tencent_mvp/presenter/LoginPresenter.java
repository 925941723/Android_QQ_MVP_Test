package com.example.administrator.tencent_mvp.presenter;

import android.app.Activity;
import android.content.Context;

import com.example.administrator.tencent_mvp.model.ILoginModel;
import com.example.administrator.tencent_mvp.model.LoginModel;
import com.example.administrator.tencent_mvp.model.OnLoginListener;
import com.example.administrator.tencent_mvp.view.ILoginActivityView;

/**
 * Created by Administrator on 2015/12/10.
 */
public class LoginPresenter {
    private ILoginModel iLoginModel;
    private ILoginActivityView iLoginActivityView;

    public LoginPresenter(ILoginActivityView iLoginActivityView){
        this.iLoginActivityView = iLoginActivityView;
        this.iLoginModel = new LoginModel();
    }

    public void login(Activity activity){
        iLoginModel.login(activity, new OnLoginListener() {
            @Override
            public void loginSuccess(String result) {
                iLoginActivityView.toMainActivity();
            }

            @Override
            public void loginFail() {
                iLoginActivityView.showFailedError();
            }
        });
    }
}
