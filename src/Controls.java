//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Button;
import java.awt.Color;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Rectangle;

class Controls extends Panel {
    MyCanvas target;
    CNEControls secondCtrl;
    Scheduler theScheduler;
    StatisticPanel statisticResult;
    Button stopButton;
    Button startButton;
    Button resetButton;
    Button stepButton;
    Label l;

    public Controls(MyCanvas var1, Scheduler var2, CNEControls var3, StatisticPanel var4) {
        this.target = var1;
        this.statisticResult = var4;
        this.theScheduler = var2;
        this.secondCtrl = var3;
        this.setLayout(new FlowLayout());
        this.setBackground(Color.lightGray);
        var1.setForeground(Color.black);
        this.l = new Label("                                  ");
        this.startButton = new Button("   Start  ");
        this.stopButton = new Button("   Stop   ");
        this.resetButton = new Button("   Reset  ");
        new Font("TimesRoman", 1, 20);
        this.stepButton = new Button("   Step   ");
        this.add(this.startButton);
        this.add(this.stopButton);
        this.add(this.resetButton);
        this.add(this.stepButton);
        this.add(this.secondCtrl);
    }

    public void paint(Graphics var1) {
        Rectangle var2 = this.bounds();
        var1.setColor(Color.lightGray);
        var1.drawRect(0, 0, var2.width, var2.height);
    }

    public boolean action(Event var1, Object var2) {
        if (!"   Start  ".equals(var2) && !" Play ".equals(var2)) {
            if ("   Stop   ".equals(var2)) {
                this.theScheduler.stop();
                this.startButton.setLabel(" Play ");
                return true;
            } else if ("   Reset  ".equals(var2)) {
                this.startButton.setLabel("   Start  ");
                this.theScheduler.resetSim();
                this.secondCtrl.reset_Scrollbar();
                Scheduler.slider_num_one = 1000;
                Scheduler.slider_num_two = 500;
                this.statisticResult.Reset();
                return true;
            } else if ("   Step   ".equals(var2)) {
                this.theScheduler.start();
                Scheduler.slider_num_one = 1000;
                Scheduler.slider_num_two = 500;

                try {
                    Thread.sleep((long)Scheduler.slider_num_two);
                } catch (InterruptedException var3) {
                }

                this.theScheduler.stop();
                return true;
            } else {
                return false;
            }
        } else {
            this.theScheduler.start();
            return true;
        }
    }
}
