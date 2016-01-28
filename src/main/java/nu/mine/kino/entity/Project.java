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

package nu.mine.kino.entity;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author Masatomi KINO and JavaBeans Creator Plug-in
 * @version $Revision$
 */
public class Project implements java.io.Serializable {

    /**
     * �^�X�N���
     */
    private nu.mine.kino.entity.TaskInformation[] taskInformations;

    /**
     * �v���W�F�N�g�J�n��
     */
    private java.util.Date projectStartDate;

    /**
     * �v���W�F�N�g�I����
     */
    private java.util.Date projectEndDate;

    /**
     * ���
     */
    private java.util.Date baseDate;

    /**
     * �x���J�����_�[
     */
    private nu.mine.kino.entity.Holiday[] holidays;

    /**
     * �^�X�N�����Z�b�g����B
     * 
     * @param taskInformations
     *            �^�X�N���
     */
    public void setTaskInformations(
            nu.mine.kino.entity.TaskInformation[] taskInformations) {
        this.taskInformations = taskInformations;
    }

    /**
     * �v���W�F�N�g�J�n�����Z�b�g����B
     * 
     * @param projectStartDate
     *            �v���W�F�N�g�J�n��
     */
    public void setProjectStartDate(java.util.Date projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    /**
     * �v���W�F�N�g�I�������Z�b�g����B
     * 
     * @param projectEndDate
     *            �v���W�F�N�g�I����
     */
    public void setProjectEndDate(java.util.Date projectEndDate) {
        this.projectEndDate = projectEndDate;
    }

    /**
     * ������Z�b�g����B
     * 
     * @param baseDate
     *            ���
     */
    public void setBaseDate(java.util.Date baseDate) {
        this.baseDate = baseDate;
    }

    /**
     * �x���J�����_�[���Z�b�g����B
     * 
     * @param holidays
     *            �x���J�����_�[
     */
    public void setHolidays(nu.mine.kino.entity.Holiday[] holidays) {
        this.holidays = holidays;
    }

    /**
     * �^�X�N�����擾����B
     * 
     * @return �^�X�N���
     */
    public nu.mine.kino.entity.TaskInformation[] getTaskInformations() {
        return taskInformations;
    }

    /**
     * �v���W�F�N�g�J�n�����擾����B
     * 
     * @return �v���W�F�N�g�J�n��
     */
    public java.util.Date getProjectStartDate() {
        return projectStartDate;
    }

    /**
     * �v���W�F�N�g�I�������擾����B
     * 
     * @return �v���W�F�N�g�I����
     */
    public java.util.Date getProjectEndDate() {
        return projectEndDate;
    }

    /**
     * ������擾����B
     * 
     * @return ���
     */
    public java.util.Date getBaseDate() {
        return baseDate;
    }

    /**
     * �x���J�����_�[���擾����B
     * 
     * @return �x���J�����_�[
     */
    public nu.mine.kino.entity.Holiday[] getHolidays() {
        return holidays;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("�^�X�N���", taskInformations)
                .append("�v���W�F�N�g�J�n��", projectStartDate)
                .append("�v���W�F�N�g�I����", projectEndDate).append("���", baseDate)
                .append("�x���J�����_�[", holidays).toString();
    }
}