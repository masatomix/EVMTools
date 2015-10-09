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
import nu.mine.kino.projects.utils.ProjectUtils;
import nu.mine.kino.projects.utils.ReadUtils;
import nu.mine.kino.projects.utils.WriteUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.jenkinsci.remoting.RoleChecker;
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

    private static final String seriesFileNameSuffix = PMConstants.SERIES_DAT_FILENAME;

    @Override
    protected int run() throws Exception {
        // ���ΓI�Ɏw�肳�ꂽ�t�@�C���ɂ��āA���[�N�X�y�[�X���[�g�Ƀt�@�C���R�s�[���܂��B
        FilePath someWorkspace = job.getSomeWorkspace();
        FilePath org = new FilePath(someWorkspace, fileName);
        // FilePath excelSource = new FilePath(someWorkspace, fileName +
        // "."+PMConstants.TMP_EXT);
        FilePath jsonSource = new FilePath(someWorkspace,
                ProjectUtils.findJSONFileName(fileName) + "."
                        + PMConstants.TMP_EXT);
        stdout.println(org);
        stdout.println("���̃t�@�C���̓��ւ�菈�����s���܂��B");
        if (jsonSource.exists()) { //
            String tmpPrefix = prefix;
            if (StringUtils.isEmpty(prefix)) {
                tmpPrefix = PMConstants.BASE;
            }

            String destFileName = tmpPrefix
                    + "_"
                    + ProjectUtils.findJSONFileName((new FilePath(
                            someWorkspace, fileName).getName()));
            FilePath dest = new FilePath(someWorkspace, destFileName);
            jsonSource.copyTo(dest);
            stdout.println("[" + jsonSource.getName() + "] -> ["
                    + dest.getName() + "] �R�s�[����");

            final AbstractBuild<?, ?> shimeBuild = job.getLastSuccessfulBuild();

            String baseDateStr = jsonSource.act(new DateFileExecutor());
            writeBaseDateFile(baseDateStr, shimeBuild);

            stdout.println("���: " + baseDateStr + " ����߂܂����B���ւ�菈��������I�����܂����B");

            // Prefix�����i�V�̎������A���n��t�@�C������������
            String seriesFileName = tmpPrefix + "_" + seriesFileNameSuffix;
            writeSeriesFile(baseDateStr, seriesFileName, shimeBuild);

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

    private void writeSeriesFile(String baseDateStr, String fileName,
            final AbstractBuild<?, ?> shimeBuild) {
        // stdout.printf("[%s]\n",
        // shimeBuild.getRootDir().getAbsolutePath());
        // stdout.printf("[%s]:[%s]:[%s]\n", baseDateStr,
        // shimeBuild.getNumber(), shimeBuild.getId());
        String prevData = findSeriesFile(fileName);
        String currentData = appendData(prevData, shimeBuild.getNumber(),
                baseDateStr);
        File file = new File(shimeBuild.getRootDir().getAbsolutePath(),
                fileName);
        WriteUtils.writeFile(currentData.getBytes(), file);
        stdout.printf("EVM���n����t�@�C��(%s)�ɏ���ǋL���ăr���h #%s �ɏ������݂܂����B\n", fileName,
                shimeBuild.getNumber());
    }

    private void writeBaseDateFile(String baseDateStr,
            final AbstractBuild<?, ?> shimeBuild) {
        WriteUtils
                .writeFile(baseDateStr.getBytes(), new File(shimeBuild
                        .getRootDir().getAbsolutePath(),
                        PMConstants.DATE_DAT_FILENAME));
        stdout.printf("����t�@�C��(%s)���r���h #%s �ɏ������݂܂����B\n",
                PMConstants.DATE_DAT_FILENAME, shimeBuild.getNumber());
    }

    // private static class DateFileCopyExecutor implements FileCallable<String>
    // {
    //
    // @Override
    // public String invoke(File jsonFile, VirtualChannel channel)
    // throws IOException, InterruptedException {
    // Date baseDate = PMUtils.getBaseDateFromJSON(jsonFile);
    // // Date baseDate = PMUtils.getBaseDateFromExcel(f);
    // if (baseDate != null) {
    // String baseDateStr = DateFormatUtils.format(baseDate,
    // "yyyyMMdd");
    // WriteUtils.writeFile(baseDateStr.getBytes(),
    // new File(jsonFile.getParentFile(),
    // PMConstants.DATE_DAT_FILENAME));
    // return baseDateStr;
    // }
    // return null;
    // }
    //
    // @Override
    // public void checkRoles(RoleChecker checker) throws SecurityException {
    // // TODO �����������ꂽ���\�b�h�E�X�^�u
    //
    // }
    //
    // }

    private static class DateFileExecutor implements FileCallable<String> {

        @Override
        public String invoke(File jsonFile, VirtualChannel channel)
                throws IOException, InterruptedException {
            Date baseDate = PMUtils.getBaseDateFromJSON(jsonFile);
            if (baseDate != null) {
                String baseDateStr = DateFormatUtils.format(baseDate,
                        "yyyyMMdd");
                return baseDateStr;
            }
            return null;
        }

        @Override
        public void checkRoles(RoleChecker checker) throws SecurityException {
            // TODO �����������ꂽ���\�b�h�E�X�^�u

        }

    }

    private String findSeriesFile(String fileName) {
        AbstractBuild<?, ?> build = PMUtils.findBuild(job, fileName);
        if (build == null) {
            stdout.printf("EVM���n����t�@�C��(%s)���v���W�F�N�g��ɑ��݂��Ȃ��̂ŁA�t�@�C����V�K�쐬���܂��B\n",
                    fileName);
            return null;
        } else {
            stdout.printf("EVM���n����t�@�C��(%s)�� �r���h #%s ��Ɍ�����܂����B\n", fileName,
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
        StringBuffer buffer = new StringBuffer().append(baseDateStr)
                .append("\t").append(buildNumber);
        if (prevData != null) {
            buffer.append("\n").append(prevData);
        }
        return new String(buffer);
    }
}