package com.example.rent.weatherapplication.command_pattern;

import android.util.Log;

/**
 * Created by RENT on 2017-03-16.
 */

public class DisplayNameFirstCommand implements Command {

    @Override
    public void execute() {
        Log.d("command", "Maćko Nazwisko"); //wyswietla najpierw imie a potem nazwisko
    }



}
