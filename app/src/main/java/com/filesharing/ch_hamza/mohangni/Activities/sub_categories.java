package com.filesharing.ch_hamza.mohangni.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.filesharing.ch_hamza.mohangni.Adapters.sub_cat_recylcerview;
import com.filesharing.ch_hamza.mohangni.Config;
import com.filesharing.ch_hamza.mohangni.PojoClass.sub_cat;
import com.filesharing.ch_hamza.mohangni.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class sub_categories extends AppCompatActivity {
    ArrayList<sub_cat> arrayList = new ArrayList<>();
    RecyclerView recyclerView;
    sub_cat_recylcerview adapter;
    RecyclerView.LayoutManager layoutManager;
    String WEB_URL;
    private ProgressDialog loading;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sub_cat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ImageView bag=(ImageView) findViewById(R.id.bag);
        bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent= new Intent(sub_categories.this,MyCart.class);
             //   startActivity(intent);
            }
        });
        recyclerView=(RecyclerView) findViewById(R.id.model_recyclerView);
        layoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        WEB_URL=intent.getStringExtra("weburl");
        ImageView whatsapp=(ImageView) findViewById(R.id.whatsapp);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri  =Uri.parse("smsto:"+"+923161433343");
                Intent intent =new Intent(Intent.ACTION_SENDTO,uri);
                intent.setPackage("com.whatsapp");
                startActivity(intent);
            }
        });
        Getsub_categories();

    }

    private void Getsub_categories() {
        loading = ProgressDialog.show(sub_categories.this,"loading","plasewait",false,false);
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_Sub_Categories, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray data=null;
                try {
                    data = new JSONArray(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (data == null){
                    Intent intent=new Intent(sub_categories.this,All_Products.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
                else {
                    try {
                        for (int j=0;j<data.length();j++){
                            JSONObject cat = data.getJSONObject(j);
                            arrayList.add(new sub_cat(cat.getString("Catagory_id"), cat.getString("name")));
                        }
                        adapter = new sub_cat_recylcerview(arrayList, sub_categories.this,WEB_URL);
                        recyclerView.setAdapter(adapter);
                        loading.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        },
        new com.android.volley.Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(sub_categories.this.getApplicationContext(),"Volley Error"+error, Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("categories", id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(sub_categories.this.getApplicationContext());
        requestQueue.add(request);

    }

}