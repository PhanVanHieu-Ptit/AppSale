package com.example.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.R;
import com.example.appbanhang.adapter.LaptopAdapter;
import com.example.appbanhang.model.Sanpham;
import com.example.appbanhang.ultil.CheckConnection;
import com.example.appbanhang.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaptopActivity extends AppCompatActivity {
    Toolbar toolbarLaptop;
    ListView listViewLaptop;
    LaptopAdapter laptopAdapter;
    ArrayList<Sanpham> mangLaptop;
    int idLaptop=1;
    int page = 1;
    View footerview;
    boolean isLoading = false;
    boolean limitdata = false;
    myHandler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        AnhXa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            GetIdLoaiSanPham();
            ActionToolbar();
            GetData(page);
            LoadMoreData();
        }
        else{
            CheckConnection.showToastShort(getApplicationContext(),"Ban hay kiem tra lai ket noi Internet");
            finish();
        }

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

    private void GetData(int page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.Duongdandienthoai+String.valueOf(page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id =0;
                String TenLaptop="";
                int GiaLaptop=0;
                String MotaLaptop="";
                String HinhLaptop="";
                int IdSanphamLaptop=0;
                if (response!=null && response.length()!=2){
                    listViewLaptop.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            TenLaptop = jsonObject.getString("tensp");
                            GiaLaptop = jsonObject.getInt("giasp");
                            HinhLaptop = jsonObject.getString("hinhanhsp");
                            MotaLaptop = jsonObject.getString("motasp");
                            IdSanphamLaptop = jsonObject.getInt("idsanpham");
                            mangLaptop.add(new Sanpham(id,TenLaptop,GiaLaptop,HinhLaptop,MotaLaptop,IdSanphamLaptop));
                            laptopAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        CheckConnection.showToastShort(getApplicationContext(),e.toString());
                    }

                }
                else{
                    limitdata = true;
                    listViewLaptop.removeFooterView(footerview);
                    CheckConnection.showToastShort(getApplicationContext(),"Da het du lieu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToastShort(getApplicationContext(),error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String,String>();
                param.put("idsanpham",String.valueOf(idLaptop));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void ActionToolbar() {
        setSupportActionBar(toolbarLaptop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLaptop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetIdLoaiSanPham() {
        idLaptop = getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("giatriloaisanpham",idLaptop+"");
    }

    public class myHandler extends Handler{
    @Override
    public void handleMessage(@NonNull Message msg) {
        switch (msg.what){
            case 0:
                listViewLaptop.addFooterView(footerview);
                break;
            case 1:
                GetData(++page);
                isLoading = false;
                break;
             }
        super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
    private void LoadMoreData(){
        listViewLaptop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham",mangLaptop.get(position));
                startActivity(intent);
            }
        });
        listViewLaptop.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount!=0 && isLoading==false && limitdata==false){
                    isLoading = true;
                    LaptopActivity.ThreadData threadData = new LaptopActivity.ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void AnhXa() {
        toolbarLaptop = findViewById(R.id.toolbarLapTop);
        listViewLaptop = findViewById(R.id.listviewlaptop);
        mangLaptop = new ArrayList<>();
        laptopAdapter = new LaptopAdapter(getApplicationContext(), mangLaptop);
        listViewLaptop.setAdapter(laptopAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar,null);
        mHandler = new myHandler();
    }
}