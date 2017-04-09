package com.anwesome.ui.notificationbutton;

import android.graphics.*;

/**
 * Created by anweshmishra on 09/04/17.
 */
public class NotificationButtonController {
    private float x,y,size,deg = 0,dir = 0;
    public NotificationButtonController(float x,float y,float size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }
    public void draw(Canvas canvas, Paint paint) {
        canvas.save();
        canvas.translate(x,y);
        canvas.rotate(deg);
        Path path = new Path();
        path.moveTo(-size/4,0);
        path.lineTo(-size/4+size/10,-size/10);
        float cax = 0,cay = -size/10,ha = (size*9)/10,wa = size/4-size/10;
        path.arcTo(new RectF(cax-wa,cay-ha,cax+wa,cay+ha),180,180, true);
        path.lineTo(size/4,0);
        canvas.drawPath(path,paint);
        canvas.drawCircle(0,-size,size/40,paint);
        float sax = 0,say = size/20,sar = size/40;
        canvas.drawArc(new RectF(sax-sar,say-sar,sax+sar,say+sar),0,180,true,paint);
        canvas.restore();
    }
    public void update() {
        deg+=dir*30;
        if(deg>=360) {
            dir = 0;
            deg = 0;
        }
    }
    public void startMoving() {
        dir = deg == 0?1:dir;
    }
    public boolean stop() {
        return dir == 0;
    }
}
