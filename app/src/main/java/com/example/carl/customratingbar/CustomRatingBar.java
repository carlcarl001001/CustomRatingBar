package com.example.carl.customratingbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.nfc.Tag;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.BaseExpandableListAdapter;

import androidx.annotation.Nullable;

public class CustomRatingBar extends View {
    private Bitmap mStarNormalBitmap ;
    private Bitmap mStarFocusBitmap ;
    private int mCurrentGrade=0;
    private int mGradeNumber=0;
    private int mInterval = 0;
    private String TAG ="chen";
    public CustomRatingBar(Context context) {
        //super(context);
        this(context,null);
    }

    public CustomRatingBar(Context context, @Nullable AttributeSet attrs) {
        //super(context, attrs);
        this(context,attrs,0);
    }

    public CustomRatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.RatingBar);
        int starNormalId = array.getResourceId(R.styleable.RatingBar_starNormal,0);
        if (starNormalId == 0){
            throw new RuntimeException("请设置属性 starNormal");
        }
        mStarNormalBitmap = BitmapFactory.decodeResource(getResources(),starNormalId);
        int starFocusId = array.getResourceId(R.styleable.RatingBar_starFocus,0);
        if (starFocusId == 0){
            throw new RuntimeException("请设置属性 starFocus");
        }
        mStarFocusBitmap = BitmapFactory.decodeResource(getResources(),starFocusId);
        mGradeNumber = array.getInt(R.styleable.RatingBar_gradeNumber,mGradeNumber);
        mInterval = array.getInt(R.styleable.RatingBar_interval,0);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //高度 一张图片的高度 ，自己实现 padding +间隔
        int height = mStarNormalBitmap.getHeight()+getPaddingTop()+getPaddingBottom();
        int width = mStarNormalBitmap.getWidth()*mGradeNumber+getPaddingLeft()+getPaddingRight();
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int start = getPaddingLeft();
        int top =getPaddingTop();
        for (int i=0;i<mGradeNumber;i++){
            int x = i*(mStarNormalBitmap.getWidth()+mInterval);
            if (i>=mCurrentGrade){
                canvas.drawBitmap(mStarNormalBitmap,start+x,top,null);
            }else {
                canvas.drawBitmap(mStarFocusBitmap,start+x,top,null);
            }
            //canvas.drawBitmap(mStarNormalBitmap,x,0,null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                int currentGrade = (int)(moveX/mStarNormalBitmap.getWidth()+1);
                if (currentGrade<0){
                    currentGrade=0;
                }
                if (currentGrade>mGradeNumber){
                    currentGrade = mGradeNumber;
                }
                if (currentGrade!=mCurrentGrade) {
                    mCurrentGrade = currentGrade;
                    Log.i(TAG, "mCurrentGrade:" + mCurrentGrade);
                    invalidate();
                }
                break;

        }
        return true;
    }
}













