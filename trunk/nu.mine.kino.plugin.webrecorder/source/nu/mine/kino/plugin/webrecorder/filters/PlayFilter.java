/******************************************************************************
 * Copyright (c) 2008 Masatomi KINO and others. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *      Masatomi KINO - initial API and implementation
 * $Id$
 ******************************************************************************/
//�쐬��: 2012/06/03

package nu.mine.kino.plugin.webrecorder.filters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import nu.mine.kino.plugin.webrecorder.WebRecorderPlugin;

import org.apache.log4j.Logger;
import org.eclipse.jetty.util.IO;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class PlayFilter implements Filter {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(PlayFilter.class);

    private ServletContext servletContext;

    @Override
    public void destroy() {
        // TODO �����������ꂽ���\�b�h�E�X�^�u

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        logger.debug("doFilter(ServletRequest, ServletResponse, FilterChain) - start");

        File file = WebRecorderPlugin.getDefault().getCachePathFromRequest(
                request);

        if (file.exists()) {
            logger.info(file.getPath() + " ���������̂ŃL���b�V������Ԃ��܂�");
            returnFromCache(file.getAbsolutePath(), response);
        } else {
            logger.info(file.getPath() + " ���Ȃ������̂ŁA�T�[�o����擾���ĕԂ��܂�");
            chain.doFilter(request, response);
        }

        logger.debug("doFilter(ServletRequest, ServletResponse, FilterChain) - end");
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        servletContext = arg0.getServletContext();
    }

    private void returnFromCache(String filePath, ServletResponse response)
            throws FileNotFoundException, IOException {
        String mime = servletContext.getMimeType(filePath);
        if (mime != null) {
            response.setContentType(mime);
        }

        IO.copy(new FileInputStream(filePath), response.getOutputStream());
    }
}
