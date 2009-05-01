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
//�쐬��: 2009/05/01
package nu.mine.kino.plugin.plugindocumentcreator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class Utils {
    /**
     * �w�肵���g���|�C���g�ɑ΂��āA������ނ̃T�u�v�f������ꍇ�Aid�Ŏw�肵���T�u�v�f��{@link IConfigurationElement}
     * ��List��Ԃ����\�b�h�ł��B
     * 
     * @param extensionPointId
     *            �w�肵���g���|�C���g
     * @param id
     *            �T�u�v�f�̖���
     * @return
     */
    public static List<IConfigurationElement> getConfigurationElements(
            String extensionPointId, String id) {
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IExtensionPoint point = registry.getExtensionPoint(extensionPointId);
        IExtension[] extensions = point.getExtensions();

        List<IConfigurationElement> ansList = new ArrayList<IConfigurationElement>();
        // ����for�� extension�^�O �̌J��Ԃ�
        for (int i = 0; i < extensions.length; i++) {
            IConfigurationElement[] elements = extensions[i]
                    .getConfigurationElements();
            // ����for�� ���̉��̃^�O�̌J��Ԃ�
            for (int j = 0; j < elements.length; j++) {
                // null��������Aadd�����Ⴄ�B
                if (id == null || elements[j].getName().equals(id)) {
                    ansList.add(elements[j]);
                }
            }
        }
        return ansList;
    }

    public static List<IConfigurationElement> getConfigurationElements(
            String extensionPointId) {
        return getConfigurationElements(extensionPointId, null);
    }
}
