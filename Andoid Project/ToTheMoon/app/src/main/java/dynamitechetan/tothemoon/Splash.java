package dynamitechetan.tothemoon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class Splash extends Activity {

    private TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        FullScreencall();
    name = (TextView) findViewById(R.id.name);

        Typeface mCustomFont = Typeface.createFromAsset(getAssets(), "fonts/STENCIL.TTF");
        name.setTypeface(mCustomFont);
        name.setText("RUNTIME ROCKERZ");
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent startActivity = new Intent(Splash.this, FindMoon.class);
//                startActivity(startActivity);
//                finish();
//            }
//
//        }, 5000);


        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                if(isOnline()) {

                Intent startActivity = new Intent(Splash.this, FindMoon.class);
                startActivity(startActivity); }
                else{
                    Intent startActivity = new Intent(Splash.this, OFFLINE.class);
                    startActivity(startActivity);
                }


                finish();
            }

        }, 5000);



    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
    public void FullScreencall() {
        if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if(Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
