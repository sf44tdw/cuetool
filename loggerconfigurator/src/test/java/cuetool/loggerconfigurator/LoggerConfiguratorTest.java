/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuetool.loggerconfigurator;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;

/**
 *
 * @author normal
 */
public class LoggerConfiguratorTest {
    
    public LoggerConfiguratorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getCallerLogger method, of class LoggerConfigurator.
     * @throws java.lang.ClassNotFoundException
     */
    @Test
    public void testGetCallerLogger() throws ClassNotFoundException {
        Logger result = LoggerConfigurator.getCallerLogger();
        assertTrue(result!=null);
        result.info("getCallerLogger");
    }
    
}
