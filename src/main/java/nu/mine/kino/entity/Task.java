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
public class Task implements java.io.Serializable {

    /**
     * id
     */
    private String id;

    /**
     * ���ID
     */
    private String taskId;

    /**
     * ����
     */
    private String type;

    /**
     * �^�X�N�́��ɊY�������
     */
    private String taskSharp;

    /**
     * �^�X�N��
     */
    private String taskName;

    /**
     * �^�X�N����1�C���f���g���ꂽ��
     */
    private String taskName1;

    /**
     * �^�X�N����2�C���f���g���ꂽ��
     */
    private String taskName2;

    /**
     * �^�X�N����3�C���f���g���ꂽ��
     */
    private String taskName3;

    /**
     * ��s���ID
     */
    private String senkoTaskId;

    /**
     * �S��
     */
    private String personInCharge;

    /**
     * �^�X�N�T�v
     */
    private String taskAbstract;

    /**
     * �\��H��
     */
    private double numberOfManDays;

    /**
     * �\��J�n��
     */
    private java.util.Date scheduledStartDate;

    /**
     * �\��I����
     */
    private java.util.Date scheduledEndDate;

    /**
     * �v���b�g���ꂽ���̃f�[�^
     */
    private java.util.Map<String, String> plotDataMap;

    /**
     * �ғ��\�����
     */
    private int numberOfDays;

    /**
     * id���Z�b�g����B
     * 
     * @param id
     *            id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * ���ID���Z�b�g����B
     * 
     * @param taskId
     *            ���ID
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * ���ނ��Z�b�g����B
     * 
     * @param type
     *            ����
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * �^�X�N�́��ɊY���������Z�b�g����B
     * 
     * @param taskSharp
     *            �^�X�N�́��ɊY�������
     */
    public void setTaskSharp(String taskSharp) {
        this.taskSharp = taskSharp;
    }

    /**
     * �^�X�N�����Z�b�g����B
     * 
     * @param taskName
     *            �^�X�N��
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * �^�X�N����1�C���f���g���ꂽ����Z�b�g����B
     * 
     * @param taskName1
     *            �^�X�N����1�C���f���g���ꂽ��
     */
    public void setTaskName1(String taskName1) {
        this.taskName1 = taskName1;
    }

    /**
     * �^�X�N����2�C���f���g���ꂽ����Z�b�g����B
     * 
     * @param taskName2
     *            �^�X�N����2�C���f���g���ꂽ��
     */
    public void setTaskName2(String taskName2) {
        this.taskName2 = taskName2;
    }

    /**
     * �^�X�N����3�C���f���g���ꂽ����Z�b�g����B
     * 
     * @param taskName3
     *            �^�X�N����3�C���f���g���ꂽ��
     */
    public void setTaskName3(String taskName3) {
        this.taskName3 = taskName3;
    }

    /**
     * ��s���ID���Z�b�g����B
     * 
     * @param senkoTaskId
     *            ��s���ID
     */
    public void setSenkoTaskId(String senkoTaskId) {
        this.senkoTaskId = senkoTaskId;
    }

    /**
     * �S�����Z�b�g����B
     * 
     * @param personInCharge
     *            �S��
     */
    public void setPersonInCharge(String personInCharge) {
        this.personInCharge = personInCharge;
    }

    /**
     * �^�X�N�T�v���Z�b�g����B
     * 
     * @param taskAbstract
     *            �^�X�N�T�v
     */
    public void setTaskAbstract(String taskAbstract) {
        this.taskAbstract = taskAbstract;
    }

    /**
     * �\��H�����Z�b�g����B
     * 
     * @param numberOfManDays
     *            �\��H��
     */
    public void setNumberOfManDays(double numberOfManDays) {
        this.numberOfManDays = numberOfManDays;
    }

    /**
     * �\��J�n�����Z�b�g����B
     * 
     * @param scheduledStartDate
     *            �\��J�n��
     */
    public void setScheduledStartDate(java.util.Date scheduledStartDate) {
        this.scheduledStartDate = scheduledStartDate;
    }

