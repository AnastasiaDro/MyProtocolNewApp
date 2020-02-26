package com.mymur.myprotocolnewapp;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter {
   // private ArrayList myDataList;
    MyData myData;
    ArrayList myDataList;
    int activityCode;
    String hidingName;

    //ДЛЯ КОНТЕСТНОГО МЕНЮ
    private int position;
    public int getPosition(){
        return position;
    }

    public void setPosition(int position){
        this.position = position;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public MyViewHolder(final View itemView){
            super(itemView);

//ДЛЯ КОНТЕКСТНОГО МЕНЮ
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            switch (activityCode) {
                case (Constants.HOME_ACTIVITY_CONSTANT):
                    menu.add(this.getAdapterPosition(), 111, 100, R.string.aboutStudent);
                    break;
                case (Constants.PRACTICE_ACTIVITY_CONSTANT):
                    menu.add(this.getAdapterPosition(), 222, 100, R.string.aboutTrial);
                    break;
            }
            menu.add(this.getAdapterPosition(), 333, 200, R.string.hide);
        }
    }




    //Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter (ArrayList myDataList, int activityCode) {
        this.myData = MyData.getMyData();
        this.myDataList = myDataList;
        this.activityCode = activityCode;
        //строка, которую нужно скрыть и изменить в базе данных
        hidingName = null;

    }



    //удаление элемента из списка
    //метод скрытия студента из списка
    public void removeItem(int position){
        hideNameByMyData(position);
    }

    //передаем обработку в MyData (поменять значение видимости в БД, удалить из CurrentHashMap)
    public void hideNameByMyData(int position) {
        hidingName = myDataList.get(position).toString();
        System.out.println("МЫ СКРЫВАЕМ ИЗ СПИСКА:");
        System.out.println(hidingName);
        myData.hideNameFromList(hidingName);
    }


    //Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.linear_card, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView textView = holder.itemView.findViewById(R.id.textName);
        textView.setText(myDataList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return myDataList.size();
    }






}
