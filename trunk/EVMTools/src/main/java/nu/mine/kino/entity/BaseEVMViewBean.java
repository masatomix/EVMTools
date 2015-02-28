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

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * ��ʂŎg�p����EVM���
 * 
 * @author Masatomi KINO and JavaBeans Creator Plug-in
 */
public class BaseEVMViewBean {

    /**
     * Planned Value
     */
    private double plannedValue;

    /**
     * Actual Cost
     */
    private double actualCost;

    /**
     * �i����
     */
    private double progressRate;

    /**
     * Earned Value
     */
    private double earnedValue;

    /**
     * �������H��
     */
    private double bac;

    /**
     * �X�P�W���[���̍��� (EV-PV)
     */
    private double sv;

    /**
     * �R�X�g�̍��� (EV-AC)
     */
    private double cv;

    /**
     * �X�P�W���[�������w�� (EV/PV)
     */
    private double spi;

    /**
     * �R�X�g�����w�� (EV/AC)
     */
    private double cpi;

    /**
     * �c��ƃR�X�g�\�� �iBAC�|EV�j/CPI
     */
    private double etc;

    /**
     * �������R�X�g�\�� (AC�{ETC)
     */
    private double eac;

    /**
     * �������R�X�g���� (BAC-EAC)
     */
    private double vac;

    /**
     * ���
     */
    private java.util.Date baseDate;

    /**
     * SPI�̏󋵂�\���A�C�R���̃p�X
     */
    private String spiIconFileName;

    /**
     * CPI�̏󋵂�\���A�C�R���̃p�X
     */
    private String cpiIconFileName;

    /**
     * Planned Value���Z�b�g����B
     * 
     * @param plannedValue
     *            Planned Value
     */
    public void setPlannedValue(double plannedValue) {
        this.plannedValue = plannedValue;
    }

    /**
     * Actual Cost���Z�b�g����B
     * 
     * @param actualCost
     *            Actual Cost
     */
    public void setActualCost(double actualCost) {
        this.actualCost = actualCost;
    }

    /**
     * �i�������Z�b�g����B
     * 
     * @param progressRate
     *            �i����
     */
    public void setProgressRate(double progressRate) {
        this.progressRate = progressRate;
    }

    /**
     * Earned Value���Z�b�g����B
     * 
     * @param earnedValue
     *            Earned Value
     */
    public void setEarnedValue(double earnedValue) {
        this.earnedValue = earnedValue;
    }

    /**
     * BAC (Budget at Completion)���Z�b�g����B
     * 
     * @param bac
     *            BAC (Budget at Completion)
     */
    public void setBac(double bac) {
        this.bac = bac;
    }

    /**
     * SV (Scheduled Variance)���Z�b�g����B
     * 
     * @param sv
     *            SV (Scheduled Variance)
     */
    public void setSv(double sv) {
        this.sv = sv;
    }

    /**
     * CV (Cost Variance)���Z�b�g����B
     * 
     * @param cv
     *            CV (Cost Variance)
     */
    public void setCv(double cv) {
        this.cv = cv;
    }

    /**
     * SPI (Schedule Performance Index)���Z�b�g����B
     * 
     * @param spi
     *            SPI (Schedule Performance Index)
     */
    public void setSpi(double spi) {
        this.spi = spi;
    }

    /**
     * CPI (Cost Performance Index)���Z�b�g����B
     * 
     * @param cpi
     *            CPI (Cost Performance Index)
     */
    public void setCpi(double cpi) {
        this.cpi = cpi;
    }

    /**
     * ETC(Estimate To Completion)���Z�b�g����B
     * 
     * @param etc
     *            ETC(Estimate To Completion)
     */
    public void setEtc(double etc) {
        this.etc = etc;
    }

    /**
     * EAC(Estimate At Completion)���Z�b�g����B
     * 
     * @param eac
     *            EAC(Estimate At Completion)
     */
    public void setEac(double eac) {
        this.eac = eac;
    }

