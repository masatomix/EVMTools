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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import nu.mine.kino.entity.PVBean;
import nu.mine.kino.entity.Project;
import nu.mine.kino.projects.utils.ProjectUtils;
import nu.mine.kino.projects.utils.WriteUtils;

/**
 * �v���W�F�N�g�S�̂�PV������Ƃɏo�͂���B
 * 
 * @author Masatomi KINO and JavaBeans Creator Plug-in
 * @version $Revision$
 */
public class PVCreator {
    public static List<PVBean> createCurrentList(Project project)
            throws ProjectException {
        return ProjectUtils.getCurrentPVList(project);
    }

    private static File internalCreate(File input, ProjectCreator creator,
            String suffix) throws ProjectException {

        Project project = creator.createProject();
        Date baseDate = project.getBaseDate();

        String baseDateStr = new SimpleDateFormat("yyyyMMdd").format(baseDate);
        File outputFile = WriteUtils.input2Output(input, baseDateStr, suffix);

        WriteUtils.writePV(project, outputFile);
        return outputFile;

    }

    /**
     * Excel�t�@�C���������ɁA{@link Project}�𐶐����APV�𐶐����ďo�͂���B
     * �A�E�g�v�b�g�t�@�C�����́u�C���v�b�g�t�@�C����+_PV.tsv �v�B
     * �f�B���N�g���́A�C���v�b�g�t�@�C���̂���t�@�C���̏ꏊ�Ƀv���W�F�N�g�̊���̃f�B���N�g�����@���Ċi�[����B
     * 
     * @param input
     * @return �A�E�g�v�b�g�t�@�C���̃p�X
     * @throws ProjectException
     */
    public static File create(File input) throws ProjectException {
        return internalCreate(input, new ExcelProjectCreator(input), "_PV.tsv");
    }

    /**
     * JSON�e�L�X�g�t�@�C���������ɁA{@link Project}�𐶐����APV�𐶐����ďo�͂���B
     * �A�E�g�v�b�g�t�@�C�����́u�C���v�b�g�t�@�C����+_PVj.tsv �v�B
     * �f�B���N�g���́A�C���v�b�g�t�@�C���̂���t�@�C���̏ꏊ�Ƀv���W�F�N�g�̊���̃f�B���N�g�����@���Ċi�[����B
     * 
     * @param input
     * @return �A�E�g�v�b�g�t�@�C���̃p�X
     * @throws ProjectException
     */
    public static File createFromJSON(File input) throws ProjectException {
        return internalCreate(input, new JSONProjectCreator(input), "_PVj.tsv");
    }

    private static File internalCreateForPivot(File input,
            ProjectCreator creator, String suffix) throws ProjectException {

        Project project = creator.createProject();
        Date baseDate = project.getBaseDate();

        String baseDateStr = new SimpleDateFormat("yyyyMMdd").format(baseDate);
        File outputFile = WriteUtils.input2Output(input, baseDateStr, suffix);

        WriteUtils.writePVForPivot(project, outputFile);
        return outputFile;

    }

    /**
     * Excel�t�@�C���������ɁA{@link Project}�𐶐����APV�𐶐�����Pivot�ɓK�����t�H�[�}�b�g�ŏo�͂���B
     * �A�E�g�v�b�g�t�@�C�����́u�C���v�b�g�t�@�C����+_PVPivot.tsv �v�B
     * �f�B���N�g���́A�C���v�b�g�t�@�C���̂���t�@�C���̏ꏊ�Ƀv���W�F�N�g�̊���̃f�B���N�g�����@���Ċi�[����B
     * 
     * @param input
     * @return �A�E�g�v�b�g�t�@�C���̃p�X
     * @throws ProjectException
     */
    public static File createForPivot(File input) throws ProjectException {
        return internalCreateForPivot(input, new ExcelProjectCreator(input),
                "_PVPivot.tsv");
    }

    /**
     * JSON�e�L�X�g�t�@�C���������ɁA{@link Project}�𐶐����APV�𐶐�����Pivot�ɓK�����t�H�[�}�b�g�ŏo�͂���B
     * �A�E�g�v�b�g�t�@�C�����́u�C���v�b�g�t�@�C����+_PVjPivot.tsv �v�B
     * �f�B���N�g���́A�C���v�b�g�t�@�C���̂���t�@�C���̏ꏊ�Ƀv���W�F�N�g�̊���̃f�B���N�g�����@���Ċi�[����B
     * 
     * @param input
     * @return �A�E�g�v�b�g�t�@�C���̃p�X
     * @throws ProjectException
     */
    public static File createForPivotFromJSON(File input)
            throws ProjectException {
        return internalCreateForPivot(input, new JSONProjectCreator(input),
                "_PVjPivot.tsv");
    }

}
