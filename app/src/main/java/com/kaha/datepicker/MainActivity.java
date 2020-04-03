package com.kaha.datepicker;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_show_date)
    TextView tvShowDate;

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_select_date})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_select_date:

                UserBirthDateDialog dateDialog = new UserBirthDateDialog(activity) {
                    @Override
                    public void positiveSelect(String selectBirth) {
                        tvShowDate.setText(selectBirth);
                    }
                };
                dateDialog.show();
                String s = tvShowDate.getText().toString();
                if (!TextUtils.isEmpty(s)) {
                    try {
                        dateDialog.setSelectDate(s);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
