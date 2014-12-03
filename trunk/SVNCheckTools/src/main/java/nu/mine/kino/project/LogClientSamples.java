package nu.mine.kino.project;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNLogClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

/******************************************************************************
 * Copyright (c) 2014 Masatomi KINO and others. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *      Masatomi KINO - initial API and implementation
 * $Id$
 ******************************************************************************/
//�쐬��: 2014/12/01

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class LogClientSamples {
    private static final String userName = "svnuser";

    private static final String password = "svnuserpass";

    public static void main(String[] args) throws SVNException, ParseException {
        new LogClientSamples().execute();
    }

    private static class LogEntryHandler implements ISVNLogEntryHandler {

        @Override
        public void handleLogEntry(SVNLogEntry entry) throws SVNException {
            System.out.print(entry.getRevision() + "\t");
            System.out.print(entry.getDate() + "\t");
            // System.out.print(arg0.getChangedPaths());
            System.out.print(entry.getAuthor() + "\t");
            System.out.println(entry.getMessage());
            Map<String, SVNLogEntryPath> changedPaths = entry.getChangedPaths();
            System.out.println(changedPaths);
        }

    }

    private void execute() throws SVNException, ParseException {
        System.out.println("start");
        init();

        String svnURL = "https://www.masatom.in/svnsamples/repo/trunk/";

        ISVNAuthenticationManager auth = SVNWCUtil
                .createDefaultAuthenticationManager(userName, password);
        SVNLogClient logClient = SVNClientManager.newInstance(
                SVNWCUtil.createDefaultOptions(true), auth).getLogClient();

        SVNURL targetUrl = SVNURL.parseURIEncoded(svnURL);
        ISVNLogEntryHandler handler = new LogEntryHandler();
        // String[] path = new String[] { "EVMTools", "EVMTools" };
        // logClient.doLog(targetUrl, null, SVNRevision.HEAD, SVNRevision.HEAD,
        // SVNRevision.create(0), true, true, 50, handler);

        SVNRepository repository = DAVRepositoryFactory.create(targetUrl);
        repository.setAuthenticationManager(auth);

        String sDate = "20141128";
        Date startDate = DateUtils
                .parseDate(sDate, new String[] { "yyyyMMdd" });
        long startRev = repository.getDatedRevision(startDate);

        String eDate = "20141130";// ����𒴂��Ȃ����c�ōŐV��Rev�����B
        Date endDate = DateUtils.parseDate(eDate, new String[] { "yyyyMMdd" });
        long endRev = repository
                .getDatedRevision(DateUtils.addDays(endDate, 1));

        // repository.getDatedRevision(new Date());
        // ���̃��\�b�h�́A�w�肵�����t���u�����Ȃ��v�ő��Rev��Ԃ��B
        // From/To�w�肷��ꍇ�Ȃǂ́A
        // From �� ���̓��𒴂��Ȃ��ő��Rev�Ȃ̂�Rev+1���Ȃ��ƃ_��
        // To �� ���t��1�i�߂Ȃ��Ƃ��̓��͊܂܂�Ȃ��̂� ���t��1�i�߂�B

        System.out.println(startRev + 1);
        System.out.println(endRev);

        // ���Ԓ���Rev�����݂��Ȃ��ƁA�Ȃ񂩃����W����O�ꂽ���c���A���Ă��邪�A�ǂ��������I�ɂ�
        // ���L�̂悤�Ȏw��Ƃ��Ȃ����ʂɂȂ�ۂ��B
        // svn log -r {20131128}:{20131130} --verbose
        // https://www.masatom.in/svnsamples/repo/trunk/
        // ------------------------------------------------------------------------
        // r531 | masatomix | 2013-09-17 19:54:11 +0900 (2013/09/17 (��)) | 1
        // line
        // �ύX�̂������p�X:
        // M /trunk/text2unicode/src/nu/mine/kino/gae/Text.java
        // M /trunk/text2unicode/test/nu/mine/kino/gae/TextTest.java
        // M /trunk/text2unicode/war/WEB-INF/web.xml
        // M /trunk/text2unicode/war/index.html
        logClient.doLog(targetUrl, null, SVNRevision.HEAD,
                SVNRevision.create(startDate),
                SVNRevision.create(DateUtils.addDays(endDate, 1)), true, true,
                50, handler);

    }

    private void init() {
        DAVRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();
        FSRepositoryFactory.setup();
    }

}