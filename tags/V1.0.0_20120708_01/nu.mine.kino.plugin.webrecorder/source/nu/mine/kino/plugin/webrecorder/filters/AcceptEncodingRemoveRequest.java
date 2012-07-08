/******************************************************************************
 * Copyright (c) 2012 Masatomi KINO and others. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *      Masatomi KINO - initial API and implementation
 * $Id$
 ******************************************************************************/
//�쐬��: 2012/06/07

package nu.mine.kino.plugin.webrecorder.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import nu.mine.kino.plugin.commons.utils.StringUtils;

/**
 * Accept Encoding����������Request Wrapper�B �N���C�A���g��Accept-Encoding: gzip,deflate
 * ���w�肵�Ă���ƁAGzip���k���ꂽ�R���e���c������Ă��āA�t�@�C���ۑ��̃v���O����{@link ServletOutputStreamImpl}
 * ���Ή��ł��Ă��Ȃ��B�Ȃ̂ŁA�b���Accept-Encoding �w�b�_���������鏈�������Ă���B
 * 
 * @author Masatomi KINO
 * @version $Revision$
 */
public class AcceptEncodingRemoveRequest extends HttpServletRequestWrapper {

    public AcceptEncodingRemoveRequest(HttpServletRequest httpServletRequest)
            throws IOException {
        super(httpServletRequest);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        if (!StringUtils.isEmpty(name) && name.equals("Accept-Encoding")) {
            return Collections.enumeration(Collections.EMPTY_LIST);
        }
        return super.getHeaders(name);
    }

    @Override
    public String getHeader(String name) {
        if (!StringUtils.isEmpty(name) && name.equals("Accept-Encoding")) {
            return null;
        }
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        List<String> retList = new ArrayList<String>();
        Enumeration<String> headerNames = super.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String header = (String) headerNames.nextElement();
            if (!header.equals("Accept-Encoding")) {
                retList.add(header);
            }
        }
        return Collections.enumeration(retList);
    }
}
