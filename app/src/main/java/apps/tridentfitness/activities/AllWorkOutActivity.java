package apps.tridentfitness.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nshmura.snappysmoothscroller.SnappyLinearLayoutManager;

import java.util.ArrayList;

import apps.tridentfitness.Network.ApiUtils;
import apps.tridentfitness.Network.WebApi;
import apps.tridentfitness.Responses.UserAllResponse;
import apps.tridentfitness.getset.detailpageGetSet;
import apps.tridentfitness.utilHelper.AdManager;
import apps.tridentfitness.utilHelper.RecyclerItemClickListener;
import apps.tridentfitness.utilHelper.SqliteHelper;
import apps.tridentfitness.R;
import apps.tridentfitness.adapter.WorkoutListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllWorkOutActivity extends AppCompatActivity {

    private static final String TAG = "WorkOut";
    private RecyclerView workout_recycle;
    ArrayList<detailpageGetSet> allExercise;
    private SqliteHelper sqliteHelper;
    private ImageView btn_back;
    ProgressDialog progressDialog;
    WorkoutListAdapter adapter;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        setContentView(R.layout.activity_work_out);
        allExercise = new ArrayList<>();
        adapter  = new WorkoutListAdapter(workout_recycle, allExercise, AllWorkOutActivity.this);

        init();
        getData();
    }

    private void init() {
        workout_recycle = findViewById(R.id.workout_recycle);
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllWorkOutActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void getData() {
        progressDialog = new ProgressDialog(AllWorkOutActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        WebApi webApi = ApiUtils.getClient().create(WebApi.class);
        Call<UserAllResponse> call = webApi.user_all_Workout("2");
        call.enqueue(new Callback<UserAllResponse>() {
            @Override
            public void onResponse(Call<UserAllResponse> call, Response<UserAllResponse> response) {
                progressDialog.dismiss();
                if (response.body().getStatus() == 1){
                    for (int i =0; i < response.body().getResult().size();i++){
                        detailpageGetSet getSet = new detailpageGetSet();
                        String id = response.body().getResult().get(i).getId();
                        String name = response.body().getResult().get(i).getWorkoutName();
                        String image = response.body().getResult().get(i).getWorkoutImage();
                        String url = "";
                        getSet.setUrl(url);
                        getSet.setId(id);
                        getSet.setImage(image);
                        getSet.setName(name);
                       /* if (getString(R.string.show_admmob_ads).equals("yes") || getString(R.string.show_facebook_ads).equals("yes")) {
                            if (cur.getPosition() % 5 == 0 && cur.getPosition() != 0) {
                                allExercise.add(null);
                            }
                        }*/
                        allExercise.add(getSet);
                        Log.e(TAG, "onCreate: " + name);
                    }

                    SnappyLinearLayoutManager verticalLayoutManager1 = new SnappyLinearLayoutManager(AllWorkOutActivity.this, SnappyLinearLayoutManager.VERTICAL, false);
                    workout_recycle.setLayoutManager(verticalLayoutManager1);

                    adapter.notifyDataSetChanged();
                    workout_recycle.setAdapter(adapter);

                }else {
                    Toast.makeText(getApplicationContext(),response
                            .body().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserAllResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_SHORT).show();
            }
        });


      /*  try {
            sqliteHelper = new SqliteHelper(AllWorkOutActivity.this);
            SQLiteDatabase db1 = sqliteHelper.getWritableDatabase();
            Cursor cur = db1.rawQuery("select * from exercise ", null);
            if (cur.getCount() != 0) {
                if (cur.moveToFirst()) {
                    do {
                        detailpageGetSet getSet = new detailpageGetSet();
                        String id = cur.getString(cur.getColumnIndex("id"));
                        String name = cur.getString(cur.getColumnIndex("name"));
                        String image = cur.getString(cur.getColumnIndex("image"));
                        String url = cur.getString(cur.getColumnIndex("url"));
                        getSet.setUrl(url);
                        getSet.setId(id);
                        getSet.setImage(image);
                        getSet.setName(name);
                        if (getString(R.string.show_admmob_ads).equals("yes") || getString(R.string.show_facebook_ads).equals("yes")) {
                            if (cur.getPosition() % 5 == 0 && cur.getPosition() != 0) {
                                allExercise.add(null);
                            }
                        }
                        allExercise.add(getSet);
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


   /*     adapter.setOnItemClickListener(new WorkoutListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                try {
                    if (allExercise.get(position).getId() != null) {
                        Intent intent = new Intent(AllWorkOutActivity.this, ExcerciseDetail.class);
                        intent.putExtra("id", allExercise.get(position).getId());
                        intent.putExtra("url", allExercise.get(position).getUrl());
                        startActivity(intent);
                       // AdManager.increaseCount(AllWorkOutActivity.this);
                       // AdManager.showInterstial(AllWorkOutActivity.this);
                    }
                }catch (Exception e){
                    Log.e(TAG, "onItemClick: "+e.getMessage() );
                }
            }
        });*/

        /*workout_recycle.addOnItemTouchListener(new RecyclerItemClickListener(AllWorkOutActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                try {
                    if (allExercise.get(position).getId() != null) {
                        Intent intent = new Intent(AllWorkOutActivity.this, ExcerciseDetail.class);
                        intent.putExtra("id", allExercise.get(position).getId());
                        intent.putExtra("url", allExercise.get(position).getUrl());
                        startActivity(intent);
                        AdManager.increaseCount(AllWorkOutActivity.this);
                        AdManager.showInterstial(AllWorkOutActivity.this);
                    }
                }catch (Exception e){
                    Log.e(TAG, "onItemClick: "+e.getMessage() );
                }
            }
        }));*/
    }

}
