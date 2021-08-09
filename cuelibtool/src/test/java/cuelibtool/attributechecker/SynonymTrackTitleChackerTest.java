/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cuelibtool.attributechecker;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.Charset;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import common.Common;
import cuelibtool.cuesheetloader.CueSheetLoader;

/**
 *
 * @author uname
 */
public class SynonymTrackTitleChackerTest {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());
    private CueSheetLoader cp;
    private LineNumberReader LNR1, LNR2, LNR3;
    private final Charset charset = Common.READ_CHARSET;

    //エラーなし
    private final File target_normal = Common.getTarget_normal();
    //タイトル重複_隣のトラック
    private final File target_error_1 = Common.getTarget_error_double_track_01();
    //タイトル重複_離れたトラック
    private final File target_error_2 = Common.getTarget_error_double_track_02();

    public SynonymTrackTitleChackerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        try {
            this.LNR1 = new LineNumberReader(new InputStreamReader(new FileInputStream(target_normal), charset));
            this.LNR2 = new LineNumberReader(new InputStreamReader(new FileInputStream(target_error_1), charset));
            this.LNR3 = new LineNumberReader(new InputStreamReader(new FileInputStream(target_error_2), charset));
        } catch (IOException ex) {
            log.error("例外発生", ex);
        }
    }

    @After
    public void tearDown() {
        try {
            if (this.LNR1 != null) {
                this.LNR1.close();
            }
            if (this.LNR2 != null) {
                this.LNR2.close();
            }
            if (this.LNR3 != null) {
                this.LNR3.close();
            }
        } catch (IOException ex) {
            log.error("例外発生", ex);
        }
    }

    /**
     * Test of check method, of class SynonymTrackTitleChacker.
     */
    @Test
    public void testCheck() {
        System.out.println("check");
        boolean expResult;
        expResult = true;

        boolean ret;

        log.info("**********************************************************************");
        log.info("エラーなし");
        ret = testb(this.LNR1);
        log.info("結果= " + ret);
        assertEquals(ret, expResult);
        log.info("**********************************************************************");

        expResult = false;

        log.info("**********************************************************************");
        log.info("Titleが重複");
        log.info("隣のトラック");
        ret = testb(this.LNR2);
        log.info("結果= " + ret);
        assertEquals(ret, expResult);
        log.info("離れたトラック");
        ret = testb(this.LNR3);
        log.info("結果= " + ret);
        assertEquals(ret, expResult);
        log.info("**********************************************************************");

    }

    private boolean testb(LineNumberReader LNR) {
        CueSheetLoader cp = new CueSheetLoader(LNR);
        cp.load();
        log.info(cp.toString());
        SynonymTrackTitleChacker instance = new SynonymTrackTitleChacker();
        boolean result = instance.check(cp.getCueSheet());
        return result;
    }
}
