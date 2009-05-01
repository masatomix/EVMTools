/******************************************************************************
 * Copyright (c) 2009 Masatomi KINO and others. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *      Masatomi KINO - initial API and implementation
 * $Id$
 ******************************************************************************/
//�쐬��: 2006/10/22
package nu.mine.kino.plugin.plugindocumentcreator;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

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
     * ������ID����A�g���|�C���g�t�H�[�}�b�^�[���擾���郁�\�b�h�ł��B
     * ����ID�͐ݒ��̃L�[�ƂȂ��Ă���ID�ł���A�g���|�C���gID�ł͂Ȃ����Ƃɒ��ӁB
     * 
     * @param id
     *            �g���|�C���g��ID�ł͂Ȃ��B
     * @return
     */
    public IExtensionPointFormatter getExtensionFormatter(String id) {
        logger.debug("getExtensionFormatter(String) - start");
        // �g���|�C���gID�ƁA�T�u�v�f�̖��̂��g���āA�T�u�v�f�̃��X�g���擾����Utils���\�b�h�B
        List<IConfigurationElement> list = Utils.getConfigurationElements(
                "nu.mine.kino.plugin.plugindocumentcreator.formatters",
                "formatter");
        // ���邮�����āA������ID�̐ݒ���������A�t�H�[�}�b�^���쐬���ĕԂ��B
        for (IConfigurationElement element : list) {
            String keyId = element.getAttribute("id");
            // ��̃L�[�ƁA�����̃L�[����v�����ꍇ�A
            if (keyId.equals(id)) {
                logger.debug(id + "�p�̃t�H�[�}�b�^�𐶐����܂�");
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
        logger.debug(id + "�ɂ̓f�t�H���g�̃t�H�[�}�b�^���g�p���܂�");
        logger.debug("getExtensionFormatter(String) - end");
        return new DefaultExtensionPointFormatter();
    }
}
