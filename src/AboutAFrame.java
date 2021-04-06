//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Button;
import java.awt.Event;
import java.awt.Frame;
import java.awt.TextArea;

public class AboutAFrame extends Frame {
    Button close;
    String str = "\n\n\n\tThis applet was implemented by Ngon Tran under\n\tthe supervision of Dr. Daniel A. Menasce' at the\n\tCenter for the New Engineer at George Mason University.\n\n\tSummer 1996.\n\n\tThe initial version of this applet was developed by Avijit Chakraborty\n\tand Joyject Bhowmik, but has been greatly modified by Ngon Tran.";
    TextArea textA;

    AboutAFrame() {
        super("About the Author ");
        this.textA = new TextArea(this.str, 10, 50);
        this.textA.setEditable(false);
        this.close = new Button("Close");
        this.add("Center", this.textA);
        this.add("South", this.close);
    }

    public boolean action(Event var1, Object var2) {
        if (var1.target == this.close) {
            this.hide();
            this.dispose();
            return true;
        } else {
            return false;
        }
    }
}
