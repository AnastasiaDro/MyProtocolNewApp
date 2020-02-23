package com.mymur.myprotocolnewapp;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter {
   // private ArrayList myDataList;
    MyData myData;
    ArrayList myDataList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(final View itemView){
            super(itemView);

        }
    }

    //Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter (ArrayList myDataList) {
        this.myData = MyData.getMyData();
        this.myDataList = myDataList;
    }


    //Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create a new view
 //    TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.linear_card, parent, false);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.linear_card, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView textView = holder.itemView.findViewById(R.id.textName);
        Log.d("MyDataList", myDataList.toString());
        Log.d("position=", Integer.toString(position));
        textView.setText(myDataList.get(position).toString());
    }


    public void changeDataList(ArrayList newDataList) {
        myDataList = newDataList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return myDataList.size();
    }
}
