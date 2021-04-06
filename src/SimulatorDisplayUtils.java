//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class SimulatorDisplayUtils extends VMS {
    private int canvasWidth = 552;
    private int canvasHeight = 279;
    private int originX = 10;
    private int originY = 10;
    private int MMUoX;
    private int MMUoY;
    private int MMUwidth;
    private int MMUheight;
    private final int MMU_BOX_HEIGHT = 350;
    private Color C_SHADOW = new Color(200, 200, 200);
    private Color C_PROCESS_RUN = new Color(100, 210, 250);
    private Color C_PROCESS_SLEEP = new Color(215, 235, 250);
    private final int SHADOW_WIDTH = 4;
    private final int SHADOW_HEIGHT = 4;
    private final int TOTAL_PROCS = 4;
    private final int PROCESS_WIDTH = 40;
    private final int PROCESS_HEIGHT = 50;
    private int processOriginX = 40;
    private int processOriginY = 2;
    private int processSeparation;
    private final Color C_KERNEL_BG = new Color(200, 255, 196);
    private int kernelX;
    private int kernelY;
    private final int KERNEL_WIDTH = 80;
    private final int KERNEL_HEIGHT = 25;
    private final int TOTAL_TLB_ENTRIES = 4;
    private final int TLB_WIDTH = 100;
    private final int TLB_HEIGHT = 20;
    private int TLBoX;
    private int TLBoY;
    private final Color C_TLB_BG = new Color(240, 240, 180);
    private final int TOTAL_PT_ENTRIES = 32;
    private final int PT_WIDTH = 15;
    private final int PT_HEIGHT = 40;
    private int PToX;
    private int PToY;
    private final Color C_PT_BG = new Color(255, 240, 200);
    private final int TOTAL_MM_CELLS = 16;
    private final int MM_WIDTH = 25;
    private final int MM_HEIGHT = 25;
    private int MMoX;
    private int MMoY;
    private final Color C_MM_BG = new Color(255, 195, 180);
    private final int HD_WIDTH = 100;
    private final int HD_HEIGHT = 60;
    private int HDoX;
    private int HDoY;
    private final Color C_HD_BG = new Color(255, 240, 210);
    private final Color C_HD_LINES = new Color(140, 100, 80);
    private final Color C_CONN_PASSIVE = new Color(220, 220, 220);
    private final Color C_CONN_ACTIVE = new Color(255, 0, 0);
    private Color C_HIT = new Color(0, 255, 0);
    private Color C_MISS = new Color(255, 0, 0);
    private Graphics theG;
    private final String[] procStr = new String[]{"P1", "P2", "P3", "P4"};
    private final String[] numStr = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    StatisticPanel s_Statistic;
    int firstTime;

    public SimulatorDisplayUtils() {
        this.processSeparation = (this.canvasWidth - 80 - 160) / 3;
        this.kernelX = this.originX + this.canvasWidth / 2 - 40;
        this.kernelY = this.processOriginY + 50 + 25;
        this.MMUoX = this.originX + 15;
        this.MMUoY = this.processOriginY + 50 + 80;
        this.MMUwidth = this.canvasWidth - 20;
        this.MMUheight = this.MMUoY + 350;
        this.TLBoX = this.MMUoX + 20;
        this.TLBoY = this.MMUoY + 100;
        this.PToX = this.MMUoX + 20;
        this.PToY = this.TLBoY + 180;
        this.MMoX = this.MMUoX + 100 + 200;
        this.MMoY = this.MMUoY + 20;
        this.HDoX = this.MMUoX + 100 + 200;
        this.HDoY = this.MMUoY + 160;
    }

    public void changeProcess() {
        int var1 = VMS.theScheduler.getPID();
        if (var1 == 0) {
            this.C_PROCESS_RUN = new Color(0, 153, 255);
            this.C_HIT = new Color(0, 153, 255);
        }

        if (var1 == 1) {
            this.C_PROCESS_RUN = new Color(255, 255, 153);
            this.C_HIT = new Color(255, 255, 153);
        }

        if (var1 == 2) {
            this.C_PROCESS_RUN = new Color(0, 255, 153);
            this.C_HIT = new Color(0, 255, 153);
        }

        if (var1 == 3) {
            this.C_PROCESS_RUN = new Color(255, 51, 0);
            this.C_HIT = new Color(255, 51, 0);
        }

    }

    private void delay(long var1) {
        long var3 = System.currentTimeMillis();

        while(System.currentTimeMillis() < var3 + var1) {
        }

    }

    public void displayLayout(int var1, Graphics var2) {
        this.theG = var2;
        if (var1 == 0) {
            this.displayPT(VMS.theScheduler.theMMU[VMS.theScheduler.getPID()].getCurPTI(), false);
        }

        int var3;
        if (var1 == -1) {
            this.drawShadow(this.MMUoX, this.MMUoY, this.MMUwidth, 350);
            this.theG.setColor(Color.black);
            this.theG.drawRect(this.MMUoX, this.MMUoY, this.MMUwidth, 350);
            ++this.firstTime;
            this.displayKernel();
            this.theG.setFont(new Font("TimesRoman", 1, 14));
            this.theG.drawString("  MMU  ", this.kernelX + 15, this.kernelY + 17);
            this.joinKernelToTLB(false);

            for(var3 = 0; var3 < 4; ++var3) {
                this.displayProcess(var3, false);
                this.theG.setFont(new Font("TimesRoman", 1, 14));
                this.theG.drawString(this.procStr[var3], this.processOriginX + var3 * (40 + this.processSeparation) + 20 - 3, this.processOriginY + 50 + 20);
            }

            this.theG.setFont(new Font("TimesRoman", 1, 14));
            this.theG.drawString("TLB", this.TLBoX + 35, this.TLBoY - 10);

            for(var3 = 0; var3 < 4; ++var3) {
                this.displayTLB(var3, false);
            }

            this.theG.setFont(new Font("TimesRoman", 1, 14));
            this.theG.drawString("Page Table", this.PToX + 200, this.PToY - 10);
            this.drawShadow(this.PToX, this.PToY, 480, 40);

            for(var3 = 0; var3 < 32; ++var3) {
                this.displayPT(var3, false);
            }

            for(var3 = 0; var3 < 32; ++var3) {
                this.displayPTIndex(var3);
            }

            this.theG.setFont(new Font("TimesRoman", 1, 14));
            this.theG.drawString("Memory", this.MMoX + 60, this.MMoY + 120);
            this.drawShadow(this.MMoX, this.MMoY, 100, 100);

            for(var3 = 0; var3 < 16; ++var3) {
                this.displayMM(var3, false, -1);
            }

            this.displayHD();
            this.theG.setFont(new Font("TimesRoman", 1, 14));
            this.theG.setColor(Color.black);
            this.theG.drawString("Hard Disk", this.HDoX + 20, this.HDoY + 30);
            this.joinProcessToKernel(true, VMS.getRunningProc());

            for(var3 = 0; var3 < 4; ++var3) {
                if (var3 != VMS.getRunningProc()) {
                    this.joinProcessToKernel(false, var3);
                }
            }

            this.joinMMToKernel(false);
            this.joinTLBToPT(false);
            this.joinPTToMM(false);
            this.joinTLBToMM(false);
            this.joinHDToMM(false);
            this.joinProcessToKernel(false, 0);
            this.joinProcessToKernel(false, 1);
            this.joinProcessToKernel(false, 2);
            this.joinProcessToKernel(false, 3);
            this.theG.setColor(Color.white);
            this.theG.fillRect(this.TLBoX + 40, this.TLBoY + 90, 100, 40);
            this.theG.fillRect(this.PToX + 350, this.PToY - 23, 150, 20);
            this.theG.setColor(Color.white);
            this.theG.fillRect(this.MMoX + 130, this.MMoY + 70, 100, 50);
        }

        if (var1 == 1) {
            if (VMS.getSwitchProcess()) {
                this.changeProcess();

                for(var3 = 0; var3 < 4; ++var3) {
                    this.displayTLB(var3, false);
                }

                for(var3 = 0; var3 < 32; ++var3) {
                    this.displayPT(var3, false);
                }
            }

            this.displayKernel();
            this.theG.setFont(new Font("TimesRoman", 1, 14));
            this.theG.drawString("  MMU  ", this.kernelX + 15, this.kernelY + 17);
            this.joinMMToKernel(true);
            this.displayProcess(VMS.getLastRunningProc(), false);
            this.joinProcessToKernel(false, VMS.getLastRunningProc());
            this.displayProcess(VMS.getRunningProc(), true);
            this.joinProcessToKernel(true, VMS.getRunningProc());
            this.joinHDToMM(false);
            this.joinKernelToTLB(true);
            int var4;
            if (VMS.getTlbState()) {
                this.theG.setColor(Color.white);
                this.theG.fillRect(this.PToX + 350, this.PToY - 23, 150, 20);
                this.theG.fillRect(this.MMoX + 130, this.MMoY + 70, 100, 50);

                for(var4 = 0; var4 < 4; ++var4) {
                    this.displayTLB(var4, false);
                }

                this.displayTLB(this.getTlbHitIndex(), true);
                this.joinTLBToPT(false);
                this.joinTLBToMM(true);
                this.joinPTToMM(false);
                this.displayHitMM(VMS.getCurMM(), VMS.getMMvPageFrame());
                this.theG.setColor(Color.white);
                this.theG.fillRect(this.TLBoX + 40, this.TLBoY + 90, 100, 40);
                this.theG.setColor(Color.blue);
                this.theG.setFont(new Font("TimesRoman", 1, 14));
                this.theG.drawString("TLB hit", this.TLBoX + 45, this.TLBoY + 110);
                this.theG.setFont(new Font("TimesRoman", 1, 12));
                return;
            }

            for(var4 = 0; var4 < 4; ++var4) {
                this.displayTLB(var4, false);
            }

            this.joinTLBToPT(true);
            this.joinTLBToMM(false);
            this.theG.setColor(Color.white);
            this.theG.fillRect(this.TLBoX + 40, this.TLBoY + 90, 100, 40);
            this.theG.setColor(Color.red);
            this.theG.setFont(new Font("TimesRoman", 1, 14));
            this.theG.drawString("TLB miss", this.TLBoX + 45, this.TLBoY + 110);
            if (VMS.theScheduler.theMMU[VMS.theScheduler.getPID()].getPtState()) {
                this.theG.setFont(new Font("TimesRoman", 1, 12));
                this.theG.setColor(Color.red);
                this.theG.drawString("         ", this.MMoX + 135, this.MMoY + 80);
                this.theG.drawString("           ", this.MMoX + 135, this.MMoY + 100);
                this.displayPT(VMS.theScheduler.theMMU[VMS.theScheduler.getPID()].getLastPTI(), false);
                this.displayPT(VMS.theScheduler.theMMU[VMS.theScheduler.getPID()].getCurPTI(), true);
                this.joinPTToMM(true);
                this.displayHitMM(VMS.getCurMM(), VMS.getMMvPageFrame());
                this.theG.setColor(Color.white);
                this.theG.fillRect(this.PToX + 350, this.PToY - 23, 150, 20);
                this.theG.setFont(new Font("TimesRoman", 1, 14));
                this.theG.setColor(Color.blue);
                this.theG.drawString("Page Table hit", this.PToX + 360, this.PToY - 10);
                this.theG.setColor(Color.white);
                this.theG.fillRect(this.MMoX + 130, this.MMoY + 70, 100, 50);
                return;
            }

            this.joinPTToMM(false);
            this.joinTLBToPT(false);
            this.joinKernelToTLB(false);
            this.joinHDToMM(true);
            this.theG.setColor(Color.white);
            this.theG.fillRect(this.PToX + 350, this.PToY - 23, 150, 20);
            this.theG.setFont(new Font("TimesRoman", 1, 14));
            this.theG.setColor(Color.red);
            this.theG.drawString("Page Fault", this.PToX + 360, this.PToY - 10);
            this.theG.setColor(Color.white);
            this.theG.fillRect(this.MMoX + 130, this.MMoY + 70, 100, 50);
            this.theG.setFont(new Font("TimesRoman", 1, 12));
            this.theG.setColor(Color.red);
            this.theG.drawString("Paging in", this.MMoX + 135, this.MMoY + 80);
            this.theG.drawString("Updating PT", this.MMoX + 135, this.MMoY + 100);
            this.displayMM(VMS.getCurMM(), true, VMS.getMMvPageFrame());
            this.displayPT(VMS.theScheduler.theMMU[VMS.theScheduler.getPID()].getLastPTI(), false);
            this.displayPT(VMS.theScheduler.theMMU[VMS.theScheduler.getPID()].getCurPTI(), false);
        }

    }

    protected void displayProcess(int var1, boolean var2) {
        int var3 = this.processOriginX + var1 * (40 + this.processSeparation);
        int var4 = this.processOriginY;
        this.theG.setFont(new Font("TimesRoman", 1, 14));
        if (var1 == 0) {
            this.C_PROCESS_SLEEP = new Color(0, 153, 255);
        }

        if (var1 == 1) {
            this.C_PROCESS_SLEEP = new Color(255, 255, 153);
        }

        if (var1 == 2) {
            this.C_PROCESS_SLEEP = new Color(0, 255, 153);
        }

        if (var1 == 3) {
            this.C_PROCESS_SLEEP = new Color(255, 51, 0);
        }

        if (var2) {
            this.theG.setColor(this.C_PROCESS_RUN);
            this.theG.fillRect(var3, var4, 40, 50);
            this.drawShadow(var3, var4, 40, 50);
            this.theG.setColor(Color.black);
            this.theG.drawRect(var3, var4, 40, 50);
            this.theG.setColor(Color.black);
            this.theG.drawString("Exec ", var3 + 5, var4 + 15);
            this.theG.setFont(new Font("TimesRoman", 1, 14));
            this.theG.drawString(Integer.toString(VMS.gimmePageNum), var3 + 13, var4 + 35);
        } else {
            this.theG.setColor(this.C_PROCESS_SLEEP);
            this.theG.fillRect(var3, var4, 40, 50);
            this.drawShadow(var3, var4, 40, 50);
            this.theG.setColor(Color.black);
            this.theG.drawRect(var3, var4, 40, 50);
            this.theG.setFont(new Font("TimesRoman", 1, 10));
            this.theG.drawString("Sleep", var3 + 10, var4 + 30);
        }

        this.theG.setFont(new Font("TimesRoman", 1, 14));
        this.theG.setColor(Color.black);
    }

    public void displayKernel() {
        this.theG.setColor(this.C_KERNEL_BG);
        this.theG.fillRect(this.kernelX, this.kernelY, 80, 25);
        this.drawShadow(this.kernelX, this.kernelY, 80, 25);
        this.theG.setColor(Color.black);
        this.theG.drawRect(this.kernelX, this.kernelY, 80, 25);
    }

    public void displayTLB(int var1, boolean var2) {
        int var3 = this.TLBoX;
        int var4 = this.TLBoY + var1 * 20;
        if (var2) {
            this.theG.setColor(this.C_HIT);
        } else {
            this.theG.setColor(this.C_TLB_BG);
        }

        this.theG.fillRect(var3, var4, 100, 20);
        if (var1 == 3) {
            this.drawShadow(var3, var4, 100, 20);
        }

        this.theG.setColor(this.C_SHADOW);
        this.theG.fillRect(var3 + 100 + 1, var4 + 4, 4, 20);
        this.theG.setColor(Color.black);
        this.theG.drawRect(var3, var4, 100, 20);
        this.theG.drawLine(var3 + 50, var4, var3 + 50, var4 + 20);
        this.theG.setColor(Color.black);
        this.theG.setFont(new Font("TimesRoman", 1, 14));
        VMS.theScheduler.getPID();
        if (VMS.TLB_TAG[var1] == -1) {
            this.theG.drawString("E", var3 + 20, var4 + 15);
            this.theG.drawString("E", var3 + 70, var4 + 15);
        } else {
            Graphics var10000 = this.theG;
            VMS.theScheduler.getPID();
            var10000.drawString(Integer.toString(VMS.TLB_TAG[var1]), var3 + 20, var4 + 15);
            var10000 = this.theG;
            VMS.theScheduler.getPID();
            var10000.drawString(Integer.toString(VMS.TLB_DATA[var1]), var3 + 70, var4 + 15);
        }
    }

    public void displayPTIndex(int var1) {
        int var2 = this.PToX + var1 * 15;
        int var3 = this.PToY + 18;
        this.theG.setColor(Color.black);
        this.theG.setFont(new Font("TimesRoman", 0, 10));
        this.theG.drawString(Integer.toString(var1), var2 + 4, var3 + 35);
    }

    public void displayPT(int var1, boolean var2) {
        int var5 = VMS.theScheduler.getPID();
        int var3 = this.PToX + var1 * 15;
        int var4 = this.PToY;
        if (var2) {
            this.theG.setColor(this.C_HIT);
        } else {
            this.theG.setColor(this.C_PT_BG);
        }

        this.theG.fillRect(var3, var4, 15, 40);
        this.theG.setColor(Color.black);
        this.theG.drawRect(var3, var4, 15, 40);
        this.theG.setColor(Color.black);
        this.theG.setFont(new Font("TimesRoman", 0, 10));
        this.theG.drawString(Integer.toString(VMS.theScheduler.theMMU[var5].PT_STATE[var1]), var3 + 4, var4 + 10);
        this.theG.drawLine(var3, var4 + 15, var3 + 15, var4 + 15);
        if (VMS.theScheduler.theMMU[var5].PT_NUM[var1] == -1) {
            this.theG.drawString("E", var3 + 4, var4 + 35);
        } else {
            this.theG.drawString(Integer.toString(VMS.theScheduler.theMMU[var5].PT_NUM[var1]), var3 + 4, var4 + 35);
        }
    }

    public void displayHitMM(int var1, int var2) {
        int var3 = this.MMoX + var1 % 4 * 25;
        int var4;
        if (var1 == 0) {
            var4 = this.MMoY;
        } else {
            var4 = this.MMoY + var1 / 4 * 25;
        }

        this.theG.setFont(new Font("TimesRoman", 0, 10));

        for(int var5 = 0; var5 < 4; ++var5) {
            this.theG.setColor(Color.black);
            this.theG.drawRect(var3, var4, 25, 25);
            this.theG.setColor(this.C_HIT);
            this.theG.fillRect(var3, var4, 25, 25);
            this.theG.setColor(Color.white);
            this.theG.fillRect(var3, var4, 25, 25);
            this.delay(20L);
            this.theG.setColor(this.C_HIT);
            this.theG.fillRect(var3, var4, 25, 25);
        }

        this.theG.setColor(Color.black);
        this.theG.drawRect(var3, var4, 25, 25);
        this.theG.drawString(Integer.toString(var1), var3 + 4, var4 + 10);
        this.theG.setFont(new Font("TimesRoman", 0, 12));
        if (var2 < 0) {
            this.theG.drawString(" ", var3 + 8, var4 + 21);
        } else {
            this.theG.drawString(Integer.toString(var2), var3 + 8, var4 + 21);
        }
    }

    public void displayMM(int var1, boolean var2, int var3) {
        int var4 = this.MMoX + var1 % 4 * 25;
        int var5;
        if (var1 == 0) {
            var5 = this.MMoY;
        } else {
            var5 = this.MMoY + var1 / 4 * 25;
        }

        this.theG.setFont(new Font("TimesRoman", 0, 10));
        if (var2) {
            this.theG.setColor(this.C_HIT);
        } else {
            this.theG.setColor(this.C_MM_BG);
        }

        this.theG.fillRect(var4, var5, 25, 25);
        this.theG.setColor(Color.black);
        this.theG.drawRect(var4, var5, 25, 25);
        this.theG.drawString(Integer.toString(var1), var4 + 4, var5 + 10);
        this.theG.setFont(new Font("TimesRoman", 0, 12));
        if (var3 < 0) {
            this.theG.drawString(" ", var4 + 8, var5 + 21);
        } else {
            this.theG.drawString(Integer.toString(var3), var4 + 8, var5 + 21);
        }
    }

    public void displayHD() {
        int var1 = this.HDoX;
        int var2 = this.HDoY;
        this.theG.setColor(this.C_HD_BG);
        this.theG.fillRect(var1, var2, 100, 60);
        this.theG.setColor(this.C_HD_LINES);
        this.drawShadow(var1, var2, 100, 60);
        this.theG.setColor(Color.black);
        this.theG.drawRect(var1, var2, 100, 60);
        this.theG.setColor(this.C_HD_LINES);
        this.theG.fillRect(var1 + 1, var2 + 60 - 20, 98, 2);
        this.theG.setColor(Color.white);
        this.theG.fillRect(var1 + 1, var2 + 60 - 20 + 3, 98, 2);
        this.theG.setColor(Color.green);
        this.theG.fillRect(var1 + 80, var2 + 60 - 20, 4, 4);
        this.theG.setColor(Color.red);
        this.theG.fillRect(var1 + 80 + 5, var2 + 60 - 20, 4, 4);
    }

    public void joinKernelToTLB(boolean var1) {
        if (var1) {
            this.theG.setColor(this.C_CONN_ACTIVE);
        } else {
            this.theG.setColor(this.C_CONN_PASSIVE);
        }

        this.theG.fillRect(this.kernelX + 26 - 1, this.kernelY + 25 + 6, 3, 18);
        this.theG.fillRect(this.TLBoX + 25, this.kernelY + 25 + 6 + 18, this.kernelX + 26 - (this.TLBoX + 25) + 2, 3);
        this.theG.fillRect(this.TLBoX + 25, this.kernelY + 25 + 6 + 18, 3, this.TLBoY - (this.kernelY + 25 + 6 + 18) - 2);
    }

    public void joinTLBToPT(boolean var1) {
        if (var1) {
            this.theG.setColor(this.C_CONN_ACTIVE);
        } else {
            this.theG.setColor(this.C_CONN_PASSIVE);
        }

        this.theG.fillRect(this.TLBoX + 25, this.TLBoY + 80, 3, this.PToY - (this.TLBoY + 80) - 2);
    }

    public void joinPTToMM(boolean var1) {
        if (var1) {
            this.theG.setColor(this.C_CONN_ACTIVE);
        } else {
            this.theG.setColor(this.C_CONN_PASSIVE);
        }

        this.theG.fillRect(220, 245, 3, this.PToY - 245 - 2);
        this.theG.fillRect(220, 245, 104, 3);
    }

    public void joinTLBToMM(boolean var1) {
        if (var1) {
            this.theG.setColor(this.C_CONN_ACTIVE);
        } else {
            this.theG.setColor(this.C_CONN_PASSIVE);
        }

        this.theG.fillRect(this.TLBoX + 100 + 2, 280, 37, 3);
        this.theG.fillRect(this.TLBoX + 100 + 2 + 35, 195, 3, 87);
        this.theG.fillRect(this.TLBoX + 100 + 2 + 35, 195, 142, 3);
    }

    public void joinMMToKernel(boolean var1) {
        if (var1) {
            this.theG.setColor(this.C_CONN_ACTIVE);
        } else {
            this.theG.setColor(this.C_CONN_PASSIVE);
        }

        this.theG.fillRect(this.kernelX + 53 + 65, this.kernelY + 25 + 18 + 6, 3, 24);
        this.theG.fillRect(this.kernelX + 53, this.kernelY + 25 + 18 + 6, 65, 3);
        this.theG.fillRect(this.kernelX + 53, this.kernelY + 25 + 6, 3, 18);
    }

    public void joinHDToMM(boolean var1) {
        if (var1) {
            this.theG.setColor(this.C_CONN_ACTIVE);
        } else {
            this.theG.setColor(this.C_CONN_PASSIVE);
        }

        this.theG.fillRect(this.HDoX + 50, this.HDoY - 2 - 35, 3, 35);
    }

    public void joinProcessToKernel(boolean var1, int var2) {
        if (var1) {
            this.theG.setColor(this.C_CONN_ACTIVE);
        } else {
            this.theG.setColor(this.C_CONN_PASSIVE);
        }

        int var3;
        int var4;
        if (var2 == 0) {
            var3 = this.processOriginX + 40 + 4 + 1;
            var4 = this.processOriginY + 25;
            this.theG.fillRect(var3, var4, this.processSeparation / 2, 3);
            this.theG.fillRect(var3 + this.processSeparation / 2, var4, 3, this.kernelY + 12 - var4);
            this.theG.fillRect(var3 + this.processSeparation / 2, this.kernelY + 12, this.kernelX - (var3 + this.processSeparation / 2), 3);
        }

        if (var2 == 1) {
            var3 = this.processOriginX + 80 + this.processSeparation + 4 - 1;
            var4 = this.processOriginY + 25;
            this.theG.fillRect(var3, var4, this.kernelX + 26 - var3, 3);
            this.theG.fillRect(this.kernelX + 26, var4, 3, this.kernelY - var4);
        }

        if (var2 == 2) {
            var3 = this.processOriginX + var2 * (40 + this.processSeparation) - 1;
            var4 = this.processOriginY + 25;
            this.theG.fillRect(this.kernelX + 52, var4, var3 - (this.kernelX + 52), 3);
            this.theG.fillRect(this.kernelX + 52, var4, 3, this.kernelY - var4);
        }

        if (var2 == 3) {
            var3 = this.processOriginX + var2 * (40 + this.processSeparation) - 1;
            var4 = this.processOriginY + 25;
            this.theG.fillRect(var3 - this.processSeparation / 2, var4, this.processSeparation / 2, 3);
            this.theG.fillRect(var3 - this.processSeparation / 2, var4, 3, this.kernelY + 12 - var4);
            this.theG.fillRect(this.kernelX + 80 + 4 + 1, this.kernelY + 12, var3 - this.processSeparation / 2 - (this.kernelX + 80 + 2), 3);
        }

    }

    private void drawShadow(int var1, int var2, int var3, int var4) {
        this.theG.setColor(this.C_SHADOW);
        this.theG.fillRect(var1 + var3 + 1, var2 + 1 + 4, 4, var4);
        this.theG.fillRect(var1 + 1 + 4, var2 + 1 + var4, var3, 4);
    }
}
