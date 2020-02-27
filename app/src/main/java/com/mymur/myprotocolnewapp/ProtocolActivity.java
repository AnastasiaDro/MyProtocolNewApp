package com.mymur.myprotocolnewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mymur.myprotocolnewapp.Interfaces.ActivMethods;

public class ProtocolActivity extends AppCompatActivity implements ActivMethods {
    ListFragment fragment;
    int placeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol);
        init();

    }

    @Override
    public void init() {
        placeId = R.id.placeholderForProtocol;
        fragment = new ListFragment(placeId, getActivityCode());
        fragment.postFragment(this);
    }

    @Override
    public int getActivityCode() {
        return Constants.PRACTICE_ACTIVITY_CONSTANT;
    }
}
