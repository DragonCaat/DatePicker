package com.kaha.datepicker.date;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import com.kaha.datepicker.WheelPicker;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Darcy
 * @Date 2019/9/17
 * @package com.kaha.datepicker.date
 * @Desciption
 */
public class MonthPicker extends WheelPicker<String> {

    private static int MAX_MONTH = 12;
    private static int MIN_MONTH = 1;

    private int mSelectedMonth;

    private OnMonthSelectedListener mOnMonthSelectedListener;

    private int mYear;
    private long mMaxDate, mMinDate;
    private int mMaxYear, mMinYear;
    private int mMinMonth = MIN_MONTH;
    private int mMaxMonth = MAX_MONTH;

    public MonthPicker(Context context) {
        this(context, null);
    }

    public MonthPicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonthPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setItemMaximumWidthText("00");
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumIntegerDigits(2);
        //setDataFormat(numberFormat);

        Calendar.getInstance().clear();
        mSelectedMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        updateMonth();

        setSelectedMonth(mSelectedMonth, false);
        setOnWheelChangeListener(new OnWheelChangeListener<String>() {
            @Override
            public void onWheelSelected(String item, int position) {
                String substring = item.substring(0, item.length() - 1);
                int month = Integer.parseInt(substring);
                mSelectedMonth = month;
                if (mOnMonthSelectedListener != null) {
                    mOnMonthSelectedListener.onMonthSelected(month);
                }
            }
        });
    }

    public void updateMonth() {
        List<String> list = new ArrayList<>();
        for (int i = mMinMonth; i <= mMaxMonth; i++) {
            list.add(i + "月");
        }
        setDataList(list);
    }

    public void setMaxDate(long date) {
        mMaxDate = date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        mMaxYear = calendar.get(Calendar.YEAR);
    }

    public void setMinDate(long date) {
        mMinDate = date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        mMinYear = calendar.get(Calendar.YEAR);
    }


    public void setYear(int year) {
        mYear = year;
        mMinMonth = MIN_MONTH;
        mMaxMonth = MAX_MONTH;
        if (mMaxDate != 0 && mMaxYear == year) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mMaxDate);
            mMaxMonth = calendar.get(Calendar.MONTH) + 1;

        }
        if (mMinDate != 0 && mMinYear == year) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mMinDate);
            mMinMonth = calendar.get(Calendar.MONTH) + 1;

        }
        updateMonth();
        if (mSelectedMonth > mMaxMonth) {
            setSelectedMonth(mMaxMonth, false);
        } else if (mSelectedMonth < mMinMonth) {
            setSelectedMonth(mMinMonth, false);
        } else {
            setSelectedMonth(mSelectedMonth, false);
        }
    }

    public int getSelectedMonth() {
        return mSelectedMonth;
    }

    public void setSelectedMonth(int selectedMonth) {
        setSelectedMonth(selectedMonth, true);
    }

    public void setSelectedMonth(int selectedMonth, boolean smoothScroll) {

        setCurrentPosition(selectedMonth - mMinMonth, smoothScroll);
    }

    public void setOnMonthSelectedListener(OnMonthSelectedListener onMonthSelectedListener) {
        mOnMonthSelectedListener = onMonthSelectedListener;
    }

    public interface OnMonthSelectedListener {
        void onMonthSelected(int month);
    }

}
