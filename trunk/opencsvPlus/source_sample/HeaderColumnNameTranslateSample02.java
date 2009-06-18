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
//�쐬��: 2009/06/05
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nu.mine.kino.csv.CSVSampleBean;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

/**
 * sample.csv��ǂݍ���ŁA�l��W���o�͂ɏo�͂��邾���̃T���v���ł��B
 * ���̃T���v���́ACSV�̈�s�ڂ��w�b�_�s�ƌ��Ȃ��A�w�b�_�̖��̂�JavaBeans�̃t�B�[���h��Map���g���Ċ֘A�Â��邱�Ƃɂ��A
 * CSV��JavaBeans�Ƀ}�b�s���O���܂��B
 * 
 * @author Masatomi KINO
 * @version $Revision$
 */
public class HeaderColumnNameTranslateSample02 {
    private static final String CSV_FILE = "sample.csv";

    public static void main(String[] args) throws IOException {
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
            System.out.println(bean);
        }
    }

    private static Map<String, String> createMapping() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("��", "last_name");
        map.put("��", "first_name");
        map.put("�N��", "age");
        return map;
    }
}
