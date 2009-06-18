/******************************************************************************
 * Copyright (c) 2009 Masatomi KINO and others. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *      Masatomi KINO - initial API and implementation
 * $Id$
 ******************************************************************************/
//�쐬��: 2009/06/06
import java.io.FileReader;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;

/**
 * sample.csv��ǂݍ���ŁA�l��W���o�͂ɏo�͂��邾���̃T���v���ł��B
 * 
 * @author Masatomi KINO
 * @version $Revision$
 */
public class CSVReaderSample01 {
    public static void main(String[] args) throws IOException {
        CSVReader reader = new CSVReader(new FileReader("sample.csv"));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            // nextLine[] is an array of values from the line
            System.out.println(nextLine[0] + " " + nextLine[1] + " etc...");
        }
    }
}
