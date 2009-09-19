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
//�쐬��: 2009/06/27
package nu.mine.kino.plugin.jdt.utils.handlers;

import java.lang.reflect.InvocationTargetException;

import nu.mine.kino.plugin.jdt.utils.JDTUtils;
import nu.mine.kino.plugin.jdt.utils.JDTUtilsPlugin;
import nu.mine.kino.plugin.jdt.utils.WorkbenchRunnableAdapter;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.filebuffers.LocationKind;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.ui.actions.OrganizeImportsAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * GUI����Ăяo����āA�I�������\�[�X�ɑ΂��āAtoString��ǉ�����A�N�V�����n���h���ł��B �I�������I�u�W�F�N�g��
 * {@link ICompilationUnit}�̎��͊܂܂��N���X�S�Ăɑ΂���toString���\�b�h��ǉ����܂��B �I�������I�u�W�F�N�g��
 * {@link IType}�̎��́A�I�����Ă���N���X�ɑ΂��Ă̂݁AtoString���\�b�h��ǉ����܂��B
 * 
 * @author Masatomi KINO
 * @version $Revision$
 */
public class AddToStringHandler extends AbstractHandler implements IHandler {

    public Object execute(ExecutionEvent event) throws ExecutionException {
        final IJavaElement element = JDTUtils.getJavaElement(event);
        // targets:�����Ώۂ̃N���X�Q�B
        IJavaElement[] targets = null;

        // �\�[�X���̂�I�������ꍇ�A���̔z���̃N���X�B�������Ώۂɂ���B
        if (element instanceof ICompilationUnit) {
            // event����CompilationUnit���擾�B
            final ICompilationUnit unit = (ICompilationUnit) element;
            // unit����A�q�v�f������IJavaElement�̔z��Ƃ��Ď擾�B
            // ����IJavaElement�B�́A�p�b�P�[�W��������AIType�������肷��B
            targets = JDTUtils.getChildren(unit);
        }
        // �\�[�X���̃N���X����I�������ꍇ�A���������������Ώۂɂ���B
        else if (element.getElementType() == IJavaElement.TYPE) {
            IType type = (IType) element;
            IJavaElement[] tmpTargets = new IJavaElement[] { type };
            targets = tmpTargets;
        }

        // �Y���t�@�C�����J���Ă����B
        ICompilationUnit unit = (ICompilationUnit) element
                .getAncestor(IJavaElement.COMPILATION_UNIT);
        JDTUtils.openEditor(unit);

        try {
            // �ʃX�����N�����āA���s���ďI���B
            IWorkbenchSite site = HandlerUtil.getActiveSite(event);
            AddToStringThread op = new AddToStringThread(targets, site);
            PlatformUI.getWorkbench().getProgressService().runInUI(
                    PlatformUI.getWorkbench().getProgressService(),
                    new WorkbenchRunnableAdapter(op, op.getScheduleRule()),
                    op.getScheduleRule());
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            JDTUtilsPlugin.logException(targetException);
        } catch (InterruptedException e) {
            JDTUtilsPlugin.logException(e, false);
        }
        // ////////////////////////////////////////////////////////////////////////////
        return null;
    }

    /**
     * Eclipse ���[�N�x���`���̃_�C�A���O��ŁA���ۂɏ��������s����X���b�h�B
     * 
     * @author Masatomi KINO
     * @version $Revision$
     */
    private class AddToStringThread implements IWorkspaceRunnable {
        // ��{�I�ɂ�IType�̔z��B���܂Ƀp�b�P�[�W�錾(IPackageDeclaration)���������Ă�B
        private final IJavaElement[] targets;

        private final IWorkbenchSite site;

        public AddToStringThread(IJavaElement[] targets, IWorkbenchSite site) {
            this.targets = targets;
            this.site = site;
        }

        public ISchedulingRule getScheduleRule() {
            return ResourcesPlugin.getWorkspace().getRoot();
        }

        public void run(IProgressMonitor monitor) throws CoreException {
            addToString(targets, monitor);
            importActionRun();
        }

        private void importActionRun() {
            // �ȉ��́AICompilationUnit���擾���āAimport�����Y��ɂ���A�N�V���������s����B
            ICompilationUnit unit = (ICompilationUnit) targets[0]
                    .getAncestor(IJavaElement.COMPILATION_UNIT);
            OrganizeImportsAction importsAction = new OrganizeImportsAction(
                    site);
            IStructuredSelection selection = new StructuredSelection(unit);
            importsAction.run(selection);
        }

        /**
         * @param targets
         * @param monitor
         * @throws CoreException
         */
        private void addToString(IJavaElement[] targets,
                IProgressMonitor monitor) throws CoreException {
            if (targets == null || targets.length == 0) {
                return;
            }
            try {
                monitor.beginTask("toString��ǉ����܂�", 5);
                // ITextFileBufferManager�̎擾�B
                ITextFileBufferManager manager = FileBuffers
                        .getTextFileBufferManager();
                // IPath path = unit.getPath();
                IPath path = targets[0].getPath();
                // �t�@�C����connect
                SubProgressMonitor subMonitor = new SubProgressMonitor(monitor,
                        4);// monitor��5�̂����A4���󂯎��B
                subMonitor.beginTask("", targets.length);
                manager.connect(path, LocationKind.IFILE, subMonitor);
                try {
                    // document�擾�B
                    IDocument document = manager.getTextFileBuffer(path,
                            LocationKind.IFILE).getDocument();
                    String lineDelim = TextUtilities
                            .getDefaultLineDelimiter(document);
                    IJavaProject project = targets[0].getJavaProject();

                    // ���̃N���X�������̃v���W�F�N�g�ŗ��p�\�����`�F�b�N����B
                    boolean canUseToStringBuilder = JDTUtils.canUseThisClass(
                            project,
                            "org.apache.commons.lang.builder.ToStringBuilder",
                            subMonitor);

                    // �q�v�f�́A�p�b�P�[�W�錾��������A�N���X�������肷��B��̃\�[�X�ɕ����N���X�������Ă���ꍇ�����邵�B
                    for (final IJavaElement javaElement : targets) {
                        // ���^(�N���X)��������΁AIType�ɃL���X�g���Ă����B
                        if (javaElement.getElementType() == IJavaElement.TYPE) { // IPackageDeclaration�͏��O�������̂ŁB
                            IType type = (IType) javaElement;
                            String createToString = JDTUtils.createToString(
                                    type, lineDelim, project,
                                    canUseToStringBuilder);
                            type.createMethod(createToString, null, true,
                                    subMonitor);
                        }
                        subMonitor.worked(1);
                    }
                } finally {
                    manager.disconnect(path, LocationKind.IFILE, subMonitor);
                    subMonitor.done();
                }
            } finally {
                monitor.worked(1);
                monitor.done();
            }
        }
    }

}
