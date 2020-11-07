package apps.tridentfitness.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nshmura.snappysmoothscroller.SnappyLinearLayoutManager;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import apps.tridentfitness.R;
import apps.tridentfitness.adapter.CustomButtonListener;
import apps.tridentfitness.adapter.WorkoutListAdapter;
import apps.tridentfitness.getset.SelectItem;
import apps.tridentfitness.getset.detailpageGetSet;
import apps.tridentfitness.utilHelper.MyApplication;
import apps.tridentfitness.utilHelper.SqliteHelper;

public class WorkoutStep1to4Item extends AppCompatActivity implements CustomButtonListener {
    RecyclerView listView;
    public Button btn_addWorkout;
    SqliteHelper sqliteHelper;
    RelativeLayout relativeLayout, rel_step;
    CustomExerciseAdapter adapter;
    ImageView img_back;
    String cat_id;
    private MyApplication mApp;
    TextView txt_workout, txt_selectexcercise;
    ArrayList<SelectItem> workoutArrayList1;
    ArrayList<SelectItem> workoutArrayList2;
    ArrayList<SelectItem> workoutArrayList3;
    ArrayList<SelectItem> workoutArrayList4;
    int key = 0;
    private int numberOfCheckboxesChecked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.WHITE);
        setContentView(R.layout.activity_workout_step_item);
        mApp = MyApplication.getInstance();
        getIntents();
        init();
        getList();
    }

    private void getIntents() {
        Intent intent = getIntent();
        key = intent.getIntExtra("key",1);
    }

    private void init() {
        workoutArrayList1 = new ArrayList<>();
        workoutArrayList2 = new ArrayList<>();
        workoutArrayList3 = new ArrayList<>();
        workoutArrayList4 = new ArrayList<>();
        relativeLayout = findViewById(R.id.rel_header);
        rel_step = findViewById(R.id.rel_step);
        listView = findViewById(R.id.listview);
        img_back = findViewById(R.id.img_back);
        txt_workout = findViewById(R.id.txt_workout);
        txt_selectexcercise = findViewById(R.id.txt_selectexcercise);
        btn_addWorkout = findViewById(R.id.btn_addWorkout);
        if (key == 1){
            txt_selectexcercise.setText(getString(R.string.step1));
        }else if (key == 2){
            txt_selectexcercise.setText(getString(R.string.step2));
        }else if (key == 3){
            txt_selectexcercise.setText(getString(R.string.step3));
        }else if (key == 4){
            txt_selectexcercise.setText(getString(R.string.step4));
        }
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }


    private void getList() {

        try {
            sqliteHelper = new SqliteHelper(WorkoutStep1to4Item.this);
            SQLiteDatabase db1 = sqliteHelper.getWritableDatabase();
            Cursor cur = null;
            if (key == 1) {
                cur = db1.rawQuery("SELECT * FROM exercise Where cat_id ='" + "1" + "';", null);
            }else if (key == 2){
                cur = db1.rawQuery("SELECT * FROM exercise Where cat_id ='" + "2" + "';", null);
            }else  if (key == 3){
                cur = db1.rawQuery("SELECT * FROM exercise Where cat_id ='" + "3" + "';", null);
            }else if (key == 4){
                cur = db1.rawQuery("SELECT * FROM exercise Where cat_id ='" + "4" + "';", null);
            }
            if (cur.getCount() != 0) {
                if (cur.moveToFirst()) {
                    do {
                        SelectItem getSet = new SelectItem();
                        String id = cur.getString(cur.getColumnIndex("id"));
                        String name = cur.getString(cur.getColumnIndex("name"));
                        String image = cur.getString(cur.getColumnIndex("image"));
                        String url = cur.getString(cur.getColumnIndex("url"));
                        String time = cur.getString(cur.getColumnIndex("time"));
                        String calories = cur.getString(cur.getColumnIndex("calories"));
                        String gif = cur.getString(cur.getColumnIndex("gif"));
                        getSet.setUrl(url);
                        getSet.setId(id);
                        getSet.setImage(image);
                        getSet.setName(name);
                        getSet.setTime(time);
                        getSet.setGif(gif);
                        getSet.setCalories(calories);
                        if (key ==1){
                            workoutArrayList1.add(getSet);
                            Log.e("WorkoutStep1to4Item", "getList: "+ workoutArrayList1.size());
                        }else if (key == 2){
                            workoutArrayList2.add(getSet);
                        }else if (key == 3){
                            workoutArrayList3.add(getSet);
                        }else if (key == 4){
                            workoutArrayList4.add(getSet);
                        }
                        Log.e("", "onCreate: " + name);

                    } while (cur.moveToNext());
                }
            }
            cur.close();
            db1.close();
        } catch (Exception e) {
            Log.e("", "onCreate: " + e.getCause());
            e.printStackTrace();
        }


        SnappyLinearLayoutManager verticalLayoutManager1 = new SnappyLinearLayoutManager(WorkoutStep1to4Item.this, SnappyLinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(verticalLayoutManager1);
        if (key ==1){
            adapter = new CustomExerciseAdapter(listView, workoutArrayList1, WorkoutStep1to4Item.this);
        }else if (key == 2){
            adapter = new CustomExerciseAdapter(listView, workoutArrayList2, WorkoutStep1to4Item.this);
        }else if (key == 3){
            adapter = new CustomExerciseAdapter(listView, workoutArrayList3, WorkoutStep1to4Item.this);
        }else if (key == 4){
            adapter = new CustomExerciseAdapter(listView, workoutArrayList4, WorkoutStep1to4Item.this);
        }
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        adapter.setCustomButtonListener(WorkoutStep1to4Item.this);

        btn_addWorkout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (numberOfCheckboxesChecked == 3 && key != 4) {

                    Intent intent = getIntent();
                    key++;
                    intent.putExtra("key",key);
                    startActivity(intent);
                }else if (numberOfCheckboxesChecked == 3){
                    Intent intent = new Intent(WorkoutStep1to4Item.this, WorkoutStep5Item.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(WorkoutStep1to4Item.this, "Please pick any 3 exercise", Toast.LENGTH_SHORT).show();
                }
            }

        });


    }

    @Override
    public void OnImageDisplayclick(int position) {
        Intent intent = new Intent(WorkoutStep1to4Item.this, ExcerciseDetail.class);
        if (key == 1) {
            intent.putExtra("id", workoutArrayList1.get(position).getId());
            intent.putExtra("url", workoutArrayList1.get(position).getUrl());
        }else if (key == 2){
            intent.putExtra("id", workoutArrayList2.get(position).getId());
            intent.putExtra("url", workoutArrayList2.get(position).getUrl());
        }else if (key == 3){
            intent.putExtra("id", workoutArrayList3.get(position).getId());
            intent.putExtra("url", workoutArrayList3.get(position).getUrl());
        }else if (key == 4){
            intent.putExtra("id", workoutArrayList4.get(position).getId());
            intent.putExtra("url", workoutArrayList4.get(position).getUrl());
        }
        startActivity(intent);
    }


    public class CustomExerciseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        Context context;
        ArrayList<SelectItem> rowItems;
        String category;
        private CustomButtonListener listener;
        int selectedposition = -1;
        ArrayList<SelectItem> step1;
        ArrayList<SelectItem> step2;
        ArrayList<SelectItem> step3;
        ArrayList<SelectItem> step4;
        private final LayoutInflater inflater;
        RecyclerView recyclerView;

        public void setCustomButtonListener(CustomButtonListener listener) {
            this.listener = listener;
        }

        public CustomExerciseAdapter(RecyclerView recyclerView, ArrayList<SelectItem> rowItems, Context context) {
            this.context = context;
            this.rowItems = rowItems;
            this.recyclerView = recyclerView;
            this.step1 = new ArrayList<>();
            this.step2 = new ArrayList<>();
            this.step3 = new ArrayList<>();
            this.step4 = new ArrayList<>();
            this.inflater = LayoutInflater.from(context);
        }

        private class ItemViewHolder extends RecyclerView.ViewHolder {

            TextView txt_detail;
            public ImageView imageView, img_arrow;
            final CheckBox unchecked;

            public ItemViewHolder(@NonNull View view) {
                super(view);
                txt_detail = view.findViewById(R.id.txt_detail);
                imageView = view.findViewById(R.id.img_jumping);
                unchecked = view.findViewById(R.id.uncheckBox);
                img_arrow = view.findViewById(R.id.img_arrow);
            }
            /*public void bind(final detailpageGetSet item, final WorkoutListAdapter.OnItemClickListener listener) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(item);
                    }
                });
            }*/
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_selectworkout, parent, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            final SelectItem rowItem = rowItems.get(position);
            ((ItemViewHolder) holder).txt_detail.setText(rowItem.getName());

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Glide.with(context)
                    .load("file:///android_asset/Exercise_img/" +rowItem.getImage())
                    .into(((ItemViewHolder) holder).imageView);

            ((ItemViewHolder) holder).unchecked.setTag(position);
            ((ItemViewHolder) holder).unchecked.setOnCheckedChangeListener(null);
            ((ItemViewHolder) holder).unchecked.setFocusable(false);
            if (rowItems.get(position).isSelect) {
                ((ItemViewHolder) holder).unchecked.setChecked(true);
            } else {
                ((ItemViewHolder) holder).unchecked.setChecked(false);
            }
            ((ItemViewHolder) holder).unchecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (((ItemViewHolder) holder).unchecked.isChecked() && numberOfCheckboxesChecked >= 3) {

                        if (rowItems.get(position).isSelect && isChecked) {
                            ((ItemViewHolder) holder).unchecked.setChecked(false);
                            rowItems.get(position).isSelect = false;
                        } else {
                            ((ItemViewHolder) holder).unchecked.setChecked(false);
                            rowItems.get(position).isSelect = false;
                            Toast.makeText(context, "Max limit reached", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (isChecked) {
                            rowItems.get(position).isSelect = true;

                            rowItem.getId();
                            rowItem.getName();
                            rowItem.getCalories();
                            rowItem.getTime();
                            rowItem.getUrl();
                            if (key == 1) {
                                step1.add(rowItem);
                                mApp.setStep1(step1);
                            }else if (key == 2){
                                step2.add(rowItem);
                                mApp.setStep2(step2);


                            }else if (key ==3){
                                step3.add(rowItem);
                                mApp.setStep3(step3);
                            }else if (key == 4){
                                step4.add(rowItem);
                                mApp.setStep4(step4);
                            }
                            numberOfCheckboxesChecked++;
                        } else {
                            rowItems.get(position).isSelect = false;
                            if (key == 1) {
                                step1.remove(rowItem);
                                mApp.setStep1(step1);
                            }else if (key == 2){
                                step2.remove(rowItem);
                                mApp.setStep1(step2);
                            }else if (key ==3){
                                step3.remove(rowItem);
                                mApp.setStep1(step3);
                            }else if (key == 4){
                                step4.remove(rowItem);
                                mApp.setStep1(step4);
                            }
                            numberOfCheckboxesChecked--;
                        }
                    }
                }
            });
            applyClickEvents(((ItemViewHolder) holder), position);
        }

        @Override
        public int getItemCount() {
            return rowItems.size();
        }
        private void applyClickEvents(ItemViewHolder holder, final int i) {
            ((ItemViewHolder) holder).img_arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnImageDisplayclick(i);

                }
            });
        }
    }
}
