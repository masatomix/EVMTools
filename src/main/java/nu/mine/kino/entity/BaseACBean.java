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
public class BaseACBean implements java.io.Serializable {

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

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id)
                .append("���ID", taskId).append("Actual Cost", actualCost)
                .append("���", baseDate).toString();
    }
}