/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.File;
import java.nio.charset.Charset;

/**
 *
 * @author normal
 */
public class Common {

    public static final Charset READ_CHARSET = Charset.forName("JISAutoDetect");

    private static final String TEST_DIRECTORY_NAME = "testdata";

    public static File getTestDir() {
        return new File(Common.TEST_DIRECTORY_NAME);
    }

    //問題のないファイル
    private static final File target_normal = new File(Common.getTestDir(), "Testdata_normal.cue");

    //トラック番号が連番ではない。
    private static final File target_error_trackno = new File(Common.getTestDir(), "Testdata_errorTrackNo.cue");

    //時間が一定ではない。
    private static final File target_error_time = new File(Common.getTestDir(), "Testdata_errorTimeLine.cue");

    //ファイルがwavではなくape
    private static final File target_apefile = new File(Common.getTestDir(), "Testdata_apeFile.cue");

    //タイトル重複_隣のトラック
    private static final File target_error_double_track_01 = new File(Common.getTestDir(), "Testdata_error_t_syn_t_ne.cue");

    //タイトル重複_離れたトラック
    private static final File target_error_double_track_02 = new File(Common.getTestDir(), "Testdata_error_t_syn_t_se.cue");

    //Titleが空欄
    //アルバム
    private static final File target_error_1 = new File(Common.getTestDir(), "Testdata_error_blankalbumtitle.cue");
    //トラック
    private static final File target_error_2 = new File(Common.getTestDir(), "Testdata_error_blanktracktitle.cue");
    //Performerが空欄
    //アルバム
    private static final File target_error_3 = new File(Common.getTestDir(), "Testdata_error_blankalbumperformer.cue");
    //トラック
    private static final File target_error_4 = new File(Common.getTestDir(), "Testdata_error_blanktrackperformer.cue");
    //Titleに禁止文字(<)あり。
    //アルバム
    private static final File target_error_5 = new File(Common.getTestDir(), "Testdata_error_a_pc_t_mp_l.cue");
    //トラック
    private static final File target_error_6 = new File(Common.getTestDir(), "Testdata_error_t_pc_t_mp_l.cue");
    //Titleに禁止文字(>)あり。
    //アルバム
    private static final File target_error_7 = new File(Common.getTestDir(), "Testdata_error_a_pc_t_mp_r.cue");
    //トラック
    private static final File target_error_8 = new File(Common.getTestDir(), "Testdata_error_t_pc_t_mp_r.cue");
    //Titleに禁止文字(～)あり。
    //アルバム
    private static final File target_error_9 = new File(Common.getTestDir(), "Testdata_error_a_pc_t_w.cue");
    //トラック
    private static final File target_error_10 = new File(Common.getTestDir(), "Testdata_error_t_pc_t_w.cue");
    //Performerに禁止文字(<)あり。
    //アルバム
    private static final File target_error_11 = new File(Common.getTestDir(), "Testdata_error_a_pc_p_mp_l.cue");
    //トラック
    private static final File target_error_12 = new File(Common.getTestDir(), "Testdata_error_t_pc_p_mp_l.cue");
    //Performerに禁止文字(>)あり。
    //アルバム
    private static final File target_error_13 = new File(Common.getTestDir(), "Testdata_error_a_pc_p_mp_r.cue");
    //トラック
    private static final File target_error_14 = new File(Common.getTestDir(), "Testdata_error_t_pc_p_mp_r.cue");
    //Performerに禁止文字(～)あり。
    //アルバム
    private static final File target_error_15 = new File(Common.getTestDir(), "Testdata_error_a_pc_p_w.cue");
    //トラック
    private static final File target_error_16 = new File(Common.getTestDir(), "Testdata_error_t_pc_p_w.cue");

   public static final File getTarget_normal() {
        return Common.getCopyFile(target_normal);
    }

   public static final File getTarget_error_trackno() {
        return Common.getCopyFile(target_error_trackno);
    }

   public static final File getTarget_error_time() {
        return Common.getCopyFile(target_error_time);
    }

   public static final File getTarget_apefile() {
        return Common.getCopyFile(target_apefile);
    }

   public static final File getTarget_error_double_track_01() {
        return Common.getCopyFile(target_error_double_track_01);
    }

   public static final File getTarget_error_double_track_02() {
        return Common.getCopyFile(target_error_double_track_02);
    }

   public static final File getTarget_error_1() {
        return Common.getCopyFile(target_error_1);
    }

   public static final File getTarget_error_2() {
        return Common.getCopyFile(target_error_2);
    }

   public static final File getTarget_error_3() {
        return Common.getCopyFile(target_error_3);
    }

   public static final File getTarget_error_4() {
        return Common.getCopyFile(target_error_4);
    }

   public static final File getTarget_error_5() {
        return Common.getCopyFile(target_error_5);
    }

   public static final File getTarget_error_6() {
        return Common.getCopyFile(target_error_6);
    }

   public static final File getTarget_error_7() {
        return Common.getCopyFile(target_error_7);
    }

   public static final File getTarget_error_8() {
        return Common.getCopyFile(target_error_8);
    }

   public static final File getTarget_error_9() {
        return Common.getCopyFile(target_error_9);
    }

   public static final File getTarget_error_10() {
        return Common.getCopyFile(target_error_10);
    }

   public static final File getTarget_error_11() {
        return Common.getCopyFile(target_error_11);
    }

   public static final File getTarget_error_12() {
        return Common.getCopyFile(target_error_12);
    }

   public static final File getTarget_error_13() {
        return Common.getCopyFile(target_error_13);
    }

   public static final File getTarget_error_14() {
        return Common.getCopyFile(target_error_14);
    }

   public static final File getTarget_error_15() {
        return Common.getCopyFile(target_error_15);
    }

   public static final File getTarget_error_16() {
        return Common.getCopyFile(target_error_16);
    }

    private static final File getCopyFile(File target) {
        return new File(target.getAbsolutePath());
    }
}
