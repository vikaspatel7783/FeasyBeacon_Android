package com.feasycom.fsybecon.walkinradius.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feasycom.fsybecon.Activity.SetActivity;
import com.feasycom.fsybecon.R;
import com.feasycom.fsybecon.walkinradius.LoginFieldsValidator;
import com.feasycom.fsybecon.walkinradius.UiUtils;
import com.feasycom.fsybecon.walkinradius.manager.AccountBeaconManager;
import com.feasycom.fsybecon.walkinradius.manager.BeaconInfoException;
import com.feasycom.fsybecon.walkinradius.manager.UserCredentialManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.walkinradius.beacon.networking.AndroidNetworking;
import com.walkinradius.beacon.networking.model.BeaconInfo;
import com.walkinradius.beacon.networking.retrofit.RetrofitNetworking;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtTextPassword;
    private EditText edtTextUserName;
    private LinearLayout pgBarLayout;
    private String mUserName;

    private UserCredentialManager userCredentialManager = new UserCredentialManager();
    private AccountBeaconManager accountBeaconManager = new AccountBeaconManager();
    private AndroidNetworking mAndroidNetworking = new RetrofitNetworking();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkinradius_login);

        edtTextUserName = findViewById(R.id.edtTxtUsername);
        edtTextPassword = findViewById(R.id.edtTxtPassword);
        pgBarLayout = findViewById(R.id.progressBarLayout);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (!UiUtils.isNetworkConnected(this)) {
            showMessage(getString(R.string.no_internet_message));
            return;
        }

        if (!isCredentialsValidForLength(getUserName(), getPassword())) {
            return;
        }

        showProgressBar();
        setProgressBarMessage(getString(R.string.progress_msg_login));

        this.mUserName = getUserName();

        mAndroidNetworking.validateCredentials(getUserName(), getPassword(), callbackLogin);
    }

    public void showProgressBar() {
        pgBarLayout.setVisibility(View.VISIBLE);
    }

    private void setProgressBarMessage(String progressBarMessage) {
        ((TextView)pgBarLayout.findViewById(R.id.textViewPgbar)).setText(progressBarMessage);
    }

    public void hideProgressBar() {
        pgBarLayout.setVisibility(View.GONE);
    }

    public void showMessage(String message) {
        UiUtils.getAlertDialog(this, "LOGIN", message, null).show();
    }

    public void showUserNamePasswordBlankMessage() {
        UiUtils.getAlertDialog(this, "LOGIN", getResources().getString(R.string.message_username_or_password_blank), null).show();
    }

    public void showDashboard() {
        Intent intent = new Intent(this, SetActivity.class);
        startActivity(intent);

        finish();
    }

    private AndroidNetworking.Callback callbackLogin = new AndroidNetworking.Callback() {

        @Override
        public void onSuccess(String message) {

            userCredentialManager.setUserName(mUserName);
            userCredentialManager.setUserStatus("Active");

            setProgressBarMessage(getString(R.string.progress_msg_fetch_account_beacon));

            // Hit account beacon service
            mAndroidNetworking.getBeaconsInfo(callbackAccountBeacons,
                    userCredentialManager.getUserName(),
                    userCredentialManager.getUserStatus());
        }

        @Override
        public void onFailure(String message) {
            showMessage(message);
            hideProgressBar();
        }
    };

    private AndroidNetworking.Callback callbackAccountBeacons = new AndroidNetworking.Callback() {

        @Override
        public void onSuccess(String beaconsList) {
            hideProgressBar();
            handleServerBeaconsResponse(beaconsList);
        }

        @Override
        public void onFailure(String message) {
            hideProgressBar();
            showMessage(message);
        }
    };

    private boolean isCredentialsValidForLength(String userName, String password) {

        LoginFieldsValidator loginFieldsValidator = new LoginFieldsValidator();

        boolean isUserNameLengthNonZero = loginFieldsValidator.isLengthNonZero(userName);
        if (!isUserNameLengthNonZero) {
            showUserNamePasswordBlankMessage();
            return false;
        }

        boolean isPasswordLengthNonZero = loginFieldsValidator.isLengthNonZero(password);
        if (!isPasswordLengthNonZero) {
            showUserNamePasswordBlankMessage();
            return false;
        }

        return true;
    }

    public String getUserName() {
        return edtTextUserName.getText().toString();
    }

    public String getPassword() {
        return edtTextPassword.getText().toString();
    }

    public void handleServerBeaconsResponse(String beaconsListJson) {
        List<BeaconInfo> beaconsList = new Gson().fromJson(beaconsListJson, new TypeToken<List<BeaconInfo>>(){}.getType());

        // Mock testing
        //List<BeaconInfo> beaconsList = new ArrayList<>();
        //addMockBeaconInfoToList(beaconsList);
        //addMockBeaconInfoToList(beaconsList);
        //addMockBeaconInfoToList(beaconsList);

        if (null == beaconsList || beaconsList.size() == 0) {
            showMessage("No beacons received.");
            return;
        }

        accountBeaconManager.setAccountBeaconsList(beaconsList);
        try {
            accountBeaconManager.registerAccountMac();
        } catch (BeaconInfoException e) {
            UiUtils.getAlertDialog(this,
                    "AccountBeaconResponse",
                    e.getMessage(),
                    (dialog, which) -> finish()).show();
            return;
        }

        showDashboard();
    }
}
