package com.fan.core.module_fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.fan.core.R;
import com.fan.core.helper.UpdateBitmap;
import com.fan.core.model.user.Responsedata;
import com.fan.core.module.GetUserData;
import com.fan.core.module.MyBalanceActivity;
import com.fan.core.module.Profile;
import com.fan.core.service.NotificationServ;
import com.fan.core.util.AppConstants;
import com.fan.core.util.CircleImageView;
import com.fan.core.util.SharedPrefUtility;

import static com.fan.core.util.AppConstants.CLASSES;
import static com.fan.core.util.AppConstants.callLanguage;
import static com.fan.core.util.AppConstants.getUserSaved;
import static com.fan.core.util.AppConstants.profile_pic;
import static com.fan.core.util.AppConstants.startTutorial;

/**
 * Created by mohit.soni @ 05-Oct-19.
 */
public class MainFragmentActivity extends FragmentActivity implements UpdateBitmap {
    FragmentManager fragmentManager;
    ProgressDialog progressDialog;

    Toolbar tb_main_frame_2, tb_header;
    RelativeLayout rl_main_frame_my_team,
            rl_main_frame_pl;
    ImageView iv_main_frame_my_team,
            iv_main_frame_pl, iv_power;
    ImageView iv_toolbar_back;
    TextView tv_toolbar_head, tv_toolbar_language;
    ImageView iv_toolbar_wallet_icon, iv_toolbar_icon_tutorial;
    CircleImageView iv_toolbar_profile_image;

    Responsedata responsedata;
    Resources resources;
    FrameLayout main_fragment;

    LinearLayout ll_toolbar_language_change;
    TextView tv_toolbar_language_amheric, tv_toolbar_language_english;
    SharedPrefUtility prefUtility;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.main_fragment);




        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        }
        resources = getResources();
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage("Getting Content");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);

        tb_header = (Toolbar) findViewById(R.id.tb_header);
        tb_main_frame_2 = (Toolbar) findViewById(R.id.tb_main_frame_2);
        main_fragment = (FrameLayout) findViewById(R.id.fragment);
        main_fragment.setBackgroundColor(Color.parseColor("#eeeeee"));

