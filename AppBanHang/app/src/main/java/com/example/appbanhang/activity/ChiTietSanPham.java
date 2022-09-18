package com.example.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.example.appbanhang.model.Giohang;
import com.example.appbanhang.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSanPham extends AppCompatActivity {

    Toolbar toolbarChitiet;
    ImageView imgChitiet;
    TextView txtTen,txtGia, txtMota;
    Spinner spinner;
    Button btndatmua;
    int id =0;
    String Tenchitiet ="";
    int Giachitiet =0;
    String Hinhanhchitiet ="";
    String Motachitiet="";
    int Idsanpham = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        AnhXa();
        ActionToolbar();
        GetInformation();
        CatchEvenSpinner();
        EventButton();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void EventButton() {
        btndatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.mangGiohang.size()>0){
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists =false;
                    for(int i=0;i<MainActivity.mangGiohang.size();i++){
                        if (MainActivity.mangGiohang.get(i).getIdsp()==id){
                            MainActivity.mangGiohang.get(i).setSoluonsp(MainActivity.mangGiohang.get(i).getSoluonsp()+sl);
                            if (MainActivity.mangGiohang.get(i).getSoluonsp()>10){
                                MainActivity.mangGiohang.get(i).setSoluonsp(10);
                            }
                            MainActivity.mangGiohang.get(i).setGiasp(Giachitiet*MainActivity.mangGiohang.get(i).getSoluonsp());
                            exists=true;
                        }
                    }
                    if (exists == false){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString() );
                        long Giamoi = soluong * Giachitiet;
                        MainActivity.mangGiohang.add(new Giohang(id,Tenchitiet,Giamoi,Hinhanhchitiet,soluong));
                    }
                }
                else{
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString() );
                    long Giamoi = soluong * Giachitiet;
                    MainActivity.mangGiohang.add(new Giohang(id,Tenchitiet,Giamoi,Hinhanhchitiet,soluong));
                }
                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEvenSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInformation() {

        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanpham.getId();
        Tenchitiet = sanpham.getTensanpham();
        Giachitiet = sanpham.getGiasanpham();
        Hinhanhchitiet = sanpham.getHinhsanpham();
        Motachitiet = sanpham.getMotasanpham();
        Idsanpham = sanpham.getIdSanpham();
        txtTen.setText(Tenchitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGia.setText("Giá: "+decimalFormat.format(Giachitiet)+"Đ");
        txtMota.setText(Motachitiet);
        Picasso.with(getApplicationContext()).load(Hinhanhchitiet)
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error_image)
                .into(imgChitiet);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarChitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void AnhXa() {
        toolbarChitiet = findViewById(R.id.toolbarchitietsanpham);
        imgChitiet = findViewById(R.id.imageviewchitietsanpham);
        txtTen = findViewById(R.id.textviewtenchitietsanpham);
        txtGia = findViewById(R.id.textviewgiachitietsanpham);
        txtMota = findViewById(R.id.textviewmotachitietsanpham);
        spinner = findViewById(R.id.spinner);
        btndatmua = findViewById(R.id.buttondatmua);
    }
}