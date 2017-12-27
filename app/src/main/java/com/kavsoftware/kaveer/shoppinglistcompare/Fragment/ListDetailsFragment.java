package com.kavsoftware.kaveer.shoppinglistcompare.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.kavsoftware.kaveer.shoppinglistcompare.DB.DbHandler.DBHandler;
import com.kavsoftware.kaveer.shoppinglistcompare.Helper.ExpandableListViewAdapter;
import com.kavsoftware.kaveer.shoppinglistcompare.Model.ExpandableDetailsViewModel;
import com.kavsoftware.kaveer.shoppinglistcompare.Model.GroceryDetailsViewModel;
import com.kavsoftware.kaveer.shoppinglistcompare.Model.ListViewModel;
import com.kavsoftware.kaveer.shoppinglistcompare.Model.MasterSupermarketViewModel;
import com.kavsoftware.kaveer.shoppinglistcompare.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListDetailsFragment extends Fragment {
    private ExpandableListView expandableListView;
    private List<String> parentHeaderInformation;
    ArrayList<MasterSupermarketViewModel> stores = new ArrayList<>();
    ListViewModel listDetails = new ListViewModel();

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
            listDetails= (ListViewModel) bundle.getSerializable(String.valueOf(R.string.bundleListDetails));

            InitializeControl(view);

            if (listDetails.isViewOnly()){
                getActivity().setTitle(listDetails.getListName());

                DisplayExpandableListView();

            }else {
                listDetails.setUuid(GenerateUuid());
                listDetails.setListId(SaveListName(listDetails));
               // SaveListDetails(listDetails);
                SetListIdForListDetails(listDetails.getGroceryDetail(), listDetails.getListId());
                SaveGroceryDetails(listDetails);

                DisplayExpandableListView();
            }

        }catch (Exception ex){
            Log.e("error", ex.getMessage());
        }

        return view;
    }

    private void DisplayExpandableListView() {
        stores = GetStoreNameByListId();
       // GenerateExpandableParentText(stores);
        HashMap<String, List<String>> allChildItems = GroupedChildItems();
        ExpandableListViewAdapter expandableListViewAdapter = new ExpandableListViewAdapter(getContext(), parentHeaderInformation, allChildItems);
        expandableListView.setAdapter(expandableListViewAdapter);
    }

    private void GenerateExpandableParentText(ArrayList<MasterSupermarketViewModel> stores) {
        parentHeaderInformation = new ArrayList<>();
        ArrayList<GroceryDetailsViewModel> groceryDetails = GetGroceryDetailsByListId(listDetails.getListId());

        for (MasterSupermarketViewModel item:stores) {
            parentHeaderInformation.add(item.getStoreName());

            for (GroceryDetailsViewModel items:groceryDetails) {
                if (item.getStoreId() == items.getStoreId()){

                }
            }
        }
    }

    private ArrayList<GroceryDetailsViewModel> GetGroceryDetailsByListId(int listId) {
        ArrayList<GroceryDetailsViewModel> result = new ArrayList<>();

        DBHandler db = new DBHandler(getContext());
        result = db.GetGroceryDetailsByListId(listId);

        return result;
    }

    private ArrayList<MasterSupermarketViewModel> GetStoreNameByListId() {
        ArrayList<MasterSupermarketViewModel> result = new ArrayList<>();

        DBHandler db = new DBHandler(getContext());
        result = db.GetStoreDetailsByListId(listDetails);

        return result;
    }

    private void InitializeControl(View view) {
        expandableListView = view.findViewById(R.id.expandableListView);
    }

    private HashMap<String, List<String>> GroupedChildItems(){
        HashMap<String, List<String>> childContent = new HashMap<String, List<String>>();

        parentHeaderInformation = new ArrayList<>();
        ArrayList<GroceryDetailsViewModel> groceryDetails = GetGroceryDetailsByListId(listDetails.getListId());
        List<String> storeItem = new ArrayList<>();

        ArrayList<GroceryDetailsViewModel> groceryDetailsFinal = new ArrayList<>();
        ArrayList<ExpandableDetailsViewModel> expandableStoreDetails = new ArrayList<>();

        SetParentHeader(expandableStoreDetails);
        for (MasterSupermarketViewModel store:stores) {

            for (GroceryDetailsViewModel groceryGroupByStoreId: groceryDetails) {
                if (groceryGroupByStoreId.getStoreId() == store.getStoreId()){

                    for (GroceryDetailsViewModel groceryGroupByGroceryId:groceryDetails) {
                        if (groceryGroupByStoreId.getGroceryId() == groceryGroupByGroceryId.getGroceryId()) {

                            groceryDetailsFinal.add(groceryGroupByGroceryId);
                        }
                    }


                    GroceryDetailsViewModel sortedGroceryList = GetMinimumPrice(groceryDetailsFinal);
                    PopulateExpandableListView(sortedGroceryList, expandableStoreDetails, storeItem, childContent);

                    groceryDetailsFinal = new ArrayList<>();
                }
            }

            storeItem = new ArrayList<>();
        }

        ValidateParentHeader(expandableStoreDetails, childContent);

//        List<String> cars = new ArrayList<>();
//        cars.add("Volvo");
//        cars.add("BMW");
//        cars.add("Toyota");
//        cars.add("Nissan");
//
//        List<String> houses = new ArrayList<String>();
//        houses.add("Duplex");
//        houses.add("Twin Duplex");
//        houses.add("Bungalow");
//        houses.add("Two Storey");
//
//        List<String> footballClubs = new ArrayList<String>();
//        footballClubs.add("Liverpool");
//        footballClubs.add("Arsenal");
//        footballClubs.add("Stoke City");
//        footballClubs.add("West Ham");
//
//        childContent.put(parentHeaderInformation.get(0), cars);
//        childContent.put(parentHeaderInformation.get(1), houses);

       // childContent.put(parentHeaderInformation.get(2), footballClubs);

        return childContent;

    }

    private void ValidateParentHeader(ArrayList<ExpandableDetailsViewModel> expandableStoreDetails, HashMap<String, List<String>> childContent) {
        List<String> storeItem = new ArrayList<>();

        for (ExpandableDetailsViewModel item:expandableStoreDetails) {
            if (!childContent.containsKey(parentHeaderInformation.get(item.getCounter()))){
                storeItem.add("No item in this store");
                childContent.put(parentHeaderInformation.get(item.getCounter()), storeItem);
            }
        }
    }

    private void SetParentHeader(ArrayList<ExpandableDetailsViewModel> expandableStoreDetails) {
        int counter = 0;

        for (MasterSupermarketViewModel store:stores) {
            ExpandableDetailsViewModel storeDetails = new ExpandableDetailsViewModel();
            parentHeaderInformation.add(store.getStoreName());
            storeDetails.setStoreId(store.getStoreId());
            storeDetails.setCounter(counter);
            expandableStoreDetails.add(storeDetails);

            counter ++;
        }
    }

    private void PopulateExpandableListView(GroceryDetailsViewModel sortedGroceryList, ArrayList<ExpandableDetailsViewModel> expandableStoreDetails, List<String> storeItem, HashMap<String, List<String>> childContent) {

        for (ExpandableDetailsViewModel item:expandableStoreDetails) {
            if (item.getStoreId() == sortedGroceryList.getStoreId()){
                DBHandler db = new DBHandler(getContext());
                storeItem.add(String.valueOf(db.GetGroceryNameByGroceryId(sortedGroceryList.getGroceryId()) + " at Rs. " + sortedGroceryList.getPrice()));
                childContent.put(parentHeaderInformation.get(item.getCounter()), storeItem);

            }
        }
    }

    private GroceryDetailsViewModel GetMinimumPrice(ArrayList<GroceryDetailsViewModel> groceryDetailsFinal) {
        GroceryDetailsViewModel result = new GroceryDetailsViewModel();
        ArrayList<Float> price = new ArrayList<>();

        for (GroceryDetailsViewModel item:groceryDetailsFinal) {
            price.add(item.getPrice());
        }
        float minimumPrice = Collections.min(price);

        for (GroceryDetailsViewModel item:groceryDetailsFinal) {
           if (item.getPrice() == minimumPrice){
               result = item;
           }
        }

        return result;
    }

    private void SetListIdForListDetails(ArrayList<GroceryDetailsViewModel> groceryDetail, int listId) {
        for (GroceryDetailsViewModel item:groceryDetail) {
            item.setListId(listId);
        }
    }

    private void SaveGroceryDetails(ListViewModel listDetails) {
        for (GroceryDetailsViewModel item:listDetails.getGroceryDetail()) {
            DBHandler db = new DBHandler(getContext());
            db.PostGroceryDetails(item);
        }

    }

//    private void SaveListDetails(ListViewModel listDetails) {
//        for (MasterSupermarketViewModel item:listDetails.getStores()) {
//            DBHandler db = new DBHandler(getContext());
//            db.PostListDetails(listDetails.getListId(), item.getStoreId());
//        }
//
//    }

    private int SaveListName(ListViewModel listDetails) {
        int result = 0;
        DBHandler db = new DBHandler(getContext());
        result = db.PostList(listDetails);

        return result;
    }

    private String GenerateUuid() {
        String result;

        UUID uuid = UUID.randomUUID();
        result = uuid.toString();

        return result;
    }

}
