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

public class ExcelPOIScheduleBean2Task {

    /**
     * �����̃I�u�W�F�N�g�̃v���p�e�B����f�[�^���R�s�[���Ė߂�l�̃I�u�W�F�N�g�𐶐����ĕԂ����\�b�h�B
     * 
     * @param source
     * @return
     */
    public static Task convert(ExcelPOIScheduleBean source) {
        Task dest = new Task();

        // �K�v�ɉ����ē���ȍڂ��ւ����� �J�n
        ((Task) dest).setId(source.getId());
        ((Task) dest).setTaskId(source.getTaskId());
        ((Task) dest).setType(source.getType());
        ((Task) dest).setTaskSharp(source.getTaskSharp());
        ((Task) dest).setTaskName(source.getTaskName());
        ((Task) dest).setTaskName1(source.getTaskName1());
        ((Task) dest).setTaskName2(source.getTaskName2());
        ((Task) dest).setTaskName3(source.getTaskName3());
        ((Task) dest).setSenkoTaskId(source.getSenkoTaskId());
        ((Task) dest).setPersonInCharge(source.getPersonInCharge());
        ((Task) dest).setTaskAbstract(source.getTaskAbstract());
        ((Task) dest).setScheduledEndDate(scheduledEndDate(source, dest));
        ((Task) dest).setScheduledStartDate(scheduledStartDate(source, dest));
        ((Task) dest).setNumberOfDays(source.getNumberOfDays() == null ? 0
                : source.getNumberOfDays());
        ((Task) dest)
                .setNumberOfManDays(source.getNumberOfManDays() == null ? Double.NaN
                        : source.getNumberOfManDays());

        // ����ȍڂ��ւ����� �I��

        return dest;
    }

    /**
     * ����������������փv���p�e�B���R�s�[���郁�\�b�h�B
     * 
     * @param source
     * @param dest
     */
    public static void convert(ExcelPOIScheduleBean source, Task dest) {
        // �K�v�ɉ����ē���ȍڂ��ւ����� �J�n
        ((Task) dest).setId(source.getId());
        ((Task) dest).setTaskId(source.getTaskId());
        ((Task) dest).setType(source.getType());
        ((Task) dest).setTaskSharp(source.getTaskSharp());
        ((Task) dest).setTaskName(source.getTaskName());
        ((Task) dest).setTaskName1(source.getTaskName1());
        ((Task) dest).setTaskName2(source.getTaskName2());
        ((Task) dest).setTaskName3(source.getTaskName3());
        ((Task) dest).setSenkoTaskId(source.getSenkoTaskId());
        ((Task) dest).setPersonInCharge(source.getPersonInCharge());
        ((Task) dest).setTaskAbstract(source.getTaskAbstract());
        ((Task) dest).setScheduledEndDate(scheduledEndDate(source, dest));
        ((Task) dest).setScheduledStartDate(scheduledStartDate(source, dest));
        ((Task) dest).setNumberOfDays(source.getNumberOfDays() == null ? 0
                : source.getNumberOfDays());
        ((Task) dest)
                .setNumberOfManDays(source.getNumberOfManDays() == null ? Double.NaN
                        : source.getNumberOfManDays());

        // ����ȍڂ��ւ����� �I��

    }

    private static java.util.Date scheduledEndDate(ExcelPOIScheduleBean source,
            Task dest) {
        if (source.getScheduledEndDate() != null) {
            return source.getScheduledEndDate();
        }
        return dest.getScheduledEndDate();
    }

    private static java.util.Date scheduledStartDate(ExcelPOIScheduleBean source,
            Task dest) {
        if (source.getScheduledStartDate() != null) {
            return source.getScheduledStartDate();
        }
        return dest.getScheduledStartDate();
    }

}
