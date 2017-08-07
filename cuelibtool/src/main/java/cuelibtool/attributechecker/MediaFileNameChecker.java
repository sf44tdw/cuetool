/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cuelibtool.attributeChecker;

import cuetool.loggerconfigurator.LoggerConfigurator;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.opf_labs.audio.CueSheet;
import org.opf_labs.audio.FileData;
import org.slf4j.Logger;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * CueSheetのFILE項目がwavファイルであるか調査する。
 *
 * @author uname
 */
public class MediaFileNameChecker implements AttributeChecker {

    static {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }
    private static final Logger log = LoggerConfigurator.getCallerLogger();
    
    private final String REGEX = "^.*\\.wav";
    private final Pattern p = Pattern.compile(REGEX);

    @Override
    public synchronized boolean check(CueSheet cs) {
        boolean ret = true;
        Iterator<FileData> I_FDS = cs.getFileData().iterator();
        while (I_FDS.hasNext()) {
            FileData FD = I_FDS.next();
            Matcher m = p.matcher(FD.getFile());
            ret = ret && m.find();
            if (log.isDebugEnabled()) {
                log.debug("Filename= {} Match= {} Return= {}", FD.getFile(), m.find(), ret);
            }
        }
        if (ret == true) {
            log.info("wavファイルが指定されています。");
        } else {
            log.warn("wavファイル以外が指定されています。");
        }
        return ret;
    }
}
