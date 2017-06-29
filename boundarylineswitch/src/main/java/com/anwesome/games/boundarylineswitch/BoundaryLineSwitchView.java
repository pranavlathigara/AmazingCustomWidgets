package com.anwesome.games.boundarylineswitch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by anweshmishra on 29/06/17.
 */

public class BoundaryLineSwitchView extends View {
    private int w,h,time = 0,size;
    private Bitmap bitmap;
    private int color = Color.parseColor("#00BCD4");
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public BoundaryLineSwitchView(Context context, Bitmap bitmap) {
        super(context);
        this.bitmap = bitmap;
    }
    public void onDraw(Canvas canvas) {
        if(time == 0) {
            w = canvas.getWidth();
            h = canvas.getHeight();
            size = Math.min(w,h)/2;
            bitmap = Bitmap.createScaledBitmap(bitmap,size,size,true);
        }
        canvas.save();
        canvas.translate(w/2,h/2);
        canvas.drawBitmap(bitmap,-size/2,-size/2,paint);
        canvas.restore();
        time++;
    }
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {

        }
        return true;
    }
    private class BoundaryLine {
        private float wx = 0;
        private int index = 0,dir = 0;
        public BoundaryLine(int index) {
            this.index = index;
        }
        public void draw(Canvas canvas) {
            canvas.save();
            canvas.translate(w/2,h/2);
            canvas.rotate(index*90);
            canvas.drawLine(size/2,-wx,size/2,wx,paint);
            canvas.restore();
        }
        public void update() {
            wx += (size/10)*dir;
            if(wx>size/2) {
                dir = 0;
                wx = size/2;
            }
            if(wx<0) {
                dir = 0;
                wx = 0;
            }
        }
        public int hashCode() {
            return index;
        }
        public void startUpdating(int dir) {
            this.dir = dir;
        }
        public boolean stopUpdating() {
            return dir == 0;
        }
    }
    private class AnimationHandler {
        private boolean animated = false;
        private BoundaryLine curr,prev;
        public void animate(Canvas canvas) {
            if(curr != null) {
                curr.draw(canvas);
            }
            if(prev != null) {
                prev.draw(canvas);
            }
            if(animated) {
                if(prev!=null) {
                    prev.update();
                }
                if(curr != null) {
                    curr.update();
                }
                if(curr.stopUpdating()) {
                    prev = curr;
                    animated = false;
                    curr = null;
                }
                try  {
                    Thread.sleep(50);
                    invalidate();
                }
                catch (Exception ex) {

                }
            }
        }
        public void startAnimating() {
            if(!animated && curr == null) {
                if(prev != null) {
                    prev.startUpdating(-1);
                }
                animated = true;
                postInvalidate();
            }
        }
    }
}
