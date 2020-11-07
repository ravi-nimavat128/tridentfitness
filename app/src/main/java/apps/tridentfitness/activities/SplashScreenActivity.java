package apps.tridentfitness.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import apps.tridentfitness.R;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {




                    Intent intent = new Intent(SplashScreenActivity.this, Splacescreen.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);


                //   edt_pin.setVisibility(View.VISIBLE);
                //   btn.setVisibility(View.VISIBLE);
               /* Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();*/

            }
        }, 2000);


    }
}
