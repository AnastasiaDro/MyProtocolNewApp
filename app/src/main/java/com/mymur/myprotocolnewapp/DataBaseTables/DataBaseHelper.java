package com.mymur.myprotocolnewapp.DataBaseTables;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mymur.myprotocolnewapp.DataBaseTables.PracticingResultsTable;
import com.mymur.myprotocolnewapp.DataBaseTables.ResCodeTable;
import com.mymur.myprotocolnewapp.DataBaseTables.StudentsTable;
import com.mymur.myprotocolnewapp.DataBaseTables.TrialsTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class DataBaseHelper extends SQLiteOpenHelper{
    //константы
    private static final String DATABASE_NAME = "protocols.bd";
    private static final int DATABASE_VERSION = 1;
    String [] resNames;
    private ArrayList<String> studentsNamesArr;
    private ArrayList<String> studentTrialsNamesArr;
    private ArrayList<String> newStudentsNamesArr;
    private ArrayList<String> newStudentTrialsArr;
    private HashMap <String, Integer> studentsMap;
    private HashMap <String, Integer> trialsOfStudentMap;
    private ArrayList <Integer> studentTrialsIDArr;
    private HashSet  <String> allTrialsSet;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        resNames = new String[]{"bad", "with_hint", "good"};
        studentsMap = new HashMap<>();
        trialsOfStudentMap = new HashMap<>();
        studentsNamesArr = new ArrayList<>();
        studentTrialsNamesArr = new ArrayList<>();
        newStudentsNamesArr = new ArrayList<>();
        newStudentTrialsArr = new ArrayList<>();
        studentTrialsIDArr = new ArrayList<>();

        //пробы
        allTrialsSet = new HashSet<>();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //создадим 3 таблицы-справочника

        StudentsTable.createTable(db);
        TrialsTable.createTable(db);
        ResCodeTable.createTable(db);
        //создадим таблицу для результатов
        PracticingResultsTable.createTable(db);
        //заполним таблицу резалтКодов сразу же
        ResCodeTable.addAllResNames(resNames, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void saveArrayStudentsToDb(ArrayList <String> newStudentsArray) {
        for (int i = 0; i < newStudentsArray.size(); i++) {
    //        StudentsTable.addStudent(newStudentsArray.get(i), this.getWritableDatabase());
            saveOneStudentToDb(newStudentsArray.get(i));
        }
    }

    public void saveOneStudentToDb(String newStudent) {
        StudentsTable.addStudent(newStudent, this.getWritableDatabase());
    }


    //извлекает HashMap всех студентов
     public HashMap<String, Integer>  extractStudents() {
          studentsMap = StudentsTable.getAllStudentsNamesIfVisible(this.getWritableDatabase());
          return studentsMap;
     }




        public void saveNewTrialToDbIfNotExists (String trialName) {
        TrialsTable.addTrialIfNotExists(trialName, this.getWritableDatabase());
    }

    public void addTrialsResult (int studentId, int trialId, int resCode, SQLiteDatabase database) {
        PracticingResultsTable.addPracticing(studentId, trialId, resCode, database);
    }

    public int getTrialIdByName(String trialName) {
        int trialId = -1;
        trialId = TrialsTable.getAllTrialsNamesAndId(this.getReadableDatabase()).get(trialName);
        return trialId;
    }

    //получить все пробы студента в виде HashMap  Id пробы - имя пробы:
    public HashMap extractTrialsOfStudentHashMap (int studentId){
        //получаем список всех Id всех проб, которые выполнял студент
        ArrayList <Integer> allStudentTrialsIds = new ArrayList<>();
        allStudentTrialsIds = PracticingResultsTable.getStudentTrialsIDArray(studentId, this.getReadableDatabase());
        HashMap <Integer, String> allTrialsMap = new HashMap<>();
        //получаем HashMap всех проб
        allTrialsMap = TrialsTable.getTrialsIdAndNamesIfVisible(this.getReadableDatabase());
        //Создадим HashMap для пар "ключ-значение" проб, которые есть у студента
        HashMap <Integer, String> studentTrialsHashMap = new HashMap();
        // инт, куда будем в цикле записывать id, хранящийся в массиве
        Integer key;
        for (int i = 0; i < allStudentTrialsIds.size(); i++) {
            key = allStudentTrialsIds.get(i);
            studentTrialsHashMap.put(key, allTrialsMap.get(key));
        }
        return studentTrialsHashMap;
    }



    public ArrayList <String> getAllStudentTrialsNamesByIdArrayList(int studentId){
        ArrayList <Integer> allStudentTrialsIds = new ArrayList<>();
        allStudentTrialsIds = PracticingResultsTable.getStudentTrialsIDArray(studentId, this.getReadableDatabase());
        HashMap <Integer, String> allTrialsMap = new HashMap<>();
        allTrialsMap = TrialsTable.getTrialsIdAndNamesIfVisible(this.getReadableDatabase());
        ArrayList<String> studentTrialsNames = new ArrayList<>();
        for (int i = 0; i < allStudentTrialsIds.size(); i++) {
            studentTrialsNames.add(allTrialsMap.get(allStudentTrialsIds.get(i)));
        }
        return studentTrialsNames;
    }

    //делает значение в списке студентов невидимым
    //TODO
    public void makeStudentInvisible(int student_id) {
        StudentsTable.makeStudentInvisible(student_id, this.getWritableDatabase());
    }

    public void makeTrialInvisible(int trial_id) {
        TrialsTable.makeTrialInvisible(trial_id, this.getWritableDatabase());
    }


}




