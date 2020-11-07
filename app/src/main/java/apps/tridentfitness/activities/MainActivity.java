package apps.tridentfitness.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.nshmura.snappysmoothscroller.SnappyLinearLayoutManager;

import java.util.ArrayList;

import apps.tridentfitness.Network.ApiUtils;
import apps.tridentfitness.Network.WebApi;
import apps.tridentfitness.Responses.AllWorkout;
import apps.tridentfitness.adapter.HomePageCategoryAdapter;
import apps.tridentfitness.getset.HomePageCategoryGetset;
import apps.tridentfitness.utilHelper.AdManager;
import apps.tridentfitness.utilHelper.RecyclerItemClickListener;
import apps.tridentfitness.utilHelper.SPmanager;
import apps.tridentfitness.utilHelper.SqliteHelper;
import apps.tridentfitness.R;
import apps.tridentfitness.getset.detailpageGetSet;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, HomePageCategoryAdapter.OnItemClickListener {
    ArrayList<HomePageCategoryGetset> homePageCategoryList;
    RelativeLayout rel_bmi, rel_setting, rel_progress, rel_custom, rel_workout;
    SqliteHelper sqliteHelper;
    private String TAG = "MainActivity";
    HomePageCategoryAdapter adapter;
    Button button;
    private static InterstitialAd interstitialAd;

    RecyclerView rec_homePageCategory;
    public static ArrayList<detailpageGetSet> detailList;
    private ProgressDialog progressDialog;
    public static int countBackPress = 0;
    private String name;
    private String description, image, type, calories, time, tot_exercise, id;
    private int Pos;
    String data;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.WHITE);
        AudienceNetworkAds.initialize(this);
        setContentView(R.layout.activity_main);
        data = getIntent().getStringExtra("data");
        init();
        addData(data);
        listclick();


    }

    private void init() {
        homePageCategoryList = new ArrayList<>();
        rec_homePageCategory = findViewById(R.id.rec_homePageCategory);
        rel_bmi = findViewById(R.id.rel_bmi);
        rel_setting = findViewById(R.id.rel_setting);
        rel_progress = findViewById(R.id.rel_progress);
        rel_custom = findViewById(R.id.rel_custom);
        rel_workout = findViewById(R.id.rel_workout);
        button = (Button) findViewById(R.id.btnfeedback);

        if (!SPmanager.getFirstTime(MainActivity.this)) {
            SPmanager.isFirstTime(MainActivity.this, true);
            SPmanager.setChecked(MainActivity.this, true);
            SPmanager.setGuidence(MainActivity.this, true);
            SPmanager.setCountdown(MainActivity.this, true);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoContactUs(MainActivity.this);
            }
        });
    }

    private void interstitialAd_() {
        interstitialAd = new InterstitialAd(MainActivity.this);
        interstitialAd.setAdUnitId(getString(R.string.interstial_ad_unit_id));
        AdRequest request = new AdRequest.Builder().build();
        interstitialAd.loadAd(request);

        interstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("id", "" + homePageCategoryList.get(Pos).getId());
                intent.putExtra("description", "" + homePageCategoryList.get(Pos).getDescription());
                intent.putExtra("time", "" + homePageCategoryList.get(Pos).getMinuts());
                intent.putExtra("cal", "" + homePageCategoryList.get(Pos).getCalories());
                intent.putExtra("tot_ex", "" + homePageCategoryList.get(Pos).getTotexercise());
                intent.putExtra("type", "" + homePageCategoryList.get(Pos).getTyps());
                intent.putExtra("name", "" + homePageCategoryList.get(Pos).getName());
                intent.putExtra("image", "" + homePageCategoryList.get(Pos).getImage());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("id", "" + homePageCategoryList.get(Pos).getId());
                intent.putExtra("description", "" + homePageCategoryList.get(Pos).getDescription());
                intent.putExtra("time", "" + homePageCategoryList.get(Pos).getMinuts());
                intent.putExtra("cal", "" + homePageCategoryList.get(Pos).getCalories());
                intent.putExtra("tot_ex", "" + homePageCategoryList.get(Pos).getTotexercise());
                intent.putExtra("type", "" + homePageCategoryList.get(Pos).getTyps());
                intent.putExtra("name", "" + homePageCategoryList.get(Pos).getName());
                intent.putExtra("image", "" + homePageCategoryList.get(Pos).getImage());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
    }

    private void adDialog() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Wait ! Showing Ad"); // Setting Message
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();

            }

        }).start();
        AdManager.increaseCount(MainActivity.this);
        AdManager.showInterstial(MainActivity.this);
