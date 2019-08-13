import java.io.*;

public class Main {

    public static void main(String[] args) {
        try {
        String fileName = "D:\test";
        InputStream inStream = null;
        OutputStream outStream = null;

        outStream = new FileOutputStream(fileName);
        outStream = new BufferedOutputStream(outStream);

        for (int i = 1000000; --i>=0;){
            outStream.write(i);
        }
        outStream.close();
        
        inStream = new FileInputStream(fileName);
        inStream = new BufferedInputStream(inStream);

        while (inStream.read() != -1){

        }
        inStream.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
