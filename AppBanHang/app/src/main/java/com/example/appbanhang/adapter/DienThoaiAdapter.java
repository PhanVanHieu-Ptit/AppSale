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

public class DienThoaiAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arrayDienThoai;

    public DienThoaiAdapter(Context context, ArrayList<Sanpham> arrayDienThoai) {
        this.context = context;
        this.arrayDienThoai = arrayDienThoai;
    }

    @Override
    public int getCount() {
        return arrayDienThoai.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayDienThoai.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView txtTenDienthoai,txtGiaDienthoai,txtMotaDienThoai;
        public ImageView imgDienthoai;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_dien_thoai,null);
            viewHolder.txtTenDienthoai = convertView.findViewById(R.id.textviewdienthoai);
            viewHolder.txtGiaDienthoai = convertView.findViewById(R.id.textviewgiadienthoai);
            viewHolder.txtMotaDienThoai = convertView.findViewById(R.id.textviewmotadienthoai);
            viewHolder.imgDienthoai = convertView.findViewById(R.id.imageviewdienthoai);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(position);
        viewHolder.txtTenDienthoai.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaDienthoai.setText("Giá: "+decimalFormat.format(sanpham.getGiasanpham())+"Đ");
        viewHolder.txtMotaDienThoai.setMaxLines(2);
        viewHolder.txtMotaDienThoai.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMotaDienThoai.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhsanpham())
        .placeholder(R.drawable.no_image)
        .error(R.drawable.error_image)
        .into(viewHolder.imgDienthoai);
        return convertView;
    }
}
