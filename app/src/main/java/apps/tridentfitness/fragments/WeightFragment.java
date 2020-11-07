package apps.tridentfitness.fragments;


import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

//import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import net.skoumal.fragmentback.BackFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import apps.tridentfitness.activities.MainActivity;
import apps.tridentfitness.getset.WeightgetSet;
import apps.tridentfitness.utilHelper.SPmanager;
import apps.tridentfitness.utilHelper.SqliteHelper;
import apps.tridentfitness.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeightFragment extends Fragment implements View.OnClickListener, BackFragment {

    private static final String TAG = "WeightFragment";
    private TextView btn_week, btn_all, btn_year, btn_month, txt_start, txt_current, txt_change;
    private View view;
    Button btn_weight;
    private LineChart chart;
    private Log Print;
    private String weight;
    ArrayList<WeightgetSet> yAxisData;
    boolean isweek = true, isMonth, isYear, isAll;
    private String[] calendarDays;
    private String start_weight, current_weight;
    boolean iskg = true, islb;
    private SqliteHelper sqliteHelper;
    private SQLiteDatabase db1;
    private String today_date;
    private ImageView img_change;
    ArrayList<String> xdata;
    private SimpleDateFormat sdf;
    private XAxis xAcis;
    private ArrayList<String> labels;

    public WeightFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_weight, container, false);
        yAxisData = new ArrayList<>();
        xdata = new ArrayList<>();
        init();
        return view;
    }

    private void init() {
        btn_week = view.findViewById(R.id.btn_week);
        btn_month = view.findViewById(R.id.btn_month);
        btn_year = view.findViewById(R.id.btn_year);
        btn_all = view.findViewById(R.id.btn_all);
        txt_start = view.findViewById(R.id.txt_start);
        txt_current = view.findViewById(R.id.txt_current);
        txt_change = view.findViewById(R.id.txt_change);
        btn_weight = view.findViewById(R.id.btn_weight);
        img_change = view.findViewById(R.id.img_change);
        chart = view.findViewById(R.id.chart);
        btn_week.setOnClickListener(this);
        btn_year.setOnClickListener(this);
        btn_month.setOnClickListener(this);
        btn_all.setOnClickListener(this);
        btn_weight.setOnClickListener(this);
        start_weight = SPmanager.getPreference(view.getContext(), "startweight");
        current_weight = SPmanager.getPreference(view.getContext(), "currentweight");
        getweightdata();
        if (yAxisData.size() != 0) {
            setChartdata();
        }
    }


    private void setChartdata() {
        chart.setScaleEnabled(false);
        chart.setVisibility(View.VISIBLE);

        final List<Entry> entries = new ArrayList<Entry>();
        labels = new ArrayList<String>();

        Float max = Float.valueOf(0);
        //display chart value in desc order
        for (int i = 0; i < yAxisData.size(); i++) {
            entries.add(new Entry(i, Float.parseFloat(yAxisData.get(i).getWeight())));
            max = Math.max(Float.parseFloat(yAxisData.get(i).getWeight()), max);
            labels.add(getFullDateFormat(i).toUpperCase());
        }
        Log.e("max", String.valueOf(max));

        LineDataSet dataSet = new LineDataSet(entries, ""); // add entries to dataset
        dataSet.setColor(Color.BLACK);
        dataSet.setHighlightEnabled(true);
        dataSet.setHighLightColor(Color.TRANSPARENT);
        dataSet.setValueTextSize(5);
        dataSet.setCircleColor(getContext().getResources().getColor(R.color.green));
        dataSet.setCircleColorHole(getContext().getResources().getColor(R.color.green));
        dataSet.setColor(getContext().getResources().getColor(R.color.green));
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setFillColor(getResources().getColor(R.color.green));
        dataSet.setDrawFilled(true);
        dataSet.setCircleSize(3);
        YAxis rightYAxis = chart.getAxisRight();
        rightYAxis.setEnabled(false);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setEnabled(true);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setGridColor(Color.LTGRAY);
        leftAxis.enableGridDashedLine(10, 5, 0);
        leftAxis.setTextSize(10);
        leftAxis.setAxisMinimum(0);
        leftAxis.setAxisMaximum(Math.round(max + 20));

        XAxis xAcis = chart.getXAxis();
        xAcis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAcis.setDrawLabels(true);
        xAcis.setDrawGridLines(false);
        xAcis.setDrawAxisLine(false);
        xAcis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int v = Math.round(value);
                String data = "";
                if (v == 0) {
                    data = labels.get(0);
                } else if (v != -1 && !(labels.size() == v)) {
                    try {
                        data = labels.get(v);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    data = "";
                }
                return data;
            }

        });

        xAcis.setTextSize(10);
        xAcis.setGranularity(1f);
        xAcis.setLabelCount(7);
        chart.setDragEnabled(false);
        LineData lineData = new LineData(dataSet);
        lineData.setDrawValues(false);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.setData(lineData);

        float xMax = chart.getData().getDataSetByIndex(0).getXMax();
        if (!isweek && labels.size() != 7)
            xMax = xMax + 1;
        else if (isweek && labels.size() != 7)
            xMax = xMax + 1;
        float xMin = -1;

        xAcis.setAxisMaximum(xMax);
        xAcis.setAxisMinimum(xMin);
        if (isYear) {
            xAcis.setSpaceMin(52);
            chart.setVisibleXRange(0, 365);
            chart.moveViewToX(yAxisData.size());
            chart.notifyDataSetChanged();
        } else if (isMonth) {

            chart.setVisibleXRange(0, 32);
            xAcis.setSpaceMin(4);
            chart.notifyDataSetChanged();
        } else if (isweek) {
            chart.setVisibleXRange(0, 7);
            chart.moveViewToX(yAxisData.size());
            chart.notifyDataSetChanged();
        }
        chart.invalidate();
        chart.notifyDataSetChanged();
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                LineDataSet dataSet = new LineDataSet(entries, ""); // add entries to dataset
                dataSet.setHighlightEnabled(true);
                dataSet.setHighLightColor(Color.TRANSPARENT);
                dataSet.setValueTextSize(5);
                dataSet.setCircleColor(getContext().getResources().getColor(R.color.green));
                dataSet.setCircleColorHole(getContext().getResources().getColor(R.color.green));
                dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                dataSet.setFillColor(getResources().getColor(R.color.green));
                dataSet.setDrawFilled(true);
                dataSet.setCircleSize(3);
                LineData lineData = new LineData(dataSet);
                if (chart.getData().getDataSets().get(0).getColor() == getContext().getResources().getColor(R.color.green)) {
                    dataSet.setColor(getContext().getResources().getColor(R.color.green));
                    lineData.setDrawValues(true);
                } else if (chart.getData().getDataSets().get(0).getColor() == getContext().getResources().getColor(R.color.green)) {
                    dataSet.setColor(getContext().getResources().getColor(R.color.green));
                    lineData.setDrawValues(false);
                }
                chart.setData(lineData);
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }


    private String getFullDateFormat(int position) {
        SimpleDateFormat f = new SimpleDateFormat("E,dd MMM yyyy");
        String fullDate = "";
        try {
            Date fd = f.parse(yAxisData.get(position).getDays());
            if (isYear)
                sdf = new SimpleDateFormat("dd MMM");
            else if (isMonth) {
                XAxis xAxis = chart.getXAxis();
                xAxis.setLabelCount(7);
                sdf = new SimpleDateFormat("MMM dd");
            } else if (isweek) {
                sdf = new SimpleDateFormat("E");
            } else if (isAll) {
                sdf = new SimpleDateFormat("E,dd MMM");
            }
            fullDate = sdf.format(fd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fullDate;
    }


    private void getweightdata() {
        sqliteHelper = new SqliteHelper(view.getContext());
        SQLiteDatabase db1 = sqliteHelper.getWritableDatabase();
        try {
            Cursor cur = db1.rawQuery("select * from weight_data", null);
            Log.e("getweightdata", "" + cur);
            Log.d("getweightdata", "" + cur.getCount());
            if (cur.getCount() != 0) {
                if (cur.moveToFirst()) {
                    do {

                        String first_time = cur.getString(cur.getColumnIndex("first_time"));
                        String current_weight = cur.getString(cur.getColumnIndex("current_weight"));
                        String date = cur.getString(cur.getColumnIndex("day"));
                        WeightgetSet weightgetSet = new WeightgetSet();

                        weightgetSet.setWeight(current_weight);
                        weightgetSet.setDays(date);
                        yAxisData.add(weightgetSet);

                        SQLiteDatabase db2 = sqliteHelper.getWritableDatabase();
                        try {
                            Cursor curs = db1.rawQuery("select * from weight_data Where first_time ='" + first_time + "';", null);
                            if (curs.getCount() != 0) {
                                if (curs.moveToFirst()) {
                                    do {
                                        String weight = cur.getString(cur.getColumnIndex("weight"));
                                        txt_start.setText(weight);
                                    } while (curs.moveToNext());
                                }
                            }
                            curs.close();
                            db2.close();
                        } catch (Exception e) {
                            Log.e(TAG, "getweightdata: " + e.getMessage());
                            // TODO: handle exception
                        }


                        txt_current.setText(current_weight);

                        String current = txt_current.getText().toString().trim();
                        String start = txt_start.getText().toString().trim();

                        double chamge = Double.parseDouble(current) - Double.parseDouble(start);
                        txt_change.setText(String.valueOf(chamge));

                        if (chamge > 0) {
                            img_change.setVisibility(View.VISIBLE);
                            img_change.setBackground(view.getResources().getDrawable(R.drawable.up_arrow));
                        } else if (chamge < 0) {
                            img_change.setVisibility(View.VISIBLE);
                            img_change.setBackground(view.getResources().getDrawable(R.drawable.down_arrow));
                        } else {
                            img_change.setVisibility(View.GONE);
                        }


                    } while (cur.moveToNext());
                }
            }
            cur.close();
            db1.close();
        } catch (
                Exception e) {
            Log.e(TAG, "getweightdata: " + e.getMessage());
            // TODO: handle exception
        }

    }


    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {

            case R.id.btn_week:
                isMonth = false;
                isYear = false;
                isAll = false;
                isweek = true;
                btn_week.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.btn_selected));
                btn_year.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.btn_unselected));
                btn_all.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.btn_unselected));
                btn_month.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.btn_unselected));
                btn_all.setTextColor(getResources().getColor(R.color.black));
                btn_month.setTextColor(getResources().getColor(R.color.black));
                btn_year.setTextColor(getResources().getColor(R.color.black));
                btn_week.setTextColor(getResources().getColor(R.color.white));
