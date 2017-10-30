package com.park1993.diary1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by alfo6-2 on 2017-07-14.
 */

public class MainRecyclerAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<Item> items;
    LayoutInflater inflater;


    public MainRecyclerAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
        inflater=LayoutInflater.from(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=inflater.inflate(R.layout.item,parent,false);
        MyViewHolder holder=new MyViewHolder(view);




        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(!items.get(position).getQuery().equals("")) {
            ((MyViewHolder) holder).tvQuery.setText(items.get(position).getQuery());
        }
        if(items.get(position).getContent()!=null) {
            ((MyViewHolder) holder).tvContent.setText(items.get(position).getContent());
        }else {
            ((MyViewHolder) holder).tvContent.setText("");
        }
        if(items.get(position).getImgUri()!=null) Glide.with(context).load(items.get(position).getImgUri()).into(((MyViewHolder)holder).circleImageView);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvQuery;
        TextView tvContent;
        CircleImageView circleImageView;


        public MyViewHolder(final View itemView) {
            super(itemView);

            tvQuery=(TextView)itemView.findViewById(R.id.tv_query);
            tvContent=(TextView)itemView.findViewById(R.id.tv_content);
            circleImageView=(CircleImageView)itemView.findViewById(R.id.iv_circle);



            Typeface typeface = Typeface.createFromAsset(context.getAssets(),"nanumpen.ttf");
            tvContent.setTypeface(typeface);
            tvQuery.setTypeface(typeface);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(items.get(getLayoutPosition()).getImgUri()!=null){
                        ((MainActivity)context).카드뷰클릭(tvQuery.getText().toString(),tvContent.getText().toString(),getLayoutPosition(),(CardView) itemView,items.get(getLayoutPosition()).getImgUri());

                    }else {
                        ((MainActivity)context).카드뷰클릭(tvQuery.getText().toString(),tvContent.getText().toString(),getLayoutPosition(),(CardView) itemView);
                    }


                }
            });


        }
    }
}
