package com.cpm.parameters;

public final class Constant {

    public static final int NUMBER_DEFOULT = 6;
    public static final int ACCURACY_DEFOULT = 3;

    public static final String PROG_NAME = "CPM";
    public static final String VERSION = "v. 2.0";
    public static final String METHOD_NAME = "Cremer-Pople method";
    public static final String FILE_RESULT = PROG_NAME + "_Result.txt";
    public static final String FILE_MANUAL = "manual.pdf";
    public static final String FILE_SYSTEM = "system.cpm";

    public static final String FONT_NAME = "Times New Roman";
    public static final String MAIN_FRAME_NAME = PROG_NAME + ": " + METHOD_NAME;

    public static final String ERROR = PROG_NAME + ": Error!";
    public static final String WARNING = PROG_NAME + ": Warning!";
    public static final String ABOUT = PROG_NAME + ": About the program";
    public static final String LICENSE = PROG_NAME + ": License agreement";
    public static final String NUMERICAL_VALUE = "Torsion angles should take numerical values!";
    public static final String NUMBERS_DIAPASON = "The number of members in the cycle can be anywhere from 5 to 15!";
    public static final String ACCURACY_DIAPASON = "The order of accuracy can be anywhere from 1 to 5!";
    public static final String ENTER_ANGLES = "Enter the experimental torsion angles!";
    public static final String SAVE_RESULT = "Do you want to save results to file?";
    public static final String FILE_ABSENT = "File is absent!";

    public static final String AUTHOR_NAMES = "Yu. A. Salimov";
    public static final String AUTHOR_EMAILS = "yuriy.alex.salimov@gmail.com";

    public static final String PROGRAM_ABOUT = PROG_NAME + " " + VERSION + "\n"
            + "\nAuthor: " + AUTHOR_NAMES + "\n"
            + "\nE-mail:\n" + AUTHOR_EMAILS;

    public static final String LICENSE_AGREEMENT = PROG_NAME + " " + VERSION + "\n"
            + "Non-commercial use.\n"
            + "You agree?\n";

    public static final String DATE_PATTERN = "dd.MM.yyyy  hh:mm:ss a";

    private Constant() {
    }
}
