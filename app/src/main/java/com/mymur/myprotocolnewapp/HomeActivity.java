package com.mymur.myprotocolnewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.mymur.myprotocolnewapp.Interfaces.ActivMethods;

//основная активность
public class HomeActivity extends AppCompatActivity implements ActivMethods {

    ListFragment fragment;
    private MyData myData;
    int placeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        placeId = R.id.placeholder;
        init();
    }

    @Override
    public void init(){
        fragment = new ListFragment(placeId, getActivityCode());
        Log.d("HomeActivity", "Создан ListFragment");
        fragment.postFragment(this);
    }

    @Override
    public int getActivityCode(){
        return Constants.HOME_ACTIVITY_CONSTANT;
    }
}
