package app;

/**
 * Created by Atiar on 4/16/18.
 */

import android.app.Application;
import android.text.TextUtils;

import com.google.firebase.FirebaseApp;


public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();


    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        FirebaseApp.initializeApp(this);
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

}