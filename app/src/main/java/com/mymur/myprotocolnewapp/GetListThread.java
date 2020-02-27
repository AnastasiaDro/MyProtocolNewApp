package com.mymur.myprotocolnewapp;

import android.util.Log;

import com.mymur.myprotocolnewapp.Constants;
import com.mymur.myprotocolnewapp.DataBaseTables.DataBaseHelper;
import com.mymur.myprotocolnewapp.MyData;

import java.util.HashMap;


import static com.mymur.myprotocolnewapp.Constants.HOME_ACTIVITY_CONSTANT;
import static com.mymur.myprotocolnewapp.Constants.SPLASH_ACTIVITY_CONSTANT;
import static com.mymur.myprotocolnewapp.MyData.getInstance;

public class GetListThread extends Thread {

    HashMap studentsHashMap;
    HashMap studentTrialsHashMap;
    DataBaseHelper dbHelper;
    MyData myData;
    int activityCode;
    int currentStudentId;

    public GetListThread(DataBaseHelper dbHelper) {
        this.dbHelper = dbHelper;

        myData = MyData.getInstance(dbHelper);
        this.activityCode = myData.getCurrentActivityCode();
        System.out.println("activityCode: в гетлист тред" + activityCode);
        studentsHashMap = new HashMap();
        studentTrialsHashMap = new HashMap();
        currentStudentId = myData.getCurrentStudentId();
    }

    @Override
    public void run() {
        super.run();
        switch (activityCode) {
        case SPLASH_ACTIVITY_CONSTANT :
                studentsHashMap = dbHelper.extractStudents();
                myData.setCurrentHashMap(studentsHashMap);
                break;
        case Constants.PRACTICE_ACTIVITY_CONSTANT:
                if (!(currentStudentId <0)) {
                    studentTrialsHashMap = dbHelper.extractTrialsOfStudentHashMap(currentStudentId);
                } else {
                    Log.d("MyData", "не выбран студент");
                }
            myData.setCurrentHashMap(studentTrialsHashMap);
            break;
            case Constants.HOME_ACTIVITY_CONSTANT:
                studentsHashMap = dbHelper.extractStudents();
                myData.setCurrentHashMap(studentsHashMap);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + activityCode);
        }

    }
}
