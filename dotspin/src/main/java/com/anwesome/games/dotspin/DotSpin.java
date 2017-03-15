package com.anwesome.games.dotspin;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by anweshmishra on 16/03/17.
 */
public class DotSpin {
    private Activity activity;
    private DotSpinButton dotSpinButton = DotSpinButton.getInstance();
    public DotSpin(Activity activity) {
        this.activity = activity;
    }
    public void show(int x,int y) {

    }
    private class DotSpinView extends View {
        private boolean isAnimated = false;
        private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        public DotSpinView(Context context) {
            super(context);
        }
        public void onDraw(Canvas canvas) {
            dotSpinButton.draw(canvas,paint);
            if(isAnimated) {
                dotSpinButton.update();
                if(dotSpinButton.stopped()) {
                    isAnimated = false;
                }
                try {
                    Thread.sleep(50);
                    invalidate();
                }
                catch (Exception ex) {

                }
            }
        }
        public boolean onTouchEvent(MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN && !isAnimated) {
                dotSpinButton.startMoving();
                isAnimated = true;
                postInvalidate();
            }
            return true;
        }
    }
}
