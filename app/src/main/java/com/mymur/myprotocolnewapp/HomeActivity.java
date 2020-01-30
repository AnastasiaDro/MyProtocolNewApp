package com.mymur.myprotocolnewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mymur.myprotocolnewapp.Interfaces.ActivMethods;

//основная активность
public class HomeActivity extends AppCompatActivity implements ActivMethods {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }


    @Override
    public void init(){

    }

    @Override
    public int getActivityCode(){
        return Constants.HOME_ACTIVITY_CONSTANT;
    }
}
