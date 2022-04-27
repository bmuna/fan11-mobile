package com.fan.core.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.fan.core.R;
import com.fan.core.model.country.CountryBean;
import com.fan.core.model.country.Responsedatum;
import com.fan.core.model.get_player.GetPlayer;
import com.fan.core.model.telegram.TelegramBean;
import com.fan.core.model.transfer_list.PlayerDatum;
import com.fan.core.model.user.Responsedata;
import com.fan.core.model.user.UserBean;
import com.fan.core.module.Full_Address;
import com.fan.core.module.LandingActivity;
import com.fan.core.module.Personal_Information;
import com.fan.core.module.Profile;
import com.fan.core.module.TutorialPage;
import com.fan.core.module_fragment.MainFragmentActivity;
import com.fan.core.module_fragment.PL;
import com.fan.core.service.NotificationServ;
import com.fan.core.tutorial.Main;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static com.fan.core.util.AppConstants.updateNull;


/**
 * Created by mohit.soni @ 01-Oct-19.
 */

public class AppConstants {
    public static final String TAG = AppConstants.class.getSimpleName();
    public static String ROOT_PACKAGE = "";

    Context context;

    // local variable
    public static String APP_NAME = "Fan 11";
    public static final String SYSTEM_ROOT = Environment.getExternalStorageDirectory() + "/" + APP_NAME + "/";
    public static final String ROOT = Environment.getExternalStorageDirectory() + "/" + APP_NAME + "/log/";
    public static SharedPrefUtility preferences;

    static String yr = "", day = "", month = "";
    static File system_root = new File(SYSTEM_ROOT);
    static File root = new File(ROOT);
    public ProgressDialog progressDialog;

    public static String selectDate = "";
    public static int LOGIN = 101;
    public static int REGISTER = 102;
    public static int FORGET_PASSWORD = 103;
    public static int VERIFY_OTP = 104;
    public static int CHANGE_PASSWORD = 105;
    public static int GET_TABLE_DATA_STATE = 106;
    public static int GET_TABLE_DATA_CITY = 107;
    public static int UPLOAD_PROFILE_CONTENT = 108;
    public static int VERIFY_ACCOUNT = 109;
    public static int API_TELEGRAM = 110;
    public static int LOGIN_WITH_SOCIAL_MEDIA = 111;
    public static int GET_GAMES = 112;
    public static int GET_PLAYERS = 113;
    public static int CREATE_TEAM = 114;

    public static int FIXTURES = 115;
    public static int TABLES = 116;
    public static int POINT_AND_LEAGUES = 117;
    public static int LEAGUE_USERS = 118;
    public static int VIEW_SCORES = 119;
    public static int PLAYED_USERS = 120;
    public static int TRANSFER_LIST = 121;
    public static int MY_BALANCE = 122;
    public static int ADD_BALANCE = 123;
    public static int ADD_COUPON = 123;
    public static int PAYMENT_HISTORY = 124;
    public static int PRIVACY_POLICY = 125;
    public static int TERMS_AND_CONDITIONS = 126;
    public static int MY_REFERRALS = 127;
    public static int JOIN_GAME = 127;
    public static int SEND_OTP_FOR_PAYMENT_ON_AMOLE = 128;
    public static int WITHDRAW = 129;
    public static int GAMES_JOINED_USER = 130;
    public static int VIEW_SCORES_NEW = 131;






    public static HashMap<String, String> language = new HashMap<>();
    public static String CURRENT_LANGUAGE = "English script";
    public static Class<?>[] CLASSES = new Class[]{
            NotificationServ.class
    };

    public static Map<Class<?>, Boolean> SERVICE_CLASS = new HashMap<>();

    public static String TELEGRAM_PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2lKDiZY71HVqcKpG2ojG\n" +
            "N8bv5hgJh4KaDLnOESLDTKnRGH02aTrlb3xqjstskVh6axFgeBRb46FNlHprDAGb\n" +
            "qAnRL4cRi9edrTGAOd/QCQnsn3FzEQP3QxFeGY5669ZoxP8ZMzpsUmRUv2+dG4wJ\n" +
            "hfTAlPmo3U0/rmS9TUX/xglsRhRlDGordhor3ETjFZV7xoc7Z2+xi4US/evogRzN\n" +
            "yXpaySYTF90dZYP2rXSOCx2O7d0cmpedlVYrcLAnPrywIqrhW2lzTQNgKQeI77HA\n" +
            "mtOs3sZdNMSsCBOS4keGXwEuC2CnQUOkJTRltGsbUtxcce0fa4xMK1sjlSgmtsxm\n" +
            "HQIDAQAB\n" +
            "-----END PUBLIC KEY-----";
    public static int TELEGRAM_BOT_ID = 912237569;
    public static String TELEGRAM_NONCE = "fan_11_query_for_login_" + new Random().nextInt(9999);

