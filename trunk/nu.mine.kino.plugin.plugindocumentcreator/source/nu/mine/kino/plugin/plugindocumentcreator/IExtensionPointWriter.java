/*******************************************************************************
 * Copyright (c) 2006 Masatomi KINO.
 * All rights reserved. 
 * $Id: IExtensionPointWriter.java,v 1.1 2006/10/29 14:23:31 cvsuser Exp $
 *******************************************************************************/
//�쐬��: 2006/10/29
package nu.mine.kino.plugin.plugindocumentcreator;

/**
 * �g���|�C���g�̏��������o���C���^�t�F�[�X�ł�
 * 
 * @author Masatomi KINO
 * @version $Revision: 1.1 $
 */
public interface IExtensionPointWriter {
    /**
     * �w�肳�ꂽ�g���|�C���gID�̏��������o���܂��B
     * 
     * @param extensionPointName
     */
    void write(String extensionPointName);
}
