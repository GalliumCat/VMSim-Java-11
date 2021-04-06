//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;

class MyCanvas extends Panel {
    static int width;
    static int height;
    public static SimulatorDisplayUtils theDispUtils;
    Graphics offScreenGraphics;
    Image offScreenImage;
    public int drawAll;

    public MyCanvas(int var1, int var2) {
        width = var1;
        height = var2;
        this.drawAll = -1;
    }

    public SimulatorDisplayUtils createDispUtils() {
        theDispUtils = new SimulatorDisplayUtils();
        return theDispUtils;
    }

    public void switchProcess() {
        this.drawAll = 0;
        this.repaint();
    }

    public void display() {
        this.drawAll = 1;
        this.repaint();
    }

    public void updatePTonly() {
        Graphics var1 = this.getGraphics();
        this.drawAll = 0;
        this.paint(var1);
    }

    public void update(Graphics var1) {
        this.paint(var1);
    }

    public void paint(Graphics var1) {
        this.setBackground(Color.white);
        theDispUtils.displayLayout(this.drawAll, var1);
    }
}
