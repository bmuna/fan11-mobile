package com.fan.core.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.fan.core.model.update.UpdateBean;
import com.fan.core.model.user.Responsedata;
import com.fan.core.model.user.UserBean;

/**
 * Created by mohit.soni @ 01-Oct-19.
 */

public class SharedPrefUtility {

    public static final String ID = "id";
    public static final String USER_TYPE = "user_type";
    public static final String FB_ID = "fb_id";
    public static final String GOOGLE_ID = "google_id";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String MOBILE_NO = "mobile_no";
    public static final String PROFILE_IMAGE = "profile_image";
    public static final String PROFILE_IMAGE_LOCAL = "profile_image_local";
    public static final String STATUS = "status";
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";
    public static final String DOB = "dob";
    public static final String ADDRESS = "address";
    public static final String CITY_NAME = "city_name";
    public static final String CITY = "city";
    public static final String STATE_NAME = "state_name";
    public static final String STATE = "state";
    public static final String GENDER = "gender";
    public static final String REFERRALCODE = "referralcode";
    public static final String REFERBY = "referby";
    public static final String COUNTRY_NAME = "country_name";
    public static final String COUNTRY = "country";
    public static final String POSTAL_CODE = "postal_code";
    public static final String NOTIFICATION = "notification";
    public static final String UPLOAD_ID = "upload_id";
    public static final String UPLOAD_ID_LOCAL = "upload_id_local";
    public static final String UPLOAD_SIGNATURE = "upload_signature";
    public static final String UPLOAD_SIGNATURE_LOCAL = "upload_signature_local";
    public static final String MY_BALANCE = "my_balance";
    public static final String LANGUAGE = "language";

    public static final String REMEMBER_ME = "remember_me";
    public static final String LANDING = "landing";
    private final String PREF_LOGIN = "login_pref";
    private Context context;
    public  SharedPrefUtility(Context context) {
        this.context = context;
    }

    /**
     * to save user data
     */
    public void setUserData(UserBean responseModel) {
        Responsedata responsedata = responseModel.getResponsedata();
        if (responseModel != null) {
            SharedPreferences preferences = context.getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString(ID, responsedata.getId());
            editor.putString(USER_TYPE, responsedata.getUserType());
            editor.putString(FB_ID, responsedata.getFbId());
            editor.putString(GOOGLE_ID, responsedata.getGoogleId());
            editor.putString(EMAIL, responsedata.getEmail());
            editor.putString(PASSWORD, responsedata.getPassword());
            editor.putString(FIRST_NAME,responsedata.getFirstName());
            editor.putString(LAST_NAME, responsedata.getLastName());
            editor.putString(MOBILE_NO, responsedata.getMobileNo());
            editor.putString(PROFILE_IMAGE, responsedata.getProfileImage());
            editor.putString(STATUS, responsedata.getStatus());
            editor.putString(CREATED_AT, responsedata.getCreatedAt());
            editor.putString(UPDATED_AT, responsedata.getUpdatedAt());
            editor.putString(DOB,responsedata.getDob());
            editor.putString(ADDRESS, responsedata.getAddress());
            editor.putString(CITY_NAME, responsedata.getCity_name());
            editor.putString(CITY, responsedata.getCity());
            editor.putString(STATE_NAME, responsedata.getState_name());
            editor.putString(STATE, responsedata.getState());
            editor.putString(COUNTRY_NAME, responsedata.getCountry_name());
            editor.putString(GENDER, responsedata.getGender());
            editor.putString(REFERRALCODE, responsedata.getReferralCode());
            editor.putString(REFERBY, responsedata.getReferBy());
            editor.putString(COUNTRY, responsedata.getCountry());
            editor.putString(POSTAL_CODE, responsedata.getPostalCode());
            editor.putString(NOTIFICATION,responsedata.getNotification());
            editor.putString(UPLOAD_ID, responsedata.getUploadId());
            editor.putString(UPLOAD_SIGNATURE,responsedata.getUploadSignature());
            editor.putString(PROFILE_IMAGE_LOCAL,"");
            editor.putString(UPLOAD_ID_LOCAL,"");
            editor.putString(UPLOAD_SIGNATURE_LOCAL,"");
            editor.putString(MY_BALANCE,responsedata.getMyBalance());
            editor.putBoolean(REMEMBER_ME,responsedata.isRememberMe());
            editor.apply();
        }
    }

