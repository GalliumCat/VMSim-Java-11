//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Color;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.Scrollbar;

class CNEControls extends Panel {
    MyCanvas target;
    Scheduler theScheduler;
    Label l;
    int resetValue;
    Scrollbar speed;

    public CNEControls(MyCanvas var1, Scheduler var2) {
        this.target = var1;
        this.theScheduler = var2;
        this.setLayout(new FlowLayout());
        this.setBackground(Color.lightGray);
        var1.setForeground(Color.black);
        this.l = new Label("Normal ");
        this.speed = new Scrollbar(0, 5, 1, 1, 10);
        this.add(new Label("     SPEED BAR"));
        this.add(this.speed);
        this.add(this.l);
    }

    public void reset_Scrollbar() {
        this.speed.setValue(5);
    }

    public void paint(Graphics var1) {
        Rectangle var2 = this.bounds();
        var1.setColor(Color.lightGray);
        var1.drawRect(0, 0, var2.width, var2.height);
    }

    public boolean handleEvent(Event var1) {
        if (var1.target instanceof Scrollbar) {
            switch(((Scrollbar)var1.target).getValue()) {
                case 1:
                    Scheduler.slider_num_one = 200;
                    Scheduler.slider_num_two = 100;
                    this.l.setText("Fastest");
                    break;
                case 2:
                    Scheduler.slider_num_one = 400;
                    Scheduler.slider_num_two = 200;
                    this.l.setText("Fast   ");
                    break;
                case 3:
                    Scheduler.slider_num_one = 600;
                    Scheduler.slider_num_two = 300;
                    this.l.setText("Fast   ");
                    break;
                case 4:
                    Scheduler.slider_num_one = 800;
                    Scheduler.slider_num_two = 400;
                    this.l.setText("Fast   ");
                    break;
                case 5:
                    Scheduler.slider_num_one = 1000;
                    Scheduler.slider_num_two = 500;
                    this.l.setText("Normal ");
                    break;
                case 6:
                    Scheduler.slider_num_one = 1200;
                    Scheduler.slider_num_two = 500;
                    this.l.setText("Slow   ");
                    break;
                case 7:
                    Scheduler.slider_num_one = 1400;
                    Scheduler.slider_num_two = 500;
                    this.l.setText("Slow   ");
                    break;
                case 8:
                    Scheduler.slider_num_one = 1600;
                    Scheduler.slider_num_two = 600;
                    this.l.setText("Slow   ");
                    break;
                case 9:
                    Scheduler.slider_num_one = 1800;
                    Scheduler.slider_num_two = 800;
                    this.l.setText("Slow   ");
                    break;
                case 10:
                    Scheduler.slider_num_one = 2000;
                    Scheduler.slider_num_two = 1000;
                    this.l.setText("Slowest");
            }

            return true;
        } else {
            return false;
        }
    }
}
