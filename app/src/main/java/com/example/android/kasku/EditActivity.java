package com.example.android.kasku;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.example.android.kasku.helper.SqliteHelper;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;


public class EditActivity extends AppCompatActivity {

    MainActivity m = new MainActivity();

    DatePickerDialog datePickerDialog;
    String tanggal, status;

    RadioGroup radio_status;
    RadioButton radio_masuk, radio_keluar;
    EditText edit_jumlah, edit_keterangan,edit_tanggal;
    RippleView rip_simpan;
    SqliteHelper sqliteHelper;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        tanggal = ""; //untuk menyimpan tanggal ke sqlite, formatnya " yyyy-mm-dd"
        status = "";

        radio_status = findViewById(R.id.radio_status);
        radio_masuk = findViewById(R.id.radio_masuk);
        radio_keluar = findViewById(R.id.radio_keluar);
        edit_jumlah = findViewById(R.id.edit_jumlah);
        edit_tanggal = findViewById(R.id.edit_tanggal);
        edit_keterangan = findViewById(R.id.edit_keterangan);
        rip_simpan = findViewById(R.id.rip_simpan);

        sqliteHelper = new SqliteHelper(this);
        SQLiteDatabase database = sqliteHelper.getReadableDatabase();
        cursor = database.rawQuery("SELECT *, strftime('%d/%m/%Y', tanggal) AS tgl FROM transaksi WHERE transaksi_id='" + m.transaksi_id + "'", null);
        cursor.moveToFirst();

        status = cursor.getString(1);
        switch (status){
            case "MASUK":radio_masuk.setChecked(true);
                break;
            case "KELUAR":radio_keluar.setChecked(true);
                break;
        }

        radio_status.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radio_masuk:
                        status = "MASUK";
                        break;
                    case R.id.radio_keluar:
                        status = "KELUAR";
                        break;
                }
                Log.d("Log status", status);
            }
        });

        edit_jumlah.setText(cursor.getString(2));
        edit_keterangan.setText(cursor.getString(3));
        tanggal = cursor.getString(4);
        edit_tanggal.setText(cursor.getString(5));

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        edit_tanggal = findViewById(R.id.edit_tanggal);
        edit_tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(EditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        NumberFormat numberFormat = new DecimalFormat("00");
                        tanggal = numberFormat.format(year) + "-" + numberFormat.format((month + 1)) + "-" + numberFormat.format(dayOfMonth);
                        edit_tanggal.setText(numberFormat.format(dayOfMonth) + "/" + numberFormat.format((month + 1)) + "/" + numberFormat.format(year));
                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });

        rip_simpan.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

            @Override
            public void onComplete(RippleView rippleView) {
                if (status.equals("") || edit_jumlah.getText().toString().equals("") || edit_keterangan.getText().toString().equals("")) {
                    Toast.makeText(EditActivity.this,
                            "Silakan isi data terlebih dahulu", //Context, Content
                            Toast.LENGTH_LONG).show();

                } else {
                    SQLiteDatabase database = sqliteHelper.getWritableDatabase();
                    database.execSQL(
                            "UPDATE  transaksi SET status='"+ status + "', jumlah='" + edit_jumlah.getText().toString() +
                                    "', keterangan='" + edit_keterangan.getText().toString() + "', tanggal='" + tanggal +
                                    "' WHERE transaksi_id='" + m.transaksi_id + "'"
                    );
                    Toast.makeText(EditActivity.this,
                            "Perubahan data transaksi berhasil tersimpan",  Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });

        //set title
        getSupportActionBar().setTitle("Edit Data");

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
