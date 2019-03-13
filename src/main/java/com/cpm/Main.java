package com.cpm;

import com.cpm.parameters.Constant;
import com.cpm.parameters.ParametersSystem;
import com.cpm.prog.Conformation;
import com.cpm.prog.Program;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        newApplication();
    }

    private static void newApplication() {
        ParametersSystem.setFileName(Constant.FILE_SYSTEM);
        /*final ParametersSystem parameters = ParametersSystem.getInstance();
        try {
            if (!parameters.isLicense()) {
                final Thread thread = new Thread(new License());
                thread.start();
                thread.join();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }*/
        new Thread(new Program(new Conformation())).start();
    }
}