//                getweightdata();
                if (yAxisData.size() != 0) {
                    setChartdata();
                }
                break;
            case R.id.btn_month:
                isweek = false;
                isYear = false;
                isAll = false;
                isMonth = true;
                btn_month.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.btn_selected));
                btn_week.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.btn_unselected));
                btn_year.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.btn_unselected));
                btn_all.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.btn_unselected));

                btn_month.setTextColor(getResources().getColor(R.color.white));
                btn_all.setTextColor(getResources().getColor(R.color.black));
                btn_year.setTextColor(getResources().getColor(R.color.black));
                btn_week.setTextColor(getResources().getColor(R.color.black));
//                getweightdata();
                if (yAxisData.size() != 0) {
                    setChartdata();
                }

                break;
            case R.id.btn_year:
                isweek = false;
                isMonth = false;
                isAll = false;
                isYear = true;
                btn_week.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.btn_unselected));
                btn_year.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.btn_selected));
                btn_all.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.btn_unselected));
                btn_month.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.btn_unselected));
                btn_year.setTextColor(getResources().getColor(R.color.white));
                btn_all.setTextColor(getResources().getColor(R.color.black));
                btn_month.setTextColor(getResources().getColor(R.color.black));
                btn_week.setTextColor(getResources().getColor(R.color.black));