    public static ArrayList<Responsedatum> country_data = new ArrayList<>();
    public static Responsedatum country = null;

    public static List<com.fan.core.model.state.Responsedatum> state_responsedata = new ArrayList<>();
    public static com.fan.core.model.state.Responsedatum state = null;

    public static List<com.fan.core.model.city.Responsedatum> city_responsedata = new ArrayList<>();
    public static com.fan.core.model.city.Responsedatum city = null;

    public static SharedPrefUtility prefUtility;

    public static ArrayList<com.fan.core.model.get_player.Responsedatum> PLAYERS = new ArrayList<>();

    public static Bitmap profile_pic = null;
    public static Bitmap upload_id = null;
    public static Bitmap upload_signature = null;

    public AppConstants(Context context) {
        this.context = context;
        prefUtility = new SharedPrefUtility(context);
    }

    /**
     * get calender of date and time
     *
     * @return date format
     */
    public DateFormat getCal() {
        /*DateFormat dateFormat =  new SimpleDateFormat("yyyy/MM/dd HH:mm:ss EEEE");
        Log.i(TAG,dateFormat.format(new Date()).split(" ")[2]);*/
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss EEEE", Locale.US);
    }

    /**
     * get date
     *
     * @return date format
     */
    public String[] getDate() {
        return getCal().format(new Date()).split(" ")[0].split("/");
    }

    /**
     * get time
     *
     * @return time format
     */
    public String[] getTime() {
        return getCal().format(new Date()).split(" ")[1].split(":");
    }

    /**
     * get day
     *
     * @return time format
     */
    public String[] getDay() {
        return getCal().format(new Date()).split(" ")[2].split(":");
    }

    /**
     * get month name
     *
     * @param value
     * @return
     */
    public static String getMonth(String value) {
        switch (value) {
            case "01":
                return "Jan";

            case "02":
                return "Feb";

            case "03":
                return "March";

            case "04":
                return "April";

            case "05":
                return "May";

            case "06":
                return "June";

            case "07":
                return "July";

            case "08":
                return "Aug";

            case "09":
                return "Sep";

            case "10":
                return "Oct";

            case "11":
                return "Nov";

            case "12":
                return "Dec";
        }
        return value;
    }

    public static Typeface getPoppinsLight(Activity activity) {
        return Typeface.createFromAsset(activity.getAssets(), "font/poppins_light.ttf");
    }

