package uz.gita.puzzle15.app;

import android.app.Application;

import uz.gita.puzzle15.utils.MySharedPref;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MySharedPref.init(this);
    }
}
