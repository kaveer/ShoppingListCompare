package com.kavsoftware.kaveer.shoppinglistcompare.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kavsoftware.kaveer.shoppinglistcompare.DB.DbHandler.DBHandler;
import com.kavsoftware.kaveer.shoppinglistcompare.Helper.Common;
import com.kavsoftware.kaveer.shoppinglistcompare.Model.ListViewModel;
import com.kavsoftware.kaveer.shoppinglistcompare.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListNameFragment extends Fragment {
    FloatingActionButton addListFloatControl;
    ListView listViewList;

    Common common = new Common();

    ArrayList<ListViewModel> list;
    ArrayAdapter<String> adapter;
    ArrayList<String> listAdapter = new ArrayList<>();

    public ListNameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("History");

        View view = inflater.inflate(R.layout.fragment_list_name, container, false);

//        DBHandler db = new DBHandler(getContext());
//        db.GetGroceryDetailsByListId(listId);

        try{
            InitializeControl(view);

            listAdapter = GetList();
            GenerateListView();

            addListFloatControl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ListNameAddFragment fragment = new ListNameAddFragment();
                    android.support.v4.app.FragmentTransaction fmTransaction = getFragmentManager().beginTransaction();
                    fmTransaction.replace(R.id.MainFrameLayout, fragment);
                    fmTransaction.commit();
                }
            });

        }catch (Exception ex){
            String fileName = common.GetDateNow() + ".txt";
            common.SaveFileToInternalStorage(getActivity(), fileName , ex.getMessage() ,true);
        }

        return view;
    }

    private ArrayList<String> GetList() {
        ArrayList<String> result = new ArrayList<>();

        DBHandler db = new DBHandler(getContext());
        list = db.GetList();

        if (list.size() > 0){
            for (ListViewModel item:list) {
                result.add(item.getDate() + " " + item.getListName());
            }
        }else {
            result.add("No list available");
        }

        return result;
    }

    private void GenerateListView() {
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, listAdapter){

            @Override
            public View getView(int position, View convertView, ViewGroup parent){

                View view = super.getView(position, convertView, parent);

                TextView ListItemShow = view.findViewById(android.R.id.text1);

                ListItemShow.setTextColor(Color.parseColor("#ffffff"));

                return view;
            }

        };
        listViewList.setAdapter(adapter);
        if (!listAdapter.contains("No list available")){
            registerForContextMenu(listViewList);
        }


    }

    private void InitializeControl(View view) {
        addListFloatControl = view.findViewById(R.id.fab);
        listViewList = view.findViewById(R.id.listViewList);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select Option");
        menu.add(0, v.getId(), 0, "View");
        menu.add(0, v.getId(), 0, "Delete");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ListViewModel listDetails = new ListViewModel();

        int index = GetItemIndex(item);
        String listNameWithDate = GetListByIndex(index);
        SplitListName(listDetails, listNameWithDate);

        if(item.getTitle()=="Delete"){

            listDetails.setListId(GetListIdByListDetails(listDetails));
            DeleteByListId(listDetails.getListId());

            listAdapter = GetList();
            GenerateListView();
        }

        if(item.getTitle()=="View"){
            listDetails.setViewOnly(true);
            listDetails.setListId(GetListIdByListDetails(listDetails));

            Bundle bundle = new Bundle();
            bundle.putSerializable(String.valueOf(R.string.bundleListDetails), listDetails);

            ListDetailsFragment fragment = new ListDetailsFragment();
            fragment.setArguments(bundle);
            android.support.v4.app.FragmentTransaction fmTransaction = getFragmentManager().beginTransaction();
            fmTransaction.replace(R.id.MainFrameLayout, fragment);
            fmTransaction.commit();
        }

        return super.onContextItemSelected(item);
    }

    private void DeleteByListId(int listId) {
        DBHandler db = new DBHandler(getContext());
        db.DeleteByListId(listId);
    }

    private int GetListIdByListDetails(ListViewModel listDetails) {
        int result = 0;

        DBHandler db = new DBHandler(getContext());
        result = db.GetIdByListDetails(listDetails);

        return result;
    }

    private void SplitListName(ListViewModel listDetails, String listNameWithDate) {
        String[] splitArray = listNameWithDate.split("\\s+");
        String date = "";
        String name = "";
        int counter = 0;
        
        for (String item:splitArray) {
           counter ++;
           
           if (counter == 1){
               date = item;
           }else {
               if (name.isEmpty()){
                   name = item;
               }else {
                   name = name + " " + item;
               }
           }
        }
        
        listDetails.setDate(date);
        listDetails.setListName(name);


    }

    private String GetListByIndex(int index) {
        String result;
        result = adapter.getItem(index);

        return result;
    }

    private int GetItemIndex(MenuItem item) {
        int result = 0;
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        result = info.position;

        return  result;
    }

}
