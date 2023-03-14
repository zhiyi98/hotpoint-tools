package com.ruoyi.common.core.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author zhiyi98
 */
public class ShellCommandExecutor {

    public static String execute(String command) {
        StringBuilder result = new StringBuilder();
        try {
            ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", command);
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}