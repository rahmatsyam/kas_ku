package com.example.android.kasku;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.example.android.kasku.helper.SqliteHelper;

public class AddActivity extends AppCompatActivity {

    RadioGroup radio_status;
    EditText edit_jumlah, edit_keterangan;
    Button btn_simpan;
    RippleView rip_simpan;
    String status;
    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        status = "";
        sqliteHelper = new SqliteHelper(this);

        radio_status = findViewById(R.id.radio_status);
        edit_jumlah = findViewById(R.id.edit_jumlah);
        edit_keterangan = findViewById(R.id.edit_keterangan);
        btn_simpan = findViewById(R.id.btn_simpan);
        rip_simpan = findViewById(R.id.rip_simpan);

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

        rip_simpan.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

            @Override
            public void onComplete(RippleView rippleView) {
                if (status.equals("") || edit_jumlah.getText().toString().equals("") || edit_keterangan.getText().toString().equals("")) {
                    Toast.makeText(AddActivity.this,
                            "Silakan isi data terlebih dahulu", //Context, Content
                            Toast.LENGTH_LONG).show();

                } else {
                    SQLiteDatabase database = sqliteHelper.getWritableDatabase();
                    database.execSQL( "INSERT INTO transaksi(status, jumlah, keterangan) VALUES ('" +
                     status + "','" + edit_jumlah.getText().toString() + "','" + edit_keterangan.getText().toString() + "')"
                    );
                    Toast.makeText(AddActivity.this,
                            "Data transaksi berhasil tersimpan",  Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //String jumlah     = edit_jumlah.getText().toString();
                //String keterangan = edit_keterangan.getText().toString();
                //Contoh toast
//                Toast.makeText(AddActivity.this,
//                        "Jumlah :" + edit_jumlah.getText().toString() + " Keterangan : " + edit_keterangan.getText().toString(), //Context, Content
//                        Toast.LENGTH_LONG).show();
                //contoh log
//                Log.d("Log pesan","Simpan dulu");  //Tag, Content
            }
        });

        //set title
        getSupportActionBar().setTitle("Tambah Baru");

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
