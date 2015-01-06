/******************************************************************************
 * Copyright (c) 2014 Masatomi KINO and others. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *      Masatomi KINO - initial API and implementation
 * $Id$
 ******************************************************************************/
//�쐬��: 2014/11/03

package nu.mine.kino.projects.utils;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import nu.mine.kino.entity.ExcelScheduleBean;

/**
 * �v���W�F�N�g�����v�Z���邽�߂�Base�ƂȂ�f�[�^����邽�߂�Utils
 * 
 * @author Masatomi KINO
 * @version $Revision$
 */
public class BaseDataUtils {
    // //////////////// �ғ��\����� //////////////////////////////
    /**
     * Excel��̃v���b�g�f�[�^��Map����A�ғ��\��������v�Z�B �v���b�g����Ă��鐔���J�E���g���Ă���B
     * 
     * @param plotDataMap
     *            Excel��̃v���b�g�f�[�^�̃}�b�v
     * @return �ғ��\�����
     */
    private static int plotCount(Map<String, String> plotDataMap) {
        String[] strArray = plotDataMap.values().toArray(
                new String[plotDataMap.size()]);
        int plotNumber = 0;
        for (String target : strArray) {
            if (!Utils.isEmpty(target)) {
                plotNumber++;
            }
        }
        return plotNumber;
    }

    /**
     * Excel��̍s�I�u�W�F�N�g����ғ��\��������v�Z�B�v���b�g����Ă��鐔���J�E���g���Ă���B
     * 
     * @param instance
     *            Excel��̍s�I�u�W�F�N�g
     * @return �ғ��\�����
     */
    public static int calculateNumberOfDays(ExcelScheduleBean instance) {
        return plotCount(instance.getPlotDataMap());
    }

    // //////////////// �ғ��\����� //////////////////////////////

    // //////////////// �v���W�F�N�g�̊J�n���E�I���� //////////////////////////////

    /**
     * Excel��̃v���b�g�f�[�^��Map ����ŏ��̓��t�������B�悤����ɊJ�n���B
     * 
     * @param plotDataMap
     *            Excel��̃v���b�g�f�[�^��Map
     * @return �ŏ��̓��t
     */
    private static Date minDate(Map<String, String> plotDataMap) {
        boolean exist = false;
        Set<String> keySet = plotDataMap.keySet();
        long ans = Long.MAX_VALUE;
        for (String serial : keySet) {
            if (!Utils.isEmpty(plotDataMap.get(serial))) {
                Date targetDate = Utils.excelSerialValue2Date(serial);
                ans = Math.min(ans, targetDate.getTime());
                exist = true;
            }
        }
        return exist ? new Date(ans) : null;
    }

    /**
     * Excel��̃v���b�g�f�[�^��Map ����ő�̓��t�������B�悤����ɏI�����B
     * 
     * @param plotDataMap
     *            Excel��̃v���b�g�f�[�^��Map
     * @return �ő�̓��t
     */
    private static Date maxDate(Map<String, String> plotDataMap) {
        boolean exist = false;
        Set<String> keySet = plotDataMap.keySet();
        long ans = Long.MIN_VALUE;
        for (String serial : keySet) {
            if (!Utils.isEmpty(plotDataMap.get(serial))) {
                Date targetDate = Utils.excelSerialValue2Date(serial);
                ans = Math.max(ans, targetDate.getTime());
                exist = true;
            }
        }
        return exist ? new Date(ans) : null;

    }

    /**
     * Excel��̍s�I�u�W�F�N�g���� �ŏ��̓��t�������B�悤����ɊJ�n���B
     * 
     * @param instance
     *            Excel��̍s�I�u�W�F�N�g
     * @return �J�n���t
     */
    public static Date calculateMinDate(ExcelScheduleBean instance) {
        return minDate(instance.getPlotDataMap());
    }

    /**
     * Excel��̍s�I�u�W�F�N�g���� �ő�̓��t�������B�悤����ɏI�����B
     * 
     * @param instance
     *            Excel��̍s�I�u�W�F�N�g
     * @return �I�����t
     */
    public static Date calculateMaxDate(ExcelScheduleBean instance) {
        return maxDate(instance.getPlotDataMap());
    }

