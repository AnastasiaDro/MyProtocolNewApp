package com.mymur.myprotocolnewapp;

import android.util.Log;

import com.mymur.myprotocolnewapp.DataBaseTables.DataBaseHelper;
import com.mymur.myprotocolnewapp.Interfaces.Observable;
import com.mymur.myprotocolnewapp.Interfaces.Observer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MyData implements Observable {
    private static MyData instance;
    private List observers;
    private DataBaseHelper dbHelper;
    //список студентов имя/номер
    private HashMap studentsHashMap;
    private HashMap studentTrialsHashMap;
    int currentStudentId;


    //делаем из класса синглтон

    private MyData(DataBaseHelper dbHelper){
        studentsHashMap = new HashMap();
        observers = new LinkedList<>();
        this.dbHelper = dbHelper;
        currentStudentId = -1;


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


    //метод для получения текущего HashMap-а для загрузки в List на RecyclerView
    private HashMap <String, Integer> getCurrentHashMap(int activityCode){
        Thread dbThread;
        HashMap currentHashmap = new HashMap();
        Runnable currentHashMapRunnable;
        switch (activityCode) {
            case Constants.SPLASH_ACTIVITY_CONSTANT:
            currentHashMapRunnable = new Runnable() {
                @Override
                public void run() {
                    studentsHashMap = dbHelper.extractStudents();
                }
            };
            currentHashmap = studentsHashMap;
                break;
            case Constants.HOME_ACTIVITY_CONSTANT:
                currentHashMapRunnable = new Runnable() {

                        @Override
                        public void run () {
                            if (!(currentStudentId <0)) {
                                studentTrialsHashMap = dbHelper.extractTrialsOfStudentHashMap(currentStudentId);
                            } else {
                                Log.d("MyData", "не выбран студент");
                            }
                    }

                };
                currentHashmap = studentTrialsHashMap;
                break;

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

        return currentHashmap;
    }


    //поменяем местами ключ и значение в HashMap (идея в том, что менять местами данные в HashMap быстрее, чем поновой выгружать из базы
    public HashMap reverseKeyValue (HashMap <Object, Object> oldHashmap) {
        HashMap newHashMap = new HashMap();
        for (Map.Entry entry : oldHashmap.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " Value: "
                    + entry.getValue());
            newHashMap.put(entry.getValue().toString(), Integer.valueOf(entry.getKey().toString()));
        }
        return newHashMap;
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
