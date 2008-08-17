/******************************************************************************
 * Copyright (c) 2008 Masatomi KINO and others. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *      Masatomi KINO - initial API and implementation
 * $Id$
 ******************************************************************************/
// �쐬��: 2008/08/15
package nu.mine.kino.utils.beangenerator.sheetdata;

import org.apache.commons.lang.builder.ToStringBuilder;

import net.java.amateras.xlsbeans.annotation.Column;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class FieldInformation {

    private String fieldNameJ;

    private String fieldName;

    private String fieldType;

    private String description;

    @Column(columnName = "�t�B�[���h�����{��")
    public void setFieldNameJ(String fieldNameJ) {
        this.fieldNameJ = fieldNameJ;
    }

    @Column(columnName = "�t�B�[���h��")
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Column(columnName = "�^")
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    @Column(columnName = "����")
    public void setDescription(String description) {
        this.description = description;
    }

    public String getFieldNameJ() {
        return fieldNameJ;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("�t�B�[���h�����{��", fieldNameJ)
                .append("����", description).append("�t�B�[���h��", fieldName).append(
                        "�^", fieldType).toString();
    }
}
