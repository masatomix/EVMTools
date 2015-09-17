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
//作成日: 2014/11/04

package nu.mine.kino.projects;

import static nu.mine.kino.projects.utils.PoiUtils.getTaskId;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.java.amateras.xlsbeans.XLSBeans;
import net.java.amateras.xlsbeans.XLSBeansException;
import nu.mine.kino.entity.ACTotalBean;
import nu.mine.kino.entity.EVTotalBean;
import nu.mine.kino.entity.ExcelPOIScheduleBean;
import nu.mine.kino.entity.ExcelPOIScheduleBean2ACTotalBean;
import nu.mine.kino.entity.ExcelPOIScheduleBean2EVTotalBean;
import nu.mine.kino.entity.ExcelPOIScheduleBean2PVTotalBean;
import nu.mine.kino.entity.ExcelPOIScheduleBean2Task;
import nu.mine.kino.entity.ExcelPOIScheduleBean2TaskInformation;
import nu.mine.kino.entity.ExcelScheduleBean;
import nu.mine.kino.entity.ExcelScheduleBean2Task;
import nu.mine.kino.entity.ExcelScheduleBeanSheet2Project;
import nu.mine.kino.entity.Holiday;
import nu.mine.kino.entity.PVTotalBean;
import nu.mine.kino.entity.Project;
import nu.mine.kino.entity.Row2ExcelPOIScheduleBean;
import nu.mine.kino.entity.Task;
import nu.mine.kino.entity.TaskInformation;
import nu.mine.kino.projects.utils.PoiUtils;
import nu.mine.kino.projects.utils.ProjectUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.bbreak.excella.core.util.PoiUtil;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class ExcelProjectCreator extends InputStreamProjectCreator {

    // public ExcelProjectCreator(InputStream in) {
    // super(in);
    // }

    private final File file;

    public ExcelProjectCreator(File file) throws ProjectException {
        super(file);
        this.file = file;
    }

    /**
     * Excel フォーマットのFile(InputStream)からProjectを作成する
     * 
     * @see nu.mine.kino.projects.ProjectCreator#createProject(java.io.InputStream)
     */
    @Override
    public Project createProjectFromStream() throws ProjectException {

        Map<String, ExcelPOIScheduleBean> poiMap = new HashMap<String, ExcelPOIScheduleBean>();
        Date baseDate = null;
        Holiday[] holidays = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            Workbook workbook = WorkbookFactory.create(in);
            Sheet sheet = workbook.getSheetAt(0);

            baseDate = ProjectUtils.createBaseDate(workbook, sheet);
            poiMap = ProjectUtils.createExcelPOIScheduleBeanMap(workbook,
                    baseDate);
            holidays = ProjectUtils.createHolidays(workbook);
        } catch (InvalidFormatException e) {
            throw new ProjectException(e);
        } catch (FileNotFoundException e) {
            throw new ProjectException(e);
        } catch (IOException e) {
            throw new ProjectException(e);
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO 自動生成された catch ブロック
                    e.printStackTrace();
                }
        }

        try {
            List<TaskInformation> taskInfoList = new ArrayList<TaskInformation>();
            ExcelScheduleBeanSheet sheet = new XLSBeans().load(
                    getInputStream(), ExcelScheduleBeanSheet.class);
            sheet.setBaseDate(baseDate);
            java.util.List<ExcelScheduleBean> instanceList = sheet
                    .getExcelScheduleBean();

            for (ExcelScheduleBean instance : instanceList) {

                // poiBeanからJavaBeansへコピー。
                ExcelPOIScheduleBean poiBean = poiMap.get(instance.getTaskId());

                // if (poiBean != null) {
                // ExcelPOIScheduleBean2ExcelScheduleBean.convert(poiBean,
                // instance);
                // }

                if (poiBean != null && !StringUtils.isEmpty(poiBean.getId())) {
                    // instance.setBaseDate(sheet.getBaseDate());
                    Task task = ExcelScheduleBean2Task.convert(instance);
                    // if (poiBean != null) {
                    ExcelPOIScheduleBean2Task.convert(poiBean, task);
                    // }
                    if (StringUtils.isEmpty(poiBean.getTaskId())) {
                        String message = String.format(
                                "id: %s のタスクIDが未記載です。必須項目のためエラーとして処理を終了します。",
                                poiBean.getId());
                        throw new ProjectException(message);
                    }

                    PVTotalBean pvTotalBean = ExcelPOIScheduleBean2PVTotalBean
                            .convert(poiBean);
                    ACTotalBean acTotalBean = ExcelPOIScheduleBean2ACTotalBean
                            .convert(poiBean);
                    EVTotalBean evTotalBean = ExcelPOIScheduleBean2EVTotalBean
                            .convert(poiBean);
                    TaskInformation taskInfo = ExcelPOIScheduleBean2TaskInformation
                            .convert(poiBean);
                    taskInfo.setTask(task);
                    taskInfo.setPV(pvTotalBean);
                    taskInfo.setAC(acTotalBean);
                    taskInfo.setEV(evTotalBean);
                    taskInfoList.add(taskInfo);
                }
            }

            Project project = new Project();
            project.setTaskInformations(taskInfoList
                    .toArray(new TaskInformation[taskInfoList.size()]));

            ExcelScheduleBeanSheet2Project.convert(sheet, project);

            project.setHolidays(holidays);
            // System.out.println("---");
            // System.out.println(project.getProjectStartDate());
            // System.out.println(project.getProjectEndDate());
            // System.out.println("---");
            return project;
        } catch (XLSBeansException e) {
            throw new ProjectException(e);
            // } finally {
            // if (in != null) {
            // try {
            // in.close();
            // } catch (IOException e) {
            // throw new ProjectException(e);
            // }
            // }
        }
    }

    private void setTask(ExcelPOIScheduleBean source, Task dest) {
        dest.setScheduledEndDate(scheduledEndDate(source, dest));
        dest.setScheduledStartDate(scheduledStartDate(source, dest));
        dest.setNumberOfDays(source.getNumberOfDays() == null ? 0 : source
                .getNumberOfDays());
        dest.setNumberOfManDays(source.getNumberOfManDays() == null ? Double.NaN
                : source.getNumberOfManDays());
    }

    private java.util.Date scheduledEndDate(ExcelPOIScheduleBean source,
            Task dest) {
        if (source.getScheduledEndDate() != null) {
            return source.getScheduledEndDate();
        }
        return dest.getScheduledEndDate();
    }

    private java.util.Date scheduledStartDate(ExcelPOIScheduleBean source,
            Task dest) {
        if (source.getScheduledStartDate() != null) {
            return source.getScheduledStartDate();
        }
        return dest.getScheduledStartDate();
    }

    // private void setEV(ExcelPOIScheduleBean source, EVTotalBean dest) {
    // dest.setEarnedValue(source.getEarnedValue() == null ? Double.NaN
    // : source.getEarnedValue());
    // dest.setProgressRate(source.getProgressRate() == null ? Double.NaN
    // : source.getProgressRate());
    // if (source.getEndDate() != null) {
    // dest.setEndDate(source.getEndDate());
    // }
    // if (source.getStartDate() != null) {
    // dest.setStartDate(source.getStartDate());
    // }
    // }
    //
    // private void setAC(ExcelPOIScheduleBean source, ACTotalBean dest) {
    // dest.setActualCost(source.getActualCost() == null ? Double.NaN : source
    // .getActualCost());
    // }
    //
    // private void setPV(ExcelPOIScheduleBean source, PVTotalBean dest) {
    // dest.setPlannedValue(source.getPlannedValue() == null ? Double.NaN
    // : source.getPlannedValue());
    // }

}
