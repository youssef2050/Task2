package com.menu.task3.ui.dashboard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.menu.task3.ui.dashboard.Adapter.Group;

import java.util.List;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<List<Group>> datas;


    public DashboardViewModel() {
        datas = new MutableLiveData<>();
        datas.postValue(Group.data);
    }

    public MutableLiveData<List<Group>> getDatas() {
        return datas;
    }


}