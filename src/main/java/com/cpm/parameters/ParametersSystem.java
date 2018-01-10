package com.cpm.parameters;

import com.cpm.prog.Files;

import java.io.IOException;
import java.io.Serializable;

public final class ParametersSystem implements Serializable {

    private static final long serialVersionUID = 1L;
    private static transient String fileName;
    private int number;
    private int accuracy;
    private boolean onTop;
    private boolean autoSave;
    private boolean license;

    private transient static ParametersSystem parameters;

    private ParametersSystem() {
        this.number = Constant.NUMBER_DEFOULT;
        this.accuracy = Constant.ACCURACY_DEFOULT;
    }

    public static ParametersSystem getInstance() {
        if (parameters == null) {
            try {
                parameters = (ParametersSystem) Files.deserialization(fileName);
            } catch (IOException | ClassNotFoundException ex) {
                parameters = new ParametersSystem();
            }
        }
        return parameters;
    }

    @Override
    public String toString() {
        return "number = " + this.number +
                "; \r\naccuracy = " + this.accuracy +
                "; \r\nonTop = " + this.onTop +
                "; \r\navtoSave = " + this.autoSave +
                "; \r\nlicense = " + this.license;
    }

    public static void setFileName(final String fileName) {
        ParametersSystem.fileName = fileName;
    }

    public static String getFileName() {
        return fileName;
    }

    public void setNumber(final int number) {
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    public void setAccuracy(final int accuracy) {
        this.accuracy = accuracy;
    }

    public int getAccuracy() {
        return this.accuracy;
    }

    public void setOnTop(final boolean onTop) {
        this.onTop = onTop;
    }

    public boolean isOnTop() {
        return this.onTop;
    }

    public void setAutoSave(final boolean autoSave) {
        this.autoSave = autoSave;
    }

    public boolean isAutoSave() {
        return this.autoSave;
    }

    public void setLicense(final boolean license) {
        this.license = license;
    }

    public boolean isLicense() {
        return this.license;
    }
}
