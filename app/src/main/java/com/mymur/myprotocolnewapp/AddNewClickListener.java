package com.mymur.myprotocolnewapp;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import com.mymur.myprotocolnewapp.DataBaseTables.DataBaseHelper;

public class AddNewClickListener implements View.OnClickListener {
    String myNewString;
    MyData myData;
    DataBaseHelper dataBaseHelper;
    int activityCode;

    public AddNewClickListener(int activityCode) {
        this.myData = MyData.getMyData();
        this.dataBaseHelper = myData.getDbHelper();
        this.activityCode = activityCode;
    }

    @Override
    public void onClick(View v) {
        final Context context = v.getContext();
        final EditText input = new EditText(context);
        createInputDialog(context, input);
    }

    //делаем диалог с юзером для добавления нового значения в отображаемый массив
    protected void createInputDialog(Context context, final EditText input) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.enter_name);
        builder.setView(input);
        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myNewString = input.getText().toString();
                addToDataBaseAndUpdateData(myNewString);
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


    //добавляет новую строку в базу данных
    public void addToDataBaseAndUpdateData(String myNewString) {
        switch (activityCode) {
            case (Constants.HOME_ACTIVITY_CONSTANT):
                dataBaseHelper.saveOneStudentToDb(myNewString);
            break;
            case (Constants.PRACTICE_ACTIVITY_CONSTANT):
                dataBaseHelper.saveNewTrialToDbIfNotExists(myNewString);
             break;
        }
        //обновляем MyData и уведомляем слушателей
        myData.updateData(activityCode);
    }

}
