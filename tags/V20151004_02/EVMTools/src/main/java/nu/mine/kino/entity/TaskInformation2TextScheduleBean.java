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

public class TaskInformation2TextScheduleBean {

    /**
     * �����̃I�u�W�F�N�g�̃v���p�e�B����f�[�^���R�s�[���Ė߂�l�̃I�u�W�F�N�g�𐶐����ĕԂ����\�b�h�B
     * 
     * @param source
     * @return
     */
    public static TextScheduleBean convert(TaskInformation source) {
        TextScheduleBean dest = new TextScheduleBean();

        // �K�v�ɉ����ē���ȍڂ��ւ����� �J�n
        ((TextScheduleBean) dest).setTaskId(source.getTaskId());
        ((TextScheduleBean) dest).setTaskName(source.getTask().getTaskName());
        ((TextScheduleBean) dest).setPersonInCharge(source.getTask()
                .getPersonInCharge());
        ((TextScheduleBean) dest).setNumberOfManDays(source.getTask()
                .getNumberOfManDays());
        ((TextScheduleBean) dest).setScheduledStartDate(source.getTask()
                .getScheduledStartDate());
        ((TextScheduleBean) dest).setScheduledEndDate(source.getTask()
                .getScheduledEndDate());
        ((TextScheduleBean) dest).setProgressRate(source.getEV()
                .getProgressRate());
        ((TextScheduleBean) dest).setNumberOfDays(source.getTask()
                .getNumberOfDays());
        ((TextScheduleBean) dest).setPlannedValue(source.getPV()
                .getPlannedValue());
        ((TextScheduleBean) dest).setEarnedValue(source.getEV()
                .getEarnedValue());
        ((TextScheduleBean) dest).setActualCost(source.getAC().getActualCost());
        ((TextScheduleBean) dest).setBaseDate(source.getBaseDate());

        // ����ȍڂ��ւ����� �I��

        return dest;
    }

    /**
     * ����������������փv���p�e�B���R�s�[���郁�\�b�h�B
     * 
     * @param source
     * @param dest
     */
    public static void convert(TaskInformation source, TextScheduleBean dest) {
        // �K�v�ɉ����ē���ȍڂ��ւ����� �J�n
        ((TextScheduleBean) dest).setTaskId(source.getTaskId());
        ((TextScheduleBean) dest).setTaskName(source.getTask().getTaskName());
        ((TextScheduleBean) dest).setPersonInCharge(source.getTask()
                .getPersonInCharge());
        ((TextScheduleBean) dest).setNumberOfManDays(source.getTask()
                .getNumberOfManDays());
        ((TextScheduleBean) dest).setScheduledStartDate(source.getTask()
                .getScheduledStartDate());
        ((TextScheduleBean) dest).setScheduledEndDate(source.getTask()
                .getScheduledEndDate());
        ((TextScheduleBean) dest).setProgressRate(source.getEV()
                .getProgressRate());
        ((TextScheduleBean) dest).setNumberOfDays(source.getTask()
                .getNumberOfDays());
        ((TextScheduleBean) dest).setPlannedValue(source.getPV()
                .getPlannedValue());
        ((TextScheduleBean) dest).setEarnedValue(source.getEV()
                .getEarnedValue());
        ((TextScheduleBean) dest).setActualCost(source.getAC().getActualCost());
        ((TextScheduleBean) dest).setBaseDate(source.getBaseDate());

        // ����ȍڂ��ւ����� �I��

    }

}
