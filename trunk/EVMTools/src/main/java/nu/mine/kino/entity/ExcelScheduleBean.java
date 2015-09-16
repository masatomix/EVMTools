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

import net.java.amateras.xlsbeans.annotation.Column;
import net.java.amateras.xlsbeans.annotation.MapColumns;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author Masatomi KINO and JavaBeans Creator Plug-in
 * @version $Revision$
 */
public class ExcelScheduleBean {

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
     * ��v�A�E�g�v�b�g
     */
    private String output;

    /**
     * ��T�̊������ѕ�
     */
    private String reportOfLastWeek;

    /**
     * ���T�̊����\��
     */
    private String reportOfThisWeek;

    /**
     * ��
     */
    private String status;

    /**
     * ���l
     */
    private String remarks;

    /**
     * ���
     */
    private java.util.Date baseDate;

    /**
     * �v���b�g���ꂽ���̃f�[�^
     */
    private java.util.Map<String, String> plotDataMap;

    /**
     * id���Z�b�g����B
     * 
     * @param id
     *            id
     */
    @Column(columnName = "#")//$NON-NLS-1$
    public void setId(String id) {
        this.id = id;
    }

    /**
     * ���ID���Z�b�g����B
     * 
     * @param taskId
     *            ���ID
     */
    @Column(columnName = "���ID")//$NON-NLS-1$
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * ���ނ��Z�b�g����B
     * 
     * @param type
     *            ����
     */
    @Column(columnName = "����")//$NON-NLS-1$
    public void setType(String type) {
        this.type = type;
    }

    /**
     * �^�X�N�́��ɊY���������Z�b�g����B
     * 
     * @param taskSharp
     *            �^�X�N�́��ɊY�������
     */
    @Column(columnName = "a")//$NON-NLS-1$
    public void setTaskSharp(String taskSharp) {
        this.taskSharp = taskSharp;
    }

    /**
     * �^�X�N�����Z�b�g����B
     * 
     * @param taskName
     *            �^�X�N��
     */
    @Column(columnName = "�^�X�N")//$NON-NLS-1$
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * �^�X�N����1�C���f���g���ꂽ����Z�b�g����B
     * 
     * @param taskName1
     *            �^�X�N����1�C���f���g���ꂽ��
     */
    @Column(columnName = "b")//$NON-NLS-1$
    public void setTaskName1(String taskName1) {
        this.taskName1 = taskName1;
    }

    /**
     * �^�X�N����2�C���f���g���ꂽ����Z�b�g����B
     * 
     * @param taskName2
     *            �^�X�N����2�C���f���g���ꂽ��
     */
    @Column(columnName = "c")//$NON-NLS-1$
    public void setTaskName2(String taskName2) {
        this.taskName2 = taskName2;
    }

    /**
     * �^�X�N����3�C���f���g���ꂽ����Z�b�g����B
     * 
     * @param taskName3
     *            �^�X�N����3�C���f���g���ꂽ��
     */
    @Column(columnName = "d")//$NON-NLS-1$
    public void setTaskName3(String taskName3) {
        this.taskName3 = taskName3;
    }

    /**
     * ��s���ID���Z�b�g����B
     * 
     * @param senkoTaskId
     *            ��s���ID
     */
    @Column(columnName = "��s���ID")//$NON-NLS-1$
    public void setSenkoTaskId(String senkoTaskId) {
        this.senkoTaskId = senkoTaskId;
    }

    /**
     * �S�����Z�b�g����B
     * 
     * @param personInCharge
     *            �S��
     */
    @Column(columnName = "�S��")//$NON-NLS-1$
    public void setPersonInCharge(String personInCharge) {
        this.personInCharge = personInCharge;
    }

    /**
     * �^�X�N�T�v���Z�b�g����B
     * 
     * @param taskAbstract
     *            �^�X�N�T�v
     */
    @Column(columnName = "�^�X�N�T�v")//$NON-NLS-1$
    public void setTaskAbstract(String taskAbstract) {
        this.taskAbstract = taskAbstract;
    }

    /**
     * ��v�A�E�g�v�b�g���Z�b�g����B
     * 
     * @param output
     *            ��v�A�E�g�v�b�g
     */
    @Column(columnName = "��v�A�E�g�v�b�g")//$NON-NLS-1$
    public void setOutput(String output) {
        this.output = output;
    }

    /**
     * ��T�̊������ѕ񍐂��Z�b�g����B
     * 
     * @param reportOfLastWeek
     *            ��T�̊������ѕ�
     */
    @Column(columnName = "��T�̊������ѕ�")//$NON-NLS-1$
    public void setReportOfLastWeek(String reportOfLastWeek) {
        this.reportOfLastWeek = reportOfLastWeek;
    }

    /**
     * ���T�̊����\����Z�b�g����B
     * 
     * @param reportOfThisWeek
     *            ���T�̊����\��
     */
    @Column(columnName = "���T�̊����\��")//$NON-NLS-1$
    public void setReportOfThisWeek(String reportOfThisWeek) {
        this.reportOfThisWeek = reportOfThisWeek;
    }

    /**
     * �󋵂��Z�b�g����B
     * 
     * @param status
     *            ��
     */
    @Column(columnName = "��")//$NON-NLS-1$
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * ���l���Z�b�g����B
     * 
     * @param remarks
     *            ���l
     */
    @Column(columnName = "���l")//$NON-NLS-1$
    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
     * �K���g�`���[�g���Z�b�g����B
     * 
     * @param plotDataMap
     *            �K���g�`���[�g
     */
    @MapColumns(previousColumnName = "���l")
    public void setPlotDataMap(java.util.Map<String, String> plotDataMap) {
        this.plotDataMap = plotDataMap;
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
     * ��v�A�E�g�v�b�g���擾����B
     * 
     * @return ��v�A�E�g�v�b�g
     */
    public String getOutput() {
        return output;
    }

    /**
     * ��T�̊������ѕ񍐂��擾����B
     * 
     * @return ��T�̊������ѕ�
     */
    public String getReportOfLastWeek() {
        return reportOfLastWeek;
    }

    /**
     * ���T�̊����\����擾����B
     * 
     * @return ���T�̊����\��
     */
    public String getReportOfThisWeek() {
        return reportOfThisWeek;
    }

    /**
     * �󋵂��擾����B
     * 
     * @return ��
     */
    public String getStatus() {
        return status;
    }

    /**
     * ���l���擾����B
     * 
     * @return ���l
     */
    public String getRemarks() {
        return remarks;
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
     * �K���g�`���[�g���擾����B
     * 
     * @return �K���g�`���[�g
     */
    public java.util.Map<String, String> getPlotDataMap() {
        return plotDataMap;
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
                .append("�^�X�N�T�v", taskAbstract).append("��v�A�E�g�v�b�g", output)
                .append("��T�̊������ѕ�", reportOfLastWeek)
                .append("���T�̊����\��", reportOfThisWeek).append("��", status)
                .append("���l", remarks).append("���", baseDate)
                .append("�K���g�`���[�g", plotDataMap).toString();
    }
}