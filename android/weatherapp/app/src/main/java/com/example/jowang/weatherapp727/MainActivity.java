package com.example.jowang.weatherapp727;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

import Utils.Utils;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import data.CityPreference;
import data.JSONWeatherParser;
import data.WeatherHttpClient;
import model.Weather;

public class MainActivity extends AppCompatActivity {
    private TextView cityName;
    private TextView temp;
    private ImageView iconView;
    private TextView description;
    private TextView humidity;
    private TextView pressure;
    private TextView wind;
    private TextView sunrise;
    private TextView sunset;
    private TextView updated;
    Weather weather=new Weather();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName=(TextView)findViewById(R.id.cityText);
        iconView=(ImageView) findViewById(R.id.thumbnailIcon);
        temp=(TextView)findViewById(R.id.tempText);
        description=(TextView)findViewById(R.id.cloudText);
        humidity=(TextView)findViewById(R.id.humidText);
        pressure=(TextView)findViewById(R.id.pressureText);
        wind=(TextView)findViewById(R.id.windText);
        sunrise=(TextView)findViewById(R.id.riseText);
        sunset=(TextView)findViewById(R.id.sunsetText);
        updated=(TextView)findViewById(R.id.updateText);
        CityPreference cityPreference=new CityPreference(MainActivity.this);
        renderWeatherData(cityPreference.getCity());

    }
    public void renderWeatherData(String city){
        WeatherTask weatherTask=new WeatherTask();
        weatherTask.execute(new String[]{city});
    }
    private class DownloadImageAsyncTask extends AsyncTask<String,Void,Bitmap>{
        @Override
        protected Bitmap doInBackground(String... strings) {
            return downloadImage(strings[0]);
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            iconView.setImageBitmap(bitmap);
        }
        private Bitmap downloadImage(String code){
            final CloseableHttpClient httpClient= HttpClients.createDefault();
            final HttpGet getRequest=new HttpGet(Utils.ICON_URL+code+".png");
            try {
                HttpResponse response=httpClient.execute(getRequest);
                final int statusCode=response.getStatusLine().getStatusCode();
                if (statusCode!= HttpStatus.SC_OK){
                    Log.e("downloadimage","Error: "+statusCode);
                    return null;
                }
                final HttpEntity entity=response.getEntity();
                if (entity!=null){
                    InputStream inputStream=null;
                    inputStream=entity.getContent();
                    final Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class WeatherTask extends AsyncTask<String,Void,Weather>{

        @Override
        protected Weather doInBackground(String... strings) {
            String data=(new WeatherHttpClient()).getWeatherData(strings[0]);

            weather= JSONWeatherParser.getWeather(data);
            Log.v("data: ",weather.currentCondition.getDescription());
            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
            DateFormat df=DateFormat.getTimeInstance();
            String sunriseDate=df.format(new Date(weather.place.getSunrise()));
            String sunsetDate=df.format(new Date(weather.place.getSunset()));
            String updateDate=df.format(new Date(weather.place.getLastupdate()));
            DecimalFormat decimalFormat=new DecimalFormat("#.#");
            String tempFormat=decimalFormat.format(weather.currentCondition.getTemperature()-273.15);
            cityName.setText(weather.place.getCity()+","+weather.place.getCountry());
            temp.setText(""+tempFormat+" ℃");
            humidity.setText("湿度: "+weather.currentCondition.getHumidity()+"%");
            pressure.setText("气压: "+weather.currentCondition.getPressure()+"hPa");
            wind.setText("风力: "+weather.wind.getSpeed()+"mps");
            sunrise.setText("日出: "+sunriseDate);
            sunset.setText("日落: "+sunsetDate);
            updated.setText("更新时间: "+updateDate);
            description.setText("天气: "+weather.currentCondition.getCondition()+"("+weather.currentCondition.getDescription()+")");
            weather.iconData=weather.currentCondition.getIcon();
            System.out.println(weather.iconData);
            new DownloadImageAsyncTask().execute(weather.iconData);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.change_cityId){
            showInputDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showInputDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("change city");
        final EditText cityInput=new EditText(MainActivity.this);
        cityInput.setInputType(InputType.TYPE_CLASS_TEXT);
        cityInput.setHint("London");
        builder.setView(cityInput);
        builder.setPositiveButton("submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                CityPreference cityPreference=new CityPreference(MainActivity.this);
                cityPreference.setCity(cityInput.getText().toString());
                String newCity=cityPreference.getCity();
                renderWeatherData(newCity);
            }
        });
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CityPreference cityPreference=new CityPreference(MainActivity.this);
        cityPreference.onDestroy();
    }
}





