    public void updateUserData(UpdateBean responseModel) {
        com.fan.core.model.update.Responsedata responsedata = responseModel.getResponsedata();
        if (responseModel != null) {
            SharedPreferences preferences = context.getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString(ID, responsedata.getId());
            editor.putString(USER_TYPE, responsedata.getUserType());
            editor.putString(FB_ID, responsedata.getFbId());
            editor.putString(GOOGLE_ID, responsedata.getGoogleId());
            editor.putString(EMAIL, responsedata.getEmail());
            editor.putString(PASSWORD, responsedata.getPassword());
            editor.putString(FIRST_NAME,responsedata.getFirstName());
            editor.putString(LAST_NAME, responsedata.getLastName());
            editor.putString(MOBILE_NO, responsedata.getMobileNo());
            editor.putString(PROFILE_IMAGE, responsedata.getProfileImage());
            editor.putString(STATUS, responsedata.getStatus());
            editor.putString(CREATED_AT, responsedata.getCreatedAt());
            editor.putString(UPDATED_AT, responsedata.getUpdatedAt());
            editor.putString(DOB,responsedata.getDob());
            editor.putString(ADDRESS, responsedata.getAddress());
            editor.putString(CITY_NAME, responsedata.getCity_name());
            editor.putString(CITY, responsedata.getCity());
            editor.putString(STATE_NAME, responsedata.getState_name());
            editor.putString(STATE, responsedata.getState());
            editor.putString(GENDER, responsedata.getGender());
            editor.putString(REFERRALCODE, responsedata.getReferralCode());
            editor.putString(REFERBY, responsedata.getReferBy());
            editor.putString(COUNTRY_NAME, responsedata.getCountry_name());
            editor.putString(COUNTRY, responsedata.getCountry());
            editor.putString(POSTAL_CODE, responsedata.getPostalCode());
            editor.putString(NOTIFICATION,responsedata.getNotification());
            editor.putString(UPLOAD_ID, responsedata.getUploadId());
            editor.putString(UPLOAD_SIGNATURE,responsedata.getUploadSignature());
            editor.putString(PROFILE_IMAGE_LOCAL,"");
            editor.putString(UPLOAD_ID_LOCAL,"");
            editor.putString(UPLOAD_SIGNATURE_LOCAL,"");
            editor.putString(MY_BALANCE,responsedata.getMyBalance());
            editor.putBoolean(REMEMBER_ME,responsedata.isRememberMe());
            editor.apply();
        }
    }

    public void updateUserData(String tag,String data) {
            SharedPreferences preferences = context.getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(tag, data);

            editor.apply();
    }


/*    public void setLoginData(Responsedata responsedata) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(EMAIL, responsedata.getEmail());
        editor.putString(PASSWORD, responsedata.getPassword());
        editor.apply();
    }*/

