package com.example.project_chia;

import javafx.fxml.FXML;

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


    public commands(String command, String sendedValue) throws IOException {
        if (command == "findPaths") {
            try {
                findPaths_command();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (command == "checkPlots") {
            try {
                checkPlots_command(Integer.valueOf(sendedValue));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (command == "mergeResults") {
            mergeResults("", Integer.parseInt(sendedValue));
        } else if (command == "lowPlots") {
            lowPlots(Float.valueOf(sendedValue));
        } else if (command == "add_a_path") {
            add_a_path_command(sendedValue);
        } else if (command == "Show_Keys") {
            Show_Keys_command();
        } else if (command == "RemoveALine") {
            RemoveALine_command("findPaths_command", null, null);
        } else if (command == "savePassphrase") {
            passphrase_save(sendedValue);
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
        tmp1 = Path.of("C:\\tmp1\\result");
        if (Files.isDirectory(tmp1)) {
            System.out.println("---->there is result in tmp1");
        } else {
            System.out.println("---->there is no result folder in tmp1, Trying to Create new one");
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
        } else if (whatCommand == "lowScorePlot") {
            File errorPlotFile = new File("C:\\tmp1\\result\\errorPlot.txt");
            File lowScorePlot = new File("C:\\tmp1\\result\\lowScorePlot.txt");
            if (errorPlotFile.exists()) {
                System.out.println(errorPlotFile + "---->lowScorePlot.txt already exists");
            } else {
                System.out.println(errorPlotFile + "---->errorPlotFile.txt does not exists and creating new plots_name.txt");
                new File("C:\\tmp1\\result\\errorPlot.txt");
            }

            if (lowScorePlot.exists()) {
                System.out.println(lowScorePlot + "---->lowScorePlot.txt already exists");
            } else {
                System.out.println(lowScorePlot + "---->lowScorePlot.txt does not exists and creating new plots_name.txt");
                new File("C:\\tmp1\\result\\lowScorePlot.txt");
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

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
        String line;

        //Read from the original file and write to the new
        //unless content matches data to be removed.
        System.out.println("while a geldi");
        int loop1 = 1;
        String xchversion = chiaVersion();
        while ((line = br.readLine()) != null) {
            System.out.println("location for terminal:" + line);
            if (line.endsWith("\\")) {
                // Executing the command with ProcessBuilder
                ProcessBuilder plot_checker = new ProcessBuilder("C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\powershell.exe", "cd C:\\Users\\" + windows_username
                        + "\\AppData\\Local\\chia-blockchain\\app-" + xchversion + "\\resources\\app.asar.unpacked\\daemon ; " +
                        "./chia.exe plots check -g " + line + " " +
                        "-n " + check_Number + "  >C:\\tmp1\\result\\" + loop1 + ".txt 2>&1 ; exit");

                Process process = plot_checker.start();

                int exitCode = process.waitFor();
                System.out.println("Exit code: " + exitCode);
            } else {
                // Executing the command with ProcessBuilder
                ProcessBuilder plot_checker = new ProcessBuilder("C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\powershell.exe", "cd C:\\Users\\" + windows_username
                        + "\\AppData\\Local\\chia-blockchain\\app-" + xchversion + "\\resources\\app.asar.unpacked\\daemon ; " +
                        "./chia.exe plots check -g " + line + "\\ " +
                        "-n " + check_Number + "  >C:\\tmp1\\result\\" + loop1 + ".txt 2>&1 ; exit");

                Process process = plot_checker.start();

                int exitCode = process.waitFor();
                System.out.println("Exit code: " + exitCode);
            }
            RemoveALine_command("plotParsing", line, loop1);
            RemoveALine_command("clearEmptySpace", line, loop1);

            loop1++;
        }
        loop1 -= 1;
        br.close();
        mergeResults(null, loop1);
        System.out.println("checkPlots_command is done");
    }

    public void lowPlots(Float health) {
        System.out.println("\nfounding low score result files");
        System.out.println("\nplot_paths.txt organizing");
        BufferedReader br;
        BufferedWriter bw_err;
        BufferedWriter bw_low;
        String line;
        checkFile("lowScorePlot");
        try {
            File inputFile = new File("C:\\tmp1\\result\\result.txt");
            File errorPlotFile = new File("C:\\tmp1\\result\\errorPlot.txt");
            File lowScorePlot = new File("C:\\tmp1\\result\\lowScorePlot.txt");


            br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
            bw_err = new BufferedWriter(new FileWriter(errorPlotFile));
            bw_low = new BufferedWriter(new FileWriter(lowScorePlot));

            //Read from the original file and write to the new
            //unless content matches data to be removed.
            System.out.println("lowScore ilk while ina geldi");
            int lineInt1 = 1;
            int lineInt2 = 0;
            int lineInt3 = 0;
            int lineInt4 = 0;
            String line1 = null;
            String line2 = null;
            String line3 = null;
            String line4 = null;
            String line5 = null;
            boolean count = false;
            int lap5 = 0;
            float score = 0;
            while ((line = br.readLine()) != null) {
                lap5++;
                if (line.contains("Testing plot")) {
                    line1 = line;

                } else if (line.contains("Pool public key")) {
                    line2 = line;
                    count = true;

                } else if (line.contains("Farmer public key")) {
                    line3 = line;

                } else if (line.contains("Local sk")) {
                    line4 = line;

                } else if (line.contains("Proofs")) {
                    line5 = line;
                }
                if (lap5 == 5) {
                    if (count == true) {

                        if (line5.startsWith("ERROR")) {
                            bw_err.write(line1);
                            bw_err.newLine();
                            bw_err.flush();

                            bw_err.write(line2);
                            bw_err.newLine();
                            bw_err.flush();

                            bw_err.write(line3);
                            bw_err.newLine();
                            bw_err.flush();

                            bw_err.write(line4);
                            bw_err.newLine();
                            bw_err.flush();

                            bw_err.write(line5);
                            bw_err.newLine();
                            bw_err.flush();

                        } else {
                            String s1 = line5.substring(line5.indexOf(",") + 1);
                            s1.trim();
                            score = Float.parseFloat(s1);
                            System.out.println("score= " + score);
                            if (score < health) {
                                bw_low.write(line1);
                                bw_low.newLine();
                                bw_low.flush();

                                bw_low.write(line2);
                                bw_low.newLine();
                                bw_low.flush();

                                bw_low.write(line3);
                                bw_low.newLine();
                                bw_low.flush();

                                bw_low.write(line4);
                                bw_low.newLine();
                                bw_low.flush();

                                bw_low.write(line5);
                                bw_low.newLine();
                                bw_low.flush();
                            }
                        }

                        count = false;
                    } else if (count == false) {
                        if (line5.startsWith("ERROR")) {
                            bw_err.write(line1);
                            bw_err.newLine();
                            bw_err.flush();

                            bw_err.write(line3);
                            bw_err.newLine();
                            bw_err.flush();

                            bw_err.write(line4);
                            bw_err.newLine();
                            bw_err.flush();

                            bw_err.write(line5);
                            bw_err.newLine();
                            bw_err.flush();

                        } else {
                            String s1 = line5.substring(line5.indexOf(",") + 1);
                            s1.trim();
                            score = Float.parseFloat(s1);
                            System.out.println("score= " + score);
                            if (score < health) {
                                bw_low.write(line1);
                                bw_low.newLine();
                                bw_low.flush();

                                bw_low.write(line3);
                                bw_low.newLine();
                                bw_low.flush();

                                bw_low.write(line4);
                                bw_low.newLine();
                                bw_low.flush();

                                bw_low.write(line5);
                                bw_low.newLine();
                                bw_low.flush();
                            }
                        }
                    }
                    lap5 = 0;
                }
            }
            bw_err.close();
            bw_low.close();
            br.close();


        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("lowScorePlot.txt and errorPlot.txt is completed");
    }

    public void mergeResults(String line, int loop1) {
        System.out.println("\nmerging result files");

        try {
            int i = loop1;
            File newFile = new File("C:\\tmp1\\result\\result.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(newFile));
            File inputFile = null;
            inputFile = new File("C:\\tmp1\\result\\" + i + ".txt");
            BufferedReader br = new BufferedReader((new InputStreamReader(new FileInputStream(inputFile), StandardCharsets.UTF_16)));
            System.out.println(" mergeResults while ina geldi");
            System.out.println("i=" + i);
            while (true) {
                System.out.println("while a girdi");
                inputFile = new File("C:\\tmp1\\result\\" + i + ".txt");
                try {
                    if (!inputFile.exists()) {
                        System.out.println("last file string to read is not exist");
                        i = 0;
                        break;
                    } else {
                        br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
                        System.out.println("loop1= " + i);
                        while ((line = br.readLine()) != null) {
                            bw.write(line);
                            bw.newLine();
                        }
                        i -= 1;
                    }
                    if (i == 0)
                        break;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            bw.close();
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("result.txt merging is completed");
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
                File inputFile = new File("C:\\tmp1\\result\\" + loop1 + ".txt");
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
                File inputFile = new File("C:\\tmp1\\result\\" + loop1 + ".txt");
                if (!inputFile.isFile()) {
                    System.out.println("File does not exist");
                    return;
                }
                //Construct the new file that will later be renamed to the original filename.
                File tempFile = new File(inputFile + ".tmp");
                br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), StandardCharsets.UTF_8));
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
