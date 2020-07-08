package com.android.myapplication;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.myapplication.Adapter.HomeAdapter;
import com.android.myapplication.Model.HomeModel;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home extends AppCompatActivity {

    RecyclerView recyclerView;
    List<HomeModel> homeModels ;
    ProgressDialog progressDialog;
    int retrycount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home");
        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(decor.findViewById(android.R.id.statusBarBackground), true);
        fade.excludeTarget(decor.findViewById(android.R.id.navigationBarBackground), true);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
        homeModels = new ArrayList<>();


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait!!");
        progressDialog.show();
        progressDialog.setCancelable(false);

        recyclerView = findViewById(R.id.HomeRcv);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(layoutmanager);
        JSON_WEB_CALL("https://dev-api.musewearables.com:9000/v1/test/route/animation");
    }


    public void JSON_WEB_CALL(final String Url) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("card_data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                HomeModel getHomemodel = new HomeModel();
                                try {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    getHomemodel.setTitle(json.getString("title"));
                                    getHomemodel.setSubtitle(json.getString("subtitle"));
                                    getHomemodel.setUrl(json.getString("url"));
                                    getHomemodel.setContent(json.getString("content"));
                                    Log.i("message",json.getString("title"));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                homeModels.add(getHomemodel);
                            }
                           // Toast.makeText(Home.this, "Len" + homeModels.size(), Toast.LENGTH_LONG).show();
                            HomeAdapter adapter = new HomeAdapter(Home.this, homeModels);
                            recyclerView.setAdapter(adapter);
                            progressDialog.hide();
                            progressDialog.setCancelable(true);

                        } catch (JSONException e) {
                            Toast.makeText(Home.this,"Jsonexception"+e.getMessage(),Toast.LENGTH_LONG).show();
                            progressDialog.hide();
                            progressDialog.setCancelable(true);
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home.this, "Error" + error +"\n"+ error.getMessage(), Toast.LENGTH_LONG).show();
                retrycount++;
                if(retrycount<3)
                {
                    Toast.makeText(Home.this, "Retrying .....", Toast.LENGTH_LONG).show();
                    JSON_WEB_CALL(Url);
                }
                progressDialog.hide();
                progressDialog.setCancelable(true);
            }
        })
        {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded/json";
            }
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("x-api-key", "NEEDKEYHERE");
                return params;
            }

        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}