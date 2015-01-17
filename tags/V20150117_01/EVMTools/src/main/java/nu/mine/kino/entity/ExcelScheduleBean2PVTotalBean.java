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

public class ExcelScheduleBean2PVTotalBean {

    /**
     * �����̃I�u�W�F�N�g�̃v���p�e�B����f�[�^���R�s�[���Ė߂�l�̃I�u�W�F�N�g�𐶐����ĕԂ����\�b�h�B
     * 
     * @param source
     * @return
     */
    public static PVTotalBean convert(ExcelScheduleBean source) {
        PVTotalBean dest = new PVTotalBean();

        // �K�v�ɉ����ē���ȍڂ��ւ����� �J�n
        ((PVTotalBean) dest).setId(source.getId());
        ((PVTotalBean) dest).setTaskId(source.getTaskId());
        ((PVTotalBean) dest).setPlannedValue(nu.mine.kino.projects.utils.Utils
                .convert2Double(source.getPlannedValue()));
        ((PVTotalBean) dest).setBaseDate(source.getBaseDate());

        // ����ȍڂ��ւ����� �I��

        return dest;
    }

    /**
     * ����������������փv���p�e�B���R�s�[���郁�\�b�h�B
     * 
     * @param source
     * @param dest
     */
    public static void convert(ExcelScheduleBean source, PVTotalBean dest) {
        // �K�v�ɉ����ē���ȍڂ��ւ����� �J�n
        ((PVTotalBean) dest).setId(source.getId());
        ((PVTotalBean) dest).setTaskId(source.getTaskId());
        ((PVTotalBean) dest).setPlannedValue(nu.mine.kino.projects.utils.Utils
                .convert2Double(source.getPlannedValue()));
        ((PVTotalBean) dest).setBaseDate(source.getBaseDate());

        // ����ȍڂ��ւ����� �I��

    }

}
