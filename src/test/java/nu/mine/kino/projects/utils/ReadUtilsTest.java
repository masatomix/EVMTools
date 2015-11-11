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
//çÏê¨ì˙: 2015/11/11

package nu.mine.kino.projects.utils;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class ReadUtilsTest {
    public static final String BASE = "base";

    public static final String DATE_DAT_FILENAME = BASE + "_date.dat";

    public static final String SERIES_DAT_FILENAME = BASE + "_series.dat";

    @Test
    public void testReadFile() throws IOException {
        String base_date = ReadUtils.readFile(new File(DATE_DAT_FILENAME));
        String series_date = ReadUtils.readFile(new File(SERIES_DAT_FILENAME));

        System.out.println("[" + base_date + "]");
        System.out.println("--------------");
        System.out.println("[" + series_date + "]");
        System.out.println("--------------");
    }

}
