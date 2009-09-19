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
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

/**
 * 
 * sample.csv��ǂݍ���ŁA�l��W���o�͂ɏo�͂��邾���̃T���v���ł��B ���̃T���v���́ACSV�̕��я��ɑ΂���
 * �t�B�[���h�̏��Ԃ��w�肷�鎖�ŁACSV�ƃt�B�[���h�����}�b�s���O���Ă��܂��B
 * 
 * @author Masatomi KINO
 * @version $Revision$
 */
public class ColumnPositionSample02 {
    private static final String CSV_FILE = "sample.csv";

    public static void main(String[] args) throws IOException {
        ColumnPositionMappingStrategy strat = new ColumnPositionMappingStrategy();
        strat.setType(CSVSampleBean.class);
        // CSV�̏��ԂŁA�ǂ̃t�B�[���h�Ƀ}�b�s���O����΂��������w�肷��B
        String[] columns = new String[] { "last_name", "first_name", "age" };
        strat.setColumnMapping(columns);

        CsvToBean csv = new CsvToBean();
        List<CSVSampleBean> list = csv.parse(strat, new FileReader(CSV_FILE));
        for (CSVSampleBean bean : list) {
            System.out.println(bean);
        }
    }
}
