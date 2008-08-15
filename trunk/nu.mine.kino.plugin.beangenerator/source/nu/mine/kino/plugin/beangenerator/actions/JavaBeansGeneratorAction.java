/******************************************************************************
 * Copyright (c) 2008 Masatomi KINO and others. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *      Masatomi KINO - initial API and implementation
 * $Id$
 ******************************************************************************/
//�쐬��: 2008/08/15
package nu.mine.kino.plugin.beangenerator.actions;

import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class JavaBeansGeneratorAction implements IObjectActionDelegate {
    private static final Logger logger = Logger
            .getLogger(JavaBeansGeneratorAction.class);

    private IStructuredSelection ss;

    private IWorkbenchSite site;

    private static final String MESSAGE_CONFIRM = "JavaBeans�̎������������s���܂��B��낵���ł����H\r\n���łɃt�@�C�������݂���ꍇ�A�㏑������܂��B";

    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        this.site = targetPart.getSite();
    }

    public void run(IAction action) {
        Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getShell();

        if (!MessageDialog
                .openConfirm(shell, "JavaBeans�̎�������", MESSAGE_CONFIRM)) {
            return;
        }
        logger.debug("�_�C�A���O�\��");

        /* ���s���̃_�C�A���O�\�� */
        ProgressMonitorDialog dialog = new ProgressMonitorDialog(shell);
        dialog.setCancelable(true);

        try {
            logger.debug("���s");
            JavaBeansCreatorWithProgress progress = new JavaBeansCreatorWithProgress(
                    ss, site);
            dialog.run(true, true, progress);
        } catch (InvocationTargetException e) {
            logger.error("���f", e);
        } catch (InterruptedException e) {
            logger.error("���f", e);
        }
    }

    public void selectionChanged(IAction action, ISelection selection) {
        ss = (IStructuredSelection) selection;
    }

}
