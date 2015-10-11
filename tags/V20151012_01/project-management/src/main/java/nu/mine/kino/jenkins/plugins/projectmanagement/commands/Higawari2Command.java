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
import nu.mine.kino.projects.ProjectException;
import nu.mine.kino.projects.utils.ProjectUtils;

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
public class Higawari2Command extends CLICommand {

    @Argument(metaVar = "JOB", usage = "���ւ�菈�����s���v���W�F�N�g���w�肵�܂��B", index = 0, required = true)
    public AbstractProject<?, ?> job;

    @Argument(metaVar = "PREFIX", usage = "���ւ�菈�����s���t�@�C����prefix���w�肵�܂��B", index = 1, required = false)
    public String prefix;

    @Override
    public String getShortDescription() {
        return "�w�肵���v���W�F�N�g�̓��ւ�菈�����s���܂��B";
    }

    private static final String seriesFileNameSuffix = PMConstants.SERIES_DAT_FILENAME;

    @Override
    protected int run() throws Exception {
        String originalExcelFileName = PMUtils.findProjectFileName(job);
        if (originalExcelFileName == null) {
            throw new ProjectException("�X�P�W���[���t�@�C���������邱�Ƃ��ł��܂���ł����B");
        }
        stdout.println("EVM�t�@�C����: " + originalExcelFileName);

        // ���ΓI�Ɏw�肳�ꂽ�t�@�C���ɂ��āA���[�N�X�y�[�X���[�g�Ƀt�@�C���R�s�[���܂��B
        FilePath someWorkspace = job.getSomeWorkspace();
        FilePath excelFilePath = new FilePath(someWorkspace,
                originalExcelFileName);
        String previousJsonFileName = PMUtils
                .getPreviousJsonFileName(originalExcelFileName);
        FilePath jsonSource = new FilePath(someWorkspace, previousJsonFileName);
        stdout.println(excelFilePath);
        stdout.println("���̃t�@�C���̓��ւ�菈�����s���܂��B");
        if (jsonSource.exists()) { //
            String tmpPrefix = prefix;
            if (StringUtils.isEmpty(prefix)) {
                tmpPrefix = PMConstants.BASE;
            }

            String destFileName = tmpPrefix
                    + "_"
                    + ProjectUtils.findJSONFileName((new FilePath(
                            someWorkspace, originalExcelFileName).getName()));
            FilePath dest = new FilePath(someWorkspace, destFileName);
            jsonSource.copyTo(dest);
            stdout.println(jsonSource.getParent() + " �� �ŃR�s�[");
            stdout.println("[" + jsonSource.getName() + "] -> ["
                    + dest.getName() + "] �R�s�[����");

            final AbstractBuild<?, ?> shimeBuild = job.getLastSuccessfulBuild();

            String baseDateStr = jsonSource.act(new DateFileExecutor());
            PMUtils.writeBaseDateFile(baseDateStr, shimeBuild, stdout);

            // FilePath dest2 = new FilePath(
            // new FilePath(shimeBuild.getRootDir()), destFileName);
            // jsonSource.copyTo(dest2);

            stdout.println("���: " + baseDateStr + " ����߂܂����B���ւ�菈��������I�����܂����B");

            // Prefix�����i�V�̎������A���n��t�@�C������������
            String seriesFileName = tmpPrefix + "_" + seriesFileNameSuffix;
            PMUtils.writeSeriesFile(job, baseDateStr, seriesFileName,
                    shimeBuild, stdout, stderr);

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

}
