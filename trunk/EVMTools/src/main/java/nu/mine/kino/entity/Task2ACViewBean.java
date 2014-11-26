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

public class Task2ACViewBean {

    /**
     * �����̃I�u�W�F�N�g�̃v���p�e�B����f�[�^���R�s�[���Ė߂�l�̃I�u�W�F�N�g�𐶐����ĕԂ����\�b�h�B
     * 
     * @param source
     * @return
     */
    public static ACViewBean convert(Task source) {
        ACViewBean dest = new ACViewBean();

        // �K�v�ɉ����ē���ȍڂ��ւ����� �J�n
        ((ACViewBean) dest).setId(source.getId());
        ((ACViewBean) dest).setTaskId(source.getTaskId());
        ((ACViewBean) dest).setType(source.getType());
        ((ACViewBean) dest).setTaskName(source.getTaskName());
        ((ACViewBean) dest).setPersonInCharge(source.getPersonInCharge());
        ((ACViewBean) dest).setTaskAbstract(source.getTaskAbstract());
        ((ACViewBean) dest).setNumberOfManDays(source.getNumberOfManDays());
        ((ACViewBean) dest).setScheduledStartDate(source
                .getScheduledStartDate());
        ((ACViewBean) dest).setScheduledEndDate(source.getScheduledEndDate());
        ((ACViewBean) dest).setNumberOfDays(source.getNumberOfDays());

        // ����ȍڂ��ւ����� �I��

        return dest;
    }

    /**
     * ����������������փv���p�e�B���R�s�[���郁�\�b�h�B
     * 
     * @param source
     * @param dest
     */
    public static void convert(Task source, ACViewBean dest) {
        // �K�v�ɉ����ē���ȍڂ��ւ����� �J�n
        ((ACViewBean) dest).setId(source.getId());
        ((ACViewBean) dest).setTaskId(source.getTaskId());
        ((ACViewBean) dest).setType(source.getType());
        ((ACViewBean) dest).setTaskName(source.getTaskName());
        ((ACViewBean) dest).setPersonInCharge(source.getPersonInCharge());
        ((ACViewBean) dest).setTaskAbstract(source.getTaskAbstract());
        ((ACViewBean) dest).setNumberOfManDays(source.getNumberOfManDays());
        ((ACViewBean) dest).setScheduledStartDate(source
                .getScheduledStartDate());
        ((ACViewBean) dest).setScheduledEndDate(source.getScheduledEndDate());
        ((ACViewBean) dest).setNumberOfDays(source.getNumberOfDays());

        // ����ȍڂ��ւ����� �I��

    }

}
