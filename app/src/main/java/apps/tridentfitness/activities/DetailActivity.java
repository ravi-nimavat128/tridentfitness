package apps.tridentfitness.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nshmura.snappysmoothscroller.SnappyLinearLayoutManager;

import java.util.ArrayList;

import apps.tridentfitness.Network.ApiUtils;
import apps.tridentfitness.Network.WebApi;
import apps.tridentfitness.Responses.AllExerice;
import apps.tridentfitness.getset.detailpageGetSet;
import apps.tridentfitness.utilHelper.AdManager;
import apps.tridentfitness.utilHelper.RecyclerItemClickListener;
import apps.tridentfitness.utilHelper.SqliteHelper;
import apps.tridentfitness.utilHelper.StartDialog;
import apps.tridentfitness.BuildConfig;
import apps.tridentfitness.R;
import apps.tridentfitness.adapter.WorkoutListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    RecyclerView listview;
    TextView txt_detail, num_excecise, num_time, text_restName, num_calories, txt_type;
    SqliteHelper sqliteHelper;
    String id, description, time, cal, tot_ex, name, image, type;
    Button btn_start;
    RelativeLayout excecise_image;
    ImageView share, back;
    private String TAG = "DetailActivity";
    public static ArrayList<detailpageGetSet> detailpageGetSetArrayList;
    ProgressDialog progressDialog;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.WHITE);
        setContentView(R.layout.activity_detail);
        progressDialog = new ProgressDialog(DetailActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        getIntents();
        init();
        setImage();
        getData();
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startdialog(true);
            }
        });
    }

    private void setImage() {
        try {
            switch (image) {
                case "one":
                    excecise_image.setBackground(getResources().getDrawable(R.drawable.one));
                    break;
                case "two":
                    excecise_image.setBackground(getResources().getDrawable(R.drawable.two));
                    break;
                case "three":
                    excecise_image.setBackground(getResources().getDrawable(R.drawable.three));
                    break;
                case "foure":
                    excecise_image.setBackground(getResources().getDrawable(R.drawable.four));
                    break;
                case "five":
                    excecise_image.setBackground(getResources().getDrawable(R.drawable.five));
                    break;
                case "six":
                    excecise_image.setBackground(getResources().getDrawable(R.drawable.six));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getIntents() {
        Intent intent = getIntent();
        description = intent.getStringExtra("description");
        id = intent.getStringExtra("id");
        time = intent.getStringExtra("time");
        cal = intent.getStringExtra("cal");
        tot_ex = intent.getStringExtra("tot_ex");
        name = intent.getStringExtra("name");
        type = intent.getStringExtra("type");
        image = intent.getStringExtra("image");

    }

    @Override
    protected void onResume() {
        super.onResume();
        AdManager.setUpInterstial(this);
    }

    private void init() {
        detailpageGetSetArrayList = new ArrayList<>();
        btn_start = findViewById(R.id.btn_start);
        share = findViewById(R.id.share);
        excecise_image = findViewById(R.id.excecise_image);
        listview = findViewById(R.id.listview);
        txt_detail = findViewById(R.id.txt_detail);
        num_excecise = findViewById(R.id.num_excecise);
        num_time = findViewById(R.id.num_time);
        num_calories = findViewById(R.id.num_calories);
        txt_type = findViewById(R.id.txt_type);
        text_restName = findViewById(R.id.text_restName);

        num_excecise.setText(tot_ex);
        text_restName.setText(name);
        num_time.setText(time);
        num_calories.setText(cal);
        txt_type.setText(type);
        txt_detail.setText(description);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                    String shareMessage = "\n For Inquiry contact: +91 9998828203 \n";
                    //shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "Share"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getData() {


        progressDialog.show();

        WebApi webApi = ApiUtils.getClient().create(WebApi.class);
        Call<AllExerice> call = webApi.all_exercise(id);
        call.enqueue(new Callback<AllExerice>() {
            @Override
            public void onResponse(Call<AllExerice> call, Response<AllExerice> response) {
                progressDialog.dismiss();
                if (response.body().getStatus() == 1){
                    for (int i =0; i < response.body().getResult().size();i++){

                        detailpageGetSet getSet = new detailpageGetSet();
                        String id = response.body().getResult().get(i).getId();
                        String name =  response.body().getResult().get(i).getWorkoutName();
                        String image =  response.body().getResult().get(i).getExerciseImage();
                        String url =  "";
                        String time =  response.body().getResult().get(i).getTime();
                        String gif =  response.body().getResult().get(i).getExerciseGif();
                        getSet.setUrl(url);
                        getSet.setId(id);
                        getSet.setTime(time);
                        getSet.setImage(image);
                        getSet.setGif(gif);
                        getSet.setName(name);
                        detailpageGetSetArrayList.add(getSet);
                        Log.e(TAG, "onCreate: " + name);


                    }

                    SnappyLinearLayoutManager verticalLayoutManager1 = new SnappyLinearLayoutManager(DetailActivity.this, SnappyLinearLayoutManager.VERTICAL, false);
                    listview.setLayoutManager(verticalLayoutManager1);
                    WorkoutListAdapter adapter = new WorkoutListAdapter(listview, detailpageGetSetArrayList, DetailActivity.this);
                    adapter.notifyDataSetChanged();
                    listview.setAdapter(adapter);


                }else {
                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AllExerice> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Server error",Toast.LENGTH_SHORT).show();
            }
        });



        /*try {
            sqliteHelper = new SqliteHelper(DetailActivity.this);
            SQLiteDatabase db1 = sqliteHelper.getWritableDatabase();
            Cursor cur = db1.rawQuery("select * from exercise where  " + type.toLowerCase() + "  ='" + "1" + "';", null);
            if (cur.getCount() != 0) {
                if (cur.moveToFirst()) {
                    do {
                        detailpageGetSet getSet = new detailpageGetSet();
                        String id = cur.getString(cur.getColumnIndex("id"));
                        String name = cur.getString(cur.getColumnIndex("name"));
                        String image = cur.getString(cur.getColumnIndex("image"));
                        String url = cur.getString(cur.getColumnIndex("url"));
                        String time = cur.getString(cur.getColumnIndex("time"));
                        String gif = cur.getString(cur.getColumnIndex("gif"));
                        getSet.setUrl(url);
                        getSet.setId(id);
                        getSet.setTime(time);
                        getSet.setImage(image);
                        getSet.setGif(gif);
                        getSet.setName(name);
                        detailpageGetSetArrayList.add(getSet);
                        Log.e(TAG, "onCreate: " + name);
                    } while (cur.moveToNext());
                }
            }
            cur.close();
            db1.close();
        } catch (Exception e) {
            Log.e(TAG, "onCreate: " + e.getCause());
            e.printStackTrace();
        }*/



  /*      listview.addOnItemTouchListener(new RecyclerItemClickListener(DetailActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(DetailActivity.this, ExcerciseDetail.class);
                intent.putExtra("id", detailpageGetSetArrayList.get(position).getId());
                intent.putExtra("url", detailpageGetSetArrayList.get(position).getUrl());
                startActivity(intent);
                AdManager.increaseCount(DetailActivity.this);
                AdManager.showInterstial(DetailActivity.this);
            }
        }));*/

    }


    private void startdialog(boolean isCancel) {
        StartDialog startDialog = new StartDialog(DetailActivity.this, android.R.style.Theme_NoTitleBar_Fullscreen, "detail", time, cal, tot_ex, name);
        startDialog.setCancelable(isCancel);
        startDialog.show();
    }

}
