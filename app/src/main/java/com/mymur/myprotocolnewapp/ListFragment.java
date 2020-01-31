package com.mymur.myprotocolnewapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.mymur.myprotocolnewapp.Interfaces.Observer;

import java.util.ArrayList;

public class ListFragment extends Fragment implements Observer {

    private MaterialButton addNewBtn;
    private TextView listTitleText;
    //массив для списка
    protected ArrayList<String> stringsArray;
    private String activityName;
    private String myNewString;

    private MyData myData;
    private int placeId;


    //для RecyclerView
    //адаптер



    //Конструктор
    public ListFragment() {
        myData.registerObserver(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        stringsArray = new ArrayList<>();
//
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        //задаём кнопке addnew кликлистенер
        MaterialButton addNewBtn = view.findViewById(R.id.addNewBtn);
       // addNewBtn.setOnClickListener(addNewClickListener);
        //ищем текстВью с заголовком
        listTitleText = view.findViewById(R.id.listTitleText);



        return view;
    }




//TODO
//вот тут нужно сделать обновление списка recyclerView
    @Override
    public void updateViewData(String newString) {

    }
}
