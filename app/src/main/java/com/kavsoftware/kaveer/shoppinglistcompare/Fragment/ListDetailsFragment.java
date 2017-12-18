package com.kavsoftware.kaveer.shoppinglistcompare.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kavsoftware.kaveer.shoppinglistcompare.Model.ListViewModel;
import com.kavsoftware.kaveer.shoppinglistcompare.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListDetailsFragment extends Fragment {


    public ListDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Details");
        View view = inflater.inflate(R.layout.fragment_list_details, container, false);

        try{

            Bundle bundle = getArguments();
            ListViewModel listDetails= (ListViewModel) bundle.getSerializable(String.valueOf(R.string.bundleListDetails));

            if (listDetails.isViewOnly()){
                //view here
            }else {
                //save here
                //save list
                //get listId by name, date and guid
                //save list id and store id in associative table

                // save storeid and groceryid in associa table

                // display in listview

            }

        }catch (Exception ex){
            Log.e("error", ex.getMessage());
        }

        return view;
    }

}
