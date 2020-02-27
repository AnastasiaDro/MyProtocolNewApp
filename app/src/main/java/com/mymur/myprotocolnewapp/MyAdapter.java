package com.mymur.myprotocolnewapp;

import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;

public class MyAdapter extends RecyclerView.Adapter {
   // private ArrayList myDataList;
    MyData myData;
    ArrayList myDataList;
    int activityCode;
    String hidingName;
    private int selectedPosition;


    //ДЛЯ КОНТЕСТНОГО МЕНЮ
   int position;
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
        //выделенная цветом позиция

        public MyViewHolder(final View itemView){
            super(itemView);

            final TextView textView = itemView.findViewById(R.id.textName);
//ДЛЯ КОНТЕКСТНОГО МЕНЮ
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   Связано с выделением
                int currentPosition = getAdapterPosition();
                //   adapterPos = currentPosition;
                selectItemView(currentPosition, itemView);
                setCurrentIdInMyData(textView, itemView);
                }
             }
            );
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            switch (activityCode) {
                case (Constants.HOME_ACTIVITY_CONSTANT):
                    menu.add(this.getAdapterPosition(), Constants.ABOUT_STUDENT_CONTEXTMENU_ITEM, 100, R.string.aboutStudent);
                    break;
                case (Constants.PRACTICE_ACTIVITY_CONSTANT):
                    menu.add(this.getAdapterPosition(), Constants.ABOUT_TRIAL_CONTEXTMENU_ITEM, 100, R.string.aboutTrial);
                    break;
            }
            menu.add(this.getAdapterPosition(), Constants.HIDE_CONTEXTMENU_ITEM, 200, R.string.hide);
        }



    }

    public void setCurrentIdInMyData(TextView textView, View itemView) {
        String textViewContent = textView.getText().toString();
        HashMap <String, Integer> currentHashMap = myData.getCurrentHashMap();
        int currentId = currentHashMap.get(textViewContent);
        switch (activityCode) {
            case (Constants.HOME_ACTIVITY_CONSTANT):
                Intent intent = new Intent(textView.getContext(), ProtocolActivity.class);
                intent.putExtra("StudentName", textViewContent);
                //заменим в MyData имя текущего студента
                myData.setCurrentStudentId(currentId);
                itemView.getContext().startActivity(intent);
                break;
            case (Constants.PRACTICE_ACTIVITY_CONSTANT):
                myData.setCurrentTrialId(currentId);
                break;
        }
    }

    public void selectItemView(int currentPosition, View itemView) {
        if (selectedPosition != currentPosition) {
            // Temporarily save the last selected position
            int lastSelectedPosition = selectedPosition;
            // Save the new selected position
            selectedPosition = currentPosition;
            // update the previous selected row
            notifyItemChanged(lastSelectedPosition);
            // select the clicked row
            itemView.setSelected(true);
        }
    }


    //Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter (ArrayList myDataList) {
        this.myData = MyData.getMyData();
        this.myDataList = myDataList;
        this.activityCode = myData.getCurrentActivityCode();
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
