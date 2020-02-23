package com.mymur.myprotocolnewapp;

import android.util.Log;

import com.mymur.myprotocolnewapp.DataBaseTables.DataBaseHelper;
import com.mymur.myprotocolnewapp.Interfaces.Observable;
import com.mymur.myprotocolnewapp.Interfaces.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MyData implements Observable {
    private static MyData instance;
    private List <Observer> observers;
    private DataBaseHelper dbHelper;
    //список студентов имя/номер
    private HashMap studentsHashMap;
    private HashMap studentTrialsHashMap;


    private HashMap currentHashMap;

    int currentStudentId;



    private ArrayList dataListForRecycler;



    //делаем из класса синглтон

    public void setCurrentHashMap(HashMap currentHashMap) {
        this.currentHashMap = currentHashMap;
    }

    public int getCurrentStudentId() {
        return currentStudentId;
    }

    private MyData(DataBaseHelper dbHelper){
        studentsHashMap = new HashMap();
        observers = new LinkedList<>();
        this.dbHelper = dbHelper;
        currentStudentId = -1;
        currentHashMap = new HashMap();
        dataListForRecycler = new ArrayList();

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

    public DataBaseHelper getDbHelper() {
        return dbHelper;
    }

    public ArrayList getDataListForRecycler() {
        if (!currentHashMap.isEmpty()) {
            dataListForRecycler.clear();
        }
        Log.d("getDataList myData", currentHashMap.toString());
        dataListForRecycler.addAll(currentHashMap.values());
        return dataListForRecycler;
    }

    //метод для получения текущего HashMap-а для загрузки в List на RecyclerView
    public HashMap  takeCurrentHashMap(int activityCode){
       GetListThread getListThread;
       Log.d("начало currentHashMap=", currentHashMap.toString());

        //ДОБАВИЛА
        getListThread = new GetListThread(dbHelper, activityCode);
        getListThread.start();
        try {
            getListThread.join();
        } catch (InterruptedException e){
           Log.d("Потоки MyData", "не дождались выгрузки потока");
        }
        //он изменяется из в GetListThread:
        Log.d("после потока HashMap=", currentHashMap.toString());
      return currentHashMap;
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


    //обновляем MyData и уведомляем слушателей
    public void updateData(int activityCode){
        //поновой получаем текущий hashMap для ArrayList-а
        takeCurrentHashMap(activityCode);
        //уведомляем всех слушателей
        notifyObservers();
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
        for (Observer observer : observers) {
            observer.updateViewData();
        }
    }







}
