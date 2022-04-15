package com.example.project_chia;

import java.io.*;

public class getVersion {
    String windows_username = System.getProperty("user.name");

    public getVersion() throws IOException {
        ProcessBuilder cia_version = new ProcessBuilder("powershell.exe", "(Get-Item C:\\Users\\" + windows_username +
                "\\AppData\\Local\\chia-blockchain\\chia.exe).VersionInfo.ProductVersion" +
                " >C:\\tmp1\\chia_version.txt 2>&1 ; exit");
        Process process = cia_version.start();
        System.out.println("Getversion done");


    }
}
