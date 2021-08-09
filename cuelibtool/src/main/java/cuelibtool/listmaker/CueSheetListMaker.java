/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cuelibtool.listmaker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.digitalmediaserver.cuelib.CueSheet;
import org.slf4j.bridge.SLF4JBridgeHandler;

import cuelibtool.attributechecker.AttributeChecker;
import cuelibtool.cuesheetloader.CueSheetLoader;
import cuelibtool.fileseeker.CueSheetFileSeeker;
import loggerconfigurator.LoggerConfigurator;

/**
 * 特定のディレクトリの下にあるCueSheetファイルを捜索し、チェックを行った後リストを作る。
 * 正常にパースできなかったか、チェックを通過しなかったファイルは無視される。
 * CueSheetに記載されているメディアファイルが実在するかどうかについては感知しない。
 *
 * @author uname
 */
public class CueSheetListMaker {

    static {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    private static final org.slf4j.Logger log = LoggerConfigurator.getlnstance().getCallerLogger();
    private final File root;
    private final Charset charset;
    private final List<AttributeChecker> checker;

    private final boolean recursive;

    private final class DataPair {

        private final File cueFile;
        private final CueSheet sheet;

        private DataPair(File cueFile, CueSheet sheet) {
            this.cueFile = cueFile;
            this.sheet = sheet;
        }

        public File getCueFile() {
            return cueFile;
        }

        public CueSheet getSheet() {
            return sheet;
        }
    }

    /**
     * @param root 検索するディレクトリ
     * @param charset ファイル読み込みの際の文字コード
     * @param checker チェックリスト
     * @param recursive サブディレクトリも検索するか。trueをセットすると検索する。
     */
    public CueSheetListMaker(File root, Charset charset, List<AttributeChecker> checker, boolean recursive) {
        this.root = root;
        this.charset = charset;
        List<AttributeChecker> ch_Temp = new ArrayList<>();
        ch_Temp.addAll(checker);
        this.checker = Collections.unmodifiableList(ch_Temp);
        this.recursive = recursive;
    }

    public final synchronized File getSourceDir() {
        return root;
    }

    /*
     * 特定のディレクトリの下にあるCueSheetファイルを捜索し、チェックを行った後リストを作る。
     * 正常にパースできなかったか、チェックを通過しなかったファイルは無視される。
     * また、コメント欄はCueSheetのフルパスに置き換えられる。
     *
     @return 正常終了 ロードしたCueSheetの一覧
     *
     * パスが空欄かCueSheetが見つからなかったかエラー null
     */
    public synchronized List<CueSheet> MakeCueSheetList() {
        List<CueSheet> cueSheets = Collections.synchronizedList(new ArrayList<CueSheet>());
        for (DataPair dp : this._MakeList()) {
            cueSheets.add(dp.getSheet());
        }
        return cueSheets;
    }

    /*
     * 特定のディレクトリの下にあるCueSheetファイルを捜索し、チェックを行った後リストを作る。
     * 正常にパースできなかったか、チェックを通過しなかったファイルは無視される。
     * また、コメント欄はCueSheetのフルパスに置き換えられる。
     *
     @return 正常終了 ロードしたCueSheetファイルの一覧
     *
     * パスが空欄かCueSheetが見つからなかったかエラー null
     */
    public synchronized List<File> MakeCueFileList() {
        List<File> cueSheetFiles = Collections.synchronizedList(new ArrayList<File>());
        for (DataPair dp : this._MakeList()) {
            cueSheetFiles.add(dp.getCueFile());
        }
        return cueSheetFiles;
    }

    private synchronized List<DataPair> _MakeList() {
        List<DataPair> dps = Collections.synchronizedList(new ArrayList<DataPair>());
        try {

            if (this.root.isDirectory()) {
                CueSheetFileSeeker seeker = new CueSheetFileSeeker(this.root);
                log.info("検索対象を発見しました。");
                log.info("探査開始。");

                seeker.setRecursive(recursive);

                if (seeker.isRecursive() == false) {
                    log.info("サブディレクトリ探査は行いません。");
                }

                if (seeker.isRecursive() == true) {
                    log.info("サブディレクトリ探査を行います。");
                }

                List<File> res = seeker.seek();
                log.info("ファイル数= {}", res.size());
                Iterator<File> it_F = res.iterator();
                while (it_F.hasNext()) {
                    File F = it_F.next();
                    log.info("チェック対象 = {}", F);
                    log.info("文字コード {}", charset);
                    try (LineNumberReader LNR = new LineNumberReader(new InputStreamReader(new FileInputStream(F), charset))) {
                        CueSheetLoader cl = new CueSheetLoader(LNR);
                        if (cl.load()) {
                            final CueSheet cs = cl.getCueSheet();
                            cs.setComment(F.getAbsolutePath());

                            if (this.check(cs, this.checker.iterator())) {
                                DataPair dp = new DataPair(F, cl.getCueSheet());
                                dps.add(dp);
                                log.info("リストに追加しました。");
                            } else {
                                log.warn("内容に不備があったため、リストに追加しませんでした。");
                            }

                        } else {
                            log.warn("パースに問題があったため、リストに追加しませんでした。");
                        }
                    }
                }
                return dps;
            } else {
                log.info("ディレクトリではない。");
                return null;
            }
        } catch (IOException e) {
            //例外が起きたときはnull
            log.error("例外発生。", e);
            return null;
        }
    }

    private synchronized boolean check(CueSheet cs, Iterator<AttributeChecker> it_C) {
        boolean ret = true;
        while (it_C.hasNext()) {
            AttributeChecker C = it_C.next();
            ret = ret & C.check(cs);
        }
        return ret;
    }

    public boolean isRecursive() {
        return recursive;
    }

}
