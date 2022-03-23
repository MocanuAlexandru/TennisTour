package fileServices;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;

public class ReadService {
    private static ReadService ourInstance = new ReadService();

    //FUNCTII SINGLETON
    public static ReadService getInstance() {
        return ourInstance;
    }
    private ReadService() {
    }

    //FUNCTII SERVICE
    public LinkedList<String[]> importDataCSV(String filePath){
        LinkedList<String[]> result = new LinkedList<>();
        try(RandomAccessFile in = new RandomAccessFile(filePath, "r")){
            String aux;
            while((aux = in.readLine())!=null)
                result.add(aux.split(","));
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

}
