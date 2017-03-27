package com.example.appla.iattended;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import android.os.RemoteException;
import android.util.Log;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;



public class BeaconService extends Service implements BeaconConsumer {
    private BeaconManager beaconManager;
    private static final String TAG = "BeaconReferenceApp";
    final static String MY_ACTION = "MY_ACTION";

    public BeaconService() {

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().clear();
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24"));

        beaconManager.bind(this);
        //TODO do something useful
        Log.d("Rana","Service started!");
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        return null;
    }

    @Override
    public void onBeaconServiceConnect() {
        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
            //beaconManager.startRangingBeaconsInRegion(new Region());
        } catch (RemoteException e) {
        }


        beaconManager.setRangeNotifier(new RangeNotifier() {
                                           @Override
                                           public void didRangeBeaconsInRegion(final Collection<Beacon> beacons, Region region) {
                                               Thread thread = new Thread(new Runnable() {
                                                   @Override
                                                   public void run() {
                                                       try {
                                                           Intent intent = new Intent();
                                                           intent.setAction(MY_ACTION);

                                                           if (beacons.size() > 0) {
                                                               String distance = beacons.iterator().next().getDistance() + " meters away.";
                                                               final String major = beacons.iterator().next().getId2() + "";
                                                               final String minor = beacons.iterator().next().getId3() + "";
                                                               final int rssi = beacons.iterator().next().getRssi();
                                                               final int power = beacons.iterator().next().getTxPower();
                                                               final String name = beacons.iterator().next().getBluetoothName();
                                                               Log.d("Major", major);
                                                               Log.d("Minor", minor);
                                                               Log.d("RSSI", rssi +"");
                                                               Log.d("power ", power +"");
                                                               Log.d("distance", distance);
                                                               Log.d("Name", name);

                                                               if (name.equals("C5.102") && major.equals("1")){
                                                                   Log.d("Rana","Beacon 1   "+Double.parseDouble(distance.substring(0,distance.indexOf(' ')))+"");
                                                                   intent.putExtra("D1",Double.parseDouble(distance.substring(0,distance.indexOf(' '))));

                                                               }if (name.equals("C5.102") && major.equals("2")){
                                                                   Log.d("Rana","Beacon 2   "+Double.parseDouble(distance.substring(0,distance.indexOf(' ')))+"");
                                                                   intent.putExtra("D2",Double.parseDouble(distance.substring(0,distance.indexOf(' '))));

                                                               }if (name.equals("C5.102") && major.equals("3")){
                                                                   Log.d("Rana","Beacon 3   "+Double.parseDouble(distance.substring(0,distance.indexOf(' ')))+"");
                                                                   intent.putExtra("D3",Double.parseDouble(distance.substring(0,distance.indexOf(' '))));

                                                               }if (name.equals("C5.102") && major.equals("4")){
                                                                   Log.d("Rana","Beacon 4   "+Double.parseDouble(distance.substring(0,distance.indexOf(' ')))+"");
                                                                   intent.putExtra("D4",Double.parseDouble(distance.substring(0,distance.indexOf(' '))));

                                                               }
                                                               sendBroadcast(intent);
                                                           }

                                                       } catch (Exception e) {
                                                           e.printStackTrace();
                                                       }
                                                   }

                                               });
                                               thread.start();


                                           }
                                       }

        );
        beaconManager.setMonitorNotifier(new

                                                 MonitorNotifier() {
                                                     @Override
                                                     public void didEnterRegion(Region region) {
                                                         Log.i(TAG, "I just saw an beacon for the first time!");
                                                     }

                                                     @Override
                                                     public void didExitRegion(Region region) {
                                                         Log.i(TAG, "I no longer see an beacon");
                                                     }

                                                     @Override
                                                     public void didDetermineStateForRegion(int state, Region region) {
                                                         Log.i(TAG, "I have just switched from seeing/not seeing beacons: " + state);
                                                     }
                                                 }

        );
    }



}
