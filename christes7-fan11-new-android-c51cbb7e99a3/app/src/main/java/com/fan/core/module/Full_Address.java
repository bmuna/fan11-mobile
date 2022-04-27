package com.fan.core.module;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Base64;
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
import com.fan.core.helper.UpdateBitmap;
import com.fan.core.model.city.CityBean;
import com.fan.core.model.country.Responsedatum;
import com.fan.core.model.image_upload.ImageUploadBean;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.fan.core.util.AppConstants.GET_TABLE_DATA_CITY;
import static com.fan.core.util.AppConstants.GET_TABLE_DATA_STATE;
import static com.fan.core.util.AppConstants.callCountry;
import static com.fan.core.util.AppConstants.callLanguage;
import static com.fan.core.util.AppConstants.city;
import static com.fan.core.util.AppConstants.city_responsedata;
import static com.fan.core.util.AppConstants.country;
import static com.fan.core.util.AppConstants.getUserSaved;
import static com.fan.core.util.AppConstants.profile_pic;
import static com.fan.core.util.AppConstants.scaleBitmap;
import static com.fan.core.util.AppConstants.startTutorial;
import static com.fan.core.util.AppConstants.state;
import static com.fan.core.util.AppConstants.state_responsedata;

/**
 * Created by mohit.soni @ 07-Oct-19.
 */
public class Full_Address extends BaseActivity implements UpdateBitmap {
    public static final String TAG = Full_Address.class.getSimpleName();

    TextView tv_toolbar_head, tv_full_address_address_change, tv_full_address_city_change,
            tv_full_address_pincode_change, tv_full_address_state_change,
            tv_full_address_country_change,
            tv_full_address_upload_id, tv_full_address_upload_signature,
            tv_toolbar_language;
    EditText et_full_address_address, et_full_address_city, et_full_address_country,
            et_full_address_pincode, et_full_address_state;
    RelativeLayout rl_full_address_upload_id, rl_full_address_upload_signature;

    ImageView iv_full_address_upload_id, iv_full_address_upload_signature, iv_toolbar_back;
    ImageView iv_toolbar_wallet_icon, iv_toolbar_icon_tutorial;
    CircleImageView iv_toolbar_profile_image;
    boolean add_change = true;
    boolean pin_change = true;

    Responsedata responsedata;
    ArrayList<EditText> editTextArrayList = new ArrayList<>();
    HashMap<EditText, TextView> editTextTextViewHashMap = new HashMap<>();
    Resources resources;

    public static int UPDATE_PROFILE_EDIT = 1001;
    public static int UPDATE_PROFILE_ID = 1002;
    public static int UPDATE_PROFILE_SIGNATURE = 1003;
    public static int IMAGE_PICK_ID = 1004;
    public static int IMAGE_PICK_SIGNATURE = 1005;

    public String img_text = "";
    public File profile_local_path;

