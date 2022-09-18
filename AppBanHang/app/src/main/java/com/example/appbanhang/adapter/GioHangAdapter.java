package com.example.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.example.appbanhang.activity.GioHangActivity;
import com.example.appbanhang.activity.MainActivity;
import com.example.appbanhang.model.Giohang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<Giohang> arrayGiohang;

    public GioHangAdapter(Context context, ArrayList<Giohang> arrayGiohang) {
        this.context = context;
        this.arrayGiohang = arrayGiohang;
    }

    @Override
    public int getCount() {
        return arrayGiohang.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayGiohang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txttengiohang,txtgiagiohang;
        public ImageView imggiohang;
        public Button btnminus, btnplus, btnvalues;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        if (convertView==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_gio_hang,null);
            viewHolder.txttengiohang = convertView.findViewById(R.id.textviewTengiohang);
            viewHolder.txtgiagiohang = convertView.findViewById(R.id.textviewgiagiohang);
            viewHolder.imggiohang = convertView.findViewById(R.id.imageviewgiohang);
            viewHolder.btnminus = convertView.findViewById(R.id.buttonminus);
            viewHolder.btnplus = convertView.findViewById(R.id.buttonplus);
            viewHolder.btnvalues = convertView.findViewById(R.id.buttonvalues);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Giohang giohang = (Giohang) getItem(position);
        viewHolder.txttengiohang.setText(giohang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiagiohang.setText(decimalFormat.format(giohang.getGiasp())+"Đ");
        Picasso.with(context).load(giohang.getHinhsp())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error_image)
                .into(viewHolder.imggiohang);
        viewHolder.btnvalues.setText(giohang.getSoluonsp()+"");
        int sl = Integer.parseInt(viewHolder.btnvalues.getText().toString());
        if (sl>=10){
            viewHolder.btnplus.setVisibility(View.INVISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }
        else if (sl<=1){
            viewHolder.btnminus.setVisibility(View.INVISIBLE);
        }
        else{
            viewHolder.btnminus.setVisibility(View.VISIBLE);
            viewHolder.btnplus.setVisibility(View.VISIBLE);
        }
        viewHolder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(viewHolder.btnvalues.getText().toString())+1;
                int slHientai = (int) MainActivity.mangGiohang.get(position).getSoluonsp();
                long giahientai = MainActivity.mangGiohang.get(position).getGiasp();
                MainActivity.mangGiohang.get(position).setSoluonsp(slmoinhat);
                long giamoinhat = (giahientai*slmoinhat)/slHientai;
                MainActivity.mangGiohang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat1 = new DecimalFormat("###,###,###");
                viewHolder.txtgiagiohang.setText(decimalFormat1.format(giamoinhat)+"Đ");
                GioHangActivity.EventUltil();
                if (slmoinhat>9){
                    viewHolder.btnplus.setVisibility(View.INVISIBLE);
                    viewHolder.btnminus.setVisibility(View.VISIBLE);
                    viewHolder.btnvalues.setText(String.valueOf(slmoinhat));
                }
                else{
                    viewHolder.btnminus.setVisibility(View.VISIBLE);
                    viewHolder.btnplus.setVisibility(View.VISIBLE);
                    viewHolder.btnvalues.setText(String.valueOf(slmoinhat));
                }
            }
        });
        viewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(viewHolder.btnvalues.getText().toString())-1;
                int slHientai = (int) MainActivity.mangGiohang.get(position).getSoluonsp();
                long giahientai = MainActivity.mangGiohang.get(position).getGiasp();
                MainActivity.mangGiohang.get(position).setSoluonsp(slmoinhat);
                long giamoinhat = (giahientai*slmoinhat)/slHientai;
                MainActivity.mangGiohang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat1 = new DecimalFormat("###,###,###");
                viewHolder.txtgiagiohang.setText(decimalFormat1.format(giamoinhat)+"Đ");
                GioHangActivity.EventUltil();
                if (slmoinhat<2){
                    viewHolder.btnplus.setVisibility(View.VISIBLE);
                    viewHolder.btnminus.setVisibility(View.INVISIBLE);
                    viewHolder.btnvalues.setText(String.valueOf(slmoinhat));
                }
                else{
                    viewHolder.btnminus.setVisibility(View.VISIBLE);
                    viewHolder.btnplus.setVisibility(View.VISIBLE);
                    viewHolder.btnvalues.setText(String.valueOf(slmoinhat));
                }
            }
        });
        return convertView;
    }
}
