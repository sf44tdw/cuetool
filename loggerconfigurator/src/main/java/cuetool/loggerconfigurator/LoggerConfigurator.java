package cuetool.loggerconfigurator;

/*
 * Copyright (C) 2017 normal
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.NOPLogger;

/**
 *
 *
 * @author dosdiaopfhj
 */
public final class LoggerConfigurator {

    private static final Logger LOG = LoggerFactory.getLogger(LoggerConfigurator.class);

    private LoggerConfigurator() {
    }

    /**
     * ロガーにクラス名を自動設定する。 クラスにアノテーションが指定されている場合、そのクラスのログは出さない。
     *
     * @return このメソッドを呼び出したクラスの名前をセットしたロガー
     */
    public final static synchronized Logger getCallerLogger() {
        final String className;
        final Class<?> target_class;
        try {
            className = new Throwable().getStackTrace()[1].getClassName();
            target_class = Class.forName(className);
        } catch (ClassNotFoundException ex) {
            LOG.error("呼び出し元が見つかりませんでした。NOPLoggerを返却します。", ex);
            return NOPLogger.NOP_LOGGER;
        }

        SuppressLog issup = target_class.getAnnotation(SuppressLog.class);
        if ((issup != null) && issup.value()) {
            LOG.debug("ログ出力抑止。クラス = {}",className);
            return NOPLogger.NOP_LOGGER;
        } else {
            LOG.debug("ログ出力。クラス = {}",className);
            return LoggerFactory.getLogger(className);
        }
    }

}