package com.fan.core.tutorial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;

import com.fan.core.R;
import com.fan.core.model.user.Responsedata;
import com.fan.core.module.AddBalanceActivity;
import com.fan.core.module.AddCouponActivity;
import com.fan.core.module.BaseActivity;
import com.fan.core.module.MyBalanceActivity;
import com.fan.core.util.CircleImageView;
import com.fan.core.util.SharedPrefUtility;

import static com.fan.core.util.AppConstants.callLanguage;
import static com.fan.core.util.AppConstants.getUserSaved;
import static com.fan.core.util.AppConstants.profile_pic;
import static com.fan.core.util.AppConstants.startTutorial;

public class Main extends BaseActivity {
    public static final String TAG = Main.class.getSimpleName();

    TextView tv_toolbar_head, tv_toolbar_language, tv_add_balance_amount,
            tv_add_balance_amount_1, tv_add_balance_amount_2, tv_add_balance_amount_3;
    ImageView iv_toolbar_wallet_icon, iv_toolbar_icon_tutorial, iv_toolbar_back;
    CircleImageView iv_toolbar_profile_image;
    LinearLayout ll_amount_otp;
    Button bt_add_balance;
    Responsedata responsedata;
    Resources resources;
    EditText et_add_balance_amount_add, et_add_balance_amount_add_otp;
    LinearLayout ll_toolbar_language_change;
    TextView tv_toolbar_language_amheric, tv_toolbar_language_english;
    SharedPrefUtility prefUtility;

    ExpandableCustomAdapter expandableCustomAdapter;
    ExpandableListView expandableListView;
    List<String> headerData;
    HashMap<String,ArrayList<ChildDataModel>> childData;
    ChildDataModel childDataModel;
    Context mContext;
    ArrayList<ChildDataModel> one,two,three,four,five,six,seven,eight,nine;
    private int lastExpandedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        }
        resources = getResources();
        iv_toolbar_wallet_icon = (ImageView) findViewById(R.id.iv_toolbar_wallet_icon);
        iv_toolbar_icon_tutorial = (ImageView) findViewById(R.id.iv_toolbar_icon_tutorial);
        ll_amount_otp = (LinearLayout) findViewById(R.id.ll_amount_otp);
        iv_toolbar_profile_image = (CircleImageView) findViewById(R.id.iv_toolbar_profile_image);
        et_add_balance_amount_add_otp = (EditText) findViewById(R.id.et_add_balance_amount_add_otp);
        tv_toolbar_head = (TextView) findViewById(R.id.tv_toolbar_head);
        iv_toolbar_back = (ImageView) findViewById(R.id.iv_toolbar_back);
        tv_toolbar_language = (TextView) findViewById(R.id.tv_toolbar_language);
        tv_add_balance_amount = (TextView) findViewById(R.id.tv_add_balance_amount);
        et_add_balance_amount_add = (EditText) findViewById(R.id.et_add_balance_amount_add);
        tv_add_balance_amount_1 = (TextView) findViewById(R.id.tv_add_balance_amount_1);
        tv_add_balance_amount_2 = (TextView) findViewById(R.id.tv_add_balance_amount_2);
        tv_add_balance_amount_3 = (TextView) findViewById(R.id.tv_add_balance_amount_3);
        tv_toolbar_language_amheric = (TextView) findViewById(R.id.tv_toolbar_language_amheric);
        tv_toolbar_language_english = (TextView) findViewById(R.id.tv_toolbar_language_english);
        ll_toolbar_language_change = (LinearLayout) findViewById(R.id.ll_toolbar_language_change);

        bt_add_balance = (Button) findViewById(R.id.bt_add_balance);

        iv_toolbar_icon_tutorial.setVisibility(View.VISIBLE);
        iv_toolbar_wallet_icon.setVisibility(View.VISIBLE);
        iv_toolbar_wallet_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main.this, MyBalanceActivity.class));
            }
        });

        if (profile_pic != null) {
            iv_toolbar_profile_image.setImageBitmap(profile_pic);
        }
        try {
            responsedata = getUserSaved(this);
        } catch (NullPointerException e) {
        }

        iv_toolbar_icon_tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTutorial(Main.this);
            }
        });
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

        tv_toolbar_language_amheric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prefUtility != null) {
                    prefUtility.setLanguage("ar");
                }
                ll_toolbar_language_change.setBackground(resources.getDrawable(R.drawable.round_button_toolbar_language_amheric));
                tv_toolbar_language_amheric.setTextColor(resources.getColor(R.color.black));
                tv_toolbar_language_english.setTextColor(resources.getColor(R.color.grey));
                callLanguage(Main.this, Main.this, AddBalanceActivity.class, resources, 1);
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
                callLanguage(Main.this, Main.this, AddBalanceActivity.class, resources, 0);
            }
        });

