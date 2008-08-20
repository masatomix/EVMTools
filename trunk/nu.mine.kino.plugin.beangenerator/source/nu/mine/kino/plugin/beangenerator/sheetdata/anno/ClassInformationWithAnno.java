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
package nu.mine.kino.plugin.beangenerator.sheetdata.anno;

import java.util.List;

import net.java.amateras.xlsbeans.annotation.HorizontalRecords;
import net.java.amateras.xlsbeans.annotation.LabelledCell;
import net.java.amateras.xlsbeans.annotation.LabelledCellType;
import nu.mine.kino.plugin.beangenerator.sheetdata.IClassInformation;
import nu.mine.kino.plugin.beangenerator.sheetdata.IFieldInformation;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class ClassInformationWithAnno implements IClassInformation {
    private List<IFieldInformation> fieldInformations;

    private String classNameJ;

    private String description;

    private String packageName;

    private String className;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("�N���X�����{��", classNameJ).append( //$NON-NLS-1$
                "����", description).append("�p�b�P�[�W��", packageName).append("�N���X��", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                className).append(fieldInformations.toArray()).toString();
    }

    @HorizontalRecords(tableLabel = "�N���X���", recordClass = FieldInformationWithAnno.class)//$NON-NLS-1$
    public void setFieldInformations(List<IFieldInformation> fieldInformations) {
        this.fieldInformations = fieldInformations;
    }

    @LabelledCell(label = "�N���X�����{��", type = LabelledCellType.Right)//$NON-NLS-1$
    public void setClassNameJ(String classNameJ) {
        this.classNameJ = classNameJ;
    }

    @LabelledCell(label = "����", type = LabelledCellType.Right)//$NON-NLS-1$
    public void setDescription(String description) {
        this.description = description;
    }

    @LabelledCell(label = "�p�b�P�[�W��", type = LabelledCellType.Right)//$NON-NLS-1$
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @LabelledCell(label = "�N���X��", type = LabelledCellType.Right)//$NON-NLS-1$
    public void setClassName(String className) {
        this.className = className;
    }

    public List<IFieldInformation> getFieldInformations() {
        return fieldInformations;
    }

    public String getClassNameJ() {
        return classNameJ;
    }

    public String getDescription() {
        return description;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }
}
