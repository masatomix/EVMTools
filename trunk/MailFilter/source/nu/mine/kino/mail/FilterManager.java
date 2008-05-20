/*******************************************************************************
 * Copyright (c) 2008 Masatomi KINO. All rights reserved. $Id$
 ******************************************************************************/
// �쐬��: 2008/05/19
package nu.mine.kino.mail;

import java.util.LinkedList;
import java.util.List;

public class FilterManager {
    List<IMailFilter> filters = new LinkedList<IMailFilter>();

    public void addFilter(IMailFilter filter) {
        filters.add(filter);
    }

    /**
     * �Z�b�g���ꂽ�t�B���^���������s���郁�\�b�h�ł��B
     * 
     * @param mailData
     * @return
     * @throws FilterException
     */
    public String doFilters(String mailData) throws FilterException {
        String tmp = mailData;
        for (IMailFilter filter : filters) {
            tmp = filter.doFilter(tmp);
        }
        return tmp;
    }
}
