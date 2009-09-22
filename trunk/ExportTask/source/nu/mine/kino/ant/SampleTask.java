/*******************************************************************************
 * Copyright (c) 2006 Masatomi KINO.
 * All rights reserved. 
 * $Id$
 *******************************************************************************/
//�쐬��: 2006/12/16
package nu.mine.kino.ant;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class SampleTask extends Task {
    private Echo echo;

    public void execute() throws BuildException {
        System.out.println("�G�R�[���Ă܂�: " + echo.getMessage());
    }

    public Echo createEcho() {
        if (echo == null) {
            echo = new Echo();
        }
        return echo;
    }

}
