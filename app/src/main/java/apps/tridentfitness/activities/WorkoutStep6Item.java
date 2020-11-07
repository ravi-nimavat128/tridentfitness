package apps.tridentfitness.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import apps.tridentfitness.R;
import apps.tridentfitness.getset.SelectItem;
import apps.tridentfitness.utilHelper.MyApplication;
import apps.tridentfitness.utilHelper.SqliteHelper;

public class WorkoutStep6Item extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "WorkoutStep6Item";
    RelativeLayout rel_header;
    RelativeLayout rel_step;
    RelativeLayout rel_repeatworkout;
    RelativeLayout rel_interval;
    LinearLayout rel_time;
    RelativeLayout rel_one_layout;
    RelativeLayout rel_two_layout;
    RelativeLayout rel_three_layout;
    RelativeLayout rl_5seonds_layout;
    RelativeLayout rl_10seonds_layout;
    RelativeLayout rl_15seconds_layout;
    Button btn_workout;
    ImageView img_time;
    ImageView img_totalworkout;
    ImageView totalimg_calories;
    EditText edit_name;
    int calories, time, workout;
    String name;
    String id;
    String url;
    SqliteHelper sqliteHelper;
    int calories1;
    int time1;
    TextView txt_repeatwork;
    TextView txt_repeatecercise;
    TextView txt_1;
    TextView txt_2;
    TextView txt_3;
    TextView txt_intervaltime;
    TextView txt_interval_1;
    TextView txt_interval_2;
    TextView txt_interval_3;
    TextView txt_time;
    TextView txt_calories;
    TextView txt_totalworkout;
    private int restSeconds = 10;
    private int repeatNumber = 1;
    String obj;
    private MyApplication mApp;

    ArrayList<SelectItem> dragItemArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.WHITE);
        setContentView(R.layout.activity_workout_step6_item);
        mApp = MyApplication.getInstance();
        dragItemArrayList = mApp.getMainDragList();
        getIntents();
        init();
    }

    private void getIntents() {
        Intent intent = getIntent();
        calories = intent.getIntExtra("calories", 1);
        time = intent.getIntExtra("time", 1);
        workout = intent.getIntExtra("workout", 1);
    }

    private void init() {
        rel_time = findViewById(R.id.rel_time);
        rel_header = findViewById(R.id.rel_header);
        rel_step = findViewById(R.id.rel_step);
        rel_repeatworkout = findViewById(R.id.rel_repeatworkout);
        rel_interval = findViewById(R.id.rel_interval);
        btn_workout = findViewById(R.id.btn_workout);
        txt_totalworkout = findViewById(R.id.txt_totalworkout);
        txt_calories = findViewById(R.id.txt_calories);
        txt_time = findViewById(R.id.txt_time);
        edit_name = findViewById(R.id.edt_name);
        txt_repeatwork = findViewById(R.id.txt_repeatwork);
        txt_repeatecercise = findViewById(R.id.txt_repeatecercise);
        rel_one_layout = (RelativeLayout) findViewById(R.id.rel_one_layout);
        rel_two_layout = (RelativeLayout) findViewById(R.id.rel_two_layout);
        rel_three_layout = (RelativeLayout) findViewById(R.id.rel_three_layout);
        rl_5seonds_layout = (RelativeLayout) findViewById(R.id.rl_5seonds_layout);
        rl_10seonds_layout = (RelativeLayout) findViewById(R.id.rl_10seonds_layout);
        rl_15seconds_layout = (RelativeLayout) findViewById(R.id.rl_15seconds_layout);
        txt_1 = (TextView) findViewById(R.id.txt_1);
        txt_2 = (TextView) findViewById(R.id.txt_2);
        txt_3 = (TextView) findViewById(R.id.txt_3);
        txt_intervaltime = findViewById(R.id.txt_intervaltime);
        txt_interval_1 = (TextView) findViewById(R.id.txt_interval_1);
        txt_interval_2 = (TextView) findViewById(R.id.txt_interval_2);
        txt_interval_3 = (TextView) findViewById(R.id.txt_interval_3);
        edit_name.clearFocus();
        circuitNumberSelected(this.rel_one_layout);
        secondSelected(this.rl_10seonds_layout);
        int time1 = (workout * 10) / 60;
        int time2 = time + time1;
        txt_time.setText(String.valueOf(time2));
        txt_calories.setText(String.valueOf(calories));
        txt_totalworkout.setText(String.valueOf(workout));
        obj = edit_name.getText().toString();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_10seonds_layout:
                secondSelected(rl_10seonds_layout);
                this.restSeconds = 10;
                changeRestTime();
                return;
            case R.id.rl_15seconds_layout:
                secondSelected(rl_15seconds_layout);
                this.restSeconds = 15;
                changeRestTime();
                return;
            case R.id.rl_5seonds_layout:
                secondSelected(rl_5seonds_layout);
                this.restSeconds = 5;
                changeRestTime();
                return;
            case R.id.rel_one_layout:
                circuitNumberSelected(rel_one_layout);
                this.repeatNumber = 1;
                changeCircuit();
                return;
            case R.id.btn_workout:
                obj = edit_name.getText().toString();
                if (obj != null && !obj.equals("")) {
                    saveWorkout();
                } else {
                    Toast.makeText(WorkoutStep6Item.this, "Pleses enter Workout Name", Toast.LENGTH_SHORT).show();
                }
                return;
            case R.id.rel_three_layout:
                circuitNumberSelected(rel_three_layout);
                this.repeatNumber = 3;
                changeCircuit();
                return;
            case R.id.rel_two_layout:
                circuitNumberSelected(rel_two_layout);
                this.repeatNumber = 2;
                changeCircuit();
        }
    }

    private void resetTime() {
        time1 = time * repeatNumber;
        int workout1 = workout * repeatNumber;
        time1 += (int) (((float) (workout1 * restSeconds)) / 60.0f);
    }

    private void changeRestTime() {
        resetTime();
        txt_time.setText(String.valueOf(time1));
    }

    private void resetCalories() {
        calories1 = calories * repeatNumber;
    }

    private void changeCircuit() {
        resetTime();
        resetCalories();
        txt_time.setText(String.valueOf(time1));
        txt_calories.setText(String.valueOf(calories1));
    }

    private void circuitNumberSelected(RelativeLayout relativeLayout) {
        rel_one_layout.setSelected(true);
        rel_two_layout.setSelected(true);
        rel_three_layout.setSelected(true);
        relativeLayout.setSelected(false);

        if (rel_one_layout.isSelected()) {
            rel_one_layout.setBackground(getResources().getDrawable(R.drawable.repeat_box));
            txt_1.setTextColor(getResources().getColor(R.color.black));
        } else {
            rel_one_layout.setBackground(getResources().getDrawable(R.drawable.repeat_fill_box));
            txt_1.setTextColor(getResources().getColor(R.color.white));
        }
        if (rel_two_layout.isSelected()) {
            rel_two_layout.setBackground(getResources().getDrawable(R.drawable.repeat_box));
            txt_2.setTextColor(getResources().getColor(R.color.black));
        } else {
            rel_two_layout.setBackground(getResources().getDrawable(R.drawable.repeat_fill_box));
            txt_2.setTextColor(getResources().getColor(R.color.white));
        }
        if (rel_three_layout.isSelected()) {
            rel_three_layout.setBackground(getResources().getDrawable(R.drawable.repeat_box));
            txt_3.setTextColor(getResources().getColor(R.color.black));
        } else {
            rel_three_layout.setBackground(getResources().getDrawable(R.drawable.repeat_fill_box));
            txt_3.setTextColor(getResources().getColor(R.color.white));
        }
    }

    private void secondSelected(RelativeLayout relativeLayout) {
        rl_5seonds_layout.setSelected(true);
        rl_15seconds_layout.setSelected(true);
        rl_10seonds_layout.setSelected(true);
        relativeLayout.setSelected(false);
        if (rl_5seonds_layout.isSelected()) {
            rl_5seonds_layout.setBackground(getResources().getDrawable(R.drawable.repeat_box));
            txt_interval_1.setTextColor(getResources().getColor(R.color.black));
        } else {
            rl_5seonds_layout.setBackground(getResources().getDrawable(R.drawable.repeat_fill_box));
            txt_interval_1.setTextColor(getResources().getColor(R.color.white));
        }
        if (rl_10seonds_layout.isSelected()) {
            rl_10seonds_layout.setBackground(getResources().getDrawable(R.drawable.repeat_box));
            txt_interval_2.setTextColor(getResources().getColor(R.color.black));
        } else {
            rl_10seonds_layout.setBackground(getResources().getDrawable(R.drawable.repeat_fill_box));
            txt_interval_2.setTextColor(getResources().getColor(R.color.white));
        }
        if (rl_15seconds_layout.isSelected()) {
            rl_15seconds_layout.setBackground(getResources().getDrawable(R.drawable.repeat_box));
            txt_interval_3.setTextColor(getResources().getColor(R.color.black));
        } else {
            rl_15seconds_layout.setBackground(getResources().getDrawable(R.drawable.repeat_fill_box));
            txt_interval_3.setTextColor(getResources().getColor(R.color.white));
        }
    }

    private void saveWorkout() {
        sqliteHelper = new SqliteHelper(WorkoutStep6Item.this);
        SQLiteDatabase db1 = sqliteHelper.getWritableDatabase();

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("cat_name", obj);
            contentValues.put("time", txt_time.getText().toString().trim());
            contentValues.put("calories", txt_calories.getText().toString().trim());
            contentValues.put("totalexercise", workout);

            db1.insert("workoutname", null, contentValues);
            db1.close();
        } catch (Exception e) {
            Log.e(TAG, "saveWorkout: " + e.getMessage());
        }

        for (int i = 0; i < dragItemArrayList.size(); i++) {
            sqliteHelper = new SqliteHelper(WorkoutStep6Item.this);
            SQLiteDatabase db = sqliteHelper.getWritableDatabase();

            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", dragItemArrayList.get(i).getName());
                contentValues.put("cat_id", dragItemArrayList.get(i).getId());
                contentValues.put("url", dragItemArrayList.get(i).getUrl());
                contentValues.put("image", dragItemArrayList.get(i).getImage());
                contentValues.put("gif", dragItemArrayList.get(i).getGif());
                contentValues.put("cat_name", obj);
                contentValues.put("time", dragItemArrayList.get(i).getTime());
                contentValues.put("calories", txt_calories.getText().toString().trim());
                contentValues.put("totalexercise", workout);
                contentValues.put("cycle", repeatNumber);
                contentValues.put("intervaltime", restSeconds);

                db.insert("customworkout", null, contentValues);
                db.close();
                Intent intent = new Intent(WorkoutStep6Item.this, CustomWorkoutActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } catch (Exception e) {
                Log.e(TAG, "saveWorkout: " + e.getMessage());
            }
        }
    }

}
