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
//�쐬��: 2012/07/07

package nu.mine.kino.plugin.webrecorder.filters;

import static nu.mine.kino.plugin.webrecorder.ProxyConstant.EXCEPT_EXTs;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nu.mine.kino.plugin.commons.utils.StringUtils;
import nu.mine.kino.plugin.webrecorder.HttpRequestUtils;
import nu.mine.kino.plugin.webrecorder.models.RequestResponseModel;
import nu.mine.kino.plugin.webrecorder.views.ListView;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class ViewAddFilter implements Filter {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(ViewAddFilter.class);

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        logger.debug("doFilter(ServletRequest, ServletResponse, FilterChain) - start");

        HttpServletRequest hRequest = (HttpServletRequest) request;
        HttpServletResponse hResponse = (HttpServletResponse) response;

        chain.doFilter(request, response);

        String url = HttpRequestUtils.getURLBase(hRequest);// URL�𕜌�

        boolean isExcept = StringUtils.isEmpty(url)
                || StringUtils.endsWith(url, EXCEPT_EXTs);
        if (!isExcept) {
            // 2012/07/09
            // Response���ł�������O�ɃR�R�ɗ��Ă��܂��̂ŁAResponse�w�b�_���Z�b�g�����܂ő҂B�B�z���g�ɂ��������R�[�h�ł����̂��^��(�L�t`;)
            int counter = 0;
            while ((hResponse.getHeaderNames().size() == 0)) {
                counter++;
                String requestURI = hRequest.getRequestURI();
                if (counter >= 10) {// 10��J��Ԃ��ă_���Ȃ�A������߂�
                    logger.debug(counter + " ��ځB�܂�Response�w�b�_���Z�b�g����ĂȂ���������߁B: "
                            + requestURI);
                    break;
                }
                try {
                    logger.debug(counter + " ��ځB�܂�Response�w�b�_���Z�b�g����ĂȂ��̂ŁA�҂��B: "
                            + requestURI);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    logger.error(
                            "doFilter(ServletRequest, ServletResponse, FilterChain)",
                            e);
                    // TODO �����������ꂽ catch �u���b�N
                    e.printStackTrace();
                }
            }

            RequestResponseModel model = convert(hRequest, hResponse);
            addInformation(model);
        }

        logger.debug("doFilter(ServletRequest, ServletResponse, FilterChain) - end");
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO �����������ꂽ���\�b�h�E�X�^�u

    }

    // private void addInformation(final HttpServletResponse hResponse) {
    // checkAsyncExec(new Runnable() {
    // @Override
    // public void run() {
    // IWorkbenchPage workbenchPage = PlatformUI.getWorkbench()
    // .getActiveWorkbenchWindow().getActivePage();
    // try {
    // ListView view = (ListView) workbenchPage
    // .showView(ListView.ID);
    // view.addRequestResponseModel(new RequestResponseModel());
    // } catch (PartInitException e) {
    // // TODO �����������ꂽ catch �u���b�N
    // e.printStackTrace();
    // }
    //
    // }
    // });
    // }

    private void addInformation(final RequestResponseModel model) {
        checkAsyncExec(new Runnable() {
            @Override
            public void run() {
                IWorkbenchPage workbenchPage = PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getActivePage();
                try {
                    ListView view = (ListView) workbenchPage
                            .showView(ListView.ID);
                    view.addRequestResponseModel(model);
                } catch (PartInitException e) {
                    // TODO �����������ꂽ catch �u���b�N
                    e.printStackTrace();
                }

            }
        });
    }

    private RequestResponseModel convert(HttpServletRequest request,
            HttpServletResponse response) {
        RequestResponseModel model = new RequestResponseModel();
        setRequestInfo(request, model);
        setResponseInfo(response, model);
        return model;
    }

    private void setResponseInfo(HttpServletResponse response,
            RequestResponseModel model) {
        if (response.getHeaderNames().size() == 0) {
            return;
        }
        Collection<String> headerNames = response.getHeaderNames();
        for (String headerName : headerNames) {
            String log = String.format("%1$s: %2$s", headerName,
                    response.getHeader(headerName));
            System.out.println(log);
        }
        System.out.println(response.getCharacterEncoding());
        System.out.println(response.getContentType());
        System.out.println(response.getStatus());

        try {
            // http://kenichiro22.hatenablog.com/entry/20090526/1243308889
            DateFormat df = new SimpleDateFormat(
                    "EEE, dd MMM yyyy hh:mm:ss zzz", java.util.Locale.US);
            Date parse = df.parse(response.getHeader("Date"));
            model.setResDate(parse);
        } catch (ParseException e) {
            // TODO �����������ꂽ catch �u���b�N
            e.printStackTrace();
        }
    }

    private void setRequestInfo(HttpServletRequest request,
            RequestResponseModel model) {
        String method = request.getMethod();
        String url = request.getRequestURI();// URL�𕜌�
        String queryString = request.getQueryString();
        String host = request.getHeader("Host");
        String reqContentType = request.getHeader("Content-Type");
        String reqContentLength = request.getHeader("Content-Length");

        try {
            String body = HttpRequestUtils.getBody(request);
            model.setRequestBody(body);
        } catch (IOException e) {
            // TODO �����������ꂽ catch �u���b�N
            e.printStackTrace();
        }

        model.setHost(host);
        model.setMethod(method);
        model.setUrl(url);
        model.setQueryString(queryString);
        model.setReqContentLength(reqContentLength);
        model.setReqContentType(reqContentType);
    }

    private boolean checkAsyncExec(Runnable thread) {
        if (!Display.getDefault().isDisposed()) {
            Display.getDefault().asyncExec(thread);
            return true;
        } else {
            return false;
        }
    }
}