//        ImageView imageView = (ImageView) findViewById(R.id.expandable_icon);
//        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_arrow_right_24));


        //initializing arraylists
        headerData = new ArrayList<>();
        childData = new HashMap<String,ArrayList<ChildDataModel>>();
        one = new ArrayList<>();
        two = new ArrayList<>();
        three = new ArrayList<>();
        four = new ArrayList<>();
        five = new ArrayList<>();
        six = new ArrayList<>();
        seven = new ArrayList<>();
        eight = new ArrayList<>();
        nine = new ArrayList<>();





        // link listview from activity_main.xml
        expandableListView = findViewById(R.id.expandAbleListView);


        headerData.add("Sign Up");
        //adding countries to Asian continent
        childDataModel = new ChildDataModel(1,"Sign up with Fan 11 and get an Amole account and 10Birr deposited for your first game.",R.drawable.layer_1);
        one.add(childDataModel);
        childData.put(headerData.get(0),one);



//        headerData.add("Choose Contest");
//        childDataModel = new ChildDataModel(1,"In the fire tab, you can enter games and compete with others to win prizes. Press i to gt information for each game, click on join to enter your team ",R.drawable.layer_2);
//        asianCountries.add(childDataModel);
//        childData.put(headerData.get(1),africanCountries);
////
////
//        headerData.add("Make your picks");
////        //adding countries to NORTH AMERICA continent
//        childDataModel = new ChildDataModel(1,"Select players by clicking on players. To deselect a player just press on highlighted player. The progress bar will show you " +
//                "how many players you need to finish building your team",R.drawable.layer_3);
//        asianCountries.add(childDataModel);
//        childData.put(headerData.get(2),nAmericanCountries);
//
//
              headerData.add("Choose Contest");
        childDataModel = new ChildDataModel(1,"In the fire tab, you can enter games and compete with others to win prizes. Press i to gt information for each game, click on join to enter your team ",R.drawable.layer_2);
        two.add(childDataModel);
        childData.put(headerData.get(1),two);

        headerData.add("Make Your Picks");
        childDataModel = new ChildDataModel(1,"Select players by clicking on players. To deselect a player just press on highlighted player. The progress bar will show you " +
                "how many players you need to finish building your team",R.drawable.layer_3);
        three.add(childDataModel);
        childData.put(headerData.get(2),three);

        headerData.add("View Team");
        childDataModel = new ChildDataModel(1,"Enter \"View Team\" to view your team. select Captain" +
                "and your vice captain and submit",R.drawable.layer_4);
        four.add(childDataModel);
        childData.put(headerData.get(3),four);

        headerData.add("Point System");
        childDataModel = new ChildDataModel(1,"Watch your team is earn points with this tactics",R.drawable.layer_5);
        five.add(childDataModel);
        childData.put(headerData.get(4),five);

        headerData.add("View Points");
        childDataModel = new ChildDataModel(1,"After you have selected your team, watch them play" +
                "in real life and earn points based on this table" ,R.drawable.layer_6);
        six.add(childDataModel);
        childData.put(headerData.get(5),six);

        headerData.add("My Team");
        childDataModel = new ChildDataModel(1,"Go to my team page to see your team and get your live points and how you are doing" +
                "with other players" ,R.drawable.layer_7);
        seven.add(childDataModel);
        childData.put(headerData.get(6),seven);

        headerData.add("Ranking");
        childDataModel = new ChildDataModel(1,"Fan11 is different because you win by beating other players, not the house" ,R.drawable.layer_8);
        eight.add(childDataModel);
        childData.put(headerData.get(7),eight);

        headerData.add("Wallet");
        childDataModel = new ChildDataModel(1,"To add/withdraw money, enter our wallet page and add balance from your Amole account" +
                "to your fan11 account. you can also view your previous transactions" ,R.drawable.layer_9);
        nine.add(childDataModel);
        childData.put(headerData.get(8),nine);




        //set adapter to list view
        expandableCustomAdapter = new ExpandableCustomAdapter(mContext,headerData,childData);
        expandableListView.setAdapter(expandableCustomAdapter);

        //child click listener
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int headPosition, int childPosition, long id) {
                Toast.makeText(mContext,
                        headerData.get(headPosition)
                                + " has country "
                                + childData.get(
                                headerData.get(headPosition)).get(
                                childPosition).getTitle(), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });

        //group expanded
//        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//
//            @Override
//            public void onGroupExpand(int headPosition) {
//                if (lastExpandedPosition != -1
//                        && headPosition != lastExpandedPosition) {
//                    expandableListView.collapseGroup(lastExpandedPosition);
//                }
//                lastExpandedPosition = headPosition;
////                Toast.makeText(mContext,
////                        headerData.get(headPosition) + " continent expanded",
////                        Toast.LENGTH_SHORT).show();
//            }
//        });

        //group collapsed
//        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
//            @Override
//            public void onGroupCollapse(int headPosition) {
//                Toast.makeText(mContext,
//                        headerData.get(headPosition) + " continent collapsed",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });

        //Group Indicator
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                parent.smoothScrollToPosition(groupPosition);

                if (parent.isGroupExpanded(groupPosition)) {
                    ImageView imageView = v.findViewById(R.id.expandable_icon);
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_arrow_right_24));
                } else {
                    ImageView imageView = v.findViewById(R.id.expandable_icon);
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_arrow_drop_down_24));
                }
                return false    ;
            }
        });
    }

    @Override
    public void onResponseListener(int requestCode, Object responseModel) {

    }

    @Override
    public void onErrorListener(int requestCode, String error) {

    }
}