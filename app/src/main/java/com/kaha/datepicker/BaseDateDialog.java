package com.kaha.datepicker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.kaha.datepicker.date.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 日期选择框的基类
 *
 * @author Darcy
 * @Date 2019/9/19
 * @package com.app.baby.widget.datedialog
 * @Desciption
 */
public abstract class BaseDateDialog extends Dialog {

    @BindView(R.id.datePicker)
    DatePicker datePicker;

    @BindView(R.id.tv_date_des)
    TextView tvDateDes;

    private Activity activity;

    private int dateModel = 0;//日期弹框的模式
    //选中的日期
    private String selectBirth;

    public BaseDateDialog(Activity activity) {
        super(activity, R.style.BottomDialog);
        this.activity = activity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_user_date_layout);
        //setViewLocation();
        setCanceledOnTouchOutside(true);// 外部点击取消
        ButterKnife.bind(this);
        setViewLocation();
        //选中监听事件
        datePicker.setOnDateSelectedListener(selectedListener);

        setDateDuring();
    }

    protected boolean isDayPickerVis() {
        return true;
    }

    /**
     * 设置日期的描述
     *
     * @param des 日期的描述
     * @return void
     * @date 2019-9-19 11:24:00
     */
    public void setDateDes(String des) {
        tvDateDes.setText(des);
    }

    /**
     * 设置日期的描述
     *
     * @param des 日期的描述
     * @return void
     * @date 2019-9-19 11:24:00
     */
    public void setDateDes(@StringRes int des) {
        tvDateDes.setText(activity.getResources().getString(des));
    }


    /**
     * 设置日期的时间
     */
    public abstract void setDateDuring();

    /**
     * @param maxMillis 时间最大值
     * @param minMillis 时间最小值
     * @return void
     * @date 2019-9-19 10:11:25
     */
    public void setDateDuring(long maxMillis, long minMillis) {
        datePicker.setMinDate(minMillis);
        datePicker.setMaxDate(maxMillis);
    }

    //日历框选中事件
    private DatePicker.OnDateSelectedListener selectedListener = new DatePicker.OnDateSelectedListener() {
        @Override
        public void onDateSelected(int year, int month, int day) {
            String birth = year + "-" + month + "-" + day + " 00:00:00";
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat Formate = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
            try {
                Date parse = Formate.parse(birth);
                selectBirth = Formate.format(parse);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    };


    /**
     * 设置dialog位于屏幕底部
     *
     * @return void
     * @Date 2018-11-19
     */
    private void setViewLocation() {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;

        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 0;
        lp.y = height;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        onWindowAttributesChanged(lp);
    }

    @OnClick({R.id.tv_cancel, R.id.tv_ok})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                this.cancel();
                break;
            case R.id.tv_ok:
                positiveSelect(selectBirth);
                this.cancel();
        }
    }


    /**
     * @param selectBirth 选中的日期
     * @return void
     * @date 2019-9-18 10:06:34
     */
    public abstract void positiveSelect(String selectBirth);


    /**
     * 获取其中的年月日
     *
     * @param birth 生日 ：格式 yyyy-dd-mm
     * @param index 0：年  1：月 ，2：日
     * @return String
     * @date 2019-9-17 18:46:51
     */
    private String parseBirthday(String birth, int index) {
        String[] split = birth.split("-");
        if (index == 0) {
            return split[0];
        } else if (index == 1) {
            return split[1];
        } else {
            return split[2];
        }


    }

    public static final SimpleDateFormat sdf_yyyyMMDD = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    /**
     * 设置选中日期
     *
     * @param birth1 要选中的日期
     * @return void
     * @date 2019-9-17 18:50:20
     */
    public void setSelectDate(String birth1) throws ParseException {
        Date date = sdf_yyyyMMDD.parse(birth1);
        String birth = parseString(date);

        int year = Integer.parseInt(parseBirthday(birth, 0));
        int month = Integer.parseInt(parseBirthday(birth, 1));
        int day = Integer.parseInt(parseBirthday(birth, 2));
        datePicker.setDate(year, month, day);
        this.selectBirth = birth;
    }

    /**
     * 默认格式
     */
    public static final String PATTERN_YYYYMMdd = "yyyy-MM-dd";

    /**
     * 设置选中日期
     */
    public void setSelectDate(int year, int month, int day) {
        datePicker.setDate(year, month, day);
        this.selectBirth = formatTime(year, month, day, PATTERN_YYYYMMdd);
    }

    /**
     * 出生日期字符串转化成字符串
     *
     * @return Date 要转化的日期
     * @Date 2018-11-20
     */
    public static String parseString(Date strDate) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(strDate);
        return format;
    }

    /**
     * 格式化时间
     *
     * @param year    年份
     * @param month   月份
     * @param day     天数
     * @param pattern 时间格式化
     * @return 字符串
     */
    public static String formatTime(@NonNull int year, @NonNull int month, @NonNull int day, @NonNull String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        return sdf.format(c.getTime());
    }

}
