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

import net.java.amateras.xlsbeans.annotation.MapColumns;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Excel�f�[�^��ǂݍ���ŊǗ�����JavaBeans�B�ق�POI�Ɏ���đ����āA
 * POI�Ŏ擾����JavaBeans����t�B�[���h�Ƀf�[�^���R�s�[���Ă�����Ă���B
 * 
 * @author Masatomi KINO and JavaBeans Creator Plug-in
 */
public class ExcelScheduleBean extends ExcelPOIScheduleBean {

    /**
     * �v���b�g���ꂽ���̃f�[�^
     */
    private java.util.Map<String, String> plotDataMap;

    /**
     * �K���g�`���[�g���Z�b�g����B
     * 
     * @param plotDataMap
     *            �K���g�`���[�g
     */
    @MapColumns(previousColumnName = "���l")
    public void setPlotDataMap(java.util.Map<String, String> plotDataMap) {
        this.plotDataMap = plotDataMap;
    }

    /**
     * �K���g�`���[�g���擾����B
     * 
     * @return �K���g�`���[�g
     */
    public java.util.Map<String, String> getPlotDataMap() {
        return plotDataMap;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("�K���g�`���[�g", plotDataMap)
                .toString();
    }
}