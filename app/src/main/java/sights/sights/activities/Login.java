package sights.sights.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import AppLogic.PreferenceData;
import HttpNetwork.LoginAsyncRunner;
import HttpNetwork.NetworkHelper;
import model.User;
import sights.sights.R;

public class Login extends Activity {

    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);

        checkforLoginStatus();

        logo = (ImageView) findViewById(R.id.logo);
        loginButton = (LoginButton)findViewById(R.id.login_button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                goToMap();
                loginResult.getAccessToken().getUserId();

                LoginAsyncRunner runner = new LoginAsyncRunner();
                String usertoken = loginResult.getAccessToken().getToken();
                String userid = loginResult.getAccessToken().getUserId();
                PreferenceData.setPrefLoggedinUserId(getApplicationContext(),userid);
                PreferenceData.setPrefUserLoggedinStatus(getApplicationContext(),true);
                setUser();
                runner.execute(usertoken,userid);
            }

            @Override
            public void onCancel() {
                info.setText("Login attempt cancelled.");
            }

            @Override
            public void onError(FacebookException e) {
                info.setText("Login attempt failed.");
            }
        });
    }

    private void checkforLoginStatus() {
        if(PreferenceData.getPrefUserLoggedinStatus(this) == false){
            return;
        }else{
            setUser();
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    protected void goToMap(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    protected void setUser(){
        User.getInstance().setUserId(PreferenceData.getPrefLoggedinUserId(this));
    }

}


