package apps.tridentfitness.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import apps.tridentfitness.R;
import apps.tridentfitness.adapter.SelectDateAdapter;
import apps.tridentfitness.getset.CompletgetSet;
import apps.tridentfitness.utilHelper.SqliteHelper;

public class SelectDateActivity extends AppCompatActivity {

    private static final String TAG = "SelectDateActivity";
    private RecyclerView selectdate_recycle;
    ArrayList<CompletgetSet> selectdateList;
    private String todays_date;
    TextView txt_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.WHITE);
        setContentView(R.layout.activity_select_date);
        selectdateList = new ArrayList<>();
        getintents();
        init();
        setUpList();
    }

    private void getintents() {
        Intent intent = getIntent();
        todays_date = intent.getStringExtra("date");
    }

    private void init() {
        selectdate_recycle = findViewById(R.id.selectdate_recycle);
        txt_date = findViewById(R.id.txt_date);
        txt_date.setText(todays_date);
    }

    private void setUpList() {

        SqliteHelper sqliteHelper = new SqliteHelper(SelectDateActivity.this);
        SQLiteDatabase db1 = sqliteHelper.getWritableDatabase();
        try {
            Cursor cursor = db1.rawQuery("select * from complete_workout where todays_date  ='" + todays_date + "';", null);
            if (cursor.getCount() != 0) {
                if (cursor.moveToFirst()) {
                    do {
                        String day = cursor.getString(cursor.getColumnIndex("day"));
                        String todays_date = cursor.getString(cursor.getColumnIndex("todays_date"));
                        String months = cursor.getString(cursor.getColumnIndex("month"));
                        String workout_name = cursor.getString(cursor.getColumnIndex("workout_name"));
                        String CURRENT_TIME = cursor.getString(cursor.getColumnIndex("current_time"));
                        String total_time = cursor.getString(cursor.getColumnIndex("total_time"));
                        String calories = cursor.getString(cursor.getColumnIndex("calories"));
                        CompletgetSet completgetSet = new CompletgetSet();
                        completgetSet.setDay(day);
                        completgetSet.setTodays_date(todays_date);
                        completgetSet.setMonth(months);
                        completgetSet.setCalories(calories);
                        completgetSet.setTotal_time(total_time);
                        completgetSet.setWorkout_name(workout_name);
                        completgetSet.setCurrent_time(CURRENT_TIME);
                        selectdateList.add(completgetSet);

                        updateList();

                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
            db1.close();

        } catch (Exception e) {
            Log.e(TAG, "setUpList: " + e.getMessage());
        }
    }

    private void updateList() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        selectdate_recycle.setLayoutManager(mLayoutManager);
        // Initialize a new instance of RecyclerView Adapter instance
        SelectDateAdapter mAdapter = new SelectDateAdapter(this, selectdateList);
        // Set the adapter for RecyclerView
        selectdate_recycle.setAdapter(mAdapter);
    }

    public String getURLForResource(int resourceId) {
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resourceId).toString();
    }

}
