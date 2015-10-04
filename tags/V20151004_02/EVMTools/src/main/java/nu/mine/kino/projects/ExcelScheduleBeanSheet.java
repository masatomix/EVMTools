package nu.mine.kino.projects;

/******************************************************************************
 * Copyright (c) 2008-2009 Masatomi KINO and others. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *      Masatomi KINO - initial API and implementation
 * $Id$
 ******************************************************************************/

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import nu.mine.kino.entity.ExcelScheduleBean;
import nu.mine.kino.projects.utils.PoiUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.bbreak.excella.core.util.PoiUtil;

/**
 * Excel�V�[�g����v���W�F�N�g�����擾����N���X�B
 * 
 * @author Masatomi KINO and JavaBeans Creator Plug-in
 * @version $Revision$
 */
//@Sheet(name = "�K���g�`���[�g�i���͗�j")//$NON-NLS-1$
// @Sheet(number = 0)
public class ExcelScheduleBeanSheet {
    private java.util.List<ExcelScheduleBean> instanceList;

    private Date baseDate;

    private Date projectStartDate;

    private Date projectEndDate;

    private int dateFirstCellnum;

    private int dateLastCellNum;

    //    @HorizontalRecords(tableLabel = "#Gantt", recordClass = ExcelScheduleBean.class, terminal = RecordTerminal.Border)//$NON-NLS-1$
    // public void setExcelScheduleBean(
    // java.util.List<ExcelScheduleBean> instanceList) {
    // this.instanceList = instanceList;
    // instanceList.remove(0);// ��s�ڂ̓^�C�g���̎ז��Ȃ̂ŁA���O�B
    // // if (!instanceList.isEmpty()) {
    // // Date[] range = BaseDataUtils.getProjectRange(instanceList);
    // // projectStartDate = range[0];
    // // projectEndDate = range[1];
    // // }
    // }

    public java.util.List<ExcelScheduleBean> getExcelScheduleBean() {
        return instanceList;
    }

    // // �ꏊ���ߑł��ŁA������擾����B
    // @Cell(row = 0, column = 25)
    // public void setBaseDate(String date) throws ParseException {
    // this.baseDate = DateUtils.parseDate(date, new String[] { "yy/MM/dd" });
    // }

    public Date getBaseDate() {
        return baseDate;
    }

    public Date getProjectStartDate() {
        return projectStartDate;
    }

    public Date getProjectEndDate() {
        return projectEndDate;
    }

    public void setBaseDate(Date baseDate) {
        this.baseDate = baseDate;
    }

    public void init(Workbook workbook) throws ProjectException {
        instanceList = new ArrayList<ExcelScheduleBean>();
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> e = sheet.rowIterator();
        int index = 0;
        int dataIndex = PoiUtils.getDataFirstRowNum(sheet);
        int headerIndex = PoiUtils.getHeaderIndex(sheet);
        Row headerRow = null;

        while (e.hasNext()) {
            // �w�b�_���I���܂Ŕ�΂��B
            if (index < headerIndex) {
                e.next();
                index++;
                continue;
            }
            // header���ɂ��ẮA���t�Z���͈̔͂ŃL�[�l���擾�������B
            if (index == headerIndex) {
                headerRow = e.next();
                dateFirstCellnum = findDateFirstCellnum(headerRow);
                dateLastCellNum = findDateLastCellnum(headerRow);
                projectStartDate = (Date) PoiUtil.getCellValue(headerRow
                        .getCell(dateFirstCellnum));
                projectEndDate = (Date) PoiUtil.getCellValue(headerRow
                        .getCell(dateLastCellNum));
                if (projectStartDate.getTime() > projectEndDate.getTime()) {
                    String msg = String.format(
                            "�v���W�F�N�g�J�n��>�v���W�F�N�g�I�����ƂȂ��Ă��܂��B[%s][%s]",
                            projectStartDate, projectEndDate);
                    throw new ProjectException(msg);
                }
                index++;
                continue;
            }

            // �w�b�_���I���܂Ŕ�΂��B
            if (index < dataIndex) {
                e.next();
                index++;
                continue;
            }
            // �f�[�^���̏���
            Row dataRow = e.next();
            Cell taskIdCell = dataRow.getCell(1);
            String taskId = PoiUtils.getTaskId(taskIdCell);
            if (!StringUtils.isEmpty(taskId)) {
                ExcelScheduleBean bean = new ExcelScheduleBean();
                bean.setTaskId(taskId);
                bean.setPlotDataMap(createPlotMap(headerRow, dataRow));
                instanceList.add(bean);
            }

        }
    }

    /**
     * POI���Ԃ��A�f�[�^�������Ă�ŏI����g���Ă��A���܂ɈȊO�ɑ傫�Ȓl���Ԃ��Ă��Ă��܂����߁A
     * 
     * @param headerRow
     * @return
     */
    private int findDateLastCellnum(Row headerRow) {
        int initNumber = headerRow.getLastCellNum();
        int ans = initNumber;
        for (int tmpNum = initNumber; tmpNum >= 0; tmpNum--) {
            Object cellValue = PoiUtil.getCellValue(headerRow.getCell(tmpNum));
            if (cellValue != null) {
                ans = tmpNum;
                break;
            }
        }
        return ans;
    }

    private Map<String, String> createPlotMap(Row headerRow, Row dataRow) {
        Map<String, String> returnMap = new HashMap<String, String>();
        for (int columNum = dateFirstCellnum; columNum <= dateLastCellNum; columNum++) {
            Date keyDate = (Date) PoiUtil.getCellValue(headerRow
                    .getCell(columNum));
            String key = date2excelSerialValue(keyDate);
            String cellValue = null2Empty((String) PoiUtil.getCellValue(dataRow
                    .getCell(columNum)));
            // System.out.printf("[%s],[%s]\n", key, cellValue);
            returnMap.put(key, cellValue);
        }

        return returnMap;
    }

    public static int findDateFirstCellnum(Row row) {
        return 26; // ���̂Ƃ���L���E�`�B
    }

    private static String date2excelSerialValue(Date date) {
        long time = date.getTime();
        // System.out.println("longValue: " + time);
        BigDecimal dec = new BigDecimal(time)
                .divide(new BigDecimal("86400000"));
        BigDecimal ans = dec.add(new BigDecimal("25569")).add(
                new BigDecimal("0.375"));
        // System.out.println("out: " + ans.toString());
        return ans.toString();
    }

    private static String null2Empty(String target) {
        return target == null ? "" : target;
    }

}
