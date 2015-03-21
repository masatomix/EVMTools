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

public class EVBean2PVACEVViewBean {

    /**
     * �����̃I�u�W�F�N�g�̃v���p�e�B����f�[�^���R�s�[���Ė߂�l�̃I�u�W�F�N�g�𐶐����ĕԂ����\�b�h�B
     * 
     * @param source
     * @return
     */
    public static PVACEVViewBean convert(EVBean source) {
        PVACEVViewBean dest = new PVACEVViewBean();

        // �K�v�ɉ����ē���ȍڂ��ւ����� �J�n
        ((PVACEVViewBean) dest).setProgressRate(source.getProgressRate());
        ((PVACEVViewBean) dest).setEarnedValue(source.getEarnedValue());
        ((PVACEVViewBean) dest).setBaseDate(source.getBaseDate());

        // ����ȍڂ��ւ����� �I��

        return dest;
    }

    /**
     * ����������������փv���p�e�B���R�s�[���郁�\�b�h�B
     * 
     * @param source
     * @param dest
     */
    public static void convert(EVBean source, PVACEVViewBean dest) {
        // �K�v�ɉ����ē���ȍڂ��ւ����� �J�n
        ((PVACEVViewBean) dest).setProgressRate(source.getProgressRate());
        ((PVACEVViewBean) dest).setEarnedValue(source.getEarnedValue());
        ((PVACEVViewBean) dest).setBaseDate(source.getBaseDate());

        // ����ȍڂ��ւ����� �I��

    }

}