//                getweightdata();
                if (yAxisData.size() != 0) {
                    setChartdata();
                }
                break;
            case R.id.btn_all:
                isAll = true;
                isweek = false;
                isYear = false;
                isMonth = false;
                btn_week.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.btn_unselected));
                btn_year.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.btn_unselected));
                btn_all.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.btn_selected));
                btn_month.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.btn_unselected));
                btn_all.setTextColor(getResources().getColor(R.color.white));
                btn_month.setTextColor(getResources().getColor(R.color.black));
                btn_year.setTextColor(getResources().getColor(R.color.black));
                btn_week.setTextColor(getResources().getColor(R.color.black));
//                getweightdata();
                if (yAxisData.size() != 0) {
                    setChartdata();
                }

                break;
            case R.id.btn_weight:
                try {
                    openWeightDialog();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    private void openWeightDialog() throws ParseException {
        final Dialog dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.weight_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        final EditText edt_date = dialog.findViewById(R.id.edt_date);
        final EditText edt_weight = dialog.findViewById(R.id.edt_weight);
        final TextView btn_kg = dialog.findViewById(R.id.btn_kg);
        TextView btn_cancel = dialog.findViewById(R.id.btn_cancel);
        TextView btn_save = dialog.findViewById(R.id.btn_save);
        final TextView btn_lb = dialog.findViewById(R.id.btn_lb);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("E,dd MMM yyyy");
        String formattedDate = df.format(c);
        edt_date.setText(formattedDate);
        edt_weight.setText(txt_current.getText().toString().trim());
        if (iskg) {
            btn_kg.setBackground(ContextCompat.getDrawable(dialog.getContext(), R.drawable.btn_selected_weight));
            btn_lb.setBackground(ContextCompat.getDrawable(dialog.getContext(), R.drawable.btn_unselected_weight));
            btn_kg.setTextColor(getResources().getColor(R.color.white));
            btn_lb.setTextColor(getResources().getColor(R.color.black));
        } else {
            btn_lb.setBackground(ContextCompat.getDrawable(dialog.getContext(), R.drawable.btn_selected_weight));
            btn_kg.setBackground(ContextCompat.getDrawable(dialog.getContext(), R.drawable.btn_unselected_weight));
            btn_lb.setTextColor(getResources().getColor(R.color.white));
            btn_kg.setTextColor(getResources().getColor(R.color.black));
        }

        btn_kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_kg.setBackground(ContextCompat.getDrawable(dialog.getContext(), R.drawable.btn_selected_weight));
                btn_lb.setBackground(ContextCompat.getDrawable(dialog.getContext(), R.drawable.btn_unselected_weight));
                btn_kg.setTextColor(getResources().getColor(R.color.white));
                btn_lb.setTextColor(getResources().getColor(R.color.black));
                String weight = edt_weight.getText().toString().trim();

                if (!weight.isEmpty() && islb) {
                    double kg = Double.parseDouble(weight) / 2.205;
                    float value = (float) Math.round(kg * 100) / 100;
                    edt_weight.setText(String.valueOf(value));
                }
                iskg = true;
                islb = false;
            }
        });
        btn_lb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_lb.setBackground(ContextCompat.getDrawable(dialog.getContext(), R.drawable.btn_selected_weight));
                btn_kg.setBackground(ContextCompat.getDrawable(dialog.getContext(), R.drawable.btn_unselected_weight));
                btn_lb.setTextColor(getResources().getColor(R.color.white));
                btn_kg.setTextColor(getResources().getColor(R.color.black));
                String weight = edt_weight.getText().toString().trim();
                if (!weight.isEmpty() && iskg) {
                    double lbs = Double.parseDouble(weight) * 2.205;
                    float value = (float) Math.round(lbs * 100) / 100;
                    edt_weight.setText(String.valueOf(value));
                }
                iskg = false;
                islb = true;
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                today_date = edt_date.getText().toString();
                weight = edt_weight.getText().toString().trim();
                sqliteHelper = new SqliteHelper(view.getContext());
                db1 = sqliteHelper.getWritableDatabase();
                Cursor cur = db1.rawQuery("SELECT * FROM weight_data Where day ='" + edt_date.getText().toString() + "';", null);
                if (cur.moveToFirst()) {

                    updateWeight();
                    getweightdata();
                    cur.close();
                    db1.close();
                } else {
                    insertWeight();
                    getweightdata();
                    cur.close();
                    db1.close();
                }
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(WeightFragment.this).attach(WeightFragment.this).commit();

                dialog.dismiss();
            }
        });


    }

    private void insertWeight() {

        sqliteHelper = new SqliteHelper(view.getContext());
        db1 = sqliteHelper.getWritableDatabase();
        try {
            ContentValues insertValues = new ContentValues();
            if (!SPmanager.getFirstTimeWeight(view.getContext())) {
                insertValues.put("first_time", today_date);
                SPmanager.setFirstTimeWeight(view.getContext(), true);
            }
            insertValues.put("weight", weight);
            insertValues.put("current_weight", weight);
            insertValues.put("day", today_date);
            db1.insert("weight_data", null, insertValues);
            db1.close();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Menudetails", "onMinusClicked: " + e.getMessage());
        }

    }

    private void updateWeight() {

        sqliteHelper = new SqliteHelper(view.getContext());
        db1 = sqliteHelper.getWritableDatabase();
        try {
            ContentValues insertValues = new ContentValues();

            insertValues.put("weight", weight);
            Log.e(TAG, "updateWeight: weight" + weight);

            insertValues.put("current_weight", weight);
            Log.e(TAG, "updateWeight: current_weight" + weight);

            insertValues.put("day", today_date);
            Log.e(TAG, "updateWeight: current_date" + today_date);

            db1.update("weight_data", insertValues, "day= ?", new String[]{today_date});
            db1.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "updateWeight: " + e.getMessage());
        }

    }

    @Override
    public boolean onBackPressed() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(intent);
        return false;
    }

    @Override
    public int getBackPriority() {
        return NORMAL_BACK_PRIORITY;
    }
}
