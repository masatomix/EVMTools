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
 * 
 * @author Masatomi KINO and JavaBeans Creator Plug-in
 */
public class ProjectUser {

    /**
     * id
     */
    private String id;

    /**
     * ���Ȃ��̖��O
     */
    private String fullName;

    /**
     * ����
     */
    private String description;

    /**
     * E-mail
     */
    private String emailAddress;

    /**
     * URL
     */
    private String url;

    /**
     * �W�v���ꂽPlanned Value
     */
    private double plannedValue;

    /**
     * �W�v���ꂽActual Cost
     */
    private double actualCost;

    /**
     * �W�v���ꂽEarned Value
     */
    private double earnedValue;

    /**
     * �W�v���ꂽPlanned Value
     */
    private double plannedValue_p1;

    /**
     * id���Z�b�g����B
     * 
     * @param id
     *            id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * ���Ȃ��̖��O���Z�b�g����B
     * 
     * @param fullName
     *            ���Ȃ��̖��O
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * �������Z�b�g����B
     * 
     * @param description
     *            ����
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * E-mail���Z�b�g����B
     * 
     * @param emailAddress
     *            E-mail
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * URL���Z�b�g����B
     * 
     * @param url
     *            URL
     */
    public void setUrl(String url) {
        this.url = url;
    }

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
     * Earned Value���Z�b�g����B
     * 
     * @param earnedValue
     *            Earned Value
     */
    public void setEarnedValue(double earnedValue) {
        this.earnedValue = earnedValue;
    }

    /**
     * Planned Value���Z�b�g����B
     * 
     * @param plannedValue_p1
     *            Planned Value
     */
    public void setPlannedValue_p1(double plannedValue_p1) {
        this.plannedValue_p1 = plannedValue_p1;
    }

    /**
     * id���擾����B
     * 
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * ���Ȃ��̖��O���擾����B
     * 
     * @return ���Ȃ��̖��O
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * �������擾����B
     * 
     * @return ����
     */
    public String getDescription() {
        return description;
    }

    /**
     * E-mail���擾����B
     * 
     * @return E-mail
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * URL���擾����B
     * 
     * @return URL
     */
    public String getUrl() {
        return url;
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
     * Earned Value���擾����B
     * 
     * @return Earned Value
     */
    public double getEarnedValue() {
        return earnedValue;
    }

    /**
     * Planned Value���擾����B
     * 
     * @return Planned Value
     */
    public double getPlannedValue_p1() {
        return plannedValue_p1;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id)
                .append("���Ȃ��̖��O", fullName).append("����", description)
                .append("E-mail", emailAddress).append("URL", url)
                .append("Planned Value", plannedValue)
                .append("Actual Cost", actualCost)
                .append("Earned Value", earnedValue)
                .append("Planned Value", plannedValue_p1).toString();
    }
}