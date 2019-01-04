package com.feasycom.fsybecon.walkinradius.manager;

public class UserCredentialManager {

    private static String mUserName;
    private static String mUserStatus;

    public String getUserName() {
        return mUserName;
    }

    public String getUserStatus() {
        return mUserStatus;
    }

    public void setUserStatus(String userStatus) {
        this.mUserStatus = userStatus;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

}