    /**
     * VAC(Variance At Completion)���Z�b�g����B
     * 
     * @param vac
     *            VAC(Variance At Completion)
     */
    public void setVac(double vac) {
        this.vac = vac;
    }

    /**
     * ������Z�b�g����B
     * 
     * @param baseDate
     *            ���
     */
    public void setBaseDate(java.util.Date baseDate) {
        this.baseDate = baseDate;
    }

    /**
     * SPI Icon File Name���Z�b�g����B
     * 
     * @param spiIconFileName
     *            SPI Icon File Name
     */
    public void setSpiIconFileName(String spiIconFileName) {
        this.spiIconFileName = spiIconFileName;
    }

    /**
     * CPI Icon File Name���Z�b�g����B
     * 
     * @param cpiIconFileName
     *            CPI Icon File Name
     */
    public void setCpiIconFileName(String cpiIconFileName) {
        this.cpiIconFileName = cpiIconFileName;
    }

    /**
     * Planned Value���擾����B
     * 
     * @return Planned Value
     */
    public double getPlannedValue() {
        return plannedValue;
    }

    /**
     * Actual Cost���擾����B
     * 
     * @return Actual Cost
     */
    public double getActualCost() {
        return actualCost;
    }

    /**
     * �i�������擾����B
     * 
     * @return �i����
     */
    public double getProgressRate() {
        return progressRate;
    }

    /**
     * Earned Value���擾����B
     * 
     * @return Earned Value
     */
    public double getEarnedValue() {
        return earnedValue;
    }

    /**
     * BAC (Budget at Completion)���擾����B
     * 
     * @return BAC (Budget at Completion)
     */
    public double getBac() {
        return bac;
    }

    /**
     * SV (Scheduled Variance)���擾����B
     * 
     * @return SV (Scheduled Variance)
     */
    public double getSv() {
        return sv;
    }

    /**
     * CV (Cost Variance)���擾����B
     * 
     * @return CV (Cost Variance)
     */
    public double getCv() {
        return cv;
    }

    /**
     * SPI (Schedule Performance Index)���擾����B
     * 
     * @return SPI (Schedule Performance Index)
     */
    public double getSpi() {
        return spi;
    }

    /**
     * CPI (Cost Performance Index)���擾����B
     * 
     * @return CPI (Cost Performance Index)
     */
    public double getCpi() {
        return cpi;
    }

    /**
     * ETC(Estimate To Completion)���擾����B
     * 
     * @return ETC(Estimate To Completion)
     */
    public double getEtc() {
        return etc;
    }

    /**
     * EAC(Estimate At Completion)���擾����B
     * 
     * @return EAC(Estimate At Completion)
     */
    public double getEac() {
        return eac;
    }

    /**
     * VAC(Variance At Completion)���擾����B
     * 
     * @return VAC(Variance At Completion)
     */
    public double getVac() {
        return vac;
    }

    /**
     * ������擾����B
     * 
     * @return ���
     */
    public java.util.Date getBaseDate() {
        return baseDate;
    }

    /**
     * SPI Icon File Name���擾����B
     * 
     * @return SPI Icon File Name
     */
    public String getSpiIconFileName() {
        return spiIconFileName;
    }

    /**
     * CPI Icon File Name���擾����B
     * 
     * @return CPI Icon File Name
     */
    public String getCpiIconFileName() {
        return cpiIconFileName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("Planned Value", plannedValue)
                .append("Actual Cost", actualCost).append("�i����", progressRate)
                .append("Earned Value", earnedValue)
                .append("BAC (Budget at Completion)", bac)
                .append("SV (Scheduled Variance)", sv)
                .append("CV (Cost Variance)", cv)
                .append("SPI (Schedule Performance Index)", spi)
                .append("CPI (Cost Performance Index)", cpi)
                .append("ETC(Estimate To Completion)", etc)
                .append("EAC(Estimate At Completion)", eac)
                .append("VAC(Variance At Completion)", vac)
                .append("���", baseDate)
                .append("SPI Icon File Name", spiIconFileName)
                .append("CPI Icon File Name", cpiIconFileName).toString();
    }
}