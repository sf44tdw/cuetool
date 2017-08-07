/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cuelibtool.attributeChecker;

import cuetool.loggerconfigurator.LoggerConfigurator;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.opf_labs.audio.CueSheet;
import org.opf_labs.audio.TrackData;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * Trackのtitleの内容が重複している(多分曲名をつけていない)CueSheetを見つける。
 * (ファイルを切り分けるときに同じ名前のファイルになってしまうため。)
 *
 * @author uname
 */
public class SynonymTrackTitleChacker implements AttributeChecker {

    static {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    private static final org.slf4j.Logger log = LoggerConfigurator.getCallerLogger();

    @Override
    public final synchronized boolean check(CueSheet cs) {
        boolean Ret = true;
        try {
            //タイトルの重複を検査するためのマップ。
            Set<String> testSet = Collections.synchronizedSet(new HashSet<>());
            Iterator<TrackData> it_t;
            it_t = cs.getAllTrackData().iterator();
            while (it_t.hasNext()) {
                TrackData TD = it_t.next();

                final String s;
                if (TD.getTitle() != null) {
                    s = TD.getTitle();
                    if (testSet.contains(s) == false) {
                        testSet.add(TD.getTitle());
                        log.info("タイトルは重複していません。 {}", TD.getTitle());
                        log.info("正常");
                        Ret = Ret & true;
                    } else {
                        log.info("タイトルが重複しています。  {}", TD.getTitle());
                        Ret = Ret & false;
                    }
                } else {
                    log.info("タイトルがセットされていません。");
                    return false;
                }

            }
            log.info("判定結果  {}", Ret);
            return Ret;
        } catch (Exception e) {
            log.error(" エラーです。", e);
            return false;
        }
    }
}
