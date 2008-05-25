/*******************************************************************************
 * Copyright (c) 2008 Masatomi KINO. All rights reserved. $Id$
 ******************************************************************************/
// �쐬��: 2008/05/19
package nu.mine.kino.mail;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.log4j.Logger;

public class FilterManager {
    private List<IMailFilter> filters = new LinkedList<IMailFilter>();

    private static Logger logger = Logger.getLogger(FilterException.class);

    public FilterManager() throws IOException {
        init();
    }

    private void init() {
        ExtendedProperties props = new ExtendedProperties();
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream resourceAsStream = loader
                    .getResourceAsStream("filter.properties");
            InputStream in = new BufferedInputStream(resourceAsStream);
            props.load(in);
        } catch (IOException e) {
            // �t�@�C�����Ȃ��Ƃ��ł��ˁB
            logger.error(e);
        }
        // �������ς�(load���������Ă���)�Ȃ�
        if (props.isInitialized()) {
            String[] stringArray = props.getStringArray("filterClass");
            for (String name : stringArray) {
                try {
                    IMailFilter filter = (IMailFilter) Class.forName(name)
                            .newInstance();
                    filters.add(filter);
                } catch (InstantiationException e) {
                    logger.error(e);
                } catch (IllegalAccessException e) {
                    logger.error(e);
                } catch (ClassNotFoundException e) {
                    logger.error(e);
                }
            }// �������ŃG���[�ɂȂ��Ă��A�Ƃ肠���������ݒ肵�Ȃ������Ƃ����ƂŐ�ɐi��
        }
    }

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
        if (!filters.isEmpty()) {
            String tmp = mailData;
            for (IMailFilter filter : filters) {
                logger.debug("���s����t�B���^��: " + filter.getClass().getName());
                tmp = filter.doFilter(tmp);
            }
            return tmp;
        }
        logger.warn("���s����t�B���^���ݒ肳��Ă��܂���ł���");
        return mailData;
    }
}
