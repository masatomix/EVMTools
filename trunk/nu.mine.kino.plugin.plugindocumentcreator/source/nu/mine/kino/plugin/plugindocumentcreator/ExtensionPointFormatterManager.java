/*******************************************************************************
 * Copyright (c) 2006 Masatomi KINO.
 * All rights reserved. 
 * $Id$
 *******************************************************************************/
//�쐬��: 2006/10/22
package nu.mine.kino.plugin.plugindocumentcreator;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * �g���|�C���g�����o�͂���t�H�[�}�b�^�N���X���Ǘ�����N���X�ł��B
 * 
 * @author Masatomi KINO
 * @version $Revision$
 */
public class ExtensionPointFormatterManager {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(ExtensionPointFormatterManager.class);

    /**
     * �����̊g���|�C���gID����A�g���|�C���g�t�H�[�}�b�^�[���擾���郁�\�b�h�ł��B
     * 
     * @param extensionPointName
     * @return
     */
    public IExtensionPointFormatter getExtensionFormatter(
            String extensionPointName) {
        logger.debug("getExtensionFormatter(String) - start");

        // �v���O�C���̃��W�X�g���擾
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        // ���W�X�g������A�g���|�C���g���Ŋg���|�C���g���擾
        IExtensionPoint point = registry
                .getExtensionPoint("nu.mine.kino.plugin.plugindocumentcreator.formatters");
        // nu.mine.kino.plugin.plugindocumentcreator.formatters �g���|�C���g
        // ���g���Ă���v���O�C���ꗗ���擾�B
        IConfigurationElement[] configurationElements = point
                .getConfigurationElements();
        // ���邮�����āA�����̊g���|�C���gID�Ɠ����L�q���������A�t�H�[�}�b�^���쐬���ĕԂ��B
        // �}�b�`����L�q�́A2006/10/29���݁A������Ȃ��z��B
        for (int i = 0; i < configurationElements.length; i++) {
            IConfigurationElement element = configurationElements[i];
            String id = element.getAttribute("extension-point-id");
            if (id.equals(extensionPointName)) {
                logger.debug(extensionPointName + "�p�̃t�H�[�}�b�^�𐶐����܂�");
                try {
                    // �g���|�C���g�Ɠ��̃C���X�^���X�������@�B
                    IExtensionPointFormatter formatter = (IExtensionPointFormatter) element
                            .createExecutableExtension("class");
                    logger.debug("getExtensionFormatter(String) - end");
                    return formatter;
                } catch (CoreException e) {
                    logger.error("getExtensionFormatter(String)", e);
                    Activator.logException(e, false);
                    break;
                }
            }
        }
        logger.debug(extensionPointName + "�ɂ̓f�t�H���g�̃t�H�[�}�b�^���g�p���܂�");
        logger.debug("getExtensionFormatter(String) - end");
        return new DefaultExtensionPointFormatter();
    }
}
