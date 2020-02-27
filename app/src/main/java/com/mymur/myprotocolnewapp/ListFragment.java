package com.mymur.myprotocolnewapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.mymur.myprotocolnewapp.Interfaces.ActivMethods;
import com.mymur.myprotocolnewapp.Interfaces.Observer;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ListFragment extends Fragment implements Observer {

    private MaterialButton addNewBtn;
    private TextView listTitleText;
    private RecyclerView recyclerView;
   // private RecyclerView.Adapter myAdapter;
    MyAdapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;

    //массив для списка
    protected ArrayList<String> stringsArray;
    private String activityName;
    private String myNewString;

    private MyData myData;
    private int placeId;
    private int activityCode;

    //КликЛистенер для кнопки
    AddNewClickListener addNewClickListener;


    //Конструктор
    public ListFragment(int placeId, int activityCode) {
        this.placeId = placeId;
        this.activityCode = activityCode;
       // this.activityCode = activityCode;
        myData = MyData.getMyData();
        myData.registerObserver(this);
        addNewClickListener = new AddNewClickListener(activityCode);
        //отправим в MyData код текущей активности
        myData.setCurrentActivityCode(activityCode);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //не даём пересоздать фрагмент при повороте экрана
        setRetainInstance(true);
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        //получим доступ к классу MyData;
        myData = MyData.getMyData();
        //ищем кнопки и т.п.
        findViews(view);
        //RecyclerView
        initRecycler(view);

//ДЛЯ КОНТЕКСТНОГО МЕНЮ
        registerForContextMenu(recyclerView);
        return view;
    }



    public void findViews(View view){
        addNewBtn = view.findViewById(R.id.addNewBtn);
        addNewBtn.setOnClickListener(addNewClickListener);
        //ищем текстВью с заголовком
        listTitleText = view.findViewById(R.id.listTitleText);
    }

    public void initRecycler(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerForFragment);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        // specify an adapter (see also next example)

        myAdapter = new MyAdapter(myData.getDataListForRecycler());
        recyclerView.setAdapter(myAdapter);
    }

    //Вставить фрагмент
    public void postFragment(AppCompatActivity activity){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(placeId, this);
        ft.commit();
    }


//обновление списка recyclerView
    @Override
    public void updateViewData() {
        myData.getDataListForRecycler();
        myAdapter.notifyDataSetChanged();
    }


    //КОНТЕКСТНОЕ МЕНЮ
    @Override
    public boolean onContextItemSelected (MenuItem item) {

        switch (item.getItemId()){
            case Constants.ABOUT_STUDENT_CONTEXTMENU_ITEM:
                //TODO
            return true;
            case Constants.ABOUT_TRIAL_CONTEXTMENU_ITEM:
                //TODO
            return true;
            case Constants.HIDE_CONTEXTMENU_ITEM:
                myAdapter.removeItem(item.getGroupId());
                displayMessage(getResources().getString(R.string.positionHide));
            return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    //Вывод сообщения
    private void displayMessage(String message) {
        Snackbar.make(this.getView(), message, Snackbar.LENGTH_LONG).show();
    }
}
