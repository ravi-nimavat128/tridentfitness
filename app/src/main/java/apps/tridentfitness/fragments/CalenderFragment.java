package apps.tridentfitness.fragments;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.skoumal.fragmentback.BackFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import apps.tridentfitness.acs_calender.CalendarSelecterView;
import apps.tridentfitness.acs_calender.MonthChangeListenr;
import apps.tridentfitness.activities.MainActivity;
import apps.tridentfitness.getset.CompletgetSet;
import apps.tridentfitness.utilHelper.CustomViewPagers;
import apps.tridentfitness.utilHelper.SqliteHelper;
import apps.tridentfitness.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalenderFragment extends Fragment implements BackFragment {
    private static final String TAG = "CalenderFragment";
    public static CustomViewPagers mViewPager;
    private View view;
    TextView txt_totalworkout, txt_total_time, total_calories;
    ArrayList<CompletgetSet> worklist;

    public CalenderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_calender, container, false);
        worklist = new ArrayList<>();
        init();
        getDatabase();
        return view;
    }

    private void getDatabase() {
        SqliteHelper sqliteHelper = new SqliteHelper(view.getContext());
        SQLiteDatabase db1 = sqliteHelper.getWritableDatabase();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String todays_date = simpleDateFormat.format(calendar.getTime());
        try {
            Cursor cursor = db1.rawQuery("select * from complete_workout ", null);
            if (cursor.getCount() != 0) {
                if (cursor.moveToFirst()) {
                    do {
                        String total_exercise = cursor.getString(cursor.getColumnIndex("total_exercise"));
                        String total_time = cursor.getString(cursor.getColumnIndex("total_time"));
                        String calories = cursor.getString(cursor.getColumnIndex("calories"));

                        CompletgetSet completgetSet = new CompletgetSet();
                        completgetSet.setTotal_time(total_time);
                        completgetSet.setTotal_exercise(total_exercise);
                        completgetSet.setCalories(calories);
                        worklist.add(completgetSet);
                        txt_total_time.setText(total_time);
                        total_calories.setText(calories);
                    } while (cursor.moveToNext());
                }
            }
            int sum = 0;
            int sumOfcalories = 0;
            for (int i = 0; i < worklist.size(); i++) {
                sum += Integer.parseInt(worklist.get(i).getTotal_time());
                sumOfcalories += Integer.parseInt(worklist.get(i).getCalories());
            }
            txt_totalworkout.setText(String.valueOf(worklist.size()));
            txt_total_time.setText(String.valueOf(sum));
            total_calories.setText(String.valueOf(sumOfcalories));
            cursor.close();
            db1.close();

        } catch (Exception e) {
            Log.e(TAG, "setUpList: " + e.getMessage());
        }
    }

    private void init() {
        mViewPager = (CustomViewPagers) view.findViewById(R.id.calendar_pager);
        mViewPager.setAdapter(new CalendarAdapter());
        mViewPager.setCurrentItem(Integer.MAX_VALUE / 2);

        txt_totalworkout = view.findViewById(R.id.txt_totalworkout);
        txt_total_time = view.findViewById(R.id.txt_total_time);
        total_calories = view.findViewById(R.id.total_calories);
        Calendar c = Calendar.getInstance();
    }

    private static Calendar getCalendarForNow() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new Date());
        return calendar;
    }

    MonthChangeListenr mMonthChangeListenr = new MonthChangeListenr() {

        @Override
        public void LastMonthClicked() {
            mViewPager.arrowScroll(View.FOCUS_LEFT);
        }

        @Override
        public void nextMonthClicker() {
            mViewPager.arrowScroll(View.FOCUS_RIGHT);
        }
    };

    class CalendarAdapter extends PagerAdapter {
        CalendarSelecterView[] views = new CalendarSelecterView[5];
        private Calendar mCalendar;

        public CalendarAdapter() {
            for (int i = 0; i < 5; i++) {
                views[i] = new CalendarSelecterView(view.getContext());
                views[i].setOnMonthChangeListenr(mMonthChangeListenr);
            }
            mCalendar = Calendar.getInstance();
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View collection, int position, Object view) {
            ((CustomViewPagers) collection).removeView((View) view);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            CalendarSelecterView v = views[position % 5];
            int months = Integer.MAX_VALUE / 2 - position;
            Calendar febFirst = (Calendar) mCalendar.clone();
            febFirst.add(Calendar.MONTH, -(months));
            int year = febFirst.get(Calendar.YEAR);
            int month = febFirst.get(Calendar.MONTH);
            SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
            String month_name = month_date.format(febFirst.getTime());
            SimpleDateFormat _date = new SimpleDateFormat("dd");
            String date_name = _date.format(febFirst.getTime());
            v.setTime(year, month_name, date_name, false);
            container.addView(v);
            return v;
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
