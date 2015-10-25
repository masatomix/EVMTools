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

public class Issue2PVTotalBean {

    /**
     * �����̃I�u�W�F�N�g�̃v���p�e�B����f�[�^���R�s�[���Ė߂�l�̃I�u�W�F�N�g�𐶐����ĕԂ����\�b�h�B
     * 
     * @param source
     * @return
     */
    public static PVTotalBean convert(Issue source) {
        PVTotalBean dest = new PVTotalBean();

        // �K�v�ɉ����ē���ȍڂ��ւ����� �J�n
        ((PVTotalBean) dest).setTaskId(Integer.toString(source.getId()));

        // ����ȍڂ��ւ����� �I��

        return dest;
    }

    /**
     * ����������������փv���p�e�B���R�s�[���郁�\�b�h�B
     * 
     * @param source
     * @param dest
     */
    public static void convert(Issue source, PVTotalBean dest) {
        // �K�v�ɉ����ē���ȍڂ��ւ����� �J�n
        ((PVTotalBean) dest).setTaskId(Integer.toString(source.getId()));

        // ����ȍڂ��ւ����� �I��

    }

}
