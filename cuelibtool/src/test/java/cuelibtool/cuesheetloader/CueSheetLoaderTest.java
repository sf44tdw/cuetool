/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuelibtool.cuesheetloader;

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

/**
 *
 * @author normal
 */
public class CueSheetLoaderTest {

    public CueSheetLoaderTest() {
    }
    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());
    private CueSheetLoader cp;
    private LineNumberReader LNR1, LNR2, LNR3;
    private final Charset charset = Common.READ_CHARSET;
    
    private final File target_normal = Common.getTarget_normal();

    //トラック番号が連番ではない。
    private final File target_error_trackno = Common.getTarget_error_trackno();

    //時間が一定ではない。
    private final File target_error_time = Common.getTarget_error_time();

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
            this.LNR2 = new LineNumberReader(new InputStreamReader(new FileInputStream(target_error_trackno), charset));
            this.LNR3 = new LineNumberReader(new InputStreamReader(new FileInputStream(target_error_time), charset));
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
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}    

    @Test
    public void doTest() {
        CueSheetLoader cp1 = new CueSheetLoader(this.LNR1);
        cp1.load();
        log.info(cp1.toString());
        CueSheetLoader cp2 = new CueSheetLoader(this.LNR2);
        cp2.load();
        log.info(cp2.toString());
        CueSheetLoader cp3 = new CueSheetLoader(this.LNR3);
        cp3.load();
        log.info(cp3.toString());
    }

}
