package com.cpm.parameters;

public final class Constant {

    public static final int NUMBER_DEFOULT = 6;
    public static final int ACCURACY_DEFOULT = 3;

    public static final String NAME = "CPM";
    public static final String VERSION = "v. 1.0";
    public static final String FILE_RESULT = NAME + "_Result.txt";
    public static final String FILE_MANUAL = "manual.pdf";
    public static final String FILE_SYSTEM = "system.cpm";

    public static final String FONT_NAME = "Times New Roman";
    public static final String MAIN_FRAME_NAME = NAME + ": Cremer-Pople method";

    public static final String ERROR = NAME + ": Error!";
    public static final String WARNING = NAME + ": Warning!";
    public static final String ABOUT = NAME + ": About the program";
    public static final String LICENSE = NAME + ": License agreement";
    public static final String NUMERICAL_VALUE = "Torsion angles should take numerical values!";
    public static final String NUMBERS_DIAPASON = "The number of members in the cycle can be anywhere from 5 to 15!";
    public static final String ACCURACY_DIAPASON = "The order of accuracy can be anywhere from 1 to 5!";
    public static final String ENTER_ANGLES = "Enter the experimental torsion angles!";
    public static final String SAVE_RESULT = "Do you want to save results to file?";
    public static final String FILE_ABSENT = "File is absent!";

    public static final String PROGRAM_ABOUT = NAME + " " + VERSION + "\n"
            + "\nAuthors: Yu. A. Salimov and Yu. G. Vlasenko\n"
            + "\nE-mail:\n"
            + "yuriy.alex.salimov@gmail.com\n"
            + "jgeorge@ukr.net";

    public static final String LICENSE_AGREEMENT = NAME + " " + VERSION + "\n"
            + "Same text\n"
            + "You agree?\n";

    private Constant() {
    }
}
