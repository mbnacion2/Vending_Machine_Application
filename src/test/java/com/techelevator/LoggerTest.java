package com.techelevator;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Scanner;

public class LoggerTest {
    @Test
    public void test_logger() {
        //test log() method
        File file = new File("log.txt");
        Logger.log("Logger test message");//test message to check in log.txt
        String line = "";
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
            }
            Assert.assertTrue(line.contains("Logger test message"));//check for test message in log
        } catch (Exception e){
            Assert.assertNull(e);
        }
    }
}
