package com.techelevator;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Scanner;

public class LoggerTest {
    @Test
    public void test_logger() {
        File file = new File("log.txt");
        Logger.log("Another Logger test message");
        String line = "";
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
            }
            Assert.assertTrue(line.contains("Logger test message"));
        } catch (Exception e){
            Assert.assertNull(e);
        }
    }
}
