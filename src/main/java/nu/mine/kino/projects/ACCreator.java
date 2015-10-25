package nu.mine.kino.projects;

/******************************************************************************
 * Copyright (c) 2008-2009 Masatomi KINO and others. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *      Masatomi KINO - initial API and implementation
 * $Id$
 ******************************************************************************/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nu.mine.kino.entity.ACBean;
import nu.mine.kino.entity.Project;
import nu.mine.kino.entity.TaskInformation;
import nu.mine.kino.projects.utils.ProjectUtils;
import nu.mine.kino.projects.utils.WriteUtils;

import org.apache.commons.lang.StringUtils;

/**
 * 2�_�Ԃ̍�������邱�Ƃɂ��A���̊��Ԃ�AC���擾����B
 * 
 * @author Masatomi KINO and JavaBeans Creator Plug-in
 * @version $Revision$
 */
public class ACCreator {
    private static Object[] internalCreateList(Project targetProject,
            Project baseProject) throws ProjectException {
        List<ACBean> returnList = new ArrayList<ACBean>();
        TaskInformation[] taskInfos1 = targetProject.getTaskInformations();
        for (TaskInformation taskInfo1 : taskInfos1) {
            TaskInformation taskInfo2 = ProjectUtils.getTaskInformation(
                    baseProject, taskInfo1.getTaskId());
            ACBean ac = ProjectUtils.getACBean(taskInfo1, taskInfo2);
            // System.out.println(ac);
            returnList.add(ac);
        }
        return new Object[] { returnList, targetProject };
    }

    private static Object[] internalCreateList(File target, File base,
            ProjectCreator creator1, ProjectCreator creator2)
            throws ProjectException {
        // java.io.InputStream in1 = null;
        // java.io.InputStream in2 = null;
        try {
            // in1 = new java.io.FileInputStream(target);
            Project project1 = creator1.createProject();
            // in2 = new java.io.FileInputStream(base);
            Project project2 = creator2.createProject();

            return internalCreateList(project1, project2);
            // } catch (FileNotFoundException e) {
            // throw new ProjectException(e);
        } finally {
            // if (in1 != null) {
            // try {
            // in1.close();
            // } catch (IOException e) {
            // throw new ProjectException(e);
            // }
            // }
            // if (in2 != null) {
            // try {
            // in2.close();
            // } catch (IOException e) {
            // throw new ProjectException(e);
            // }
            // }
        }
    }

    private static File internalCreate(File target, File base,
            ProjectCreator creator1, ProjectCreator creator2, String suffix)
            throws ProjectException {

        Object[] tmp = internalCreateList(target, base, creator1, creator2);
        List<ACBean> returnList = (List<ACBean>) tmp[0];
        Project project1 = (Project) tmp[1];

        Date baseDate = project1.getBaseDate();
        String baseDateStr = new SimpleDateFormat("yyyyMMdd").format(baseDate);

        File outputFile = WriteUtils.input2Output(target, baseDateStr, suffix);
        WriteUtils.writeAC(project1, returnList, outputFile);

        return outputFile;

    }

    /**
     * 2��Excel�t�@�C���������ɁA2��{@link Project}�𐶐����A������AC�̍������v�Z���� �t�@�C���o�͂���B
     * �A�E�g�v�b�g�t�@�C�����́u�C���v�b�g�t�@�C����+_AC.tsv �v�B
     * �f�B���N�g���́A�C���v�b�g�t�@�C���̂���t�@�C���̏ꏊ�Ƀv���W�F�N�g�̊���̃f�B���N�g�����@���Ċi�[����B
     * 
     * 
     * @param target
     * @param base
     * @return
     * @throws ProjectException
     */
    public static File create(File target, File base) throws ProjectException {
        return create(target, base, null);
    }

    public static File create(File target, File base, String base_prefix)
            throws ProjectException {
        String suffix = "_AC.tsv";
        if (!StringUtils.isEmpty(base_prefix)) {
            suffix = "_" + base_prefix + "AC.tsv";
        }
        return internalCreate(target, base, new ExcelProjectCreator(target),
                new ExcelProjectCreator(base), suffix);
    }

    /**
     * 2��JSON�e�L�X�g�t�@�C���������ɁA2��{@link Project}�𐶐����A������AC�̍������v�Z���� �t�@�C���o�͂���B
     * �A�E�g�v�b�g�t�@�C�����́u�C���v�b�g�t�@�C����+_ACj.tsv �v�B
     * �f�B���N�g���́A�C���v�b�g�t�@�C���̂���t�@�C���̏ꏊ�Ƀv���W�F�N�g�̊���̃f�B���N�g�����@���Ċi�[����B
     * 
     * @param target
     * @param base
     * @return
     * @throws ProjectException
     */
    public static File createFromJSON(File target, File base)
            throws ProjectException {
        return createFromJSON(target, base, null);
    }

    public static File createFromJSON(File target, File base, String base_prefix)
            throws ProjectException {
        String suffix = "_ACj.tsv";
        if (!StringUtils.isEmpty(base_prefix)) {
            suffix = "_" + base_prefix + "ACj.tsv";
        }
        return internalCreate(target, base, new JSONProjectCreator(target),
                new JSONProjectCreator(base), suffix);
    }

    public static List<ACBean> createACList(File target, File base)
            throws ProjectException {
        Object[] tmp = internalCreateList(target, base,
                new ExcelProjectCreator(target), new ExcelProjectCreator(base));
        return (List<ACBean>) tmp[0];
    }

    public static List<ACBean> createACListFromJSON(File target, File base)
            throws ProjectException {
        Object[] tmp = internalCreateList(target, base, new JSONProjectCreator(
                target), new JSONProjectCreator(base));
        return (List<ACBean>) tmp[0];
    }

}
