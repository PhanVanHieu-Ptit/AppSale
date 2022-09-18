package com.example.appbanhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.R;
import com.example.appbanhang.adapter.LoaispAdapter;
import com.example.appbanhang.adapter.SanphamAdapter;
import com.example.appbanhang.model.Giohang;
import com.example.appbanhang.model.Loaisp;
import com.example.appbanhang.model.Sanpham;
import com.example.appbanhang.ultil.CheckConnection;
import com.example.appbanhang.ultil.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;
    ArrayList<Loaisp> mangLoaisp;
    LoaispAdapter loaispAdapter;
    int id =0;
    String tenloaisp ="";
    String hinhanhloaisanpham="";
    ArrayList<Sanpham> mangSanpham ;
    SanphamAdapter sanphamAdapter;
    public  static ArrayList<Giohang> mangGiohang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewFlipper();
            GetDuLieuLoaisp();
            GetDuLieuSanPhamMoiNhat();
            CatchOnIteamListView();
        }
        else {
            CheckConnection.showToastShort(getApplicationContext(), "Ban hay kiem tra lai ket noi !");
            finish();
        }
        ActionBar();
        ActionViewFlipper();
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

    private void CatchOnIteamListView() {
        listViewManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            CheckConnection.showToastShort(getApplicationContext(), "Ban hay kiem tra lai ket noi!");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,DienThoaiActivity.class);
                            intent.putExtra("idloaisanpham",mangLoaisp.get(position).getId());
                            startActivity(intent);
                        }
                        else {
                            CheckConnection.showToastShort(getApplicationContext(), "Ban hay kiem tra lai ket noi!");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,LaptopActivity.class);
                            intent.putExtra("idloaisanpham",mangLoaisp.get(position).getId());
                            startActivity(intent);
                        }
                        else {
                            CheckConnection.showToastShort(getApplicationContext(), "Ban hay kiem tra lai ket noi!");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,LienHeActivity.class);
                            startActivity(intent);
                        }
                        else {
                            CheckConnection.showToastShort(getApplicationContext(), "Ban hay kiem tra lai ket noi!");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,ThongTinActivity.class);
                            startActivity(intent);
                        }
                        else {
                            CheckConnection.showToastShort(getApplicationContext(), "Ban hay kiem tra lai ket noi!");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void GetDuLieuSanPhamMoiNhat(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.Duongdansanphammoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response!= null){
                    int ID = 0;
                    String Tensanpham = "";
                    Integer Gia = 0;
                    String Hinhanhsanpham ="";
                    String Motasanpham ="";
                    int IdSanPham =0;
                    for (int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            Tensanpham = jsonObject.getString("tensp");
                            Gia = jsonObject.getInt("giasp");
                            Hinhanhsanpham = jsonObject.getString("hinhsanpham");
                            Motasanpham = jsonObject.getString("motasp");
                            IdSanPham = jsonObject.getInt("idsanpham");
                            mangSanpham.add(new Sanpham(ID,Tensanpham,Gia, Hinhanhsanpham, Motasanpham, IdSanPham));
                            sanphamAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDuLieuLoaisp() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.Duongdanloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response !=null){
                    for (int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenloaisp = jsonObject.getString("tenloaisp");
                            hinhanhloaisanpham = jsonObject.getString("hinhanhloaisp");
                            mangLoaisp.add(new Loaisp(id,tenloaisp,hinhanhloaisanpham));
                            loaispAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    mangLoaisp.add(3,new Loaisp(0,"Lien He","https://nhakhoatamthe.com/images/phone.png"));
                    mangLoaisp.add(4,new Loaisp(0,"Thong tin","https://agitech.com.vn/images/NguyenDuy/1889339220_thong_itn.png"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToastShort(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://photo-cms-kienthuc.zadn.vn/zoom/800/Uploaded/nguyenvan/2016_12_06/phim/phim-0_JEMD.png");
        mangquangcao.add("https://cellphones.com.vn/sforum/wp-content/uploads/2019/12/Redmi-K30-render-1.jpg");
        mangquangcao.add("https://photo-cms-sggp.zadn.vn/w580/Uploaded/2021/yfsgf/2020_09_24/hinh11_mzad.jpg");
        mangquangcao.add("https://st.quantrimang.com/photos/image/2018/07/14/quang-cao-smartphone-650.jpg");
        mangquangcao.add("https://www.viettablet.com/images/news/44/xiaomi-mi-mix-4-lo-anh-quang-cao.jpg");
        for (int i=0; i<mangquangcao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void AnhXa() {
        toolbar                  = findViewById(R.id.toolbarmanHinhChinh);
        viewFlipper              = findViewById(R.id.viewlipper);
        recyclerViewManHinhChinh = findViewById(R.id.recyclerview);
        navigationView           = findViewById(R.id.navigationview);
        listViewManHinhChinh     = findViewById(R.id.listviewmanHinhChinh);
        drawerLayout             = findViewById(R.id.drawerlayout);
        mangLoaisp               = new ArrayList<>();
        mangLoaisp.add(0,new Loaisp(0,"Trang Chinh", "https://vietadsgroup.vn/Uploads/files/trangchu-la-gi.png"));
        loaispAdapter            = new LoaispAdapter(mangLoaisp,getApplicationContext());
        listViewManHinhChinh.setAdapter(loaispAdapter);
        mangSanpham              = new ArrayList<>();
        sanphamAdapter           = new SanphamAdapter(getApplicationContext(), mangSanpham);
        recyclerViewManHinhChinh.setHasFixedSize(true);
        recyclerViewManHinhChinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewManHinhChinh.setAdapter(sanphamAdapter);
        if (mangGiohang!=null){

        }
        else{
            mangGiohang = new ArrayList<>();
        }
    }
}