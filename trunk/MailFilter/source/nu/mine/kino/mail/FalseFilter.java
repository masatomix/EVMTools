/*******************************************************************************
 * Copyright (c) 2008 Masatomi KINO. All rights reserved. $Id$
 ******************************************************************************/
// �쐬��: 2008/05/19
package nu.mine.kino.mail;

import java.util.ArrayList;
import java.util.List;

public class FalseFilter implements IMailFilter {
    List<String> whiteList = new ArrayList<String>();

    public String doFilter(String mailData) throws FilterException {
        throw new FilterException("False Filter ���Ă΂�܂����B", mailData);
    }
}
