/*******************************************************************************
 * Copyright (c) 2006 Masatomi KINO.
 * All rights reserved. 
 * $Id$
 *******************************************************************************/
//�쐬��: 2006/10/22
package nu.mine.kino.plugin.plugindocumentcreator.impl;

import nu.mine.kino.plugin.plugindocumentcreator.IExtensionPointFormatter;

import org.eclipse.core.runtime.IConfigurationElement;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class ViewExtensionPointFormatter implements IExtensionPointFormatter {

    public String[] getInformations(IConfigurationElement element) {
        String pluginId = element.getNamespaceIdentifier(); // �v���O�C����ID
        String id = element.getAttribute("id");// �r���[��ID��J�e�S��ID�Ȃ�
        if (element.getName().equals("view")) {
            String category = element.getAttribute("category");
            category = category == null ? "" : category;
            return new String[] { pluginId, "�r���[", id,
                    element.getAttribute("name"),
                    element.getAttribute("class"), category };
        } else if (element.getName().equals("category")) {
            return new String[] { pluginId, "�J�e�S��", id,
                    element.getAttribute("name") };
        } else if (element.getName().equals("stickyView")) {
            return new String[] { pluginId, "Sticky�ȃr���[", id };
        }
        return new String[0];

    }

    /*
     * (non-Javadoc)
     * 
     * @see nu.mine.kino.plugin.plugindocumentcreator.IExtensionPointFormatter#getHeaders()
     */
    public String[] getHeaders() {
        return new String[] { "�v���O�C��ID", "���", "ID", "�r���[��", "�����N���X",
                "������J�e�S��ID" };
    }
}
