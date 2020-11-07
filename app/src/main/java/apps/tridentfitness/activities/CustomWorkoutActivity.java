package apps.tridentfitness.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import apps.tridentfitness.R;
import apps.tridentfitness.adapter.CusomWorkoutAdapter;
import apps.tridentfitness.getset.CusomItem;
import apps.tridentfitness.utilHelper.SqliteHelper;

public class CustomWorkoutActivity extends AppCompatActivity {
    TextView txt_detail, txt_color;
    ListView listView;
    Button btn_addworkout;
    ImageView img_back;
    String name;
    SqliteHelper sqliteHelper;
    CusomWorkoutAdapter adapter;
    ArrayList<CusomItem> cusomItemArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.WHITE);
        setContentView(R.layout.activity_custom);
        init();
        getList();
    }

    private void getList() {
        try {
            sqliteHelper = new SqliteHelper(CustomWorkoutActivity.this);
            SQLiteDatabase db1 = sqliteHelper.getWritableDatabase();
            Cursor cur = db1.rawQuery("select * from workoutname;", null);
            if (cur.getCount() != 0) {
                if (cur.moveToFirst()) {
                    do {
                        CusomItem cusomItem = new CusomItem();
                        name = cur.getString(cur.getColumnIndex("cat_name"));
                        String id = cur.getString(cur.getColumnIndex("id"));
                        String time = cur.getString(cur.getColumnIndex("time"));
                        String calories = cur.getString(cur.getColumnIndex("calories"));
                        String exercise = cur.getString(cur.getColumnIndex("totalexercise"));

                        cusomItem.setDetail(name);
                        cusomItem.setId(id);
                        cusomItem.setNum_calories(calories);
                        cusomItem.setNum_time(time);
                        cusomItem.setNum_excecise(exercise);
                        cusomItemArrayList.add(cusomItem);
                    } while (cur.moveToNext());
                }
            }
            cur.close();
            db1.close();
        } catch (SQLException e) {
            Log.e("", "onCreate: " + e.getCause());
            e.printStackTrace();
        }

        adapter = new CusomWorkoutAdapter(CustomWorkoutActivity.this, cusomItemArrayList);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CustomWorkoutActivity.this, CustomWorkoutItem.class);
                intent.putExtra("cat_name", cusomItemArrayList.get(position).getDetail());
                intent.putExtra("time", cusomItemArrayList.get(position).getNum_time());
                intent.putExtra("name", cusomItemArrayList.get(position).getDetail());
                intent.putExtra("cal", cusomItemArrayList.get(position).getNum_calories());
                intent.putExtra("tot_ex", cusomItemArrayList.get(position).getNum_excecise());
                startActivity(intent);
            }
        });

    }

    private void init() {
        cusomItemArrayList = new ArrayList<>();
        txt_detail = findViewById(R.id.txt_detail);
        listView = findViewById(R.id.listview);
        img_back = findViewById(R.id.img_back);
        btn_addworkout = findViewById(R.id.btn_addWorkout);
        btn_addworkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomWorkoutActivity.this, WorkoutStep1to4Item.class);
                startActivity(intent);
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CustomWorkoutActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finishAffinity();
    }
}
