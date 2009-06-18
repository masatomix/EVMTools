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
import java.util.List;

import nu.mine.kino.csv.CSVSampleBean;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameAutoTranslateMappingStrategy;
import au.com.bytecode.opencsv.bean.HeaderColumnNameMappingStrategy;

/**
 * sample.csv��ǂݍ���ŁA�l��W���o�͂ɏo�͂��邾���̃T���v���ł��B
 * ���̃T���v���́ACSV�̈�s�ڂ��w�b�_�s�ƌ��Ȃ��A�w�b�_�̖��̂�JavaBeans�̃t�B�[���h����ݒ�t�@�C���Ŋ֘A�Â��邱�Ƃɂ��A
 * CSV��JavaBeans�Ƀ}�b�s���O���܂��B
 * 
 * @author Masatomi KINO
 * @version $Revision$
 */
public class HeaderColumnNameAutoTranslateSample03 {
    private static final String CSV_FILE = "sample.csv";

    public static void main(String[] args) throws IOException {
        HeaderColumnNameMappingStrategy strat = new HeaderColumnNameAutoTranslateMappingStrategy();
        // FileInputStream in = new FileInputStream(new File("hogehoge.txt"));
        // strat.setInputStream(in);
        strat.setType(CSVSampleBean.class);
        CsvToBean csv = new CsvToBean();
        List<CSVSampleBean> list = csv.parse(strat, new FileReader(CSV_FILE));
        for (CSVSampleBean bean : list) {
            System.out.println(bean);
        }
    }
}
