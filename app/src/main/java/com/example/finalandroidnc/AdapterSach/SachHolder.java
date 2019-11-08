package com.example.finalandroidnc.AdapterSach;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finalandroidnc.R;

public class SachHolder extends RecyclerView.ViewHolder {




    public TextView tvHoTen;
    public ImageView imgDelAccount;
    public ImageView imgFixAccount;





    public SachHolder(@NonNull final View itemView) {
        super(itemView);




        tvHoTen = itemView.findViewById(R.id.tvHoTen);
        imgDelAccount = itemView.findViewById(R.id.imgDelAccount);
        imgFixAccount = itemView.findViewById(R.id.imgFixAccount);



    }

}
