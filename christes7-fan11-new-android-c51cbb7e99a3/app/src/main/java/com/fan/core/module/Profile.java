package com.fan.core.module;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.fan.core.R;
import com.fan.core.helper.UpdateBitmap;
import com.fan.core.model.country.Responsedatum;
import com.fan.core.model.image_upload.ImageUploadBean;
import com.fan.core.model.user.Responsedata;
import com.fan.core.module_fragment.MainFragmentActivity;
import com.fan.core.util.AppConstants;
import com.fan.core.util.CircleImageView;
import com.fan.core.util.NetworkAPI;
import com.fan.core.util.SharedPrefUtility;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.fan.core.util.AppConstants.UPLOAD_PROFILE_CONTENT;
import static com.fan.core.util.AppConstants.callLanguage;
import static com.fan.core.util.AppConstants.city;
import static com.fan.core.util.AppConstants.clearPref;
import static com.fan.core.util.AppConstants.country;
import static com.fan.core.util.AppConstants.getStringImage;
import static com.fan.core.util.AppConstants.getUserSaved;
import static com.fan.core.util.AppConstants.profile_pic;
import static com.fan.core.util.AppConstants.scaleBitmap;
import static com.fan.core.util.AppConstants.startTutorial;
import static com.fan.core.util.AppConstants.state;
import static com.fan.core.util.AppConstants.upload_id;
import static com.fan.core.util.AppConstants.upload_signature;

/**
 * Created by mohit.soni @ 05-Oct-19.
 */
public class Profile extends BaseActivity implements UpdateBitmap {
    public static final String TAG = Profile.class.getSimpleName();

    RelativeLayout rl_profile_2;
    TextView tv_profile_personal_information,
            tv_profile_full_address, tv_profile_referral_codes, tv_profile_privacy_policy,
            tv_profile_terms_and_conditions, tv_toolbar_head, tv_profile_logout, tv_profile_language,
            tv_toolbar_language;
    EditText et_profile_person_name;

    ImageView iv_profile_name_edit, iv_toolbar_back, iv_toolbar_wallet_icon, iv_toolbar_icon_tutorial;
    CircleImageView iv_profile_person_default, iv_toolbar_profile_image;

    Responsedata responsedata;

    public static int IMAGE_PICK = 1002;
    /*public static int UPDATE_PROFILE_EDIT = 1001;*/
    public String img_text = "";
    public File profile_local_path;
    public static boolean init = true;
    Resources resources;

