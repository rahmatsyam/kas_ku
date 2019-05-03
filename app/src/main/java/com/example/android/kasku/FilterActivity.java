package com.example.android.kasku;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.andexert.library.RippleView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

public class FilterActivity extends AppCompatActivity {

    EditText edit_dari, edit_sampai;
    RippleView rip_simpan;
    DatePickerDialog datePickerDialog;
    MainActivity d = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        edit_dari = findViewById(R.id.edit_dari);
        edit_sampai = findViewById(R.id.edit_sampai);

        rip_simpan = findViewById(R.id.rip_simpan);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        edit_dari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(FilterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        NumberFormat numberFormat = new DecimalFormat("00");
                        d.tgl_dari = numberFormat.format(year) + "-" + numberFormat.format((month + 1)) + "-" + numberFormat.format(dayOfMonth);
                        edit_dari.setText(numberFormat.format(dayOfMonth) + "/" + numberFormat.format((month + 1)) + "/" + numberFormat.format(year));
                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });


        edit_sampai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(FilterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        NumberFormat numberFormat = new DecimalFormat("00");
                        d.tgl_sampai = numberFormat.format(year) + "-" + numberFormat.format((month + 1)) + "-" + numberFormat.format(dayOfMonth);
                        edit_sampai.setText(numberFormat.format(dayOfMonth) + "/" + numberFormat.format((month + 1)) + "/" + numberFormat.format(year));
                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });

        rip_simpan.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                if(d.tgl_dari.equals("") || d.tgl_sampai.equals("")){
                    Toast.makeText(FilterActivity.this,"Isi data terlebih dahulu",Toast.LENGTH_LONG).show();
                } else {
                    d.filter = true;
                    d.text_filter.setText(edit_dari.getText().toString()+ "-" + edit_sampai.getText().toString());
                    finish();
                }
            }
        });


        getSupportActionBar().setTitle("Filter");
        //Back icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //click back icon
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
