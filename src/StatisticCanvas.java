//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Panel;

class StatisticCanvas extends Panel {
    public static int num_references;
    public static int num_Page_Fault;
    public static int num_TLB_Misses;
    static String pageFault;
    String s_TBL;
    String total_Refer;
    String ratio_PageFault;
    String ratio_TLBMisses;
    float ratio_Page;
    float ratio_TLB;

    StatisticCanvas() {
        num_Page_Fault = 0;
        num_TLB_Misses = 0;
        num_references = 0;
        this.total_Refer = "No. of References:  " + num_references;
        pageFault = "Page Faults: " + num_Page_Fault;
        this.s_TBL = "TLB Misses: " + num_TLB_Misses;
        this.ratio_Page = 0.0F;
        this.ratio_TLB = 0.0F;
        this.action();
    }

    public void action() {
        this.total_Refer = "Total References:  " + num_references;
        pageFault = "Page Faults: " + num_Page_Fault;
        this.s_TBL = "TLB Misses: " + num_TLB_Misses;
        if (num_references != 0) {
            int var1 = (int)((float)num_Page_Fault / (float)num_references * 100.0F);
            this.ratio_Page = (float)var1 / 100.0F;
            int var2 = (int)((float)num_TLB_Misses / (float)num_references * 100.0F);
            this.ratio_TLB = (float)var2 / 100.0F;
        } else {
            this.ratio_Page = 0.0F;
            this.ratio_TLB = 0.0F;
        }

        this.ratio_PageFault = "Page Fault Ratio: " + this.ratio_Page;
        this.ratio_TLBMisses = "TLB Miss Ratio: " + this.ratio_TLB;
        this.repaint();
    }

    public void paint(Graphics var1) {
        Font var2 = new Font("TimesRoman", 0, 12);
        var1.setFont(var2);
        this.setBackground(Color.pink);

        try {
            var1.drawString(this.total_Refer, 3, 40);
            var1.drawString(pageFault, 20, 60);
            var1.drawString(this.s_TBL, 20, 80);
            var1.drawString(this.ratio_PageFault, 3, 150);
            var1.drawString(this.ratio_TLBMisses, 3, 170);
        } catch (NullPointerException var3) {
            System.out.println("paint()");
        }
    }
}
