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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IConfigurationElement;

/**
 * �g���|�C���g�̏���CSV�`���ŏ����o���N���X�ł��B
 * 
 * @author Masatomi KINO
 * @version $Revision$
 */
public class ExtensionPointWriter implements IExtensionPointWriter {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(ExtensionPointWriter.class);

    private static final String LINE_SEPARATOR = System
            .getProperty("line.separator");

    private StringBuffer csvData;

    private BufferedWriter out;

    /**
     * @param file
     * @throws FileNotFoundException
     */
    public ExtensionPointWriter(File file) throws FileNotFoundException {
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                file)));
        csvData = new StringBuffer();
    }

    /**
     * �w�肳�ꂽ�g���|�C���g�̏��������o�����\�b�h�ł��B
     * 
     * @param id
     *            �g���|�C���gID�łȂ��AID
     */
    public void write(String id) {// ����ID�B�g���|�C���gID����Ȃ�
        logger.debug("write(String) - start");
        // ����(id)�̐ݒ肩��A�t�H�[�}�b�^�[�N���X���擾���A
        IExtensionPointFormatter formatter = new ExtensionPointFormatterManager()
                .getExtensionFormatter(id);
        // StringBuffer�ɏ��������o���Ă����B
        // formatter����A�w�b�_������̔z����擾���A
        String[] headers = formatter.getHeaders();
        // ""�Ȃǂ̐��`�������o�R����
        String headerString = convertArray2CSV(headers);

        // CSV��append
        csvData.append(headerString);
        csvData.append(LINE_SEPARATOR);

        // �����ł�������ID->�g���|�C���gID�ɕϊ����K�v�B
        String extensionPointId = id2ExtensionPointId(id);

        // extensionPointId ���g�p���Ă���v���O�C���ꗗ���擾�B
        List<IConfigurationElement> configurationElements = Utils
                .getConfigurationElements(extensionPointId);

        for (IConfigurationElement element : configurationElements) {
            String[] informations = formatter.getInformations(element);
            String rowData = convertArray2CSV(informations);
            csvData.append(rowData);
            csvData.append(LINE_SEPARATOR);
        }
        try {
            // buffer���t�@�C���ɏ����o���[
            out.write(csvData.toString());
        } catch (IOException e) {
            logger.error("write(String)", e);
            Activator.logException(e, false);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                logger.error("write(String)", e);
                Activator.logException(e, false);
            }
        }
        logger.debug("write(String) - end");
    }

    private String id2ExtensionPointId(String id) {
        List<IConfigurationElement> list = Utils.getConfigurationElements(
                "nu.mine.kino.plugin.plugindocumentcreator.formatters",
                "formatter");
        for (IConfigurationElement element : list) {
            String idInXml = element.getAttribute("id");
            if (idInXml.equals(id)) {
                logger.debug(id + " �̐ݒ肪������܂����B");
                String extension_point_id = element
                        .getAttribute("extension-point-id");
                logger.debug("�g���|�C���gID�� " + extension_point_id + " �ł��ˁB");
                return extension_point_id;
            }
        }
        return null;
    }

    private String convertArray2CSV(String[] strArray) {
        StringBuffer retData = new StringBuffer();
        retData.append('"');
        for (int i = 0; i < strArray.length; i++) {
            String header = strArray[i];
            if (i > 0) {
                retData.append("\",\"");
            }
            retData.append(header);
        }
        retData.append("\"");
        return new String(retData);
    }

    // /**
    // * �����̊g���|�C���g���g���Ă���v���O�C����Ԃ��B�A���擾�����IExtension�C���^�t�F�[�X (�v���O�C��)�ɂ́A
    // * ���̊g���|�C���g�̏�񂵂������ĂȂ��B
    // *
    // * @param extensionPointName
    // * @return
    // */
    // private IExtension[] getPlugin(String extensionPointName) {
    // // �v���O�C���̃��W�X�g���擾
    // IExtensionRegistry registry = Platform.getExtensionRegistry();
    // // ���W�X�g������A�g���|�C���g���Ŋg���|�C���g���擾
    // IExtensionPoint point = registry.getExtensionPoint(extensionPointName);
    // // ���̊g���|�C���g���g���Ă���v���O�C���ꗗ���擾�B
    // if (point != null) {
    // IExtension[] extensions = point.getExtensions();
    // return extensions;
    // }
    // return new IExtension[0];
    // }

}
