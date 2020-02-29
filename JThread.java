package com.metanit;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class JThread extends Thread {
    public int lengthPolinoms;
    public int StepenPolinoma;
    public double minimal;
    public double maximal;

    int countPoints = 144000000 / 4;
    long startTime;
    long runTime = 0;
    double share;
    double[] array;

    private final Set<OnFinishListener> listeners
            = new CopyOnWriteArraySet<OnFinishListener>();

    public final void addListener(final OnFinishListener listener) {
        listeners.add(listener);
    }

    public final void removeListener(final OnFinishListener listener) {
        listeners.remove(listener);
    }

    private final void notifyListeners() {
        for (OnFinishListener listener : listeners) {
            listener.nottifyOfFinish(this);
        }
    }

    JThread(double[] array, double minimal, double maximal) {
        lengthPolinoms = array.length - 2;
        StepenPolinoma = array.length - 3;
        this.minimal = minimal;
        this.maximal = maximal;
        share = (maximal - minimal) / (countPoints - 1);
        this.array = array;
    }

    @Override
    public final void run() {
        try {
            doRun();
        } finally {
            notifyListeners();
        }
    }

    public void doRun() {
        for (double point = minimal; point <= maximal; ) {

            double sum = 0.0;
            int k = StepenPolinoma;

            for (int j = 2; j < array.length; j++) {
                startTime = System.nanoTime();
                sum += array[j] * Math.pow(point, k);
                runTime += System.nanoTime() - startTime;
                k--;
            }
            point += share;
        }
    }
}


