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
//�쐬��: 2014/11/14

package nu.mine.kino.entity;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public interface Validatable {

    /**
     * �I�u�W�F�N�g���L�����ǂ����𔻒肷��B������,������ 0�̔z��ł������� null�̏ꍇ�͕t�я����͕]���ɉe�����Ȃ�
     * 
     * @param conditions
     *            �t�я���
     * @return
     */
    public boolean isValid(Object... conditions);

}