    /*ArrayList<EditText> editTextArrayList = new ArrayList<>();*/
    LinearLayout ll_toolbar_language_change;
    TextView tv_toolbar_language_amheric, tv_toolbar_language_english;
    SharedPrefUtility prefUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.profile);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        }
        resources = getResources();
        rl_profile_2 = (RelativeLayout) findViewById(R.id.rl_profile_2);

        tv_profile_personal_information = (TextView) findViewById(R.id.tv_profile_personal_information);
        /*tv_profile_notification = (TextView) findViewById(R.id.tv_profile_notification);*/
        tv_profile_full_address = (TextView) findViewById(R.id.tv_profile_full_address);
        tv_profile_referral_codes = (TextView) findViewById(R.id.tv_profile_referral_codes);
        tv_profile_privacy_policy = (TextView) findViewById(R.id.tv_profile_privacy_policy);
        tv_profile_terms_and_conditions = (TextView) findViewById(R.id.tv_profile_terms_and_conditions);
        tv_profile_logout = (TextView) findViewById(R.id.tv_profile_logout);
        tv_profile_language = (TextView) findViewById(R.id.tv_profile_language);
        tv_toolbar_language = (TextView) findViewById(R.id.tv_toolbar_language);

        et_profile_person_name = (EditText) findViewById(R.id.et_profile_person_name);

        iv_toolbar_back = (ImageView) findViewById(R.id.iv_toolbar_back);
        tv_toolbar_head = (TextView) findViewById(R.id.tv_toolbar_head);
        iv_toolbar_profile_image = (CircleImageView) findViewById(R.id.iv_toolbar_profile_image);
        iv_toolbar_wallet_icon = (ImageView) findViewById(R.id.iv_toolbar_wallet_icon);
        iv_toolbar_icon_tutorial = (ImageView) findViewById(R.id.iv_toolbar_icon_tutorial);

        iv_profile_name_edit = (ImageView) findViewById(R.id.iv_profile_name_edit);
        iv_profile_person_default = (CircleImageView) findViewById(R.id.iv_profile_person_default);

        tv_toolbar_language_amheric = (TextView) findViewById(R.id.tv_toolbar_language_amheric);
        tv_toolbar_language_english = (TextView) findViewById(R.id.tv_toolbar_language_english);
        ll_toolbar_language_change = (LinearLayout) findViewById(R.id.ll_toolbar_language_change);

        iv_toolbar_wallet_icon.setVisibility(View.VISIBLE);
        iv_toolbar_icon_tutorial.setVisibility(View.VISIBLE);
        tv_toolbar_head.setText(getResources().getString(R.string.profile));
        if (profile_pic != null) {
            iv_toolbar_profile_image.setImageBitmap(profile_pic);
            iv_profile_person_default.setImageBitmap(profile_pic);
        }

        iv_toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_profile_personal_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Personal_Information.class));
                finish();
            }
        });
        tv_profile_full_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Full_Address.class));
                finish();
            }
        });
        tv_profile_terms_and_conditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Terms.class));
                finish();
            }
        });
        tv_profile_referral_codes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, ReferralActivity.class));
                finish();
            }
        });
        iv_toolbar_wallet_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, MyBalanceActivity.class));
            }
        });
        iv_toolbar_icon_tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTutorial(Profile.this);
            }
        });
        tv_profile_privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, PrivacyPolicyActivity.class));
                finish();
            }
        });
        try {
            responsedata = getUserSaved(this);
        } catch (NullPointerException e) {
        }
        if (!responsedata.getCountry_name().equals("")) {
            Responsedatum country_ = new Responsedatum();
            country_.setCountryId(responsedata.getCountry());
            country_.setErrorMessage("");
            country_.setName(responsedata.getCountry_name());
            country_.setPhonecode(responsedata.getPostalCode());
            country_.setSortname("");
            country = country_;
        }
        if (!responsedata.getState_name().equals("")) {
            com.fan.core.model.state.Responsedatum state_ = new com.fan.core.model.state.Responsedatum();
            state_.setCountryId(responsedata.getCountry());
            state_.setErrorMessage("");
            state_.setName(responsedata.getState_name());
            state_.setStateId(responsedata.getState());
            state = state_;
        }
        if (!responsedata.getCity_name().equals("")) {
            com.fan.core.model.city.Responsedatum city_ = new com.fan.core.model.city.Responsedatum();
            city_.setCityId(responsedata.getCity());
            city_.setErrorMessage("");
            city_.setName(responsedata.getCity_name());
            city_.setStateId(responsedata.getState());
            city = city_;
        }
        if (init) {
            /*callCountryDataUpdate(responsedata);*/
            new GetUserData(this, responsedata.getProfileImage(), iv_toolbar_profile_image, true, this);
            init = false;
        }
        et_profile_person_name.setText(responsedata.getFirstName() + " " + responsedata.getLastName());

        iv_profile_name_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*callNameChange();*/
                startActivity(new Intent(Profile.this, Personal_Information.class));
            }
        });
        rl_profile_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int have = ContextCompat.checkSelfPermission(Profile.this, Manifest.permission.CAMERA);
                if (have == 0) {
                    openImageIntent(IMAGE_PICK);
                }
            }
        });

