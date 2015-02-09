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
//�쐬��: 2015/01/27

package nu.mine.kino.jenkins.plugins.projectmanagement.commands;

import hudson.Extension;
import hudson.FilePath;
import hudson.FilePath.FileCallable;
import hudson.cli.CLICommand;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.remoting.VirtualChannel;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import nu.mine.kino.jenkins.plugins.projectmanagement.PMConstants;
import nu.mine.kino.jenkins.plugins.projectmanagement.utils.PMUtils;
import nu.mine.kino.projects.utils.ReadUtils;
import nu.mine.kino.projects.utils.WriteUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.kohsuke.args4j.Argument;

/**
 * Jenkins�ɂ����EVM Excel�}�N���t�@�C���̓��ւ�菈�������{���܂��B
 * 
 * @author Masatomi KINO
 * @version $Revision$
 */
@Extension
public class HigawariCommand extends CLICommand {

    @Argument(metaVar = "JOB", usage = "���ւ�菈�����s���v���W�F�N�g���w�肵�܂��B", index = 0, required = true)
    public AbstractProject<?, ?> job;

    @Argument(metaVar = "FILE", usage = "EVM�t�@�C�������w�肵�܂�", index = 1, required = true)
    public String fileName;

    @Argument(metaVar = "PREFIX", usage = "���ւ�菈�����s���t�@�C����prefix���w�肵�܂��B", index = 2, required = false)
    public String prefix;

    @Override
    public String getShortDescription() {
        return "�w�肵���v���W�F�N�g�̓��ւ�菈�����s���܂��B";
    }

    private static final String seriesFileName = PMConstants.SERIES_DAT_FILENAME;

    @Override
    protected int run() throws Exception {
        // ���ΓI�Ɏw�肳�ꂽ�t�@�C���ɂ��āA���[�N�X�y�[�X���[�g�Ƀt�@�C���R�s�[���܂��B
        FilePath someWorkspace = job.getSomeWorkspace();
        FilePath org = new FilePath(someWorkspace, fileName);
        // FilePath excelSource = new FilePath(someWorkspace, fileName +
        // ".tmp");
        FilePath jsonSource = new FilePath(someWorkspace, fileName + ".json"
                + ".tmp");
        stdout.println(org);
        stdout.println("���̃t�@�C���̓��ւ�菈�����s���܂��B");
        // if (excelSource.exists() && jsonSource.exists()) { //
        if (jsonSource.exists()) { //
            String tmpPrefix = prefix;
            if (StringUtils.isEmpty(prefix)) {
                tmpPrefix = "base";
            }

            String destFileName = tmpPrefix + "_"
                    + (new FilePath(someWorkspace, fileName).getName())
                    + ".json";
            FilePath dest = new FilePath(someWorkspace, destFileName);
            jsonSource.copyTo(dest);
            stdout.println("[" + jsonSource.getName() + "] -> ["
                    + dest.getName() + "] �R�s�[����");

            String baseDateStr = jsonSource.act(new DateFileCopyExecutor());
            stdout.println("���: " + baseDateStr + " ����߂܂����B���ւ�菈��������I�����܂����B");

            // Prefix�����i�V�̎������A���n��t�@�C������������
            if (StringUtils.isEmpty(prefix)) {
                final AbstractBuild<?, ?> shimeBuild = job
                        .getLastSuccessfulBuild();
                // stdout.printf("[%s]\n",
                // shimeBuild.getRootDir().getAbsolutePath());
                // stdout.printf("[%s]:[%s]:[%s]\n", baseDateStr,
                // shimeBuild.getNumber(), shimeBuild.getId());
                String prevData = findSeriesFile(seriesFileName);
                String currentData = appendData(prevData,
                        shimeBuild.getNumber(), baseDateStr);
                File file = new File(shimeBuild.getRootDir().getAbsolutePath(),
                        seriesFileName);
                WriteUtils.writeFile(currentData.getBytes(), file);
                stdout.printf("EVM���n����t�@�C���ɏ���ǋL���ăr���h #%s �ɏ������݂܂����B\n",
                        shimeBuild.getNumber());
            }

        } else {
            stderr.println("---- �G���[�������������ߓ��ւ�菈�����~���܂��B------");
            // if (!excelSource.exists()) {
            // stderr.println("�o�b�N�A�b�v�t�@�C��:");
            // stderr.println(excelSource);
            // stderr.println("�����݂��Ȃ����ߓ��ւ�菈�����~���܂��B");
            // }
            if (!jsonSource.exists()) {
                stderr.println("�o�b�N�A�b�v�t�@�C��(������擾����):");
                stderr.println(jsonSource);
                stderr.println("�����݂��Ȃ����ߓ��ւ�菈�����~���܂��B");
            }
            stderr.println("------------------------------------------------");
            return -1;
        }
        return 0;
    }

    private static class DateFileCopyExecutor implements FileCallable<String> {

        @Override
        public String invoke(File jsonFile, VirtualChannel channel)
                throws IOException, InterruptedException {
            Date baseDate = PMUtils.getBaseDateFromJSON(jsonFile);
            // Date baseDate = PMUtils.getBaseDateFromExcel(f);
            if (baseDate != null) {
                String baseDateStr = DateFormatUtils.format(baseDate,
                        "yyyyMMdd");
                WriteUtils.writeFile(baseDateStr.getBytes(),
                        new File(jsonFile.getParentFile(),
                                PMConstants.DATE_DAT_FILENAME));
                return baseDateStr;
            }
            return null;
        }

    }

    private String findSeriesFile(String fileName) {
        AbstractBuild<?, ?> build = PMUtils.findBuild(job, fileName);
        if (build == null) {
            stdout.println("EVM���n����t�@�C�����v���W�F�N�g��ɑ��݂��Ȃ��̂ŁA�t�@�C����V�K�쐬���܂��B");
            return null;
        } else {
            stdout.printf("EVM���n����t�@�C���� �r���h #%s ��Ɍ�����܂����B\n",
                    build.getNumber());
        }
        try {
            return ReadUtils.readFile(new File(build.getRootDir(), fileName));
        } catch (IOException e) {
            stderr.println("EVM���n����t�@�C����T���ۂɃG���[�����������̂ŁA�t�@�C����V�K�쐬���܂��B");
        }
        return null;
    }

    private String appendData(String prevData, int buildNumber,
            String baseDateStr) {
        StringBuffer buffer = new StringBuffer();
        if (prevData != null) {
            buffer.append(prevData).append("\n");
        }
        return new String(buffer.append(baseDateStr).append("\t")
                .append(buildNumber));
    }
}
