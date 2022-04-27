package com.fan.core.module;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.fan.core.R;
import com.fan.core.model.city.CityBean;
import com.fan.core.model.country.Responsedatum;
import com.fan.core.model.state.StateBean;
import com.fan.core.model.update_profile.UpdateProfileBean;
import com.fan.core.model.user.Responsedata;
import com.fan.core.util.AppConstants;
import com.fan.core.util.CircleImageView;
import com.fan.core.util.NetworkAPI;
import com.fan.core.util.SharedPrefUtility;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;

import static com.fan.core.util.AppConstants.GET_TABLE_DATA_CITY;
import static com.fan.core.util.AppConstants.GET_TABLE_DATA_STATE;
import static com.fan.core.util.AppConstants.callCountry;
import static com.fan.core.util.AppConstants.callLanguage;
import static com.fan.core.util.AppConstants.city;
import static com.fan.core.util.AppConstants.city_responsedata;
import static com.fan.core.util.AppConstants.country;
import static com.fan.core.util.AppConstants.getUserSaved;
import static com.fan.core.util.AppConstants.profile_pic;
import static com.fan.core.util.AppConstants.startTutorial;
import static com.fan.core.util.AppConstants.state;
import static com.fan.core.util.AppConstants.state_responsedata;

/**
 * Created by mohit.soni @ 07-Oct-19.
 */
public class Personal_Information extends BaseActivity {
    public static final String TAG = Register.class.getSimpleName();

    TextView tv_personal_information_first_name_change, tv_personal_information_last_name_change,
            tv_personal_information_password_change, tv_personal_information_country_change,
            tv_personal_information_address_change, tv_personal_information_city_change,
            tv_personal_information_pincode_change, tv_personal_information_state_change, tv_toolbar_head,
            tv_toolbar_language;
    /*Switch sw_personal_information_auto_sms;*/
    EditText et_personal_information_first_name, et_personal_information_last_name,
            et_personal_information_email, et_personal_information_password, et_personal_information_country,
            et_personal_information_dob, et_personal_information_mobile, et_personal_information_address,
            et_personal_information_city, et_personal_information_pincode, et_personal_information_state;

    RelativeLayout rl_personal_information_mobile, rl_personal_information_dob, rl_personal_information_password,
            rl_personal_information_email;

    CircleImageView iv_toolbar_profile_image;
    ImageView iv_toolbar_back;
    ImageView iv_toolbar_icon_tutorial,iv_toolbar_wallet_icon;

    boolean first_name_change = true;
    boolean last_name_change = true;
    boolean pass_change = true;
    boolean add_change = true;
    boolean pin_change = true;

    Responsedata responsedata;
    Resources resources;
    ArrayList<EditText> editTextArrayList = new ArrayList<>();
    HashMap<EditText, TextView> editTextTextViewHashMap = new HashMap<>();

    public static int UPDATE_PROFILE_EDIT = 1001;
    public static int UPDATE_PROFILE_SWITCH_ON = 1003;
    public static int UPDATE_PROFILE_SWITCH_OFF = 1004;

    public boolean init = true;