//        if (responsedata.getProfileImage().isEmpty()) {
//            if (responsedata.getGender().equals("female")) {
//                iv_profile_person_default.setBackground(getResources().getDrawable(R.mipmap.female_icon));
//            } else {
//                iv_profile_person_default.setBackground(getResources().getDrawable(R.mipmap.male_icon));
//            }
//
//        }

        tv_profile_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init = true;
                profile_pic = null;
                upload_id = null;
                upload_signature = null;
                LoginManager.getInstance().logOut();
                clearPref(Profile.this);
                startActivity(new Intent(Profile.this, Login.class));
                finish();
            }
        });

        tv_toolbar_language_amheric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prefUtility != null) {
                    prefUtility.setLanguage("ar");
                }
                ll_toolbar_language_change.setBackground(resources.getDrawable(R.drawable.round_button_toolbar_language_amheric));
                tv_toolbar_language_amheric.setTextColor(resources.getColor(R.color.black));
                tv_toolbar_language_english.setTextColor(resources.getColor(R.color.grey));
                callLanguage(Profile.this, Profile.this, Profile.class, resources, 1);
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
                callLanguage(Profile.this, Profile.this, Profile.class, resources, 0);
            }
        });
        /*tv_toolbar_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callLanguage(MainFragmentActivity.this,MainFragmentActivity.this,MainFragmentActivity.class,resources);
            }
        });*/
        /*tv_toolbar_language.setText(CURRENT_LANGUAGE);*/
        tv_profile_language.setVisibility(View.GONE);
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

    /*public void setProfilePic(){
        if (profile_pic != null) {
            iv_toolbar_profile_image.setImageBitmap(profile_pic);
        }
    }*/
    @Override
    protected void onResume() {
        super.onResume();
        try {
            responsedata = getUserSaved(this);
        } catch (NullPointerException e) {
        }
        et_profile_person_name.setText(responsedata.getFirstName() + " " + responsedata.getLastName());
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Profile.this, MainFragmentActivity.class));
        finish();
    }

    @Override
    public void onResponseListener(int requestCode, Object responseModel) {
        try {
            log(TAG, responseModel.toString());
            Gson gson = new Gson();
            if (requestCode == UPLOAD_PROFILE_CONTENT) {
                ImageUploadBean bean = gson.fromJson((String) responseModel, ImageUploadBean.class);
                if (bean.getResponseCode() == 1) {
                    String path = "file://" + profile_local_path.getAbsolutePath().toString();
                    prefUtility.updateUserData("profile_image", bean.getResponsedata().getUploadUrl());
                    prefUtility.updateUserData("profile_image_local", path);
                    new GetUserData(Profile.this, bean.getResponsedata().getUploadUrl(), iv_profile_person_default, true, this);
                    showToast("Updated");
                } else {
                    showToast(bean.getResponsedata().getErrorMessage());
                }
            }
        /*if (requestCode == UPDATE_PROFILE_EDIT) {
            String[] updateUI = string.split("#");
            EditText editText = getEditTextToUpdate(updateUI[0]);
            UpdateProfileBean bean = gson.fromJson((String) responseModel, UpdateProfileBean.class);
            if (bean.getResponseCode() == 1) {
                editText.setText(updateUI[2]);
                prefUtility.updateUserData(updateUI[0], updateUI[2]);
                showToast("Updated");
                try {
                    responsedata = getUserSaved(this);
                } catch (NullPointerException e) {
                }
                et_profile_person_name.setText(responsedata.getFirstName() + " " + responsedata.getLastName());
            } else {
                editText.setText(updateUI[1]);
                showToast(bean.getResponsedata().getErrorMessage());
            }
            string = "";
        }*/
        } catch (JsonSyntaxException e) {
            log(TAG, e.getMessage());
        }
        hideProgress();

    }

    @Override
    public void onErrorListener(int requestCode, String error) {
        showToast(requestCode + " : " + error);
    }

    /**
     * send update request to server
     */
    public void sendUpdateRequest() {
        showDialog("Uploading Image", true);
        if (AppConstants.isOnline(Profile.this)) {
            RequestParams params = new RequestParams();
            params.put("user_id", responsedata.getId());
            params.put("type", "profile_image");
            params.put("encoded_image", img_text);
            postService(NetworkAPI.UPLOAD_PROFILE_CONTENT, params, UPLOAD_PROFILE_CONTENT);
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
    private void openImageIntent(int reqCode) {
        final File root = new File(Environment.getExternalStorageDirectory() + File.separator +
                "Fan11" + File.separator);
        // Determine Uri of camera image to save.

        root.mkdirs();
        final String fname = "profile.jpg";
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
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK) {
            final boolean isCamera;
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
            Uri selectedImageUri;
            if (isCamera) {
                selectedImageUri = outputFileUri;
            } else {
                selectedImageUri = data == null ? null : data.getData();
            }
            if (selectedImageUri != null) {
                Bitmap bitmap = scaleBitmap(selectedImageUri, Profile.this);
                if (bitmap != null) {
                    img_text = getStringImage(bitmap);
                    sendUpdateRequest();
                }
            }
        }
    }

    /*public void callNameChange() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.name_change_dialog);

        final EditText et_name_change_first_name = (EditText) dialog.findViewById(R.id.et_name_change_first_name);
        final EditText et_name_change_last_name = (EditText) dialog.findViewById(R.id.et_name_change_last_name);

        final TextView tv_name_change_first_name_change = (TextView) dialog.findViewById(R.id.tv_name_change_first_name_change);
        final TextView tv_name_change_last_name_change = (TextView) dialog.findViewById(R.id.tv_name_change_last_name_change);

        et_name_change_first_name.setTag("first_name");
        et_name_change_last_name.setTag("last_name");

        editTextArrayList.add(et_name_change_first_name);
        editTextArrayList.add(et_name_change_last_name);

        et_name_change_first_name.setText(responsedata.getFirstName());
        et_name_change_last_name.setText(responsedata.getLastName());

        tv_name_change_first_name_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppConstants.isOnline(Profile.this)) {
                    first_name_change = stateChange(first_name_change, tv_name_change_first_name_change, et_name_change_first_name);
                } else {
                    showToast(getResources().getString(R.string.no_internet_connection));
                }
            }
        });
        tv_name_change_last_name_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppConstants.isOnline(Profile.this)) {
                    last_name_change = stateChange(last_name_change, tv_name_change_last_name_change, et_name_change_last_name);
                } else {
                    showToast(getResources().getString(R.string.no_internet_connection));
                }
            }
        });
        dialog.show();
    }*/

   /* String string = "";
    boolean first_name_change = true;
    boolean last_name_change = true;*/

    @Override
    public void setBitmap(Bitmap bitmap) {
        profile_pic = bitmap;
        iv_toolbar_profile_image.setImageBitmap(profile_pic);
        iv_profile_person_default.setImageBitmap(profile_pic);
    }
}