//        setBackgroundColor(getResources().getColor(R.color.green));


        iv_toolbar_back = (ImageView) findViewById(R.id.iv_toolbar_back);
        iv_toolbar_wallet_icon = (ImageView) findViewById(R.id.iv_toolbar_wallet_icon);
        iv_toolbar_icon_tutorial = (ImageView) findViewById(R.id.iv_toolbar_icon_tutorial);
        iv_power = (ImageView) findViewById(R.id.iv_power);
        iv_toolbar_profile_image = (CircleImageView) findViewById(R.id.iv_toolbar_profile_image);
        tv_toolbar_head = (TextView) findViewById(R.id.tv_toolbar_head);
        tv_toolbar_language = (TextView) findViewById(R.id.tv_toolbar_language);

        tv_toolbar_language_amheric = (TextView) findViewById(R.id.tv_toolbar_language_amheric);
        tv_toolbar_language_english = (TextView) findViewById(R.id.tv_toolbar_language_english);
        ll_toolbar_language_change = (LinearLayout) findViewById(R.id.ll_toolbar_language_change);

        iv_toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        iv_toolbar_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainFragmentActivity.this, Profile.class));
                finish();
            }
        });
        iv_toolbar_icon_tutorial.setVisibility(View.VISIBLE);
        iv_toolbar_wallet_icon.setVisibility(View.VISIBLE);
        tv_toolbar_head.setText(getResources().getString(R.string.profile));
        try {
            responsedata = getUserSaved(this);
        } catch (NullPointerException e) {
        }
        if (profile_pic != null) {
            iv_toolbar_profile_image.setImageBitmap(profile_pic);
        } else {
            new GetUserData(this, responsedata.getProfileImage(), iv_toolbar_profile_image, false, this);
        }

        /*rl_main_frame_create_team = (RelativeLayout) findViewById(R.id.rl_main_frame_create_team);*/
        rl_main_frame_my_team = (RelativeLayout) findViewById(R.id.rl_main_frame_my_team);
        /*rl_main_frame_transfer = (RelativeLayout) findViewById(R.id.rl_main_frame_transfer);*/
        rl_main_frame_pl = (RelativeLayout) findViewById(R.id.rl_main_frame_pl);

        iv_main_frame_my_team = (ImageView) findViewById(R.id.iv_main_frame_my_team);
        /* iv_main_frame_transfer = (ImageView) findViewById(R.id.iv_main_frame_transfer);*/
        iv_main_frame_pl = (ImageView) findViewById(R.id.iv_main_frame_pl);
        /* iv_main_frame_create_team = (ImageView) findViewById(R.id.iv_main_frame_create_team);*/


        /*iv_main_frame_create_team.setBackgroundResource(R.mipmap.t_shirt_on);*/
        iv_main_frame_my_team.setBackgroundResource(R.mipmap.award_off);
        /*iv_main_frame_transfer.setBackgroundResource(R.mipmap.transfer_off);*/
        iv_main_frame_pl.setBackgroundResource(R.mipmap.pl_off);

        /*iv_main_frame_create_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCreateTeam();
                replaceFragment(new CreateTeam(MainFragmentActivity.this,MainFragmentActivity.this));
            }
        });*/
        rl_main_frame_my_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMyTeam();
                replaceFragment(new My_Team(MainFragmentActivity.this, MainFragmentActivity.this));
            }
        });
        /*iv_main_frame_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTransfer();
                replaceFragment(new Transfer(MainFragmentActivity.this, MainFragmentActivity.this));
            }
        });*/
        iv_main_frame_pl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePL();
                replaceFragment(new PL(MainFragmentActivity.this, MainFragmentActivity.this));
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
                tv_toolbar_language_english.setTextColor(resources.getColor(R.color.white));
                callLanguage(MainFragmentActivity.this, MainFragmentActivity.this, MainFragmentActivity.class, resources, 1);
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
                tv_toolbar_language_amheric.setTextColor(resources.getColor(R.color.white));
                callLanguage(MainFragmentActivity.this, MainFragmentActivity.this, MainFragmentActivity.class, resources, 0);
            }
        });
        iv_toolbar_icon_tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTutorial(MainFragmentActivity.this);
            }
        });
        iv_toolbar_wallet_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainFragmentActivity.this, MyBalanceActivity.class));
            }
        });
        /*tv_toolbar_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callLanguage(MainFragmentActivity.this,MainFragmentActivity.this,MainFragmentActivity.class,resources);
            }
        });*/

        iv_power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new GameList(MainFragmentActivity.this, MainFragmentActivity.this));
            }
        });
        ll_toolbar_language_change.setBackground(resources.getDrawable(R.drawable.round_button_toolbar_language_english));
        /*   tv_toolbar_language.setText(CURRENT_LANGUAGE);*/
        replaceFragment(new GameList(MainFragmentActivity.this, MainFragmentActivity.this));
        prefUtility = new SharedPrefUtility(this);
        if (prefUtility != null) {
            String lng = prefUtility.getLanguage();
            if (lng.equals("en")) {
                ll_toolbar_language_change.setBackground(resources.getDrawable(R.drawable.round_button_toolbar_language_english));
                tv_toolbar_language_english.setTextColor(resources.getColor(R.color.black));
                tv_toolbar_language_amheric.setTextColor(resources.getColor(R.color.white));
            } else {
                ll_toolbar_language_change.setBackground(resources.getDrawable(R.drawable.round_button_toolbar_language_amheric));
                tv_toolbar_language_amheric.setTextColor(resources.getColor(R.color.black));
                tv_toolbar_language_english.setTextColor(resources.getColor(R.color.white ));
            }
        }
    }



    public void updateGameList() {
        setTitle(getResources().getString(R.string.games));
        /* iv_main_frame_create_team.setBackgroundResource(R.mipmap.t_shirt_off);*/
        iv_main_frame_my_team.setBackgroundResource(R.mipmap.award_off);
        /*iv_main_frame_transfer.setBackgroundResource(R.mipmap.transfer_off);*/
        iv_main_frame_pl.setBackgroundResource(R.mipmap.pl_off);
    }

    public void updateCreateTeam() {
        setTitle(getResources().getString(R.string.team));
        iv_main_frame_my_team.setBackgroundResource(R.mipmap.award_off);
        iv_main_frame_pl.setBackgroundResource(R.mipmap.pl_off);
    }

    public void updateMyTeam() {
        setTitle(getResources().getString(R.string.my_team));
        /*iv_main_frame_create_team.setBackgroundResource(R.mipmap.t_shirt_off);*/
        iv_main_frame_my_team.setBackgroundResource(R.mipmap.award_on);
        /* iv_main_frame_transfer.setBackgroundResource(R.mipmap.transfer_off);*/
        iv_main_frame_pl.setBackgroundResource(R.mipmap.pl_off);
    }

    public void updateTransfer() {
        setTitle(getResources().getString(R.string.transfer));
        iv_main_frame_my_team.setBackgroundResource(R.mipmap.award_off);
        iv_main_frame_pl.setBackgroundResource(R.mipmap.pl_off);
    }

    public void updatePL() {
        setTitle(getResources().getString(R.string.tables));
        /* iv_main_frame_create_team.setBackgroundResource(R.mipmap.t_shirt_off);*/
        iv_main_frame_my_team.setBackgroundResource(R.mipmap.award_off);
        /*iv_main_frame_transfer.setBackgroundResource(R.mipmap.transfer_off);*/
        iv_main_frame_pl.setBackgroundResource(R.mipmap.pl_on);
    }

    public void replaceFragment(Fragment destFragment) {
        fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(destFragment.getClass().getSimpleName() + "");
        fragmentTransaction.replace(R.id.fragment, destFragment);
        fragmentTransaction.commit();
    }

    public void setTitle(String string) {
        tv_toolbar_head.setText(string);
    }

    /**
     * show alert with message
     *
     * @param activity
     * @param title
     * @param message
     * @param status
     */
    public void showDoubleButtonAlertDialogFrag(final Activity activity, String title, String message,
                                                Boolean status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(status);
        builder.setPositiveButton(getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }
                });
        builder.setNegativeButton(getString(android.R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void showProgress(String message, boolean isCancelable) {
        progressDialog.setMessage(message);
        progressDialog.setCancelable(isCancelable);
        progressDialog.show();
    }

    public void showProgress() {
        progressDialog.show();
    }

    public void hideProgress() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() == 1) {
            showDoubleButtonAlertDialogFrag(this, getResources().getString(R.string.exit), getResources().getString(R.string.you_want_to_exit_app), false);
        } else {
            fragmentManager.popBackStack();
        }
        showToolbar();
    }

    public void hideToolbar() {
        tb_main_frame_2.setVisibility(View.GONE);
        tb_header.setVisibility(View.GONE);
    }

    public void showToolbar() {
        tb_main_frame_2.setVisibility(View.VISIBLE);
        tb_header.setVisibility(View.VISIBLE);
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void log(String TAG, String msg) {
        Log.i(TAG, msg);
    }

    @Override
    public void setBitmap(Bitmap bitmap) {
        hideProgress();
    }

    public void fin(){
        finish();
    }
}
