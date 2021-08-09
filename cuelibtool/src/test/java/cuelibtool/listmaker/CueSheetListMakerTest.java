/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cuelibtool.listmaker;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.digitalmediaserver.cuelib.CueSheet;
import org.digitalmediaserver.cuelib.CueSheetSerializer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import common.Common;
import cuelibtool.attributechecker.AttributeChecker;
import cuelibtool.attributechecker.MediaFileNameChecker;
import cuelibtool.attributechecker.SynonymTrackTitleChacker;
import cuelibtool.attributechecker.TitleAndPerformerChecker;

/**
 *
 * @author uname
 */
public class CueSheetListMakerTest {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());
    private Charset charset = Common.READ_CHARSET;
    private File DIR;
    private List<AttributeChecker> checker = new ArrayList<>();

    public CueSheetListMakerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.checker.add(new MediaFileNameChecker());
        this.checker.add(new SynonymTrackTitleChacker());
        this.checker.add(new TitleAndPerformerChecker());
        this.DIR = Common.getTestDir();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getSourceDir method, of class CueSheetListMaker.
     */
    @Test
    public void testGetSourceDir() {
        System.out.println("getSourceDir");
        CueSheetListMaker instance = new CueSheetListMaker(this.DIR, this.charset, this.checker, false);
        File expResult = Common.getTestDir();
        File result = instance.getSourceDir();
        assertEquals(expResult, result);
    }

    /**
     * Test of MakeCueSheetList method, of class CueSheetListMaker.
     */
    @Test
    public void testMakeCueSheetList() {
        System.out.println("MakeCueSheetList");
        CueSheetListMaker instance = new CueSheetListMaker(this.DIR, this.charset, this.checker, false);

        List<CueSheet> dest;
        dest = instance.MakeCueSheetList();
        CueSheetSerializer CS = new CueSheetSerializer();
        Iterator<CueSheet> it_cu = dest.iterator();
        while (it_cu.hasNext()) {
            CueSheet c = it_cu.next();
            log.info("メッセージ件数 = " + c.getMessages().size());
            log.info(CS.serializeCueSheet(c));
        }
        //正しいcuesheetと時間軸に問題のあるCueSheetが1件ずつ残る。
        assertEquals(dest.size(), 2);
    }

    /**
     * Test of MakeCueFileList method, of class CueSheetListMaker.
     */
    @Test
    public void testMakeCueFileList() {
        System.out.println("MakeCueFileList");
        CueSheetListMaker instance = new CueSheetListMaker(this.DIR, this.charset, this.checker, false);
        List<File> expResult = new ArrayList<File>();
        final File target_normal = Common.getTarget_normal();

        //時間が一定ではない。
        final File target_error_time = Common.getTarget_error_time();

        expResult.add(Common.getTarget_error_time());
        expResult.add(Common.getTarget_normal());

        List<File> result = instance.MakeCueFileList();

        Iterator<File> it_result = result.iterator();
        Iterator<File> it_expResult = expResult.iterator();

        while (it_result.hasNext() && it_expResult.hasNext()) {
            File res = it_result.next();
            File expRes = it_expResult.next();
            log.info("{} = {}", res.getAbsolutePath(), expRes.getAbsolutePath());
            if (!res.getAbsolutePath().equals(expRes.getAbsolutePath())) {
                fail("リストの内容が想定と違います。");
            }
        }
    }

    /**
     * Test of isRecursive method, of class CueSheetListMaker.
     */
    @Test
    public void testIsRecursive() {
        System.out.println("isRecursive");
        CueSheetListMaker instance = new CueSheetListMaker(this.DIR, this.charset, this.checker, false);
        boolean expResult = false;
        boolean result = instance.isRecursive();
        assertEquals(expResult, result);
    }
}
