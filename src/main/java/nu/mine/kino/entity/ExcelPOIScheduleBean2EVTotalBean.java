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

public class ExcelPOIScheduleBean2EVTotalBean {

    /**
     * �����̃I�u�W�F�N�g�̃v���p�e�B����f�[�^���R�s�[���Ė߂�l�̃I�u�W�F�N�g�𐶐����ĕԂ����\�b�h�B
     * 
     * @param source
     * @return
     */
    public static EVTotalBean convert(ExcelPOIScheduleBean source) {
        EVTotalBean dest = new EVTotalBean();

        // �K�v�ɉ����ē���ȍڂ��ւ����� �J�n
        ((EVTotalBean) dest).setId(source.getId());
        ((EVTotalBean) dest).setTaskId(source.getTaskId());
        ((EVTotalBean) dest).setBaseDate(source.getBaseDate());
        ((EVTotalBean) dest).setStartDate(startDate(source, dest));
        ((EVTotalBean) dest).setEndDate(endDate(source, dest));
        ((EVTotalBean) dest)
                .setEarnedValue(source.getEarnedValue() == null ? Double.NaN
                        : source.getEarnedValue());
        ((EVTotalBean) dest)
                .setProgressRate(source.getProgressRate() == null ? Double.NaN
                        : source.getProgressRate());

        // ����ȍڂ��ւ����� �I��

        return dest;
    }

    /**
     * ����������������փv���p�e�B���R�s�[���郁�\�b�h�B
     * 
     * @param source
     * @param dest
     */
    public static void convert(ExcelPOIScheduleBean source, EVTotalBean dest) {
        // �K�v�ɉ����ē���ȍڂ��ւ����� �J�n
        ((EVTotalBean) dest).setId(source.getId());
        ((EVTotalBean) dest).setTaskId(source.getTaskId());
        ((EVTotalBean) dest).setBaseDate(source.getBaseDate());
        ((EVTotalBean) dest).setStartDate(startDate(source, dest));
        ((EVTotalBean) dest).setEndDate(endDate(source, dest));
        ((EVTotalBean) dest)
                .setEarnedValue(source.getEarnedValue() == null ? Double.NaN
                        : source.getEarnedValue());
        ((EVTotalBean) dest)
                .setProgressRate(source.getProgressRate() == null ? Double.NaN
                        : source.getProgressRate());

        // ����ȍڂ��ւ����� �I��

    }

    private static java.util.Date startDate(ExcelPOIScheduleBean source,
            EVTotalBean dest) {
        return source.getStartDate() != null ? source.getStartDate() : dest
                .getStartDate();
    }

    private static java.util.Date endDate(ExcelPOIScheduleBean source,
            EVTotalBean dest) {
        return source.getEndDate() != null ? source.getEndDate() : dest
                .getEndDate();
    }

}
