package com.techelevator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static PrintWriter logWriter= null;

    public static void log(String message){
        try{
            if(logWriter==null){
                File file= new File("log.txt");
                logWriter= new PrintWriter(new FileOutputStream(file, true));
            }
            logWriter.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a")) +" "+ message);
            logWriter.flush();



        }catch (Exception e){
            System.err.println("Error writing to Log File.");
        }
    }
}