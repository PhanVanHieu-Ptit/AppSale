package com.example.appbanhang.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.example.appbanhang.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LaptopAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arrayLaptop;

    public LaptopAdapter(Context context, ArrayList<Sanpham> arrayLaptop) {
        this.context = context;
        this.arrayLaptop = arrayLaptop;
    }

    @Override
    public int getCount() {
        return arrayLaptop.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayLaptop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView txttenLaptop,txtgiaLaptop,txtmottaLaptop;
        public ImageView imgLaptop;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_lap_top,null);
            viewHolder.txttenLaptop = convertView.findViewById(R.id.textviewtenlaptop);
            viewHolder.txtgiaLaptop = convertView.findViewById(R.id.textviewgialaptop);
            viewHolder.txtmottaLaptop = convertView.findViewById(R.id.textviewmotalaptop);
            viewHolder.imgLaptop = convertView.findViewById(R.id.imageviewlaptop);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(position);
        viewHolder.txttenLaptop.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiaLaptop.setText("Giá: "+decimalFormat.format(sanpham.getGiasanpham())+"Đ");
        viewHolder.txtmottaLaptop.setMaxLines(2);
        viewHolder.txtmottaLaptop.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmottaLaptop.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhsanpham())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error_image)
                .into(viewHolder.imgLaptop);
        return convertView;
    }
}
