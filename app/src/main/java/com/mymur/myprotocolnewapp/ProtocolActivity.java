package com.mymur.myprotocolnewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ProtocolActivity extends AppCompatActivity {
    int activityCode = Constants.PRACTICE_ACTIVITY_CONSTANT;
    MyData myData = MyData.getMyData();
    int placeId = R.id.placeholderForProtocol;
    ListFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myData.setCurrentActivityCode(activityCode);
        Bundle bundle = new Bundle();
        bundle.putInt("CurrentPosition", 0);
        setContentView(R.layout.activity_protocol);
        fragment = new ListFragment(placeId, activityCode);
        fragment.setArguments(bundle);
        fragment.postFragment(this);

    }
}
