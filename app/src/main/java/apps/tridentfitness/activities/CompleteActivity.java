package apps.tridentfitness.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import apps.tridentfitness.R;
import apps.tridentfitness.utilHelper.SqliteHelper;

public class CompleteActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "CompleteActivity";
    ImageView img_sad, img_medium, img_happyreate;
    TextView txt_excecise, txt_minutes, txt_workoutname, txt_save, txt_calories;
    private boolean isSad, isMedium, ishappy;
    private SqliteHelper sqliteHelper;
    private String time, cal, tot_ex, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.WHITE);
        setContentView(R.layout.activity_complete);
        getintents();
        init();
    }
    private void getintents() {
        Intent intent = getIntent();
        time = intent.getStringExtra("time");
        name = intent.getStringExtra("name");
        cal = intent.getStringExtra("cal");
        tot_ex = intent.getStringExtra("tot_ex");
    }
    private void init() {
        img_sad = findViewById(R.id.img_sad);
        img_medium = findViewById(R.id.img_medium);
        img_happyreate = findViewById(R.id.img_happyreate);
        txt_excecise = findViewById(R.id.txt_excecise);
        txt_minutes = findViewById(R.id.txt_minutes);
        txt_calories = findViewById(R.id.txt_calories);
        txt_save = findViewById(R.id.txt_save);
        txt_workoutname = findViewById(R.id.txt_workoutname);

        txt_excecise.setText(tot_ex);
        txt_minutes.setText(time);
        txt_calories.setText(cal);
        txt_workoutname.setText(name);

        img_sad.setOnClickListener(this);
        img_medium.setOnClickListener(this);
        img_happyreate.setOnClickListener(this);
        txt_save.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int viewID = v.getId();
        switch (viewID) {
            case R.id.img_sad:
                img_sad.setColorFilter(ContextCompat.getColor(CompleteActivity.this, R.color.greensy), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_medium.setColorFilter(ContextCompat.getColor(CompleteActivity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_happyreate.setColorFilter(ContextCompat.getColor(CompleteActivity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                isSad = true;
                isMedium = false;
                ishappy = false;
                break;
            case R.id.img_medium:
                img_sad.setColorFilter(ContextCompat.getColor(CompleteActivity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_medium.setColorFilter(ContextCompat.getColor(CompleteActivity.this, R.color.greensy), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_happyreate.setColorFilter(ContextCompat.getColor(CompleteActivity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                isSad = false;
                isMedium = true;
                ishappy = false;
                break;
            case R.id.img_happyreate:
                img_sad.setColorFilter(ContextCompat.getColor(CompleteActivity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_medium.setColorFilter(ContextCompat.getColor(CompleteActivity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                img_happyreate.setColorFilter(ContextCompat.getColor(CompleteActivity.this, R.color.greensy), android.graphics.PorterDuff.Mode.MULTIPLY);
                isSad = false;
                isMedium = false;
                ishappy = true;
                break;
            case R.id.txt_save:
                saveDatabase();
                break;
        }
    }

    private void saveDatabase() {
        sqliteHelper = new SqliteHelper(this);
        SQLiteDatabase db1 = sqliteHelper.getWritableDatabase();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
        String currentTime = df.format(calendar.getTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat sf = new SimpleDateFormat("dd");
        String todays_date = simpleDateFormat.format(calendar.getTime());
        String day = sf.format(calendar.getTime());
        SimpleDateFormat mf = new SimpleDateFormat("MMMM");
        String month = mf.format(calendar.getTime());

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("workout_name", txt_workoutname.getText().toString().trim());
            contentValues.put("total_exercise", txt_excecise.getText().toString().trim());
            contentValues.put("current_time", currentTime);
            contentValues.put("total_time", txt_minutes.getText().toString().trim());
            contentValues.put("calories", txt_calories.getText().toString().trim());
            contentValues.put("todays_date", todays_date);
            contentValues.put("day", day);
            contentValues.put("month", month);
            db1.insert("complete_workout", null, contentValues);
            db1.close();

            Intent intent = new Intent(CompleteActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "saveDatabase: " + e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent intent = new Intent(CompleteActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}
