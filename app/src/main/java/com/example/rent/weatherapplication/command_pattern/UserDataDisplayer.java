package com.example.rent.weatherapplication.command_pattern;

/**
 * Created by RENT on 2017-03-16.
 */

public class UserDataDisplayer {

    //nie musimy juz pozniej w niego ingerować!
    
    public void displayUserData(Command command){
        command.execute();
    }
}
