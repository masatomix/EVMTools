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
//�쐬��: 2014/10/15

package nu.mine.kino.projects.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.java.amateras.xlsbeans.XLSBeans;
import net.java.amateras.xlsbeans.XLSBeansException;
import nu.mine.kino.entity.ExcelScheduleBean;
import nu.mine.kino.entity.ExcelScheduleBean2Task;
import nu.mine.kino.entity.Task;
import nu.mine.kino.entity.TaskInformation;
import nu.mine.kino.projects.ExcelScheduleBeanSheet;
import nu.mine.kino.projects.ProjectException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class BaseDataUtilsTest {

    @Test
    public void test() throws ParseException {
        java.io.InputStream in = null;
        try {
            List<TaskInformation> taskInfoList = new ArrayList<TaskInformation>();

            in = new java.io.FileInputStream("project_management_tools.xls");

            ExcelScheduleBeanSheet sheet = new XLSBeans().load(in,
                    ExcelScheduleBeanSheet.class);
            java.util.List<ExcelScheduleBean> instanceList = sheet
                    .getExcelScheduleBean();

            for (ExcelScheduleBean instance : instanceList) {
                if (!StringUtils.isEmpty(instance.getId())) {
                    instance.setBaseDate(sheet.getBaseDate());
                    Task task = ExcelScheduleBean2Task.convert(instance);

                    print(task);

                }
            }
            System.out.println(instanceList.size());
            System.out.println(sheet.getBaseDate());

        } catch (FileNotFoundException e) {
            // TODO �����������ꂽ catch �u���b�N
            e.printStackTrace();
        } catch (XLSBeansException e) {
            // TODO �����������ꂽ catch �u���b�N
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO �����������ꂽ catch �u���b�N
                    e.printStackTrace();
                }
            }
        }

    }

    private static void print(Task task) throws ParseException {

        Date[] projectRange = BaseDataUtils.getProjectRange(task
                .getPlotDataMap());

        System.out.print("Date: ");
        Date targetDate = projectRange[0];
        while (!targetDate.equals(projectRange[1])) {
            System.out.printf("[%s] ", Utils.date2Str(targetDate, "MM/dd"));
            targetDate = DateUtils.addDays(targetDate, 1);
        }
        System.out.println();

        System.out.print("���X��PV: ");
        targetDate = projectRange[0];
        while (!targetDate.equals(projectRange[1])) {
            double pv = ProjectUtils.calculatePV(task, targetDate);
            System.out.printf("[%1$,.1f] ", pv);
            targetDate = DateUtils.addDays(targetDate, 1);
        }
        System.out.println();

        // String[] dates = { "20140901", "20140902", "20140903", "20140904",
        // "20140905", "20140906", "20140907", "20140908", "20140909",
        // "20140910", "20140911", "20140912", "20140913", "20140914",
        // "20140915", "20140916", "20140917", "20140918", "20140919",
        // "20140920", "20140921", "20140922", "20140923", "20140924",
        // "20140925", "20140926", "20140927", "20140928", "20140929",
        // "20140930" };
        // System.out.print("���X��PV: ");
        // for (String date : dates) {
        // Date baseDate = DateUtils.parseDate(date,
        // new String[] { "yyyyMMdd" });
        // double pv = ProjectUtils.calculatePV(task, baseDate);
        // System.out.printf("[%1$,.1f] ", pv);
        // }
        // System.out.println();
        // System.out.print("�ݐς�PV: ");
        // for (String date : dates) {
        // Date baseDate = DateUtils.parseDate(date,
        // new String[] { "yyyyMMdd" });
        // double pvs = ProjectUtils.calculatePVs(task, baseDate);
        // System.out.printf("[%1$,.1f] ", pvs);
        // }
        // System.out.println();
    }
}