    // //////////////// �v���W�F�N�g�̊J�n���E�I���� //////////////////////////////

    /**
     * Excel��̃v���b�g�f�[�^��Map ���w�肵�����t�ŕ�������B �w�肵�����t�Ƃ��Ȃ����t�́A���ɓ����B
     * 
     * @param baseDate
     *            �w�肵�����t
     * @param plotDataMap
     * @return ���������ꂽ���t�Z�b�g�̔z��B��ڂ����B��ڂ��E�B�f�[�^��Excel�̃V���A���l�B
     */
    static Set<String>[] split(Date baseDate, Map<String, String> plotDataMap) {

        Set<String> leftSet = new TreeSet<String>();
        Set<String> rightSet = new TreeSet<String>();

        Date startDate = minDate(plotDataMap);
        Date endDate = maxDate(plotDataMap);
        Set<String> keySet = plotDataMap.keySet();
        for (String serial : keySet) {
            if (!Utils.isEmpty(plotDataMap.get(serial))) {
                Date targetDate = Utils.excelSerialValue2Date(serial);
                boolean betweenLeft = Utils.isBetween(targetDate, startDate,
                        baseDate, true, true);
                boolean betweenRight = Utils.isBetween(targetDate, baseDate,
                        endDate, false, true);
                if (betweenLeft) {
                    leftSet.add(serial);
                }

                if (betweenRight) {
                    rightSet.add(serial);
                }
            }
        }
        return new Set[] { leftSet, rightSet };
    }

    /**
     * Excel��̍s�I�u�W�F�N�g(�̃��X�g)����A����Excel�t�@�C���̃v���W�F�N�g�J�n���t�A�I�����t���擾����B
     * ���ۂ́A��ڂ̍s�I�u�W�F�N�g�̓��t�𒲂ׂĂ���B
     * 
     * @param instanceList
     * @return
     */
    public static Date[] getProjectRange(
            java.util.List<ExcelScheduleBean> instanceList) {
        Date[] range = getProjectRange(instanceList.get(0));
        return range;
    }

    private static Date[] getProjectRange(ExcelScheduleBean instance) {
        return getProjectRange(instance.getPlotDataMap());
    }

    static Date[] getProjectRange(Map<String, String> plotDataMap) {
        Set<String> keySet = plotDataMap.keySet();
        long maxAns = Long.MIN_VALUE;
        long minAns = Long.MAX_VALUE;
        for (String serial : keySet) {
            Date targetDate = Utils.excelSerialValue2Date(serial);
            maxAns = Math.max(maxAns, targetDate.getTime());
            minAns = Math.min(minAns, targetDate.getTime());
        }
        return new Date[] { new Date(minAns), new Date(maxAns) };
    }

    // //////////////// �v���W�F�N�g�̊J�n���E�I���� //////////////////////////////

    /**
     * ������baseDate�̓��t�ɂ��āA ������plogDataMap�̂����A�v���b�g�̃}�X�����݂��ǂ������`�F�b�N����B
     * 
     * @param baseDate
     * @param plotDataMap
     * @return
     */
    static boolean existsPlot(Date baseDate, Map<String, String> plotDataMap) {
        Set<String> keySet = plotDataMap.keySet();
        for (String serial : keySet) {
            // value��null�l�łȂ����t�ɂ��āA
            if (!Utils.isEmpty(plotDataMap.get(serial))) {
                Date targetDate = Utils.excelSerialValue2Date(serial);
                if (targetDate.equals(baseDate)) {
                    return true;
                }
            }
        }
        return false;
    }
    // private static void printDate(Set<String> keySet) {
    // for (String serial : keySet) {
    // System.out.println(serial);
    // System.out.println(excelSerialValue2Date(serial));
    // double d = (Double.valueOf(serial) - 25569 - 0.375) * 86400000;
    // }
    // }

}
