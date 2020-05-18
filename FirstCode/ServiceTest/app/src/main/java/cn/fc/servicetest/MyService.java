package cn.fc.servicetest;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class MyService extends Service {

    private DownloadBinder mBinder = new DownloadBinder();

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d("MyService", "onCreate executed");
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this).setContentText("This is content text")
                .setContentTitle("This is content title")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .build();
        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int startId){
        Log.d("MyService", "onStartCommand executed");
        return super.onStartCommand(intent, flag, startId);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("MyService", "onDestroy executed");
    }

    public class DownloadBinder extends Binder {

        public void startDownload(){
            Log.d("Myservice", "startDownload executed");
        }

        public int getProgress(){
            Log.d("Myservice", "getProgress executed");
            return 0;
        }

    }
}
