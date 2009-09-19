/******************************************************************************
 * Copyright (c) 2008-2009 Masatomi KINO and others. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *      Masatomi KINO - initial API and implementation
 * $Id$
 ******************************************************************************/

package nu.mine.kino.csv;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * JavaBeans Generator�ׂ̈̃T���v��JavaBeans�ł��B
 * 
 * @author Masatomi KINO and JavaBeans Creator Plug-in
 * @version $Revision$
 */
public class CSVSampleBean implements Serializable {

    /**
     * �� �ł�
     */
    private String last_name;

    /**
     * �� �ł�
     */
    private String first_name;

    /**
     * �N�� �ł�
     */
    private String age;

    /**
     * �����Z�b�g����B
     * 
     * @param last_name
     *            ��
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * �����Z�b�g����B
     * 
     * @param first_name
     *            ��
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * �N����Z�b�g����B
     * 
     * @param age
     *            �N��
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * �����擾����B
     * 
     * @return ��
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     * �����擾����B
     * 
     * @return ��
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * �N����擾����B
     * 
     * @return �N��
     */
    public String getAge() {
        return age;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("��", last_name).append("��",
                first_name).append("�N��", age).toString();
    }
}