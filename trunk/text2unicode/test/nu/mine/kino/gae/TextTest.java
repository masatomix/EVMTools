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
//�쐬��: 2013/09/11

package nu.mine.kino.gae;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class TextTest {
    private Text text = new Text();

    @Test
    public void test() {
        text.setSource("��");
        String base64Encode = text.getBase64Encode();
        System.out.println(base64Encode);

    }

}