    /**
     * show date picker and set value to text view
     *
     * @param textView TextView to store value
     * @param activity context requesting for method
     */
    public static void showDatePicker(TextView textView, Activity activity) {
        //get text view
        final TextView view = (TextView) textView;
        String cal = textView.getText().toString();
        int[] c = {0, 0, 0};
        if (!cal.isEmpty()) {
            String[] ca = cal.split("-");
            c[0] = Integer.parseInt(ca[2]);
            int month = Integer.parseInt(ca[1]);
            if (month > 0) {
                c[1] = month - 1;
            } else {
                c[1] = 0;
            }
            c[2] = Integer.parseInt(ca[0]);
        } else {
            c[0] = 1950;
            c[1] = 01;
            c[2] = 01;
        }
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
                int mon = monthOfYear + 1;
                // format date
                selectDate = updateDay(dayOfMonth) + "-" + updateDay((mon)) + "-" + year;
                view.setText(selectDate);
            }
        };
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                listener,
                c[0],
                c[1],
                c[2]
        );
        /*datePickerDialog.setMinDate(calendar);*/
        datePickerDialog.setYearRange(1950, 2020);
        // disable future date
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePickerDialog.setMaxDate(calendar);
        datePickerDialog.show(activity.getFragmentManager(), "datePicker");
        datePickerDialog.setThemeDark(true);
        datePickerDialog.setAccentColor(activity.getResources().getColor(R.color.app_light_yellow));
    }

    /**
     * get date in numeric value
     *
     * @param view
     * @return array
     */
    public String getNumDate(TextView view) {
        String[] date_ = getDate();
        setTag(view, "num");
        return getDay()[0] + "" + "\n" + date_[2] + "/" + date_[1] + "/" + date_[0];
    }

    /**
     * get date in text value
     *
     * @param view
     * @return array
     */
    public String getTextDate(TextView view) {
        String[] date_ = getDate();
        setTag(view, "text");
        return getDay()[0] + "" + "\n" + date_[2] + " " + getMonth(date_[1]) + " " + date_[0].substring(2, 4);
    }

    /**
     * set tag to text view
     *
     * @param view
     * @param value
     */
    public void setTag(TextView view, String value) {
        view.setTag(value);
    }


    /**
     * check if particular permission is granted or not
     *
     * @param activity
     * @param permission
     * @return
     */
    public static boolean checkPermission(Activity activity, String permission) {
        return ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED;
    }

    /**
     * if our activity is running
     *
     * @return status
     */
    /*public static boolean isActivityRunning(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfoList = manager.getRunningTasks(Integer.MAX_VALUE);

        // iterate over activity list
        for (ActivityManager.RunningTaskInfo info : runningTaskInfoList) {
            if (info.topActivity.getPackageName().equals(AppConstants.ROOT_PACKAGE)) {
                return true;
            }
        }
        return false;
    }*/


    /**
     * get user detail
     *
     * @return user
     */
    /*public UserData getUser() {
        return preferences.getPrefData();
    }*/


    /**
     * get progress dialog
     *
     * @param activity context requesting for progress bar
     * @param message  string to display
     * @return progress bar
     */
    public ProgressDialog getProgress(Activity activity, String message) {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage(message);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);

        return progressDialog;
    }

    /**
     * save error report
     *
     * @param tag     text to attach
     * @param strings array of error
     * @param error   actual error
     */
    public void saveReport(String tag, StackTraceElement[] strings, String error) {
        checkDir();
        File dir = new File(ROOT + "/" + tag + "__" + getTodayDate() + "__" + getCurrentTime() + ".log");
        StringBuilder builder = new StringBuilder();
        String report;
        if (root.isDirectory()) {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dir)));
                builder.append("TAG : " + tag);
                builder.append("\n");
                builder.append("---------------Error--------------");
                builder.append("\n");
                builder.append("\n");
                builder.append(error);
                builder.append("\n");
                builder.append("\n");
                builder.append("---------------Detail-------------");
                builder.append("\n");
                for (StackTraceElement element : strings) {
                    builder.append(element);
                    builder.append("\n");
                }
                report = builder.toString();
                bufferedWriter.write(report);
                bufferedWriter.flush();
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * save run time exception to sdcard
     *
     * @param strings error
     */
    public void saveRunCaught(String strings) {
        checkDir();
        File dir = new File(ROOT + "/" + getTodayDate() + "__" + getCurrentTime() + ".txt");
        if (root.isDirectory()) {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dir)));
                bufferedWriter.write(strings);
                bufferedWriter.flush();
                bufferedWriter.close();
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }

    /**
     * get current date
     *
     * @return date string
     */
    public String getTodayDate() {
        final Calendar c = Calendar.getInstance();
        int todayDate = (c.get(Calendar.YEAR) * 10000) +
                ((c.get(Calendar.MONTH) + 1) * 100) +
                (c.get(Calendar.DAY_OF_MONTH));
        Log.w("selectDate:", String.valueOf(todayDate));
        return (String.valueOf(todayDate));
    }

    /**
     * get current date
     *
     * @return date string
     */
    public static String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        return (String.valueOf(dateFormat.format(new Date())));
    }

    /**
     * get current date
     *
     * @return date string
     */
    public static String[] getDateTimeArray() {
        String dateTime = getDateTime();
        return dateTime.split(" ");
    }


    /**
     * get current time
     *
     * @return time string
     */
    public String getCurrentTime() {
        final Calendar c = Calendar.getInstance();
        int currentTime = (c.get(Calendar.HOUR_OF_DAY) * 10000) +
                (c.get(Calendar.MINUTE) * 100) +
                (c.get(Calendar.SECOND));
        Log.w("TIME:", String.valueOf(currentTime));
        return (String.valueOf(currentTime));
    }

    /**
     * check if directory exist or not
     */
    public void checkDir() {
        if (!system_root.exists()) {
            system_root.mkdir();
        }
        if (!root.exists()) {
            root.mkdir();
        }
    }


    /**
     * set data in session
     *
     * @param message user getter setter
     */
   /* public void setPreference(UserData message) {
        preferences.clearPrefData();
        preferences.setPrefData(message);
        preferences.setAccess(true);
    }

    public void setMember(Object message) {
        preferences.setMemberData(message);
    }

    public static Object getMemberData() {
        return preferences.getMemberData();
    }

    public static HashMap<String, String> getMember() {
        HashMap<String, String> map = new HashMap<>();
        Object response = getMemberData();
        Gson gson = new Gson();
        Member member = gson.fromJson((String) response, Member.class);
        if(member!=null){
            for (UserDatum userDatum : member.getMemberData()) {
                map.put(userDatum.getName(), userDatum.getId().toString());
            }
        }
        return map;
    }*/


    /**
     * clear data in session
     */
    public void setClearPreference() {
        preferences.clearPrefData();
    }


    /**
     * go to intent activity
     *
     * @param activity context requesting for method
     * @param toClass  context to where intent is fire
     */
    public void goToIntent(Activity activity, Class<?> toClass) {
        Intent intent = new Intent(activity, toClass);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * get date formatted
     *
     * @return date string
     */
    public static String formateDate() {
        return day + "/" + month + "/" + yr;
    }

    /**
     * log to console
     *
     * @param TAG activity name
     * @param msg message to print
     */
    public static void log(String TAG, String msg) {
        Log.i(TAG, msg);
    }


    /**
     * open camera
     *
     * @param reqCode request code
     */
    public void openImageIntent(int reqCode, Activity activity, Uri outputFileUri) {
        /*final File root = new File(Environment.getExternalStorageDirectory() + File.separator +
                getResources().getString(R.string.app_name) + File.separator);
        // Determine Uri of camera image to save.

        root.mkdirs();
        final String fname = System.currentTimeMillis() + ".jpg";
        final File sdImageMainDirectory = new File(root, fname);
        outputFileUri = Uri.fromFile(sdImageMainDirectory);*/

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = activity.getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }

        // file system.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        // Chooser of file system options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select source");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));
        activity.startActivityForResult(chooserIntent, reqCode);
    }

    /**
     * get image as string from byte
     *
     * @param bmp bitmap
     * @return base 64 of image
     */
    public static String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 20, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }


    /**
     * get byte of base64 string image to set in imageview
     *
     * @param img_str string
     * @return byte
     */
    public static byte[] getByteofBase64Image(String img_str) {
        return Base64.decode(img_str.getBytes(), Base64.DEFAULT);
    }


    /**
     * set image from base64 string
     *
     * @param img       string
     * @param imageView image
     */
    public void setImageFromBase64(String img, ImageView imageView, Drawable drawable) {
        if (img != null) {
            if (!img.equals("")) {
                byte[] img_arr = getByteofBase64Image(img);
                Bitmap bitmap = BitmapFactory.decodeByteArray(img_arr, 0, img_arr.length);
                imageView.setImageBitmap(bitmap);
            } else {
                imageView.setBackground(drawable);
            }
        }
        System.gc();
    }

    /**
     * set image from base64 string
     *
     * @param img       string
     * @param imageView image
     */
    public void setImageFromBase64(String img, ImageView imageView, Drawable drawable,
                                   ProgressBar bar) {
        if (img != null) {
            if (!img.equals("")) {
                byte[] img_arr = getByteofBase64Image(img);
                Bitmap bitmap = BitmapFactory.decodeByteArray(img_arr, 0, img_arr.length);
                imageView.setImageBitmap(bitmap);
            } else {
                imageView.setBackground(drawable);
            }
        }
        bar.setVisibility(View.INVISIBLE);
        System.gc();
    }

    /**
     * set image from base64 string
     *
     * @param img string
     */
    public Bitmap setBitmapImageFromBase64(String img) {
        Bitmap bitmap = null;
        if (img != null) {
            if (!img.equals("")) {
                byte[] img_arr = getByteofBase64Image(img);
                bitmap = BitmapFactory.decodeByteArray(img_arr, 0, img_arr.length);
            }
        }
        System.gc();
        return bitmap;
    }


    /**
     * update day value
     *
     * @param day input day
     * @return updated day value
     */
    public static String updateDay(int day) {
        return Integer.toString(day).length() == 1 ? "0" + Integer.toString(day) : Integer.toString(day);
    }

    public static boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable()
                    && cm.getActiveNetworkInfo().isConnected()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void getCountryJson(Context context) {
        country_data.clear();
        try {
            InputStream in = context.getAssets().open("country.json");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            String string = new String(buffer, "UTF-8");
            in.close();

            Gson gson = new Gson();
            CountryBean bean = gson.fromJson((String) string, CountryBean.class);
            country_data.addAll(bean.getResponsedata());
            log(TAG, bean.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void callCountry(final TextView textView, final Context context, final Object object) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(android.R.layout.list_content);
        dialog.setTitle("Select Country");

        ListView list = (ListView) dialog.findViewById(android.R.id.list);
        ArrayList<String> country_names = new ArrayList<>();
        for (Responsedatum responsedatum : AppConstants.country_data) {
            country_names.add(responsedatum.getName());
        }
        list.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,
                android.R.id.text1, country_names));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppConstants.country = AppConstants.country_data.get(position);
                textView.setText(AppConstants.country.getName());
                if (context.getClass().getSimpleName().equals("Personal_Information")) {
                    ((Personal_Information) object).receiveCountry(AppConstants.country);
                }
                if (context.getClass().getSimpleName().equals("Full_Address")) {
                    ((Full_Address) object).receiveCountry(AppConstants.country);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public static void callGender(final TextView textView, final Context context, final Object object) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(android.R.layout.list_content);
        dialog.setTitle("Select Gender");

        ListView list = (ListView) dialog.findViewById(android.R.id.list);
        ArrayList<String> gender_names = new ArrayList<>();
        gender_names.add("Male");
        gender_names.add("Female");
        list.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,
                android.R.id.text1, gender_names));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppConstants.country = AppConstants.country_data.get(position);
                textView.setText(AppConstants.country.getName());
                if (context.getClass().getSimpleName().equals("Personal_Information")) {
                    ((Personal_Information) object).receiveCountry(AppConstants.country);
                }
                if (context.getClass().getSimpleName().equals("Full_Address")) {
                    ((Full_Address) object).receiveCountry(AppConstants.country);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void callCountry(final EditText textView, final Context context, final Object object) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(android.R.layout.list_content);
        dialog.setTitle("Select Country");

        ListView list = (ListView) dialog.findViewById(android.R.id.list);
        ArrayList<String> country_names = new ArrayList<>();
        for (Responsedatum responsedatum : AppConstants.country_data) {
            country_names.add(responsedatum.getName());
        }
        list.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,
                android.R.id.text1, country_names));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppConstants.country = AppConstants.country_data.get(position);
                textView.setText(AppConstants.country.getName());
                if (context.getClass().getSimpleName().equals("Personal_Information")) {
                    ((Personal_Information) object).receiveCountry(AppConstants.country);
                }
                if (context.getClass().getSimpleName().equals("Full_Address")) {
                    ((Full_Address) object).receiveCountry(AppConstants.country);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * get user data from save
     *
     * @return
     */
//    public static Responsedata getUserSaved(Context context) {
//        if (prefUtility == null) {
//            prefUtility = new SharedPrefUtility(context);
//        }if (prefUtility != null) {
//            prefUtility.getUserdata();
//        }
//        return prefUtility.getUserdata();
//    }
//
//    public static void clearPref(Context context) {
//        if (prefUtility == null) {
//            prefUtility = new SharedPrefUtility(context);
//        }
//        prefUtility.clearPrefData();
//    }
    public static Responsedata getUserSaved(Context context) {
        if (prefUtility == null) {
            prefUtility = new SharedPrefUtility(context);
//        }else {
//                prefUtility.getUserdata();

        }
        return prefUtility.getUserdata();

    }

    public static void clearPref(Context context) {
        if (prefUtility == null) {
            prefUtility = new SharedPrefUtility(context);
        }
        prefUtility.clearPrefData();
    }
    public static Bitmap scaleBitmap(Uri uri, Activity activity) {
        Bitmap bitmap = ScalingUtilities.decodeResource(activity, uri, 800, 800, ScalingUtilities.ScalingLogic.FIT);
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return ScalingUtilities.createScaledBitmap(bitmap, 200, 200, ScalingUtilities.ScalingLogic.FIT);
    }

    public static void callLanguage(final Context context, final Activity activity, final Class<?> clas,
                                    final Resources resources) {
        if (language.size() == 0) {
            initLanguage();
        }
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(android.R.layout.list_content);

        ListView list = (ListView) dialog.findViewById(android.R.id.list);
        Set<String> stringCollection = language.keySet();
        final ArrayList<String> language_names = new ArrayList<>();
        language_names.addAll(stringCollection);
        list.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1
                , android.R.id.text1, language_names));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String locale = language.get(language_names.get(position));
                CURRENT_LANGUAGE = language_names.get(position) + " script";
                Locale myLocale = new Locale(locale);
                DisplayMetrics dm = resources.getDisplayMetrics();
                Configuration conf = resources.getConfiguration();
                conf.locale = myLocale;
                resources.updateConfiguration(conf, dm);
                dialog.dismiss();
                Intent refresh = new Intent(context, clas);
                activity.finish();
                context.startActivity(refresh);
            }
        });
        dialog.show();
    }

    public static void callLanguage(final Context context, final Activity activity, final Class<?> clas,
                                    final Resources resources,int position) {
        if (language.size() == 0) {
            initLanguage();
        }
        Set<String> stringCollection = language.keySet();
        final ArrayList<String> language_names = new ArrayList<>();
        language_names.addAll(stringCollection);
        String locale = language.get(language_names.get(position));
        CURRENT_LANGUAGE = language_names.get(position) + " script";
        Locale myLocale = new Locale(locale);
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration conf = resources.getConfiguration();
        conf.locale = myLocale;
        resources.updateConfiguration(conf, dm);
        Intent refresh = new Intent(context, clas);
        activity.finish();
        context.startActivity(refresh);
    }

    public static void startTutorial(Context context){
        Intent i = new Intent(context, Main.class);
        context.startActivity(i);
    }

    public static void initLanguage() {
        language.put("Amharic", "am");
        language.put("English", "en_US");
    }


    /**
     * check if service is running or not
     *
     * @return state
     */
    public static void isServiceRunning(Context context, Class<?>[] cls) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (Class<?> clas : cls) {
            List<ActivityManager.RunningServiceInfo> serviceInfos = manager.getRunningServices(Integer.MAX_VALUE);
            if (serviceInfos.size() > 0) {
                for (int i = 0; i < serviceInfos.size(); i++) {
                    if (clas.getName().equals(serviceInfos.get(i).service.getClassName())) {
                        SERVICE_CLASS.put(clas, true);
                        continue;
                    } else {
                        SERVICE_CLASS.put(clas, false);
                    }
                }
            } else {
                SERVICE_CLASS.put(clas, false);
            }
        }
    }

    public static void updateNull(Responsedata responsedata) {
        if (responsedata.getId() == null) responsedata.setId("");
        if (responsedata.getUserType() == null) responsedata.setUserType("");
        if (responsedata.getFbId() == null) responsedata.setFbId("");
        if (responsedata.getGoogleId() == null) responsedata.setGoogleId("");
        if (responsedata.getEmail() == null) responsedata.setEmail("");
        if (responsedata.getPassword() == null) responsedata.setPassword("");
        if (responsedata.getFirstName() == null) responsedata.setFirstName("");
        if (responsedata.getLastName() == null) responsedata.setLastName("");
        if (responsedata.getMobileNo() == null) responsedata.setMobileNo("");
        if (responsedata.getProfileImage() == null) responsedata.setProfileImage("");
        if (responsedata.getStatus() == null) responsedata.setStatus("");
        if (responsedata.getCreatedAt() == null) responsedata.setCreatedAt("");
        if (responsedata.getUpdatedAt() == null) responsedata.setUpdatedAt("");
        if (responsedata.getDob() == null) responsedata.setDob("");
        if (responsedata.getAddress() == null) responsedata.setAddress("");
        if (responsedata.getCity() == null) responsedata.setCity("");
        if (responsedata.getCity_name() == null) responsedata.setCity_name("");
        if (responsedata.getState() == null) responsedata.setState("");
        if (responsedata.getState_name() == null) responsedata.setState_name("");
        if (responsedata.getCountry() == null) responsedata.setCountry("");
        if (responsedata.getCountry_name() == null) responsedata.setCountry_name("");
        if (responsedata.getPostalCode() == null) responsedata.setPostalCode("");
        if (responsedata.getNotification() == null) responsedata.setNotification("");
        if (responsedata.getUploadId() == null) responsedata.setUploadId("");
        if (responsedata.getUploadSignature() == null) responsedata.setUploadSignature("");
        if (responsedata.getErrorMessage() == null) responsedata.setErrorMessage("");
        if (responsedata.getProfile_local_path() == null) responsedata.setProfile_local_path("");
        if (responsedata.getUpload_id_local() == null) responsedata.setUpload_id_local("");
        if (responsedata.getMyBalance() == null) responsedata.setMyBalance("0.0");
        if (responsedata.getUpload_signature_local() == null)
            responsedata.setUpload_signature_local("");
        responsedata.setRememberMe(true);
    }

    public static void updateNull(com.fan.core.model.update.Responsedata responsedata) {
        if (responsedata.getId() == null) responsedata.setId("");
        if (responsedata.getUserType() == null) responsedata.setUserType("");
        if (responsedata.getFbId() == null) responsedata.setFbId("");
        if (responsedata.getGoogleId() == null) responsedata.setGoogleId("");
        if (responsedata.getEmail() == null) responsedata.setEmail("");
        if (responsedata.getPassword() == null) responsedata.setPassword("");
        if (responsedata.getFirstName() == null) responsedata.setFirstName("");
        if (responsedata.getLastName() == null) responsedata.setLastName("");
        if (responsedata.getMobileNo() == null) responsedata.setMobileNo("");
        if (responsedata.getProfileImage() == null) responsedata.setProfileImage("");
        if (responsedata.getStatus() == null) responsedata.setStatus("");
        if (responsedata.getCreatedAt() == null) responsedata.setCreatedAt("");
        if (responsedata.getUpdatedAt() == null) responsedata.setUpdatedAt("");
        if (responsedata.getDob() == null) responsedata.setDob("");
        if (responsedata.getAddress() == null) responsedata.setAddress("");
        if (responsedata.getCity() == null) responsedata.setCity("");
        if (responsedata.getCity_name() == null) responsedata.setCity_name("");
        if (responsedata.getState() == null) responsedata.setState("");
        if (responsedata.getState_name() == null) responsedata.setState_name("");
        if (responsedata.getCountry() == null) responsedata.setCountry("");
        if (responsedata.getCountry_name() == null) responsedata.setCountry_name("");
        if (responsedata.getPostalCode() == null) responsedata.setPostalCode("");
        if (responsedata.getNotification() == null) responsedata.setNotification("");
        if (responsedata.getUploadId() == null) responsedata.setUploadId("");
        if (responsedata.getUploadSignature() == null) responsedata.setUploadSignature("");
        if (responsedata.getErrorMessage() == null) responsedata.setErrorMessage("");
        if (responsedata.getProfile_local_path() == null) responsedata.setProfile_local_path("");
        if (responsedata.getUpload_id_local() == null) responsedata.setUpload_id_local("");
        if (responsedata.getMyBalance() == null) responsedata.setMyBalance("0.0");
        if (responsedata.getUpload_signature_local() == null)
            responsedata.setUpload_signature_local("");
        responsedata.setRememberMe(true);
    }

    public static ArrayList<String> getDummyArrayList() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        arrayList.add("two");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("five");
        arrayList.add("six");
        arrayList.add("seven");
        arrayList.add("eight");
        arrayList.add("nine");
        arrayList.add("ten");
        arrayList.add("eleven");
        arrayList.add("twelve");
        arrayList.add("");
        arrayList.add("fourteen");
        arrayList.add("fifteen");
        arrayList.add("");
        return arrayList;
    }

    public static ArrayList<String> getDummyArrayList2() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("sixteen");
        arrayList.add("fifteen");
        arrayList.add("fourteen");
        arrayList.add("thirteen");
        arrayList.add("twelve");
        arrayList.add("eleven");
        arrayList.add("ten");
        arrayList.add("nine");
        arrayList.add("eight");
        arrayList.add("seven");
        arrayList.add("six");
        arrayList.add("five");
        arrayList.add("four");
        arrayList.add("three");
        arrayList.add("two");
        arrayList.add("one");
        return arrayList;
    }
}
