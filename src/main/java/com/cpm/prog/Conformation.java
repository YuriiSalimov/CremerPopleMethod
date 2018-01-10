package com.cpm.prog;

import com.cpm.parameters.Constant;
import com.cpm.parameters.ParametersSystem;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Math.*;

public final class Conformation {

    private final ParametersSystem parameters;
    private int number;
    private int accuracy;
    private double[] fexp;
    private double[] fcalc;
    private double[] s;
    private double[] p;
    private double amplitude;
    private double theta;
    private double psi;
    private double sigma;
    private String conformation;

    public Conformation() {
        this.parameters = ParametersSystem.getInstance();
        loadProperties();
        newMassiv();
    }

    private void loadProperties() {
        this.number = this.parameters.getNumber();
        this.accuracy = this.parameters.getAccuracy();
    }

    private void newMassiv() {
        this.fexp = new double[this.number];
        this.fcalc = new double[this.number];
        this.s = new double[this.number];
        this.p = new double[this.number];
    }

    public void calculation() {
        torsionAngles();
        conformation();
        sigmaMethod();
        allRounding();
    }

    private void torsionAngles() {
        calculationPS();
        calculationTorsionAngles();
    }

    private void calculationPS() {
        final double number = this.number;
        switch (this.number % 2) {
            case 0:
                for (int i = 2; i <= (this.number / 2 - 1); i++) {
                    double param1 = 0;
                    double param2 = 0;
                    double param3 = 0;
                    for (int j = 1; j <= this.number; j++) {
                        double y = sin(this.fexp[j - 1] * PI / 360);
                        double x = 2 * PI * i * (j - 1) / number;
                        param1 += y * cos(x);
                        param2 += y * sin(x);
                        param3 += y * cos(PI * (j - 1));
                    }
                    param1 *= sqrt(2 / number);
                    param2 *= (-sqrt(2 / number));
                    param3 *= sqrt(1 / number);
                    this.p[i] = atan(param2 / param1) * 180 / PI;
                    this.s[i] = (param1 / cos(p[i] * PI / 180) + param2 / sin(p[i] * PI / 180)) / 2;
                    this.s[this.number / 2] = param3;
                }
                break;
            case 1:
                for (int i = 2; i <= (this.number - 1) / 2; i++) {
                    double param1 = 0;
                    double param2 = 0;
                    for (int j = 1; j <= this.number; j++) {
                        double y = sin(this.fexp[j - 1] * PI / 360);
                        double x = 2 * PI * i * (j - 1) / number;
                        param1 += y * cos(x);
                        param2 -= y * sin(x);
                    }
                    param1 *= sqrt(2 / number);
                    param2 *= sqrt(2 / number);
                    this.p[i] = atan(param2 / param1) * 180 / PI;
                    this.s[i] = param1 / cos(p[i] * PI / 180);
                }
                break;
            default:
                break;
        }
    }

    private void calculationTorsionAngles() {
        final double number = this.number;
        switch (this.number % 2) {
            case 0:
                for (int j = 1; j <= this.number; j++) {
                    double value = 0;
                    for (int i = 2; i <= (this.number / 2 - 1); i++) {
                        value += s[i] * cos(this.p[i] * PI / 180 + 2 * PI * i * (j - 1) / number);
                    }
                    this.fcalc[j - 1] = 2 * (asin(sqrt(2 / number) * value
                            + sqrt(1 / number) * this.s[this.number / 2]
                            * pow(cos(PI * number / 2), 2) * cos(PI * (j - 1))))
                            * 180 / PI;
                }
                break;
            case 1:
                for (int j = 1; j <= this.number; j++) {
                    double value = 0;
                    for (int i = 2; i <= (this.number - 1) / 2; i++) {
                        value += this.s[i] * cos(this.p[i] * PI / 180 + 2 * PI * i * (j - 1) / number);
                    }
                    this.fcalc[j - 1] = 2 * asin(sqrt(2 / number) * value
                            + this.s[(this.number - 1) / 2] * pow(cos(PI * number / 2), 2)
                            * cos(PI * (j - 1))) * 180 / PI;
                }
                break;
            default:
                break;
        }
    }

    private void conformation() {
        this.conformation = "";
        calculationCoordinates();
        if (isFlatCycle(fexp, 1)) {
            this.conformation = "Flat cycle";
        } else {
            if (isFlatCycle(fexp, 10)) {
                this.conformation = "Flat ";
            }
            switch (this.number) {
                case 5: // Для 5-членних
                    this.conformation += getConformationOfFiveMemberedCycle(this.p);
                    break;
                case 6: // Для 6-членних
                    this.conformation += getConformationOfSixMemberedCycle(this.psi, this.theta);
                    break;
                case 8: // Для 8-членних
                    this.conformation += getConformationOfEightMemberedCycle(this.p, this.s);
                    break;
                default:
                    this.conformation = "-";
                    break;
            }
        }
    }

