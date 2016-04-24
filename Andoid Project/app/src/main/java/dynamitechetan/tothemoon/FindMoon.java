package dynamitechetan.tothemoon;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.michaldrabik.tapbarmenulib.TapBarMenu;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dynamitechetan.tothemoon.Model.Moon;
import dynamitechetan.tothemoon.network.api;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindMoon extends AppCompatActivity implements SensorEventListener {

    @Bind(R.id.tapBarMenu)
    TapBarMenu tapBarMenu;


    public String apilat;
    public String apilong;
    public float yChange;
    public Double latitude;
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
    private Double altVal;
    public String passed_value;
//    private TextView tv_latitude;
//    private TextView tv_longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.findmoon);
        ButterKnife.bind(this);

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


        refresh();


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
                Log.i("TAG", "Item 2 selected");
                break;
            case R.id.item3:
                Log.i("TAG", "Item 3 selected");
                break;
            case R.id.item4:
                Log.i("TAG", "Item 4 selected");
                break;
        }
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
                aziVal = response.body().getAzimuth();
                disVal = response.body().getDistance();
                altVal = response.body().getAltitude();


                Toast.makeText(FindMoon.this, aziVal.toString(),
                        Toast.LENGTH_LONG).show();

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

                } else {
                    Resources res = getResources();
                    light.setImageDrawable(res.getDrawable(R.drawable.off));

                }
                if (pitch <= altVal + 20 && pitch >= altVal - 20) {

                    Resources res = getResources();
                    light_elevation.setImageDrawable(res.getDrawable(R.drawable.on));

                } else {
                    Resources res = getResources();
                    light_elevation.setImageDrawable(res.getDrawable(R.drawable.off));

                }

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
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
