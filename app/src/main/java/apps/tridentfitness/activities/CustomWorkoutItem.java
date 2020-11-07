package apps.tridentfitness.activities;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.TextView;

import com.nshmura.snappysmoothscroller.SnappyLinearLayoutManager;

import java.util.ArrayList;

import apps.tridentfitness.R;
import apps.tridentfitness.adapter.CustomButtonListener;
import apps.tridentfitness.adapter.CustomItemAdapter;
import apps.tridentfitness.getset.detailpageGetSet;
import apps.tridentfitness.utilHelper.SqliteHelper;
import apps.tridentfitness.utilHelper.StartDialog;

public class CustomWorkoutItem extends AppCompatActivity implements CustomButtonListener {
    TextView txt_workout,txt_delete;
    RecyclerView listView;
    Button btn_startWorkout;
    ImageView img_back;
    CustomItemAdapter adapter;
    SqliteHelper sqliteHelper;
    String cat_name;
    public static ArrayList<detailpageGetSet> selectItemArrayList;
    private String time, cal, tot_ex, name;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.WHITE);
        setContentView(R.layout.activity_custom_workout_item);
        getIntents();
        init();
        getList();
        btn_startWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startdialog(true);
            }
        });
    }

    private void getIntents() {
        Intent intent = getIntent();
        cat_name = intent.getStringExtra("cat_name");
        time = intent.getStringExtra("time");
        name = intent.getStringExtra("name");
        cal = intent.getStringExtra("cal");
        tot_ex = intent.getStringExtra("tot_ex");
    }

    private void init() {
        selectItemArrayList = new ArrayList<>();
        txt_workout = findViewById(R.id.txt_workout);
        listView = findViewById(R.id.listview);
        img_back = findViewById(R.id.img_back);
        txt_delete = findViewById(R.id.txt_delete);
        btn_startWorkout = findViewById(R.id.btn_startWorkout);
        txt_workout.setText(cat_name);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        txt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialog();
            }
        });
    }

    private void showdialog() {
        final Dialog dialog = new Dialog(CustomWorkoutItem.this);
        dialog.setContentView(R.layout.delete_dialog);

        TextView txt_yes = dialog.findViewById(R.id.txt_yes);
        TextView txt_no = dialog.findViewById(R.id.txt_no);
        dialog.show();

        txt_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqliteHelper = new SqliteHelper(CustomWorkoutItem.this);
                SQLiteDatabase db1 = sqliteHelper.getWritableDatabase();
                db1.execSQL("DELETE FROM workoutname Where cat_name ='" + cat_name + "';");
                db1.close();
                dialog.dismiss();
                Intent intent = new Intent(CustomWorkoutItem.this,CustomWorkoutActivity.class);
                startActivity(intent);
            }
        });
        txt_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void getList() {
        try {
            sqliteHelper = new SqliteHelper(CustomWorkoutItem.this);
            SQLiteDatabase db1 = sqliteHelper.getWritableDatabase();
            Cursor cur = db1.rawQuery("select * from customworkout where cat_name='" + cat_name + "';", null);
            if (cur.getCount() != 0) {
                if (cur.moveToFirst()) {
                    do {
                        detailpageGetSet cusomItem = new detailpageGetSet();
                        String name = cur.getString(cur.getColumnIndex("name"));
                        String id = cur.getString(cur.getColumnIndex("cat_id"));
                        String time = cur.getString(cur.getColumnIndex("time"));
                        String calories = cur.getString(cur.getColumnIndex("calories"));
                        String exercise = cur.getString(cur.getColumnIndex("totalexercise"));
                        String url = cur.getString(cur.getColumnIndex("url"));
                        String image = cur.getString(cur.getColumnIndex("image"));
                        String intervaltime = cur.getString(cur.getColumnIndex("intervaltime"));
                        String cycle = cur.getString(cur.getColumnIndex("cycle"));
                        String cat_name = cur.getString(cur.getColumnIndex("cat_name"));
                        String gif = cur.getString(cur.getColumnIndex("gif"));

                        cusomItem.setDetail(cat_name);
                        cusomItem.setId(id);
                        cusomItem.setCalories(calories);
                        cusomItem.setTime(time);
                        cusomItem.setTotalexercise(exercise);
                        cusomItem.setName(name);
                        cusomItem.setUrl(url);
                        cusomItem.setImage(image);
                        cusomItem.setGif(gif);
                        cusomItem.setResttime(intervaltime);
                        cusomItem.setCycle(cycle);
                        selectItemArrayList.add(cusomItem);
                    } while (cur.moveToNext());
                }
            }
            cur.close();
            db1.close();
        } catch (SQLException e) {
            Log.e("", "onCreate: " + e.getCause());
            e.printStackTrace();
        }
        SnappyLinearLayoutManager verticalLayoutManager1 = new SnappyLinearLayoutManager(CustomWorkoutItem.this, SnappyLinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(verticalLayoutManager1);
        adapter = new CustomItemAdapter(CustomWorkoutItem.this, selectItemArrayList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setCustomButtonListener(CustomWorkoutItem.this);

    }

    @Override
    public void OnImageDisplayclick(int position) {
        try {
            if (selectItemArrayList.get(position).getId() != null) {
                Intent intent = new Intent(CustomWorkoutItem.this, ExcerciseDetail.class);
                intent.putExtra("id", selectItemArrayList.get(position).getId());
                intent.putExtra("url", selectItemArrayList.get(position).getUrl());
                startActivity(intent);
            }
        }catch (Exception e){
            Log.e("CustomWorkoutItem", "OnImageDisplayclick: "+e.getMessage() );
        }
    }
    private void startdialog(boolean isCancel) {
        StartDialog startDialog = new StartDialog(CustomWorkoutItem.this, android.R.style.Theme_NoTitleBar_Fullscreen, "custom", time, cal, tot_ex, name);
        startDialog.setCancelable(isCancel);
        startDialog.show();
    }
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CustomWorkoutItem.this, CustomWorkoutActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finishAffinity();
    }
}