    /**
     * �\��I�������Z�b�g����B
     * 
     * @param scheduledEndDate
     *            �\��I����
     */
    public void setScheduledEndDate(java.util.Date scheduledEndDate) {
        this.scheduledEndDate = scheduledEndDate;
    }

    /**
     * �K���g�`���[�g���Z�b�g����B
     * 
     * @param plotDataMap
     *            �K���g�`���[�g
     */
    public void setPlotDataMap(java.util.Map<String, String> plotDataMap) {
        this.plotDataMap = plotDataMap;
    }

    /**
     * �ғ��\��������Z�b�g����B
     * 
     * @param numberOfDays
     *            �ғ��\�����
     */
    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    /**
     * id���擾����B
     * 
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * ���ID���擾����B
     * 
     * @return ���ID
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * ���ނ��擾����B
     * 
     * @return ����
     */
    public String getType() {
        return type;
    }

    /**
     * �^�X�N�́��ɊY���������擾����B
     * 
     * @return �^�X�N�́��ɊY�������
     */
    public String getTaskSharp() {
        return taskSharp;
    }

    /**
     * �^�X�N�����擾����B
     * 
     * @return �^�X�N��
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * �^�X�N����1�C���f���g���ꂽ����擾����B
     * 
     * @return �^�X�N����1�C���f���g���ꂽ��
     */
    public String getTaskName1() {
        return taskName1;
    }

    /**
     * �^�X�N����2�C���f���g���ꂽ����擾����B
     * 
     * @return �^�X�N����2�C���f���g���ꂽ��
     */
    public String getTaskName2() {
        return taskName2;
    }

    /**
     * �^�X�N����3�C���f���g���ꂽ����擾����B
     * 
     * @return �^�X�N����3�C���f���g���ꂽ��
     */
    public String getTaskName3() {
        return taskName3;
    }

    /**
     * ��s���ID���擾����B
     * 
     * @return ��s���ID
     */
    public String getSenkoTaskId() {
        return senkoTaskId;
    }

    /**
     * �S�����擾����B
     * 
     * @return �S��
     */
    public String getPersonInCharge() {
        return personInCharge;
    }

    /**
     * �^�X�N�T�v���擾����B
     * 
     * @return �^�X�N�T�v
     */
    public String getTaskAbstract() {
        return taskAbstract;
    }

    /**
     * �\��H�����擾����B
     * 
     * @return �\��H��
     */
    public double getNumberOfManDays() {
        return numberOfManDays;
    }

    /**
     * �\��J�n�����擾����B
     * 
     * @return �\��J�n��
     */
    public java.util.Date getScheduledStartDate() {
        return scheduledStartDate;
    }

    /**
     * �\��I�������擾����B
     * 
     * @return �\��I����
     */
    public java.util.Date getScheduledEndDate() {
        return scheduledEndDate;
    }

    /**
     * �K���g�`���[�g���擾����B
     * 
     * @return �K���g�`���[�g
     */
    public java.util.Map<String, String> getPlotDataMap() {
        return plotDataMap;
    }

    /**
     * �ғ��\��������擾����B
     * 
     * @return �ғ��\�����
     */
    public int getNumberOfDays() {
        return numberOfDays;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id)
                .append("���ID", taskId).append("����", type)
                .append("�^�X�N�́��ɊY�������", taskSharp).append("�^�X�N��", taskName)
                .append("�^�X�N����1�C���f���g���ꂽ��", taskName1)
                .append("�^�X�N����2�C���f���g���ꂽ��", taskName2)
                .append("�^�X�N����3�C���f���g���ꂽ��", taskName3)
                .append("��s���ID", senkoTaskId).append("�S��", personInCharge)
                .append("�^�X�N�T�v", taskAbstract).append("�\��H��", numberOfManDays)
                .append("�\��J�n��", scheduledStartDate)
                .append("�\��I����", scheduledEndDate)
                .append("�K���g�`���[�g", plotDataMap).append("�ғ��\�����", numberOfDays)
                .toString();
    }
}