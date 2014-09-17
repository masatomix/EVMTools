/******************************************************************************
 * Copyright (c) 2012 Masatomi KINO and others. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *      Masatomi KINO - initial API and implementation
 * $Id$
 ******************************************************************************/
//�쐬��: 2014/09/11

package nu.mine.kino.plugin.beangenerator.copyutils.sheetdata;

import net.java.amateras.xlsbeans.annotation.HorizontalRecords;
import net.java.amateras.xlsbeans.annotation.LabelledCell;
import net.java.amateras.xlsbeans.annotation.LabelledCellType;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public interface IUtilInformation {

    /**
     * 
     * @param packageName
     */
    @LabelledCell(label = "�p�b�P�[�W��", type = LabelledCellType.Right)//$NON-NLS-1$    	
    public void setPackageName(String packageName);

    /**
     * 
     * @param sourceIFName
     */
    @LabelledCell(label = "�R�s�[���C���^�t�F�[�X��", type = LabelledCellType.Right)//$NON-NLS-1$    	
    public void setSourceIFName(String sourceIFName);

    /**
     * 
     * @param destIFName
     */
    @LabelledCell(label = "�R�s�[��C���^�t�F�[�X��", type = LabelledCellType.Right)//$NON-NLS-1$    	
    public void setDestIFName(String destIFName);

    /**
     * 
     * @param destClassName
     */
    @LabelledCell(label = "�R�s�[��N���X��", type = LabelledCellType.Right)//$NON-NLS-1$    	
    public void setDestClassName(String destClassName);

    /**
     * 
     * @param className
     */
    @LabelledCell(label = "�N���X��", type = LabelledCellType.Right)//$NON-NLS-1$    	
    public void setClassName(String className);

    /**
     * 
     * @param methodName
     */
    @LabelledCell(label = "���\�b�h��", type = LabelledCellType.Right)//$NON-NLS-1$    	
    public void setMethodName(String methodName);

    /**
     * 
     * @param useBeanUtils
     */
    @LabelledCell(label = "BeanUtils���g�p����", type = LabelledCellType.Right)//$NON-NLS-1$    	
    public void setUseBeanUtils(String useBeanUtils);

    /**
     * 
     * @param methods
     */
    @LabelledCell(label = "�ǉ����\�b�h", type = LabelledCellType.Right)//$NON-NLS-1$    	
    public void setMethods(String methods);

    /**
     * 
     * @param Details
     */
    @HorizontalRecords(tableLabel = "�ڂ��ւ����[�e�B���e�B���", recordClass = DetailInformation.class)//$NON-NLS-1$	
    public void setDetails(java.util.List<IDetailInformation> Details);

    public String getPackageName();

    public String getSourceIFName();

    public String getDestIFName();

    public String getDestClassName();

    public String getClassName();

    public String getMethodName();

    public String getUseBeanUtils();

    public String getMethods();

    public java.util.List<IDetailInformation> getDetails();

}