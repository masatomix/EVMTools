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
public class TaskInformation implements java.io.Serializable {

    /**
     * id
     */
    private String id;

    /**
     * ���ID
     */
    private String taskId;

    /**
     * �^�X�N
     */
    private nu.mine.kino.entity.Task task;

    /**
     * PV
     */
    private nu.mine.kino.entity.PVTotalBean PV;

    /**
     * EV
     */
    private nu.mine.kino.entity.EVTotalBean EV;

    /**
     * AC
     */
    private nu.mine.kino.entity.ACTotalBean AC;

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
     * �^�X�N���Z�b�g����B
     * 
     * @param task
     *            �^�X�N
     */
    public void setTask(nu.mine.kino.entity.Task task) {
        this.task = task;
    }

    /**
     * PV���Z�b�g����B
     * 
     * @param PV
     *            PV
     */
    public void setPV(nu.mine.kino.entity.PVTotalBean PV) {
        this.PV = PV;
    }

    /**
     * EV���Z�b�g����B
     * 
     * @param EV
     *            EV
     */
    public void setEV(nu.mine.kino.entity.EVTotalBean EV) {
        this.EV = EV;
    }

    /**
     * AC���Z�b�g����B
     * 
     * @param AC
     *            AC
     */
    public void setAC(nu.mine.kino.entity.ACTotalBean AC) {
        this.AC = AC;
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
     * �^�X�N���擾����B
     * 
     * @return �^�X�N
     */
    public nu.mine.kino.entity.Task getTask() {
        return task;
    }

    /**
     * PV���擾����B
     * 
     * @return PV
     */
    public nu.mine.kino.entity.PVTotalBean getPV() {
        return PV;
    }

    /**
     * EV���擾����B
     * 
     * @return EV
     */
    public nu.mine.kino.entity.EVTotalBean getEV() {
        return EV;
    }

    /**
     * AC���擾����B
     * 
     * @return AC
     */
    public nu.mine.kino.entity.ACTotalBean getAC() {
        return AC;
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
                .append("���ID", taskId).append("�^�X�N", task).append("PV", PV)
                .append("EV", EV).append("AC", AC).append("���", baseDate)
                .toString();
    }
}