    LinearLayout ll_toolbar_language_change;
    TextView tv_toolbar_language_amheric,tv_toolbar_language_english;
    SharedPrefUtility prefUtility;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_information);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        }
        resources = getResources();
        tv_personal_information_first_name_change = (TextView) findViewById(R.id.tv_personal_information_first_name_change);
        tv_personal_information_last_name_change = (TextView) findViewById(R.id.tv_personal_information_last_name_change);
        tv_personal_information_password_change = (TextView) findViewById(R.id.tv_personal_information_password_change);
        tv_personal_information_address_change = (TextView) findViewById(R.id.tv_personal_information_address_change);
        tv_personal_information_city_change = (TextView) findViewById(R.id.tv_personal_information_city_change);
        tv_personal_information_pincode_change = (TextView) findViewById(R.id.tv_personal_information_pincode_change);
        tv_personal_information_state_change = (TextView) findViewById(R.id.tv_personal_information_state_change);
        tv_personal_information_country_change = (TextView) findViewById(R.id.tv_personal_information_country_change);
        tv_toolbar_language = (TextView) findViewById(R.id.tv_toolbar_language);

        iv_toolbar_back = (ImageView) findViewById(R.id.iv_toolbar_back);
        iv_toolbar_profile_image = (CircleImageView) findViewById(R.id.iv_toolbar_profile_image);
        tv_toolbar_head = (TextView) findViewById(R.id.tv_toolbar_head);
        iv_toolbar_icon_tutorial = (ImageView) findViewById(R.id.iv_toolbar_icon_tutorial);
        iv_toolbar_wallet_icon= (ImageView) findViewById(R.id.iv_toolbar_wallet_icon);


        rl_personal_information_mobile = (RelativeLayout) findViewById(R.id.rl_personal_information_mobile);
        rl_personal_information_dob = (RelativeLayout) findViewById(R.id.rl_personal_information_dob);
        rl_personal_information_password = (RelativeLayout) findViewById(R.id.rl_personal_information_password);
        rl_personal_information_email = (RelativeLayout) findViewById(R.id.rl_personal_information_email);

        et_personal_information_first_name = (EditText) findViewById(R.id.et_personal_information_first_name);
        et_personal_information_last_name = (EditText) findViewById(R.id.et_personal_information_last_name);
        et_personal_information_email = (EditText) findViewById(R.id.et_personal_information_email);
        et_personal_information_password = (EditText) findViewById(R.id.et_personal_information_password);
        et_personal_information_dob = (EditText) findViewById(R.id.et_personal_information_dob);
        et_personal_information_mobile = (EditText) findViewById(R.id.et_personal_information_mobile);
        et_personal_information_address = (EditText) findViewById(R.id.et_personal_information_address);
        et_personal_information_city = (EditText) findViewById(R.id.et_personal_information_city);
        et_personal_information_pincode = (EditText) findViewById(R.id.et_personal_information_pincode);
        et_personal_information_state = (EditText) findViewById(R.id.et_personal_information_state);
        et_personal_information_country = (EditText) findViewById(R.id.et_personal_information_country);

