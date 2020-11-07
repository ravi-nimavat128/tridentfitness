package apps.tridentfitness.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import apps.tridentfitness.R;
import apps.tridentfitness.getset.detailpageGetSet;

public class RestActivity extends Activity {

    private long totaltimes = 4000;
    private MediaPlayer mPlayer2;
    private String key;
    private String times;
    private int position;
    private TextToSpeech t1;
    private String name;
    ArrayList<detailpageGetSet> workoutList;
    private String cal;
    private String tot_ex;
    TextView txt_restcount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.WHITE);
        setContentView(R.layout.activity_rest);
        txt_restcount = findViewById(R.id.txt_restcount);
        getintents();
        inite();
        restcountdowntimer();
    }

    private void getintents() {
        Intent intent = getIntent();
        key = intent.getStringExtra("layout");
        times = intent.getStringExtra("time");
        name = intent.getStringExtra("name");
        cal = intent.getStringExtra("cal");
        tot_ex = intent.getStringExtra("tot_ex");
    }

    private void inite() {
        if (key.equals("detail")) {
            workoutList = DetailActivity.detailpageGetSetArrayList;
        } else {
            workoutList = CustomWorkoutItem.selectItemArrayList;
        }
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.ENGLISH);
                }
            }
        });
    }

    private void restcountdowntimer() {
        CountDownTimer mcountdownTimer = new CountDownTimer(totaltimes, 1000) {
            @Override
            public void onTick(long l) {
                totaltimes = l;
                int second = (int) (totaltimes / 1000) % 60;
                // Update UI
                txt_restcount.setText("" + second);
                if (second == 3){
                    ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(500);
                }else if (second == 2){
                    ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(500);
                }
                if (second == 1) {
                    txt_restcount.setText("" + 1);
                    ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(500);
                    final Handler handler = new Handler();
                    final int delay = 50; //milliseconds

                    handler.postDelayed(new Runnable() {
                        public void run() {
                            //do something
                            Intent intent = new Intent(RestActivity.this, WorkoutActivity.class);
                            intent.putExtra("layout", key);
                            intent.putExtra("time", times);
                            intent.putExtra("cal", cal);
                            intent.putExtra("tot_ex", tot_ex);
                            intent.putExtra("name", name);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
//                            handler.postDelayed(this, delay);
                        }
                    }, delay);
                }
            }
            @Override
            public void onFinish() {
            }
        }.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mPlayer2 != null) {
            mPlayer2.stop();
            if (isFinishing()) {
                mPlayer2.stop();
                mPlayer2.release();
            }
        }
    }
}
