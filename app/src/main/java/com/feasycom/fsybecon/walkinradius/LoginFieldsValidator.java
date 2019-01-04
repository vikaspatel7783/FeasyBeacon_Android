package com.feasycom.fsybecon.walkinradius;

public class LoginFieldsValidator {


    public boolean isLengthNonZero(String value) {

        if (null == value || value.length() == 0) {
            return false;
        }

        return true;
    }
}
