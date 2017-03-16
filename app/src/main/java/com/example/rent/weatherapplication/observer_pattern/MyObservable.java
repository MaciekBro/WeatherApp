package com.example.rent.weatherapplication.observer_pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RENT on 2017-03-16.
 */

public class MyObservable implements Observable {

    private List<Observer> observers = new ArrayList<>();

    @Override
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void notifyAllSubscribers() {
        for (Observer observer : observers) {
            observer.notifyMy();
        }
    }

}
