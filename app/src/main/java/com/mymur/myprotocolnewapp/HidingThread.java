package com.mymur.myprotocolnewapp;

import android.util.Log;

import com.mymur.myprotocolnewapp.DataBaseTables.DataBaseHelper;

import java.util.HashMap;

import static com.mymur.myprotocolnewapp.Constants.HOME_ACTIVITY_CONSTANT;
import static com.mymur.myprotocolnewapp.Constants.PRACTICE_ACTIVITY_CONSTANT;

public class HidingThread extends Thread {

    DataBaseHelper dbHelper;
    int activityCode;
    int currentId;

    public HidingThread(DataBaseHelper dbHelper, int activityCode, int currentId){
        this.dbHelper = dbHelper;
        this.activityCode = activityCode;
        this.currentId = currentId;
    }

    @Override
    public void run(){
        switch (activityCode) {
            case HOME_ACTIVITY_CONSTANT :
                dbHelper.makeStudentInvisible(currentId);
                break;
            case Constants.PRACTICE_ACTIVITY_CONSTANT:
                dbHelper.makeTrialInvisible(currentId);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + activityCode);
        }
    }

}
