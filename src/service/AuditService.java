package service;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public final class AuditService {
    private static AuditService INSTANCE = null;
    private static final String FILE_PATH = "D:\\FMI\\Anul II\\Semestrul II\\PAO\\Laborator\\Proiect\\src\\audit\\audit.csv";
    private final File file;

    private AuditService() {
        this.file = new File(FILE_PATH);
        boolean fileExists = this.file.exists();
        try(FileWriter fileWriter = new FileWriter(this.file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            if(!fileExists) {
                bufferedWriter.write("Date&Time,Object,Action");
                bufferedWriter.newLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AuditService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AuditService();
        }
        return INSTANCE;
    }

    public void writeLog(Map<String,String> log) {
        try(FileWriter fileWriter = new FileWriter(this.file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            String currDateAndTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(currDateAndTime).append(",");
            stringBuilder.append(log.get("object")).append(",");
            stringBuilder.append(log.get("command"));

            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.newLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}