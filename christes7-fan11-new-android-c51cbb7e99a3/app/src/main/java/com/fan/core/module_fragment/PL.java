package com.fan.core.module_fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.fan.core.R;
import com.fan.core.adapter.Fixtures_Adapter;
import com.fan.core.custom_view.CustomViewPager;
import com.fan.core.helper.PLEnum;
import com.fan.core.model.fixtures.FixturesData;
import com.fan.core.util.AppConstants;
import com.fan.core.util.NetworkAPI;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.RequestParams;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import static com.fan.core.util.AppConstants.FIXTURES;
import static com.fan.core.util.AppConstants.updateNull;

/**
 * Created by mohit.soni @ 20-Oct-19.
 */
public class PL extends BaseFragment {
    private final String TAG = PL.class.getSimpleName();
    private Context context;
    private Activity activity;
    private CustomViewPager viewPage;
    private TextView tv_pl_fixtures;
    PLEnum plEnum = null;
    RecyclerView rv_fixtures;

    Fixtures_Adapter fixtures_adapter;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat dateFormatdisplay = new SimpleDateFormat("MM/dd/yyyy");
    Date date;
    public String selectDate = "";
    ArrayList<FixturesData> fixturesDataArrayList = new ArrayList<>();

    public PL(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        init(context);
        date = new Date();
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainFragmentActivity) context).updatePL();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pl, container, false);

        viewPage = (CustomViewPager) view.findViewById(R.id.vp_pl);
        tv_pl_fixtures = (TextView) view.findViewById(R.id.tv_pl_fixtures);

        viewPage.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 1;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                plEnum = PLEnum.values()[position];
                int layout = plEnum.getLayoutResId();
                ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(layout, container, false);


                if (layout == R.layout.pl_fixtures) {
                   /* final TextView tv_fixture_date_1 = (TextView) viewGroup.findViewById(R.id.tv_fixture_date_1);
                    final TextView tv_fixture_date_2 = (TextView) viewGroup.findViewById(R.id.tv_fixture_date_2);
                    final TextView tv_fixture_date_3 = (TextView) viewGroup.findViewById(R.id.tv_fixture_date_3);
                    final TextView tv_fixture_date_4 = (TextView) viewGroup.findViewById(R.id.tv_fixture_date_4);
                    final TextView tv_fixture_date_5 = (TextView) viewGroup.findViewById(R.id.tv_fixture_date_5);

                    final RelativeLayout rl_fixture_icon = (RelativeLayout) viewGroup.findViewById(R.id.rl_fixture_icon);*/

                    callService(dateFormatdisplay.format(date) + "");
                    rv_fixtures = (RecyclerView) viewGroup.findViewById(R.id.rv_fixtures);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                   /* mLayoutManager.setStackFromEnd(true);*/
                    rv_fixtures.setLayoutManager(mLayoutManager);
                   /* tv_fixture_date_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tv_fixture_date_1.setTextColor(context.getResources().getColor(R.color.app_light_yellow));
                            tv_fixture_date_2.setTextColor(context.getResources().getColor(R.color.white));
                            tv_fixture_date_3.setTextColor(context.getResources().getColor(R.color.white));
                            tv_fixture_date_4.setTextColor(context.getResources().getColor(R.color.white));
                            tv_fixture_date_5.setTextColor(context.getResources().getColor(R.color.white));
                            rv_fixture.setAdapter(fixtures_adapter);
                            rv_fixture.scrollToPosition(fixtures_adapter.getItemCount()-1);
                        }
                    });
                    tv_fixture_date_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tv_fixture_date_1.setTextColor(context.getResources().getColor(R.color.white));
                            tv_fixture_date_2.setTextColor(context.getResources().getColor(R.color.app_light_yellow));
                            tv_fixture_date_3.setTextColor(context.getResources().getColor(R.color.white));
                            tv_fixture_date_4.setTextColor(context.getResources().getColor(R.color.white));
                            tv_fixture_date_5.setTextColor(context.getResources().getColor(R.color.white));
                            rv_fixture.setAdapter(fixtures_adapter);
                            rv_fixture.scrollToPosition(fixtures_adapter.getItemCount()-1);
                        }
                    });
                    tv_fixture_date_3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tv_fixture_date_1.setTextColor(context.getResources().getColor(R.color.white));
                            tv_fixture_date_2.setTextColor(context.getResources().getColor(R.color.white));
                            tv_fixture_date_3.setTextColor(context.getResources().getColor(R.color.app_light_yellow));
                            tv_fixture_date_4.setTextColor(context.getResources().getColor(R.color.white));
                            tv_fixture_date_5.setTextColor(context.getResources().getColor(R.color.white));
                            rv_fixture.setAdapter(fixtures_adapter);
                            rv_fixture.scrollToPosition(fixtures_adapter.getItemCount()-1);
                        }
                    });
                    tv_fixture_date_4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tv_fixture_date_1.setTextColor(context.getResources().getColor(R.color.white));
                            tv_fixture_date_2.setTextColor(context.getResources().getColor(R.color.white));
                            tv_fixture_date_3.setTextColor(context.getResources().getColor(R.color.white));
                            tv_fixture_date_4.setTextColor(context.getResources().getColor(R.color.app_light_yellow));
                            tv_fixture_date_5.setTextColor(context.getResources().getColor(R.color.white));
                            rv_fixture.setAdapter(fixtures_adapter);
                            rv_fixture.scrollToPosition(fixtures_adapter.getItemCount()-1);
                        }
                    });
                    tv_fixture_date_5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tv_fixture_date_1.setTextColor(context.getResources().getColor(R.color.white));
                            tv_fixture_date_2.setTextColor(context.getResources().getColor(R.color.white));
                            tv_fixture_date_3.setTextColor(context.getResources().getColor(R.color.white));
                            tv_fixture_date_4.setTextColor(context.getResources().getColor(R.color.white));
                            tv_fixture_date_5.setTextColor(context.getResources().getColor(R.color.app_light_yellow));
                            rv_fixture.setAdapter(fixtures_adapter);
                            rv_fixture.scrollToPosition(fixtures_adapter.getItemCount()-1);
                        }
                    });
                    tv_fixture_date_1.setTextColor(context.getResources().getColor(R.color.app_light_yellow));
                    tv_fixture_date_2.setTextColor(context.getResources().getColor(R.color.white));
                    tv_fixture_date_3.setTextColor(context.getResources().getColor(R.color.white));
                    tv_fixture_date_4.setTextColor(context.getResources().getColor(R.color.white));
                    tv_fixture_date_5.setTextColor(context.getResources().getColor(R.color.white));*/
                }

                if (layout == R.layout.table) {
                    /*if (AppConstants.isOnline(context)) {
                        RequestParams params = new RequestParams();
                        getServicewithHeader(NetworkAPI.FIXTURES, params, TABLES);
                        showDialog("Getting tables",false);
                    } else {
                        showToast(getResources().getString(R.string.no_internet_connection));
                    }
                    rv_fixtures = (RecyclerView) viewGroup.findViewById(R.id.rv_fixtures);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                    rv_fixtures.setLayoutManager(mLayoutManager);*/
                }
                container.addView(viewGroup);
                return viewGroup;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewPager) container).removeView((View) object);
            }
        });
        tv_pl_fixtures.setTextColor(context.getResources().getColor(R.color.black));
        tv_pl_fixtures.setText(dateFormatdisplay.format(date) + "");
        /* tv_pl_table.setTextColor(context.getResources().getColor(R.color.white));*/
        viewPage.setCurrentItem(0, true);
        tv_pl_fixtures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cal = tv_pl_fixtures.getText().toString();
                int[] c = {0, 0, 0};
                if (!cal.isEmpty()) {
                    String[] ca = cal.split("/");
                    c[0] = Integer.parseInt(ca[1]);
                    int month = Integer.parseInt(ca[0]);
                    if (month > 0) {
                        c[1] = month - 1;
                    } else {
                        c[1] = 0;
                    }
                    c[2] = Integer.parseInt(ca[2]);
                }
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
                        int mon = monthOfYear + 1;
                        selectDate = updateDay(mon) + "/" + updateDay((dayOfMonth)) + "/" + year;
                        tv_pl_fixtures.setText(selectDate);
                        rv_fixtures.setAdapter(null);
                        callService(selectDate);
                    }
                };
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                        listener,
                        c[2],
                        c[1],
                        c[0]
                );
                /*datePickerDialog.setMinDate(calendar);*/
                datePickerDialog.setYearRange(2000, 2020);
                // disable future date
                datePickerDialog.show(activity.getFragmentManager(), "datePicker");
                datePickerDialog.setThemeDark(true);
                datePickerDialog.setAccentColor(activity.getResources().getColor(R.color.app_light_yellow));
            }
        });
       /* tv_pl_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_pl_fixtures.setTextColor(context.getResources().getColor(R.color.white));
                tv_pl_table.setTextColor(context.getResources().getColor(R.color.app_light_yellow));
                viewPage.setCurrentItem(1, true);
            }
        });*/
        viewPage.disableScroll();
        return view;
    }

    @Override
    public void onResponseListener(int requestCode, Object responseModel) {
        log(TAG, responseModel.toString());
        Gson gson = new Gson();
        /*if (requestCode == FIXTURES) {
            UserBean bean = gson.fromJson((String) responseModel, UserBean.class);
            final Fixtures_Adapter fixtures_adapter = new Fixtures_Adapter(context, AppConstants.getDummyArrayList2());
            rv_fixtures.setAdapter(fixtures_adapter);
        }
        if (requestCode == TABLES) {
            UserBean bean = gson.fromJson((String) responseModel, UserBean.class);
            final Table_Adapter table_adapter = new Table_Adapter(context, AppConstants.getDummyArrayList2());
            rv_fixtures.setAdapter(table_adapter);
        }*/
        try {
            JSONObject root = new JSONObject(responseModel.toString());

            JSONObject api = root.getJSONObject("api");
            JSONObject fixtures = api.getJSONObject("fixtures");
            Iterator keys = fixtures.keys();

            fixturesDataArrayList.clear();
            while (keys.hasNext()) {
                String currentDynamicKey = (String) keys.next();
                JSONObject currentDynamicValue = fixtures.getJSONObject(currentDynamicKey);

                FixturesData fixturesData = new FixturesData();

                fixturesData.setFixtureId(currentDynamicValue.getString("fixture_id"));
                fixturesData.setEventTimestamp(currentDynamicValue.getString("event_timestamp"));
                fixturesData.setEventDate(currentDynamicValue.getString("event_date"));
                fixturesData.setLeagueId(currentDynamicValue.getString("league_id"));
                fixturesData.setRound(currentDynamicValue.getString("round"));
                fixturesData.setHomeTeamId(currentDynamicValue.getString("homeTeam_id"));
                fixturesData.setAwayTeamId(currentDynamicValue.getString("awayTeam_id"));
                fixturesData.setHomeTeam(currentDynamicValue.getString("homeTeam"));
                fixturesData.setAwayTeam(currentDynamicValue.getString("awayTeam"));
                fixturesData.setStatus(currentDynamicValue.getString("status"));
                fixturesData.setStatusShort(currentDynamicValue.getString("statusShort"));
                fixturesData.setGoalsHomeTeam(currentDynamicValue.getString("goalsHomeTeam"));
                fixturesData.setGoalsAwayTeam(currentDynamicValue.getString("goalsAwayTeam"));
                fixturesData.setHalftimeScore(currentDynamicValue.getString("halftime_score"));
                fixturesData.setFinalScore(currentDynamicValue.getString("final_score"));
                fixturesData.setPenalty(currentDynamicValue.getString("penalty"));
                fixturesData.setElapsed(currentDynamicValue.getString("elapsed"));
                fixturesData.setFirstHalfStart(currentDynamicValue.getString("firstHalfStart"));
                fixturesData.setSecondHalfStart(currentDynamicValue.getString("secondHalfStart"));

                fixturesDataArrayList.add(fixturesData);

            }
            fixtures_adapter = new Fixtures_Adapter(context, fixturesDataArrayList);
            rv_fixtures.setAdapter(fixtures_adapter);
            /*rv_fixtures.scrollToPosition(0);*/


        } catch (JSONException e) {

        } catch (NullPointerException e){
            e.printStackTrace();
        }catch (JsonSyntaxException e) {
            log(TAG, e.getMessage());
        }
        hideProgress();
    }

    @Override
    public void onErrorListener(int requestCode, String error) {
        showToast(requestCode + " : " + error);
    }

    public void callService(String date) {
        String[] d = date.split("/");
        if (AppConstants.isOnline(context)) {
            String url = NetworkAPI.FIXTURES + d[2] + "-" + d[0] + "-" + d[1];
            RequestParams params = new RequestParams();
            getServicewithHeader(url, params, FIXTURES);
            showDialog("Getting fixtures", true);
        } else {
            showToast(getResources().getString(R.string.no_internet_connection));
        }
    }

    public static String updateDay(int day) {
        return Integer.toString(day).length() == 1 ? "0" + Integer.toString(day) : Integer.toString(day);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
