package com.mymur.myprotocolnewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.mymur.myprotocolnewapp.DataBaseTables.DataBaseHelper;
import com.mymur.myprotocolnewapp.Interfaces.ActivMethods;



//только для splash-экрана, будем как рекомендовали на хабре, запускать его немедленно

public class SplashActivity extends AppCompatActivity implements ActivMethods {

    DataBaseHelper dataBaseHelper;
    MyData myData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //создаем помощник базы данных


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //тут выгрузить список студентов

            }
        });
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void init(){
        dataBaseHelper = new DataBaseHelper(this);
        myData = MyData.getInstance(dataBaseHelper);
    }

    @Override
    public int getActivityCode(){
        return Constants.SPLASH_ACTIVITY_CONSTANT;
    }

}