    private static String getConformationOfFiveMemberedCycle(final double[] p) {
        final String conformation;
        double vPsi = abs(p[2]);
        vPsi -= 36 * (int) (vPsi / 36);
        if (vPsi > 9 && vPsi <= 27) {
            conformation = "Konvert";
        } else if ((vPsi >= 0 && vPsi <= 9) || (vPsi > 27 && vPsi <= 36)) {
            conformation = "Tvist-conformation";
        } else {
            conformation = "";
        }
        return conformation;
    }

    private static String getConformationOfSixMemberedCycle(final double psi, final double theta) {
        String conformation = "";
        final double vPsi = psi - 60 * (int) (psi / 60);
        if ((theta >= 0 && theta <= 10) || (theta >= 170 && theta <= 180)) {
            conformation = "Chair"; // Крісло.
        } else if (theta >= 85 && theta <= 95) {
            if ((vPsi >= 0 && vPsi <= 15) || (vPsi >= 45 && vPsi <= 60)) {
                conformation = "Tvist-bath"; // Твіст-ванна.
            } else if (vPsi > 15 && vPsi < 45) {
                conformation = "Proper bath";  // Правильна ванна.
            }
        } else if ((theta > 10 && theta < 85) || (theta > 95 && theta < 170)) {
            if ((vPsi >= 0 && vPsi <= 15) || (vPsi >= 45 && vPsi <= 60)) {
                conformation = "Half-chair"; // Напівкрісло.
            } else if (vPsi > 15 && vPsi < 45) {
                conformation = "Half-bath"; // Напівванна.
            }
        }
        return conformation;
    }

    private static String getConformationOfEightMemberedCycle(final double[] p, final double[] s) {
        String conformation = "";
        if ((s[2] >= -0.001) && (s[2] <= 0.001) && (s[3] >= -0.001) && (s[3] <= 0.001) && (s[4] != 0)) {
            conformation = "Crown"; // Корона.
        } else if ((s[3] >= -0.001) && (s[3] <= 0.001) && (s[4] >= -0.001) && (s[4] <= 0.001) && (s[2] != 0)) {
            final double vPsi = p[2] - 90 * (int) (p[2] / 90);
            if ((vPsi < 22.5) || (vPsi > 68.5)) {
                conformation = "Bath"; // Ванна.
            } else if ((vPsi >= 22.5) || (vPsi <= 68.5)) {
                conformation = "Bath-bath"; // Ванна-ванна.
            }
        } else if ((s[2] >= -0.001) && (s[2] <= 0.001) && (s[4] >= -0.001) && (s[4] <= 0.001) && (s[3] != 0)) {
            final double vPsi = p[3] - 45 * (int) (p[3] / 45);
            if ((vPsi < 11.25) || (vPsi > 33.75)) {
                conformation = "Chair."; // Крісло.
            } else if ((vPsi >= 11.25) || (vPsi <= 33.75)) {
                conformation = "Long chair"; // Довге крісло.
            }
        }
        return conformation;
    }

    private static boolean isFlatCycle(final double[] angles, final double etalonAngle) {
        int counter = 0;
        for (double angle : angles) {
            if (abs(angle) <= etalonAngle) {
                counter++;
            }
        }
        return (counter >= angles.length);
    }

    private void calculationCoordinates() {
        if (this.number == 6) {
            this.theta = atan(this.s[2] / this.s[3]) * 180 / PI;
            this.psi = this.p[2];
            checkAngles();
            this.amplitude = 0;
            for (double angle : this.fexp) {
                this.amplitude += pow(sin(angle * PI / 360), 2);
            }
            this.amplitude = (abs(sqrt(this.amplitude)) +
                    abs(this.s[2] / sin(this.theta * PI / 180)) +
                    abs(this.s[3] / cos(this.theta * PI / 180))) / 3;
        } else {
            this.amplitude = 0;
            this.theta = 0;
            this.psi = 0;
        }
    }

    private void checkAngles() {
        if (this.theta < 0) {
            this.theta = abs(theta);
        } else if (this.theta > 180) {
            this.theta = 360 - this.theta;
        }
        if (this.psi < 0) {
            this.psi = this.psi + 360;
        } else if (psi > 360) {
            this.psi -= 360 * (int) (psi / 360);
        }
    }

    private void sigmaMethod() {
        this.sigma = 0;
        for (int i = 0; i < this.number; i++) {
            this.sigma += pow(this.fexp[i] - this.fcalc[i], 2);
        }
        final double number = this.number;
        this.sigma = sqrt(this.sigma / (number - 1));
    }

