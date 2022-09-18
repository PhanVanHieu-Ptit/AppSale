package com.example.appbanhang.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.R;
import com.example.appbanhang.ultil.CheckConnection;
import com.example.appbanhang.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongtinkhachhangActivity extends AppCompatActivity {

    EditText edtTenkhachhang, edtEmail, edtsdt;
    Button btnxacnhan, btntrove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinkhachhang);

        AnhXa();
        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            EventButton();
        }
        else{
            CheckConnection.showToastShort(getApplicationContext(),"Ban hay kiem tra lai ket noi Internet!");
        }
    }

    private void EventButton() {
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = edtTenkhachhang.getText().toString().trim();
                String sdt = edtsdt.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                if (ten.length()>0 && sdt.length()>0 && email.length()>0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.Duongdandonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            Log.d("madonhang",madonhang);
                            if (Integer.parseInt(madonhang)>0){
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST, Server.Duongdanchitietdonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("1")){
                                            MainActivity.mangGiohang.clear();
                                            CheckConnection.showToastShort(getApplicationContext(),"Ban da them du lieu gio hanh thanh cong!");
                                            Intent intent =new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            CheckConnection.showToastShort(getApplicationContext(), "Moi ban tiep tuc mua hang!");
                                        }
                                        else{
                                            CheckConnection.showToastShort(getApplicationContext(), "Du lieu gio hang cua ban bi loi!");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Nullable
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i=0;i<MainActivity.mangGiohang.size();i++){
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang", madonhang);
                                                jsonObject.put("masanpham", MainActivity.mangGiohang.get(i).getIdsp());
                                                jsonObject.put("tensanpham", MainActivity.mangGiohang.get(i).getTensp());
                                                jsonObject.put("giasanpham", MainActivity.mangGiohang.get(i).getGiasp());
                                                jsonObject.put("soluongsanpham", MainActivity.mangGiohang.get(i).getSoluonsp());
                                            }
                                            catch (JSONException e){
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String, String> hashMap = new HashMap<String, String>();
                                        hashMap.put("json", jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String,String>();
                            hashMap.put("tenkhachhang",ten);
                            hashMap.put("sodienthoai",sdt);
                            hashMap.put("email",email);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
                else {
                    CheckConnection.showToastShort(getApplicationContext(),"Hay kiem tra lai du lieu!");
                }
            }
        });
    }

    private void AnhXa() {
        edtTenkhachhang  = findViewById(R.id.edittextTenkhachhang);
        edtEmail = findViewById(R.id.edittextEmail);
        edtsdt = findViewById(R.id.edittextSodienthoai);
        btnxacnhan = findViewById(R.id.buttonXacnhan);
        btntrove = findViewById(R.id.buttonTrove);
    }
}