    /**
     * get login detail
     * @return
     */
    public Responsedata getUserdata() {
        Responsedata userData = new Responsedata();
        SharedPreferences preferences = context.getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE);
        userData.setId(preferences.getString(ID, null));
        userData.setUserType(preferences.getString(USER_TYPE, null));
        userData.setFbId(preferences.getString(FB_ID, null));
        userData.setGoogleId(preferences.getString(GOOGLE_ID, null));
        userData.setEmail(preferences.getString(EMAIL, null));
        userData.setPassword(preferences.getString(PASSWORD, null));
        userData.setFirstName(preferences.getString(FIRST_NAME, null));
        userData.setLastName(preferences.getString(LAST_NAME, null));
        userData.setMobileNo(preferences.getString(MOBILE_NO, null));
        userData.setProfileImage(preferences.getString(PROFILE_IMAGE, null));
        userData.setStatus(preferences.getString(STATUS, null));
        userData.setCreatedAt(preferences.getString(CREATED_AT, null));
        userData.setUpdatedAt(preferences.getString(UPDATED_AT, null));
        userData.setDob(preferences.getString(DOB, null));
        userData.setGender(preferences.getString(GENDER, null));
        userData.setReferralCode(preferences.getString(REFERRALCODE, null));
        userData.setReferBy(preferences.getString(REFERBY, null));
        userData.setAddress(preferences.getString(ADDRESS, null));
        userData.setCity_name(preferences.getString(CITY_NAME, null));
        userData.setCity(preferences.getString(CITY, null));
        userData.setState_name(preferences.getString(STATE_NAME, null));
        userData.setState(preferences.getString(STATE, null));
        userData.setCountry_name(preferences.getString(COUNTRY_NAME, null));
        userData.setCountry(preferences.getString(COUNTRY, null));
        userData.setPostalCode(preferences.getString(POSTAL_CODE, null));
        userData.setNotification(preferences.getString(NOTIFICATION, null));
        userData.setUploadId(preferences.getString(UPLOAD_ID, null));
        userData.setUploadSignature(preferences.getString(UPLOAD_SIGNATURE, null));
        userData.setProfile_local_path(preferences.getString(PROFILE_IMAGE_LOCAL, null));
        userData.setUpload_id_local(preferences.getString(UPLOAD_ID_LOCAL, null));
        userData.setUpload_signature_local(preferences.getString(UPLOAD_SIGNATURE_LOCAL, null));
        userData.setMyBalance(preferences.getString(MY_BALANCE, null));
        userData.setRememberMe(preferences.getBoolean(REMEMBER_ME, false));
        return userData;
    }


    /**
     * clear all {@link SharedPreferences} data
     */
    public void clearPrefData() {
        SharedPreferences preferences;
        preferences = context.getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ID, "");
        editor.putString(USER_TYPE, "");
        editor.putString(FB_ID, "");
        editor.putString(GOOGLE_ID, "");
        editor.putString(EMAIL, "");
        editor.putString(PASSWORD, "");
        editor.putString(FIRST_NAME,"");
        editor.putString(LAST_NAME, "");
        editor.putString(MOBILE_NO, "");
        editor.putString(PROFILE_IMAGE, "");
        editor.putString(STATUS, "");
        editor.putString(CREATED_AT, "");
        editor.putString(UPDATED_AT, "");
        editor.putString(DOB,"");
        editor.putString(ADDRESS, "");
        editor.putString(CITY, "");
        editor.putString(CITY_NAME, "");
        editor.putString(STATE, "");
        editor.putString(STATE_NAME, "");
        editor.putString(COUNTRY, "");
        editor.putString(COUNTRY_NAME, "");
        editor.putString(GENDER, "");
        editor.putString(REFERRALCODE, "");
        editor.putString(REFERBY, "");
        editor.putString(POSTAL_CODE, "");
        editor.putString(NOTIFICATION,"");
        editor.putString(UPLOAD_ID, "");
        editor.putString(UPLOAD_SIGNATURE,"");
        editor.putString(PROFILE_IMAGE_LOCAL,"");
        editor.putString(UPLOAD_ID_LOCAL,"");
        editor.putString(UPLOAD_SIGNATURE_LOCAL,"");
        editor.putString(MY_BALANCE,"");
        editor.putBoolean(REMEMBER_ME,false);
        editor.apply();
    }

    /**
     * set Access
     * @param access
     */
    public void setAccess(Boolean access) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(REMEMBER_ME, access);
        editor.apply();
    }

    /**
     * set language flag
     * @param lng
     */
    public void setLanguage(String lng) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LANGUAGE, lng);
        editor.apply();
    }

    /**
     * get language flag
     * @return
     */
    public String getLanguage() {
        SharedPreferences preferences = context.getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE);
        return preferences.getString(LANGUAGE, "eng");
    }

    /**
     * set landing
     * @param show
     */
    public void setlanding(Boolean show) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(LANDING, show);
        editor.apply();
    }

    /**
     * get landing
     * @return
     */
    public boolean getlanding() {
        SharedPreferences preferences = context.getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE);
        return preferences.getBoolean(LANDING, true);
    }

    /**
     * get Access
     * @return
     */
    public boolean getAccess() {
        SharedPreferences preferences = context.getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE);
        return preferences.getBoolean(REMEMBER_ME, false);
    }

    /**
     * set state data
     * @param key
     * @param key_name
     * @param value
     * @param value_name
     */
    public void setStateCity(String key,String key_name,String value,String value_name) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, key_name);
        editor.putString(value, value_name);
        editor.apply();
    }
}
