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
//�쐬��: 2012/06/08

package nu.mine.kino.plugin.webrecorder.preferences;

import static nu.mine.kino.plugin.webrecorder.ProxyConstant.POST_BODY_FLAG;
import static nu.mine.kino.plugin.webrecorder.ProxyConstant.TRIM_FLAG;
import static nu.mine.kino.plugin.webrecorder.ProxyConstant.TRIM_LENGTH;
import static nu.mine.kino.plugin.webrecorder.ProxyConstant.TRIM_START_INDEX;

import java.io.File;

import nu.mine.kino.plugin.webrecorder.ProxyConstant;
import nu.mine.kino.plugin.webrecorder.WebRecorderPlugin;

import org.apache.log4j.Logger;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class PostPreferencePage extends FieldEditorPreferencePage implements
        IWorkbenchPreferencePage {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(ProxyPreferencePage.class);

    private BooleanFieldEditor trimFlagField;

    private IntegerFieldEditor trimStartIndexField;

    private IntegerFieldEditor trimLengthField;

    private BooleanFieldEditor postBodyFlagField;

    public PostPreferencePage() {
        super(GRID);
        setPreferenceStore(WebRecorderPlugin.getDefault().getPreferenceStore());
        setDescription("Web Recorder��POST���̐ݒ肪�ύX�ł��܂��B");
    }

    /**
     * Creates the field editors. Field editors are abstractions of the common
     * GUI blocks needed to manipulate various types of preferences. Each field
     * editor knows how to save and restore itself.
     */
    public void createFieldEditors() {
        {
            postBodyFlagField = new BooleanFieldEditor(POST_BODY_FLAG,
                    "POST�̓��N�G�X�gBody�ŕۑ����ύX����", BooleanFieldEditor.DEFAULT,
                    getFieldEditorParent());
            addField(postBodyFlagField);
        }
        {
            trimFlagField = new BooleanFieldEditor(ProxyConstant.TRIM_FLAG,
                    "�@���N�G�X�gBody���g�������Ĕ�r����", getFieldEditorParent());
            addField(trimFlagField);
        }
        {
            trimStartIndexField = new IntegerFieldEditor(TRIM_START_INDEX,
                    "�@�g�����J�n�ʒu", getFieldEditorParent());
            addField(trimStartIndexField);
        }
        {
            trimLengthField = new IntegerFieldEditor(TRIM_LENGTH,
                    "�@�J�n���牽����\n�@(0�͖����܂�)", getFieldEditorParent());
            addField(trimLengthField);
        }

        initState();
    }

    private void initState() {
        boolean postBodyFlag = WebRecorderPlugin.getDefault()
                .getPreferenceStore().getBoolean(POST_BODY_FLAG);
        boolean trimFlag = WebRecorderPlugin.getDefault().getPreferenceStore()
                .getBoolean(TRIM_FLAG);
        updateAllState(postBodyFlag);
        updateTrimFieldState(postBodyFlag && trimFlag);
    }

    @Override
    protected void performDefaults() {
        super.performDefaults();
        initState();
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        super.propertyChange(event);
        boolean postBodyFlag = postBodyFlagField.getBooleanValue();
        boolean trimFlag = trimFlagField.getBooleanValue();
        updateAllState(postBodyFlag);
        updateTrimFieldState(postBodyFlag && trimFlag);
    }

    private void updateAllState(boolean enabled) {
        // POST��Body�ŕۑ����ς��Ȃ��΂����AGUI����͕s�ɁB
        trimFlagField.setEnabled(enabled, getFieldEditorParent());
        updateTrimFieldState(enabled);
    }

    private void updateTrimFieldState(boolean enabled) {
        trimStartIndexField.setEnabled(enabled, getFieldEditorParent());
        trimLengthField.setEnabled(enabled, getFieldEditorParent());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init(IWorkbench workbench) {
        String cacheBasepath = WebRecorderPlugin.getDefault()
                .getCacheBasepath();
        logger.debug("�L���b�V���f�B���N�g��: " + cacheBasepath);
        new File(cacheBasepath).mkdirs();
    }

}