    private void allRounding() {
        for (int i = 0; i < this.number; i++) {
            this.fcalc[i] = rounding(this.fcalc[i], this.accuracy);
        }
        final double parameter = this.number - (this.number / 2) * 2;
        if (parameter == 0) { // Для парних
            for (int i = 2; i <= (this.number / 2 - 1); i++) {
                this.p[i] = rounding(this.p[i], this.accuracy);
                this.s[i] = rounding(this.s[i], this.accuracy);
            }
            this.s[this.number / 2] = rounding(this.s[this.number / 2], this.accuracy);
        } else if (parameter == 1) { // Для не парних
            for (int i = 2; i <= (this.number - 1) / 2; i++) {
                this.p[i] = rounding(this.p[i], this.accuracy);
                this.s[i] = rounding(this.s[i], this.accuracy);
            }
        }
        this.sigma = rounding(this.sigma, this.accuracy);
        if (this.number == 6) {
            this.amplitude = rounding(this.amplitude, this.accuracy);
            this.theta = rounding(this.theta, this.accuracy);
            this.psi = rounding(this.psi, this.accuracy);
        }
    }

    private static double rounding(final double value, final int r) {
        final int k = (int) pow(10, r);
        double x = abs(value) * k;
        double y = (int) (x);
        if ((x - y) >= 0.5) {
            y += 1;
        }
        x = (value < 0) ? -y / k : y / k;
        return x;
    }

    public void save() throws IOException {
        Files.write(Constant.FILE_RESULT, toString());
    }

    public void saveAs(final String path) throws IOException {
        Files.write(path, toString());
    }

    @Override
    public String toString() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy  hh:mm:ss a");
        final StringBuilder sb = new StringBuilder("Yu. A. Salimov, Yu. G. Vlasenko, CPM v.1\r\n");
        sb.append("\r\n       Cremer-Pople Method\r\n")
                .append("\r\nDate: ").append(dateFormat.format(new Date()))
                .append("\r\n\r\nThe number of members in the cycle = ").append(this.number)
                .append("\r\nThe order of accuracy in calculations = ").append(this.accuracy)
                .append("\r\n\r\nTorsion angles (d):")
                .append("\r\nexperimental   calculated");

        for (int i = 0; i < this.number; i++) {
            sb.append("\r\n   ").append(this.fexp[i]).append("   \t").append(this.fcalc[i]);
        }

        sb.append("\r\n\r\nCoordinates of folding:");
        if (this.number % 2 == 0) { // Для парних
            for (int i = 2; i <= (this.number / 2 - 1); i++) {
                sb.append("\r\nP(").append(i).append(") = ").append(p[i]).append(" d");
            }
            for (int i = 2; i <= (this.number / 2 - 1); i++) {
                sb.append("\r\nS(").append(i).append(") = ").append(s[i]);
            }
            sb.append("\r\nS(").append(this.number / 2).append(") = ").append(s[this.number / 2]);
        } else if (this.number % 2 == 1) { // Для не парних
            for (int i = 2; i <= (this.number - 1) / 2; i++) {
                sb.append("\r\nP(").append(i).append(") = ").append(p[i]).append(" d");
            }
            for (int i = 2; i <= (this.number - 1) / 2; i++) {
                sb.append("\r\nS(").append(i).append(") = ").append(s[i]);
            }
        }
        if (this.number == 6) {
            sb.append("\r\nThe modified parameters of the Cremer-Pople:")
                    .append("\r\nAmplitude = ").append(this.amplitude)
                    .append("\r\nTheta = ").append(this.theta).append(" d")
                    .append("\r\nPsi = ").append(this.psi).append(" d");
        }
        sb.append("\r\n\r\nConformation - ").append(this.conformation)
                .append("\r\nSigma = ").append(this.sigma).append(" d")
                .append("\r\n\r\n! d - degree");
        return sb.toString();
    }

    public void setNumber(final int number) {
        this.number = number;
        newMassiv();
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

    public void setFexps(final double[] fexp) {
        this.fexp = fexp;
    }

    public double[] getFexps() {
        return this.fexp;
    }

    public void setFcalcs(final double[] fcalc) {
        this.fcalc = fcalc;
    }

    public double[] getFcalcs() {
        return this.fcalc;
    }

    public void setFexp(final double value, final int i) {
        this.fexp[i] = value;
    }

    public double getFexp(final int i) {
        return this.fexp[i];
    }

    public double getFcalc(final int i) {
        return this.fcalc[i];
    }

    public double getS(final int i) {
        return this.s[i];
    }

    public double getP(final int i) {
        return this.p[i];
    }

    public double getAmplitude() {
        return this.amplitude;
    }

    public double getTheta() {
        return this.theta;
    }

    public double getPsi() {
        return this.psi;
    }

    public double getSigma() {
        return this.sigma;
    }

    public String getConformation() {
        return this.conformation;
    }
}
