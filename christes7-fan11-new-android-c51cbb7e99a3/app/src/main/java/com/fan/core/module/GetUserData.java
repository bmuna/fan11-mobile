package com.fan.core.module;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fan.core.R;
import com.fan.core.helper.UpdateBitmap;
import com.fan.core.model.city.CityBean;
import com.fan.core.model.country.Responsedatum;
import com.fan.core.model.state.StateBean;
import com.fan.core.model.user.Responsedata;
import com.fan.core.util.AppConstants;
import com.fan.core.util.NetworkAPI;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.RequestParams;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.fan.core.util.AppConstants.GET_TABLE_DATA_CITY;
import static com.fan.core.util.AppConstants.GET_TABLE_DATA_STATE;
import static com.fan.core.util.AppConstants.city;
import static com.fan.core.util.AppConstants.city_responsedata;
import static com.fan.core.util.AppConstants.country;
import static com.fan.core.util.AppConstants.getUserSaved;
import static com.fan.core.util.AppConstants.prefUtility;
import static com.fan.core.util.AppConstants.profile_pic;
import static com.fan.core.util.AppConstants.state;
import static com.fan.core.util.AppConstants.state_responsedata;

public class GetUserData extends BaseActivity {
    private static  final String TAG = GetUserData.class.getSimpleName();
    Context context;
    Responsedata responsedata;
    boolean getData = false;
    String urls;
    /*ProgressDialog progressDialog;*/
    UpdateBitmap updateBitmap;
    Dialog dialog = null;
    TextView tv_football_progress;
    RelativeLayout rl_football;

    public GetUserData(Context context, String urls, ImageView imageView, boolean getData,
                       UpdateBitmap updateBitmap) {
        this.context = context;
        this.updateBitmap = updateBitmap;
        this.getData = getData;
        this.urls = urls;
        try {
            responsedata = getUserSaved(this);
        } catch (NullPointerException e) {
        }
        /*progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage(context.getResources().getString(R.string.collecting_detail));
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();*/

        callDialog();
        new DownloadImageTask(imageView).execute();
    }

    public void callDialog() {
        dialog = new Dialog(this.context);
        dialog.setContentView(R.layout.football_progress);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        rl_football = (RelativeLayout) dialog.findViewById(R.id.rl_football);
        iv_football = (ImageView) dialog.findViewById(R.id.iv_football);
        tv_football_progress = (TextView) dialog.findViewById(R.id.tv_football_progress);
        iv_animation = AnimationUtils.loadAnimation(context,R.anim.rotate);
        showDialog();
    }

    public void showDialog() {
        tv_football_progress.setText("");
        dialog.setCancelable(false);
        iv_football.setAnimation(iv_animation);
        dialog.show();
    }
    public void callCountryDataUpdate(Responsedata bean) {
        if (!bean.getCountry().equals("")) {
            setCountryData(bean.getCountry(), bean);
        } else {
            country = null;
            state = null;
            city = null;
        }
    }

    public void setCountryData(String countryId, Responsedata bean) {
        try {
            AppConstants.getCountryJson(context);
            for (Responsedatum responsedatum : AppConstants.country_data) {
                if (responsedatum.getCountryId().equals(countryId)) {
                    country = responsedatum;
                }
            }
            if (country != null) {
                if (prefUtility != null) {
                    prefUtility.setStateCity("country", country.getCountryId(), "country_name", country.getName());
                }
                if (!bean.getCountry().equals("")) {
                    sendUpdateStateRequest();
                } else {
                    dialog.dismiss();
                }
            } else {
                dialog.dismiss();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            dialog.dismiss();
        }
    }

    public void sendUpdateStateRequest() {
        if (AppConstants.isOnline(context)) {
            RequestParams params = new RequestParams();
            params.put("tablename", "state");
            params.put("field", "country_id");
            params.put("value", country.getCountryId());
            postService(NetworkAPI.GET_TABLE_DATA, params, GET_TABLE_DATA_STATE);
        } else {
            showToast(getResources().getString(R.string.no_internet_connection));
        }
    }

    public void setStateData(String stateId, Responsedata responsedata) {
        for (com.fan.core.model.state.Responsedatum responsedatum : state_responsedata) {
            if (responsedatum.getStateId().equals(stateId)) {
                state = responsedatum;
            }
        }
        try {
            if (state != null) {
                if (prefUtility != null) {
                    prefUtility.setStateCity("state", state.getStateId(), "state_name", state.getName());
                }
                if (!responsedata.getState().equals("")) {
                    sendUpdateCityRequest();
                } else {
                    dialog.dismiss();
                }
            } else {
                dialog.dismiss();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            dialog.dismiss();
        }
    }

    public void sendUpdateCityRequest() {
        if (AppConstants.isOnline(context)) {
            RequestParams params = new RequestParams();
            params.put("tablename", "city");
            params.put("field", "state_id");
            params.put("value", state.getStateId());
            postService(NetworkAPI.GET_TABLE_DATA, params, GET_TABLE_DATA_CITY);
        } else {
            showToast(getResources().getString(R.string.no_internet_connection));
        }
    }

    public void setCityData(String cityId) {
        for (com.fan.core.model.city.Responsedatum responsedatum : city_responsedata) {
            if (responsedatum.getCityId().equals(cityId)) {
                city = responsedatum;
            }
        }
        try {
            if (city != null) {
                if (prefUtility != null) {
                    prefUtility.setStateCity("city", city.getCityId(), "city_name", city.getName());
                }
            }
            dialog.dismiss();
        } catch (NullPointerException e) {
            e.printStackTrace();
            dialog.dismiss();
        }

    }

    @Override
    public void onResponseListener(int requestCode, Object responseModel) {
        try {
            Gson gson = new Gson();
            if (requestCode == GET_TABLE_DATA_STATE) {
                StateBean bean = gson.fromJson((String) responseModel, StateBean.class);
                state_responsedata = bean.getResponsedata();
                setStateData(responsedata.getState(), responsedata);
            }
            if (requestCode == GET_TABLE_DATA_CITY) {
                CityBean bean = gson.fromJson((String) responseModel, CityBean.class);
                city_responsedata = bean.getResponsedata();
                setCityData(responsedata.getCity());
            }
        } catch (JsonSyntaxException e) {
            log(TAG, e.getMessage());
        }
    }

    @Override
    public void onErrorListener(int requestCode, String error) {
        showToast(requestCode + " : " + error);
    }

    /**
     * download image url from server
     */
    public class DownloadImageTask extends AsyncTask<Void, Void, Bitmap> {
        InputStream in;
        ImageView imageView;
        int responseCode = -1;

        public DownloadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(Void... string) {
            Bitmap bitmap = null;
            URL url = null;
            try {
                if(!urls.equals("")) {
                    url = new URL(urls);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.connect();
                    responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        in = httpURLConnection.getInputStream();
                        bitmap = BitmapFactory.decodeStream(in);
                        in.close();
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bit) {
            if (bit != null) {
                updateBitmap.setBitmap(bit);
                imageView.setImageBitmap(bit);
            }
            if (bit == null) {
                /*Toast.makeText(context,"Enable to fetch profile image",Toast.LENGTH_SHORT).show();*/
                if (responsedata.getGender().equals("female")) {
                    bit = BitmapFactory.decodeResource(context.getResources(), R.mipmap.user);
                } else {
                    bit = BitmapFactory.decodeResource(context.getResources(), R.mipmap.user);
                }
                profile_pic = bit;
                imageView.setImageBitmap(profile_pic);
            }
            if (getData) {
                callCountryDataUpdate(responsedata);
            }
            dialog.dismiss();
        }
    }
}
