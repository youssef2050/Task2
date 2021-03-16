package com.menu.task3.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.menu.task3.R;
import com.menu.task3.ui.dashboard.Adapter.AdapterGroup;
import com.menu.task3.ui.dashboard.Adapter.Group;

import java.util.List;

public class DashboardFragment extends Fragment implements AdapterGroup.OnClickItem {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.rc_list_group);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        AdapterGroup adapterGroup = new AdapterGroup(this);
        recyclerView.setAdapter(adapterGroup);
        dashboardViewModel.getDatas().observe(getViewLifecycleOwner(), new Observer<List<Group>>() {
            @Override
            public void onChanged(List<Group> groups) {
                Group.installData();
                adapterGroup.setData(groups);
            }
        });
        return root;
    }

    @Override
    public void onClick(Group group) {
        Toast.makeText(getContext(), group.getName(), Toast.LENGTH_SHORT).show();
    }
}