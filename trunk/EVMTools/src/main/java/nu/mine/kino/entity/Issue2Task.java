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

import com.taskadapter.redmineapi.bean.Issue;

public class Issue2Task {

    /**
     * �����̃I�u�W�F�N�g�̃v���p�e�B����f�[�^���R�s�[���Ė߂�l�̃I�u�W�F�N�g�𐶐����ĕԂ����\�b�h�B
     * 
     * @param source
     * @return
     */
    public static Task convert(Issue source) {
        Task dest = new Task();

        // �K�v�ɉ����ē���ȍڂ��ւ����� �J�n
        ((Task) dest).setTaskId(Integer.toString(source.getId()));
        ((Task) dest).setTaskName(source.getSubject());
        ((Task) dest).setPersonInCharge(source.getAssignee().getFullName());
        ((Task) dest).setScheduledStartDate(source.getStartDate());
        ((Task) dest).setScheduledEndDate(source.getDueDate());
        ((Task) dest).setNumberOfManDays(toNumberOfManDays(source));

        // ����ȍڂ��ւ����� �I��

        return dest;
    }

    /**
     * ����������������փv���p�e�B���R�s�[���郁�\�b�h�B
     * 
     * @param source
     * @param dest
     */
    public static void convert(Issue source, Task dest) {
        // �K�v�ɉ����ē���ȍڂ��ւ����� �J�n
        ((Task) dest).setTaskId(Integer.toString(source.getId()));
        ((Task) dest).setTaskName(source.getSubject());
        ((Task) dest).setPersonInCharge(source.getAssignee().getFullName());
        ((Task) dest).setScheduledStartDate(source.getStartDate());
        ((Task) dest).setScheduledEndDate(source.getDueDate());
        ((Task) dest).setNumberOfManDays(toNumberOfManDays(source));

        // ����ȍڂ��ւ����� �I��

    }

    public static double toNumberOfManDays(Issue source) {
        return source.getEstimatedHours() != null ? source.getEstimatedHours() / 8.0d
                : Double.NaN;
    }

}
