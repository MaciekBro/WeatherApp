package com.example.rent.weatherapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rent.weatherapplication.builder_pattern.SimpleModel;
import com.example.rent.weatherapplication.observer_pattern.MailObserver;
import com.example.rent.weatherapplication.observer_pattern.MyObservable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.temperature)
    TextView temperature;
    @BindView(R.id.humidity)
    TextView humidity;
    @BindView(R.id.wind)
    TextView wind;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.text_input_edit)
    TextInputEditText textInputEditText;
    private Retrofit retrofit;
    private ProgressDialog progressDialog;
    //    @BindView(R.id.search_button)
//    FloatingActionButton searchButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://weathers.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        search("Warszawa");
    }

    private void search(String searchQuery) {

        WeatherService weatherService = retrofit.create(WeatherService.class);
        weatherService.getActualWeather(searchQuery)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weather ->{
                   Data data = weather.getData();
                    city.setText(data.getLocation());
                    temperature.setText(data.getTemperature() + "\u00b0 C");
                    humidity.setText(data.getHumidity());
                    wind.setText(data.getWind());
                    showImageBySkyText(data.getSkytext());

                    if (progressDialog!=null) {
                        progressDialog.hide();

                        showNotification(searchQuery); //myk! wiemy ze progres bar nie pojawia sie przy wejsciu do aplikacji!! nie chcemy wyswietlac notyfikacji przy starcie apli dla defoultowej wartości
                    }


                });

    }

    private void showNotification(String searchQuery) {
        PendingIntent mainActivityPendingIntent = PendingIntent.getActivity(this, 11, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.snowy);      //

        Notification notification = new NotificationCompat.Builder(this)
                .setContentText("Zaktualizowano pogodę dla miasta: " + searchQuery)
                .setContentTitle("WeatherApp")
                .setContentIntent(mainActivityPendingIntent)

                .setSmallIcon(R.drawable.ic_notification)
                .setFullScreenIntent(mainActivityPendingIntent, true)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).setBigContentTitle("Big Content").setSummaryText("summary text"))
                .addAction(R.drawable.ic_search_black_24dp,"Search", mainActivityPendingIntent ) //max 3 akcje!
                .addAction(R.drawable.ic_search_black_24dp,"Second", mainActivityPendingIntent ) //max 3 akcje!
                .addAction(R.drawable.ic_search_black_24dp,"Third", mainActivityPendingIntent ) //max 3 akcje!
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(searchQuery.hashCode(), notification);


        SimpleModel simpleModel = new SimpleModel("Maciej", "Ktoto", "Dom", "40");

        new SimpleModel.Builder()
                .withSurname("Tatfaratfa")
                .withName("Maćkowski")      //nie musi byc po kolei
                .withAdress("Warszawa")
                .withPhone("120")
                .build();

        MyObservable myObservable = new MyObservable();
        myObservable.subscribe(new MailObserver()); //mailobserwer zaczął obserwować Observable!
        myObservable.subscribe(new MailObserver());//obserwerów moze byc całe mnóstwo!

        myObservable.notifyAllSubscribers();

    }

    @OnClick(R.id.search_button)
    void onSearchButtonClick(){

        progressDialog = ProgressDialog.show(this,"Ladowanko", "Ladowanko w toku");

        search(textInputEditText.getText().toString());
    }

    private void showImageBySkyText(String skyText) {
        if ("Sky is clear".equalsIgnoreCase(skyText)){
            icon.setImageResource(R.drawable.sun);
        } else if ("Few clouds".equalsIgnoreCase(skyText)) {
            icon.setImageResource(R.drawable.windy);
        } else {
            icon.setImageResource(R.drawable.rain);
        }
    }


}
