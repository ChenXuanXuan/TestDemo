package com.hjhz.testdemo.widget;

import android.Manifest;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.hjhz.testdemo.R;
import com.hjhz.testdemo.activity.MainActivity;
import com.hjhz.testdemo.application.AppContext;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by 陈宣宣 on 2016/6/28.
 */
public class WidgetProvider extends AppWidgetProvider{

    private Context context;

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        //widget被从屏幕移除
        Toast.makeText(context, "不要移除人家嘛", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        //最后一个widget被从屏幕移除
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        //第一个widget添加到屏幕上
        Toast.makeText(context, "这都被你发现了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        this.context = context;
        //刷新widget
        //通过 remoteview 和 AppWidgetManager

        /*//点击桌面组件时进入主程序入口
        Intent intent=new Intent(context, MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context, 0, intent, 0); //注意:PendingIntent.getActivity
        //RemoteViews类描述了一个View对象能够显示在其他进程中，可以融合layout资源文件实现布局。
        //虽然该类在android.widget.RemoteViews而不是appWidget下面,但在Android Widgets开发中会经常用到它，
        //主要是可以跨进程调用(appWidget由一个服务宿主来统一运行的)。
        RemoteViews myRemoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        //myRemoteViews.setImageViewResource(R.id.imageView, R.drawable.png1);//设置布局控件的属性（要特别注意）
        myRemoteViews.setOnClickPendingIntent(R.id.ll_desk, pendingIntent);
        ComponentName myComponentName = new ComponentName(context, WidgetProvider.class);
        //负责管理AppWidget，向AppwidgetProvider发送通知。提供了更新AppWidget状态，获取已经安装的Appwidget提供信息和其他的相关状态
        AppWidgetManager myAppWidgetManager = AppWidgetManager.getInstance(context);
        myAppWidgetManager.updateAppWidget(myComponentName, myRemoteViews);*/

        //点击发送换壁纸的广播
        Intent intent = new Intent("android.set.wallpaper");
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context, 0, intent,  0);//注意：PendingIntent.getBroadcast
        RemoteViews myRemoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        myRemoteViews.setOnClickPendingIntent(R.id.ll_desk, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, myRemoteViews);

    }

    //图片名
    String[] fileNames;
    WallpaperManager wallpaperManager;
    Bitmap bitmap;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        /*String action = intent.getAction();
        if(action.equals("android.set.wallpaper")){
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.pink);
            try {
                wallpaperManager.setBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            super.onReceive(context, intent);
        }*/

        ///////////////////////////////////////////////////////////////////////
        String action = intent.getAction();
        if(action.equals("android.set.wallpaper")){
            if(wallpaperManager==null){
                wallpaperManager = WallpaperManager.getInstance(context);
            }
            if (AppContext.picPaths.isEmpty()) {//如果picPaths中还没有照片
                getPicPath();
                bitmap = BitmapFactory.decodeFile(AppContext.picPaths.get(AppContext.current).toString());
                try {
                    wallpaperManager.setBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else{
                bitmap = BitmapFactory.decodeFile(AppContext.picPaths.get(AppContext.current).toString());
                try {
                    wallpaperManager.setBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Random random = new Random();
                AppContext.current = random.nextInt(AppContext.picPaths.size());
            }
        }else {
            super.onReceive(context, intent);
        }



    }

    //从SD卡的根目录下的wallpaper文件夹中读出图片路径到picPaths集合中
    public void getPicPath(){
        System.out.println( "getPicPath:"+AppContext.current);
        //如果手机插入了SD卡，且程序有读取SD卡的权限
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //获取手机SD卡的目录
            File sdCardDir=Environment.getExternalStorageDirectory();
            String path=null;
            try {
                path=sdCardDir.getCanonicalPath()+File.separator+"netease"+File.separator+"lofter"+File.separator+"LOFTER";
                System.out.println( "path:"+path);
            } catch (IOException e) {
                e.printStackTrace();
            }

            File file=new File(path);
            //fileNames = file.list();
            int permissionCheck1 = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
            int permissionCheck2 = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck1 != PackageManager.PERMISSION_GRANTED || permissionCheck2 != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.context,
               new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                       Manifest.permission.READ_EXTERNAL_STORAGE},
                    100);
                }

            File[] files =file.listFiles();
            if (files!=null){
                fileNames = new String[files.length];
                for (int i = 0; i < files.length; i++) {
                    if(!files[i].isDirectory()){
                        fileNames[i] = files[i].getName();
                    }
                }
                AppContext.picPaths.clear();//清空集合中的数据
                for (String picName:fileNames) {
                    if (isPicture(picName)) {
                        //将图片的完整路径添加到picPaths中
                        AppContext.picPaths.add(path+File.separator+picName);
                    }
                }
            }

         }
    }

    /**
     * 判断文件类型是否为图片
     * @param fileName  用于判断的文件名
     * @return
     */
    private boolean isPicture(String fileName) {
        //存放图片格式的数组
        String[] limit = new String[]{".png",".jpg",".gif",".jpeg" +
                        ".bmp",".PNG",".JPG",".GIF",".JPEG",".BMP"};
        for (String str:limit) {
            if (fileName.endsWith(str)) {//如果指定文件名为以上其中一种格式则返回true
                return true;
            }
        }
        return false;
    }
}
