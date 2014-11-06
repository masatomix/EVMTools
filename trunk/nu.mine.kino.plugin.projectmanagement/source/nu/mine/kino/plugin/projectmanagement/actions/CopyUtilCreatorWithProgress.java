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
//作成日: 2008/08/15
package nu.mine.kino.plugin.projectmanagement.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nu.mine.kino.plugin.projectmanagement.Activator;
import nu.mine.kino.plugin.projectmanagement.CopyUtilCreator;
import nu.mine.kino.plugin.projectmanagement.Messages;
import nu.mine.kino.plugin.projectmanagement.copyutils.sheetdata.IUtilInformation;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.ui.actions.FormatAllAction;
import org.eclipse.jdt.ui.actions.OrganizeImportsAction;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchSite;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class CopyUtilCreatorWithProgress implements IRunnableWithProgress {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(CopyUtilCreatorWithProgress.class);

    private IStructuredSelection ss;

    private List<ICompilationUnit> list = new ArrayList<ICompilationUnit>();

    private IWorkbenchSite site;

    private final String id;

    public CopyUtilCreatorWithProgress(IStructuredSelection ss,
            IWorkbenchSite site, String id) {
        this.ss = ss;
        this.site = site;
        this.id = id;
    }

    public void run(IProgressMonitor monitor) throws InvocationTargetException,
            InterruptedException {
        logger.debug("run(IProgressMonitor) - start"); //$NON-NLS-1$

        int totalWork = ss.size() * 2;
        monitor.beginTask(Messages.JavaBeansCreatorWithProgress_MSG_BEGIN_TASK,
                totalWork);

        try {
            Iterator<IFile> e = ss.iterator();
            // 選択されたExcelファイル数分、繰り返し。
            while (e.hasNext()) {
                IFile file = e.next();
                // IFile から、JavaProjectへ。Resource系から、JDTの世界へっ。
                IProject project = file.getProject();
                IJavaProject javaProject = JavaCore.create(project);
                monitor.subTask(Messages.JavaBeansCreatorWithProgress_MSG_EXECUTE
                        + file.getFullPath());
                monitor.worked(1);
                List<IUtilInformation> classInformations = Activator
                        .getDefault().getCopyUtilInInformations(file);
                // 一つのExcelファイルから、複数のクラス情報が取得できるので、取得した
                // クラス数分、繰り返し。
                for (IUtilInformation classInformation : classInformations) {
                    logger.debug(classInformation);
                    monitor.subTask(Messages.JavaBeansCreatorWithProgress_MSG_EXECUTE
                            + classInformation.getSourceIFName());

                    CopyUtilCreator[] generator = null;
                    generator = new CopyUtilCreator[] { new CopyUtilCreator(
                            javaProject) };

                    ICompilationUnit[] cuArray = new ICompilationUnit[generator.length];
                    for (int i = 0; i < generator.length; i++) {
                        
                        cuArray[i] = generator[i].create(classInformation);
                        if (cuArray[i] != null) {
                            list.add(cuArray[i]);
                        }
                    }
                    if (monitor.isCanceled()) {
                        throw new InterruptedException(
                                "Cancel has been requested."); //$NON-NLS-1$
                    }
                }
                monitor.worked(1);
            }
        } catch (CoreException e1) {
            // Activator.logException(e1, false);
            throw new InvocationTargetException(e1);
        }

        // 以下、整形処理。
        Display display = Display.getDefault();
        display.syncExec(new Runnable() {
            public void run() {
                ICompilationUnit[] units = (ICompilationUnit[]) list
                        .toArray(new ICompilationUnit[list.size()]);
                OrganizeImportsAction importsAction = new OrganizeImportsAction(
                        site);
                FormatAllAction formatAllAction = new FormatAllAction(site);
                IStructuredSelection selection = new StructuredSelection(units);
                importsAction.run(selection);
                formatAllAction.run(selection);

                // AddGetterSetterAction getterAction = new
                // AddGetterSetterAction(
                // site);
                // getterAction.run(selection);
            }
        });
        monitor.worked(totalWork);
        monitor.done();
        logger.debug("run(IProgressMonitor) - end"); //$NON-NLS-1$
    }
}
