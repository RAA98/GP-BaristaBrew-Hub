package my.insta.androrealm;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class IBarstaOffline extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
