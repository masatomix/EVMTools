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
public class BaseEVBean implements java.io.Serializable {

    /**
     * id
     */
    private String id;

    /**
     * ���ID
     */
    private String taskId;

    /**
     * �i����
     */
    private double progressRate;

    /**
     * Earned Value
     */
    private double earnedValue;

    /**
     * ���
     */
    private java.util.Date baseDate;

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
     * �i�������Z�b�g����B
     * 
     * @param progressRate
     *            �i����
     */
    public void setProgressRate(double progressRate) {
        this.progressRate = progressRate;
    }

    /**
     * Earned Value���Z�b�g����B
     * 
     * @param earnedValue
     *            Earned Value
     */
    public void setEarnedValue(double earnedValue) {
        this.earnedValue = earnedValue;
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
     * �i�������擾����B
     * 
     * @return �i����
     */
    public double getProgressRate() {
        return progressRate;
    }

    /**
     * Earned Value���擾����B
     * 
     * @return Earned Value
     */
    public double getEarnedValue() {
        return earnedValue;
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
        return new ToStringBuilder(this).append("id", id)
                .append("���ID", taskId).append("�i����", progressRate)
                .append("Earned Value", earnedValue).append("���", baseDate)
                .toString();
    }
}