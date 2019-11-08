package com.example.finalandroidnc.AdapterSach;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalandroidnc.DataMaps;
import com.example.finalandroidnc.Main2Activity;
import com.example.finalandroidnc.R;
import com.example.finalandroidnc.Sqlite;

import java.util.List;

public class SachAdapter extends RecyclerView.Adapter<SachHolder> {
    EditText edtName;
    EditText edtV;
    EditText edtV1;

    private Sqlite bookDatabaseHelper;
    private List<DataMaps> accountList;
    private Context context;

    public SachAdapter(List<DataMaps> accountList, Context context, Sqlite bookDatabaseHelper) {
        this.bookDatabaseHelper = bookDatabaseHelper;
        this.accountList = accountList;
        this.context = context;
    }

    @NonNull
    @Override
    public SachHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sach, parent, false);

        return new SachHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SachHolder holder, final int position) {
        final DataMaps account = accountList.get(position);

        holder.tvHoTen.setText(account.name);

        holder.imgDelAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa");
                builder.setMessage("Bạn có muốn xóa không?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteNote(position);
                        Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

//        holder.imgFixAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(context, FixSach.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("theLoai",account.theLoai);
//                bundle.putString("maSach",account.maSach);
//
//                bundle.putString("tenSach",account.tenSach);
//                bundle.putString("tacGia",account.tacGia);
//                bundle.putString("nhaXuatBan",account.nhaXuatBan);
//                bundle.putInt("giaBia",account.giaBia);
//
//                intent.putExtras(bundle);
//                context.startActivity(intent);
//
//
//            }
//        });
        holder.imgFixAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Sửa vị trí");


                View dialog = LayoutInflater.from(context).inflate(R.layout.dialog_edituser, null);
                builder.setView(dialog);

                edtName = dialog.findViewById(R.id.edtName);
                edtV = dialog.findViewById(R.id.edtV);
                edtV1 = dialog.findViewById(R.id.edtV1);

                DataMaps dataMaps = accountList.get(position);

                edtName.setText(dataMaps.getName());
                edtV.setText(dataMaps.getA());
                edtV1.setText(dataMaps.getB());

                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edituser();
                        Sqlite nhasachDatabase1 = new Sqlite(context);
                        accountList = nhasachDatabase1.accountList();
                        SachAdapter sachAdapter = new SachAdapter(accountList, context, nhasachDatabase1);
                        sachAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        builder.setCancelable(true);
                    }
                });

                builder.create().show();


            }
        });


    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    private void deleteNote(int position) {

        bookDatabaseHelper.deletecAcount(accountList.get(position));
        accountList.remove(position);
        Main2Activity taiKhoanFragment = new Main2Activity();
        taiKhoanFragment.notifyAdapter();

    }
    private void edituser(){
        DataMaps user = new DataMaps();

        user.setName(edtName.getText().toString());
        user.setName(edtV.getText().toString());
        user.setName(edtV1.getText().toString());


        Sqlite database = new Sqlite(context);
        database.update(user);
    }

}
