/*
 * 指定されたディレクトリ下のCueSheetファイルをリストアップする。
 */
package cuelibtool.FileSeeker;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 *
 */
public class CueSheetFileSeeker {

    static {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }
    private final File SourceDir;
    private final IOFileFilter suffix1 = FileFilterUtils.suffixFileFilter("cue");
    private final IOFileFilter suffix2 = FileFilterUtils.suffixFileFilter("Cue");
    private final IOFileFilter suffix3 = FileFilterUtils.suffixFileFilter("cUe");
    private final IOFileFilter suffix4 = FileFilterUtils.suffixFileFilter("cuE");
    private final IOFileFilter suffix5 = FileFilterUtils.suffixFileFilter("CUe");
    private final IOFileFilter suffix6 = FileFilterUtils.suffixFileFilter("cUE");
    private final IOFileFilter suffix7 = FileFilterUtils.suffixFileFilter("CuE");
    private final IOFileFilter suffix8 = FileFilterUtils.suffixFileFilter("CUE");

    IOFileFilter suffixFilter = FileFilterUtils.or(suffix1, suffix2, suffix3, suffix4, suffix5, suffix6, suffix7, suffix8);

    private IOFileFilter dirf = TrueFileFilter.INSTANCE;

    public CueSheetFileSeeker(File SourceDir) {
        this.SourceDir = SourceDir;
    }

    public synchronized boolean isRecursive() {
        boolean Ret = false;
        if (this.dirf instanceof TrueFileFilter) {
            Ret = true;
        } else if (this.dirf instanceof FalseFileFilter) {
            Ret = false;
        }
        return Ret;
    }

    /**
     * サブディレクトリも検索するか。trueをセットすると検索するようになる。デフォルトはtrue
     *
     * @param recursive セット対象
     */
    public synchronized void setRecursive(boolean recursive) {
        if (recursive == false) {
            this.dirf = FalseFileFilter.INSTANCE;
        } else {
            this.dirf = TrueFileFilter.INSTANCE;
        }
    }

    /**
     * 検索を行い、その結果を返す。
     *
     * @return 見つかったCueSheetファイルを示すファイルオブジェクトのリスト
     */
    public synchronized List<File> seek() {
        List<File> list = Collections.synchronizedList(new ArrayList<File>());
        Collection<File> files = FileUtils.listFiles(this.SourceDir, this.suffixFilter, this.dirf);
        list.addAll(files);
        return list;
    }
}
