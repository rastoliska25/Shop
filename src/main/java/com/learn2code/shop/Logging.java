package com.learn2code.shop;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class Logging {
    public static final Logger logger = Logger.getLogger(Logger.class);

    final static String PATTERN = "%d [%p|%c%C{1}] %m%n";

    static ConsoleAppender consoleAppender = new ConsoleAppender();

    public static void setAppender(){
        consoleAppender.setThreshold(Level.ALL);  //level
        consoleAppender.setLayout(new PatternLayout(PATTERN));  //layout s navrhnutym vzorom
        consoleAppender.activateOptions();
        Logger.getRootLogger().addAppender(consoleAppender); //kedykovlek vytvorim instanciu classy Logger - bude pouzity rootLogger
    }
}
