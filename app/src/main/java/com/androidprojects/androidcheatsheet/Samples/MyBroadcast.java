package com.androidprojects.androidcheatsheet.Samples;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import com.androidprojects.androidcheatsheet.R;

/**
 * Created by Nikhitha Tarala on 2/24/2017.
 */
public class MyBroadcast extends BroadcastReceiver{

    MediaPlayer mp;
    @Override
    public void onReceive(Context context, Intent intent) {

        mp=MediaPlayer.create(context, R.raw.htmlthesong);
        mp.start();
    }
}
