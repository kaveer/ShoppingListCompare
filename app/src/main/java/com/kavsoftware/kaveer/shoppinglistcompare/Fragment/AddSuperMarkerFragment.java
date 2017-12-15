package com.kavsoftware.kaveer.shoppinglistcompare.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kavsoftware.kaveer.shoppinglistcompare.Common.Common;
import com.kavsoftware.kaveer.shoppinglistcompare.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddSuperMarkerFragment extends Fragment {
    String listTitle;
    Common common = new Common();

    public AddSuperMarkerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Supermarket");

        View view = inflater.inflate(R.layout.fragment_add_super_marker, container, false);

        listTitle = common.GetBundle(this.getArguments(), R.string.bundleGetTitle);

        if (listTitle == ""){
            ListNameFragment fragment = new ListNameFragment();
            android.support.v4.app.FragmentTransaction fmTransaction = getFragmentManager().beginTransaction();
            fmTransaction.replace(R.id.MainFrameLayout, fragment);
            fmTransaction.commit();
            common.DisplayToastFromFragment(getActivity(), "Error please try again");
        }


        return view;
    }



}
