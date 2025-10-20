package com.devstack.pos.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BackupUtil {
    public static String createBackup(String host, String port, String database, String user, String password) throws IOException, IOException {
        // Path to mysqldump — adjust if your MySQL installation is elsewhere
        String dumpCommand = String.format(
                "mysqldump -h%s -P%s -u%s -p%s --databases %s",
                host, port, user, password, database
        );

        Process process = Runtime.getRuntime().exec(dumpCommand);

        // Read dump output directly into a StringBuilder
        StringBuilder dumpData = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dumpData.append(line).append(System.lineSeparator());
            }
        }

        return dumpData.toString();
    }
}
