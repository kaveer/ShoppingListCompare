package com.kavsoftware.kaveer.shoppinglistcompare.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.kavsoftware.kaveer.shoppinglistcompare.DB.DbHandler.DBHandler;
import com.kavsoftware.kaveer.shoppinglistcompare.Helper.Common;
import com.kavsoftware.kaveer.shoppinglistcompare.Model.MasterSupermarketViewModel;
import com.kavsoftware.kaveer.shoppinglistcompare.Model.ShoppingListViewModel;
import com.kavsoftware.kaveer.shoppinglistcompare.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddSuperMarkerFragment extends Fragment {
    ArrayList<MasterSupermarketViewModel> masterSupermarkets = new ArrayList<>();
    ArrayList<String> supermarkets = new ArrayList<>();
    ShoppingListViewModel listDetails = new ShoppingListViewModel();
    ArrayAdapter<String> adapter;

    Common common = new Common();

    AutoCompleteTextView autoSupermarket;
    Button addStore;
    ListView listViewStore;

    int tutorialCounter = 0;

    public AddSuperMarkerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Supermarket");
        View view = inflater.inflate(R.layout.fragment_add_super_marker, container, false);

        try{

            Bundle bundle = getArguments();
            listDetails = (ShoppingListViewModel) bundle.getSerializable(String.valueOf(R.string.bundleGetTitle));

            if (listDetails.getListName() == ""){
                ReturnToDefaultFragment();
                common.DisplayToastFromFragment(getActivity(), "Error please try again");
            }else {
                InitializeControl(view);

//                DBHandler db = new DBHandler(getContext());
//                db.PostMasterSupermarket();

                masterSupermarkets = GetMasterSuperMarketForAutoComplete();
                SetValueForAutoComplete(masterSupermarkets, getActivity());

                addStore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(IsValid()){
                            if (!supermarkets.contains(autoSupermarket.getText().toString())){

                                supermarkets.add(autoSupermarket.getText().toString());
                                DisplayTutorial(getActivity());
                                GenerateListView();

                                autoSupermarket.setText("");

                            }else {
                                common.DisplayToastFromFragment(getActivity(), "Supermarket already selected");
                            }
                        }
                    }
                });

            }

        }catch (Exception ex){
            Log.e("Error", "Error try again");
            ReturnToDefaultFragment();
        }



        return view;
    }

    private void DisplayTutorial(FragmentActivity activity) {

        if (tutorialCounter == 0){
            common.DisplayToastFromFragment(activity, "Long press item for option");
            tutorialCounter = 1;
        }
    }

    private void GenerateListView() {
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, supermarkets){

            @Override
            public View getView(int position, View convertView, ViewGroup parent){

                View view = super.getView(position, convertView, parent);

                TextView ListItemShow = view.findViewById(android.R.id.text1);

                ListItemShow.setTextColor(Color.parseColor("#ffffff"));

                return view;
            }

        };
        listViewStore.setAdapter(adapter);
        registerForContextMenu(listViewStore);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select Option");
        menu.add(0, v.getId(), 0, "Delete");
//        menu.add(0,v.getId(),0,"Message");
//        menu.add(0,v.getId(),0,"Mail");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int listPosition = info.position;


       Object t = adapter.getItem(listPosition);
       Object s = adapter.getItemId(listPosition);

       adapter.remove((String) t);

        GenerateListView();

        Object z = supermarkets;

        if(item.getTitle()=="Delete"){
            //Toast.makeText(getContext(),"Call:Selected",Toast.LENGTH_LONG).show();
           // Toast.makeText(getContext(),String.valueOf(info),Toast.LENGTH_LONG).show();


        }
//        else if (item.getTitle()=="Message"){
//            Toast.makeText(getContext(),"Message:Selected",Toast.LENGTH_LONG).show();
//        }
//        else if(item.getTitle()=="Mail"){
//            Toast.makeText(getContext(),"Mail:Selected",Toast.LENGTH_LONG).show();
//        }
        return super.onContextItemSelected(item);
    }

    private boolean IsValid() {
        boolean result = true;

        if(autoSupermarket.getText().toString().length() == 0){
            autoSupermarket.setError("Enter store name");
            return false;
        }

        return result;
    }

    private void SetValueForAutoComplete(ArrayList<MasterSupermarketViewModel> masterSupermarkets, FragmentActivity activity) {
        ArrayList<String> result = new ArrayList<>();
        for (MasterSupermarketViewModel item:masterSupermarkets) {
            result.add(item.getStoreName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, result);
        autoSupermarket.setAdapter(adapter);
    }

    private void InitializeControl(View view) {
        autoSupermarket = view.findViewById(R.id.autoSupermarket);
        addStore = view.findViewById(R.id.BtnAddStore);
        listViewStore = view.findViewById(R.id.listViewStore);

    }

    private ArrayList<MasterSupermarketViewModel> GetMasterSuperMarketForAutoComplete() {
        ArrayList<MasterSupermarketViewModel> result;

        DBHandler db = new DBHandler(getContext());
        result =  db.GetMasterSupermarket();

        return result;

    }

    private void ReturnToDefaultFragment() {
        ListNameFragment fragment = new ListNameFragment();
        android.support.v4.app.FragmentTransaction fmTransaction = getFragmentManager().beginTransaction();
        fmTransaction.replace(R.id.MainFrameLayout, fragment);
        fmTransaction.commit();
    }


}
