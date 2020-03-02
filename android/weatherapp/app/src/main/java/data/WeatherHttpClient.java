package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import Utils.Utils;
import model.Snow;

/**
 * Created by jowang on 16/7/27.
 */
public class WeatherHttpClient {
    public String getWeatherData(String place){
        try {
            String API="&APPID=3eaa6a4aed0f86a1ce1f8713f72ca73f";
            StringBuffer stringBuffer=new StringBuffer();
            InputStream inputStream=new URL(Utils.BASE_URL+place+API).openStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String line=null;
            while ((line=bufferedReader.readLine())!=null){
                stringBuffer.append(line+"\r\n");
            }
            inputStream.close();
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

















