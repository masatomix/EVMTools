package nu.mine.kino.projects.utils;

/******************************************************************************
 * Copyright (c) 2010 Masatomi KINO and others. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *      Masatomi KINO - initial API and implementation
 * $Id$
 ******************************************************************************/

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import junit.framework.Assert;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.bbreak.excella.core.util.PoiUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class POITest {

    public static Date getDate(Cell dateCell) {
        Date baseDate = null;
        if (dateCell != null) {// taskId��͐��l�������Ă��āA����𕶎���Ŏ�肽��
            if (dateCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                if (PoiUtil.isCellDateFormatted(dateCell)) {
                    baseDate = dateCell.getDateCellValue();
                }
            }
        }
        return baseDate;
    }

    // �^�X�NID���擾����B
    public static String getTaskId(Cell taskIdCell) {
        String taskId = null;
        if (taskIdCell != null) {// taskId��͐��l�������Ă��āA����𕶎���Ŏ�肽��
            if (taskIdCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                taskId = (String) PoiUtil
                        .getCellValue(taskIdCell, String.class);
            }
        }
        return taskId;
    }

    public static int getHeaderIndex(Sheet sheet) {
        int number = Integer.MIN_VALUE;
        Iterator<Row> e = sheet.rowIterator();
        int counter = 0;
        while (e.hasNext()) {
            Row row = e.next();
            Cell cell = row.getCell(0);
            if (cell.getCellType() != Cell.CELL_TYPE_STRING) {
            } else {
                if ("#".equals(cell.getStringCellValue())) {
                    number = counter;
                }
            }
            counter++;
        }
        return number;
    }

    public static int getDataFirstRowNum(Sheet sheet) {
        return getHeaderIndex(sheet) + 2;
    }

    public static int getDataLastRowNum(Sheet sheet) {
        return PoiUtil.getLastRowNum(sheet, 0, 0);
    }

    // //////////////////////
    @Test
    public void test7() {
        System.out.println("--- test7 ---");
        Sheet sheet = workbook.getSheetAt(0);
        System.out.println(getHeaderIndex(sheet));
        System.out.println(getDataFirstRowNum(sheet));
        System.out.println(getDataLastRowNum(sheet));
        System.out.println("--- test7 ---");

    }

    // �C�i�Y�}���̊�����擾����ہA���t���ǂ������`�F�b�N���Ă���B
    @Test
    public void test3() throws InvalidFormatException, IOException {
        Sheet sheet = workbook.getSheetAt(0);
        Name name = workbook.getName("�������");
        CellReference cellRef = new CellReference(name.getRefersToFormula());
        Row row = sheet.getRow(cellRef.getRow());
        Cell baseDateCell = row.getCell(cellRef.getCol());
        System.out.println("cell�����t��:"
                + PoiUtil.isCellDateFormatted(baseDateCell));
        Date baseDate = baseDateCell.getDateCellValue();
        System.out.println(baseDate);
    }

    // �Y�������W�ł̍ŉ��s���������B
    @Test
    public void test4() throws InvalidFormatException, IOException {
        Sheet sheet = workbook.getSheetAt(0);
        System.out.println("�ŏI�s:" + PoiUtil.getLastRowNum(sheet, 0, 0));
    }

    // ���l�֌W�̊m�F
    @Test
    public void test5() throws InvalidFormatException, IOException {
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> e = sheet.rowIterator();
        System.out.println("--- ���l�n�̃e�X�g ---");
        while (e.hasNext()) {
            Row row = e.next();
            Cell taskIdCell = row.getCell(1);
            String taskId = getTaskId(taskIdCell);

            System.out.printf("[%s],[%s],[%s],[%s],[%s]\n", taskId,
                    row.getCell(15), row.getCell(22), row.getCell(23),
                    row.getCell(24));

        }
        System.out.println("--- ���l�n�̃e�X�g ---");

    }

    // ���t�֌W�̊m�F
    @Test
    public void test6() throws InvalidFormatException, IOException {
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> e = sheet.rowIterator();
        System.out.println("--- ���t�n�̃e�X�g ---");
        while (e.hasNext()) {
            Row row = e.next();
            Cell taskIdCell = row.getCell(1);
            String taskId = getTaskId(taskIdCell);

            Cell scheduledSDateCell = row.getCell(16);
            Date sDate = getDate(scheduledSDateCell);
            Cell scheduledEDateCell = row.getCell(17);
            Date eDate = getDate(scheduledEDateCell);

            String pattern = "yyyy/MM/dd";
            System.out.printf("[%s],[%s],[%s]\n", taskId,
                    Utils.date2Str(sDate, pattern),
                    Utils.date2Str(eDate, pattern));

        }
        System.out.println("--- ���t�n�̃e�X�g ---");

    }

    // @Test
    public void test2() throws InvalidFormatException, IOException {
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> e = sheet.rowIterator();
        while (e.hasNext()) {
            Row row = e.next();
            Cell cell = row.getCell(0);

            if (cell.getCellType() != Cell.CELL_TYPE_STRING) {
            } else {
                System.out.println(cell.getStringCellValue());
            }
        }

    }

    // �Ƃ肠�����������Ă݂Ă���B
    @Test
    public void test1() throws InvalidFormatException, IOException {
        Sheet sheet = workbook.getSheetAt(0);
        // int[] rowBreaks = sheet.getRowBreaks();

        Row row = sheet.getRow(25);
        Cell cell = row.getCell(24);
        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_NUMERIC:
            System.out.println(cell.getNumericCellValue());
            break;
        case Cell.CELL_TYPE_STRING:
            System.out.println(cell.getStringCellValue());
            break;
        default:
            System.out.println("cellType=" + cell.getCellType());
            break;
        }
        System.out.println("value: " + cell);
        System.out.println(sheet.getRow(46).getCell(4));

        final int rowMax = sheet.getLastRowNum();
        System.out.println(rowMax);

    }

    public static void main(String[] args) {
        Workbook workbook = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(new java.io.File(
                    "project_management_tools.xls"));
            workbook = WorkbookFactory.create(in);
            Sheet sheet = workbook.getSheetAt(0);
            // int[] rowBreaks = sheet.getRowBreaks();

            Row row = sheet.getRow(25);
            Cell cell = row.getCell(24);
            switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                System.out.println(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING:
                System.out.println(cell.getStringCellValue());
                break;
            default:
                System.out.println("cellType=" + cell.getCellType());
                break;
            }
            System.out.println("value: " + cell);
            System.out.println(sheet.getRow(46).getCell(4));

            final int rowMin = sheet.getFirstRowNum();
            System.out.println(rowMin);
            final int rowMax = sheet.getLastRowNum();
            System.out.println(rowMax);

        } catch (InvalidFormatException e) {
            // TODO �����������ꂽ catch �u���b�N
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO �����������ꂽ catch �u���b�N
            e.printStackTrace();
        } catch (IOException e) {
            // TODO �����������ꂽ catch �u���b�N
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO �����������ꂽ catch �u���b�N
                    e.printStackTrace();
                }
        }

    }

    FileInputStream in = null;

    Workbook workbook = null;

    @Before
    public void before() {
        try {
            in = new FileInputStream(new java.io.File(
                    "project_management_tools.xls"));
            workbook = WorkbookFactory.create(in);
        } catch (FileNotFoundException e) {
            Assert.fail(e.getMessage());
        } catch (InvalidFormatException e) {
            Assert.fail(e.getMessage());
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    @After
    public void after() {
        if (in != null)
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail(e.getMessage());
            }
    }
}
