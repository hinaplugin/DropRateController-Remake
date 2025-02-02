package com.hinaplugin.dropRateController;

import java.util.Random;

public class Rate {

    public Random random = new Random();

    public double rate = DropRateController.config.getDouble("rate", 1.0);

    public boolean lottery(){
        return random.nextDouble() > rate / 100.0;
    }
}
