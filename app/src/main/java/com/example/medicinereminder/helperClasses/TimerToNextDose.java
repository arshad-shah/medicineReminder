package com.example.medicinereminder.helperClasses;

import android.os.CountDownTimer;


/**
 * Class for future improvements of adding timer
 * ran out of time here
 * */
//public class TimerToNextDose {
//
//    public void timer(){
//
//        //the doses per day
//        String doses = medicineReminder.getDosesPerDay();
//
//        int dosesInt = Integer.parseInt(doses);
//
//
//        long start_time = System.currentTimeMillis();
//        long end_time = System.currentTimeMillis() + (86400000 / dosesInt);
//        long difference = end_time - start_time;
//        //calculate time to next dose and create a timer to it
//        CountDownTimer timer = new CountDownTimer(difference,1000){
//
//            @Override
//            public void onTick(long l) {
//                long secondsInMilli = 1000;
//                long minutesInMilli = secondsInMilli * 60;
//                long hoursInMilli = minutesInMilli * 60;
//
//                //elapsed hours
//                int elapsedHours = (int) (l / hoursInMilli);
//                l %= hoursInMilli;
//
//                //elapsed minutes
//                int elapsedMinutes = (int) (l / minutesInMilli);
//                l %= minutesInMilli;
//
//                //elapsed seconds
//                int elapsedSeconds = (int) (l / secondsInMilli);
//                l %= secondsInMilli;
//
//                //the textview to show timer in
//                //this gives a text timer like
//                ! -----------------
//                ! | - 5 : 50 : 00 |
//                ! -----------------
//                ? counting downward towards 0
//                timerText.setText(" - " + elapsedHours + " : " + elapsedMinutes + " : " + elapsedSeconds);
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        };
//        timer.start();
//    }
//
//}
