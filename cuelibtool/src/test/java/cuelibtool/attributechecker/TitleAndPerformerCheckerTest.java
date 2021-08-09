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
public class TitleAndPerformerCheckerTest {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());
    private CueSheetLoader cp;
    private LineNumberReader LNR1, LNR2, LNR3, LNR4, LNR5, LNR6, LNR7, LNR8, LNR9, LNR10, LNR11, LNR12, LNR13, LNR14, LNR15, LNR16, LNR17;
    private final Charset charset = Common.READ_CHARSET;
    //エラーなし
    private final File target_normal = Common.getTarget_normal();
    //Titleが空欄
    //アルバム
    private final File target_error_1 = Common.getTarget_error_1();
    //トラック
    private final File target_error_2 = Common.getTarget_error_2();
    //Performerが空欄
    //アルバム
    private final File target_error_3 = Common.getTarget_error_3();
    //トラック
    private final File target_error_4 = Common.getTarget_error_4();
    //Titleに禁止文字(<)あり。
    //アルバム
    private final File target_error_5 = Common.getTarget_error_5();
    //トラック
    private final File target_error_6 = Common.getTarget_error_6();
    //Titleに禁止文字(>)あり。
    //アルバム
    private final File target_error_7 = Common.getTarget_error_7();
    //トラック
    private final File target_error_8 = Common.getTarget_error_8();
    //Titleに禁止文字(～)あり。
    //アルバム
    private final File target_error_9 = Common.getTarget_error_9();
    //トラック
    private final File target_error_10 = Common.getTarget_error_10();
    //Performerに禁止文字(<)あり。
    //アルバム
    private final File target_error_11 = Common.getTarget_error_11();
    //トラック
    private final File target_error_12 = Common.getTarget_error_12();
    //Performerに禁止文字(>)あり。
    //アルバム
    private final File target_error_13 = Common.getTarget_error_13();
    //トラック
    private final File target_error_14 = Common.getTarget_error_14();
    //Performerに禁止文字(～)あり。
    //アルバム
    private final File target_error_15 = Common.getTarget_error_15();
    //トラック
    private final File target_error_16 = Common.getTarget_error_16();

    public TitleAndPerformerCheckerTest() {

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
            this.LNR4 = new LineNumberReader(new InputStreamReader(new FileInputStream(target_error_3), charset));
            this.LNR5 = new LineNumberReader(new InputStreamReader(new FileInputStream(target_error_4), charset));
            this.LNR6 = new LineNumberReader(new InputStreamReader(new FileInputStream(target_error_5), charset));
            this.LNR7 = new LineNumberReader(new InputStreamReader(new FileInputStream(target_error_6), charset));
            this.LNR8 = new LineNumberReader(new InputStreamReader(new FileInputStream(target_error_7), charset));
            this.LNR9 = new LineNumberReader(new InputStreamReader(new FileInputStream(target_error_8), charset));
            this.LNR10 = new LineNumberReader(new InputStreamReader(new FileInputStream(target_error_9), charset));
            this.LNR11 = new LineNumberReader(new InputStreamReader(new FileInputStream(target_error_10), charset));
            this.LNR12 = new LineNumberReader(new InputStreamReader(new FileInputStream(target_error_11), charset));
            this.LNR13 = new LineNumberReader(new InputStreamReader(new FileInputStream(target_error_12), charset));
            this.LNR14 = new LineNumberReader(new InputStreamReader(new FileInputStream(target_error_13), charset));
            this.LNR15 = new LineNumberReader(new InputStreamReader(new FileInputStream(target_error_14), charset));
            this.LNR16 = new LineNumberReader(new InputStreamReader(new FileInputStream(target_error_15), charset));
            this.LNR17 = new LineNumberReader(new InputStreamReader(new FileInputStream(target_error_16), charset));

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
            if (this.LNR4 != null) {
                this.LNR4.close();
            }
            if (this.LNR5 != null) {
                this.LNR5.close();
            }
            if (this.LNR6 != null) {
                this.LNR6.close();
            }
            if (this.LNR7 != null) {
                this.LNR7.close();
            }
            if (this.LNR8 != null) {
                this.LNR8.close();
            }
            if (this.LNR9 != null) {
                this.LNR9.close();
            }
            if (this.LNR10 != null) {
                this.LNR10.close();
            }
            if (this.LNR11 != null) {
                this.LNR11.close();
            }
            if (this.LNR12 != null) {
                this.LNR12.close();
            }
            if (this.LNR13 != null) {
                this.LNR13.close();
            }
            if (this.LNR14 != null) {
                this.LNR14.close();
            }
            if (this.LNR15 != null) {
                this.LNR15.close();
            }
            if (this.LNR16 != null) {
                this.LNR16.close();
            }
            if (this.LNR17 != null) {
                this.LNR17.close();
            }
        } catch (IOException ex) {
            log.error("例外発生", ex);
        }
    }

    /**
     * Test of check method, of class TitleAndPerformerChecker.
     */
    @Test
    public void testCheck() {
        System.out.println("check");
        boolean expResult;
        boolean ret;

        expResult = true;
        log.info("**********************************************************************");
        log.info("エラーなし");
        ret = testb(this.LNR1);
        log.info("結果= " + ret);
        assertEquals(ret, expResult);
        log.info("**********************************************************************");

        expResult = false;
        log.info("**********************************************************************");
        log.info("Titleが空欄");
        log.info("アルバム");
        ret = testb(this.LNR2);
        log.info("結果= " + ret);
        assertEquals(ret, expResult);
        log.info("トラック");
        ret = testb(this.LNR3);
        log.info("結果= " + ret);
        assertEquals(ret, expResult);
        log.info("**********************************************************************");

        log.info("**********************************************************************");
        log.info("Performerが空欄");
        log.info("アルバム");
        ret = testb(this.LNR4);
        log.info("結果= " + ret);
        assertEquals(ret, expResult);
        log.info("トラック");
        ret = testb(this.LNR5);
        log.info("結果= " + ret);
        assertEquals(ret, expResult);
        log.info("**********************************************************************");

        log.info("**********************************************************************");
        log.info("Titleに禁止文字(<)あり。");
        log.info("アルバム");
        ret = testb(this.LNR6);
        log.info("結果= " + ret);
        assertEquals(ret, expResult);
        log.info("トラック");
        ret = testb(this.LNR7);
        log.info("結果= " + ret);
        assertEquals(ret, expResult);
        log.info("**********************************************************************");

        log.info("**********************************************************************");
        log.info("Titleに禁止文字(>)あり。");
        log.info("アルバム");
        ret = testb(this.LNR8);
        log.info("結果= " + ret);
        assertEquals(ret, expResult);
        log.info("トラック");
        ret = testb(this.LNR9);
        log.info("結果= " + ret);
        assertEquals(ret, expResult);
        log.info("**********************************************************************");

        log.info("**********************************************************************");
        log.info("Titleに禁止文字(～)あり。");
        log.info("アルバム");
        ret = testb(this.LNR10);
        log.info("結果= " + ret);
        assertEquals(ret, expResult);
        log.info("トラック");
        ret = testb(this.LNR11);
        log.info("結果= " + ret);
        assertEquals(ret, expResult);
        log.info("**********************************************************************");

        log.info("**********************************************************************");
        log.info("Performerに禁止文字(<)あり。");
        log.info("アルバム");
        ret = testb(this.LNR12);
        log.info("結果= " + ret);
        assertEquals(ret, expResult);
        log.info("トラック");
        ret = testb(this.LNR13);
        log.info("結果= " + ret);
        assertEquals(ret, expResult);
        log.info("**********************************************************************");

        log.info("**********************************************************************");
        log.info("Performerに禁止文字(>)あり。");
        log.info("アルバム");
        ret = testb(this.LNR14);
        log.info("結果= " + ret);
        assertEquals(ret, expResult);
        log.info("トラック");
        ret = testb(this.LNR15);
        log.info("結果= " + ret);
        assertEquals(ret, expResult);
        log.info("**********************************************************************");

        log.info("**********************************************************************");
        log.info("Performerに禁止文字(～)あり。");
        log.info("アルバム");
        ret = testb(this.LNR16);
        log.info("結果= " + ret);
        assertEquals(ret, expResult);
        log.info("トラック");
        ret = testb(this.LNR17);
        log.info("結果= " + ret);
        assertEquals(ret, expResult);
        log.info("**********************************************************************");

    }

    private boolean testb(LineNumberReader LNR) {
        CueSheetLoader cp = new CueSheetLoader(LNR);
        cp.load();
        log.info(cp.toString());
        TitleAndPerformerChecker instance = new TitleAndPerformerChecker();
        boolean result = instance.check(cp.getCueSheet());
        return result;
    }
}
