package com.example.administrator.tencent_mvp.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.tencent_mvp.R;
import com.example.administrator.tencent_mvp.model.OnLoginListener;
import com.example.administrator.tencent_mvp.presenter.LoginPresenter;
import com.example.administrator.tencent_mvp.utils.AppConstant;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity implements View.OnClickListener,ILoginActivityView {
    TextView openidTextView;
    Button loginButton;
    private LoginPresenter loginPresenter = new LoginPresenter(this);
    private Tencent mTencent;
    private Activity activity;
    private OnLoginListener loginListener;
    public static String mAppid;
    public static String openidString;
    public static String TAG = "LoginActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //用来登录的Button
        loginButton = (Button) findViewById(R.id.login);
        loginButton.setOnClickListener(this);
        //用来显示OpenID的textView
        openidTextView = (TextView) findViewById(R.id.user_openid);
    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.login:
                loginPresenter.login(this);
                break;

            default:
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode,resultCode,data,new BaseUiListener());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 当自定义的监听器实现IUiListener接口后，必须要实现接口的三个方法，
     *      * onComplete  onCancel onError
     *      *分别表示第三方登录成功，取消 ，错误。
     */
    private class BaseUiListener implements IUiListener {

        public void onCancel() {
            // TODO Auto-generated method stub
            Toast.makeText(getApplicationContext(), "取消登录", Toast.LENGTH_SHORT).show();
        }

        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
            try {
                //获得的数据是JSON格式的，获得你想获得的内容
                //如果你不知道你能获得什么，看一下下面的LOG
                Log.e(TAG, "-------------" + response.toString());
                openidString = ((JSONObject) response).getString("openid");
                loginListener.loginSuccess(openidString);
//                openidTextView.setText(openidString);
                Log.e(TAG, "-------------" + openidString);
                //access_token= ((JSONObject) response).getString("access_token");              //expires_in = ((JSONObject) response).getString("expires_in");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        public void onError(UiError arg0) {
            // TODO Auto-generated method stub
            loginListener.loginFail();
            Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void toMainActivity() {
        Toast.makeText(this,
                " login success , to MainActivity", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFailedError() {
        Toast.makeText(this,
                "login failed", Toast.LENGTH_SHORT).show();
    }
}