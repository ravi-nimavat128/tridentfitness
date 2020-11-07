package apps.tridentfitness.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daasuu.ahp.AnimateHorizontalProgressBar;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;
import apps.tridentfitness.R;
import apps.tridentfitness.getset.detailpageGetSet;
import apps.tridentfitness.utilHelper.SPmanager;

import static android.speech.tts.TextToSpeech.QUEUE_ADD;

public class WorkoutActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "WorkoutActivity";
    private AnimateHorizontalProgressBar progressbar;
    private CircularProgressIndicator pgb_progress2;
    int i = 0;
    boolean ismute;
    private TextView txt_progress;
    private TextView txt_progressCount;
    private ImageView btn_next, mute_unmute, btn_play, btn_previous, btn_pause;
    boolean isTimerunning = true;
    long totalCount;
    long interval;
    private long totaltimes, totaltimes1;
    private CountDownTimer mcountdownTimer;
    private CountDownTimer mprogresscountdownTimer;
    boolean resumed = true;
    private boolean isTimerunnings = true;
    private Chronometer chronometer;
    private long pauseOffset;
    ArrayList<detailpageGetSet> workoutList;
    private int position;
    private int nextPos;
    private TextView txt_jumping, txt_nextexercise;
    private Timer timer;
    private MediaPlayer mPlayer2;
    private Handler handler;
    String key;
    String time;
    private long pausetime = 4000;
    private long rest_total = 6000;
    private CountDownTimer mcountdownTimers;
    private TextToSpeech t1;
    private String times, cal, tot_ex, name;
    private int second;
    private TextView txt_nxt;
    private int sum;
    private int seconda;
    private String circuit;
    int circuitnumber;
    int count = 10;
    private int proPos;
    private int progressa = 0;
    private String cycle;
    boolean rest, exercise, endcyclerest;
    boolean isTime = true;
    boolean isrun = true;
    private String rest_time;
    int attempts = 1;
    private CountDownTimer countDownTimer;
    private CountDownTimer restcountDownTimer;
    private CountDownTimer endrestcountDownTimer;
    private CountDownTimer pausecoundowntimer;
    boolean isclicked = true, isguiden = true;
    private ImageView img_info, icon_back, speak_icon, img;
    private int secondo;
    private int sec = 1;
    private int EndResttime = 30000;
    private int seconde;
    AdView mAdView;
    AdRequest adRequest;
    private RelativeLayout rel_play;
    private RelativeLayout rel_pause;
    private String infokey = "0";

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.WHITE);
        setContentView(R.layout.activity_workout);
        Adshow();
        getIntens();
        if (key.equals("detail")) {
            workoutList = DetailActivity.detailpageGetSetArrayList;
            cycle = String.valueOf(circuitnumber);
            rest_time = String.valueOf(count);
        } else {
            workoutList = CustomWorkoutItem.selectItemArrayList;
            rest_time = workoutList.get(0).getResttime();
            cycle = workoutList.get(0).getCycle();
        }
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.ENGLISH);
                }
            }
        });
        init();
        Log.e("test", "onFinish: " + position);
        Log.e("test", "workoutlist" + workoutList.size());
        time = workoutList.get(position).getTime().replaceAll("\\s", "");
        restcountdowntimer(Long.parseLong(rest_time));
    }
    private void Adshow() {
        if (getResources().getString(R.string.show_admmob_ads).equals("yes")) {
            adRequest = new AdRequest.Builder().build();
            mAdView = (AdView) findViewById(R.id.adView);
            mAdView.loadAd(adRequest);

            mAdView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    mAdView.setVisibility(View.VISIBLE);
                    // Code to be executed when an ad finishes loading.
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    mAdView.setVisibility(View.GONE);
                    Log.e("Home", "onAdFailedToLoad: " + errorCode);
                    // Code to be executed when an ad request fails.
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                }

                @Override
                public void onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                }

                @Override
                public void onAdClosed() {
                    // Code to be executed when the user is about to return
                    // to the app after tapping on an ad.
                }
            });
        }
    }
    private void getIntens() {
        Intent intent = getIntent();
        key = intent.getStringExtra("layout");
        times = intent.getStringExtra("time");
        name = intent.getStringExtra("name");
        cal = intent.getStringExtra("cal");
        tot_ex = intent.getStringExtra("tot_ex");
        circuit = SPmanager.getPreference(this, "circut");

        if (circuit != null) {
            if (circuit.equals(" ")) {
                circuitnumber = 1;
            } else {
                circuitnumber = Integer.parseInt(circuit);
            }

        } else {
            circuitnumber = 1;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

        if (infokey.equals("1")) {
            chronometer.start();
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            if (rest == true) {
                restcountdowntimer(Long.parseLong(String.valueOf(secondo)) - Long.parseLong(String.valueOf(sec)));
            }
            if (exercise == true) {
                startcountdowmTimer(Long.parseLong(String.valueOf(second)) - Long.parseLong(String.valueOf(sec)));
            }
            if (endcyclerest == true) {
                EndCycleRestCountdown(totaltimes1 - Long.parseLong(String.valueOf(sec)));
            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();

        if (infokey.equals("1")) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            if (rest == true) {
                restcountDownTimer.cancel();
            }
            if (exercise == true) {
                mcountdownTimer.cancel();
            }
            if (endcyclerest == true) {
                endrestcountDownTimer.cancel();
            }
        }
    }
    private void init() {
        progressbar = findViewById(R.id.progressbar);
        img = findViewById(R.id.img);
        icon_back = findViewById(R.id.icon_back);
        pgb_progress2 = findViewById(R.id.pgb_progress2);
        img_info = findViewById(R.id.img_info);
        btn_next = findViewById(R.id.btn_next);
        btn_previous = findViewById(R.id.btn_previous);
        txt_progress = findViewById(R.id.txt_progress);
        speak_icon = findViewById(R.id.speak_icon);
        txt_progressCount = findViewById(R.id.txt_progressCount);
        txt_jumping = findViewById(R.id.txt_jumping);
        txt_nextexercise = findViewById(R.id.txt_nextexercise);
        chronometer = findViewById(R.id.chronometer);
        txt_nxt = findViewById(R.id.txt_nxt);
        mute_unmute = findViewById(R.id.mute_unmute);
        rel_play = findViewById(R.id.rel_play);
        btn_play = findViewById(R.id.btn_play);
        btn_pause = findViewById(R.id.btn_pause);
        btn_play.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        btn_previous.setOnClickListener(this);
        speak_icon.setOnClickListener(this);
        mute_unmute.setOnClickListener(this);
        img_info.setOnClickListener(this);
        icon_back.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        progressbar.setProgress(0);
        handler = new Handler();
        if (isTimerunning) {
            isTimerunning = false;
            chronometer.start();
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
        } else {
            isTimerunning = true;
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
        }
        if (SPmanager.getGuidence(WorkoutActivity.this)) {
            speak_icon.setImageDrawable(getResources().getDrawable(R.drawable.speak_icon));
        } else {
            speak_icon.setImageDrawable(getResources().getDrawable(R.drawable.speak_no));
        }
        if (SPmanager.getCountdown(WorkoutActivity.this)) {
            mute_unmute.setImageDrawable(getResources().getDrawable(R.drawable.volume));
        } else {
            mute_unmute.setImageDrawable(getResources().getDrawable(R.drawable.volume_no));
        }
        for (int j = 0; j < workoutList.size(); j++) {
            sum += Integer.parseInt(workoutList.get(j).getTime().replaceAll("\\s", ""));
        }
        if (key.equals("detail")) {
            sum = (sum + (workoutList.size() * 10)) * Integer.parseInt(cycle);
        } else {
            sum = (sum + (workoutList.size() * Integer.parseInt(rest_time))) * Integer.parseInt(cycle);
        }
        startProgressTimer(sum);
    }
    private void restcountdowntimer(final Long rest_time1) {
        btn_play.setClickable(true);
        btn_play.setEnabled(true);

        MediaPlayer mp = new MediaPlayer();
        try {
            mp = MediaPlayer.create(WorkoutActivity.this, R.raw.sys_dong);
            mp.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MediaPlayer mp1 = new MediaPlayer();
        try {
            mp1 = MediaPlayer.create(WorkoutActivity.this, R.raw.sys_dong_end);
            mp1.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MediaPlayer mp2 = new MediaPlayer();
        try {
            mp2 = MediaPlayer.create(WorkoutActivity.this, R.raw.sys_dong_end);
            mp2.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MediaPlayer mp3 = new MediaPlayer();
        try {
            mp3 = MediaPlayer.create(WorkoutActivity.this, R.raw.sys_dong_end);
            mp3.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MediaPlayer mp4 = new MediaPlayer();
        try {
            //you can change the path, here path is external directory(e.g. sdcard) /Music/maine.mp3
            mp4 = MediaPlayer.create(WorkoutActivity.this, R.raw.sys_ding);
            mp4.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            txt_jumping.setText(workoutList.get(position).getName());
            Glide.with(this).asGif().load(workoutList.get(position).getGif()).into(img);
            txt_nextexercise.setText("Get Ready");
            txt_nxt.setVisibility(View.GONE);
            Log.e(TAG, "restcountdowntimer: " + workoutList.get(position).getName());
        } catch (Exception e) {
            Log.e(TAG, "startcountdowmTimer: " + e.getMessage());
            txt_nextexercise.setText("Last");
            txt_nxt.setVisibility(View.GONE);
        }
        final MediaPlayer finalMp = mp;
        final MediaPlayer finalMp1 = mp1;
        final MediaPlayer finalMp2 = mp2;
        final MediaPlayer finalMp3 = mp3;
        final MediaPlayer finalMp4 = mp4;

        restcountDownTimer = new CountDownTimer((rest_time1 * 1000) + 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                rest = true;
                exercise = false;
                endcyclerest = false;
                totaltimes = millisUntilFinished;
                secondo = (int) (totaltimes / 1000) % 60;
                txt_progress.setText(String.valueOf((secondo)));
                finalMp.start();
//                // Update UI

                if (secondo == 10 && SPmanager.getCountdown(WorkoutActivity.this)) {
                    finalMp4.start();
                }
                int wrest;
                if (Integer.parseInt(rest_time) <= 6) {
                    wrest = Integer.parseInt(String.valueOf(rest_time)) - 1;
                } else {
                    wrest = Integer.parseInt(String.valueOf(rest_time)) - 2;
                }
                if (secondo == (wrest)) {
                    if (SPmanager.getGuidence(WorkoutActivity.this)) {
                        if (position == 0) {
                            t1.speak("Get Ready" + " " + "NextStep" + " " + workoutList.get(position).getName(), QUEUE_ADD, null);
                        } else {
                            t1.speak("Rest" + " " + "NextStep" + " " + workoutList.get(position).getName(), QUEUE_ADD, null);
                        }
                    }

                }
                Log.e("TestHello", time + "totalTimes" + totaltimes);
                if (SPmanager.getGuidence(WorkoutActivity.this) && SPmanager.getCountdown(WorkoutActivity.this)) {
                    if (secondo == 3) {
                        finalMp1.start();
                        if (finalMp.isPlaying()) {
                            finalMp.stop();
                        }
                        t1.speak("Three", QUEUE_ADD, null);
                    } else if (secondo == 2) {
                        finalMp2.start();
                        t1.speak("Two", QUEUE_ADD, null);
                        if (finalMp.isPlaying()) {
                            finalMp.stop();
                        }
                    } else if (secondo == 1) {
                        finalMp3.start();
                        btn_play.setClickable(false);
                        btn_play.setEnabled(false);
                        t1.speak("One", QUEUE_ADD, null);
                        if (finalMp.isPlaying()) {
                            finalMp.stop();
                        }
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                restcountDownTimer.cancel();
                                restcountDownTimer.onFinish();
                            }
                        }, 800);
                    }
                } else if (!SPmanager.getGuidence(WorkoutActivity.this) && !SPmanager.getCountdown(WorkoutActivity.this)) {
                    if (secondo == 3) {
                        if (finalMp.isPlaying()) {
                            finalMp.stop();
                        }
                    } else if (secondo == 2) {
                        if (finalMp.isPlaying()) {
                            finalMp.stop();
                        }
                    } else if (secondo == 1) {
                        btn_play.setClickable(false);
                        btn_play.setEnabled(false);
                        if (finalMp.isPlaying()) {
                            finalMp.stop();
                        }
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                restcountDownTimer.cancel();
                                restcountDownTimer.onFinish();

                            }
                        }, 800);
                    }
                } else if (!SPmanager.getGuidence(WorkoutActivity.this)) {
                    finalMp.start();
                    if (secondo == 3) {
                        finalMp1.start();
                        if (finalMp.isPlaying()) {
                            finalMp.stop();
                        }
                    } else if (secondo == 2) {
                        finalMp2.start();
                        if (finalMp.isPlaying()) {
                            finalMp.stop();
                        }
                    } else if (secondo == 1) {
                        finalMp3.start();
                        btn_play.setClickable(false);
                        btn_play.setEnabled(false);
                        if (finalMp.isPlaying()) {
                            finalMp.stop();
                        }
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                restcountDownTimer.cancel();
                                restcountDownTimer.onFinish();
                            }
                        }, 800);
                    }
                } else if (!SPmanager.getCountdown(WorkoutActivity.this)) {
                    if (secondo == 3) {
                        if (finalMp.isPlaying()) {
                            finalMp.stop();
                        }
                        t1.speak("Three", QUEUE_ADD, null);
                    } else if (secondo == 2) {
                        t1.speak("Two", QUEUE_ADD, null);
                        if (finalMp.isPlaying()) {
                            finalMp.stop();
                        }
                    } else if (secondo == 1) {
                        btn_play.setClickable(false);
                        btn_play.setEnabled(false);
                        t1.speak("One", QUEUE_ADD, null);
                        if (finalMp.isPlaying()) {
                            finalMp.stop();
                        }
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                restcountDownTimer.cancel();
                                restcountDownTimer.onFinish();

                            }
                        }, 800);
                    }
                }
                pgb_progress2.setMaxProgress(Integer.parseInt(rest_time));
                pgb_progress2.setCurrentProgress(pgb_progress2.getMaxProgress() - (secondo - 1));
            }

            @Override
            public void onFinish() {
                rest = false;
                exercise = true;
                endcyclerest = true;
                MediaPlayer mp = new MediaPlayer();

                if (SPmanager.getGuidence(WorkoutActivity.this)) {
                    t1.speak("begins", QUEUE_ADD, null);
                }
                final Handler handler = new Handler();
                final MediaPlayer finalMp1 = mp;
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if (SPmanager.getGuidence(WorkoutActivity.this)) {
                            t1.speak(workoutList.get(position).getName(), QUEUE_ADD, null);
                        }
                    }
                }, 1000);
                finalMp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();

                    }
                });
                try {
                    if (position < workoutList.size() * Integer.parseInt(cycle)) {

                        time = workoutList.get(position).getTime().replaceAll("\\s", "");
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                startcountdowmTimer(Long.parseLong(time));
                            }
                        }, 800);

                    }
                } catch (Exception e) {
                    e.getMessage();
                    time = workoutList.get(position).getTime().replaceAll("\\s", "");
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            startcountdowmTimer(Long.parseLong(time));
                        }
                    }, 800);
                }
            }

        };
        restcountDownTimer.start();
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_play:
                btn_play.setVisibility(View.GONE);
                btn_pause.setVisibility(View.VISIBLE);
                btn_next.setClickable(false);
                btn_previous.setClickable(false);
                chronometer.stop();
                pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();

                if (rest == true) {
                    restcountDownTimer.cancel();
                }
                if (exercise == true) {
                    mcountdownTimer.cancel();
                }
                if (endcyclerest == true) {
                    endrestcountDownTimer.cancel();
                }
                break;
            case R.id.btn_pause:

                if (SPmanager.getGuidence(WorkoutActivity.this)) {
                    t1.speak("Start In", QUEUE_ADD, null);
                }
                pausecoundowntimer();
                break;
            case R.id.btn_next:
                btn_play.setVisibility(View.VISIBLE);
                btn_pause.setVisibility(View.GONE);
                proPos = position;
                position++;
                nextPos++;

                if (mcountdownTimer != null) {
                    mcountdownTimer.cancel();
                }
                isclicked = true;
                btn_previous.setClickable(true);

                try {
                    progressa += Integer.parseInt(time) + Integer.parseInt(rest_time);
                    if (position >= workoutList.size() && attempts != Integer.parseInt(cycle)) {
                        position = 0;
                        attempts++;
                        if (mcountdownTimer != null) {
                            mcountdownTimer.cancel();
                        }
                        if (restcountDownTimer != null) {
                            restcountDownTimer.cancel();
                        }
                        restcountdowntimer(Long.parseLong(rest_time));
                        if (mprogresscountdownTimer != null) {
                            mprogresscountdownTimer.cancel();
                        }
                        progressbar.setProgress(progressa);
                        startProgressTimer(sum - Integer.parseInt(rest_time));
                    } else if (position == workoutList.size() && attempts == Integer.parseInt(cycle)) {
                        btn_next.setClickable(false);
                        position = workoutList.size() - 1;
                        mcountdownTimer.cancel();
                        restcountDownTimer.cancel();
                        restcountdowntimer(Long.parseLong(rest_time));
                        mprogresscountdownTimer.cancel();
                    } else if (position >= workoutList.size() && attempts == Integer.parseInt(cycle)) {
                        btn_next.setClickable(false);
                        mcountdownTimer.cancel();
                        restcountDownTimer.cancel();
                        restcountdowntimer(Long.parseLong(rest_time));
                    } else if (position < workoutList.size()) {
                        time = workoutList.get(position).getTime().replaceAll("\\s", "");
                        if (mcountdownTimer != null) {
                            mcountdownTimer.cancel();
                        }
                        restcountDownTimer.cancel();
                        restcountdowntimer(Long.parseLong(rest_time));
                        mprogresscountdownTimer.cancel();
                        progressbar.setProgress(progressa);
                        startProgressTimer(sum - Integer.parseInt(rest_time));
                    }
                } catch (Exception e) {

                    Log.e(TAG, "onClick: ]" + e.getMessage());
                    time = workoutList.get(workoutList.size() - 1).getTime().replaceAll("\\s", "");
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }
                    restcountDownTimer.cancel();
                    startcountdowmTimer(Long.parseLong(time));
                    mprogresscountdownTimer.cancel();
                    progressbar.setProgress(progressa);
                    startProgressTimer(sum - Integer.parseInt(rest_time));
                    Log.e(TAG, "onClick: " + e.getMessage());

                }

                break;
            case R.id.btn_previous:
                btn_play.setVisibility(View.VISIBLE);
                btn_pause.setVisibility(View.GONE);
                position--;
                nextPos--;
                btn_next.setClickable(true);
                btn_previous.setClickable(true);

                try {
                    if (mcountdownTimer != null) {
                        mcountdownTimer.cancel();
                    }
                    time = workoutList.get(position).getTime().replaceAll("\\s", "");
                    progressa -= Integer.parseInt(time) + Integer.parseInt(rest_time);
                    if (position >= workoutList.size()) {
                        btn_previous.setClickable(true);
                        position = workoutList.size() - 1;
                        time = workoutList.get(position).getTime().replaceAll("\\s", "");
                        mcountdownTimer.cancel();
                        restcountDownTimer.cancel();
                        restcountdowntimer(Long.parseLong(rest_time));
                        mprogresscountdownTimer.cancel();
                        progressbar.setProgress(progressa);
                        startProgressTimer(sum - Integer.parseInt(rest_time));
                    } else if (position <= 0 && attempts == 1) {
                        btn_previous.setClickable(false);
                        position = 0;
                        progressa = 0;
                        time = workoutList.get(position).getTime().replaceAll("\\s", "");
                        if (mcountdownTimer != null) {
                            mcountdownTimer.cancel();
                        }
                        restcountDownTimer.cancel();
                        restcountdowntimer(Long.parseLong(rest_time));
                        mprogresscountdownTimer.cancel();
                        progressbar.setProgress(progressa);
                        startProgressTimer(sum - Integer.parseInt(rest_time));
                    } else if (position <= 0 && attempts != 1) {
                        btn_previous.setClickable(true);
                        position = workoutList.size() - 1;
                        time = workoutList.get(position).getTime().replaceAll("\\s", "");
                        mcountdownTimer.cancel();
                        restcountDownTimer.cancel();
                        restcountdowntimer(Long.parseLong(rest_time));
                        attempts--;
                    } else {
                        btn_previous.setClickable(true);
                        time = workoutList.get(position).getTime().replaceAll("\\s", "");
                        progressa -= Integer.parseInt(time) - Integer.parseInt(rest_time);
                        if (mcountdownTimer != null) {
                            mcountdownTimer.cancel();
                        }
                        restcountDownTimer.cancel();
                        restcountdowntimer(Long.parseLong(rest_time));
                        mprogresscountdownTimer.cancel();
                        progressbar.setProgress(progressa);
                        startProgressTimer(sum - Integer.parseInt(rest_time));
                    }
                } catch (Exception e) {
                    startcountdowmTimer(Long.parseLong(time));
                    Log.e(TAG, "onClick: " + e.getMessage());
                }
                break;
            case R.id.mute_unmute:

                if (ismute) {
                    SPmanager.setCountdown(WorkoutActivity.this, false);
                    mute_unmute.setImageDrawable(getResources().getDrawable(R.drawable.volume_no));
                    ismute = false;
                } else {
                    SPmanager.setCountdown(WorkoutActivity.this, true);
                    mute_unmute.setImageDrawable(getResources().getDrawable(R.drawable.volume));
                    ismute = true;
                }
                break;
            case R.id.img_info:

                Intent intent = new Intent(WorkoutActivity.this, ExcerciseDetail.class);
                intent.putExtra("id", workoutList.get(position).getId());
                intent.putExtra("url", workoutList.get(position).getUrl());
                infokey = "1";
                onPause();
                startActivity(intent);
                break;
            case R.id.speak_icon:

                if (isguiden) {
                    SPmanager.setGuidence(WorkoutActivity.this, false);
                    speak_icon.setImageDrawable(getResources().getDrawable(R.drawable.speak_no));
                    isguiden = false;
                } else {
                    SPmanager.setGuidence(WorkoutActivity.this, true);
                    speak_icon.setImageDrawable(getResources().getDrawable(R.drawable.speak_icon));
                    isguiden = true;
                }
                break;
            case R.id.icon_back:
                onBackPressed();
                break;
        }
    }
    private void pausecoundowntimer() {
        pausecoundowntimer = new CountDownTimer(pausetime, 999) {
            @Override
            public void onTick(long l) {
                totaltimes = l;
                int second = (int) (totaltimes / 1000) % 60;
                txt_progress.setText("" + second);

                if (SPmanager.getGuidence(WorkoutActivity.this)) {
                    if (second == 3) {
                        t1.speak("Three", QUEUE_ADD, null);
                    } else if (second == 2) {
                        t1.speak("two", QUEUE_ADD, null);
                    } else if (second == 1) {
                        t1.speak("one", QUEUE_ADD, null);
                    }
                }
            }
            @Override
            public void onFinish() {
                pausecoundowntimer.cancel();
                btn_play.setVisibility(View.VISIBLE);
                btn_pause.setVisibility(View.GONE);
                btn_previous.setClickable(true);
                btn_next.setClickable(true);
                chronometer.start();
                chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);

                if (rest == true) {
                    restcountdowntimer(Long.parseLong(String.valueOf(secondo)) - Long.parseLong(String.valueOf(sec)));
                }
                if (exercise == true) {
                    startcountdowmTimer(Long.parseLong(String.valueOf(second)) - Long.parseLong(String.valueOf(sec)));
                }
                if (endcyclerest == true) {
                    EndCycleRestCountdown(totaltimes1 - Long.parseLong(String.valueOf(sec)));
                }
            }
        }.start();
    }
    private void startcountdowmTimer(Long timer) {
        btn_play.setClickable(true);
        btn_play.setEnabled(true);
        nextPos = position + 1;

        MediaPlayer mp = new MediaPlayer();
        try {
            //you can change the path, here path is external directory(e.g. sdcard) /Music/maine.mp3
            mp = MediaPlayer.create(WorkoutActivity.this, R.raw.sys_dong);
            mp.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MediaPlayer mp1 = new MediaPlayer();
        try {
//you can change the path, here path is external directory(e.g. sdcard) /Music/maine.mp3
            mp1 = MediaPlayer.create(WorkoutActivity.this, R.raw.sys_dong_end);
            mp1.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MediaPlayer mp2 = new MediaPlayer();
        try {
            mp2 = MediaPlayer.create(WorkoutActivity.this, R.raw.sys_dong_end);
            mp2.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MediaPlayer mp3 = new MediaPlayer();
        try {
            mp3 = MediaPlayer.create(WorkoutActivity.this, R.raw.sys_dong_end);
            mp3.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MediaPlayer mp4 = new MediaPlayer();
        try {
            //you can change the path, here path is external directory(e.g. sdcard) /Music/maine.mp3
            mp4 = MediaPlayer.create(WorkoutActivity.this, R.raw.sys_ding);
            mp4.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Glide.with(this).asGif().load( workoutList.get(position).getGif()).into(img);
            txt_jumping.setText(workoutList.get(position).getName());
            txt_nextexercise.setText("Next" + " " + ":" + " " + workoutList.get(nextPos).getName());
        } catch (Exception e) {
            Log.e(TAG, "startcountdowmTimer: " + e.getMessage());
            txt_nxt.setVisibility(View.GONE);
            if (attempts != Integer.parseInt(cycle)) {
                txt_nextexercise.setText("Next" + " " + ":" + " " + "Rest");
            } else {
                txt_nextexercise.setText("Next" + " " + ":" + " " + "Workout Complete");
            }
        }
        Log.e("TestHello", "startcountdowmTimer " + time);
        final MediaPlayer finalMp = mp;
        final MediaPlayer finalMp1 = mp1;
        final MediaPlayer finalMp2 = mp2;
        final MediaPlayer finalMp3 = mp3;
        final MediaPlayer finalMp4 = mp4;

        mcountdownTimer = new CountDownTimer((timer * 1000) + 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                rest = false;
                exercise = true;
                endcyclerest = false;
                totaltimes = millisUntilFinished;
                txt_progress.setText(String.valueOf((second)));
                int time2 = Integer.parseInt(time) / 2;
                Log.e("TestHello", time + "totalTimes " + totaltimes);
                second = (int) (totaltimes / 1000) % 60;

                if (second == 30 && SPmanager.getCountdown(WorkoutActivity.this)) {
                    finalMp4.start();
                }
                if (SPmanager.getGuidence(WorkoutActivity.this) && SPmanager.getCountdown(WorkoutActivity.this)) {
                    finalMp.start();
                    if (second == 3) {
                        finalMp1.start();
                        t1.speak("Three", QUEUE_ADD, null);
                        if (finalMp.isPlaying()) {
                            finalMp.stop();
                        }
                    } else if (second == 2) {
                        finalMp2.start();
                        t1.speak("Two", QUEUE_ADD, null);
                        if (finalMp.isPlaying()) {
                            finalMp.stop();
                        }
                    } else if (second == 1) {
                        finalMp3.start();
                        btn_play.setClickable(false);
                        t1.speak("One", QUEUE_ADD, null);
                        if (finalMp.isPlaying()) {
                            finalMp.stop();
                        }
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                mcountdownTimer.cancel();
                                mcountdownTimer.onFinish();
                            }
                        }, 800);
                    } else if (SPmanager.getChecked(WorkoutActivity.this)) {
                        finalMp.start();
                        if (second == time2) {
                            t1.speak("Half Complete", QUEUE_ADD, null);
                        }
                    }
                } else if (!SPmanager.getGuidence(WorkoutActivity.this) && !SPmanager.getCountdown(WorkoutActivity.this)) {
                    if (second == 3) {
                        if (finalMp.isPlaying()) {
                            finalMp.stop();
                        }
                    } else if (second == 2) {
                        if (finalMp.isPlaying()) {
                            finalMp.stop();
                        }
                    } else if (second == 1) {
                        btn_play.setClickable(false);
                        btn_play.setEnabled(false);
                        if (finalMp.isPlaying()) {
                            finalMp.stop();
                        }
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                mcountdownTimer.cancel();
                                mcountdownTimer.onFinish();
                            }
                        }, 800);
                    }
                } else if (!SPmanager.getGuidence(WorkoutActivity.this)) {
                    finalMp.start();
                    if (second == 3) {
                        finalMp1.start();
                        if (finalMp.isPlaying()) {
                            finalMp.stop();
                        }
                    } else if (second == 2) {
                        finalMp2.start();
                        if (finalMp.isPlaying()) {
                            finalMp.stop();
                        }
                    } else if (second == 1) {
                        finalMp3.start();
                        btn_play.setClickable(false);
                        btn_play.setEnabled(false);
                        if (finalMp.isPlaying()) {
                            finalMp.stop();
                        }
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                mcountdownTimer.cancel();
                                mcountdownTimer.onFinish();
                            }
                        }, 800);
                    }
                } else if (!SPmanager.getCountdown(WorkoutActivity.this)) {
                    if (second == 3) {
                        t1.speak("Three", QUEUE_ADD, null);
                        if (finalMp.isPlaying()) {
                            finalMp.stop();
                        }
                    } else if (second == 2) {
                        t1.speak("Two", QUEUE_ADD, null);
                        if (finalMp.isPlaying()) {
                            finalMp.stop();
                        }
                    } else if (second == 1) {
                        btn_play.setClickable(false);
                        btn_play.setEnabled(false);
                        t1.speak("One", QUEUE_ADD, null);
                        if (finalMp.isPlaying()) {
                            finalMp.stop();
                        }
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                mcountdownTimer.cancel();
                                mcountdownTimer.onFinish();
                            }
                        }, 800);
                    } else if (SPmanager.getChecked(WorkoutActivity.this)) {
                        if (second == time2) {
                            t1.speak("Half Complete", QUEUE_ADD, null);
                        }
                    }
                }
                pgb_progress2.setMaxProgress(Integer.parseInt(time));
                pgb_progress2.setCurrentProgress(pgb_progress2.getMaxProgress() - (second - 1));
            }
            @Override
            public void onFinish() {
                rest = true;
                exercise = false;
                endcyclerest = true;
                position++;
                nextPos++;
                progressa += Integer.valueOf(time) + Integer.parseInt(rest_time);
                progressbar.setProgress(progressa);
                mprogresscountdownTimer.cancel();
                startProgressTimer(sum);

                if (position >= workoutList.size() && attempts != Integer.parseInt(cycle)) {
                    txt_nextexercise.setText("Next" + " " + ":" + " " + "Rest");
                    if (SPmanager.getGuidence(WorkoutActivity.this)) {
                        t1.speak("Rest", QUEUE_ADD, null);
                    }
                    position = 0;
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            EndCycleRestCountdown(Long.parseLong(String.valueOf(EndResttime)));
                        }
                    }, 800);
                    attempts++;
                } else if (position >= workoutList.size() && attempts == Integer.parseInt(cycle)) {
                    progressbar.setProgress(sum);
                    Intent intent = new Intent(WorkoutActivity.this, CompleteActivity.class);
                    intent.putExtra("time", String.valueOf(Integer.parseInt(times) * Integer.parseInt(cycle)));
                    intent.putExtra("cal", String.valueOf(Integer.parseInt(cal) * Integer.parseInt(cycle)));
                    intent.putExtra("tot_ex", String.valueOf(Integer.parseInt(tot_ex) * Integer.parseInt(cycle)));
                    intent.putExtra("name", name);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    if (SPmanager.getGuidence(WorkoutActivity.this)) {
                        t1.speak("Congratulation Workout Complete", QUEUE_ADD, null);
                    }
                    mcountdownTimer.cancel();
                } else if (position < workoutList.size()) {
                    time = workoutList.get(position).getTime().replaceAll("\\s", "");
                    txt_jumping.setText(workoutList.get(position).getName());
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            restcountdowntimer(Long.parseLong(rest_time));
                        }
                    }, 800);
                }
                Log.e("TestHello", position + " time " + time + " nextPos " + nextPos);
                if (nextPos < workoutList.size()) {
                    txt_nextexercise.setText("Get Ready");
                }
            }
        };
        mcountdownTimer.start();
    }
    private void EndCycleRestCountdown(Long EndResttime) {
        MediaPlayer mp = new MediaPlayer();
        try {
            //you can change the path, here path is external directory(e.g. sdcard) /Music/maine.mp3
            mp = MediaPlayer.create(WorkoutActivity.this, R.raw.sys_dong);
            mp.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MediaPlayer mp1 = new MediaPlayer();
        try {
//you can change the path, here path is external directory(e.g. sdcard) /Music/maine.mp3
            mp1 = MediaPlayer.create(WorkoutActivity.this, R.raw.sys_dong_end);
            mp1.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MediaPlayer mp2 = new MediaPlayer();
        try {
            mp2 = MediaPlayer.create(WorkoutActivity.this, R.raw.sys_dong_end);
            mp2.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MediaPlayer mp3 = new MediaPlayer();
        try {
            mp3 = MediaPlayer.create(WorkoutActivity.this, R.raw.sys_dong_end);
            mp3.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

        txt_jumping.setText("Rest");
        Glide.with(this).asGif().load("file:///android_asset/exercise_gif/rest.gif").into(img);
        txt_nextexercise.setText("Next" + " " + ":" + " " + workoutList.get(position).getName());
        txt_nxt.setVisibility(View.GONE);

        final MediaPlayer finalMp = mp;
        final MediaPlayer finalMp1 = mp1;
        final MediaPlayer finalMp2 = mp2;
        final MediaPlayer finalMp3 = mp3;
        endrestcountDownTimer = new CountDownTimer(EndResttime, 1000) {
            public void onTick(long millisUntilFinished) {
                rest = false;
                exercise = false;
                endcyclerest = true;
                totaltimes1 = millisUntilFinished;
                seconde = (int) (totaltimes1 / 1000) % 60;
                txt_progress.setText(String.valueOf((seconde)));

                if (SPmanager.getGuidence(WorkoutActivity.this)) {
                    finalMp.start();
                    if (seconde == 3) {
                        finalMp1.start();
                        t1.speak("Three", QUEUE_ADD, null);
                        if (finalMp.isPlaying()) {
                            finalMp.stop();
                        }
                    } else if (seconde == 2) {
                        finalMp2.start();
                        t1.speak("Two", QUEUE_ADD, null);
                        if (finalMp.isPlaying()) {
                            finalMp.stop();
                        }
                    } else if (seconde == 1) {
                        finalMp3.start();
                        t1.speak("One", QUEUE_ADD, null);
                        if (finalMp.isPlaying()) {
                            finalMp.stop();
                        }
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                endrestcountDownTimer.cancel();
                                endrestcountDownTimer.onFinish();
                            }
                        }, 800);

                    }
                    pgb_progress2.setMaxProgress(30000);
                    pgb_progress2.setCurrentProgress(pgb_progress2.getMaxProgress() - (seconde - 1));
                }
            }

            public void onFinish() {
                rest = true;
                exercise = true;
                endcyclerest = false;
                txt_nxt.setVisibility(View.VISIBLE);
                txt_nextexercise.setVisibility(View.VISIBLE);
                progressa += Integer.valueOf(time) + Integer.parseInt(rest_time);
                mprogresscountdownTimer.cancel();
                progressbar.setProgress(progressa);
                startProgressTimer(sum);
                endrestcountDownTimer.cancel();

                handler.postDelayed(new Runnable() {
                    public void run() {
                        restcountdowntimer(Long.parseLong(rest_time));
                    }
                }, 800);
            }
        }.start();
    }
    private void startProgressTimer(final int sum) {
        final int finalSum = sum;
        mprogresscountdownTimer = new CountDownTimer(Long.parseLong(String.valueOf(finalSum * 1000)), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                totalCount = millisUntilFinished;
                int minute = (int) (totalCount / 1000) / 60;
                seconda = (int) (totalCount / 1000) % 60;
                int progress = (int) (millisUntilFinished / 1000);
                progressbar.setMax(sum);
                progressbar.setProgress(progressa + (progressbar.getMax() - (progress - 1)));
                Log.e(TAG, "onTick: " + progressa + (sum - progress));
            }
            @Override
            public void onFinish() {
            }
        };
        mprogresscountdownTimer.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (t1 != null) {
            t1.shutdown();
        }
        if (mcountdownTimer != null) {
            mcountdownTimer.cancel();
        }
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (restcountDownTimer != null) {
            restcountDownTimer.cancel();
        }
        if (endrestcountDownTimer != null) {
            endrestcountDownTimer.cancel();
        }
    }
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        if (t1 != null) {
            t1.shutdown();
        }
        if (mcountdownTimer != null) {
            mcountdownTimer.cancel();
        }
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (restcountDownTimer != null) {
            restcountDownTimer.cancel();
        }
        if (endrestcountDownTimer != null) {
            endrestcountDownTimer.cancel();
        }
        if (key != null) {
            if (key.equals("custom")) {
                Intent intent = new Intent(WorkoutActivity.this, CustomWorkoutActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else {
                Intent intent = new Intent(WorkoutActivity.this, DaysActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
        super.onBackPressed();
    }
    // home button press
    @Override
    public void onAttachedToWindow() {
        // TODO Auto-generated method stub
        if (mPlayer2 != null && mPlayer2.isPlaying()) {
            mPlayer2.stop();
            mPlayer2.release();
        }
        super.onAttachedToWindow();
    }
}