//        interstitialAd_();


        countBackPress = -1;
    }


    public void gotoContactUs(Activity activity) {
        activity.getIntent();
        Intent intent = new Intent("android.intent.action.SENDTO");
        intent.setData(Uri.parse("mailto:" + getString(R.string.email_id)));
        intent.putExtra("android.intent.extra.SUBJECT", "Contact Us");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n\n\n\n\n\n\n");
        stringBuilder.append(SettingsActivity.getHandSetInfo(activity));
        intent.putExtra("android.intent.extra.TEXT", stringBuilder.toString());
        activity.startActivity(intent);
    }

    @Override
    public void onItemClick(HomePageCategoryGetset item) {
    }
    @Override
    protected void onResume() {
        super.onResume();
        AdManager.setUpInterstial(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.rel_bmi:
                intent = new Intent(MainActivity.this, BmiActivity.class);
                startActivity(intent);
                break;
            case R.id.rel_setting:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.rel_progress:
                intent = new Intent(MainActivity.this, ProgressActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.rel_workout:
                intent = new Intent(MainActivity.this, AllWorkOutActivity.class);
                startActivity(intent);
                break;
            case R.id.rel_custom:
                intent = new Intent(MainActivity.this, CustomWorkoutActivity.class);
                startActivity(intent);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void changeStatsBarColor(Activity activity) {
        Window window = activity.getWindow();
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(activity, R.color.white));
    }

    public void addData(String data) {
        rel_bmi.setOnClickListener(this);
        rel_setting.setOnClickListener(this);
        rel_progress.setOnClickListener(this);
        rel_custom.setOnClickListener(this);
        rel_workout.setOnClickListener(this);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        WebApi webApi = ApiUtils.getClient().create(WebApi.class);
        Call<AllWorkout> call = webApi.all_Workout(data);
        call.enqueue(new Callback<AllWorkout>() {
            @Override
            public void onResponse(Call<AllWorkout> call, Response<AllWorkout> response) {
                progressDialog.dismiss();
                if (response.body().getStatus() == 1){
                    for (int i =0; i < response.body().getResult().size();i++){
                        HomePageCategoryGetset getset = new HomePageCategoryGetset();
                        name = response.body().getResult().get(i).getTitle();
                        id = response.body().getResult().get(i).getId();
                        description = response.body().getResult().get(i).getDescription();
                        tot_exercise = response.body().getResult().get(i).getTotalExc();
                        time = "0";
                        calories = "0";
                        type = "0";
                        image = response.body().getResult().get(i).getWorkoutImage();
                        getset.setCalories(calories);
                        getset.setImage(image);
                        getset.setMinuts(time);
                        getset.setName(name);
                        getset.setTotexercise(tot_exercise);
                        getset.setTyps(type);
                        getset.setId(id);
                        getset.setDescription(description);
                        homePageCategoryList.add(getset);
                    }

                    SnappyLinearLayoutManager verticalLayoutManager1 = new SnappyLinearLayoutManager(MainActivity.this, SnappyLinearLayoutManager.VERTICAL, false);
                    rec_homePageCategory.setLayoutManager(verticalLayoutManager1);
                    adapter = new HomePageCategoryAdapter(rec_homePageCategory, homePageCategoryList, MainActivity.this);
                    adapter.notifyDataSetChanged();
                    rec_homePageCategory.setAdapter(adapter);


                }else {
                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AllWorkout> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Server error",Toast.LENGTH_SHORT).show();
            }
        });


      /*  try {
            sqliteHelper = new SqliteHelper(MainActivity.this);
            SQLiteDatabase db1 = sqliteHelper.getWritableDatabase();
            Cursor cur = db1.rawQuery("select * from home_category;", null);
            if (cur.getCount() != 0) {
                if (cur.moveToFirst()) {
                    do {
                        HomePageCategoryGetset getset = new HomePageCategoryGetset();
                        name = cur.getString(cur.getColumnIndex("name"));
                        id = cur.getString(cur.getColumnIndex("id"));
                        description = cur.getString(cur.getColumnIndex("description"));
                        tot_exercise = cur.getString(cur.getColumnIndex("tot_exercise"));
                        time = cur.getString(cur.getColumnIndex("time"));
                        calories = cur.getString(cur.getColumnIndex("calories"));
                        type = cur.getString(cur.getColumnIndex("type"));
                        image = cur.getString(cur.getColumnIndex("image"));
                        getset.setCalories(calories);
                        getset.setImage(image);
                        getset.setMinuts(time);
                        getset.setName(name);
                        getset.setTotexercise(tot_exercise);
                        getset.setTyps(type);
                        getset.setId(id);
                        getset.setDescription(description);
                        homePageCategoryList.add(getset);
                    } while (cur.moveToNext());
                }
            }
            cur.close();
            db1.close();
        } catch (Exception e) {
            Log.e(TAG, "onCreate: " + e.getCause());
            e.printStackTrace();
        }*/

    }

    private void listclick() {
   /*     rec_homePageCategory.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Pos = position;
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("id", "" + homePageCategoryList.get(position).getId());
                intent.putExtra("description", "" + homePageCategoryList.get(position).getDescription());
                intent.putExtra("time", "" + homePageCategoryList.get(position).getMinuts());
                intent.putExtra("cal", "" + homePageCategoryList.get(position).getCalories());
                intent.putExtra("tot_ex", "" + homePageCategoryList.get(position).getTotexercise());
                intent.putExtra("type", "" + homePageCategoryList.get(position).getTyps());
                intent.putExtra("name", "" + homePageCategoryList.get(position).getName());
                intent.putExtra("image", "" + homePageCategoryList.get(position).getImage());
//
//                AdManager.increaseCount(MainActivity.this);
//                AdManager.showInterstial(MainActivity.this);

                countBackPress++;

                if (countBackPress >= 3) {
                    //adDialog();
                } else {
                    startActivity(intent);
                }
            }
        }));*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
