package com.kavsoftware.kaveer.shoppinglistcompare.Helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by kaveer on 11/22/2017.
 */

public class Common {

    public  boolean isNetworkConnected(FragmentActivity activity, Context context){
        try {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) activity.getSystemService(context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            return (mNetworkInfo == null) ? false : true;

        }catch (NullPointerException e){
            return false;

        }
    }

    public void DisplayToastFromFragmentLong(FragmentActivity activity, String message){
        Toast messageBox = Toast.makeText(activity , message , Toast.LENGTH_LONG);
        messageBox.show();
    }


    public void DisplayToastFromFragmentShort(FragmentActivity activity, String message){
        Toast messageBox = Toast.makeText(activity , message , Toast.LENGTH_SHORT);
        messageBox.show();
    }

    public void DisplaySnack(View view, String message, String action){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction(action, null).show();
    }

    public String GetDateNow() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); //import java.text.SimpleDateFormat instead of android.icu.text.simpleDateFormat
        String currentDate = dateFormat.format(calendar.getTime());
        return currentDate;
    }

    public Bundle SetBundle(String key, String value){
        Bundle bundle = new Bundle();
        String myMessage = value;
        bundle.putString(key, myMessage );

        return bundle;
    }

    public String GetBundle(Bundle arguments, int bundleGetTitle) {
        String result;

        Bundle bundle = arguments;
        result = bundle.getString(String.valueOf(bundleGetTitle));

        return result;
    }

}
