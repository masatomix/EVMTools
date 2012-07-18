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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Viewer�ɕ\��������(���f��)���Ǘ�����N���X�B �����f���̒ǉ��E�폜�Ȃǂ����X�i�����ɒʒm����B
 * 
 * @author Masatomi KINO
 * @version $Revision$
 */
public class Models<T> {
    private List<T> models = new ArrayList<T>();

    private List<ModelListener<T>> listeners = new ArrayList<ModelListener<T>>();

    /**
     * model �����X�g�ɒǉ�����B�ǉ����ꂽ�烊�X�i�����ɒʒm�B
     * 
     * @param model
     * @see ModelListener#modelAdded(Object)
     */
    public void addModel(T model) {
        models.add(model);
        for (ModelListener<T> listener : listeners) {
            listener.modelAdded(model);
        }
    }

    /**
     * ������model�����X�g����폜���܂��B�폜������A���X�i�����ɒʒm
     * 
     * @param model
     * @see ModelListener#modelRemoved(Object)
     */
    public void removeModel(T model) {
        models.remove(model);
        for (ModelListener<T> listener : listeners) {
            listener.modelRemoved(model);
        }
    }

    /**
     * model���ׂč폜���܂��B�폜�����烊�X�i�����ɒʒm�B
     * 
     * @see ModelListener#modelAllRemoved()
     */
    public void removeAllModels() {
        models.clear();
        for (ModelListener<T> listener : listeners) {
            listener.modelAllRemoved();
        }
    }

    public void addModelListener(ModelListener<T> listener) {
        listeners.add(listener);
    }

    public void removeModelListener(ModelListener<T> listener) {
        listeners.remove(listener);
    }
}
