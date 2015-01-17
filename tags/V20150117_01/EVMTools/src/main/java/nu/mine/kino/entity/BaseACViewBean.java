/******************************************************************************
 * Copyright (c) 2008-2014 Masatomi KINO and others. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *      Masatomi KINO - initial API and implementation
 ******************************************************************************/

package nu.mine.kino.entity;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * ��ʂŎg�p����AC���
 * 
 * @author Masatomi KINO and JavaBeans Creator Plug-in
 */
public class BaseACViewBean {

    /**
     * id
     */
    private String id;

    /**
     * ���ID
     */
    private String taskId;

    /**
     * Actual Cost
     */
    private double actualCost;

    /**
     * ���
     */
    private java.util.Date baseDate;

    /**
     * ���� (From Task)
     */
    private String type;

    /**
     * �^�X�N�́��ɊY������� (From Task)
     */
    private String taskSharp;

    /**
     * �^�X�N�� (From Task)
     */
    private String taskName;

    /**
     * �S�� (From Task)
     */
    private String personInCharge;

    /**
     * �^�X�N�T�v (From Task)
     */
    private String taskAbstract;

    /**
     * �\��H�� (From Task)
     */
    private double numberOfManDays;

    /**
     * �\��J�n�� (From Task)
     */
    private java.util.Date scheduledStartDate;

    /**
     * �\��I���� (From Task)
     */
    private java.util.Date scheduledEndDate;

    /**
     * �ғ��\����� (From Task)
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
     * Actual Cost���Z�b�g����B
     * 
     * @param actualCost
     *            Actual Cost
     */
    public void setActualCost(double actualCost) {
        this.actualCost = actualCost;
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
     * Actual Cost���擾����B
     * 
     * @return Actual Cost
     */
    public double getActualCost() {
        return actualCost;
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
                .append("���ID", taskId).append("Actual Cost", actualCost)
                .append("���", baseDate).append("����", type)
                .append("�^�X�N�́��ɊY�������", taskSharp).append("�^�X�N��", taskName)
                .append("�S��", personInCharge).append("�^�X�N�T�v", taskAbstract)
                .append("�\��H��", numberOfManDays)
                .append("�\��J�n��", scheduledStartDate)
                .append("�\��I����", scheduledEndDate)
                .append("�ғ��\�����", numberOfDays).toString();
    }
}