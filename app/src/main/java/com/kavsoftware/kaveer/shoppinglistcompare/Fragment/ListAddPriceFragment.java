package com.kavsoftware.kaveer.shoppinglistcompare.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.kavsoftware.kaveer.shoppinglistcompare.DB.DbHandler.DBHandler;
import com.kavsoftware.kaveer.shoppinglistcompare.Helper.Common;
import com.kavsoftware.kaveer.shoppinglistcompare.Model.ListViewModel;
import com.kavsoftware.kaveer.shoppinglistcompare.Model.MasterGroceryViewModel;
import com.kavsoftware.kaveer.shoppinglistcompare.Model.MasterSupermarketViewModel;
import com.kavsoftware.kaveer.shoppinglistcompare.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListAddPriceFragment extends Fragment {

    Common common = new Common();

    Spinner selectStore;
    Button add, cancel, save;
    TextInputEditText addPrice;
    AutoCompleteTextView addGroceryAutoComplete;

    ArrayList<String> storesAdapter = new ArrayList<>();
    ArrayList<MasterGroceryViewModel> groceries = new ArrayList<>();
    ArrayList<MasterGroceryViewModel> preAddGroceryList = new ArrayList<>();
    boolean action;

    public ListAddPriceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        getActivity().setTitle("Price");

        View view = inflater.inflate(R.layout.fragment_list_add_price, container, false);

        try {
            Bundle bundle = getArguments();
            final ListViewModel listDetails = (ListViewModel) bundle.getSerializable(String.valueOf(R.string.bundleShopListViewModel));

            if (listDetails.getListName() != "" && listDetails.getStores().size() >= 2){
                getActivity().setTitle(listDetails.getListName());
                InitializeControl(view);
                groceries = LoadMasterGrocery();
                PopulateAutoComplete(groceries, getActivity());
                PopulateSpinner(listDetails.getStores(), getActivity());

            }
            else {
                common.DisplayToastFromFragmentLong(getActivity(), "Error please try again");

                ListNameFragment fragment = new ListNameFragment();
                android.support.v4.app.FragmentTransaction fmTransaction = getFragmentManager().beginTransaction();
                fmTransaction.replace(R.id.MainFrameLayout, fragment);
                fmTransaction.commit();
            }

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (IsValid()){
                        if (IsExistInMasterTable(addGroceryAutoComplete.getText().toString(), groceries))
                        {
                            action = true;
                            MasterGroceryViewModel grocery = new MasterGroceryViewModel();

                            int groceryId = GetIdByGroceryName(addGroceryAutoComplete.getText().toString(), groceries, action);
                            int storeId = GetIdByStoreName(listDetails.getStores(), selectStore.getSelectedItem().toString());

                            SetGrocery(grocery ,groceryId, storeId, Float.valueOf(addPrice.getText().toString()), addGroceryAutoComplete.getText().toString());

                            if (!IsExistInPreAddGroceryList(preAddGroceryList, grocery)){
                                preAddGroceryList.add(grocery);
                                common.DisplayToastFromFragmentShort(getActivity(), "Item added");
                            }
                            else {
                                common.DisplayToastFromFragmentLong(getActivity(), "Item already added in the selected store");
                            }

                        }
                        else {
                            action = false;
                            MasterGroceryViewModel grocery = new MasterGroceryViewModel();

                            int groceryId = GetIdByGroceryName(addGroceryAutoComplete.getText().toString(), groceries, action);
                            int storeId = GetIdByStoreName(listDetails.getStores(), selectStore.getSelectedItem().toString());

                            SetGrocery(grocery ,groceryId, storeId, Float.valueOf(addPrice.getText().toString()), addGroceryAutoComplete.getText().toString());

                            if (!IsExistInPreAddGroceryList(preAddGroceryList, grocery)){
                                preAddGroceryList.add(grocery);
                                common.DisplayToastFromFragmentShort(getActivity(), "Item added");
                            }
                            else {
                                common.DisplayToastFromFragmentLong(getActivity(), "Item already added in the selected store");
                            }
                        }
                    }

                    groceries = new ArrayList<>();
                    groceries = LoadMasterGrocery();
                    PopulateAutoComplete(groceries, getActivity());
                }
            });

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listDetails.getGroceryList().size() != 0){
                        Bundle bundle = new Bundle();
                        listDetails.setGroceryList(preAddGroceryList);
                        listDetails.setViewOnly(false);
                        bundle.putSerializable(String.valueOf(R.string.bundleListDetails), listDetails);

                        ListDetailsFragment fragment = new ListDetailsFragment();
                        fragment.setArguments(bundle);
                        android.support.v4.app.FragmentTransaction fmTransaction = getFragmentManager().beginTransaction();
                        fmTransaction.replace(R.id.MainFrameLayout, fragment);
                        fmTransaction.commit();
                    }else {
                        common.DisplayToastFromFragmentLong(getActivity(), "At least one item required");
                    }
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ListNameFragment fragment = new ListNameFragment();
                    android.support.v4.app.FragmentTransaction fmTransaction = getFragmentManager().beginTransaction();
                    fmTransaction.replace(R.id.MainFrameLayout, fragment);
                    fmTransaction.commit();
                }
            });




        }catch (Exception ex){
            Log.e("error", ex.getMessage());
        }

        return view;
    }

    private void SetGrocery(MasterGroceryViewModel grocery, int groceryId, int storeId, Float price, String groceryName) {
        grocery.setGroceryId(groceryId);
        grocery.setStoreId(storeId);
        grocery.setPrice(price);
        grocery.setGroceryName(groceryName);

    }

    private boolean IsExistInPreAddGroceryList(ArrayList<MasterGroceryViewModel> preAddGroceryList, MasterGroceryViewModel grocery) {
        boolean result = false;

        for (MasterGroceryViewModel item: preAddGroceryList) {
            if (item.getGroceryName().equals(grocery.getGroceryName())){
                if(item.getStoreId() == grocery.getStoreId())
                    return true;
            }
        }

        return result;
    }

    private int GetIdByStoreName(ArrayList<MasterSupermarketViewModel> stores, String store) {
        int result = 0;

        for (MasterSupermarketViewModel item:stores) {
            if (item.getStoreName().equals(store)){
                result = item.getStoreId();
                return result;
            }
        }

        return result;
    }

    private int GetIdByGroceryName(String groceryName, ArrayList<MasterGroceryViewModel> groceries, boolean action) {
        int result = 0;

        if (action == true){
            for (MasterGroceryViewModel item:groceries) {
                if (item.getGroceryName().equals(groceryName))
                    result = item.getGroceryId();
            }
        }

        if (action == false){
            DBHandler db = new DBHandler(getContext());
            result = db.PostMasterGrocery(groceryName);
        }

        return result;

    }

    private boolean IsExistInMasterTable(String grocery, ArrayList<MasterGroceryViewModel> groceries) {
        boolean result = false;

        for (MasterGroceryViewModel item: groceries) {
            if(item.getGroceryName().equals(grocery)){
                return true;
            }
        }

        return result;
    }

    private void PopulateAutoComplete(ArrayList<MasterGroceryViewModel> groceries, FragmentActivity activity) {
        ArrayList<String> result = new ArrayList<>();
        for (MasterGroceryViewModel item:groceries) {
            result.add(item.getGroceryName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, result);
        addGroceryAutoComplete.setAdapter(adapter);
    }

    private ArrayList<MasterGroceryViewModel> LoadMasterGrocery() {
        ArrayList<MasterGroceryViewModel> result;

        DBHandler db = new DBHandler(getContext());
        result =  db.GetMasterGrocery();

        return  result;
    }

    private boolean IsValid() {
        boolean result = true;

        if (addGroceryAutoComplete.getText().toString().length() == 0){
            addGroceryAutoComplete.setError("Add grocery item");
            return false;
        }

        if (addPrice.getText().toString().length() == 0){
            addPrice.setError("Enter price");
            return false;
        }

        return result;
    }

    private void InitializeControl(View view) {
        selectStore = view.findViewById(R.id.spinnerStore);
        add = view.findViewById(R.id.addPriceAdd);
        addPrice = view.findViewById(R.id.addPrice);
        addGroceryAutoComplete = view.findViewById(R.id.addGrocery);
        cancel = view.findViewById(R.id.addPricePreviousButton);
        save = view.findViewById(R.id.addPriceSaveButton);
    }

    private void PopulateSpinner(ArrayList<MasterSupermarketViewModel> stores, FragmentActivity activity) {
        for (MasterSupermarketViewModel item: stores) {
            storesAdapter.add(item.getStoreName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item ,storesAdapter){

            @Override
            public View getView(int position, View convertView, ViewGroup parent){

                View view = super.getView(position, convertView, parent);

                TextView ListItemShow = view.findViewById(android.R.id.text1);

                ListItemShow.setTextColor(Color.parseColor("#ffffff"));

                return view;
            }

        };
        selectStore.setAdapter(adapter);
    }

}
