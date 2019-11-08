package com.example.finalandroidnc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView tvCount;
    private Button btnView;
    private Button btnAd;

    private EditText edtName;
    private EditText edtV;
    private EditText edtV1;
    private Button btnHuy;
    private Button btnAdd;

    private DataMaps dataMaps;
    private Sqlite sqlite;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCount = findViewById(R.id.tvCount);
        btnView = findViewById(R.id.btnView);
        btnAd = findViewById(R.id.btnAd);
        Sqlite sqlite = new Sqlite(this);
        sqlite.createDataBase();
        sqlite = new Sqlite(this);

        btnAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialogAdd();

            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });


    }

    private void ShowDialogAdd() {

        final Dialog add_loaichi_layout = new Dialog(MainActivity.this);
        add_loaichi_layout.setTitle("Thêm vị trí ");


        add_loaichi_layout.setContentView(R.layout.new_item_loaichi);
        add_loaichi_layout.getWindow().setBackgroundDrawableResource(R.color.colorWhite);




        edtName = add_loaichi_layout.findViewById(R.id.edtName);
        edtV = add_loaichi_layout.findViewById(R.id.edtV);
        edtV1 = add_loaichi_layout.findViewById(R.id.edtV1);
        btnHuy = add_loaichi_layout.findViewById(R.id.btnHuy);
        btnAdd = add_loaichi_layout.findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNew();
                add_loaichi_layout.cancel();

                Toast.makeText(MainActivity.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_loaichi_layout.cancel();
            }
        });
        add_loaichi_layout.show();
    }

    private void AddNew() {

        dataMaps = new DataMaps();

        dataMaps.setName(edtName.getText().toString());
        dataMaps.setA(edtV.getText().toString());
        dataMaps.setB(edtV1.getText().toString());

        Sqlite sqlite = new Sqlite(this);
        sqlite.insertAccount(dataMaps);
    }


}
