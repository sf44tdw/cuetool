/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cuelibtool.attributechecker;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.digitalmediaserver.cuelib.CueSheet;
import org.digitalmediaserver.cuelib.TrackData;
import org.slf4j.Logger;

import loggerconfigurator.LoggerConfigurator;

/**
 * TitleとPerformer(アルバム、トラックとも)について、下記の調査を行う。 1:空欄ではない。 2:<>～のいずれも含まれていない。
 *
 * @author uname
 */
public class TitleAndPerformerChecker implements AttributeChecker {

    private static final Logger log = LoggerConfigurator.getlnstance().getCallerLogger();
    //<>～のいずれかにマッチ
    private final String REGEX = "^.*[～<>].*$";
    private final Pattern p = Pattern.compile(REGEX);

    @Override
    public synchronized boolean check(CueSheet cs) {
        if (checkstring(cs.getTitle())) {
            log.info("Album (Title) は問題ありません。");
        } else {
            return false;
        }
        if (checkstring(cs.getPerformer())) {
            log.info("Album (Performer) は問題ありません。");
        } else {
            return false;
        }
        log.info("Album(Title= ,{} Performer= {})", cs.getTitle(), cs.getPerformer());
        Iterator<TrackData> it_t = cs.getAllTrackData().iterator();
        return checkTrack(it_t);
    }

    private synchronized boolean checkTrack(Iterator<TrackData> it_t) {
        boolean ret = true;

        try {
            CHECK:
            {
                while (it_t.hasNext()) {
                    TrackData td = it_t.next();
                    log.info("Track{}(Title= ,{} Performer= {})", td.getNumber(), td.getTitle(), td.getPerformer());

                    if (checkstring(td.getTitle())) {
                    } else {
                        ret = false;
                        break CHECK;
                    }
                    if (checkstring(td.getPerformer())) {
                        ret = ret && true;
                    } else {
                        ret = false;
                        break CHECK;
                    }
                }
            };
            log.info("判定結果  {}", ret);
            return ret;
        } catch (NullPointerException e) {
            log.warn("TitleもしくはPerformerが空欄です。");
            return false;
        }

    }

    /**
     * 文字列について、下記の調査を行う。 1:空ではない。 2:<>～のいずれも含まれていない。
     *
     * @param 調査する文字列。
     * @return 上記の条件に当てはまる場合はtrue
     */
    private synchronized boolean checkstring(String target) {
        if (!("".equals(target) || target == null)) {
            Matcher m = p.matcher(target);
            if (!m.find()) {
                log.info("空文字列ではなく、山括弧や波線は入っていません。");
                return true;
            } else {
                log.warn("右の文字列に山括弧か波線が含まれています。 string={}", target);
                return false;
            }
        } else {
            log.warn("空文字列です。");
            return false;
        }
    }
}
