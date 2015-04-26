package edu.newpaltz.surveywild;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.util.ArrayList;

public class MyApplication extends Application {

    public static Context mContext; // to get the application context
    public static String mImagePath;
    public static ArrayList<Species> mListSpecies;
    public static ArrayList<String> mListCName;
    public static ArrayList<SpKeyword> mListKeywords;

    public void onCreate(){
        super.onCreate();
        MyApplication.mContext = getApplicationContext();
        MyApplication.mImagePath = Environment.getExternalStorageDirectory() + "/";
    }

    public static Context getAppContext() {
        return MyApplication.mContext;
    }
}
