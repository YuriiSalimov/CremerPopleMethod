package com.cpm.prog;

import java.awt.Desktop;
import java.io.*;

public final class Files {

    private Files() {
    }

    public static int open(final String fileName) {
        int check = 0;
        try {
            final File file = new File(fileName);
            if (file.exists() && file.isFile()) {
                Desktop.getDesktop().open(file);
            } else {
                check = 7;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            check = 8;
        }
        return check;
    }

    public static void write(final String path, final String text)
            throws IOException {
        try (FileWriter fileWriter = new FileWriter(path);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
            writer.write(text);
        }
    }

    public static String read(final String path) throws IOException {
        final StringBuilder sb = new StringBuilder();
        try (FileReader fileReader = new FileReader(path);
             BufferedReader reader = new BufferedReader(fileReader)) {
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
        }
        return sb.toString();
    }

    public static File getFile(final String name) {
        return new File(name);
    }

    public static void serialization(final String path, final Object object)
            throws IOException {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(object);
        }
    }

    public static Object deserialization(final String path)
            throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return ois.readObject();
        }
    }
}
