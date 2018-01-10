package com.cpm.prog;

import com.cpm.parameters.ParametersSystem;
import com.cpm.parameters.Constant;

import javax.swing.JOptionPane;

public final class License implements Runnable {

    private final ParametersSystem parameters;

    public License() {
        this.parameters = ParametersSystem.getInstance();
    }

    @Override
    public void run() {
        final int option = JOptionPane.showConfirmDialog(
                null,
                Constant.LICENSE_AGREEMENT,
                Constant.LICENSE,
                JOptionPane.YES_NO_OPTION
        );
        if (option == JOptionPane.YES_OPTION) {
            this.parameters.setLicense(true);
        } else if (option == JOptionPane.NO_OPTION) {
            this.parameters.setLicense(false);
        }
    }
}
