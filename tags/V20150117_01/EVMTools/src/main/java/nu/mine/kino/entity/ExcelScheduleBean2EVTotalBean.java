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

public class ExcelScheduleBean2EVTotalBean {

    /**
     * �����̃I�u�W�F�N�g�̃v���p�e�B����f�[�^���R�s�[���Ė߂�l�̃I�u�W�F�N�g�𐶐����ĕԂ����\�b�h�B
     * 
     * @param source
     * @return
     */
    public static EVTotalBean convert(ExcelScheduleBean source) {
        EVTotalBean dest = new EVTotalBean();

        // �K�v�ɉ����ē���ȍڂ��ւ����� �J�n
        ((EVTotalBean) dest).setId(source.getId());
        ((EVTotalBean) dest).setTaskId(source.getTaskId());
        ((EVTotalBean) dest).setStartDate(nu.mine.kino.projects.utils.Utils
                .str2Date(source.getStartDate(), "MM/dd"));
        ((EVTotalBean) dest).setEndDate(nu.mine.kino.projects.utils.Utils
                .str2Date(source.getEndDate(), "MM/dd"));
        ((EVTotalBean) dest).setProgressRate(nu.mine.kino.projects.utils.Utils
                .convetPercentStr2Double(source.getProgressRate()));
        ((EVTotalBean) dest).setEarnedValue(nu.mine.kino.projects.utils.Utils
                .convert2Double(source.getEarnedValue()));
        ((EVTotalBean) dest).setBaseDate(source.getBaseDate());

        // ����ȍڂ��ւ����� �I��

        return dest;
    }

    /**
     * ����������������փv���p�e�B���R�s�[���郁�\�b�h�B
     * 
     * @param source
     * @param dest
     */
    public static void convert(ExcelScheduleBean source, EVTotalBean dest) {
        // �K�v�ɉ����ē���ȍڂ��ւ����� �J�n
        ((EVTotalBean) dest).setId(source.getId());
        ((EVTotalBean) dest).setTaskId(source.getTaskId());
        ((EVTotalBean) dest).setStartDate(nu.mine.kino.projects.utils.Utils
                .str2Date(source.getStartDate(), "MM/dd"));
        ((EVTotalBean) dest).setEndDate(nu.mine.kino.projects.utils.Utils
                .str2Date(source.getEndDate(), "MM/dd"));
        ((EVTotalBean) dest).setProgressRate(nu.mine.kino.projects.utils.Utils
                .convetPercentStr2Double(source.getProgressRate()));
        ((EVTotalBean) dest).setEarnedValue(nu.mine.kino.projects.utils.Utils
                .convert2Double(source.getEarnedValue()));
        ((EVTotalBean) dest).setBaseDate(source.getBaseDate());

        // ����ȍڂ��ւ����� �I��

    }

}
