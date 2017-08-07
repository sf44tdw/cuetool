/*
 * Copyright (C) 2017 normal
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cuetool.loggerconfigurator;

import cuetool.loggerconfigurator.SuppressAnnotationTester.SUPPRESS_STATUS;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author normal
 */
public class SuppressLogTest {

    private static Logger LOG = LoggerFactory.getLogger(SuppressLogTest.class);

    public SuppressLogTest() {
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

    private class NotSuppress01 extends SuppressAnnotationTester {

        public NotSuppress01() {
            super(LoggerConfigurator.getCallerLogger(), SUPPRESS_STATUS.NOT_SUPPRESS);
        }
    }

    /**
     * Test of value method, of class SuppressLog.
     */
    @Test
    public void testNotSuppress01() {
        LOG.info("NotSuppress01");
        NotSuppress01 x = new NotSuppress01();
        assertTrue(x.isExpectedLogger());
    }

    @SuppressLog(false)
    private class NotSuppress02 extends SuppressAnnotationTester {

        public NotSuppress02() {
            super(LoggerConfigurator.getCallerLogger(), SUPPRESS_STATUS.NOT_SUPPRESS);
        }
    }

    /**
     * Test of value method, of class SuppressLog.
     */
    @Test
    public void testNotSuppress02() {
        LOG.info("NotSuppress02");
        NotSuppress02 x = new NotSuppress02();
        assertTrue(x.isExpectedLogger());
    }

    @SuppressLog
    private class Suppress01 extends SuppressAnnotationTester {

        public Suppress01() {
            super(LoggerConfigurator.getCallerLogger(), SUPPRESS_STATUS.SUPPRESS);
        }
    }

    /**
     * Test of value method, of class SuppressLog.
     */
    @Test
    public void Suppress01() {
        LOG.info("Suppress01");
        Suppress01 x = new Suppress01();
        assertTrue(x.isExpectedLogger());
    }

    @SuppressLog(true)
    private class Suppress02 extends SuppressAnnotationTester {

        public Suppress02() {
            super(LoggerConfigurator.getCallerLogger(), SUPPRESS_STATUS.SUPPRESS);
        }
    }

    /**
     * Test of value method, of class SuppressLog.
     */
    @Test
    public void Suppress02() {
        LOG.info("Suppress02");
        Suppress02 x = new Suppress02();
        assertTrue(x.isExpectedLogger());
    }

}
