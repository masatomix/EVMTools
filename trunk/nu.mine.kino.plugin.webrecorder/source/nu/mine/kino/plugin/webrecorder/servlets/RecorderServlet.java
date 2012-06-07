/******************************************************************************
 * Copyright (c) 2010 Masatomi KINO and others. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *      Masatomi KINO - initial API and implementation
 * $Id$
 ******************************************************************************/
//�쐬��: 2012/06/04

package nu.mine.kino.plugin.webrecorder.servlets;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import nu.mine.kino.plugin.webrecorder.WebRecorderPlugin;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.eclipse.jetty.servlets.ProxyServlet;

/**
 * ��{�I�ɂ�Jetty�̃v���L�V�@�\���Ăяo��Servlet�ł��B ProxyServlet������A
 * �ēxGet/Post���������炨���Ȃ��A���[�J���Ɍ��ʂ�ۑ�����@�\������܂��B
 * 
 * @author Masatomi KINO
 * @version $Revision$
 */
public class RecorderServlet extends ProxyServlet {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(RecorderServlet.class);

    private static final String METHOD_GET = "GET";

    private static final String METHOD_POST = "POST";

    private ServletContext servletContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        servletContext = config.getServletContext();
    }

    @Override
    public void service(ServletRequest request, ServletResponse response)
            throws ServletException, IOException {

        HttpServletRequest hRequest = (HttpServletRequest) request;
        String method = hRequest.getMethod();

        // Get�Ȃ�eServlet�����s���A�ēxGet������s���ăf�[�^��ۑ�����
        super.service(request, response);
        
        if (method.equals(METHOD_GET)) {
            executeGet(hRequest);
        } else if (method.equals(METHOD_POST)) {
            // Post���������eServlet�ł���ProxyServlet�̏������Ă�ŁA������xPost�������������A
            // ProxyServlet���s���RequestBody�̃R���e���c���Ď擾�ł��Ȃ��Ď����Ŏ��ɍs�������ō������B
            // �Ȃ̂ŁA�����̏�����D�悵�āA�ۑ������R���e���c����Response��Ԃ��悤�ɂ���
            // 2012/06/07�ǋL:
            // �Ď擾�ł���悤Filter�𒲐������̂ŁAsuper.service()�ɖ߂��܂���!
            executePost(hRequest);
            // �L���b�V������Ԃ�
            // File file = WebRecorderPlugin.getDefault()
            // .getCachePathFromRequestForPost(request);
            // if (file.exists()) {
            // returnFromCache(file.getAbsolutePath(), response);
            // }
        }
    }

    private void executePost(HttpServletRequest hRequest) throws IOException,
            ClientProtocolException {
        String body = WebRecorderPlugin.getDefault().getBody(hRequest);
        String url = getURLBase(hRequest);

        HttpPost httppost = new HttpPost(url);
        HttpClient httpclient = new DefaultHttpClient();

        String contentType = hRequest.getContentType();
        ContentType contentTypeObj = ContentType.parse(contentType);
        StringEntity postEntity = null;
        try {
            postEntity = new StringEntity(body, contentTypeObj);
        } catch (Exception e) {
            logger.warn("ContentType���w�肵��Post���悤�Ƃ���ƃG���[�ɂȂ����̂ŁA�w�肵�Ȃ���Post���邱�Ƃɂ���");
            postEntity = new StringEntity(body);
        }

        httppost.setEntity(postEntity);
        HttpResponse httpResponse = httpclient.execute(httppost);

        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            File file = WebRecorderPlugin.getDefault()
                    .getCachePathFromRequestForPost(hRequest);
            file.getParentFile().mkdirs();
            streamToFile(entity.getContent(), file);
        }
    }

    private String getURLBase(HttpServletRequest hRequest) {
        String requestURI = hRequest.getRequestURI();

        StringBuffer buf = new StringBuffer();
        buf.append(hRequest.getHeader("Host"));
        if (requestURI != null) {
            buf.append(requestURI);
        }
        String url = hRequest.getScheme() + "://" + new String(buf);
        return url;
    }

    private String getURLWithQuery(HttpServletRequest hRequest) {
        String baseURL = this.getURLBase(hRequest);
        String queryString = hRequest.getQueryString();
        if (queryString != null) {
            logger.debug("URL: " + baseURL);
            return baseURL + "?" + queryString;
        }
        logger.debug("URL: " + baseURL);
        return baseURL;
    }

    private void executeGet(HttpServletRequest hRequest) throws IOException,
            ClientProtocolException {
        String url = getURLWithQuery(hRequest);

        HttpGet httpget = new HttpGet(url);
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse httpResponse = httpclient.execute(httpget);
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            File file = WebRecorderPlugin.getDefault().getCachePathFromRequest(
                    hRequest);
            file.getParentFile().mkdirs();
            streamToFile(entity.getContent(), file);
        }
        // Enumeration<String> headerNames = hRequest.getHeaderNames();
        // System.out.println("-----");
        // while (headerNames.hasMoreElements()) {
        // String string = (String) headerNames.nextElement();
        // System.out.println(string + ": " + hRequest.getHeader(string));
        // }

        //
        // HttpClient provides URIBuilder utility class to simplify creation and
        // modification of request URIs.
        //
        // URIBuilder builder = new URIBuilder();
        // builder.setHost(hRequest.getHeader("Host")).setPath(path);
        // builder.setScheme("http").setHost("www.google.com").setPath("/search")
        // .setParameter("q", "httpclient")
        // .setParameter("btnG", "Google Search")
        // .setParameter("aq", "f")
        // .setParameter("oq", "");
        // URI uri = builder.build();
        // HttpGet httpget = new HttpGet(uri);
        // System.out.println(httpget.getURI());
    }

    private void streamToFile(InputStream in, File file) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int j;
        while ((j = in.read(b)) != -1)
            baos.write(b, 0, j);
        byte[] pix = baos.toByteArray();
        write(pix, file);
    }

    private void write(byte[] b, File file) {
        BufferedOutputStream stream = null;
        try {
            stream = new BufferedOutputStream(new FileOutputStream(file));
            stream.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }

}
