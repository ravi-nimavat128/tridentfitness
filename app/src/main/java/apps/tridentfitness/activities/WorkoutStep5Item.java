package apps.tridentfitness.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import apps.tridentfitness.R;
import apps.tridentfitness.adapter.CustomListView;
import apps.tridentfitness.adapter.DragAdapter;
import apps.tridentfitness.getset.SelectItem;
import apps.tridentfitness.utilHelper.MyApplication;

public class WorkoutStep5Item extends AppCompatActivity {
    CustomListView listView;
    RelativeLayout relativeLayout;
    RelativeLayout rel_step;
    TextView txt_workout;
    TextView txt_selectexcercise;
    Button btn_addWorkout;
    ArrayList<SelectItem> list1 = new ArrayList<>();
    ArrayList<SelectItem> list2 = new ArrayList<>();
    ArrayList<SelectItem> list3 = new ArrayList<>();
    ArrayList<SelectItem> list4 = new ArrayList<>();
    String name;
    String id;
    String url;
    private MyApplication mApp;
    ArrayList<SelectItem> dragItemArrayList;
    DragAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.WHITE);
        setContentView(R.layout.activity_workout_step5_item);
        mApp = MyApplication.getInstance();
        init();
        getList();
    }

    private void init() {
        dragItemArrayList = new ArrayList<>();
        relativeLayout = findViewById(R.id.rel_header);
        rel_step = findViewById(R.id.rel_step);
        listView = findViewById(R.id.listView1);
        txt_workout = findViewById(R.id.txt_workout);
        txt_selectexcercise = findViewById(R.id.txt_selectexcercise);
        btn_addWorkout = findViewById(R.id.btn_addWorkout);
    }

    private void getList() {
        list1 = mApp.getStep1();
        list2 = mApp.getStep2();
        list3 = mApp.getStep3();
        list4 = mApp.getStep4();


        dragItemArrayList.addAll(list1);
        dragItemArrayList.addAll(list2);
        dragItemArrayList.addAll(list3);
        dragItemArrayList.addAll(list4);

        double sum = 0;
        double sum1 = 0;
        double total = 0;
        float value = 0;
        float value1 = 0;
        int calories = 0;
        int time = 0;
        int time1 = 0;
        for (int i = 0; i < dragItemArrayList.size(); i++) {
            SelectItem selectItem = new SelectItem();
            selectItem.setName(dragItemArrayList.get(i).getName());
            selectItem.setId(dragItemArrayList.get(i).getId());
            selectItem.setUrl(dragItemArrayList.get(i).getUrl());
            selectItem.setTime(dragItemArrayList.get(i).getTime());
            selectItem.setCalories(dragItemArrayList.get(i).getCalories());
            selectItem.setCalories(dragItemArrayList.get(i).getGif());

            sum += Double.parseDouble(dragItemArrayList.get(i).getCalories());
            value = (float) Math.round(sum * 100) / 100;
            calories = Math.round(value);
            Log.e("", "getList:AD " + value);

            sum1 += Double.parseDouble(dragItemArrayList.get(i).getTime());
            total = sum1 / 60;
            value1 = (float) Math.round(total * 100) / 100;
            time = Math.round(value1);

            Log.e("", "getList:AD " + value1);


        }

        adapter = new DragAdapter(this, dragItemArrayList, new DragAdapter.Listener() {
            @Override
            public void onGrab(int position, RelativeLayout row) {
                listView.onGrab(position, row);
            }
        });
        listView.setAdapter(adapter);

        listView.setListener(new CustomListView.Listener() {
            @Override
            public void swapElements(int indexOne, int indexTwo) {
                SelectItem temp = dragItemArrayList.get(indexOne);
                dragItemArrayList.set(indexOne, dragItemArrayList.get(indexTwo));
                dragItemArrayList.set(indexTwo, temp);
                adapter.notifyDataSetChanged();
                adapter.notifyDataSetInvalidated();
                listView.invalidateViews();
            }
        });

        mApp.setMainDragList(dragItemArrayList);

        final int finalCalories = calories;
        final int finalTime = time;
        btn_addWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutStep5Item.this, WorkoutStep6Item.class);
                intent.putExtra("calories", finalCalories);
                intent.putExtra("time", finalTime);
                intent.putExtra("workout", dragItemArrayList.size());
                startActivity(intent);
            }
        });
    }
}
