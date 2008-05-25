/*******************************************************************************
 * Copyright (c) 2008 Masatomi KINO. All rights reserved. $Id$
 ******************************************************************************/
// �쐬��: 2008/05/19
package nu.mine.kino.mail;

public class FilterException extends Exception {

    /**
     * <code>serialVersionUID</code> �̃R�����g
     */
    private static final long serialVersionUID = -4693696991817546876L;

    private final String mailData;

    public FilterException(String s, String mailData) {
        super(s);
        this.mailData = mailData;
    }

    public String getMailData() {
        return mailData;
    }

}
