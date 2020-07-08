package com.android.myapplication;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.transition.Fade;
import android.util.Base64;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Overview extends AppCompatActivity {
    ImageView image,close;
    TextView title,subtitle;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        setTitle("Overview");
        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container),true);
        fade.excludeTarget(decor.findViewById(android.R.id.statusBarBackground),true);
        fade.excludeTarget(decor.findViewById(android.R.id.navigationBarBackground),true);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

        image =findViewById(R.id.OverviewImg);
        title = findViewById(R.id.txtOverviewTitle);
        subtitle = findViewById(R.id.txtOverviewSubTitle);
        webView = findViewById(R.id.webcontext);
        close = findViewById(R.id.imageclose);

        Bundle bundle = new Bundle();
        bundle = getIntent().getBundleExtra("bundle");

        Picasso.get()
                .load(bundle.getString("url"))
                .fit()
                .centerCrop()
                .into(image);
        title.setText(bundle.getString("title"));
        subtitle.setText(bundle.getString("subtitle"));
        String con = bundle.getString("content");
        con = con.substring(9,con.length());
        String encodedHtml = Base64.encodeToString(con.getBytes(), Base64.NO_PADDING);
        webView.loadData(encodedHtml, "text/html", "base64");

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
