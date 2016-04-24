package dynamitechetan.tothemoon;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.michaldrabik.tapbarmenulib.TapBarMenu;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import dynamitechetan.tothemoon.Model.Moon;
import dynamitechetan.tothemoon.network.api;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindMoon extends AppCompatActivity implements SensorEventListener {

    @Bind(R.id.tapBarMenu)
    TapBarMenu tapBarMenu;
    private Calendar mCalendar;
    private boolean mIsNorthernHemi;
    public  String mooninfo;
    private SimpleDateFormat dateFormat;
    private static final String TAG = "MoonView";
    private static final double MOON_PHASE_LENGTH = 29.530588853;
    private static final String DATE_FORMAT = "EEEE, MMMM d";
    public String apilat;
    public String apilong;
    public float yChange;
    public Double age;
    public Double latitude;
    public         int phaseValue;
    public Double longitude;
    StringBuilder builder = new StringBuilder();
    GPSTracker gps;
    float[] history = new float[2];
    String[] direction = {"NONE", "NONE"};
    private ImageView compassImage;
    private float currentDegree = 0f;
    private SensorManager mSensorManager;
//    private TextView degreeTV;
//    private TextView poleTV;
//    private TextView textView;
    private ImageView light;
    private ImageView light_elevation;
//    private TextView azi;
//    private TextView dis;
//    private TextView alt;
    private Double aziVal;
    private Double disVal;
    private static final String TAG1 = "Find";
    private ProgressDialog progressBar;
    private Double altVal;
    public Double illumination;
    public Double dfs;
    public  String stage;
    public String passed_value;
    private int flag1,flag2,repcount;
    public String Phase,date;
//    private TextView tv_latitude;
//    private TextView tv_longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.findmoon);
        ButterKnife.bind(this);


        mCalendar = Calendar.getInstance();
        mIsNorthernHemi = true;
        dateFormat = new SimpleDateFormat(DATE_FORMAT);
        repcount=1;

        compassImage = (ImageView) findViewById(R.id.compassImageView);
//        degreeTV = (TextView) findViewById(R.id.degree);
//        poleTV = (TextView) findViewById(R.id.pole);
//        textView = (TextView) findViewById(R.id.textView);
        // custom font using typeface class
        Typeface mCustomFont = Typeface.createFromAsset(getAssets(), "fonts/STENCIL.TTF");
//        poleTV.setTypeface(mCustomFont);
        light = (ImageView) findViewById(R.id.light);
//        azi = (TextView) findViewById(R.id.azim);
//        alt = (TextView) findViewById(R.id.alt);
//        dis = (TextView) findViewById(R.id.dis);
        light_elevation = (ImageView) findViewById(R.id.light_elevation);
//        tv_latitude = (TextView) findViewById(R.id.Latitude);
//        tv_longitude = (TextView) findViewById(R.id.longitde);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);


        gps = new GPSTracker(FindMoon.this);

        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            passed_value="lat/"+Double.toString(latitude)+"/long/"+Double.toString(latitude);
            apilat = String.valueOf(latitude);
            apilong = String.valueOf(longitude);
//            tv_latitude.setText("\nLatitude: " + "" + latitude);
//            tv_longitude.setText("\nLongitude: " + "" + longitude);
        } else {
            gps.showSettingsAlert();
        }

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        progressBar = ProgressDialog.show(this, "Asking Moon its Location", "Time for you to make guesses...");
        refresh();
        update(mCalendar,true);

    }

    @OnClick(R.id.tapBarMenu) public void onMenuButtonClick() {
        tapBarMenu.toggle();
    }

    @OnClick({ R.id.item1, R.id.item2, R.id.item3, R.id.item4 }) public void onMenuItemClick(View view) {
        tapBarMenu.close();
        switch (view.getId()) {
            case R.id.item1:
                Log.i("TAG", "Item 1 selected");
                break;
            case R.id.item2:
                Intent intent1 = new Intent(FindMoon.this, NASAExplore.class);
                startActivity(intent1);
                break;
            case R.id.item3:
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.setComponent(ComponentName.unflattenFromString("com.google.android.stardroid"));
//                intent.addCategory(Intent.CATEGORY_LAUNCHER);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);

                Intent intent = getPackageManager().getLaunchIntentForPackage("com.google.android.stardroid");
                if (intent != null) {
                    // We found the activity now start the activity
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    // Bring user to the market or let them choose an app?
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse("market://details?id=" + "com.google.android.stardroid"));
                    startActivity(intent);
                }
                break;
            case R.id.item4:
                Intent intent2 = new Intent(FindMoon.this, Quiz.class);
                startActivity(intent2);
                break;

        }
    }


    private int getPhaseText(int phaseValue) {
        if (phaseValue == 0) {
            return R.string.new_moon;
        } else if (phaseValue > 0 && phaseValue < 7) {
            return R.string.waxing_crescent;
        } else if (phaseValue == 7) {
            return R.string.first_quarter;
        } else if (phaseValue > 7 && phaseValue < 15) {
            return R.string.waxing_gibbous;
        } else if (phaseValue == 15) {
            return R.string.full_moon;
        } else if (phaseValue > 15 && phaseValue < 23) {
            return R.string.waning_gibbous;
        } else if (phaseValue == 23) {
            return R.string.third_quarter;
        } else {
            return R.string.waning_crescent;
        }
    }

    private double computeMoonPhase() {
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH) + 1;
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        // Convert the year into the format expected by the algorithm.
        double transformedYear = year - Math.floor((12 - month) / 10);
        Log.i(TAG, "transformedYear: " + transformedYear);

        // Convert the month into the format expected by the algorithm.
        int transformedMonth = month + 9;
        if (transformedMonth >= 12) {
            transformedMonth = transformedMonth - 12;
        }
        Log.i(TAG, "transformedMonth: " + transformedMonth);

        // Logic to compute moon phase as a fraction between 0 and 1
        double term1 = Math.floor(365.25 * (transformedYear + 4712));
        double term2 = Math.floor(30.6 * transformedMonth + 0.5);
        double term3 = Math.floor(Math.floor((transformedYear / 100) + 49) * 0.75) - 38;

        double intermediate = term1 + term2 + day + 59;
        if (intermediate > 2299160) {
            intermediate = intermediate - term3;
        }
        Log.i(TAG, "intermediate: " + intermediate);

        double normalizedPhase = (intermediate - 2451550.1) / MOON_PHASE_LENGTH;
        normalizedPhase = normalizedPhase - Math.floor(normalizedPhase);
        if (normalizedPhase < 0) {
            normalizedPhase = normalizedPhase + 1;
        }
        Log.i(TAG, "normalizedPhase: " + normalizedPhase);

        // Return the result as a value between 0 and MOON_PHASE_LENGTH
        return normalizedPhase * MOON_PHASE_LENGTH;
    }


    private static final int [] IMAGE_LOOKUP = {
            R.drawable.moon0,
            R.drawable.moon1,
            R.drawable.moon2,
            R.drawable.moon3,
            R.drawable.moon4,
            R.drawable.moon5,
            R.drawable.moon6,
            R.drawable.moon7,
            R.drawable.moon8,
            R.drawable.moon9,
            R.drawable.moon10,
            R.drawable.moon11,
            R.drawable.moon12,
            R.drawable.moon13,
            R.drawable.moon14,
            R.drawable.moon15,
            R.drawable.moon16,
            R.drawable.moon17,
            R.drawable.moon18,
            R.drawable.moon19,
            R.drawable.moon20,
            R.drawable.moon21,
            R.drawable.moon22,
            R.drawable.moon23,
            R.drawable.moon24,
            R.drawable.moon25,
            R.drawable.moon26,
            R.drawable.moon27,
            R.drawable.moon28,
            R.drawable.moon29,
    };



    public void update(Calendar calendar, boolean isNorthernHemi) {
        mCalendar = calendar;
        mIsNorthernHemi = isNorthernHemi;

        double phase = computeMoonPhase();
        Log.i(TAG, "Computed moon phase: " + phase);

         phaseValue = ((int) Math.floor(phase)) % 30;
        Log.i(TAG, "Discrete phase value: " + phaseValue);

        date=  String.valueOf(getPhaseText(phaseValue));
//        mMoonImage.setImageResource(IMAGE_LOOKUP[phaseValue]);
        Phase=(dateFormat.format(mCalendar.getTime()));

    }

    public void refresh() {
        api.Factory.getInstance().getMoon(passed_value).enqueue(new Callback<Moon>() {
            @Override
            public void onResponse(Call<Moon> call, Response<Moon> response) {
//
//
//                alt.setText(String.valueOf(response.body().getAltitude()));
//                dis.setText(String.valueOf(response.body().getDistance()));
//                azi.setText(String.valueOf(response.body().getAzimuth()));
                aziVal = response.body().getAzimuth()+25;
                disVal = response.body().getDistance();
                altVal = response.body().getAltitude()-10;
                illumination = Math.round((response.body().getIllumination()) * 100.0) / 100.0;
                dfs = Math.round((response.body().getDistance()) * 100.0) / 100.0;
                stage = response.body().getStage();
                age = Math.round((response.body().getAge())* 100.0) / 100.0;


//
//                Toast.makeText(FindMoon.this, "Values loaded",
//                        Toast.LENGTH_LONG).show();

                mooninfo  = "Illumination : "+illumination+"\n"+"Age : "+age+"\n"+"Distance From Earth : "+ dfs +"\n"+"Stage : "+stage;
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Moon> call, Throwable t) {
                Log.e("Fail", t.getMessage());
            }
        });


    }


    @Override
    protected void onResume() {

        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
        Sensor accelerometer = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
//        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//
//
//             yChange =  event.values[1];
//
//
//
//        }


        if (sensor.getType() == Sensor.TYPE_ORIENTATION) {


            float degree = Math.round(event.values[0]);

            float pitch = Math.round(-(event.values[1]));

//            degreeTV.setText("" + Float.toString(degree) + "°");
//            textView.setText(String.valueOf(pitch));


            if (aziVal != null && altVal != null) {
                if (degree <= aziVal + 20 && degree >= aziVal - 20) {
//                Toast.makeText(FindMoon.this, aziVal.toString(),
//                        Toast.LENGTH_SHORT).show();

                    Resources res = getResources();
                    light.setImageDrawable(res.getDrawable(R.drawable.on));
                   flag1=1;

                } else {
                    Resources res = getResources();
                    light.setImageDrawable(res.getDrawable(R.drawable.off));
                    flag1=0;
                }
                if (pitch <= altVal + 20 && pitch >= altVal - 20) {

                    Resources res = getResources();
                    flag2=1;
                    light_elevation.setImageDrawable(res.getDrawable(R.drawable.on));

                } else {
                    Resources res = getResources();
                    flag2=0;
                    light_elevation.setImageDrawable(res.getDrawable(R.drawable.off));

                }


            }


            if(flag2==1&&flag1==1&&repcount==1){
//                Toast.makeText(FindMoon.this, "Moon Found",
//                        Toast.LENGTH_LONG).show();


                new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)


                        .setTitleText("You Found the Moon")
                        .setContentText(mooninfo)
                        .setConfirmText("Here's your story")

                        .setCustomImage(IMAGE_LOOKUP[phaseValue])
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {


                            @Override
                    public void onClick(SweetAlertDialog sDialog) {
                                Intent intent = new Intent(FindMoon.this, StroyPage.class);
                                startActivity(intent);
                                sDialog.cancel();
                                repcount=1;
                    }
                })
                .show();


                repcount=0;
            }

            if (degree <= 30 || degree >= 330) {
//                poleTV.setText("N");

            } else if (degree > 30 && degree < 60) {
//                poleTV.setText("NE");
            } else if (degree >= 60 && degree <= 120) {
//                poleTV.setText("E");
            } else if (degree > 120 && degree < 150) {
//                poleTV.setText("SE");
            } else if (degree >= 150 && degree <= 210) {
//                poleTV.setText("S");
            } else if (degree > 210 && degree < 240) {
//                poleTV.setText("SW");
            } else if (degree >= 240 && degree <= 300) {
//                poleTV.setText("W");
            } else if (degree > 300 && degree < 330) {
//                poleTV.setText("NW");
            }

            RotateAnimation rotation = new RotateAnimation(
                    currentDegree, -degree,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);

            rotation.setDuration(210);
            rotation.setFillAfter(true);

            compassImage.startAnimation(rotation);
            currentDegree = -degree;


        }
//
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
