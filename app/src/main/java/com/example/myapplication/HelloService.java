package com.example.myapplication;

import android.app.Service;
import android.os.IBinder;
import android.content.Intent;

public class HelloService extends Service {
   
   int mStartMode;

   IBinder mBinder;
   
   boolean mAllowRebind;

   @Override
   public void onCreate() {
     
   }

   @Override
   public int onStartCommand(Intent intent, int flags, int startId) {
      return mStartMode;
   }

   @Override
   public IBinder onBind(Intent intent) {
      return mBinder;
   }

   @Override
   public boolean onUnbind(Intent intent) {
      return mAllowRebind;
   }

   @Override
   public void onRebind(Intent intent) {

   }

   @Override
   public void onDestroy() {

   }
}