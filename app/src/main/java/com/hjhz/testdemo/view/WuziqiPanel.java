package com.hjhz.testdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.hjhz.testdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈宣宣 on 2016/6/16.
 */
public class WuziqiPanel extends View {

    private int mPanelWidth;
    private float mLineHeight;
    private int MAX_LINE = 10;
    private int MAX_COUNT_IN_LINE = 5;

    private Paint mPaint = new Paint();
    //棋子图片
    private Bitmap mWhitePiece;
    private Bitmap mBlackPiece;

    //棋子大小 比例
    private float ratioPieceOfLineHeight = 3*1.0f / 4;

    private boolean mIsWhite = true; //白棋先手   或当前轮到白棋
    private ArrayList<Point> mWhiteArray = new ArrayList<>();
    private ArrayList<Point> mBlackArray = new ArrayList<>();

    private boolean mIsGameOver;
    private boolean mIsWhiteWinner;

    //实现构造方法
    public WuziqiPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        //setBackgroundColor(0x44ff0000);
        init();
    }

    private void init() {
        //初始化画笔
        mPaint.setColor(0x88000000);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);//抗锯齿
        mPaint.setStyle(Paint.Style.STROKE);//画线

        //初始化棋子
        mWhitePiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_w2);
        mBlackPiece = BitmapFactory.decodeResource(getResources(),R.drawable.stone_b1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = Math.min(widthSize,heightSize);
        if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = heightSize;
        }else if(heightMode == MeasureSpec.UNSPECIFIED){
            width = widthSize;
        }
        setMeasuredDimension(width, width); //设置view宽高


    }

    /*
    * 尺寸变化在onSizeChanged里初始化及设置
    * */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mPanelWidth = w;
        mLineHeight = mPanelWidth*1.0f / MAX_LINE;

        int pieceWidth = (int) (mLineHeight * ratioPieceOfLineHeight);
        mWhitePiece = Bitmap.createScaledBitmap(mWhitePiece,pieceWidth,pieceWidth,false);
        mBlackPiece = Bitmap.createScaledBitmap(mBlackPiece,pieceWidth,pieceWidth,false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(mIsGameOver) return false;

        int action = event.getAction();
        if(action == MotionEvent.ACTION_UP){
            //存储用户点击的坐标
            int x = (int) event.getX();
            int y = (int) event.getY();
            Point p = getValidPoint(x,y);//拿到合法点击坐标
            //防止重复在一个坐标上落棋子
            if(mWhiteArray.contains(p) || mBlackArray.contains(p)){
                return false;
            }
            if(mIsWhite){
                mWhiteArray.add(p);
            }else {
                mBlackArray.add(p);
            }
            invalidate();//重绘
            mIsWhite = !mIsWhite;

        }
        return true;//我感兴趣，touch事件交给我处理
    }

    private Point getValidPoint(int x, int y) {
        return new Point((int)(x/mLineHeight) ,(int)(y/mLineHeight));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBoard(canvas);//绘制棋盘
        drawPieces(canvas);//绘制棋子

        checkGameOver();
    }

    private void checkGameOver() {
        boolean whiteWin = checkFiveInLine(mWhiteArray);
        boolean blackWin = checkFiveInLine(mBlackArray);

        if(whiteWin || blackWin){
            mIsGameOver = true;
            mIsWhiteWinner = whiteWin;
            String text = mIsWhiteWinner ? "白棋胜利":"黑棋胜利";
            Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    /*
    * 判断是否5个棋子连成一条线
    * */
    private boolean checkFiveInLine(List<Point> points) {
        for (Point p : points) {
            int x = p.x;
            int y = p.y;
            boolean win = checkHorizontal(x,y,points);
            if(win) return true;
            win = checkVertical(x,y,points);
            if(win) return true;
            win = checkLeftDiagonal(x,y,points);
            if(win) return true;
            win = checkRightDiagonal(x,y,points);
            if(win) return true;

        }
        return false;
    }

    /*
    * 判断x,y位置的棋子，是否横向5个成一条线*/
    private boolean checkHorizontal(int x, int y, List<Point> points) {
        int count = 1;
        //左
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if(points.contains(new Point(x-i , y))) {
                count++;
            }else
                break;
        }
        if(count == MAX_COUNT_IN_LINE) return true;
        //右
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if(points.contains(new Point(x+i , y))) {
                count++;
            }else
                break;
        }

        if(count == MAX_COUNT_IN_LINE) return true;
        return false;
    }
    /*
    * 判断x,y位置的棋子，是否纵向5个成一条线*/
    private boolean checkVertical(int x, int y, List<Point> points) {
        int count = 1;
        //上
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if(points.contains(new Point(x , y-i))) {
                count++;
            }else
                break;
        }
        if(count == MAX_COUNT_IN_LINE) return true;
        //下
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if(points.contains(new Point(x , y+i))) {
                count++;
            }else
                break;
        }

        if(count == MAX_COUNT_IN_LINE) return true;
        return false;
    }
    /*
    * 判断x,y位置的棋子，是否左斜5个成一条线*/
    private boolean checkLeftDiagonal(int x, int y, List<Point> points) {
        int count = 1;
        //左下
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if(points.contains(new Point(x-i , y+i))) {
                count++;
            }else
                break;
        }
        if(count == MAX_COUNT_IN_LINE) return true;
        //左上
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if(points.contains(new Point(x+i , y-i))) {
                count++;
            }else
                break;
        }

        if(count == MAX_COUNT_IN_LINE) return true;
        return false;
    }
    /*
    * 判断x,y位置的棋子，是否右斜5个成一条线*/
    private boolean checkRightDiagonal(int x, int y, List<Point> points) {
        int count = 1;
        //右下
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if(points.contains(new Point(x+i , y+i))) {
                count++;
            }else
                break;
        }
        if(count == MAX_COUNT_IN_LINE) return true;
        //右上
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if(points.contains(new Point(x-i , y-i))) {
                count++;
            }else
                break;
        }

        if(count == MAX_COUNT_IN_LINE) return true;
        return false;
    }

    /*
    * 绘制棋子
    * */
    private void drawPieces(Canvas canvas) {
        //白棋子
        for (int i = 0,n = mWhiteArray.size(); i < n ; i++) {
            Point whitePoint = mWhiteArray.get(i);
            canvas.drawBitmap(mWhitePiece ,
                    (whitePoint.x + (1-ratioPieceOfLineHeight)/2)*mLineHeight,
                    (whitePoint.y + (1-ratioPieceOfLineHeight)/2)*mLineHeight,null);
        }
        //黑棋子
        for (int i = 0,n = mBlackArray.size(); i < n ; i++) {
            Point blackPoint = mBlackArray.get(i);
            canvas.drawBitmap(mBlackPiece ,
                    (blackPoint.x + (1-ratioPieceOfLineHeight)/2)*mLineHeight,
                    (blackPoint.y + (1-ratioPieceOfLineHeight)/2)*mLineHeight,null);
        }
    }

    /*
    * 绘制棋盘
    * */
    private void drawBoard(Canvas canvas) {
        int w = mPanelWidth;
        float lineHeight = mLineHeight;
        for (int i = 0; i < MAX_LINE; i++) {
            //横线
            int startX = (int) (lineHeight / 2);//起始坐标
            int endX = (int) (w-lineHeight / 2);//终点坐标
            //竖线
            int y = (int) ((0.5 + i)*lineHeight);

            canvas.drawLine(startX ,y ,endX ,y ,mPaint);//画横线
            canvas.drawLine( y ,startX ,y , endX ,mPaint);//画竖线
        }
    }

    /*
    * 存储棋子位置，恢复棋盘     (View的存储与恢复,注意：布局文件中要声明id)
    * */
    private static final String INSTANCE = "instance";
    private static final String INSTANCE_GAME_OVER = "instance_game_over";
    private static final String INSTANCE_WHITE_ARRAY = "instance_white_array";
    private static final String INSTANCE_BLACK_ARRAY = "instance_black_array";
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE,super.onSaveInstanceState());
        bundle.putBoolean(INSTANCE_GAME_OVER, mIsGameOver);
        bundle.putParcelableArrayList(INSTANCE_WHITE_ARRAY, mWhiteArray);
        bundle.putParcelableArrayList(INSTANCE_BLACK_ARRAY,mBlackArray);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle){
            Bundle bundle = (Bundle) state;
            mIsGameOver = bundle.getBoolean(INSTANCE_GAME_OVER);
            mWhiteArray = bundle.getParcelableArrayList(INSTANCE_WHITE_ARRAY);
            mBlackArray = bundle.getParcelableArrayList(INSTANCE_BLACK_ARRAY);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
