package com.example.administrator.tencent_mvp.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.tencent_mvp.utils.AppConstant;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/12/10.
 */
public class LoginModel extends Activity implements ILoginModel {
    private Tencent mTencent;
    private Activity activity;
    private OnLoginListener loginListener;
    public static String mAppid;
    public static String openidString;
    public static String TAG = "LoginActivity";

    @Override
    public void login(Activity activity,OnLoginListener loginListener) {
        this.activity = activity;
        this.loginListener = loginListener;
        mAppid = AppConstant.APP_ID;
        mTencent = Tencent.createInstance(mAppid, this.activity.getApplicationContext());
        mTencent.login(this.activity, "all", new BaseUiListener());
    }

//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == Constants.REQUEST_LOGIN) {
//            Tencent.onActivityResultData(requestCode,resultCode,data,new BaseUiListener());
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    /**
     * 当自定义的监听器实现IUiListener接口后，必须要实现接口的三个方法，
     *      * onComplete  onCancel onError
     *      *分别表示第三方登录成功，取消 ，错误。
     */
    private class BaseUiListener implements IUiListener {

        public void onCancel() {
            // TODO Auto-generated method stub
//            Toast.makeText(getApplicationContext(), "取消登录", Toast.LENGTH_SHORT).show();
        }

        public void onComplete(Object response) {
            // TODO Auto-generated method stub
//            Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
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
//            Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
        }
    }
}
