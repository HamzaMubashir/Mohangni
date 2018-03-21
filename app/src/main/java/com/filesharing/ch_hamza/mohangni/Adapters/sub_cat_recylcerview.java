package com.filesharing.ch_hamza.mohangni.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.filesharing.ch_hamza.mohangni.Activities.sub_categories;
import com.filesharing.ch_hamza.mohangni.PojoClass.sub_cat;
import com.filesharing.ch_hamza.mohangni.R;

import java.util.ArrayList;

/**
 * Created by User on 2/21/2018.
 */

public class sub_cat_recylcerview extends RecyclerView.Adapter<sub_cat_recylcerview.MyViewHolder> {
    ArrayList<sub_cat> arrayList= new ArrayList<>();
    Activity activity;
    String WebUrl;
    public sub_cat_recylcerview(ArrayList<sub_cat> arrayList, Context context,String WebUrl){
        this.arrayList=arrayList;
        activity=(Activity)context;
        this.WebUrl=WebUrl;

    }

    @Override
    public sub_cat_recylcerview.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_sub_cat,parent,false);
        return new sub_cat_recylcerview.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(sub_cat_recylcerview.MyViewHolder holder, final int position) {
        holder.name.setText(arrayList.get(position).getName());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrayList.get(position).getName().equals("Blazer")){
                    String subpart=WebUrl+"/"+"blazer-2".toLowerCase().replace("","-");
                    Intent intent = new Intent(activity,sub_categories.class);
                    intent.putExtra("id",arrayList.get(position).getCatagory_id());
                    intent.putExtra("WebUrl",subpart);
                    activity.startActivity(intent);
                }else {
                    String subpart=WebUrl+"/"+arrayList.get(position).getName().toLowerCase().replace("","-");
                    Intent intent = new Intent(activity,sub_categories.class);
                    intent.putExtra("id",arrayList.get(position).getCatagory_id());
                    intent.putExtra("WebUrl",subpart);
                    activity.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
