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

package nu.mine.kino.entity;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 画面で使用するPV/AC/EV情報
 * 
 * @author Masatomi KINO and JavaBeans Creator Plug-in
 * @version $Revision$
 */
public class BasePVACEVViewBean implements java.io.Serializable {

    /**
     * id
     */
    private String id;

    /**
     * ﾀｽｸID
     */
    private String taskId;

    /**
     * Planned Value (From PV)
     */
    private double plannedValue;

    /**
     * Actual Cost (From AC)
     */
    private double actualCost;

    /**
     * 進捗率 (From EV)
     */
    private double progressRate;

    /**
     * Earned Value (From EV)
     */
    private double earnedValue;

    /**
     * Planned Value
     */
    private double plannedValue_p1;

    /**
     * 基準日
     */
    private java.util.Date baseDate;

    /**
     * 分類 (From Task)
     */
    private String type;

    /**
     * タスクの＃に該当する列 (From Task)
     */
    private String taskSharp;

    /**
     * タスク名 (From Task)
     */
    private String taskName;

    /**
     * 担当 (From Task)
     */
    private String personInCharge;

    /**
     * タスク概要 (From Task)
     */
    private String taskAbstract;

    /**
     * 予定工数 (From Task)
     */
    private double numberOfManDays;

    /**
     * 予定開始日 (From Task)
     */
    private java.util.Date scheduledStartDate;

    /**
     * 予定終了日 (From Task)
     */
    private java.util.Date scheduledEndDate;

    /**
     * 稼動予定日数 (From Task)
     */
    private int numberOfDays;

    /**
     * 要注意タスク
     */
    private boolean check;

    /**
     * idをセットする。
     * 
     * @param id
     *            id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * ﾀｽｸIDをセットする。
     * 
     * @param taskId
     *            ﾀｽｸID
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * Planned Valueをセットする。
     * 
     * @param plannedValue
     *            Planned Value
     */
    public void setPlannedValue(double plannedValue) {
        this.plannedValue = plannedValue;
    }

    /**
     * Actual Costをセットする。
     * 
     * @param actualCost
     *            Actual Cost
     */
    public void setActualCost(double actualCost) {
        this.actualCost = actualCost;
    }

    /**
     * 進捗率をセットする。
     * 
     * @param progressRate
     *            進捗率
     */
    public void setProgressRate(double progressRate) {
        this.progressRate = progressRate;
    }

    /**
     * Earned Valueをセットする。
     * 
     * @param earnedValue
     *            Earned Value
     */
    public void setEarnedValue(double earnedValue) {
        this.earnedValue = earnedValue;
    }

    /**
     * Planned Valueをセットする。
     * 
     * @param plannedValue_p1
     *            Planned Value
     */
    public void setPlannedValue_p1(double plannedValue_p1) {
        this.plannedValue_p1 = plannedValue_p1;
    }

    /**
     * 基準日をセットする。
     * 
     * @param baseDate
     *            基準日
     */
    public void setBaseDate(java.util.Date baseDate) {
        this.baseDate = baseDate;
    }

    /**
     * 分類をセットする。
     * 
     * @param type
     *            分類
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * タスクの＃に該当する列をセットする。
     * 
     * @param taskSharp
     *            タスクの＃に該当する列
     */
    public void setTaskSharp(String taskSharp) {
        this.taskSharp = taskSharp;
    }

    /**
     * タスク名をセットする。
     * 
     * @param taskName
     *            タスク名
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * 担当をセットする。
     * 
     * @param personInCharge
     *            担当
     */
    public void setPersonInCharge(String personInCharge) {
        this.personInCharge = personInCharge;
    }

    /**
     * タスク概要をセットする。
     * 
     * @param taskAbstract
     *            タスク概要
     */
    public void setTaskAbstract(String taskAbstract) {
        this.taskAbstract = taskAbstract;
    }

    /**
     * 予定工数をセットする。
     * 
     * @param numberOfManDays
     *            予定工数
     */
    public void setNumberOfManDays(double numberOfManDays) {
        this.numberOfManDays = numberOfManDays;
    }

