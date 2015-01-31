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
//�쐬��: 2015/01/31

package nu.mine.kino.projects.utils;

import java.util.Date;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.bbreak.excella.core.util.PoiUtil;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class PoiUtils {
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

}
