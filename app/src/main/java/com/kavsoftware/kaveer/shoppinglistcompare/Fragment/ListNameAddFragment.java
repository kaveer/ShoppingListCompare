package com.kavsoftware.kaveer.shoppinglistcompare.Fragment;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kavsoftware.kaveer.shoppinglistcompare.Common.Common;
import com.kavsoftware.kaveer.shoppinglistcompare.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListNameAddFragment extends Fragment {

    TextInputEditText title, date;
    Button next, cancel;

    Common common = new Common();

    public ListNameAddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("New List");
        View view = inflater.inflate(R.layout.fragment_list_name_add, container, false);

        try{

            if(InitializeTextView(view)){
                date.setText(common.GetDateNow());
            }

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(IsValid()){
                        AddSuperMarkerFragment fragment = new AddSuperMarkerFragment();
                        fragment.setArguments(common.SetBundle(String.valueOf(R.string.bundleGetTitle), title.getText().toString()));
                        android.support.v4.app.FragmentTransaction fmTransaction = getFragmentManager().beginTransaction();
                        fmTransaction.replace(R.id.MainFrameLayout, fragment);
                        fmTransaction.commit();
                    }

                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ListNameFragment fragment = new ListNameFragment();
                    android.support.v4.app.FragmentTransaction fmTransaction = getFragmentManager().beginTransaction();
                    fmTransaction.replace(R.id.MainFrameLayout, fragment);
                    fmTransaction.commit();
                }
            });

        }catch (Exception ex){
            Log.e("Error", ex.getMessage());
        }

        return view;
    }

    private boolean IsValid() {
        boolean result = true;

        if (title.getText().toString().length() == 0){
            title.setError("Enter title to continue");
            return  false;
        }

        return result;
    }

    private boolean InitializeTextView(View view) {
        try {
            date = view.findViewById(R.id.textInputDate);
            title = view.findViewById(R.id.textInputTitle);
            next = view.findViewById(R.id.addTitleOkButton);
            cancel = view.findViewById(R.id.addTitleCancelButton);

            return true;
        }catch (Exception ex){
//            display error redirect to main
            return false;
        }


    }

}
