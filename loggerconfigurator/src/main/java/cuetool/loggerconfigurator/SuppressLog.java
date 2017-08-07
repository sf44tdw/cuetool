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
package cuetool.loggerconfigurator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 特定のクラス内でのログ出力を抑止するアノテーション。引数は省略可能。<br>
 * ログを出力する<br>
 * -アノテーションなし<br>
 * -{@literal @}SuppressLog(false)<br>
ログを出力しない<br>
* -{@literal @}SuppressLog
* -{@literal @}SuppressLog(true)
 * @author normal
 */
@Retention(RetentionPolicy.RUNTIME) // プログラム全体で有効とする
@Target(ElementType.TYPE) // クラス宣言に対して使用する
public @interface SuppressLog {
    boolean value() default true; // 引数として真偽値を取る。省略時はtrue
}
