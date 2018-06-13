package gifood.id.katalogfilm.util;

import android.app.Application;
import android.content.Context;

public class KatalogApp extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();

        KatalogApp.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return KatalogApp.context;
    }
}
