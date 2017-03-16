package com.example.rent.weatherapplication.observer_pattern;

import android.util.Log;

/**
 * Created by RENT on 2017-03-16.
 */

public class MailObserver implements Observer {
    @Override
    public void notifyMy() {
        Log.d("result", "wOOWOWOWOWOOWOWw zostalem powiadomiony! ");
    }
}
