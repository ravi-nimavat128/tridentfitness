package apps.tridentfitness.acs_calender;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import apps.tridentfitness.activities.SelectDateActivity;
import apps.tridentfitness.utilHelper.SqliteHelper;
import apps.tridentfitness.R;
import apps.tridentfitness.getset.CompletgetSet;

/**
 * Adapter that show days in one month
 *
 * @author Liyanshun
 */
public class CalendarGridAdapter extends BaseAdapter {
    private static final String TAG = "CalendarGridAdapter";
    private Calendar mCalendar;
    private ArrayList<Integer> mDaysArrayList = new ArrayList<Integer>();
    private ArrayList<Integer> mDaysTypeList = new ArrayList<Integer>();
    private Context mContext;
    private final static int DAYS_OF_LAST_MONTHS = 0;
    private final static int DAYS_OF_NEXT_MONTHS = 1;
    private final static int DAYS_OF_CURRENT_MONTH = 2;
    private int mYear;
    private String mMonth;
    private boolean mshowOtherMonth;
    private int mSelectedPosition = -1;
    private int dayOfWeek;
    private SimpleDateFormat df;
    private String month_name;
    private SqliteHelper sqliteHelper;
    private String day;
    private String todays_date;
    private String months;
    ArrayList<CompletgetSet> eventList;
    private int monthof;

    public void setCheckedPosition(int checkedPosition) {
        switch (getItemViewType(checkedPosition)) {
            case DAYS_OF_CURRENT_MONTH:
                mSelectedPosition = checkedPosition;
                notifyDataSetChanged();
                break;
            default:
                break;
        }

    }

    public CalendarGridAdapter(Context c) {
        mContext = c;
    }

    public void setTime(int year, String month, boolean showOtherMonth) {
        mSelectedPosition = -1;
        mDaysArrayList.clear();
        mDaysTypeList.clear();
        mYear = year;
        mMonth = month;
        mshowOtherMonth = showOtherMonth;
        mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);

        dayOfWeek = CalendarHelp.getDayNumberOfMonth(CalendarHelp.getCurrentCalendar());
        monthof = CalendarHelp.getMonthOfYear(CalendarHelp.getCurrentCalendar());
        int daySpacing = getDaySpacing(mCalendar.get(Calendar.DAY_OF_WEEK));
        if (daySpacing > 0) {
            Calendar lastMonth = (Calendar) mCalendar.clone();
            lastMonth.add(Calendar.MONTH, -1);
            lastMonth.set(Calendar.DAY_OF_MONTH,
                    lastMonth.getActualMaximum(Calendar.DAY_OF_MONTH)
                            - daySpacing + 1);
            for (int i = 0; i < daySpacing; i++) {
                mDaysArrayList.add(lastMonth.get(Calendar.DAY_OF_MONTH));
                mDaysTypeList.add(DAYS_OF_LAST_MONTHS);
                lastMonth.add(Calendar.DAY_OF_MONTH, 1);
            }
        }

