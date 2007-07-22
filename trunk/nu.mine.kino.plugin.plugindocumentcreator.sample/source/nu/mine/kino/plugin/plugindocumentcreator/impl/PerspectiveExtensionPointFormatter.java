/*******************************************************************************
 * Copyright (c) 2006 Masatomi KINO.
 * All rights reserved. 
 * $Id$
 *******************************************************************************/
//�쐬��: 2006/10/22
package nu.mine.kino.plugin.plugindocumentcreator.impl;

import nu.mine.kino.plugin.plugindocumentcreator.IExtensionPointFormatter;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IConfigurationElement;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class PerspectiveExtensionPointFormatter implements
        IExtensionPointFormatter {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(PerspectiveExtensionPointFormatter.class);

    public String[] getInformations(IConfigurationElement element) {
        if (element.getName().equals("perspective")) {
            return new String[] { element.getNamespaceIdentifier(), "�p�[�X�y�N�e�B�u",
                    element.getAttribute("id"), element.getAttribute("name"),
                    element.getAttribute("class"), element.getAttribute("icon") };
        }
        return new String[0];

    }

    /*
     * (non-Javadoc)
     * 
     * @see nu.mine.kino.plugin.plugindocumentcreator.IExtensionPointFormatter#getHeaders()
     */
    public String[] getHeaders() {
        return new String[] { "�v���O�C��ID", "���", "ID", "���O", "�����N���X", "�A�C�R����" };
    }
}
