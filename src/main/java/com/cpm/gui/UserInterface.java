package com.cpm.gui;

import com.cpm.prog.Conformation;
import com.cpm.parameters.Constant;
import com.cpm.parameters.ParametersSystem;
import com.cpm.prog.Program;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public abstract class UserInterface extends JFrame implements ActionListener {

    private static final Cursor CURSOR = new Cursor(Cursor.HAND_CURSOR);

    protected int width;
    protected int height;

    protected JPanel mainPanel;
    protected JFrame mainFrame;
    protected JFrame frameProperties;

    protected JButton bCalculation;
    protected JButton bSave;
    protected JButton bOpen;
    protected JButton bClean;
    protected JButton bExit;
    protected JButton bOk;
    protected JButton bCancel;

    protected JMenuItem miCalculation;
    protected JMenuItem miSave;
    protected JMenuItem miSaveAs;
    protected JMenuItem miOpen;
    protected JMenuItem miClean;
    protected JMenuItem miExit;
    protected JMenuItem miProperties;
    protected JMenuItem miLicense;
    protected JMenuItem miAbout;
    protected JMenuItem miManual;

    protected JTextField[] tFexp;
    protected JTextField[] tFcalc;
    protected JTextField[] tP;
    protected JTextField[] tS;
    protected JTextField tNumber;
    protected JTextField tAccuracy;
    protected JTextField tAmplitude;
    protected JTextField tTheta;
    protected JTextField tPsi;
    protected JTextField tSigma;
    protected JTextField tConformation;

    protected JCheckBox checkOnTop;
    protected JCheckBox checkAutoSave;

    protected ParametersSystem parameters;
    protected Conformation conformation;

    protected void createMainFrame() {
        this.mainPanel = createPanel();
        this.mainFrame = createFrame(
                this.mainPanel,
                Constant.MAIN_FRAME_NAME,
                Toolkit.getDefaultToolkit()
                        .getImage(
                                this.getClass().getClassLoader()
                                .getResource("images/icon.gif")
                        ),
                JFrame.EXIT_ON_CLOSE,
                false,
                this.parameters.isOnTop(),
                false,
                this.width,
                this.height
        );
        this.mainFrame.setFocusable(true);
    }

    protected void createTorsionAngles() {
        createLabel(this.mainPanel, "Torsion angles:", 16, Font.BOLD, 75, 25, 115, 20);
        this.tFexp = new JTextField[this.parameters.getNumber()];
        this.tFcalc = new JTextField[this.parameters.getNumber()];
        for (int i = 0; i < this.parameters.getNumber(); i++) {
            createLabel(this.mainPanel, "φ     = ", 17, Font.BOLD | Font.ITALIC, 10, 30 * i + 50, 47, 22);
            createLabel(this.mainPanel, "exp", 13, Font.BOLD, 20, 30 * i + 43, 30, 20);
            createLabel(this.mainPanel, "" + (i + 1), 13, Font.BOLD, 20, 30 * i + 60, 20, 20);
            this.tFexp[i] = createTextField(this.mainPanel, "", 15, 57, 30 * i + 50, 65, 22, color(255, 255, 180), true);

            createLabel(this.mainPanel, "φ     = ", 17, Font.BOLD | Font.ITALIC, 130, 30 * i + 50, 47, 22);
            createLabel(this.mainPanel, "calc", 13, Font.BOLD, 140, 30 * i + 43, 30, 20);
            createLabel(this.mainPanel, "" + (i + 1), 13, Font.BOLD, 140, 30 * i + 60, 20, 20);
            this.tFcalc[i] = createTextField(this.mainPanel, "", 15, 177, 30 * i + 50, 80, 22, color(255, 255, 255), false);
        }
    }

    protected void createMPCP() {
        createLabel(this.mainPanel, "The modified parameters of the Cremer-Pople:", 16, Font.BOLD, 265, 25, 320, 20);

        createLabel(this.mainPanel, "S = ", 16, Font.BOLD | Font.ITALIC, 275, 50, 30, 22);
        this.tAmplitude = createTextField(this.mainPanel, "", 16, 303, 50, 93, 22, color(255, 255, 255), false);

        createLabel(this.mainPanel, "θ = ", 16, Font.BOLD | Font.ITALIC, 275, 80, 30, 22);
        this.tTheta = createTextField(mainPanel, "", 16, 303, 80, 93, 22, color(255, 255, 255), false);

        createLabel(this.mainPanel, "ψ = ", 16, Font.BOLD | Font.ITALIC, 275, 110, 30, 22);
        this.tPsi = createTextField(this.mainPanel, "", 16, 303, 110, 93, 22, color(255, 255, 255), false);

        createLabel(this.mainPanel, "σ = ", 16, Font.BOLD | Font.ITALIC, 423, 110, 30, 22);
        this.tSigma = createTextField(this.mainPanel, "", 16, 452, 110, 93, 22, color(255, 255, 255), false);

        createLabel(this.mainPanel, "Conformation:", 15, Font.BOLD, 440, 50, 100, 22);
        this.tConformation = createTextField(this.mainPanel, "", 15, 405, 80, 165, 22, color(255, 255, 255), false);

        createLabel(this.mainPanel, "Coordinates of folding:", 16, Font.BOLD, 335, 145, 160, 20);

        this.tP = new JTextField[this.parameters.getNumber()];
        this.tS = new JTextField[this.parameters.getNumber()];
        int number = 0;
        double parametr = this.parameters.getNumber() - (this.parameters.getNumber() / 2) * 2;
        if (parametr == 0) { //Для парних
            number = this.parameters.getNumber() / 2 - 1;
            createLabel(this.mainPanel, "S  = ", 16, Font.BOLD | Font.ITALIC, 416, 30 * parameters.getNumber() / 2 + 110, 35, 22);
            createLabel(this.mainPanel, "" + (this.parameters.getNumber() / 2), 13, Font.BOLD, 424, 30 * parameters.getNumber() / 2 + 120, 20, 20);
            this.tS[this.parameters.getNumber() / 2] = createTextField(this.mainPanel, "", 15, 452, 30 * parameters.getNumber() / 2 + 110, 93, 22, color(255, 255, 255), false);
        } else if (parametr == 1) {
            number = (this.parameters.getNumber() - 1) / 2;   //Для не парних
        }

        for (int i = 2; i <= number; i++) {
            createLabel(this.mainPanel, "ψ  = ", 16, Font.BOLD | Font.ITALIC, 272, 30 * i + 110, 35, 22);
            createLabel(this.mainPanel, "" + i, 13, Font.BOLD, 282, 30 * i + 120, 20, 20);
            this.tP[i] = createTextField(this.mainPanel, "", 15, 305, 30 * i + 110, 93, 22, color(255, 255, 255), false);

            createLabel(this.mainPanel, "S  = ", 16, Font.BOLD | Font.ITALIC, 416, 30 * i + 110, 35, 22);
            createLabel(this.mainPanel, "" + i, 13, Font.BOLD, 424, 30 * i + 120, 20, 20);
            this.tS[i] = createTextField(this.mainPanel, "", 15, 452, 30 * i + 110, 93, 22, color(255, 255, 255), false);
        }
    }

    protected void createButtons() {
        this.bCalculation = createButton(mainPanel, "Calculation", 15, new ImageIcon(Program.class.getResource("/javax/swing/plaf/metal/icons/ocean/computer.gif")), 10, height - 65, 130, 30, true);
        this.bClean = createButton(this.mainPanel, "Clean", 15, new ImageIcon(Program.class.getResource("/javax/swing/plaf/metal/icons/ocean/paletteClose.gif")), 145, height - 65, 90, 30, true);
        this.bSave = createButton(this.mainPanel, "Save file", 15, new ImageIcon(Program.class.getResource("/javax/swing/plaf/metal/icons/ocean/floppy.gif")), 250, height - 65, 115, 30, false);
        this.bOpen = createButton(this.mainPanel, "Open file", 15, new ImageIcon(Program.class.getResource("/javax/swing/plaf/metal/icons/ocean/file.gif")), 370, height - 65, 115, 30, false);
        final File file = new File(Constant.FILE_RESULT);
        this.bOpen.setEnabled(file.exists() && file.isFile());
        this.bExit = createButton(this.mainPanel, "Exit", 15, new ImageIcon(Program.class.getResource("/javax/swing/plaf/metal/icons/ocean/close.gif")), 490, height - 65, 90, 30, true);
    }

    protected void createMenuBar() {
        final JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, this.width, 20);

        final JMenu mFile = createMenu(menuBar, "File", 12);
        final JMenu mEdit = createMenu(menuBar, "Edit", 12);
        final JMenu mSettings = createMenu(menuBar, "Settings", 12);
        final JMenu mHelp = createMenu(menuBar, "Help", 12);

        this.miOpen = createMenuItem(mFile, "Open", new ImageIcon(Program.class.getResource("/javax/swing/plaf/metal/icons/ocean/file.gif")), KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        this.miOpen.setEnabled(this.bOpen.isEnabled());
        this.miSave = createMenuItem(mFile, "Save", new ImageIcon(Program.class.getResource("/javax/swing/plaf/metal/icons/ocean/floppy.gif")), KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        this.miSave.setEnabled(false);
        this.miSaveAs = createMenuItem(mFile, "Save as...", new ImageIcon(Program.class.getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")), null);
        this.miSaveAs.setEnabled(false);
        mFile.add(new JSeparator());
        this.miExit = createMenuItem(mFile, "Exit", new ImageIcon(Program.class.getResource("/javax/swing/plaf/metal/icons/ocean/close.gif")), KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
        this.miCalculation = createMenuItem(mEdit, "Calculation", new ImageIcon(Program.class.getResource("/javax/swing/plaf/metal/icons/ocean/computer.gif")), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_MASK));
        this.miClean = createMenuItem(mEdit, "Clean", new ImageIcon(Program.class.getResource("/javax/swing/plaf/metal/icons/ocean/paletteClose.gif")), KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
        this.miProperties = createMenuItem(mSettings, "Properties", new ImageIcon(Program.class.getResource("/javax/swing/plaf/metal/icons/ocean/menu.gif")), KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
        this.miManual = createMenuItem(mHelp, "Manual", new ImageIcon(Program.class.getResource("/javax/swing/plaf/metal/icons/ocean/file.gif")), KeyStroke.getKeyStroke(KeyEvent.VK_F1, InputEvent.CTRL_MASK));
        this.miLicense = createMenuItem(mHelp, "License agreement", new ImageIcon(Program.class.getResource("/javax/swing/plaf/metal/icons/ocean/file.gif")), KeyStroke.getKeyStroke(KeyEvent.VK_F2, InputEvent.CTRL_MASK));
        mHelp.add(new JSeparator());
        this.miAbout = createMenuItem(mHelp, "About the program", new ImageIcon(Program.class.getResource("/javax/swing/plaf/metal/icons/ocean/homeFolder.gif")), KeyStroke.getKeyStroke(KeyEvent.VK_F3, InputEvent.CTRL_MASK));

        this.mainPanel.add(menuBar);
    }

    protected void propertiesFrame() {
        final int widthP = 305;
        final int heightP = 220;
        final JPanel panel = createPanel();
        this.frameProperties = createFrame(panel, "CPM: Properties", Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource("images/icon.gif")), JFrame.HIDE_ON_CLOSE, false, mainFrame.isAlwaysOnTop(), true, widthP, heightP);

        createLabel(panel, "Enter the number of members in the cycle:", 15, Font.BOLD, 10, 5, 288, 20);
        createLabel(panel, "Enter the order of accuracy in calculations:", 15, Font.BOLD, 10, 55, 288, 20);
        createLabel(panel, "N = ", 16, Font.BOLD | Font.ITALIC, 100, 30, 32, 23);
        createLabel(panel, "O = ", 16, Font.BOLD | Font.ITALIC, 100, 80, 32, 23);

        this.tNumber = createTextField(panel, "", 15, 130, 30, 50, 23, color(255, 255, 255), true);
        this.tAccuracy = createTextField(panel, "", 15, 130, 80, 50, 23, color(255, 255, 255), true);

        this.checkOnTop = createCheckBox(panel, "- always on top.", 15, Font.BOLD, this.mainFrame.isAlwaysOnTop(), 10, 110, 150, 20);
        this.checkAutoSave = createCheckBox(panel, "- auto save data on exit.", 15, Font.BOLD, this.parameters.isAutoSave(), 10, 130, 200, 20);

        this.bOk = createButton(panel, "OK", 14, null, 140, heightP - 60, 60, 28, true);
        this.bCancel = createButton(panel, "Cancel", 14, null, 205, heightP - 60, 80, 28, true);
    }

    protected void showMessage(int check, String nameFile) {
        String text;
        String name;
        int option;
        switch (check) {
            case 1:
                text = Constant.PROGRAM_ABOUT;
                name = Constant.ABOUT;
                option = JOptionPane.INFORMATION_MESSAGE;
                break;
            case 2:
                text = Constant.NUMERICAL_VALUE;
                name = Constant.ERROR;
                option = JOptionPane.ERROR_MESSAGE;
                break;
            case 3:
                text = Constant.NUMBERS_DIAPASON;
                name = Constant.ERROR;
                option = JOptionPane.ERROR_MESSAGE;
                break;
            case 4:
                text = Constant.ACCURACY_DIAPASON;
                name = Constant.ERROR;
                option = JOptionPane.ERROR_MESSAGE;
                break;
            case 5:
                text = Constant.ENTER_ANGLES;
                name = Constant.ERROR;
                option = JOptionPane.ERROR_MESSAGE;
                break;
            case 6:
                text = Constant.SAVE_RESULT;
                name = Constant.WARNING;
                option = JOptionPane.WARNING_MESSAGE;
                break;
            case 7:
                text = "\"" + nameFile + "\" - " + Constant.FILE_ABSENT;
                name = Constant.WARNING;
                option = JOptionPane.WARNING_MESSAGE;
                this.bOpen.setEnabled(false);
                this.miOpen.setEnabled(false);
                break;
            default:
                text = Constant.ERROR;
                name = Constant.ERROR;
                option = JOptionPane.ERROR_MESSAGE;
                break;
        }

        if (check == 1) {
            JOptionPane.showMessageDialog(null, text, name, option);
        } else {
            final JLabel message = new JLabel(text);
            message.setFont(font(16, Font.BOLD));
            if (check == 6) {
                option = JOptionPane.showConfirmDialog(null, message, name, JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    try {
                        this.conformation.save();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, message, name, option);
            }
        }
    }

    private JFrame createFrame(
            final JPanel panel,
            final String text,
            final Image image,
            final int operation,
            final boolean resizable,
            final boolean onTop,
            final boolean visible,
            final int width,
            final int height
    ) {
        final JFrame frame = new JFrame(text);
        frame.setIconImage(image);
        frame.setDefaultCloseOperation(operation);
        frame.setResizable(resizable);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(panel);
        frame.setAlwaysOnTop(onTop);
        frame.setVisible(visible);
        return frame;
    }

    private JPanel createPanel() {
        final JPanel panel = new JPanel();
        panel.setBackground(color(230, 230, 230));
        panel.setLayout(null);
        panel.setOpaque(true);
        return panel;
    }

    private void createLabel(
            final JPanel panel,
            final String name,
            final int fontSize,
            final int option,
            final int widthCoordinate,
            final int heightCoordinate,
            final int width,
            final int height
    ) {
        final JLabel label = new JLabel(name);
        label.setFont(font(fontSize, option));
        label.setBounds(widthCoordinate, heightCoordinate, width, height);
        //label.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(label);
    }

    private JTextField createTextField(
            final JPanel panel,
            final String text,
            final int fontSize,
            final int widthCoordinate,
            final int heightCoordinate,
            final int width,
            final int height,
            final Color color,
            final boolean editable
    ) {
        final JTextField field = new JTextField(text);
        field.setFont(font(fontSize, Font.BOLD));
        field.setBounds(widthCoordinate, heightCoordinate, width, height);
        field.setBackground(color);
        field.setEditable(editable);
        panel.add(field);
        return field;
    }

    private JButton createButton(
            final JPanel panel,
            final String text,
            final int fontSize,
            final Icon icon,
            final int widthCoordinate,
            final int heightCoordinate,
            final int width,
            final int height,
            final boolean enabled
    ) {
        final JButton button = new JButton(text);
        button.setFont(font(fontSize, Font.BOLD));
        button.setBounds(widthCoordinate, heightCoordinate, width, height);
        button.setCursor(CURSOR);
        button.addActionListener(this);
        button.setIcon(icon);
        button.setEnabled(enabled);
        panel.add(button);
        return button;
    }

    private JMenu createMenu(
            final JMenuBar menuBar,
            final String name,
            final int fontSize
    ) {
        final JMenu menu = new JMenu(name);
        menu.setFont(font(fontSize, Font.BOLD));
        menu.setCursor(CURSOR);
        menuBar.add(menu);
        return menu;
    }

    private JMenuItem createMenuItem(
            final JMenu menu,
            final String name,
            final ImageIcon icon,
            final KeyStroke key
    ) {
        final JMenuItem item = new JMenuItem(name);
        item.setCursor(CURSOR);
        item.setIcon(icon);
        item.setAccelerator(key);
        item.addActionListener(this);
        menu.add(item);
        return item;
    }

    private JCheckBox createCheckBox(
            final JPanel panel,
            final String text,
            final int fontSize,
            final int option,
            final boolean checked,
            final int widthCoordinate,
            final int heightCoordinate,
            final int width,
            final int height
    ) {
        final JCheckBox checkBox = new JCheckBox(text);
        checkBox.setBounds(widthCoordinate, heightCoordinate, width, height);
        checkBox.setFont(font(fontSize, option));
        checkBox.setBackground(color(230, 230, 230));
        checkBox.setSelected(checked);
        panel.add(checkBox);
        return checkBox;
    }

    private Font font(final int fontSize, final int option) {
        return new Font(Constant.FONT_NAME, option, fontSize);
    }

    private Color color(final int red, final int green, final int blue) {
        return new Color(red, green, blue);
    }
}
