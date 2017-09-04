package com.tatlicilar.visualeducation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Asus on 15.08.2017.
 */
/*
public class myAdapter extends RecyclerView.Adapter<myAdapter.MyViewHolder> {
    Context contetx;
    LayoutInflater inflater;
    ArrayList<NavigationDrawerItem> mDataList;
    myAdapter(Context c, ArrayList<NavigationDrawerItem> data)
    {
    this.contetx=c;
        this.inflater=LayoutInflater.from(contetx);
        mDataList=data;
    }

    {

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      //View v=inflater.inflate(R.layout.single_line,parent,false);
      MyViewHolder holder=new MyViewHolder(v);
        return  holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavigationDrawerItem tıklama=mDataList.get(position);
        holder.setData(tıklama,position);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView title;
        ImageView image;
      public MyViewHolder(View itemView) {
          super(itemView);
          title=itemView.findViewById(R.id.title);
          image=itemView.findViewById(R.id.imgIcon);

      }
        public void setData(NavigationDrawerItem tıklama, int position) {

            this.title.setText(tıklama.getTitle());
            this.image.setImageResource(tıklama.getImageId());
        }
    }

}
*/
