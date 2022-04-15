package com.example.project_chia;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class getpassphrase {
    public String getpassphraseFile() {
        File file1 = new File("C:\\tmp1\\passphrase.txt");
        //File file1 = new File("C:\\tmp1\\passphrase.txt");
        if (Files.isDirectory(Path.of("C:\\tmp1"))) {
            System.out.println("---->there is tmp1 in C");
        } else {
            System.out.println("---->there is no tmp1 folder in C, Trying to Create new one");
            try {
                Files.createDirectory(Path.of("C:\\tmp1"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (file1.exists()) {
            System.out.println(file1 + "---->passphrase.txt already exists");
            return "exist";

        } else {
            System.out.println(file1 + "---->passphrase.txt does not exists and creating new passphrase.txt");
            new File("C:\\tmp1\\passphrase.txt");
            return "not_exist";
        }
    }

}
