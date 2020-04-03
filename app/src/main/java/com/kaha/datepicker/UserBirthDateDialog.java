package com.kaha.datepicker;

import android.app.Activity;

import java.util.Calendar;

/**
 * 用户生日的dialog
 *
 * @author Darcy
 * @Date 2019/9/19
 * @package com.app.baby.widget.datedialog
 * @Desciption
 */
public abstract class UserBirthDateDialog extends BaseDateDialog {


    public UserBirthDateDialog(Activity activity) {
        super(activity);
    }

    /**
     * 重写父类的方法，设置日期的最大最小值
     *
     * @param ，
     * @return void
     * @date 2019-9-19 10:16:44
     */
    public void setDateDuring() {
        //用户生日模式
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -100);
        long minMillis = calendar.getTimeInMillis();
        datePicker.setMaxDate(System.currentTimeMillis());
        datePicker.setMinDate(minMillis);
        //setDateDes(R.string.temp_date);
    }

}
