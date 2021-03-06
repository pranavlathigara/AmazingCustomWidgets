package com.anwesome.games.breakablebutton;

import android.graphics.*;

/**
 * Created by anweshmishra on 23/03/17.
 */
public class BreakableButtonController {
    private String text;
    private TextPart textParts[] = new TextPart[2];
    private int color = Color.parseColor("#00897B");
    private float w,h,x,y,dir = 0,deg=0,time=0;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public void setColor(int color) {
        this.color = color;
    }
    public BreakableButtonController(String text,float w) {
        this.w = w/2;
        this.h = w/4;
        this.x = w/2;
        this.y = w/2;
        this.text = text;
        initTextParts();
    }
    public boolean stopped() {
        return dir == 0;
    }
    public void initTextParts() {
        paint.setTextSize(h/3);
        String msg = "";
        int i = 0;
        for(char letter:text.toCharArray()) {

            if(paint.measureText(msg+letter) > paint.measureText(text)/2) {
                if(i == 1) {
                    msg = msg+letter;
                }
                textParts[i] = new TextPart(msg,(i-1)*paint.measureText(msg));
                i++;
                if(i == 2 ){
                    break;
                }
                msg = ""+letter;
            }
            else {
                msg = msg+letter;
            }
        }
    }
    public void draw(Canvas canvas) {

        canvas.save();
        canvas.translate(x,y);
        for(int i=0;i<2;i++) {
            paint.setColor(color);
            canvas.save();
            canvas.rotate((1-2*i)*deg);
            canvas.drawRect(new RectF((-w/2)*i,-h,(-w/2)*i+w/2,0),paint);
            textParts[1-i].draw(canvas,paint);
            canvas.restore();
        }
        canvas.restore();
    }
    public void update() {
        if(deg == 45 && time<10) {
            time+=dir;
            if(time == 10) {
                dir = -1;
            }
        }
        else {
            deg+=dir*9;
            if(deg>=45) {
                deg = 45;
            }
            if(deg <= 0) {
                deg = 0;
                dir = 0;
            }
        }


    }
    public void startMoving() {
        if(dir == 0) {
            dir = 1;
            time = 0;
        }
    }
    public static BreakableButtonController getInstance(String text,float w) {
        return new BreakableButtonController(text,w);
    }
    public int hashCode() {
        return (int)(x+y+w+h+dir+deg)+text.hashCode();
    }
    private class TextPart {
        private String text;
        private float x;
        public TextPart(String text,float x) {
            this.x = x;
            this.text = text;
        }
        public void draw(Canvas canvas,Paint paint) {
            paint.setColor(Color.WHITE);
            canvas.drawText(text,x,-h/2,paint);
        }
    }
}
