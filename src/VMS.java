//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

// Decompiled from*: https://denninginstitute.com/workbenches/vmsim/vm.html
// *is a Java applet, will not work properly on nearly all browsers

import java.applet.Applet;
import java.awt.*;
import javax.swing.JFrame;

public class VMS extends Applet {
    public static MyCanvas theCanvas;
    public Thread schdThread;
    public static Scheduler theScheduler;
    SimulatorDisplayUtils vmsDispUtils;
    private static StatisticPanel statisticResult;
    private static int runningProcess;
    private static int lastRunningProcess;
    private static int vPageFrame;
    private static int tlbHitIndex;
    private static int lastTlbHitIndex;
    private static boolean tlbState;
    private static int curMM;
    private static int lastMM;
    private static int num_Process_Exec;
    private static boolean switchProcess;
    private static boolean updatePT;
    public static int[] TLB_TAG;
    public static int[] TLB_DATA;
    public static int[] TLB;
    public static int TLB_LRU;
    public static int PM_LRU;
    public static int[] PM;
    public int[] PM_IN_TLB;
    public static int[] PM_IN_PT;
    static int gimmePageNum;

    /* https://stackoverflow.com/a/20180873
    /  So it can be compiled for post-Java 11
     */
    public static void main(String[] args) {
        VMS vms = new VMS();
        JFrame VMSFrame = new JFrame("VMSsim");
        VMSFrame.add(vms);
        VMSFrame.pack();
        VMSFrame.setVisible(true);
        VMSFrame.setExtendedState(Frame.MAXIMIZED_BOTH | VMSFrame.getExtendedState());
        VMSFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vms.init();
    }

    protected static void setUpdatePT(boolean var0) {
        updatePT = var0;
    }

    protected static boolean updatePageTable() {
        return updatePT;
    }

    protected static void setSwitchProcess(boolean var0) {
        switchProcess = var0;
    }

    protected static boolean getSwitchProcess() {
        return switchProcess;
    }

    protected static void setCurMM(int var0) {
        curMM = var0;
    }

    protected static int getCurMM() {
        return curMM;
    }

    protected static void setLastMM(int var0) {
        lastMM = var0;
    }

    protected static int getLastMM() {
        return lastMM;
    }

    protected static void setNum_Process_Exec(int var0) {
        num_Process_Exec = var0;
    }

    protected static int getNum_Process_Exec() {
        return num_Process_Exec;
    }

    protected static void setTlbState(boolean var0) {
        tlbState = var0;
    }

    protected static boolean getTlbState() {
        return tlbState;
    }

    protected static void setTlbHitIndex(int var0) {
        tlbHitIndex = var0;
    }

    protected static void setLastTlbHitIndex(int var0) {
        lastTlbHitIndex = var0;
    }

    protected int getTlbHitIndex() {
        return tlbHitIndex;
    }

    protected static int getLastTlbHitIndex() {
        return lastTlbHitIndex;
    }

    protected static void setRunningProc(int var0) {
        runningProcess = var0;
    }

    protected static void setLastRunningProc(int var0) {
        lastRunningProcess = var0;
    }

    protected static int getRunningProc() {
        return runningProcess;
    }

    protected static int getLastRunningProc() {
        return lastRunningProcess;
    }

    protected static void setMMvPageFrame(int var0) {
        vPageFrame = var0;
    }

    protected static int getMMvPageFrame() {
        return vPageFrame;
    }

    protected static int getPMinPT(int var0) {
        return PM_IN_PT[var0];
    }

    protected static int TLBgetLRU() {
        int var0 = TLB[0];

        int var1;
        for(var1 = 1; var1 < 4; ++var1) {
            if (var0 > TLB[var1]) {
                var0 = TLB[var1];
            }
        }

        for(var1 = 0; var0 != TLB[var1]; ++var1) {
        }

        return var1;
    }

    protected static int PMgetLRU() {
        int var0 = PM[0];

        int var1;
        for(var1 = 1; var1 < 16; ++var1) {
            if (var0 > PM[var1]) {
                var0 = PM[var1];
            }
        }

        for(var1 = 0; var0 != PM[var1]; ++var1) {
        }

        return var1;
    }

    protected int TLBgetData(int var1) {
        return TLB_DATA[var1];
    }

    protected int TLBgetEntry(int var1, int var2) {
        return TLB_TAG[var1] == var2 ? TLB_DATA[var1] : -1;
    }

    protected int TLBsetEntry(int var1, int var2) {
        int var3 = TLBgetLRU();
        TLB_TAG[var3] = var1;
        TLB_DATA[var3] = var2;
        return var3;
    }

    protected static void TLBincrLRU(int var0) {
        ++TLB_LRU;
        TLB[var0] = TLB_LRU;
    }

    protected static void PMincrLRU(int var0) {
        ++PM_LRU;
        PM[var0] = PM_LRU;
    }

    protected static void PMresetLRU(int var0) {
        PM[var0] = 0;
    }

    protected static void incr_Reference() {
        statisticResult.references();
    }

    protected static void incr_TLB_Miss() {
        statisticResult.TLB_Misses();
    }

    protected static void incr_PT_Miss() {
        statisticResult.Page_Fault();
    }

    public static void TLBresetLRU() {
        TLB_LRU = 0;

        for(int var0 = 0; var0 < 4; ++var0) {
            TLB_TAG[var0] = -1;
            TLB_DATA[var0] = -1;
            TLB[var0] = 0;
        }

        System.out.println("_____________________________");
    }

    public void init() {
        short var3 = 300;
        short var4 = 200;
        this.setLayout(new BorderLayout());
        statisticResult = new StatisticPanel();
        theCanvas = new MyCanvas(var3, var4);
        this.vmsDispUtils = theCanvas.createDispUtils();
        theScheduler = new Scheduler();
        CNEControls var2 = new CNEControls(theCanvas, theScheduler);
        Controls var1 = new Controls(theCanvas, theScheduler, var2, statisticResult);
        this.add("Center", theCanvas);
        this.add("South", var1);
        this.add("East", statisticResult);
        runningProcess = 0;
        gimmePageNum = -1;
    }

    public static void restartSim() {
        runningProcess = 0;
        gimmePageNum = -1;
        theCanvas.drawAll = -1;
        theCanvas.repaint();
    }

    public static void switchProcess() {
        theCanvas.drawAll = 0;
        theCanvas.repaint();
    }

    public SimulatorDisplayUtils returnDispUtils() {
        return this.vmsDispUtils;
    }

    public void paint() {
    }

    public VMS() {
    }
}
