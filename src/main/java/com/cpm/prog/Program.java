package com.cpm.prog;

import com.cpm.gui.UserInterface;
import com.cpm.parameters.Constant;
import com.cpm.parameters.ParametersSystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public final class Program extends UserInterface implements Runnable, ActionListener {

    private String fileName;

    public Program(final Conformation conformation) {
        this.conformation = conformation;
        this.parameters = ParametersSystem.getInstance();
        this.fileName = Constant.FILE_RESULT;
        init();
    }

    private void init() {
        this.width = 595;
        this.height = 115 + 30 * this.conformation.getNumber();
    }

    @Override
    public void run() {
        createMainFrame();
        createTorsionAngles();
        createMPCP();
        createButtons();
        createMenuBar();
        setEnabled();
    }

    private void setEnabled() {
        final File file = new File(Constant.FILE_RESULT);
        if (file.exists() && file.isFile()) {
            this.bOpen.setEnabled(true);
            this.miOpen.setEnabled(true);
        } else {
            this.bOpen.setEnabled(false);
            this.miOpen.setEnabled(false);
        }
        this.mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(final ActionEvent evt) {
        final AbstractButton bID = (AbstractButton) evt.getSource();
        if (bID == this.bCalculation || bID == this.miCalculation) {
            calculation();
        } else if (bID == this.bSave || bID == this.miSave) {
            saveData();
        } else if (bID == this.miSaveAs) {
            saveDataAs();
        } else if (bID == this.bOpen || bID == this.miOpen) {
            openDataFile();
        } else if (bID == this.bClean || bID == this.miClean) {
            cleanData();
        } else if (bID == this.bExit || bID == this.miExit) {
            exitProgram();
        } else if (bID == this.miProperties) {
            showProperties();
        } else if (bID == this.miLicense) {
            final Thread thread = new Thread(new License());
            thread.start();
        } else if (bID == this.miManual) {
            openManual();
        } else if (bID == this.miAbout) {
            showMessage(1, null);
        } else if (bID == this.bOk) {
            saveProperties(true);
        } else if (bID == this.bCancel) {
            saveProperties(false);
        }
        this.mainPanel.setFocusable(true);
    }

    private void calculation() {
        if (this.conformation != null) {
            int check = 0;
            for (int i = 0; i < this.conformation.getNumber(); i++) {
                try {
                    String value = this.tFexp[i].getText();
                    if (value.equals("")) {
                        check = 5;
                        break;
                    } else {
                        value = value.replace(",", ".")
                                .replace(" ", "")
                                .replace("º", "");
                        this.conformation.setFexp(Double.parseDouble(value), i);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    check = 2;
                    break;
                }
            }
            if (check != 0) {
                showMessage(check, null);
            } else {
                this.conformation.calculation();
                result();
                this.bSave.setEnabled(true);
                this.miSave.setEnabled(true);
                this.miSaveAs.setEnabled(true);
            }
        }
    }

    private void result() {
        for (int i = 0; i < this.conformation.getNumber(); i++) {
            this.tFexp[i].setText(this.conformation.getFexp(i) + " º");
            this.tFcalc[i].setText(this.conformation.getFcalc(i) + " º");
        }
        this.tAmplitude.setText(Double.toString(this.conformation.getAmplitude()));
        this.tTheta.setText(this.conformation.getTheta() + " º");
        this.tPsi.setText(this.conformation.getPsi() + " º");
        this.tConformation.setText(this.conformation.getConformation());
        this.tSigma.setText(this.conformation.getSigma() + " º");
        int param1 = 0;
        double param2 = this.conformation.getNumber() - (this.conformation.getNumber() / 2) * 2;
        if (param2 == 0) { //Для парних
            param1 = this.conformation.getNumber() / 2 - 1;
            this.tS[this.conformation.getNumber() / 2].setText(
                    Double.toString(this.conformation.getS(this.conformation.getNumber() / 2))
            );
        } else if (param2 == 1) {
            param1 = (this.conformation.getNumber() - 1) / 2;  //Для не парних
        }
        for (int i = 2; i <= param1; i++) {
            this.tP[i].setText(this.conformation.getP(i) + " º");
            this.tS[i].setText(Double.toString(this.conformation.getS(i)));
        }
    }

    private void saveData() {
        try {
            this.conformation.save();
            this.fileName = Constant.FILE_RESULT;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.bOpen.setEnabled(true);
        this.miOpen.setEnabled(true);
        this.bSave.setEnabled(false);
        this.miSave.setEnabled(false);
    }

    private void saveDataAs() {
        final JFileChooser dialog = new JFileChooser(".");
        dialog.setSelectedFile(new File(Constant.FILE_RESULT));
        if (dialog.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            final File file = dialog.getSelectedFile();
            try {
                this.fileName = file.getAbsolutePath();
                this.conformation.saveAs(this.fileName);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            this.bOpen.setEnabled(true);
            this.bSave.setEnabled(false);
            this.miSave.setEnabled(false);
        }
    }

    private void openDataFile() {
        final int check = Files.open(this.fileName);
        if (check != 0) {
            showMessage(check, this.fileName);
        }
    }

    private void cleanData() {
        for (int i = 0; i < this.conformation.getNumber(); i++) {
            this.tFexp[i].getText();
            this.tFexp[i].setText(null);
            this.tFcalc[i].setText(null);
        }
        this.tAmplitude.setText(null);
        this.tTheta.setText(null);
        this.tPsi.setText(null);
        this.tConformation.setText(null);
        this.tSigma.setText(null);
        if ((this.conformation.getNumber() - (this.conformation.getNumber() / 2) * 2) == 0) {
            final int number = this.conformation.getNumber() / 2 - 1;
            for (int i = 2; i <= number; i++) {
                this.tP[i].setText(null);
                this.tS[i].setText(null);
            }
            this.tS[this.conformation.getNumber() / 2].setText(null);
        }
        int number = 0;
        double parameter = this.conformation.getNumber() - (this.conformation.getNumber() / 2) * 2;
        if (parameter == 0) { //Для парних
            number = this.conformation.getNumber() / 2 - 1;
            this.tS[this.conformation.getNumber() / 2].setText(null);
        } else if (parameter == 1) {
            number = (this.conformation.getNumber() - 1) / 2;  //Для не парних
        }
        for (int i = 2; i <= number; i++) {
            this.tP[i].setText(null);
            this.tS[i].setText(null);
        }
        this.bSave.setEnabled(false);
        this.miSave.setEnabled(false);
        this.miSaveAs.setEnabled(false);
    }

    private void exitProgram() {
        if (this.parameters.isAutoSave() && this.bSave.isEnabled()) {
            try {
                this.conformation.save();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (this.bSave.isEnabled()) {
            showMessage(6, null);
        }
        try {
            saveProp();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.exit(0);
    }

    private void saveProp() throws IOException {
        Files.serialization(Constant.FILE_SYSTEM, parameters);
    }

    private void showProperties() {
        propertiesFrame();
        this.tNumber.setText(Integer.toString(this.conformation.getNumber()));
        this.tAccuracy.setText(Integer.toString(this.conformation.getAccuracy()));
        this.mainFrame.setEnabled(false);
        this.frameProperties.setEnabled(true);
    }

    private void openManual() {
        final int check = Files.open(Constant.FILE_MANUAL);
        if (check != 0) {
            showMessage(check, Constant.FILE_MANUAL);
        }
    }

    private void saveProperties(final boolean checked) {
        if (checked) {
            int number;
            int accuracy;
            int check = -1;
            try {
                check = 3;
                final String numberString = this.tNumber.getText().replace(",", ".");
                number = (int) Double.parseDouble(numberString);
                check = 4;
                final String accuracyString = this.tAccuracy.getText().replace(",", ".");
                accuracy = (int) Double.parseDouble(accuracyString);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                showMessage(check, null);
                return;
            }
            this.mainFrame.setAlwaysOnTop(this.checkOnTop.isSelected());
            this.frameProperties.setAlwaysOnTop(this.checkOnTop.isSelected());
            this.parameters.setOnTop(this.mainFrame.isAlwaysOnTop());
            this.parameters.setAutoSave(this.checkAutoSave.isSelected());
            if (number < 5 || number > 15) {
                showMessage(3, null);
            } else if (accuracy < 1 || accuracy > 5) {
                showMessage(4, null);
            } else {
                if (this.bSave.isEnabled() && !this.parameters.isAutoSave()) {
                    showMessage(6, null);
                }
                if (accuracy != this.conformation.getAccuracy()) {
                    this.parameters.setAccuracy(accuracy);
                    this.conformation.setAccuracy(accuracy);
                }
                if (number != this.conformation.getNumber()) {
                    this.parameters.setNumber(number);
                    this.conformation.setNumber(number);
                    this.mainFrame.hide();
                    init();
                    run();
                }
                this.mainFrame.setEnabled(true);
                this.frameProperties.setEnabled(false);
                this.frameProperties.hide();
            }
        } else {
            this.mainFrame.setEnabled(true);
            this.frameProperties.setEnabled(false);
            this.frameProperties.hide();
        }
    }
}

/*
 Если что, это - не я!
 */
