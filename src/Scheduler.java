//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class Scheduler extends SimulatorDisplayUtils implements Runnable {
    private int pid;
    public static Thread schdThread;
    public static int slider_num_one = 1000;
    public static int slider_num_two = 500;
    public MMU[] theMMU = new MMU[4];

    public int getPID() {
        return this.pid;
    }

    public Scheduler() {
        for(int var1 = 0; var1 < 4; ++var1) {
            this.theMMU[var1] = new MMU(32, 16, 4);
        }

        this.pid = 0;
    }

    public void start() {
        if (schdThread == null) {
            schdThread = new Thread(this);
            schdThread.start();
        }

        schdThread.resume();
    }

    public void run() {
        this.pid = 0;
        VMS.setLastRunningProc(this.pid);
        VMS.setRunningProc(this.pid);
        int var1 = RandomNumGenerate(2) * 2 + 4;
        this.theMMU[this.pid].setPageState(false);

        while(schdThread != null) {
            VMS.setNum_Process_Exec(var1);
            VMS.setSwitchProcess(true);

            for(int var2 = 0; var2 < var1; ++var2) {
                this.Process(this.pid);

                try {
                    Thread.sleep((long)slider_num_one);
                } catch (InterruptedException var3) {
                }
            }

            VMS.setLastRunningProc(this.pid);
            this.pid = (this.pid + 1) % 4;
            VMS.setRunningProc(this.pid);
            this.theMMU[this.pid].setPageState(false);
            MMU.counter = 0;
            VMS.TLBresetLRU();
            var1 = RandomNumGenerate(2) * 2 + 4;
        }

        schdThread = null;
    }

    public void stop() {
        if (schdThread != null) {
            schdThread.suspend();
        }

    }

    public void resetSim() {
        if (schdThread != null) {
            schdThread.stop();
        }

        schdThread = null;
        VMS.restartSim();

        for(int var1 = 0; var1 < 4; ++var1) {
            this.theMMU[var1].reset();
            MMU.counter = 0;
        }

    }

    public void Process(int var1) {
        boolean var6 = true;
        VMS.gimmePageNum = RandomNumGenerate(32);
        VMS.incr_Reference();
        int var2 = this.theMMU[var1].virtualToPhysical(VMS.gimmePageNum);
        if (var2 == 2) {
            int var7 = VMS.getCurMM();
            int var5 = VMS.getPMinPT(var7);
            boolean var3;
            if (var5 == var1) {
                var3 = true;
            } else {
                var3 = false;
            }

            if (var5 != -1) {
                this.theMMU[var5].dumpPT(var7, var3);
            }

            this.theMMU[var1].swapIn(VMS.gimmePageNum);
            VMS.PM_IN_PT[var7] = var1;
            VMS.theCanvas.display();
        }

    }

    public static int RandomNumGenerate(int var0) {
        double var1 = Math.random();
        int var3 = (int)(var1 * 1000.0D);
        return var3 % var0;
    }
}
