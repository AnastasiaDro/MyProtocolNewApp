package com.mymur.myprotocolnewapp;

import android.util.Log;

import com.mymur.myprotocolnewapp.DataBaseTables.DataBaseHelper;
import com.mymur.myprotocolnewapp.Interfaces.Observable;
import com.mymur.myprotocolnewapp.Interfaces.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MyData implements Observable {
    private static MyData instance;
    private List observers;
    private DataBaseHelper dbHelper;
    //список студентов имя/номер
    private HashMap studentsHashMap;
    private HashMap studentTrialsHashMap;


    //делаем из класса синглтон

    private MyData(DataBaseHelper dbHelper){
        studentsHashMap = new HashMap();
        observers = new LinkedList<>();
        this.dbHelper = dbHelper;


        //в получившийся аррэйлист загружаем данные из БД
    }


    public static MyData getInstance(DataBaseHelper dbHelper){
        if (instance == null) {
            instance = new MyData(dbHelper);
        }
        return instance;
    }

    public static MyData getMyData(){
        return instance;
    }


    private HashMap <String, Integer> getCurrentHashMap(int activityCode){
        Thread dbThread;

        Runnable currentHashMapRunnable;
        switch (activityCode) {
            case Constants.SPLASH_ACTIVITY_CONSTANT:
            currentHashMapRunnable = new Runnable() {
                @Override
                public void run() {
                    studentsHashMap = dbHelper.extractStudents();
                }
            };
                break;
            case Constants.HOME_ACTIVITY_CONSTANT:
                currentHashMapRunnable = new Runnable() {
                    @Override
                    public void run() {
                        studentTrialsHashMap = dbHelper.);
                    }
                };

            default:
                throw new IllegalStateException("Unexpected value: " + activityCode);
        }
        dbThread = new Thread(currentHashMapRunnable, "");
        dbThread.start();
        try {
            dbThread.join();
        } catch (InterruptedException e){
           Log.d("Потоки MyData", "не дождались выгрузки потока");
        }

        return
    }





    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {

    }



}
