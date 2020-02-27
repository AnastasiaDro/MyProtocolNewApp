package com.mymur.myprotocolnewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mymur.myprotocolnewapp.Interfaces.ActivMethods;

public class ProtocolActivity extends AppCompatActivity implements ActivMethods {
    ListFragment listFragment;
    TrialDataFragment trialFragment;
    int placeId_list;
    int placeId_trialData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol);
        init();

    }

    @Override
    public void init() {
        placeId_list = R.id.placeholderForProtocol;
        placeId_trialData = R.id.placePracticeFragment;
        listFragment = new ListFragment(placeId_list, getActivityCode());
        trialFragment = new TrialDataFragment(placeId_trialData);
        listFragment.postFragment(this);
        trialFragment.postFragment(this);
    }

    @Override
    public int getActivityCode() {
        return Constants.PRACTICE_ACTIVITY_CONSTANT;
    }
}