    LinearLayout ll_toolbar_language_change;
    TextView tv_toolbar_language_amheric, tv_toolbar_language_english;
    SharedPrefUtility prefUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_address);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        }
        resources = getResources();
        tv_full_address_address_change = (TextView) findViewById(R.id.tv_full_address_address_change);
        tv_full_address_country_change = (TextView) findViewById(R.id.tv_full_address_country_change);
        tv_full_address_state_change = (TextView) findViewById(R.id.tv_full_address_state_change);
        tv_full_address_city_change = (TextView) findViewById(R.id.tv_full_address_city_change);
        tv_full_address_pincode_change = (TextView) findViewById(R.id.tv_full_address_pincode_change);
        tv_full_address_upload_id = (TextView) findViewById(R.id.tv_full_address_upload_id);
        tv_full_address_upload_signature = (TextView) findViewById(R.id.tv_full_address_upload_signature);
        tv_toolbar_language = (TextView) findViewById(R.id.tv_toolbar_language);

        iv_toolbar_back = (ImageView) findViewById(R.id.iv_toolbar_back);
        tv_toolbar_head = (TextView) findViewById(R.id.tv_toolbar_head);
        iv_toolbar_wallet_icon = (ImageView) findViewById(R.id.iv_toolbar_wallet_icon);
        iv_toolbar_icon_tutorial = (ImageView) findViewById(R.id.iv_toolbar_icon_tutorial);
        iv_toolbar_profile_image = (CircleImageView) findViewById(R.id.iv_toolbar_profile_image);

        iv_full_address_upload_id = (ImageView) findViewById(R.id.iv_full_address_upload_id);
        iv_full_address_upload_signature = (ImageView) findViewById(R.id.iv_full_address_upload_signature);

        rl_full_address_upload_id = (RelativeLayout) findViewById(R.id.rl_full_address_upload_id);
        rl_full_address_upload_signature = (RelativeLayout) findViewById(R.id.rl_full_address_upload_signature);

        et_full_address_address = (EditText) findViewById(R.id.et_full_address_address);
        et_full_address_country = (EditText) findViewById(R.id.et_full_address_country);
        et_full_address_state = (EditText) findViewById(R.id.et_full_address_state);
        et_full_address_city = (EditText) findViewById(R.id.et_full_address_city);
        et_full_address_pincode = (EditText) findViewById(R.id.et_full_address_pincode);

        tv_toolbar_language_amheric = (TextView) findViewById(R.id.tv_toolbar_language_amheric);
        tv_toolbar_language_english = (TextView) findViewById(R.id.tv_toolbar_language_english);
        ll_toolbar_language_change = (LinearLayout) findViewById(R.id.ll_toolbar_language_change);

        tv_toolbar_head.setText(getResources().getString(R.string.full_address));
        iv_toolbar_icon_tutorial.setVisibility(View.VISIBLE);
        iv_toolbar_wallet_icon.setVisibility(View.VISIBLE);
        if (profile_pic != null) {
            iv_toolbar_profile_image.setImageBitmap(profile_pic);
        }
        try {
            responsedata = getUserSaved(this);
        } catch (NullPointerException e) {
        }
        et_full_address_address.setTag("address");
        et_full_address_country.setTag("country");
        et_full_address_state.setTag("state");
        et_full_address_city.setTag("city");
        et_full_address_pincode.setTag("postal_code");

        /*editTextArrayList.add(et_full_address_address);
        editTextArrayList.add(et_full_address_city);
        editTextArrayList.add(et_full_address_pincode);
        editTextArrayList.add(et_full_address_state);
        editTextArrayList.add(et_full_address_country);*/

        tv_full_address_address_change.setTag(true);
        tv_full_address_country_change.setTag(true);
        tv_full_address_state_change.setTag(true);
        tv_full_address_city_change.setTag(true);
        tv_full_address_pincode_change.setTag(true);

        editTextTextViewHashMap.put(et_full_address_address, tv_full_address_address_change);
        editTextTextViewHashMap.put(et_full_address_country, tv_full_address_country_change);
        editTextTextViewHashMap.put(et_full_address_state, tv_full_address_state_change);
        editTextTextViewHashMap.put(et_full_address_city, tv_full_address_city_change);
        editTextTextViewHashMap.put(et_full_address_pincode, tv_full_address_pincode_change);

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

        iv_toolbar_wallet_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Full_Address.this, MyBalanceActivity.class));
            }
        });
        iv_toolbar_icon_tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTutorial(Full_Address.this);
            }
        });
        tv_full_address_address_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppConstants.isOnline(Full_Address.this)) {
                    add_change = stateChange(add_change, tv_full_address_address_change, et_full_address_address);
                } else {
                    showToast(getResources().getString(R.string.no_internet_connection));
                }
            }
        });
        tv_full_address_city_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppConstants.isOnline(Full_Address.this)) {
                    sendUpdateCityRequest();
                    editTextArrayList.clear();
                    editTextArrayList.add(et_full_address_city);
                    string = et_full_address_city.getTag().toString() + "#" + et_full_address_city.getText().toString();
                } else {
                    showToast(getResources().getString(R.string.no_internet_connection));
                }
            }
        });
        tv_full_address_pincode_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppConstants.isOnline(Full_Address.this)) {
                    pin_change = stateChange(pin_change, tv_full_address_pincode_change, et_full_address_pincode);
                } else {
                    showToast(getResources().getString(R.string.no_internet_connection));
                }
            }
        });
        tv_full_address_country_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppConstants.isOnline(Full_Address.this)) {
                    editTextArrayList.clear();
                    editTextArrayList.add(et_full_address_country);
                    string = et_full_address_country.getTag().toString() + "#" + et_full_address_country.getText().toString();
                    callCountry(et_full_address_country, Full_Address.this, Full_Address.this);
                } else {
                    showToast(getResources().getString(R.string.no_internet_connection));
                }
            }
        });
        tv_full_address_state_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppConstants.isOnline(Full_Address.this)) {
                    sendUpdateStateRequest();
                    editTextArrayList.clear();
                    editTextArrayList.add(et_full_address_state);
                    string = et_full_address_state.getTag().toString() + "#" + et_full_address_state.getText().toString();
                } else {
                    showToast(getResources().getString(R.string.no_internet_connection));
                }
            }
        });

        rl_full_address_upload_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageIntent(IMAGE_PICK_ID, "upload_id");
            }
        });
        rl_full_address_upload_signature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageIntent(IMAGE_PICK_SIGNATURE, "upload_signature");
            }
        });


        et_full_address_address.setText(responsedata.getAddress());
        et_full_address_city.setText(city != null ? city.getName() : "");
        et_full_address_pincode.setText(responsedata.getPostalCode());
        et_full_address_country.setText(country != null ? country.getName() : "");
        et_full_address_state.setText(state != null ? state.getName() : "");


        if (responsedata.getUploadId().isEmpty()) {
            tv_full_address_upload_id.setVisibility(View.VISIBLE);
        } else {
            tv_full_address_upload_id.setVisibility(View.GONE);
            new GetUserData(Full_Address.this, responsedata.getUploadId(), iv_full_address_upload_id, false, this);
        }

        if (responsedata.getUploadSignature().isEmpty()) {
            tv_full_address_upload_signature.setVisibility(View.VISIBLE);
        } else {
            tv_full_address_upload_signature.setVisibility(View.GONE);
            new GetUserData(Full_Address.this, responsedata.getUploadSignature(), iv_full_address_upload_signature, false, this);
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
                callLanguage(Full_Address.this, Full_Address.this, Full_Address.class, resources, 1);
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
                callLanguage(Full_Address.this, Full_Address.this, Full_Address.class, resources, 0);
            }
        });
        /*tv_toolbar_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callLanguage(MainFragmentActivity.this,MainFragmentActivity.this,MainFragmentActivity.class,resources);
            }
        });*/
        /*tv_toolbar_language.setText(CURRENT_LANGUAGE);*/
        prefUtility = new SharedPrefUtility(this);
        if (prefUtility != null) {
            String lng = prefUtility.getLanguage();
            if (lng.equals("en")) {
                ll_toolbar_language_change.setBackground(resources.getDrawable(R.drawable.round_button_toolbar_language_english));
                tv_toolbar_language_english.setTextColor(resources.getColor(R.color.black));
                tv_toolbar_language_amheric.setTextColor(resources.getColor(R.color.grey));
            } else {
                ll_toolbar_language_change.setBackground(resources.getDrawable(R.drawable.round_button_toolbar_language_amheric));
                tv_toolbar_language_amheric.setTextColor(resources.getColor(R.color.black));
                tv_toolbar_language_english.setTextColor(resources.getColor(R.color.grey));
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Full_Address.this, Profile.class));
        finish();
    }

    @Override
    public void onResponseListener(int requestCode, Object responseModel) {
        try {
            log(TAG, responseModel.toString());
            Gson gson = new Gson();
            if (requestCode == UPDATE_PROFILE_EDIT) {
                String[] updateUI = string.split("#");
                EditText editText = getEditTextToUpdate(updateUI[0]);
                UpdateProfileBean bean = gson.fromJson((String) responseModel, UpdateProfileBean.class);
                if (bean.getResponseCode() == 1) {
                    editText.setText(updateUI[2]);
                    editText.setTextColor(Color.BLACK);
                    if (editText.getTag().toString().equals("country")) {
                        state = null;
                        city = null;
                        et_full_address_state.setText("");
                        et_full_address_city.setText("");
                        et_full_address_pincode.setText("");
                        prefUtility.setStateCity("state", "", "state_name", "");
                        prefUtility.setStateCity("city", "", "city_name", "");
                        prefUtility.updateUserData("postal_code", "");
                        prefUtility.updateUserData("country", country.getCountryId());
                        prefUtility.updateUserData("country_name", updateUI[2]);
                    }
                    if (editText.getTag().toString().equals("state")) {
                        city = null;
                        et_full_address_city.setText("");
                        et_full_address_pincode.setText("");
                        prefUtility.setStateCity("city", "", "city_name", "");
                        prefUtility.updateUserData("postal_code", "");
                        prefUtility.updateUserData("state", state.getStateId());
                        prefUtility.updateUserData("state_name", updateUI[2]);
                    }
                    if (editText.getTag().toString().equals("city")) {
                        et_full_address_pincode.setText("");
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
            if (requestCode == GET_TABLE_DATA_STATE) {
                StateBean bean = gson.fromJson((String) responseModel, StateBean.class);
                state_responsedata = bean.getResponsedata();
                callState();
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
            if (requestCode == UPDATE_PROFILE_ID) {
                ImageUploadBean bean = gson.fromJson((String) responseModel, ImageUploadBean.class);
                if (bean.getResponseCode() == 1) {
                    String path = "file://" + profile_local_path.getAbsolutePath().toString();
                    prefUtility.updateUserData("upload_id", bean.getResponsedata().getUploadUrl());
                    prefUtility.updateUserData("upload_id_local", path);
                    tv_full_address_upload_id.setVisibility(View.GONE);
                    new GetUserData(Full_Address.this, bean.getResponsedata().getUploadUrl(), iv_full_address_upload_id, false, this);
                    showToast("Id Updated");
                } else {
                    showToast(bean.getResponsedata().getErrorMessage());
                }
            }
            if (requestCode == UPDATE_PROFILE_SIGNATURE) {
                ImageUploadBean bean = gson.fromJson((String) responseModel, ImageUploadBean.class);
                if (bean.getResponseCode() == 1) {
                    String path = "file://" + profile_local_path.getAbsolutePath().toString();
                    prefUtility.updateUserData("upload_signature", bean.getResponsedata().getUploadUrl());
                    prefUtility.updateUserData("upload_signature_local", path);
                    tv_full_address_upload_signature.setVisibility(View.GONE);
                    new GetUserData(Full_Address.this, bean.getResponsedata().getUploadUrl(), iv_full_address_upload_signature, false, this);
                    showToast("Signature Updated");
                } else {
                    showToast(bean.getResponsedata().getErrorMessage());
                }
            }
        } catch (JsonSyntaxException e) {
            log(TAG, e.getMessage());
        }
        hideProgress();
    }

    public void receiveCountry(Responsedatum responsedatum) {
        et_full_address_country.setTag("country");
        string = string + "#" + responsedatum.getName();
        et_full_address_country.setText(country.getCountryId());
        et_full_address_country.setTextColor(Color.WHITE);
        sendUpdateRequest(et_full_address_country, UPDATE_PROFILE_EDIT, 1);
    }

    @Override
    public void onErrorListener(int requestCode, String error) {
        showToast(requestCode + " : " + error);
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
        if (AppConstants.isOnline(Full_Address.this)) {
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
            if (!editText.getText().toString().equals("")) {
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

    /**
     * get particular edit text to update
     *
     * @param tag
     * @return
     */
    public EditText getEditTextToUpdate(String tag) {
        for (EditText editText : editTextArrayList) {
            if (editText.getTag().toString().equals(tag)) {
                return editText;
            }
        }
        return null;
    }

    public void sendUpdateStateRequest() {
        if (country != null) {
            showDialog("Update state", false);
            if (AppConstants.isOnline(Full_Address.this)) {
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
                et_full_address_state.setText(state.getStateId());
                et_full_address_state.setTextColor(Color.WHITE);
                sendUpdateRequest(et_full_address_state, UPDATE_PROFILE_EDIT, 1);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void sendUpdateCityRequest() {
        if (state != null) {
            showDialog("Update city", false);
            if (AppConstants.isOnline(Full_Address.this)) {
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
                et_full_address_city.setText(city.getCityId());
                et_full_address_city.setTextColor(Color.WHITE);
                sendUpdateRequest(et_full_address_city, UPDATE_PROFILE_EDIT, 1);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * send update request to server
     */
    public void sendUpdateRequest(String img_type, int respondecode) {
        showDialog("Uploading Image", false);
        if (AppConstants.isOnline(Full_Address.this)) {
            RequestParams params = new RequestParams();
            params.put("user_id", responsedata.getId());
            params.put("type", img_type);
            params.put("encoded_image", img_text);
            postService(NetworkAPI.UPLOAD_PROFILE_CONTENT, params, respondecode);
        } else {
            showToast(getResources().getString(R.string.no_internet_connection));
        }
    }

    private Uri outputFileUri;

    /**
     * open camera
     *
     * @param reqCode request code
     */
    private void openImageIntent(int reqCode, String img_name) {
        final File root = new File(Environment.getExternalStorageDirectory() + File.separator +
                "Fan11" + File.separator);
        root.mkdirs();
        final String fname = img_name + ".jpg";
        profile_local_path = new File(root, fname);
        outputFileUri = Uri.fromFile(profile_local_path);

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

        startActivityForResult(chooserIntent, reqCode);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri selectedImageUri;
        final boolean isCamera;
        Bitmap bitmap = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                isCamera = true;
            } else {
                final String action = data.getAction();
                if (action == null) {
                    isCamera = false;
                } else {
                    isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                }
            }
            if (isCamera) {
                selectedImageUri = outputFileUri;
            } else {
                selectedImageUri = data == null ? null : data.getData();
            }
            if (selectedImageUri != null) {
                bitmap = scaleBitmap(selectedImageUri, Full_Address.this);
            }
        }
        if (requestCode == IMAGE_PICK_ID) {
            if (bitmap != null) {
                img_text = getStringImage(bitmap);
                sendUpdateRequest("upload_id", UPDATE_PROFILE_ID);
            }
        }
        if (requestCode == IMAGE_PICK_SIGNATURE) {
            if (bitmap != null) {
                img_text = getStringImage(bitmap);
                sendUpdateRequest("upload_signature", UPDATE_PROFILE_SIGNATURE);
            }
        }
    }

    /**
     * get image as string from byte
     *
     * @param bmp bitmap
     * @return base 64 of image
     */
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    @Override
    public void setBitmap(Bitmap bitmap) {

    }
}
