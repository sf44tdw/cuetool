/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cuelibtool.cuesheetloader;


import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Iterator;

import org.digitalmediaserver.cuelib.CueParser;
import org.digitalmediaserver.cuelib.CueSheet;
import org.digitalmediaserver.cuelib.CueSheetSerializer;
import org.digitalmediaserver.cuelib.Message;
import org.slf4j.bridge.SLF4JBridgeHandler;

import loggerconfigurator.LoggerConfigurator;

/**
 * CueSheetのパースとオブジェクトへの格納を行う。 また、CueSheetにパース段階の警告やエラーが含まれていないことをチェックする。<br>
 * 警告やエラーが含まれているCueSheetは破棄する。
 *
 * <>が含まれているCuesheetはパースできない。cuelibのwavecutterでテンプレートに使われているためと思われる。<br>
 *
 * パーサ側では、時間軸のエラーは拾ってくれない。さほど問題が無いので無視する。<br>
 *
 * @author uname
 */
public class CueSheetLoader {

    static {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }
    private final LineNumberReader LN;
    private CueSheet cs;
    private static final org.slf4j.Logger log = LoggerConfigurator.getlnstance().getCallerLogger();

    public CueSheetLoader(LineNumberReader LN) {
        this.LN = LN;
    }

    /**
     * 指定されたストリームからCueSheetをロードする。
     *
     * @return ロード成功時にtrueを返す。
     */
    public final synchronized boolean load() {
        this.cs = null;
        try {
            this.cs = CueParser.parse(this.LN);
            log.trace("メッセージの個数 ={}", cs.getMessages().size());
            if (checkMessages(this.cs.getMessages().iterator()) == true) {
                this.cs = null;
                log.error("警告が存在しているため、このCueSheetのロードは行われません。");
            }
            this.LN.close();
            if (this.cs == null) {
                return false;
            } else {
                return true;
            }
        } catch (IOException ex) {
            this.cs = null;
            log.error("CueSheetのロード中に例外が発生しました。", ex);
            return false;
        }
    }

    /**
     * ロードされたCueSheetに入っているメッセージに、下記以外のものが含まれているか確認する。<br>
     *
     * "The field is too long to burn as CD-TEXT. The maximum length is 80."<br>
     *
     * @return 無視対象以外のメッセージが含まれていればtrue
     */
    private synchronized boolean checkMessages(Iterator<Message> it_m) {
        boolean ret = false;
        final String ignore = "The field is too long to burn as CD-TEXT. The maximum length is 80.";
        while (it_m.hasNext()) {
            String s = it_m.next().toString();
            if (!s.contains(ignore)) {
                ret = true;
                log.error("CueSheetのロード中にエラーまたは警告がありました。 内容: {}", s);
            } else {
                log.info("無視対象メッセージです。 内容: {}", s);
            }
        }
        return ret;
    }

    /**
     * このクラスが保持するCueSheetを返す。
     *
     * @return CueSheet
     */
    public final synchronized CueSheet getCueSheet() {
        return cs;
    }

    @Override
    public synchronized String toString() {
        try {
            if (this.cs != null) {
                CueSheetSerializer CS = new CueSheetSerializer();
                return CS.serializeCueSheet(this.cs);
            } else {
                return "NULL";
            }
        } catch (Exception ex) {
            log.error("例外発生", ex);
            return "NULL";
        }
    }
}
