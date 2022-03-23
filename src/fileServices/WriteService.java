package fileServices;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.LinkedList;

public class WriteService {
    private static WriteService ourInstance = new WriteService();

    //FUNCTII SINGLETON
    public static WriteService getInstance() {
        return ourInstance;
    }
    private WriteService() {
    }

    //FUNCTII SERVICE
    public void exportDataCSV(LinkedList<String[]> data, String filePath) {
        try(RandomAccessFile out = new RandomAccessFile(filePath, "rw")){
            out.setLength(0);
            for(String[] line : data) {
                String aux = String.join(",", line);
                for(int  i=0; i<aux.length(); ++i)
                    out.write(aux.charAt(i));
                if(line != data.getLast())
                    out.write('\n');
            }
        }
        catch(IOException exc){
            System.out.println(Arrays.toString(exc.getStackTrace()));
        }
    }

}
