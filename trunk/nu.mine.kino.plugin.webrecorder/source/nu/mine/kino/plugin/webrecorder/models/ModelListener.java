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
//�쐬��: 2012/07/07

package nu.mine.kino.plugin.webrecorder.models;

/**
 * model T�̒ʒm���󂯎��Listener�C���^�t�F�[�X
 * 
 * @author Masatomi KINO
 * @version $Revision$
 * @see Models
 */
public interface ModelListener<T> {
    /**
     * Models�N���X��model��add���ꂽ�Ƃ��ɃR�[���o�b�N����܂�
     * 
     * @param model
     */
    void modelAdded(T model);

    /**
     * Models�N���X���Ǘ����Ă��郊�X�g�����ׂč폜���ꂽ�Ƃ��ɃR�[���o�b�N����܂�
     */
    void modelAllRemoved();

    /**
     * Models�N���X��model���폜���ꂽ�Ƃ��ɃR�[���o�b�N����܂�
     * 
     * @param model
     */
    void modelRemoved(T model);
}
