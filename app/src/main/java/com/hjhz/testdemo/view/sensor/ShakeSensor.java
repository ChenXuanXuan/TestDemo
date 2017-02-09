package com.hjhz.testdemo.view.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.hjhz.testdemo.util.LogUtil;

/**
 * Created by 陈宣宣 on 2016/6/2.
 */
public class ShakeSensor implements SensorEventListener {

    public ShakeSensor(){

    }

    private Context mContext;
    private SensorManager mSensorManager;
    private Sensor mSensor;//加速度传感器

    private long lastTime;//记录最后一次时间
    private float lastX;//记录最后一次x轴的值
    private float lastY;
    private float lastZ;

    private OnShakeListener mOnShakeListener;

    public ShakeSensor(Context context){
        this.mContext = context;
    }

    public void init(){
        mSensorManager = (SensorManager)mContext.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //注册传感器
        mSensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_GAME);

    }

    /**
     * 取消注册
     */
    public void unRegisterListener(){
        mSensorManager.unregisterListener(this, mSensor);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        long currentTime = System.currentTimeMillis();
        if(currentTime-lastTime>20){
            long timeDistance = currentTime-lastTime;//两次摇动时间间隔
            lastTime = currentTime;

            //当前的值
            float x = event.values[0];//x轴变化的值
            float y = event.values[1];//y轴变化的值
            float z = event.values[2];//z轴变化的值

            double absValue = Math.abs(x+y+z-lastX-lastY-lastZ);
            //速度的阙值
            double speed = absValue/timeDistance*10000;
            if (speed>10000){
                if(null!=mOnShakeListener){

                    mOnShakeListener.onShake();
                }
            }
            lastX = x;
            lastY = y;
            lastZ = z;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    //回调接口
    public void setOnShakeListener(OnShakeListener onShakeListener){
        mOnShakeListener = onShakeListener;
    }
    public interface OnShakeListener{
        void onShake();
    }
}
