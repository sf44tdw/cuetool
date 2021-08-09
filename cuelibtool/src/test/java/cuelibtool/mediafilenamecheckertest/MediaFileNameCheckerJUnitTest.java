/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cuelibtool.mediafilenamecheckertest;

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
import cuelibtool.attributechecker.AttributeChecker;
import cuelibtool.attributechecker.MediaFileNameChecker;
import cuelibtool.cuesheetloader.CueSheetLoader;

/**
 *
 * @author uname
 */
public class MediaFileNameCheckerJUnitTest {

    private  final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());
    private CueSheetLoader cp;
    private LineNumberReader LNR1, LNR2;
    private Charset charset = Common.READ_CHARSET;
    private File target_normal = Common.getTarget_normal();

    //ファイルがwavではなくape
    private File target_apefile =Common.getTarget_apefile();

    public MediaFileNameCheckerJUnitTest() {
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
            this.LNR2 = new LineNumberReader(new InputStreamReader(new FileInputStream(target_apefile), charset));
        } catch (IOException ex) {
            log.error( "例外発生", ex);
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
        } catch (IOException ex) {
            log.error( "例外発生", ex);
        }
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //

    @Test
    public void hello() {
        AttributeChecker CK = new MediaFileNameChecker();

        CueSheetLoader cp1 = new CueSheetLoader(this.LNR1);
        cp1.load();
        log.info(cp1.toString());
        log.info("チェック結果" + CK.check(cp1.getCueSheet()));

        CueSheetLoader cp2 = new CueSheetLoader(this.LNR2);
        cp2.load();
        log.info(cp2.toString());
        log.info("チェック結果" + CK.check(cp2.getCueSheet()));

    }
}
