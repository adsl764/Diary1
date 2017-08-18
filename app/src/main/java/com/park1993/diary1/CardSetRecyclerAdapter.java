package com.park1993.diary1;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HSP on 2017-08-07.
 */



public class CardSetRecyclerAdapter extends RecyclerView.Adapter {
        Context context;
        ArrayList<Item> items;
        LayoutInflater inflater;


        public CardSetRecyclerAdapter(Context context, ArrayList<Item> items) {
            this.context = context;
            this.items = items;
            inflater=LayoutInflater.from(context);

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view=inflater.inflate(R.layout.item2,parent,false);
            MyViewHolder holder=new MyViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((CardSetRecyclerAdapter.MyViewHolder)holder).tvQuery.setText(items.get(position).getQuery());


        }

        @Override
        public int getItemCount() {
            return items.size();
        }
        class MyViewHolder extends RecyclerView.ViewHolder{

            TextView tvQuery;
            TextView tvContent;
            ImageView ivDelete;
            public MyViewHolder(final View itemView) {
                super(itemView);
                ivDelete=(ImageView)itemView.findViewById(R.id.iv_delete);
                tvQuery=(TextView)itemView.findViewById(R.id.tv_query);
                tvContent=(TextView)itemView.findViewById(R.id.tv_content);

                ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        items.remove(getLayoutPosition());
                        notifyDataSetChanged();
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((CardSetActivity)context).selectCardView(tvQuery.getText().toString(),getLayoutPosition());
                    }
                });







            }

        }
    }


