package com.menu.task3.ui.CreateGroup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.menu.task3.R;
import com.menu.task3.ui.dashboard.Adapter.Group;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateGroupFragment extends Fragment {

    private CreateGroupViewModel mViewModel;
    private EditText name, description, member;
    private CircleImageView circleImageView;
    private FloatingActionButton addImage;
    private Button addGroup;

    public static CreateGroupFragment newInstance() {
        return new CreateGroupFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_group_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CreateGroupViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = view.findViewById(R.id.cf_name_group);
        description = view.findViewById(R.id.cf_descrption_group);
        member = view.findViewById(R.id.cf_member_group);
        circleImageView = view.findViewById(R.id.cf_img_group);
        addGroup = view.findViewById(R.id.cf_add_group);
        addImage = view.findViewById(R.id.cf_add_img_group);
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Group.addtoList(new Group(getIdGroup(), name.getText().toString(), description.getText().toString(), R.drawable.img_profile, Integer.parseInt(member.getText().toString())));
                Navigation.findNavController(view).navigate(R.id.navigation_dashboard);
            }
        });
    }

    private String getIdGroup() {
        Random random = new Random();
        return random.nextInt(100) + "";
    }

}