        int daysOfThisMonth = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= daysOfThisMonth; i++) {
            mDaysArrayList.add(i);
            mDaysTypeList.add(DAYS_OF_CURRENT_MONTH);
        }

        mCalendar.set(Calendar.DAY_OF_MONTH,
                mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        daySpacing = getDaySpacingEnd(mCalendar.get(Calendar.DAY_OF_WEEK));

        if (daySpacing > 0) {
            Calendar nextMonth = (Calendar) mCalendar.clone();
            nextMonth.add(Calendar.MONTH, 1);
            nextMonth.set(Calendar.DAY_OF_MONTH, 1);
            for (int i = 0; i < daySpacing; i++) {
                mDaysArrayList.add(nextMonth.get(Calendar.DAY_OF_MONTH));
                mDaysTypeList.add(DAYS_OF_NEXT_MONTHS);
                nextMonth.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
        notifyDataSetChanged();

    }

    private int getDaySpacing(int dayOfWeek) {
        if (Calendar.SATURDAY == dayOfWeek)
            return 6;
        else
            return dayOfWeek - 1;
    }

    private int getDaySpacingEnd(int dayOfWeek) {
        return 7 - dayOfWeek;
    }

    @Override
    public int getCount() {
        return mDaysArrayList.size();
    }

    @Override
    public Integer getItem(int position) {
        return mDaysArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getItemViewType(int position) {
        return mDaysTypeList.get(position);
    }

    public int getViewTypeCount() {
        return 3;
    }

    public String getSelectedDate(int position) {
        String dateString = null;
        switch (getItemViewType(position)) {
            case DAYS_OF_CURRENT_MONTH:
                dateString = mYear + "-" + (mMonth + 1) + "-" + getItem(position);
                break;
            case DAYS_OF_NEXT_MONTHS:
                if (mshowOtherMonth) {
                    dateString = mYear + "-" + (mMonth + 2) + "-"
                            + getItem(position);
                }
                break;
            case DAYS_OF_LAST_MONTHS:
                if (mshowOtherMonth) {
                    dateString = mYear + "-" + (mMonth) + "-" + getItem(position);
                }
                break;
        }
        return dateString;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.grid_item, null);
            eventList = new ArrayList<>();
            getDateDatabase();
            holder.textView = (TextView) convertView
                    .findViewById(R.id.grid_item_text);
            convertView.setTag(holder);
            SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
            month_name = month_date.format(mCalendar.getTime());
            if (String.valueOf(dayOfWeek).startsWith("0")) {
                dayOfWeek = Integer.parseInt(String.valueOf(dayOfWeek).substring(0, 1));
            } else {
                dayOfWeek = dayOfWeek;
            }

            SimpleDateFormat curFormater = new SimpleDateFormat("dd-MMM-yyyy");
            Date dateObj = null;
            try {
                if (todays_date != null) {
                    dateObj = curFormater.parse(todays_date);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat postFormater = new SimpleDateFormat("MMMM");
            if (dateObj != null) {
                months = postFormater.format(dateObj);
            }

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        switch (getItemViewType(position)) {
            case DAYS_OF_CURRENT_MONTH:
                setTextStatus(position, holder);
                break;
            case DAYS_OF_NEXT_MONTHS:
                holder.textView.setVisibility(View.INVISIBLE);
                break;
            case DAYS_OF_LAST_MONTHS:
                if (mshowOtherMonth) {
                    setTextStatus(position, holder);
                } else {
                    holder.textView.setText("");
                    holder.textView.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                break;
        }
        return convertView;
    }

    private void getDateDatabase() {
        sqliteHelper = new SqliteHelper(mContext);
        SQLiteDatabase db1 = sqliteHelper.getWritableDatabase();
        try {
            Cursor cursor = db1.rawQuery("select * from complete_workout", null);
            Log.e(TAG, "getDateDatabase: " + cursor);
            Log.e(TAG, "getDateDatabase: " + cursor.getColumnCount());

            if (cursor.getCount() != 0) {
                if (cursor.moveToFirst()) {
                    do {
                        day = cursor.getString(cursor.getColumnIndex("day"));
                        todays_date = cursor.getString(cursor.getColumnIndex("todays_date"));
                        months = cursor.getString(cursor.getColumnIndex("month"));
                        CompletgetSet completgetSet = new CompletgetSet();
                        completgetSet.setDay(day);
                        completgetSet.setTodays_date(todays_date);
                        completgetSet.setMonth(months);
                        eventList.add(completgetSet);

                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
            db1.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getDateDatabase: " + e.getMessage());
        }
    }

    private void setTextStatus(int position, ViewHolder holder) {
        holder.textView.setText(String.valueOf(getItem(position)));
        {
            if (eventList.size() != 0) {
                for (int i = 0; i < eventList.size(); i++) {
                    String days = eventList.get(i).getDay();
                    String month = eventList.get(i).getMonth();
                    final String todays_date = eventList.get(i).getTodays_date();
                    if (String.valueOf(days).startsWith("0")) {
                        days = days.substring(1);
                    } else {
                        days = days;
                    }
                    for (int j = 0; j <= mDaysArrayList.size(); j++) {
                        try {
                            if (mMonth.equals(month_name) && mDaysArrayList.get(position).equals(dayOfWeek)) {
                                holder.textView.setTextColor(mContext.getResources().getColor(
                                        R.color.white));
                                holder.textView.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_calender_red));

                                if (days.equals(String.valueOf(getItem(position))) && month.equals(mMonth)) {
                                    holder.textView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(mContext, SelectDateActivity.class);
                                            intent.putExtra("date", todays_date);
                                            mContext.startActivity(intent);
                                        }
                                    });
                                } else {
                                    holder.textView.setOnClickListener(null);
                                }

                            } else {
                                if (days.equals(String.valueOf(getItem(position))) && month.equals(mMonth)) {
                                    holder.textView.setTextColor(Color.WHITE);
                                    holder.textView.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_calender_green));
                                    holder.textView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(mContext, SelectDateActivity.class);
                                            intent.putExtra("date", todays_date);
                                            mContext.startActivity(intent);
                                        }
                                    });
                                } else {
                                    holder.textView.setSelected(false);
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }


            } else {
                if (mMonth.equals(month_name) && mDaysArrayList.get(position).equals(dayOfWeek)) {
                    holder.textView.setTextColor(mContext.getResources().getColor(
                            R.color.white));
                    holder.textView.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_calender_red));
                } else {
                    holder.textView.setOnClickListener(null);
                }
            }
        }
    }

    class ViewHolder {
        TextView textView;
    }
}
