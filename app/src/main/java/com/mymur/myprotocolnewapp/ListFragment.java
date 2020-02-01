package com.mymur.myprotocolnewapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.mymur.myprotocolnewapp.Interfaces.Observer;

import java.util.ArrayList;

public class ListFragment extends Fragment implements Observer {

    private MaterialButton addNewBtn;
    private TextView listTitleText;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;

    //массив для списка
    protected ArrayList<String> stringsArray;
    private String activityName;
    private String myNewString;

    private MyData myData;
    private int placeId;


    //для RecyclerView
    //адаптер



    //Конструктор
    public ListFragment(int placeId) {
        this.placeId = placeId;
        myData = MyData.getMyData();
        myData.registerObserver(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        //получим доступ к классу MyData;
        myData = MyData.getMyData();
        //ищем кнопки и т.п.
        findViews(view);
        //RecyclerView
        initRecycler(view);

        return view;
    }

    public void findViews(View view){
        addNewBtn = view.findViewById(R.id.addNewBtn);
        // addNewBtn.setOnClickListener(addNewClickListener);
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



//TODO
//вот тут нужно сделать обновление списка recyclerView
    @Override
    public void updateViewData(String newString) {

    }
}
