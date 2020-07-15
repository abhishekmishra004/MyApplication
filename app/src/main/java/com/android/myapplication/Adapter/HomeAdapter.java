package com.android.myapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.shape.RoundedCornerTreatment;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.myapplication.Model.HomeModel;
import com.android.myapplication.Overview;
import com.android.myapplication.R;
import com.android.myapplication.RoundCornersTransform;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    Context context;
    List<HomeModel> homeModels;

    public HomeAdapter(Context context, List<HomeModel> homeModels) {
        this.context = context;
        this.homeModels = homeModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.card, viewGroup,false);
        return new HomeAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.title.setText(homeModels.get(i).getTitle());
        myViewHolder.subtitle.setText(homeModels.get(i).getSubtitle());
        int radius = 5;
        Picasso.get()
                .load(homeModels.get(i).getUrl())
                .fit()
                .centerCrop()
                .transform(new RoundCornersTransform(radius))
                .into(myViewHolder.homeImage);
        final int position = i;
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"position "+position,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, Overview.class);
                Bundle bundle = new Bundle();
                bundle.putString("title",homeModels.get(position).getTitle());
                bundle.putString("subtitle",homeModels.get(position).getSubtitle());
                bundle.putString("content",homeModels.get(position).getContent());
                bundle.putString("url",homeModels.get(position).getUrl());
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,myViewHolder.homeImage, ViewCompat.getTransitionName(myViewHolder.homeImage));
                intent.putExtra("bundle",bundle);
                context.startActivity(intent,optionsCompat.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title,subtitle;
        ImageView homeImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txtTitle);
            subtitle = itemView.findViewById(R.id.txtSubTitle);
            homeImage = itemView.findViewById(R.id.HomeImg);
        }
    }
}
