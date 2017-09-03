package com.example.bzdell.forus.Utils;

import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jack on 2017/9/1.
 */

public class showmytoast {

    public static void showMyToast(final Toast toast, final int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3500);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt );
    }


}
