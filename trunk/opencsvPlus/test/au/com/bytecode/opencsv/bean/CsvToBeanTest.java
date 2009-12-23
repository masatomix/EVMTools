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
//�쐬��: 2009/12/23

package au.com.bytecode.opencsv.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;

import junit.framework.TestCase;
import nu.mine.kino.csv.CSVSampleBean;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class CsvToBeanTest extends TestCase {
    private static final String CSV_FILE = "sample.csv";

    private void assertNotNull(CSVSampleBean bean) {
        String lastName = bean.getLast_name();
        String firstName = bean.getFirst_name();
        String age = bean.getAge();
        assertNotNull(lastName);
        assertNotNull(firstName);
        assertNotNull(age);
        System.out.println(bean);
    }

    /**
     * ColumnPositionMappingStrategy���g�����T���v���B
     * sample.csv��ǂݍ���ŁA�l��W���o�͂ɏo�͂��邾���̃T���v���ł��B CSV�̗�̏��ԂɁA
     * JavaBeans�̂ǂ̃t�B�[���h�Ƀ}�b�s���O����΂��������w�肵�܂��B
     */
    public void testParseMappingStrategy01() {
        // final String CSV_FILE = "sample.csv";
        ColumnPositionMappingStrategy strat = new ColumnPositionMappingStrategy();
        strat.setType(CSVSampleBean.class);
        // CSV�̏��ԂŁA�ǂ̃t�B�[���h�Ƀ}�b�s���O����΂��������w�肷��B
        String[] columns = new String[] { "last_name", "first_name", "age" };
        strat.setColumnMapping(columns);

        CsvToBean csv = new CsvToBean();
        try {
            List<CSVSampleBean> list = csv.parse(strat,
                    new FileReader(CSV_FILE));
            for (CSVSampleBean bean : list) {
                assertNotNull(bean);
            }
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        }
    }

    /**
     * sample.csv��ǂݍ���ŁA�l��W���o�͂ɏo�͂��邾���̃T���v���ł��B
     * ���̃T���v���́ACSV�̈�s�ڂ��w�b�_�s�ƌ��Ȃ��A�w�b�_�̖��̂�JavaBeans�̃t�B�[���h��Map���g���Ċ֘A�Â��邱�Ƃɂ��A
     * CSV��JavaBeans�Ƀ}�b�s���O���܂��B
     * 
     * @throws Exception
     */
    public void testParseMappingStrategy02() throws Exception {
        HeaderColumnNameTranslateMappingStrategy strat = new HeaderColumnNameTranslateMappingStrategy();
        strat.setType(CSVSampleBean.class);
        // CSV�̃w�b�_�����A�ǂ̃t�B�[���h�Ƀ}�b�s���O����΂��������w�肷��B
        // map.put("[�w�b�_��]", "[�t�B�[���h��]");
        // ���̏ꍇ�́A�w�b�_�s�����������B
        Map<String, String> map = createMapping();
        strat.setColumnMapping(map);

        CsvToBean csv = new CsvToBean();
        List<CSVSampleBean> list = csv.parse(strat, new FileReader(CSV_FILE));
        for (CSVSampleBean bean : list) {
            assertNotNull(bean);
        }
    }

    private Map<String, String> createMapping() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("��", "last_name");
        map.put("��", "first_name");
        map.put("�N��", "age");
        return map;
    }

    /**
     * sample.csv��ǂݍ���ŁA�l��W���o�͂ɏo�͂��邾���̃T���v���ł��B
     * ���̃T���v���́ACSV�̈�s�ڂ��w�b�_�s�ƌ��Ȃ��A�w�b�_�̖��̂�JavaBeans�̃t�B�[���h����ݒ�t�@�C���Ŋ֘A�Â��邱�Ƃɂ��A
     * CSV��JavaBeans�Ƀ}�b�s���O���܂��B ����Strategy�N���X�͏�����OpenCsv�ɂ͂Ȃ��������߁A���삵���N���X�ł��B
     * 
     * @author Masatomi KINO
     * @version $Revision$
     */
    public void testParseMappingStrategy03() throws Exception {
        System.out.println("parseMappingStrategy03: start.");
        // HeaderColumnNameAutoTranslateMappingStrategy strat = new
        // HeaderColumnNameAutoTranslateMappingStrategy();
        // FileInputStream in = new FileInputStream(new File("hogehoge.txt"));
        // strat.setInputStream(in);
        HeaderColumnNameMappingStrategy strat = new HeaderColumnNameAutoTranslateMappingStrategy();
        strat.setType(CSVSampleBean.class);
        CsvToBean csv = new CsvToBean();
        List<CSVSampleBean> list = csv.parse(strat, new FileReader(CSV_FILE));
        for (CSVSampleBean bean : list) {
            assertNotNull(bean);
        }
        System.out.println("parseMappingStrategy03: end.");
    }

}
