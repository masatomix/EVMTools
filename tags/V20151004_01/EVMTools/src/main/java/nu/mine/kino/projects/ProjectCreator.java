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
//�쐬��: 2014/10/20

package nu.mine.kino.projects;

import nu.mine.kino.entity.Project;

/**
 * �X�g���[������{@link Project}�𐶐�����C���^�t�F�[�X�BExcel�t�@�C����������AJSON�e�L�X�g�t�@�C����������B
 * 
 * @author Masatomi KINO
 * @version $Revision$
 * @see Project
 */
public interface ProjectCreator {

    // public Project createProject(InputStream in) throws ProjectException;
    //
    // public Project createProject(File input) throws ProjectException;

    public Project createProject(Object... conditions) throws ProjectException;

    // public Project createProject() throws ProjectException;

}
