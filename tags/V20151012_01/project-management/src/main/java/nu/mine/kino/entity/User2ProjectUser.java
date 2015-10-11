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

import hudson.model.User;

public class User2ProjectUser {

    /**
     * �����̃I�u�W�F�N�g�̃v���p�e�B����f�[�^���R�s�[���Ė߂�l�̃I�u�W�F�N�g�𐶐����ĕԂ����\�b�h�B
     * 
     * @param source
     * @return
     */
    public static ProjectUser convert(User source) {
        ProjectUser dest = new ProjectUser();

        // �K�v�ɉ����ē���ȍڂ��ւ����� �J�n
        ((ProjectUser) dest).setId(source.getId());
        ((ProjectUser) dest).setFullName(source.getFullName());
        ((ProjectUser) dest).setDescription(source.getDescription());
        ((ProjectUser) dest).setEmailAddress(getEmail(source));
        ((ProjectUser) dest).setUrl(source.getUrl());

        // ����ȍڂ��ւ����� �I��

        return dest;
    }

    /**
     * ����������������փv���p�e�B���R�s�[���郁�\�b�h�B
     * 
     * @param source
     * @param dest
     */
    public static void convert(User source, ProjectUser dest) {
        // �K�v�ɉ����ē���ȍڂ��ւ����� �J�n
        ((ProjectUser) dest).setId(source.getId());
        ((ProjectUser) dest).setFullName(source.getFullName());
        ((ProjectUser) dest).setDescription(source.getDescription());
        ((ProjectUser) dest).setEmailAddress(getEmail(source));
        ((ProjectUser) dest).setUrl(source.getUrl());

        // ����ȍڂ��ւ����� �I��

    }

    private static String getEmail(User source) {
        hudson.tasks.Mailer.UserProperty property = source
                .getProperty(hudson.tasks.Mailer.UserProperty.class);
        // System.out.println("pro: " + property.getAddress());
        return property.getAddress();
    }

}
