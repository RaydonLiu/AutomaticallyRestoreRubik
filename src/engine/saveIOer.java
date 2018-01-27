package engine;

import java.io.*;

/**
 * Created by Liu on 2016/12/24 0024.
 */
public class saveIOer {
    private FileWriter fileWriter;
    private BufferedReader fileReader;
    private String path;
    public saveIOer(String path){
        this.path=path;
    }

    public void openFileWriter(){
        try {
            fileWriter=new FileWriter(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openFileRead(){
        try {
            fileReader=new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeFile(String data){
        try {
            fileWriter.write(data+"\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFile(){
        try {
            return fileReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void closeFileWriter(){
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeFileReader(){
        try {
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
