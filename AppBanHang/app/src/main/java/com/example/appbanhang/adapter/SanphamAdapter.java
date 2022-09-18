package com.example.appbanhang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.R;
import com.example.appbanhang.activity.ChiTietSanPham;
import com.example.appbanhang.model.Sanpham;
import com.example.appbanhang.ultil.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.IteamHolder> {

    Context context;
    ArrayList<Sanpham> arraysanpham;

    public SanphamAdapter(Context context, ArrayList<Sanpham> arraysanpham) {
        this.context = context;
        this.arraysanpham = arraysanpham;
    }

    @NonNull
    @Override
    public IteamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoinhat,null);

        IteamHolder iteamHolder = new IteamHolder(view);

        return iteamHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IteamHolder holder, int position) {
        Sanpham sanpham = arraysanpham.get(position);
        holder.txtTenSanPham.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiasanpham.setText("Giá: "+ decimalFormat.format(sanpham.getGiasanpham())+ "Đ");
        Picasso.with(context).load(sanpham.getHinhsanpham())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error_image)
                .into(holder.imgHinhsanpham);

    }

    @Override
    public int getItemCount() {
        return arraysanpham.size();
    }

    public class IteamHolder extends RecyclerView.ViewHolder{
        public ImageView imgHinhsanpham;
        public TextView txtTenSanPham, txtGiasanpham;

        public IteamHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhsanpham = itemView.findViewById(R.id.imageviewsanpham);
            txtGiasanpham = itemView.findViewById(R.id.textviewgiasanpham);
            txtTenSanPham = itemView.findViewById(R.id.textviewtensanpham);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietSanPham.class);
                    intent.putExtra("thongtinsanpham",arraysanpham.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.showToastShort(context,arraysanpham.get(getPosition()).getTensanpham());
                    context.startActivity(intent);
                }
            });
        }
    }
}
