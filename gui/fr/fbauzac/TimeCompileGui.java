package fr.fbauzac;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public final class TimeCompileGui {

    public static void main(String[] args) {
	TimeCompileGui.run();
    }

    private static void run() {
	selectNimbusIfAvailable();
	new TimeCompileMainFrame();
    }

    private static void selectNimbusIfAvailable() {
	try {
	    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		if ("Nimbus".equals(info.getName())) {
		    UIManager.setLookAndFeel(info.getClassName());
		    break;
		}
	    }
	} catch (Exception e) {
	    // If Nimbus is not available, you can set the GUI to another look
	    // and feel.
	    System.out.println("Warning: Nimbus not found");
	}
    }

}
