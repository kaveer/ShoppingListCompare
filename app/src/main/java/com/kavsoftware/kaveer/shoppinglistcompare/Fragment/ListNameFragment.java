package com.kavsoftware.kaveer.shoppinglistcompare.Fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kavsoftware.kaveer.shoppinglistcompare.Common.Common;
import com.kavsoftware.kaveer.shoppinglistcompare.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListNameFragment extends Fragment {
    FloatingActionButton addListFloatControl;

    Common common = new Common();

    public ListNameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("History");

        View view = inflater.inflate(R.layout.fragment_list_name, container, false);

        InitializeControl(view);

        addListFloatControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListNameAddFragment fragment = new ListNameAddFragment();
                android.support.v4.app.FragmentTransaction fmTransaction = getFragmentManager().beginTransaction();
                fmTransaction.replace(R.id.MainFrameLayout, fragment);
                fmTransaction.commit();
            }
        });


        return view;
    }

    private void InitializeControl(View view) {
        addListFloatControl = (FloatingActionButton) view.findViewById(R.id.fab);

    }

}
