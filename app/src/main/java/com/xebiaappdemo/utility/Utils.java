package com.xebiaappdemo.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2/14/2018.
 */

public class Utils {
    public static boolean isInternetConnected(Context act) {

        ConnectivityManager connectivityManager = (ConnectivityManager) act
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
