package com.novamaday.ticketbird.service;

import com.novamaday.ticketbird.module.status.StatusChanger;
import com.novamaday.ticketbird.utils.GlobalVars;

import java.util.ArrayList;
import java.util.Timer;

public class TimeManager {
    private static TimeManager instance;

    private final ArrayList<Timer> timers = new ArrayList<>();

    private TimeManager() {
    } //Prevent initialization

    /**
     * Gets the instance of the TimeManager that is loaded.
     *
     * @return The instance of the TimeManager.
     */
    public static TimeManager getManager() {
        if (instance == null) {
            instance = new TimeManager();
        }
        return instance;
    }

    /**
     * Initializes the TimeManager and schedules the appropriate Timers.
     */
    public void init() {
        Timer timer = new Timer(true);
        timer.schedule(new StatusChanger(), 10 * 1000, 10 * 1000);

        timers.add(timer);

        Timer amt = new Timer(true);
        amt.schedule(new ActivityMonitor(), GlobalVars.oneHourMs, GlobalVars.oneHourMs);

        timers.add(amt);
    }

    /**
     * Gracefully shuts down the TimeManager and exits all timer threads preventing errors.
     */
    void shutdown() {
        for (Timer t : timers) {
            t.cancel();
        }
    }
}