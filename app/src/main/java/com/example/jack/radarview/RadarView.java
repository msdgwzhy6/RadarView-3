package com.example.jack.radarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class RadarView extends View{
    public Paint mCirclePaint=new Paint();
    public List<String> name=new ArrayList<>();

    public int broad_color=0;

    public RadarView(Context context) {
        this(context,null);
    }

    public RadarView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public void setName(List<String> name) {
        if(this.name.size()==0){
            this.name.addAll(name);
        }
    }

    public RadarView(Context context,AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.RadarView);
        int numCount=typedArray.getIndexCount();

        for(int i=0;i<numCount;i++){
            int attr=typedArray.getIndex(i);
            switch(attr){
                case R.styleable.RadarView_broad_color:
                    broad_color=typedArray.getColor(attr, Color.parseColor("#d1d1d1"));
                    break;
            }
        }
        initValue();
    }

    public void initValue(){
        mCirclePaint.setColor(broad_color);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(6f);
        mCirclePaint.setAntiAlias(true);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthModel=MeasureSpec.getMode(widthMeasureSpec);
        int heightModel=MeasureSpec.getMode(heightMeasureSpec);
        int measureWidth=MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight=MeasureSpec.getSize(heightMeasureSpec);
        int width; int height;
        if(widthModel==MeasureSpec.EXACTLY){
            width=measureWidth;
        }else{
            width=getPaddingLeft()+getPaddingRight()+measureWidth;
        }
        if(heightModel==MeasureSpec.EXACTLY){
            height=measureHeight;
        }else{
            height=(getPaddingTop()+getPaddingBottom()+measureHeight)/2;
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth()/2,getHeight()/2);
        float radius=getHeight()/3;
        drawRadarBroad(canvas,radius);
        drawRadarBroad(canvas,radius*((float)3/4));
        drawRadarBroad(canvas,radius*((float)1/2));
        drawRadarBroad(canvas,radius*((float)1/4));
        drawPointLine(canvas,radius);
        drawText(canvas,radius);

    }

    public void drawPointLine(Canvas canvas,float radius){
        Path path=new Path();
        path.moveTo(0,0);
        path.lineTo(0,-radius);
        canvas.drawPath(path, mCirclePaint);

        double[] rightTop=getTopAngle(radius);
        Path path2=new Path();
        path2.moveTo(0,0);
        path2.lineTo(Double.valueOf(rightTop[0]).floatValue(),-Double.valueOf(rightTop[1]).floatValue());
        canvas.drawPath(path2, mCirclePaint);

        Path path3=new Path();
        path3.moveTo(0,0);
        path3.lineTo(-Double.valueOf(rightTop[0]).floatValue(),-Double.valueOf(rightTop[1]).floatValue());
        canvas.drawPath(path3, mCirclePaint);

        double[] rightBottom=getBottomAngle(radius);
        Path path4=new Path();
        path4.moveTo(0,0);
        path4.lineTo(-Double.valueOf(rightBottom[0]).floatValue(),Double.valueOf(rightBottom[1]).floatValue());
        canvas.drawPath(path4, mCirclePaint);

        Path path5=new Path();
        path5.moveTo(0,0);
        path5.lineTo(-Double.valueOf(-rightBottom[0]).floatValue(),Double.valueOf(rightBottom[1]).floatValue());
        canvas.drawPath(path5, mCirclePaint);
    }

    public void drawRadarBroad(Canvas canvas,float radius){
        Path path=new Path();
        //上
        path.moveTo(0,-radius);
        //右上
        double[] rightTop=getTopAngle(radius);
        path.lineTo(Double.valueOf(rightTop[0]).floatValue(),-Double.valueOf(rightTop[1]).floatValue());
        //右下
        double[] rightBottom=getBottomAngle(radius);
        path.lineTo(Double.valueOf(rightBottom[0]).floatValue(),Double.valueOf(rightBottom[1]).floatValue());
        //左下
        path.lineTo(-Double.valueOf(rightBottom[0]).floatValue(),Double.valueOf(rightBottom[1]).floatValue());
        //左上
        path.lineTo(-Double.valueOf(rightTop[0]).floatValue(),-Double.valueOf(rightTop[1]).floatValue());
        //上
        path.lineTo(0,-radius+2);
        canvas.drawPath(path, mCirclePaint);
    }

    public void drawText(Canvas canvas,float radius){

    }

    public double[] getTopAngle(float radius){
        double[] param=new double[2];
        param[0]=Math.sin(Math.toRadians(72))*radius;
        param[1]=Math.sin(Math.toRadians(18))*radius;
        return param;
    }

    public double[] getBottomAngle(float radius){
        double[] param=new double[2];
        param[0]=Math.sin(Math.toRadians(36))*radius;
        param[1]=Math.sin(Math.toRadians(54))*radius;
        return param;
    }



}
