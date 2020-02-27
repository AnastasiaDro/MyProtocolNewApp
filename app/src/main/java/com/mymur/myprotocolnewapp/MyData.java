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
    //счетчик использования метода getDataListForRecycler;
    int dataListUsing = 0;
    private HashMap <String, Integer> currentHashMap;
    int currentStudentId;
    private ArrayList dataListForRecycler;
    int currentActivityCode;



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
        //теперь получим код активности
        currentActivityCode = -1;
    }


    public static MyData getInstance(DataBaseHelper dbHelper){
        if (instance == null) {
            instance = new MyData(dbHelper);
        //если это запуск программы, то текущий код активности сделаем HOME_ACTIVITY_CONSTANT - будут выгруджаться студенты
            instance.setCurrentActivityCode(Constants.HOME_ACTIVITY_CONSTANT);
        }
        return instance;
    }

    //задаёт текущий код активности
    public void setCurrentActivityCode (int activityCode) {
        currentActivityCode = activityCode;
    }
    public Integer getCurrentActivityCode(){
        return currentActivityCode;
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
        dataListForRecycler.addAll(currentHashMap.keySet());
        //счетчик использования метода
        dataListUsing++;
        Log.d("getList use раз: ", Integer.toString(dataListUsing));
        return dataListForRecycler;
    }

    //метод для получения текущего HashMap-а для загрузки в List на RecyclerView
    public HashMap  takeCurrentHashMap(){
       GetListThread getListThread;
       Log.d("начало currentHashMap=", currentHashMap.toString());

        //ДОБАВИЛА
        getListThread = new GetListThread(dbHelper, currentActivityCode);
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
    public void updateData(){
        //поновой получаем текущий hashMap для ArrayList-а
        takeCurrentHashMap();
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

    public void hideNameFromList(String name) {
        //получим id из базы данных
        int id = currentHashMap.get(name);

        //запустим поток, который внесёт заметку в БД, что item теперь скрыт:
        HidingThread hidingThread = new HidingThread(dbHelper, currentActivityCode, id);
        hidingThread.start();
        //удалим из текущего сurrentHashMap
        currentHashMap.remove(name);
        //уведомляем наблюдателей об изменении
        notifyObservers();
    }



}
