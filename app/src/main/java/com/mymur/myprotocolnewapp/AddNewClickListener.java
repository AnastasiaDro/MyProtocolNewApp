package com.mymur.myprotocolnewapp;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.mymur.myprotocolnewapp.DataBaseTables.DataBaseHelper;

public class AddNewClickListener implements View.OnClickListener {
    String myNewString;
    MyData myData;
    DataBaseHelper dataBaseHelper;

    public AddNewClickListener() {
        this.myData = MyData.getMyData();
        this.dataBaseHelper = myData.

    }

    @Override
    public void onClick(View v) {
        final Context context = v.getContext();
        final EditText input = new EditText(context);
        createInputDialog(context, input, recyclerView);
    }

    //делаем диалог с юзером для добавления нового значения в отображаемый массив
    protected void createInputDialog(Context context, final EditText input, final RecyclerView recyclerView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.enter_name);
        builder.setView(input);
        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myNewString = input.getText().toString();
                //изменяем данные в Observable классе и в нем уже уведомляем об этом наблюдателей
                myData.changeArrayList(myNewString, activityName);
                //изменяем текущие имя и ID на новые
                // myData.setCurrentStudentNameAndId(myNewString);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();


    }

    public void addToDataBase(String myNewString) {

    }

}
