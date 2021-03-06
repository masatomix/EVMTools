/******************************************************************************
 * Copyright (c) 2008-2014 Masatomi KINO and others. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *      Masatomi KINO - initial API and implementation
 ******************************************************************************/

package nu.mine.kino.entity;

public class Task2ACViewBean {

    /**
     * 引数のオブジェクトのプロパティからデータをコピーして戻り値のオブジェクトを生成して返すメソッド。
     * 
     * @param source
     * @return
     */
    public static ACViewBean convert(Task source) {
        ACViewBean dest = new ACViewBean();

        // 必要に応じて特殊な載せ替え処理 開始
        ((ACViewBean) dest).setId(source.getId());
        ((ACViewBean) dest).setTaskId(source.getTaskId());
        ((ACViewBean) dest).setType(source.getType());
        ((ACViewBean) dest).setTaskSharp(source.getTaskSharp());
        ((ACViewBean) dest).setTaskName(source.getTaskName());
        ((ACViewBean) dest).setPersonInCharge(source.getPersonInCharge());
        ((ACViewBean) dest).setTaskAbstract(source.getTaskAbstract());
        ((ACViewBean) dest).setNumberOfManDays(source.getNumberOfManDays());
        ((ACViewBean) dest).setScheduledStartDate(source
                .getScheduledStartDate());
        ((ACViewBean) dest).setScheduledEndDate(source.getScheduledEndDate());
        ((ACViewBean) dest).setNumberOfDays(source.getNumberOfDays());

        // 特殊な載せ替え処理 終了

        return dest;
    }

    /**
     * 第一引数から第二引数へプロパティをコピーするメソッド。
     * 
     * @param source
     * @param dest
     */
    public static void convert(Task source, ACViewBean dest) {
        // 必要に応じて特殊な載せ替え処理 開始
        ((ACViewBean) dest).setId(source.getId());
        ((ACViewBean) dest).setTaskId(source.getTaskId());
        ((ACViewBean) dest).setType(source.getType());
        ((ACViewBean) dest).setTaskSharp(source.getTaskSharp());
        ((ACViewBean) dest).setTaskName(source.getTaskName());
        ((ACViewBean) dest).setPersonInCharge(source.getPersonInCharge());
        ((ACViewBean) dest).setTaskAbstract(source.getTaskAbstract());
        ((ACViewBean) dest).setNumberOfManDays(source.getNumberOfManDays());
        ((ACViewBean) dest).setScheduledStartDate(source
                .getScheduledStartDate());
        ((ACViewBean) dest).setScheduledEndDate(source.getScheduledEndDate());
        ((ACViewBean) dest).setNumberOfDays(source.getNumberOfDays());

        // 特殊な載せ替え処理 終了

    }

}
