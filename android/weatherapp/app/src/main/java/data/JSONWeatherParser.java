package data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Utils.Utils;
import model.Place;
import model.Weather;

/**
 * Created by jowang on 16/7/27.
 */
public class JSONWeatherParser {
    public static Weather getWeather(String data){
        Weather weather=new Weather();
        //create json object
        try {
            JSONObject jsonObject=new JSONObject(data);
            Place place=new Place();
            //get coord
            JSONObject coordObj=Utils.getObject("coord",jsonObject);
            place.setLat(Utils.getFloat("lat",coordObj));
            place.setLon(Utils.getFloat("lat",coordObj));
            //get sys obj
            JSONObject sysobj=Utils.getObject("sys",jsonObject);
            place.setCountry(Utils.getString("country",sysobj));
            place.setLastupdate(Utils.getInt("dt",jsonObject));
            place.setSunrise(Utils.getInt("sunrise",sysobj));
            place.setSunset(Utils.getInt("sunset",sysobj));
            place.setCity(Utils.getString("name",jsonObject));
            weather.place=place;
            //get the weather info
            JSONArray jsonArray=jsonObject.getJSONArray("weather");
            JSONObject jsonWeather=jsonArray.getJSONObject(0);
            weather.currentCondition.setWeatherId(Utils.getInt("id",jsonWeather));
            weather.currentCondition.setDescription(Utils.getString("description",jsonWeather));
            weather.currentCondition.setIcon(Utils.getString("icon",jsonWeather));
            weather.currentCondition.setCondition(Utils.getString("main",jsonWeather));
            JSONObject mainObj=Utils.getObject("main",jsonObject);
            weather.currentCondition.setHumidity(Utils.getInt("humidity",mainObj));
            weather.currentCondition.setPressure(Utils.getInt("pressure",mainObj));
            weather.currentCondition.setMinTemp(Utils.getFloat("temp_min",mainObj));
            weather.currentCondition.setMaxTemp(Utils.getFloat("temp_max",mainObj));
            weather.currentCondition.setTemperature(Utils.getDouble("temp",mainObj));
            //get wind
            JSONObject windObj=Utils.getObject("wind",jsonObject);
            weather.wind.setSpeed(Utils.getFloat("speed",windObj));
            weather.wind.setDeg(Utils.getFloat("deg",windObj));
            //get clouds
            JSONObject cloudObj=Utils.getObject("clouds",jsonObject);
            weather.clouds.setPrecipitation(Utils.getInt("all",cloudObj));

            return weather;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}





















