package com.park1993.diary1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by HSP on 2017-08-21.
 */

public class PhotoRecyclerAdapter extends RecyclerView.Adapter {

    Context context;
    DBHelper helper;
    LayoutInflater inflater;
    ArrayList<PhotoItem> photoItems;

    public void setItem(){
        helper.setPhotoItems(photoItems,this);
    }

    public PhotoRecyclerAdapter(Context context, DBHelper helper) {
        this.context = context;
        this.helper = helper;
        photoItems=new ArrayList<>();
        inflater= LayoutInflater.from(context);
        setItem();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.photo_item,parent,false);
        PhotoViewHolder holder=new PhotoViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Glide.with(context).load(photoItems.get(position).path).into(((PhotoViewHolder)holder).imageView);
        String y=(photoItems.get(position).date).substring(0,4);
        String m=(photoItems.get(position).date).substring(4,6);
        String d=(photoItems.get(position).date).substring(6,8);
        ((PhotoViewHolder)holder).textView.setText(y+"년\n"+m+"월"+d+"일");
    }

    @Override
    public int getItemCount() {
        return photoItems.size();
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;


        public PhotoViewHolder(View itemView) {
            super(itemView);
            textView=(TextView) itemView.findViewById(R.id.photo_tv);
            imageView=(ImageView)itemView.findViewById(R.id.photo);
        }
    }
}
