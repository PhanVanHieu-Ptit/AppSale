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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.R;
import com.example.appbanhang.adapter.DienThoaiAdapter;
import com.example.appbanhang.model.Sanpham;
import com.example.appbanhang.ultil.CheckConnection;
import com.example.appbanhang.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DienThoaiActivity extends AppCompatActivity {

    Toolbar toolbarDienThoai;
    ListView listViewDienThoai;
    DienThoaiAdapter dienThoaiAdapter;
    ArrayList<Sanpham> mangDienThoai;
    int idDienthoai = 0;
    int page=1;
    View footerview;
    boolean isLoading = false;
    myHandler mHandler;
    boolean limitData=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);

        AnhXa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            GetIdLoaiSanPham();
            ActionToolBar();
            GetData(page);
            LoadMoreData();
        }
        else{
            CheckConnection.showToastShort(getApplicationContext(),"Ban hay kiem tra lai ket noi!");
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

    private void LoadMoreData() {
        listViewDienThoai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham",mangDienThoai.get(position));
                startActivity(intent);
            }
        });
        listViewDienThoai.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0 && isLoading==false && limitData==false){
                    isLoading =true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.Duongdandienthoai+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String Tendienthoai ="";
                int Giadienthoai=0;
                String Hinhanhdienthoai="";
                String Motadienthoai="";
                int Idsanphamdienthoai =0;
                if (response!=null &&response.length()!=2){
                    listViewDienThoai.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Tendienthoai = jsonObject.getString("tensp");
                            Giadienthoai = jsonObject.getInt("giasp");
                            Hinhanhdienthoai = jsonObject.getString("hinhanhsp");
                            Motadienthoai = jsonObject.getString("motasp");
                            Idsanphamdienthoai = jsonObject.getInt("idsanpham");
                            mangDienThoai.add(new Sanpham(id,Tendienthoai,Giadienthoai,Hinhanhdienthoai,Motadienthoai,Idsanphamdienthoai));
                            dienThoaiAdapter.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    limitData =true;
                    listViewDienThoai.removeFooterView(footerview);
                    CheckConnection.showToastShort(getApplicationContext(),"Da het du lieu!");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String,String>();
                param.put("idsanpham",String.valueOf(idDienthoai));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarDienThoai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDienThoai.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetIdLoaiSanPham() {
        idDienthoai = getIntent().getIntExtra("idloaisanpham",-1);
        Log.d( "giatriloaisanpham",idDienthoai+"");
    }

    private void AnhXa() {
        toolbarDienThoai = findViewById(R.id.toolbarDienThoai);
        listViewDienThoai = findViewById(R.id.listviewdienthoai);
        mangDienThoai = new ArrayList<>();
        dienThoaiAdapter = new DienThoaiAdapter(getApplicationContext(),mangDienThoai);
        listViewDienThoai.setAdapter(dienThoaiAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar,null);
        mHandler = new myHandler();
    }
    public  class  myHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    listViewDienThoai.addFooterView(footerview);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public  class ThreadData extends Thread{
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
}