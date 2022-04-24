package com.example.project_chia;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;


public class commands {
    String windows_username = System.getProperty("user.name");

    public String chiaVersion() throws IOException {

        File file = new File("C:\\tmp1\\chia_version.txt");
        String version = null;
        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_16));
            version = br.readLine();
            System.out.println("Chia Version:" + version);
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }


    public commands(String command, String add_a_path_textfield) throws IOException {
        if (command == "findPaths") {
            try {
                findPaths_command();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (command == "checkPlots") {
            try {
                checkPlots_command(Integer.valueOf(add_a_path_textfield));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (command == "add_a_path") {
            add_a_path_command(add_a_path_textfield);
        } else if (command == "Show_Keys") {
            Show_Keys_command();
        } else if (command == "RemoveALine") {
            RemoveALine_command("findPaths_command", null, null);
        } else if (command == "savePassphrase") {
            passphrase_save(add_a_path_textfield);
        }
    }

    public void checkFile(String whatCommand) {
        Path tmp1 = Path.of("C:\\tmp1");
        if (Files.isDirectory(tmp1)) {
            System.out.println("---->there is tmp1 in C");
        } else {
            System.out.println("---->there is no tmp1 folder in C, Trying to Create new one");
            try {
                Files.createDirectory(tmp1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        tmp1 = Path.of("C:\\tmp1\\results");
        if (Files.isDirectory(tmp1)) {
            System.out.println("---->there is results in tmp1");
        } else {
            System.out.println("---->there is no results folder in tmp1, Trying to Create new one");
            try {
                Files.createDirectory(tmp1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (whatCommand == "checkPlots_command") {
            File file1 = new File("C:\\tmp1\\plots_name.txt");
            if (file1.exists()) {
                System.out.println(file1 + "---->plots_name.txt already exists");
            } else {
                System.out.println(file1 + "---->plots_name.txt does not exists and creating new plots_name.txt");
                new File("C:\\tmp1\\plots_name.txt");
            }
        } else if (whatCommand == "getVersion") {
            File file1 = new File("C:\\tmp1\\chia_version.txt");
            if (file1.exists()) {
                System.out.println(file1 + "---->chia_version.txt already exists");
            } else {
                System.out.println(file1 + "---->chia_version.txt does not exists and creating new plots_name.txt");
                new File("C:\\tmp1\\chia_version.txt");
            }
        } else if (whatCommand == "findPaths_command") {
            File file1 = new File("C:\\tmp1\\plot_paths.txt");
            if (file1.exists()) {
                System.out.println(file1 + "---->plot_paths.txt already exists");
            } else {
                System.out.println(file1 + "---->plots_name.txt does not exists and creating new plot_paths.txt");
                new File("C:\\tmp1\\plot_paths.txt");
            }
        } else if (whatCommand == "Show_Keys_command") {
            File file1 = new File("C:\\tmp1\\keys.txt");

            if (file1.exists()) {
                System.out.println(file1 + "---->keys.txt already exists");
            } else {
                System.out.println(file1 + "---->keys.txt does not exists and creating new plot_paths.txt");
                new File("C:\\tmp1\\keys.txt");

            }
        } else if (whatCommand == "passphrase") {
            File file1 = new File("C:\\tmp1\\passphrase.txt");

            if (file1.exists()) {
                System.out.println(file1 + "---->passphrase.txt already exists");
            } else {
                System.out.println(file1 + "---->passphrase.txt does not exists and creating new passphrase.txt");
                new File("C:\\tmp1\\passphrase.txt");

            }
        }
    }

    public void findPaths_command() throws IOException, InterruptedException {
        checkFile("findPaths_command");

        ProcessBuilder findPath = new ProcessBuilder("C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\powershell.exe", "cd C:\\Users\\" + windows_username +
                "\\AppData\\Local\\chia-blockchain\\app-" + chiaVersion() + "\\resources\\app.asar.unpacked\\daemon ; " +
                "./chia.exe plots show >C:\\tmp1\\plot_paths.txt 2>&1 ; exit");

        Process process = findPath.start();


        int exitCode = process.waitFor();
        System.out.println("Exit code: " + exitCode);

        process.destroy();

        System.out.println("findPaths_command is done");
        RemoveALine_command("findPaths_command", null, null);

    }

    public void checkPlots_command(Integer check_Number) throws IOException, InterruptedException {
        checkFile("checkPlots_command");


        File inputFile = new File("C:\\tmp1\\plot_paths.txt");
        if (!inputFile.isFile()) {
            System.out.println("File does not exist");
            return;
        }
        //
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
        String line;

        //Read from the original file and write to the new
        //unless content matches data to be removed.
        System.out.println("while a geldi");
        int loop1 = 0;
        String xchversion = chiaVersion();
        while ((line = br.readLine()) != null) {
            System.out.println("location for terminal:" + line);
            // Executing the command with ProcessBuilder
            ProcessBuilder plot_checker = new ProcessBuilder("C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\powershell.exe", "cd C:\\Users\\" + windows_username
                    + "\\AppData\\Local\\chia-blockchain\\app-" + xchversion + "\\resources\\app.asar.unpacked\\daemon ; " +
                    "./chia.exe plots check -g " + line + "\\ " +
                    "-n " + check_Number + "  >C:\\tmp1\\results\\" + loop1 + ".txt 2>&1 ; exit");
            Process process = plot_checker.start();

            int exitCode = process.waitFor();
            System.out.println("Exit code: " + exitCode);

            RemoveALine_command("plotParsing", line, loop1);
            RemoveALine_command("clearEmptySpace", line, loop1);

            loop1++;


        }
        br.close();
        mergeResults(null, loop1);
        System.out.println("checkPlots_command is done");
    }

    public void mergeResults(String line, int loop1) {
        System.out.println("\nmerging result files");
        try {
            int i;
            File newFile = new File("C:\\tmp1\\results\\results.txt.tmp");
            BufferedWriter bw = new BufferedWriter(new FileWriter(newFile));
            File inputFile = null;
            BufferedReader br = null;
            System.out.println(" mergeResults while ina geldi");
            while ((i = loop1) >= 0) {
                inputFile = new File("C:\\tmp1\\results\\" + loop1 + ".txt");
                if (!inputFile.isDirectory()) {
                    System.out.println("last file string to read is not exist");
                    break;
                } else {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), StandardCharsets.UTF_16));
                    System.out.println(" mergeResults while ina geldi");
                    while ((line = br.readLine()) != null) {
                        bw.write(line);
                        bw.newLine();
                    }
                    i -= 1;
                }
            }

            bw.close();
            br.close();

            //Delete the original file
            if (!inputFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            //Rename the new file to the filename the original file had.
            if (!newFile.renameTo(new File("results.txt")))
                System.out.println("Could not rename file");


        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("passphrase.txt organizing is completed");

    }

    public void passphrase_save(String passphraseTextField) {
        checkFile("passphrase");
        System.out.println("\nplot_paths.txt organizing");
        try {
            File inputFile = new File("C:\\tmp1\\passphrase.txt");

            //Construct the new file that will later be renamed to the original filename.
            File tempFile = new File(inputFile + ".tmp");
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), StandardCharsets.UTF_16));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
            String line;

            //Read from the original file and write to the new
            //unless content matches data to be removed.
            System.out.println("while a geldi");
            line = passphraseTextField;
            bw.write(line);
            bw.newLine();
            bw.flush();
            bw.close();
            br.close();

            //Delete the original file
            if (!inputFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inputFile))
                System.out.println("Could not rename file");


        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("passphrase.txt organizing is completed");

    }

    public void add_a_path_command(String add_a_path_textfield) throws IOException {
        String command1 = "C:\\Users\\" + windows_username + "\\AppData\\Local\\chia-blockchain\\app-" + chiaVersion() + "\\resources\\app.asar.unpacked\\daemon\\chia.exe " +
                "plots add -d " + add_a_path_textfield;

        // Executing the command 1
        Runtime rt = Runtime.getRuntime();
        Process powerShellProcess1 = rt.exec(command1);


        // Getting the results
        powerShellProcess1.getOutputStream().close();
        // Print outputs from powershell
        String line;
        System.out.println("Standard Output:");
        BufferedReader stdout = new BufferedReader(new InputStreamReader(
                powerShellProcess1.getInputStream()));
        while ((line = stdout.readLine()) != null) {
            System.out.println(line);
        }
        stdout.close();

        // Print errors from powershell
        System.out.println("Standard Error:");
        BufferedReader stderr = new BufferedReader(new InputStreamReader(
                powerShellProcess1.getErrorStream()));
        while ((line = stderr.readLine()) != null) {
            System.out.println(line);
        }
        stderr.close();

        System.out.println("add_a_path_command is done");
        System.out.println("running findPaths_command");
        try {
            findPaths_command();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void Show_Keys_command() throws IOException {
        checkFile("Show_Keys_command");
        String command1 = "cd C:\\Users\\" + windows_username + "\\AppData\\AppData\\Local\\chia-blockchain\\app-" + chiaVersion() + "\\resources\\app.asar.unpacked\\daemon ; " +
                "./chia.exe keys show >C:\\tmp1\\plot_paths.txt 2>&1";

        // Executing the command 1
        Runtime rt = Runtime.getRuntime();
        Process powerShellProcess1 = rt.exec(command1);

        try {
            int exitCode = powerShellProcess1.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Getting the results
        powerShellProcess1.getOutputStream().close();
        // Print outputs from powershell
        System.out.println("Standard Output:");

        powerShellProcess1.destroy();

        System.out.println("add_a_path_command is done");
    }

    private void RemoveALine_command(String command, String line, Integer loop1) {
        BufferedReader br;
        BufferedWriter bw;
        if (command.equals("findPaths_command")) {

            System.out.println("\nplot_paths.txt organizing");
            try {
                File inputFile = new File("C:\\tmp1\\plot_paths.txt");
                if (!inputFile.isFile()) {
                    System.out.println("File does not exist");
                    return;
                }
                //Construct the new file that will later be renamed to the original filename.
                File tempFile = new File(inputFile + ".tmp");
                br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), StandardCharsets.UTF_16));
                bw = new BufferedWriter(new FileWriter(tempFile));

                //Read from the original file and write to the new
                //unless content matches data to be removed.
                System.out.println("while a geldi");
                while ((line = br.readLine()) != null) {

                    System.out.println(line);
                    if (line.startsWith(":", 1)) {
                        bw.write(line);
                        bw.newLine();
                        bw.flush();
                    }
                }
                bw.close();
                br.close();
                //Delete the original file
                if (!inputFile.delete()) {
                    System.out.println("Could not delete file");
                    return;
                }

                //Rename the new file to the filename the original file had.
                if (!tempFile.renameTo(inputFile))
                    System.out.println("Could not rename file");


            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("plot_paths.txt organizing is completed");
        } else if (command.equals("clearing_plotCheck_output")) {
            System.out.println("\nplot_name.txt organizing");
            try {
                File inputFile = new File("C:\\tmp1\\plots_name_temp.txt");
                if (!inputFile.isFile()) {
                    System.out.println("File does not exist");
                    return;
                }
                //Construct the new file that will later be renamed to the original filename.
                File tempFile = new File(inputFile + ".tmp");
                br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), StandardCharsets.UTF_16));
                bw = new BufferedWriter(new FileWriter(tempFile));
                line = null;

                //Read from the original file and write to the new
                //unless content matches data to be removed.
                System.out.println("while a geldi");
                while ((line = br.readLine()) != null) {

                    System.out.println(line);
                    if (line.startsWith(":")) {
                        bw.write(line);
                        bw.newLine();
                        bw.flush();
                    }
                }
                bw.close();
                br.close();

                //Delete the original file
                if (!inputFile.delete()) {
                    System.out.println("Could not delete file");
                    return;
                }

                //Rename the new file to the filename the original file had.
                if (!tempFile.renameTo(inputFile))
                    System.out.println("Could not rename file");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("plot Check output cleared");
        } else if (command.equals("plotParsing")) {
            System.out.println("\n" + loop1 + ".txt organizing");
            try {
                File inputFile = new File("C:\\tmp1\\results\\" + loop1 + ".txt");
                if (!inputFile.isFile()) {
                    System.out.println("File does not exist");
                    return;
                }
                //Construct the new file that will later be renamed to the original filename.
                File tempFile = new File(inputFile + ".tmp");
                br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), StandardCharsets.UTF_16));
                bw = new BufferedWriter(new FileWriter(tempFile));
                String line1 = null;

                //Read from the original file and write to the new
                //unless content matches data to be removed.
                System.out.println("while a geldi");
                int nextline = 0;
                while ((line1 = br.readLine()) != null) {
                    line1 = line1.replace("\u001B[0m", "");
                    line1 = line1.replace("\u001B[32m", "");
                    line1 = line1.replace("\u001B[33m", "");

                    System.out.println(line1);
                    if (nextline == 1 && !line1.startsWith("-", 4)) {
                        bw.write(line1);
                        bw.write("\n");
                        nextline = 0;
                    } else if (nextline == 1 && line1.startsWith("-", 4)) {
                        bw.write("\n");
                        nextline = 0;
                    }
                    if (line1.contains("Testing plot") || line1.contains("Pool public key") ||
                            line1.contains("Farmer public key") || line1.contains("Local sk") || line1.contains("Proofs")) {
                        bw.write(line1);
                        nextline = 1;

                    }
                }

                bw.close();
                br.close();

                //Delete the original file
                if (!inputFile.delete()) {
                    System.out.println("Could not delete file");
                    return;
                }


                //Rename the new file to the filename the original file had.
                if (!tempFile.renameTo(inputFile))
                    System.out.println("Could not rename file");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            //RemoveALine_command("clearEmptySpace",line,loop1);
            System.out.println("plot Check output cleared");
        } else if (command.equals("clearEmptySpace")) {
            System.out.println("\n" + loop1 + ".txt clearEmptySpace");
            try {
                File inputFile = new File("C:\\tmp1\\results\\" + loop1 + ".txt");
                if (!inputFile.isFile()) {
                    System.out.println("File does not exist");
                    return;
                }
                //Construct the new file that will later be renamed to the original filename.
                File tempFile = new File(inputFile + ".tmp");
                br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF8"));
                bw = new BufferedWriter(new FileWriter(tempFile));
                String line1;

                //Read from the original file and write to the new
                //unless content matches data to be removed.
                System.out.println("while a geldi");
                int lineCount = 0;
                while ((line1 = br.readLine()) != null) {

                    System.out.println(line1);

                    bw.write(line1.substring(65));

                    bw.write("\n");
                    /*
                    if (!line1.isEmpty() && lineCount == 0) {

                        bw.write(line1.substring(65));

                        bw.write("\n");
                        lineCount += 1;

                    } else if (!line1.isEmpty() && lineCount == 1) {

                        bw.write(line1.substring(65));
                        bw.write("\n");
                        lineCount += 1;

                    } else if (!line1.isEmpty() && lineCount == 2) {

                        bw.write(line1.substring(65));
                        bw.write("\n");
                        lineCount += 1;

                    } else if (!line1.isEmpty() && lineCount == 3) {

                        bw.write(line1.substring(65));
                        bw.write("\n");
                        lineCount = 0;
                    } /*else if (!line1.isEmpty() && lineCount == 4) {
                        bw.write(line1.substring(84, line1.length() - 5));
                        bw.write("\n");
                        lineCount = 0;
                    }*/

                }
                bw.close();
                br.close();

                //Delete the original file
                if (!inputFile.delete()) {
                    System.out.println("Could not delete file");
                    return;
                }
                //Rename the new file to the filename the original file had.
                if (!tempFile.renameTo(inputFile))
                    System.out.println("Could not rename file");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("plot file clearing empty lines is completed");

        } /*else if (command.equals("rewritePlotInfos")) {
            System.out.println("\n" + loop1 + ".txt Rewriting");
            try {
                File inputFile = new File("C:\\tmp1\\" + loop1 + ".txt");
                if (!inputFile.isFile()) {
                    System.out.println("File does not exist");
                    return;
                }
                //Construct the new file that will later be renamed to the original filename.
                File tempFile = new File(inputFile + ".tmp");
                BufferedReader br3 = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), StandardCharsets.UTF_16));
                BufferedWriter bw3 = new BufferedWriter(new FileWriter(tempFile));
                String line1 = null;

                //Read from the original file and write to the new
                //unless content matches data to be removed.
                System.out.println("while a geldi");


                bw3.close();
                br3.close();

                //Delete the original file
                if (!inputFile.delete()) {
                    System.out.println("Could not delete file");
                    return;
                }

                //Rename the new file to the filename the original file had.
                if (!tempFile.renameTo(inputFile))
                    System.out.println("Could not rename file");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            //RemoveALine_command("clearEmptySpace",line,loop1);
            System.out.println("plot rewrite is completed");
        }*/
    }


}
