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
//作成日: 2012/06/25

package nu.mine.kino.plugin.webrecorder.jobs;

import java.io.IOException;
import java.io.InputStream;

import nu.mine.kino.plugin.commons.utils.HttpClientUtils;
import nu.mine.kino.plugin.commons.utils.RWUtils;
import nu.mine.kino.plugin.webrecorder.WebRecorderPlugin;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class DriverJob extends Job {

    private final String method;

    private final String url;

    private final String body;

    private final Text textResult;

    public DriverJob(String name, String method, String url, String body,
            Text textResult) {
        super(name);
        this.method = method;
        this.url = url;
        this.body = body;
        this.textResult = textResult;

    }

    @Override
    protected IStatus run(IProgressMonitor monitor) {
        monitor.beginTask(url + " を " + method + " でリクエスト中...", 10);
        try {
            if ("POST".equals(method)) {
                executePost(url, body);
            } else if ("GET".equals(method)) {
                executeGet(url);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        monitor.done();
        return Status.OK_STATUS;

    }

    private void executePost(String url, String body) throws IOException,
            ClientProtocolException {
        // String contentType = hRequest.getContentType();
        HttpEntity entity = HttpClientUtils
                .getHttpEntity(url, body, null, null);
        if (entity != null) {
            // InputStream stream = entity.getContent();
            final String result = EntityUtils.toString(entity);
            // final String result = RWUtils.stream2String(stream, "UTF-8");
            // WebRecorderPlugin.getDefault().printConsole(result);

            checkAsyncExec(new Runnable() {
                @Override
                public void run() {
                    textResult.setText(result);
                }
            });
        }
    }

    private void executeGet(String url) throws IOException,
            ClientProtocolException {

        HttpEntity entity = HttpClientUtils.getHttpEntity(url);
        if (entity != null) {
            // InputStream stream = entity.getContent();
            final String result = EntityUtils.toString(entity);
            // final String result = RWUtils.stream2String(stream, "UTF-8");
            // WebRecorderPlugin.getDefault().printConsole(result);

            checkAsyncExec(new Runnable() {
                @Override
                public void run() {
                    textResult.setText(result);
                }
            });

        }
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
