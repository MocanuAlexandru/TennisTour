package fileServices;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;

public class LoggerService {
    private static LoggerService instance = new LoggerService();
    private LoggerService(){}

    public static LoggerService getInstance() { return instance; }

    public void logMessage(String message){
        String filePath = "src/files/Logger.csv";
        try(RandomAccessFile out = new RandomAccessFile(filePath, "rw")){
            out.seek(out.length());

            String time = new Timestamp(Calendar.getInstance().getTimeInMillis()).toString();

            String line = String.join(",", time, message);
            for(int  i=0; i<line.length(); ++i)
                out.write(line.charAt(i));
            out.write('\n');
        }
        catch(IOException exc){
            System.out.println(Arrays.toString(exc.getStackTrace()));
        }
    }
}