    /**
     * 予定開始日をセットする。
     * 
     * @param scheduledStartDate
     *            予定開始日
     */
    public void setScheduledStartDate(java.util.Date scheduledStartDate) {
        this.scheduledStartDate = scheduledStartDate;
    }

    /**
     * 予定終了日をセットする。
     * 
     * @param scheduledEndDate
     *            予定終了日
     */
    public void setScheduledEndDate(java.util.Date scheduledEndDate) {
        this.scheduledEndDate = scheduledEndDate;
    }

    /**
     * 稼動予定日数をセットする。
     * 
     * @param numberOfDays
     *            稼動予定日数
     */
    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    /**
     * 要注意タスクをセットする。
     * 
     * @param check
     *            要注意タスク
     */
    public void setCheck(boolean check) {
        this.check = check;
    }

    /**
     * idを取得する。
     * 
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * ﾀｽｸIDを取得する。
     * 
     * @return ﾀｽｸID
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * Planned Valueを取得する。
     * 
     * @return Planned Value
     */
    public double getPlannedValue() {
        return plannedValue;
    }

    /**
     * Actual Costを取得する。
     * 
     * @return Actual Cost
     */
    public double getActualCost() {
        return actualCost;
    }

    /**
     * 進捗率を取得する。
     * 
     * @return 進捗率
     */
    public double getProgressRate() {
        return progressRate;
    }

    /**
     * Earned Valueを取得する。
     * 
     * @return Earned Value
     */
    public double getEarnedValue() {
        return earnedValue;
    }

    /**
     * Planned Valueを取得する。
     * 
     * @return Planned Value
     */
    public double getPlannedValue_p1() {
        return plannedValue_p1;
    }

    /**
     * 基準日を取得する。
     * 
     * @return 基準日
     */
    public java.util.Date getBaseDate() {
        return baseDate;
    }

    /**
     * 分類を取得する。
     * 
     * @return 分類
     */
    public String getType() {
        return type;
    }

    /**
     * タスクの＃に該当する列を取得する。
     * 
     * @return タスクの＃に該当する列
     */
    public String getTaskSharp() {
        return taskSharp;
    }

    /**
     * タスク名を取得する。
     * 
     * @return タスク名
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * 担当を取得する。
     * 
     * @return 担当
     */
    public String getPersonInCharge() {
        return personInCharge;
    }

    /**
     * タスク概要を取得する。
     * 
     * @return タスク概要
     */
    public String getTaskAbstract() {
        return taskAbstract;
    }

    /**
     * 予定工数を取得する。
     * 
     * @return 予定工数
     */
    public double getNumberOfManDays() {
        return numberOfManDays;
    }

    /**
     * 予定開始日を取得する。
     * 
     * @return 予定開始日
     */
    public java.util.Date getScheduledStartDate() {
        return scheduledStartDate;
    }

    /**
     * 予定終了日を取得する。
     * 
     * @return 予定終了日
     */
    public java.util.Date getScheduledEndDate() {
        return scheduledEndDate;
    }

    /**
     * 稼動予定日数を取得する。
     * 
     * @return 稼動予定日数
     */
    public int getNumberOfDays() {
        return numberOfDays;
    }

    /**
     * 要注意タスクを取得する。
     * 
     * @return 要注意タスク
     */
    public boolean isCheck() {
        return check;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id)
                .append("ﾀｽｸID", taskId).append("Planned Value", plannedValue)
                .append("Actual Cost", actualCost).append("進捗率", progressRate)
                .append("Earned Value", earnedValue)
                .append("Planned Value", plannedValue_p1)
                .append("基準日", baseDate).append("分類", type)
                .append("タスクの＃に該当する列", taskSharp).append("タスク名", taskName)
                .append("担当", personInCharge).append("タスク概要", taskAbstract)
                .append("予定工数", numberOfManDays)
                .append("予定開始日", scheduledStartDate)
                .append("予定終了日", scheduledEndDate)
                .append("稼動予定日数", numberOfDays).append("要注意タスク", check)
                .toString();
    }
}