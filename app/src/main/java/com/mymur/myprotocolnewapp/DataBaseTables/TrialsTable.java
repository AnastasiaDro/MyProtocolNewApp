package com.mymur.myprotocolnewapp.DataBaseTables;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

//класс таблицы проб
public class TrialsTable {
    private final static String TABLE_NAME = "Trials";
    private final static String COLUMN_ID = "_id";
    private final static String COLUMN_NAME = "name";
    private final static String COLUMN_VISIBILITY = "visibility";

    static void createTable(SQLiteDatabase database){
        database.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_VISIBILITY +" INTEGER);");

    }

    public static void addTrial(String trialName, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, trialName);
        values.put(COLUMN_VISIBILITY, 1);
        database.insert(TABLE_NAME, null, values);
    }

    public static void addTrialIfNotExists(String trialName, SQLiteDatabase database){
       if (!getAllTrialsNamesAndId(database).containsValue(trialName));
        addTrial(trialName, database);
    }

    public static ArrayList <String> getNamesOfAllStudentTrial(ArrayList <Integer> studentTrialsIDArr, SQLiteDatabase database){
        ArrayList <String> namesOfAllStudentTrial = new ArrayList<>();
        HashMap <Integer, String> trialsHashMap = getTrialsIdAndNamesIfVisible(database);
        String trialName;
        for (int i = 0; i < studentTrialsIDArr.size(); i++) {
            trialName = trialsHashMap.get(studentTrialsIDArr.get(i));
            namesOfAllStudentTrial.add(trialName);
        }
        return namesOfAllStudentTrial;
    }


    public static HashMap<Integer, String> getTrialsIdAndNamesIfVisible(SQLiteDatabase database){
        HashMap<Integer, String> trialsHashMap = new HashMap<>();
        // Cursor myCursor = database.rawQuery("select name from Students", null);
        Cursor myCursor = database.rawQuery("SELECT " + COLUMN_ID + ", "+ COLUMN_NAME + " from "  +TABLE_NAME+ " WHERE " + COLUMN_VISIBILITY +" LIKE '" + 1 + "'",  null);
        int idIndex = myCursor.getColumnIndexOrThrow(COLUMN_ID);
        int nameIndex = myCursor.getColumnIndexOrThrow(COLUMN_NAME);

        while (myCursor.moveToNext()) {
            trialsHashMap.put(myCursor.getInt(idIndex), myCursor.getString(nameIndex));
            System.out.println(" добавили пробу");
        }
        myCursor.close();
        return trialsHashMap;

    }

    public static HashMap<Integer, String> getAllTrialsIdAndNames(SQLiteDatabase database){
        HashMap<Integer, String> trialsHashMap = new HashMap<>();
        // Cursor myCursor = database.rawQuery("select name from Students", null);
        Cursor myCursor = database.rawQuery("SELECT " + COLUMN_ID + ", "+ COLUMN_NAME + " from "  +TABLE_NAME,  null);
        int idIndex = myCursor.getColumnIndexOrThrow(COLUMN_ID);
        int nameIndex = myCursor.getColumnIndexOrThrow(COLUMN_NAME);

        while (myCursor.moveToNext()) {
            trialsHashMap.put(myCursor.getInt(idIndex), myCursor.getString(nameIndex));
            System.out.println(" добавили пробу");
        }
        myCursor.close();
        return trialsHashMap;

    }


public static HashMap<String, Integer> getAllTrialsNamesAndId(SQLiteDatabase database){
    HashMap<String, Integer> trialsHashMap = new HashMap<>();
    // Cursor myCursor = database.rawQuery("select name from Students", null);
    Cursor myCursor = database.rawQuery("SELECT " + COLUMN_ID + ", "+ COLUMN_NAME + " from "  +TABLE_NAME,  null);
    int idIndex = myCursor.getColumnIndexOrThrow(COLUMN_ID);
    int nameIndex = myCursor.getColumnIndexOrThrow(COLUMN_NAME);

    while (myCursor.moveToNext()) {
        trialsHashMap.put(myCursor.getString(nameIndex), myCursor.getInt(idIndex));
        System.out.println(" добавили пробу");
    }
    myCursor.close();
    return trialsHashMap;

}

    //Метод делания студента невидимым
    public static void makeTrialInvisible(int trial_id, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_VISIBILITY, 0);
        database.update(TABLE_NAME, contentValues, COLUMN_ID + "=" + String.valueOf(trial_id), null);
    }



}