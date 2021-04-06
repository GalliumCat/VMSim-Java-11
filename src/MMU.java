//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class MMU extends VMS {
    public int[] PT_STATE;
    public int[] PT_NUM;
    public static int counter;
    int gimmePageNum = -1;
    private int vmPages;
    private int pmPages;
    private int tlbEntries;
    private int vAddr;
    private int pAddr;
    private int pmHitIndex;
    private int pmSwapIndex;
    private boolean pageState;
    private boolean pageTableState;
    private int curPTI;
    private int lastPTI;

    public int getVAddr() {
        return this.vAddr;
    }

    public int getPAddr() {
        return this.pAddr;
    }

    public void setPageState(boolean var1) {
        this.pageState = var1;
    }

    public boolean getPageState() {
        return this.pageState;
    }

    public boolean getPtState() {
        return this.pageTableState;
    }

    public int getCurPTI() {
        return this.curPTI;
    }

    public int getLastPTI() {
        return this.lastPTI;
    }

    public MMU(int var1, int var2, int var3) {
        this.vmPages = var1;
        this.pmPages = var2;
        this.tlbEntries = var3;
        VMS.TLB_TAG = new int[var3];
        VMS.TLB_DATA = new int[var3];
        VMS.TLB = new int[var3];
        VMS.TLB_LRU = 0;

        int var4;
        for(var4 = 0; var4 < var3; ++var4) {
            VMS.TLB_TAG[var4] = -1;
            VMS.TLB_DATA[var4] = -1;
            VMS.TLB[var4] = 0;
        }

        this.PT_STATE = new int[var1];
        this.PT_NUM = new int[var1];

        for(var4 = 0; var4 < var1; ++var4) {
            this.PT_STATE[var4] = 0;
            this.PT_NUM[var4] = -1;
        }

        VMS.PM_LRU = 0;
        super.PM_IN_TLB = new int[var2];
        VMS.PM_IN_PT = new int[var2];
        VMS.PM = new int[var2];

        for(var4 = 0; var4 < var2; ++var4) {
            super.PM_IN_TLB[var4] = -1;
            VMS.PM_IN_PT[var4] = -1;
            VMS.PM[var4] = 0;
        }

    }

    public void reset() {
        VMS.TLB_LRU = 0;
        VMS.PM_LRU = 0;
        counter = 0;

        int var1;
        for(var1 = 0; var1 < VMS.TLB_TAG.length; ++var1) {
            VMS.TLB_TAG[var1] = -1;
            VMS.TLB_DATA[var1] = -1;
            VMS.TLB[var1] = 0;
        }

        for(var1 = 0; var1 < this.PT_STATE.length; ++var1) {
            this.PT_STATE[var1] = 0;
            this.PT_NUM[var1] = -1;
        }

        for(var1 = 0; var1 < VMS.PM_IN_PT.length; ++var1) {
            super.PM_IN_TLB[var1] = -1;
            VMS.PM_IN_PT[var1] = -1;
            VMS.PM[var1] = 0;
        }

    }

    public int virtualToPhysical(int var1) {
        int var2 = VMS.PMgetLRU();
        int var4 = -1;
        boolean var3 = false;
        if (VMS.getTlbState()) {
            VMS.setLastTlbHitIndex(this.getTlbHitIndex());
        }

        if (this.getPtState()) {
            this.lastPTI = this.getCurPTI();
        }

        for(int var5 = 0; var5 < this.tlbEntries; ++var5) {
            var2 = this.TLBgetEntry(var5, var1);
            if (var2 != -1) {
                var3 = true;
                var4 = var5;
                break;
            }
        }

        VMS.setSwitchProcess(false);
        if (counter % VMS.getNum_Process_Exec() == 0) {
            VMS.setSwitchProcess(true);
        }

        ++counter;
        if (var3) {
            VMS.setTlbState(true);
            VMS.setTlbHitIndex(var4);
            VMS.setCurMM(var2);
            VMS.setMMvPageFrame(var1);
            VMS.theCanvas.display();
            VMS.TLBincrLRU(var4);
            VMS.PMincrLRU(var2);
            return 0;
        } else {
            VMS.setTlbState(false);
            VMS.theCanvas.display();
            VMS.incr_TLB_Miss();
            var2 = this.PTgetEntry(var1, var2);
            if (var2 != -1) {
                this.pageTableState = true;
                this.pAddr = var2;
                this.vAddr = var1;
                this.curPTI = var1;
                VMS.setCurMM(var2);
                VMS.setMMvPageFrame(var1);
                VMS.PMincrLRU(var2);
                VMS.theCanvas.display();
                var4 = this.TLBsetEntry(var1, var2);
                VMS.TLBincrLRU(var4);
                VMS.theCanvas.display();
                return 1;
            } else {
                this.pageTableState = false;
                VMS.setTlbState(false);
                VMS.setCurMM(VMS.PMgetLRU());
                this.curPTI = var1;
                VMS.incr_PT_Miss();
                return 2;
            }
        }
    }

    public void swapIn(int var1) {
        VMS.theCanvas.display();
        int var2 = VMS.PMgetLRU();
        int var3 = VMS.TLBgetLRU();
        this.TLBsetEntry(var1, var2);
        VMS.setTlbHitIndex(var3);
        VMS.TLBincrLRU(var3);
        VMS.theCanvas.display();
        this.pageTableState = false;
        this.curPTI = var1;
        this.PTsetEntry(var1, var2);
        VMS.PMincrLRU(var2);
        VMS.setCurMM(var2);
        VMS.setMMvPageFrame(var1);
        VMS.theCanvas.display();
    }

    private void PTsetEntry(int var1, int var2) {
        this.PT_STATE[var1] = 1;
        this.PT_NUM[var1] = var2;
    }

    private int PTgetEntry(int var1, int var2) {
        var2 = this.PT_NUM[var1];
        return this.PT_STATE[var1] == 1 ? var2 : -1;
    }

    public void dumpPT(int var1, boolean var2) {
        int var3;
        for(var3 = 0; var3 < this.vmPages; ++var3) {
            if (this.PT_NUM[var3] == var1 && this.PT_STATE[var3] == 1) {
                this.PT_NUM[var3] = -1;
                this.PT_STATE[var3] = 0;
                break;
            }
        }

        if (var2) {
            this.pageTableState = false;
            this.curPTI = var3;
            VMS.setUpdatePT(true);
            VMS.theCanvas.updatePTonly();
        }

    }
}
