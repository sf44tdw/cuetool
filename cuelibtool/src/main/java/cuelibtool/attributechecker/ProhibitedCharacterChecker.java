/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuelibtool.attributeChecker;

import cuetool.loggerconfigurator.LoggerConfigurator;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.opf_labs.audio.CueSheet;
import org.opf_labs.audio.TrackData;
import org.slf4j.Logger;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * TitleとPerformer(アルバム、トラックとも)について、下記の調査を行う。 1:空欄ではない。 2:<>～のいずれかが含まれている。
 *
 * @author uname
 */
public class ProhibitedCharacterChecker implements AttributeChecker {

    static {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }
    private static final Logger log = LoggerConfigurator.getCallerLogger();
    //<>～のいずれかにマッチ
    private final String REGEX = "^.*[～<>].*$";
    private final Pattern p = Pattern.compile(REGEX);

    @Override
    public synchronized boolean check(CueSheet cs) {
        if (checkstring(cs.getTitle())) {
            return true;
        }

        if (checkstring(cs.getPerformer())) {
            return true;
        }
        log.info("Album(Title= ,{} Performer= {})", cs.getTitle(), cs.getPerformer());
        Iterator<TrackData> it_t = cs.getAllTrackData().iterator();
        return checkTrack(it_t);
    }

    private synchronized boolean checkTrack(Iterator<TrackData> it_t) {
        boolean ret = false;

        try {

            while (it_t.hasNext()) {
                TrackData td = it_t.next();
                log.info("Track{}(Title= ,{} Performer= {})", td.getNumber(), td.getTitle(), td.getPerformer());
                if (checkstring(td.getTitle()) == true || checkstring(td.getPerformer()) == true) {
                    ret = true;
                    break;
                }
            }

            log.info("判定結果  {}", ret);
            return ret;
        } catch (NullPointerException e) {
            log.warn("TitleもしくはPerformerが空欄です。");
            return false;
        }

    }

    /**
     * 文字列について、下記の調査を行う。 1:空ではない。 2:<>～のいずれかが含まれている。
     *
     * @param 調査する文字列。
     * @return 上記の条件に当てはまる場合はtrue
     */
    private synchronized boolean checkstring(String target) {
        if (!("".equals(target) || target == null)) {
            Matcher m = p.matcher(target);
            if (!m.find()) {
                log.info("空文字列ではなく、山括弧や波線は入っていません。");
                return false;
            } else {
                log.info("右の文字列に山括弧か波線が含まれています。 string={}", target);
                return true;
            }
        } else {
            log.warn("空文字列です。");
            return false;
        }
    }
}
