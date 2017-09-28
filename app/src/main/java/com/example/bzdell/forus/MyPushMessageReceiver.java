package com.example.bzdell.forus;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.push.PushConstants;

/**
 * Created by Jack on 2017/9/1.
 */

public class MyPushMessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
            String jsonStr = intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);
            String content = null;
            try {
                // 处理JSON
                JSONObject jsonObject = new JSONObject(jsonStr);
                content = jsonObject.getString("alert");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            Notification notify = new Notification.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Forus软件提醒")
                    .setContentText(content)
                    .build();
            notify.flags =Notification.FLAG_INSISTENT;
            manager.notify(1, notify);


        }
    }

}
