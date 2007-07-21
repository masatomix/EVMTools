/*******************************************************************************
 * Copyright (c) 2006 Masatomi KINO.
 * All rights reserved. 
 * $Id: ExtensionPointWriter.java,v 1.6 2006/11/04 12:52:05 cvsuser Exp $
 *******************************************************************************/
//�쐬��: 2006/10/22
package nu.mine.kino.plugin.plugindocumentcreator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * �g���|�C���g�̏���CSV�`���ŏ����o���N���X�ł��B
 * 
 * @author Masatomi KINO
 * @version $Revision: 1.6 $
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
     * @param extensionPointName
     *            �g���|�C���gID
     */
    public void write(String extensionPointName) {
        logger.debug("write(String) - start");
        // ����(extensionPointName)�̊g���|�C���g�p�̃t�H�[�}�b�^�[�N���X���擾���A
        IExtensionPointFormatter formatter = new ExtensionPointFormatterManager()
                .getExtensionFormatter(extensionPointName);
        // StringBuffer�ɏ��������o���Ă����B
        // formatter����A�w�b�_������̔z����擾���A
        String[] headers = formatter.getHeaders();
        // ""�Ȃǂ̐��`�������o�R����
        String headerString = convertArray2CSV(headers);

        // CSV��append
        csvData.append(headerString);
        csvData.append(LINE_SEPARATOR);

        // extensionPointName ���g�p���Ă���v���O�C���ꗗ���擾�B
        IExtension[] plugins = getPlugin(extensionPointName);
        for (int i = 0; i < plugins.length; i++) {
            // �v���O�C����
            IConfigurationElement[] elements = plugins[i]
                    .getConfigurationElements();
            for (int j = 0; j < elements.length; j++) {
                IConfigurationElement element = elements[j];
                String[] informations = formatter.getInformations(element);
                String rowData = convertArray2CSV(informations);
                csvData.append(rowData);
                csvData.append(LINE_SEPARATOR);
            }
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

    /**
     * �����̊g���|�C���g���g���Ă���v���O�C����Ԃ��B�A���擾�����IExtension�C���^�t�F�[�X (�v���O�C��)�ɂ́A
     * ���̊g���|�C���g�̏�񂵂������ĂȂ��B
     * 
     * @param extensionPointName
     * @return
     */
    private IExtension[] getPlugin(String extensionPointName) {
        // �v���O�C���̃��W�X�g���擾
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        // ���W�X�g������A�g���|�C���g���Ŋg���|�C���g���擾
        IExtensionPoint point = registry.getExtensionPoint(extensionPointName);
        // ���̊g���|�C���g���g���Ă���v���O�C���ꗗ���擾�B
        if (point != null) {
            IExtension[] extensions = point.getExtensions();
            return extensions;
        }
        return new IExtension[0];
    }

}
