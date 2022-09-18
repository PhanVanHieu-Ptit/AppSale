package com.example.appbanhang.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.example.appbanhang.adapter.GioHangAdapter;
import com.example.appbanhang.ultil.CheckConnection;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {

    ListView lvGiohang;
    TextView txtThongbao;
    static TextView txtTongtien;
    Button btnThanhtoan,btnTieptucmua;
    Toolbar toolbarGiohang;
    GioHangAdapter gioHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        AnhXa();
        ActionToolbar();
        CheckData();
        EventUltil();
        CactchOnIteamListView();
        EventButton();
    }

    private void EventButton() {
        btnTieptucmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnThanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.mangGiohang.size()>0){
                    Intent intent = new Intent(getApplicationContext(), ThongtinkhachhangActivity.class);
                    startActivity(intent);
                }
                else{
                    CheckConnection.showToastShort(getApplicationContext(),"Gio hang dang trong, ban hay them san pham ");
                }
            }
        });
    }

    private void CactchOnIteamListView() {
        lvGiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this);
                builder.setTitle("Xac nhan xoa san pham");
                builder.setMessage("Ban that su muon xoa san pham nay ?");
                builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MainActivity.mangGiohang.size()<=0){
                            txtThongbao.setVisibility(View.VISIBLE);
                        }
                        else{
                            MainActivity.mangGiohang.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            EventUltil();
                            if (MainActivity.mangGiohang.size()<=0){
                                txtThongbao.setVisibility(View.VISIBLE);
                            }
                            else{
                                txtThongbao.setVisibility(View.INVISIBLE);
                                gioHangAdapter.notifyDataSetChanged();
                                EventUltil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gioHangAdapter.notifyDataSetChanged();
                        EventUltil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EventUltil() {
        long tongtien =0;
        for(int i=0;i<MainActivity.mangGiohang.size();i++){
            tongtien += MainActivity.mangGiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongtien.setText(decimalFormat.format(tongtien)+"Đ");
    }

    private void CheckData() {
        if (MainActivity.mangGiohang.size()<=0){
            gioHangAdapter.notifyDataSetChanged();
            txtThongbao.setVisibility(View.VISIBLE);
            lvGiohang.setVisibility(View.INVISIBLE);
        }
        else{
            gioHangAdapter.notifyDataSetChanged();
            txtThongbao.setVisibility(View.INVISIBLE);
            lvGiohang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarGiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarGiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void AnhXa() {
        lvGiohang = findViewById(R.id.listviewGiohang);
        txtThongbao = findViewById(R.id.textviewThongbao);
        txtTongtien = findViewById(R.id.textviewtongtien);
        btnThanhtoan = findViewById(R.id.buttonthanhtoangiohang);
        btnTieptucmua = findViewById(R.id.buttontieptucmuahang);
        toolbarGiohang = findViewById(R.id.toolbarGiohang);
        gioHangAdapter = new GioHangAdapter(GioHangActivity.this,MainActivity.mangGiohang);
        lvGiohang.setAdapter(gioHangAdapter);
    }
}