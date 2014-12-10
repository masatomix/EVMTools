/******************************************************************************
 * Copyright (c) 2008 Masatomi KINO and others. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *      Masatomi KINO - initial API and implementation
 * $Id$
 ******************************************************************************/
//�쐬��: 2009/12/21

package au.com.bytecode.opencsv.bean;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.List;

import junit.framework.TestCase;
import nu.mine.kino.csv.CSVSampleBean;
import au.com.bytecode.opencsv.CSVWriter;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class BeanToCsvTest extends TestCase {

    public void testWriteAll01() throws Exception {
        // HeaderColumnNameMappingStrategy ���g���ƁA�ݒ�t�@�C������l��ݒ肷�邱�Ƃ��ł���B
        HeaderColumnNameMappingStrategy strat = new HeaderColumnNameMappingStrategy();
        // FileInputStream in = new FileInputStream(new File("hogehoge.txt"));
        // strat.setInputStream(in);
        strat.setType(CSVSampleBean.class);
        BeanToCsv csv = new BeanToCsv();
        List<CSVSampleBean> list = getList();
        // �J���}��؂�ŁA""�ň͂܂Ȃ��A�΂����B
        csv.writeAll(strat, new CSVWriter(new FileWriter("SampleOut01.csv"),
                ',', CSVWriter.NO_ESCAPE_CHARACTER), list);

    }

    public void testWriteAll01_1() throws Exception {
        // HeaderColumnNameMappingStrategy ���g���ƁA�ݒ�t�@�C������l��ݒ肷�邱�Ƃ��ł���B
        HeaderColumnNameMappingStrategy strat = new HeaderColumnNameMappingStrategy();
        // FileInputStream in = new FileInputStream(new File("hogehoge.txt"));
        // strat.setInputStream(in);
        strat.setType(CSVSampleBean.class);

        BeanToCsv csv = new BeanToCsv();
        List<CSVSampleBean> list = getList();
        CSVWriter out = null;
        try {
            // �J���}��؂�ŁA""�ň͂܂Ȃ��A�΂����B
            StringWriter writer = new StringWriter();
            out = new CSVWriter(writer, ',', CSVWriter.NO_ESCAPE_CHARACTER);
            // out = new CSVWriter(new FileWriter("SampleOut01_1.csv"), ',',
            // CSVWriter.NO_ESCAPE_CHARACTER);
            for (CSVSampleBean bean : list) {
                csv.write(strat, out, bean);
            }
            System.out.println(writer);
        } finally {
            if (out != null)
                out.close();
        }

    }

    public void testWriteAll02() throws Exception {
        // ColumnPositionMappingStrategy���g���ƁA�w�肵���t�B�[���h����CSV�o�͂ł���B
        ColumnPositionMappingStrategy strat = new ColumnPositionMappingStrategy();
        strat.setType(CSVSampleBean.class);
        String[] columns = new String[] { "age", "last_name", "first_name", };
        strat.setColumnMapping(columns);
        BeanToCsv csv = new BeanToCsv();
        List<CSVSampleBean> list = getList();
        // �J���}��؂�ŁA""�ň͂܂Ȃ��A�΂����B
        csv.writeAll(strat, new CSVWriter(new FileWriter("SampleOut02.csv"),
                ',', '\u0000'), list);
    }

    private List<CSVSampleBean> getList() {
        try {
            HeaderColumnNameMappingStrategy strat = new HeaderColumnNameAutoTranslateMappingStrategy();
            strat.setType(CSVSampleBean.class);
            CsvToBean csv = new CsvToBean();
            List<CSVSampleBean> list = csv.parse(strat, new FileReader(
                    "sample.csv"));
            return list;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        return null;
    }
}