/*        sw_personal_information_auto_sms = (Switch) findViewById(R.id.sw_personal_information_auto_sms);*/

        tv_toolbar_language_amheric = (TextView) findViewById(R.id.tv_toolbar_language_amheric);
        tv_toolbar_language_english = (TextView) findViewById(R.id.tv_toolbar_language_english);
        ll_toolbar_language_change = (LinearLayout) findViewById(R.id.ll_toolbar_language_change);

        iv_toolbar_icon_tutorial.setVisibility(View.VISIBLE);
        iv_toolbar_wallet_icon.setVisibility(View.VISIBLE);
        tv_toolbar_head.setText(getResources().getString(R.string.personal_information));
        if (profile_pic != null) {
            iv_toolbar_profile_image.setImageBitmap(profile_pic);
        }
        try {
            responsedata = getUserSaved(this);
        } catch (NullPointerException e) {
        }

        et_personal_information_first_name.setTag("first_name");
        et_personal_information_last_name.setTag("last_name");
        et_personal_information_password.setTag("password");
        tv_personal_information_country_change.setTag("country");
        et_personal_information_address.setTag("address");
        et_personal_information_city.setTag("city");
        et_personal_information_pincode.setTag("postal_code");
        et_personal_information_state.setTag("state");
        et_personal_information_country.setTag("country");

        tv_personal_information_first_name_change.setTag(true);
        tv_personal_information_last_name_change.setTag(true);
        tv_personal_information_password_change.setTag(true);
        tv_personal_information_address_change.setTag(true);
        et_personal_information_address.setTag(true);
        tv_personal_information_city_change.setTag(true);
        tv_personal_information_pincode_change.setTag(true);
        tv_personal_information_state_change.setTag(true);
        tv_personal_information_country_change.setTag(true);

        editTextTextViewHashMap.put(et_personal_information_first_name, tv_personal_information_first_name_change);
        editTextTextViewHashMap.put(et_personal_information_last_name, tv_personal_information_last_name_change);
        editTextTextViewHashMap.put(et_personal_information_password, tv_personal_information_password_change);
        editTextTextViewHashMap.put(et_personal_information_address, tv_personal_information_address_change);
        editTextTextViewHashMap.put(et_personal_information_city, tv_personal_information_city_change);
        editTextTextViewHashMap.put(et_personal_information_pincode, tv_personal_information_pincode_change);
        editTextTextViewHashMap.put(et_personal_information_state, tv_personal_information_state_change);
        editTextTextViewHashMap.put(et_personal_information_country, tv_personal_information_country_change);

       /* editTextArrayList.add(et_personal_information_first_name);
        editTextArrayList.add(et_personal_information_last_name);
        editTextArrayList.add(et_personal_information_password);
        editTextArrayList.add(et_personal_information_address);
        editTextArrayList.add(et_personal_information_city);
        editTextArrayList.add(et_personal_information_pincode);
        editTextArrayList.add(et_personal_information_state);
        editTextArrayList.add(et_personal_information_country);*/


        iv_toolbar_wallet_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Personal_Information.this, MyBalanceActivity.class));
            }
        });
        iv_toolbar_icon_tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTutorial(Personal_Information.this);
            }
        });
        tv_personal_information_first_name_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppConstants.isOnline(Personal_Information.this)) {
                    first_name_change = stateChange(first_name_change, tv_personal_information_first_name_change, et_personal_information_first_name);
                } else {
                    showToast(getResources().getString(R.string.no_internet_connection));
                }
            }
        });
        tv_personal_information_last_name_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppConstants.isOnline(Personal_Information.this)) {
                    last_name_change = stateChange(last_name_change, tv_personal_information_last_name_change, et_personal_information_last_name);
                } else {
                    showToast(getResources().getString(R.string.no_internet_connection));
                }
            }
        });
        tv_personal_information_password_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppConstants.isOnline(Personal_Information.this)) {
                    pass_change = stateChange(pass_change, tv_personal_information_password_change, et_personal_information_password);
                } else {
                    showToast(getResources().getString(R.string.no_internet_connection));
                }
            }
        });
        tv_personal_information_country_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppConstants.isOnline(Personal_Information.this)) {
                    editTextArrayList.clear();
                    editTextArrayList.add(et_personal_information_country);
                    string = et_personal_information_country.getTag().toString() + "#" + et_personal_information_country.getText().toString();
                    callCountry(et_personal_information_country, Personal_Information.this, Personal_Information.this);
                } else {
                    showToast(getResources().getString(R.string.no_internet_connection));
                }
            }
        });

        tv_personal_information_address_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppConstants.isOnline(Personal_Information.this)) {
                    add_change = stateChange(add_change, tv_personal_information_address_change, et_personal_information_address);
                } else {
                    showToast(getResources().getString(R.string.no_internet_connection));
                }
            }
        });
        tv_personal_information_city_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppConstants.isOnline(Personal_Information.this)) {
                    sendUpdateCityRequest();
                    editTextArrayList.clear();
                    editTextArrayList.add(et_personal_information_city);
                    string = et_personal_information_city.getTag().toString() + "#" + et_personal_information_city.getText().toString();
                } else {
                    showToast(getResources().getString(R.string.no_internet_connection));
                }
            }
        });
        tv_personal_information_pincode_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppConstants.isOnline(Personal_Information.this)) {
                    pin_change = stateChange(pin_change, tv_personal_information_pincode_change, et_personal_information_pincode);
                } else {
                    showToast(getResources().getString(R.string.no_internet_connection));
                }

            }
        });
        tv_personal_information_state_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppConstants.isOnline(Personal_Information.this)) {
                    sendUpdateStateRequest();
                    editTextArrayList.clear();
                    editTextArrayList.add(et_personal_information_state);
                    string = et_personal_information_state.getTag().toString() + "#" + et_personal_information_state.getText().toString();
                } else {
                    showToast(getResources().getString(R.string.no_internet_connection));
                }
            }
        });
        /*sw_personal_information_auto_sms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (AppConstants.isOnline(Personal_Information.this)) {
                    EditText editText = new EditText(Personal_Information.this);
                    editText.setTag("notification");
                    if (!init) {
                        showDialog("Updating", true);
                        if (isChecked) {
                            editText.setText("enable");
                            sendUpdateRequest(editText, UPDATE_PROFILE_SWITCH_ON, 1);
                        }
                        if (!isChecked) {
                            editText.setText("disable");
                            sendUpdateRequest(editText, UPDATE_PROFILE_SWITCH_OFF, 1);
                        }
                    }

                } else {
                    showToast(getResources().getString(R.string.no_internet_connection));
                }
            }
        });*/

        iv_toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        iv_toolbar_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        et_personal_information_first_name.setText(responsedata.getFirstName());
        et_personal_information_last_name.setText(responsedata.getLastName());
        et_personal_information_password.setText(responsedata.getPassword());
        String[] date = responsedata.getDob().split("-");
        et_personal_information_dob.setText(date[1] + "-" + date[2] + "-" + date[0]);
        et_personal_information_mobile.setText(responsedata.getMobileNo());
        et_personal_information_email.setText(responsedata.getEmail());
        et_personal_information_country.setText(country != null ? country.getName() : "");
        et_personal_information_address.setText(responsedata.getAddress());
        et_personal_information_city.setText(city != null ? city.getName() : "");
        et_personal_information_pincode.setText(responsedata.getPostalCode());
        et_personal_information_state.setText(state != null ? state.getName() : "");
       /* if (responsedata.getNotification().equals("enable")) {
            sw_personal_information_auto_sms.setChecked(true);
        }
        if (responsedata.getNotification().equals("disable")) {
            sw_personal_information_auto_sms.setChecked(false);
        }*/

        if (responsedata.getEmail().equals("")) {
            rl_personal_information_email.setVisibility(View.GONE);
        }
        if (responsedata.getMobileNo().equals("")) {
            rl_personal_information_mobile.setVisibility(View.GONE);
        }
        if (responsedata.getUserType().equals("facebook")) {
            rl_personal_information_mobile.setVisibility(View.GONE);
            rl_personal_information_dob.setVisibility(View.GONE);
            rl_personal_information_password.setVisibility(View.GONE);
            rl_personal_information_email.setVisibility(View.GONE);
        }
        if (responsedata.getUserType().equals("telegram")) {
            rl_personal_information_mobile.setVisibility(View.GONE);
            rl_personal_information_dob.setVisibility(View.GONE);
            rl_personal_information_password.setVisibility(View.GONE);
            rl_personal_information_email.setVisibility(View.GONE);
        }
        tv_toolbar_language_amheric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prefUtility != null) {
                    prefUtility.setLanguage("ar");
                }
                ll_toolbar_language_change.setBackground(resources.getDrawable(R.drawable.round_button_toolbar_language_amheric));
                tv_toolbar_language_amheric.setTextColor(resources.getColor(R.color.black));
                tv_toolbar_language_english.setTextColor(resources.getColor(R.color.grey));
                callLanguage(Personal_Information.this,Personal_Information.this,Personal_Information.class,resources,1);
            }
        });
        tv_toolbar_language_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prefUtility != null) {
                    prefUtility.setLanguage("en");
                }
                ll_toolbar_language_change.setBackground(resources.getDrawable(R.drawable.round_button_toolbar_language_english));
                tv_toolbar_language_english.setTextColor(resources.getColor(R.color.black));
                tv_toolbar_language_amheric.setTextColor(resources.getColor(R.color.grey));
                callLanguage(Personal_Information.this,Personal_Information.this,Personal_Information.class,resources,0);
            }
        });
        /*tv_toolbar_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callLanguage(MainFragmentActivity.this,MainFragmentActivity.this,MainFragmentActivity.class,resources);
            }
        });*/
        /*tv_toolbar_language.setText(CURRENT_LANGUAGE);*/
        init = true;
        prefUtility = new SharedPrefUtility(this);
        if (prefUtility != null) {
            String lng = prefUtility.getLanguage();
            if(lng.equals("en")){
                ll_toolbar_language_change.setBackground(resources.getDrawable(R.drawable.round_button_toolbar_language_english));
                tv_toolbar_language_english.setTextColor(resources.getColor(R.color.black));
                tv_toolbar_language_amheric.setTextColor(resources.getColor(R.color.grey));
            }else{
                ll_toolbar_language_change.setBackground(resources.getDrawable(R.drawable.round_button_toolbar_language_amheric));
                tv_toolbar_language_amheric.setTextColor(resources.getColor(R.color.black));
                tv_toolbar_language_english.setTextColor(resources.getColor(R.color.grey));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        init = false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        init = true;
        startActivity(new Intent(Personal_Information.this, Profile.class));
        finish();
    }

    @Override
    public void onResponseListener(int requestCode, Object responseModel) {
        try {
            log(TAG, responseModel.toString());
            Gson gson = new Gson();
            if (requestCode == UPDATE_PROFILE_EDIT) {
                String[] updateUI = string.split("#");
                EditText editText = editTextArrayList.get(0);
                UpdateProfileBean bean = gson.fromJson((String) responseModel, UpdateProfileBean.class);
                if (bean.getResponseCode() == 1) {
                    editText.setText(updateUI[2]);
                    editText.setTextColor(Color.BLACK);
                    if (editText.getTag().toString().equals("country")) {
                        state = null;
                        city = null;
                        et_personal_information_state.setText("");
                        et_personal_information_city.setText("");
                        et_personal_information_pincode.setText("");
                        prefUtility.setStateCity("state", "", "state_name", "");
                        prefUtility.setStateCity("city", "", "city_name", "");
                        prefUtility.updateUserData("postal_code", "");
                        prefUtility.updateUserData("country", country.getCountryId());
                        prefUtility.updateUserData("country_name", updateUI[2]);
                    }
                    if (editText.getTag().toString().equals("state")) {
                        city = null;
                        et_personal_information_city.setText("");
                        et_personal_information_pincode.setText("");
                        prefUtility.setStateCity("city", "", "city_name", "");
                        prefUtility.updateUserData("postal_code", "");
                        prefUtility.updateUserData("state", state.getStateId());
                        prefUtility.updateUserData("state_name", updateUI[2]);
                    }
                    if (editText.getTag().toString().equals("city")) {
                        et_personal_information_pincode.setText("");
                        prefUtility.updateUserData("postal_code", "");
                        prefUtility.updateUserData("city", city.getCityId());
                        prefUtility.updateUserData("city_name", updateUI[2]);
                    }
                    showToast("Updated");
                } else {
                    editText.setText(updateUI[1]);
                    showToast(bean.getResponsedata().getErrorMessage());
                }
                string = "";
            }
            if (requestCode == UPDATE_PROFILE_SWITCH_ON) {
                UpdateProfileBean bean = gson.fromJson((String) responseModel, UpdateProfileBean.class);
                if (bean.getResponseCode() == 1) {
                    prefUtility.updateUserData("notification", "enable");
                    showToast("Updated");
                } else {
                    showToast(bean.getResponsedata().getErrorMessage());
                }
            }
            if (requestCode == UPDATE_PROFILE_SWITCH_OFF) {
                UpdateProfileBean bean = gson.fromJson((String) responseModel, UpdateProfileBean.class);
                if (bean.getResponseCode() == 1) {
                    prefUtility.updateUserData("notification", "disable");
                    showToast("Updated");
                } else {
                    showToast(bean.getResponsedata().getErrorMessage());
                }
            }
            if (requestCode == GET_TABLE_DATA_STATE) {
                StateBean bean = gson.fromJson((String) responseModel, StateBean.class);
                state_responsedata = bean.getResponsedata();
                if (state_responsedata.size() > 1) {
                    callState();
                } else {
                    showToast("No state found");
                }
            }
            if (requestCode == GET_TABLE_DATA_CITY) {
                CityBean bean = gson.fromJson((String) responseModel, CityBean.class);
                city_responsedata = bean.getResponsedata();
                if (city_responsedata.size() > 1) {
                    callCity();
                } else {
                    showToast("No city found");
                }
            }
            hideProgress();
        }catch (JsonSyntaxException e){
            log(TAG,  e.getMessage());
        }
    }

    public void receiveCountry(Responsedatum responsedatum) {
        et_personal_information_country.setTag("country");
        string = string + "#" + responsedatum.getName();
        et_personal_information_country.setText(country.getCountryId());
        et_personal_information_country.setTextColor(Color.WHITE);
        showDialog("Updating", true);
        sendUpdateRequest(et_personal_information_country, UPDATE_PROFILE_EDIT, 1);
    }

    @Override
    public void onErrorListener(int requestCode, String error) {

    }

    public boolean checkField(EditText editText, int type) {
        if (type == 0) {
            if (editText.getText().toString().equals("")) {
                showToast("Field can not be empty");
                return false;
            }
        }
        return true;
    }

    /**
     * send update request to server
     *
     * @param editText
     */
    public void sendUpdateRequest(EditText editText, int responsecode, int type) {
        if (AppConstants.isOnline(Personal_Information.this)) {
            if (checkField(editText, type)) {
                showDialog("Updating", true);
                RequestParams params = new RequestParams();
                params.put("user_id", responsedata.getId());
                params.put("field_name", editText.getTag().toString());
                params.put("field_value", editText.getText().toString());
                postService(NetworkAPI.UPDATE_PROFILE, params, responsecode);
            }
        } else {
            showToast(getResources().getString(R.string.no_internet_connection));
        }
    }

    String string = "";

    /**
     * change button state on click
     *
     * @param state
     * @param textView
     * @param editText
     * @return
     */
    public boolean stateChange(boolean state, TextView textView, EditText editText) {
        if ((Boolean) textView.getTag()) {
            disableOtherEditText(string);
            string = editText.getTag().toString() + "#" + editText.getText().toString();
            textView.setText("Save");
            editText.setEnabled(true);
            editText.requestFocus();
            editText.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            editTextArrayList.add(editText);
            textView.setTag(false);
        } else {
            string = string + "#" + editText.getText().toString();

            sendUpdateRequest(editText, UPDATE_PROFILE_EDIT, 0);
            editText.setEnabled(false);
            editText.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
            textView.setText("Change");
            textView.setTag(true);
        }
        return state;
    }

    private void disableOtherEditText(String string) {
        for (EditText editText : editTextArrayList) {
            editText.setEnabled(false);
            if(!editText.getText().toString().equals("")){
                if (!string.equals("")) {
                    editText.setText(string.split("#")[1]);
                }
            }
            editText.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
            editTextTextViewHashMap.get(editText).setText("Change");
            editTextTextViewHashMap.get(editText).setTag(true);
        }
        editTextArrayList.clear();
    }

    /*public EditText getEditTextToUpdate(String tag) {
        for (EditText editText : editTextArrayList) {
            if (editText.getTag().toString().equals(tag)) {
                return editText;
            }
        }
        return null;
    }*/

    public void sendUpdateStateRequest() {
        if (country != null) {
            showDialog("Update state",false);
            if (AppConstants.isOnline(Personal_Information.this)) {
                RequestParams params = new RequestParams();
                params.put("tablename", "state");
                params.put("field", "country_id");
                params.put("value", country.getCountryId());
                postService(NetworkAPI.GET_TABLE_DATA, params, GET_TABLE_DATA_STATE);
            } else {
                showToast(getResources().getString(R.string.no_internet_connection));
            }
        } else {
            showToast("Please update country first");
        }

    }

    public void callState() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(android.R.layout.list_content);

        ListView list = (ListView) dialog.findViewById(android.R.id.list);
        ArrayList<String> state_names = new ArrayList<>();
        for (com.fan.core.model.state.Responsedatum responsedatum : state_responsedata) {
            state_names.add(responsedatum.getName());
        }
        list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, state_names));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                state = state_responsedata.get(position);
                string = string + "#" + state.getName();
                et_personal_information_state.setText(state.getStateId());
                et_personal_information_state.setTextColor(Color.WHITE);
                showDialog("Updating", true);
                sendUpdateRequest(et_personal_information_state, UPDATE_PROFILE_EDIT, 1);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void sendUpdateCityRequest() {
        if (state != null) {
            showDialog("Update city",false);
            if (AppConstants.isOnline(Personal_Information.this)) {
                RequestParams params = new RequestParams();
                params.put("tablename", "city");
                params.put("field", "state_id");
                params.put("value", state.getStateId());
                postService(NetworkAPI.GET_TABLE_DATA, params, GET_TABLE_DATA_CITY);
            } else {
                showToast(getResources().getString(R.string.no_internet_connection));
            }
        } else {
            showToast("Please update state first");
        }

    }

    public void callCity() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(android.R.layout.list_content);

        ListView list = (ListView) dialog.findViewById(android.R.id.list);
        ArrayList<String> city_names = new ArrayList<>();
        for (com.fan.core.model.city.Responsedatum responsedatum : city_responsedata) {
            city_names.add(responsedatum.getName());
        }
        list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, city_names));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                city = city_responsedata.get(position);
                string = string + "#" + city.getName();
                et_personal_information_city.setText(city.getCityId());
                et_personal_information_city.setTextColor(Color.WHITE);
                showDialog("Updating", true);
                sendUpdateRequest(et_personal_information_city, UPDATE_PROFILE_EDIT, 1);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
