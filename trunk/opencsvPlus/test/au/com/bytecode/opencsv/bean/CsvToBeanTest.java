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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import junit.framework.TestCase;
import nu.mine.kino.csv.CSVSampleBean;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class CsvToBeanTest extends TestCase {

    /**
     * ColumnPositionMappingStrategy���g�����T���v���B
     * sample.csv��ǂݍ���ŁA�l��W���o�͂ɏo�͂��邾���̃T���v���ł��B CSV�̗�̏��ԂɁA
     * JavaBeans�̂ǂ̃t�B�[���h�Ƀ}�b�s���O����΂��������w�肵�܂��B
     */
    public void testParseMappingStrategy01() {
        final String CSV_FILE = "sample.csv";
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
                String lastName = bean.getLast_name();
                String firstName = bean.getFirst_name();
                String age = bean.getAge();
                System.out.println(bean);
                assertNotNull(lastName);
                assertNotNull(firstName);
                assertNotNull(age);
            }
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        }
    }
}
