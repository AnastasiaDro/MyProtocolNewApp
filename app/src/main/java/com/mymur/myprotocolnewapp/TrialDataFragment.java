package com.mymur.myprotocolnewapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mymur.myprotocolnewapp.Interfaces.FragmentMethods;
import com.mymur.myprotocolnewapp.Interfaces.Observer;

public class TrialDataFragment extends Fragment implements Observer, FragmentMethods {
    int currentTrialId;
    MyData myData;
    int placeId;

    public TrialDataFragment(int placeId) {
        myData = MyData.getMyData();
        myData.registerObserver(this);
        currentTrialId = myData.getCurrentTrialId();
        this.placeId = placeId;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //не даём пересоздать фрагмент при повороте экрана
        setRetainInstance(true);
        View view = inflater.inflate(R.layout.fragment_trial_data, container, false);
        //получим доступ к классу MyData;
        //myData = MyData.getMyData();
        //ищем кнопки и т.п.
        findViews(view);
        return view;
    }




    //Найти необъодимые view - кнопки и т.п.
    @Override
    public void findViews(View view) {

    }

    //Вставить фрагмент
    @Override
    public void postFragment(AppCompatActivity activity){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(placeId, this);
        ft.commit();
    }



    @Override
    public void updateViewData() {
        currentTrialId = myData.getCurrentTrialId();
    }



}
