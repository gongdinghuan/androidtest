package data;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by jowang on 16/7/28.
 */
public class CityPreference {
    SharedPreferences prefs;
    public CityPreference(Activity activity){
        prefs=activity.getPreferences(Activity.MODE_PRIVATE);
    }
    public String getCity(){
        return prefs.getString("city","mianyang");
    }
    public void setCity(String city){
        prefs.edit().putString("city",city).commit();
    }
    public void onDestroy(){
        prefs.edit().clear().commit();

    }

}
