package apps.tridentfitness.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import apps.tridentfitness.R;
import apps.tridentfitness.getset.detailpageGetSet;
import apps.tridentfitness.utilHelper.SPmanager;

public class ReworkoutActivity extends AppCompatActivity {

    private String key;
    private String times;
    private String name;
    private String cal;
    private String tot_ex;
    private String circuit;
    private ArrayList<detailpageGetSet> workoutList;
    private String cycle;
    private int circuitnumber;
    private String rest_time;
    private int count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.WHITE);
        setContentView(R.layout.activity_workout);
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
    }

    private void getIntens() {
        Intent intent = getIntent();
        key = intent.getStringExtra("layout");
        times = intent.getStringExtra("time");
        name = intent.getStringExtra("name");
        cal = intent.getStringExtra("cal");
        tot_ex = intent.getStringExtra("tot_ex");
        circuit = SPmanager.getPreference(this, "circut");
    }
}
