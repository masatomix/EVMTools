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

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author Masatomi KINO and JavaBeans Creator Plug-in
 */
public class BaseHoliday {

    /**
     * ���t
     */
    private java.util.Date date;

    /**
     * �j��
     */
    private String dayOfWeek;

    /**
     * �j��
     */
    private String name;

    /**
     * �j����`���[��
     */
    private String rule;

    /**
     * �U��
     */
    private String hurikae;

    /**
     * ���t���Z�b�g����B
     * 
     * @param date
     *            ���t
     */
    public void setDate(java.util.Date date) {
        this.date = date;
    }

    /**
     * �j�����Z�b�g����B
     * 
     * @param dayOfWeek
     *            �j��
     */
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * �j�����Z�b�g����B
     * 
     * @param name
     *            �j��
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * �j����`���[�����Z�b�g����B
     * 
     * @param rule
     *            �j����`���[��
     */
    public void setRule(String rule) {
        this.rule = rule;
    }

    /**
     * �U�ւ��Z�b�g����B
     * 
     * @param hurikae
     *            �U��
     */
    public void setHurikae(String hurikae) {
        this.hurikae = hurikae;
    }

    /**
     * ���t���擾����B
     * 
     * @return ���t
     */
    public java.util.Date getDate() {
        return date;
    }

    /**
     * �j�����擾����B
     * 
     * @return �j��
     */
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * �j�����擾����B
     * 
     * @return �j��
     */
    public String getName() {
        return name;
    }

    /**
     * �j����`���[�����擾����B
     * 
     * @return �j����`���[��
     */
    public String getRule() {
        return rule;
    }

    /**
     * �U�ւ��擾����B
     * 
     * @return �U��
     */
    public String getHurikae() {
        return hurikae;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("���t", date)
                .append("�j��", dayOfWeek).append("�j��", name)
                .append("�j����`���[��", rule).append("�U��", hurikae).toString();
    }
}