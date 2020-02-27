package com.mymur.myprotocolnewapp;

import androidx.fragment.app.Fragment;

import com.mymur.myprotocolnewapp.Interfaces.Observer;

public class TrialDataFragment extends Fragment implements Observer {
    int currentTrialId;
    MyData myData;

    public TrialDataFragment() {
        myData = MyData.getMyData();
        myData.registerObserver(this);
        currentTrialId = myData.getCurrentTrialId();
    }






    @Override
    public void updateViewData() {
        currentTrialId = myData.getCurrentTrialId();
    }
}
