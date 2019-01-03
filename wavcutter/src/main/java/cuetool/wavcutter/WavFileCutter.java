/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cuetool.wavcutter;

import cuelibtool.attributeChecker.AttributeChecker;
import cuelibtool.attributeChecker.MediaFileNameChecker;
import cuelibtool.attributeChecker.SynonymTrackTitleChacker;
import cuelibtool.attributeChecker.TitleAndPerformerChecker;
import cuelibtool.listmaker.CueSheetListMaker;
import cuetool.loggerconfigurator.LoggerConfigurator;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.lang3.ArrayUtils;
import org.opf_labs.audio.CueSheet;
import org.opf_labs.audio.tools.trackcutter.TrackCutter;
import org.opf_labs.audio.tools.trackcutter.TrackCutterConfiguration;
import org.slf4j.Logger;

/**
 *
 * CueSheetに基づいて特定ディレクトリ下のwavファイル全てをトラックごとに分割する。 lameエンコード用コマンドラインも生成する。
 * ディレクトリ<br>
 * CueSheetの文字コード<br>
 * を指定する。 CueSheetの文字コードは指定されない場合はシステムのそれを使う。
 *
 * @author uname
 */
public class WavFileCutter {

    private static final Logger LOG = LoggerConfigurator.getCallerLogger();

    private static String dumpArgs(String[] args) {
        return ArrayUtils.toString(args, "引数なし。");
    }

    public static void main(String[] args) {
        try {
            new WavFileCutter().start(args);
            System.exit(0);
        } catch (Throwable ex) {
            LOG.error("エラー。 引数 = " + dumpArgs(args), ex);
            System.exit(1);
        }
    }

    public void start(String[] args) throws org.apache.commons.cli.ParseException {

        final Option directory = Option.builder("d")
                .required(true)
                .longOpt("directory")
                .desc("検索先のディレクトリ。")
                .hasArg(true)
                .type(String.class)
                .build();

        final Option recursive = Option.builder("r")
                .longOpt("recursive")
                .required(false)
                .desc("サブディレクトリも検索するか。このオプションを指定した場合は検索する。")
                .hasArg(false)
                .build();

        final Option charcode = Option.builder("c")
                .longOpt("charcode")
                .required(false)
                .desc("cuesheetの読み込みに使用する文字コード。省略したか、対応しない文字コードの場合はシステムの標準設定を選択する。")
                .hasArg(true)
                .type(String.class)
                .build();

        final Options opts = new Options();
        opts.addOption(directory);
        opts.addOption(recursive);
        opts.addOption(charcode);

        CommandLineParser parser = new DefaultParser();

        HelpFormatter help = new HelpFormatter();
        CommandLine cl;
        try {
            cl = parser.parse(opts, args);
        } catch (org.apache.commons.cli.ParseException ex) {
            help.printHelp("使用法", opts);
            throw ex;
        }

        final String dirstr;
        final File source;
        if (cl.hasOption(directory.getOpt())) {
            dirstr = cl.getOptionValue(directory.getOpt());
        } else {
            dirstr = "";
            throw new IllegalArgumentException("検索先ディレクトリが設定されていません。");
        }
        String afterStr = null;
        try {
            //文字コード変換
            afterStr = new String(dirstr.getBytes(Charset.defaultCharset()), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            LOG.error("文字コード変換に失敗しました。", ex);
        }

        LOG.info("取得した検索先 = {}", afterStr);

        source = new File(afterStr);
        if (!source.isDirectory()) {
            throw new IllegalArgumentException("検索先がディレクトリではありません。 " + source.getAbsolutePath());
        }

        final boolean isRecursive;
        if (cl.hasOption(recursive.getOpt())) {
            isRecursive = true;
        } else {
            isRecursive = false;
        }
        LOG.info("サブディレクトリ検索 = {}", isRecursive);

        final String cs;
        if (cl.hasOption(charcode.getOpt())) {
            cs = cl.getOptionValue(charcode.getOpt());
        } else {
            cs = "";
        }
        final Charset charset;
        Charset temp_charset = null;
        try {
            //文字コードの設定
            if (!("".equals(cs))) {
                temp_charset = Charset.forName(cs);
            } else {
                LOG.info("CueSheetの文字コード指定にシステムのデフォルト値を使用します。");
                temp_charset = Charset.defaultCharset();
            }
        } catch (UnsupportedCharsetException | IllegalCharsetNameException ex) {
            LOG.warn("CueSheetの文字コード指定が正しくありません。システムのデフォルト値を使用します。", ex);
            temp_charset = Charset.defaultCharset();
        } finally {
            charset = temp_charset;
            LOG.info("CueSheetの文字コード指定 {}", charset.name());
        }

        final List<AttributeChecker> checker = new ArrayList<>();

        checker.add(
                new MediaFileNameChecker());
        checker.add(
                new SynonymTrackTitleChacker());
        checker.add(
                new TitleAndPerformerChecker());

        CueSheetListMaker instance = new CueSheetListMaker(source, charset, Collections.unmodifiableList(checker), isRecursive);
        List<org.opf_labs.audio.CueSheet> dest = instance.MakeCueSheetList();
        if (!(dest.isEmpty())) {

            Iterator<org.opf_labs.audio.CueSheet> i = dest.iterator();
            while (i.hasNext()) {
                LOG.info("切断器を生成");
                try {
                    CueSheet sheet = i.next();
                    TrackCutterConfiguration configuration = new TrackCutterConfiguration();
                    configuration.setParentDirectory(new File(sheet.getComment()).getParentFile());
                    configuration.setCutFileNameTemplate("<track>_<artist>_<title>.wav");
                    configuration.setDoPostProcessing(true);
                    configuration.setPostProcessFileNameTemplate("<track>_<artist>_<title>.mp3");
                    configuration.setPostProcessCommandTemplate("lame.exe --vbr-new -V 0 -t --tt \"<title>\" --ta \"<artist>\" --tl \"<album>\" --ty \"<year>\" --tc \"<comment>\" --tn \"<track>\" --tg \"<genre>\" \"<cutFile>\" \"<postProcessFile>\"");

                    TrackCutter TC = new TrackCutter(configuration);
                    TC.cutTracksInCueSheet(sheet);

                } catch (IOException ex) {
                    LOG.error("error", ex);
                }
            }

        } else {
            LOG.info("CueSheetなし。");
        }

    }

}
