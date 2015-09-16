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
public class ExcelPOIScheduleBean extends ExcelScheduleBean {

    /**
     * ���ID
     */
    private String taskId;

    /**
     * �\��H��
     */
    private Double numberOfManDays;

    /**
     * �\��J�n��
     */
    private java.util.Date scheduledStartDate;

    /**
     * �\��I����
     */
    private java.util.Date scheduledEndDate;

    /**
     * ���ъJ�n��
     */
    private java.util.Date startDate;

    /**
     * ���яI����
     */
    private java.util.Date endDate;

    /**
     * �i����
     */
    private Double progressRate;

    /**
     * �ғ��\�����
     */
    private Integer numberOfDays;

    /**
     * Planned Value
     */
    private Double plannedValue;

    /**
     * Earned Value
     */
    private Double earnedValue;

    /**
     * Actual Cost
     */
    private Double actualCost;

    /**
     * ���
     */
    private java.util.Date baseDate;

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
     * �\��H�����Z�b�g����B
     * 
     * @param numberOfManDays
     *            �\��H��
     */
    public void setNumberOfManDays(Double numberOfManDays) {
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
     * ���ъJ�n�����Z�b�g����B
     * 
     * @param startDate
     *            ���ъJ�n��
     */
    public void setStartDate(java.util.Date startDate) {
        this.startDate = startDate;
    }

    /**
     * ���яI�������Z�b�g����B
     * 
     * @param endDate
     *            ���яI����
     */
    public void setEndDate(java.util.Date endDate) {
        this.endDate = endDate;
    }

    /**
     * �i�������Z�b�g����B
     * 
     * @param progressRate
     *            �i����
     */
    public void setProgressRate(Double progressRate) {
        this.progressRate = progressRate;
    }

    /**
     * �ғ��\��������Z�b�g����B
     * 
     * @param numberOfDays
     *            �ғ��\�����
     */
    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    /**
     * Planned Value���Z�b�g����B
     * 
     * @param plannedValue
     *            Planned Value
     */
    public void setPlannedValue(Double plannedValue) {
        this.plannedValue = plannedValue;
    }

    /**
     * Earned Value���Z�b�g����B
     * 
     * @param earnedValue
     *            Earned Value
     */
    public void setEarnedValue(Double earnedValue) {
        this.earnedValue = earnedValue;
    }

    /**
     * Actual Cost���Z�b�g����B
     * 
     * @param actualCost
     *            Actual Cost
     */
    public void setActualCost(Double actualCost) {
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
     * ���ID���擾����B
     * 
     * @return ���ID
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * �\��H�����擾����B
     * 
     * @return �\��H��
     */
    public Double getNumberOfManDays() {
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
     * ���ъJ�n�����擾����B
     * 
     * @return ���ъJ�n��
     */
    public java.util.Date getStartDate() {
        return startDate;
    }

    /**
     * ���яI�������擾����B
     * 
     * @return ���яI����
     */
    public java.util.Date getEndDate() {
        return endDate;
    }

    /**
     * �i�������擾����B
     * 
     * @return �i����
     */
    public Double getProgressRate() {
        return progressRate;
    }

    /**
     * �ғ��\��������擾����B
     * 
     * @return �ғ��\�����
     */
    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    /**
     * Planned Value���擾����B
     * 
     * @return Planned Value
     */
    public Double getPlannedValue() {
        return plannedValue;
    }

    /**
     * Earned Value���擾����B
     * 
     * @return Earned Value
     */
    public Double getEarnedValue() {
        return earnedValue;
    }

    /**
     * Actual Cost���擾����B
     * 
     * @return Actual Cost
     */
    public Double getActualCost() {
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

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("���ID", taskId)
                .append("�\��H��", numberOfManDays)
                .append("�\��J�n��", scheduledStartDate)
                .append("�\��I����", scheduledEndDate).append("���ъJ�n��", startDate)
                .append("���яI����", endDate).append("�i����", progressRate)
                .append("�ғ��\�����", numberOfDays)
                .append("Planned Value", plannedValue)
                .append("Earned Value", earnedValue)
                .append("Actual Cost", actualCost).append("���", baseDate)
                .toString();
    }
}