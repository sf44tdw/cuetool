/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cuelibtool.attributechecker;

import org.digitalmediaserver.cuelib.CueSheet;

/**
 * CueSheetが実装した条件に適合するか調査し、適合すればtrueを返す。
 *
 * @author uname
 */
public interface AttributeChecker {

    public boolean check(CueSheet cs);
}
