package com.example.finalandroidnc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.finalandroidnc.AdapterSach.SachAdapter;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private RecyclerView rccView;

    private RecyclerView.LayoutManager layoutManager;
    private Sqlite bookDatabaseHelper;
    List<DataMaps> accountList;
    public  static SachAdapter sachAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        rccView = findViewById(R.id.rccView);
        accountList = new ArrayList<>();

        rccView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rccView.setLayoutManager(layoutManager);
        bookDatabaseHelper = new Sqlite(this);
        bookDatabaseHelper.createDataBase();
        LoadDataLoaiThu();
    }

    private void LoadDataLoaiThu() {
        accountList = bookDatabaseHelper.accountList();
        sachAdapter = new SachAdapter(accountList, this,bookDatabaseHelper);
        rccView.setAdapter(sachAdapter);
        sachAdapter.notifyDataSetChanged();

    }
    public static  void notifyAdapter(){
        sachAdapter.notifyDataSetChanged();
    }
}
