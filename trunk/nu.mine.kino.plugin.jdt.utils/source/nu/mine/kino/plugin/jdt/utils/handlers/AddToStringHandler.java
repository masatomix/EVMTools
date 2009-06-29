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
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jdt.ui.actions.OrganizeImportsAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class AddToStringHandler extends AbstractHandler implements IHandler {

    public Object execute(ExecutionEvent event) throws ExecutionException {
        final IJavaElement element = JDTUtils.getJavaElement(event);
        IJavaElement[] targets = null;

        // �\�[�X���̂�I�������ꍇ�B
        if (element instanceof ICompilationUnit) {
            // event����CompilationUnit���擾�B
            final ICompilationUnit unit = (ICompilationUnit) element;
            // unit����A�q�v�f������IJavaElement�̔z��Ƃ��Ď擾�B
            targets = JDTUtils.unit2IJavaElements(unit);
        }
        // �\�[�X���̃N���X����I�������ꍇ�B
        else if (element.getElementType() == IJavaElement.TYPE) {
            IType type = (IType) element;
            IJavaElement[] tmpTargets = new IJavaElement[] { type };
            targets = tmpTargets;
        }

        try {
            // open the editor, forces the creation of a working copy
            IEditorPart editor = JavaUI.openInEditor(targets[0]
                    .getAncestor(IJavaElement.COMPILATION_UNIT));

            IWorkbenchSite site = HandlerUtil.getActiveSite(event);
            AddToStringThread op = new AddToStringThread(targets, site);
            PlatformUI.getWorkbench().getProgressService().runInUI(
                    PlatformUI.getWorkbench().getProgressService(),
                    new WorkbenchRunnableAdapter(op, op.getScheduleRule()),
                    op.getScheduleRule());
        } catch (InvocationTargetException e) {
            JDTUtilsPlugin.logException(e);
        } catch (InterruptedException e) {
            JDTUtilsPlugin.logException(e);
        } catch (PartInitException e) {
            JDTUtilsPlugin.logException(e);
        } catch (JavaModelException e) {
            JDTUtilsPlugin.logException(e);
        }
        // ////////////////////////////////////////////////////////////////////////////
        return null;
    }

    class AddToStringThread implements IWorkspaceRunnable {
        private final IJavaElement[] javaElements;

        private final IWorkbenchSite site;

        public AddToStringThread(IJavaElement[] javaElements,
                IWorkbenchSite site) {
            this.javaElements = javaElements;
            this.site = site;
        }

        public ISchedulingRule getScheduleRule() {
            return ResourcesPlugin.getWorkspace().getRoot();
        }

        public void run(IProgressMonitor monitor) throws CoreException {
            addToString(javaElements, monitor);

            ICompilationUnit unit = (ICompilationUnit) javaElements[0]
                    .getAncestor(IJavaElement.COMPILATION_UNIT);
            OrganizeImportsAction importsAction = new OrganizeImportsAction(
                    site);
            IStructuredSelection selection = new StructuredSelection(unit);
            importsAction.run(selection);
        }
    }

    /**
     * @param elements
     * @param monitor
     * @throws CoreException
     */
    private void addToString(IJavaElement[] elements, IProgressMonitor monitor)
            throws CoreException {
        if (elements == null || elements.length == 0) {
            return;
        }
        try {
            monitor.beginTask("toString��ǉ����܂�", 5);
            // ITextFileBufferManager�̎擾�B
            ITextFileBufferManager manager = FileBuffers
                    .getTextFileBufferManager();
            // IPath path = unit.getPath();
            IPath path = elements[0].getPath();
            // �t�@�C����connect
            SubProgressMonitor subMonitor = new SubProgressMonitor(monitor, 4);
            subMonitor.beginTask("", elements.length);
            manager.connect(path, LocationKind.IFILE, subMonitor);
            try {
                // document�擾�B
                IDocument document = manager.getTextFileBuffer(path,
                        LocationKind.IFILE).getDocument();
                // IJavaProject project = unit.getJavaProject();
                IJavaProject project = elements[0].getJavaProject();

                boolean canUseToStringBuilder = JDTUtils.canUseToStringBuilder(
                        project, monitor);

                // �G�f�B�b�g�p�N���X�𐶐��B
                MultiTextEdit edit = new MultiTextEdit();

                // �q�v�f�́A�p�b�P�[�W�錾��������A�N���X�������肷��B��̃\�[�X�ɕ����N���X�������Ă���ꍇ�����邵�B
                for (final IJavaElement javaElement : elements) {
                    // ���^(�N���X)��������΁AIType�ɃL���X�g���Ă����B
                    if (javaElement.getElementType() == IJavaElement.TYPE) {
                        // �z���g�̓R�R�ŁA�I�����ꂽType���������s���Ĕ��f���K�v�B
                        // �n���h������cu������������_�ŁA�ǂ�����JavaElement�����ď���ێ����Ă����Ȃ��Ɠ���ȁB
                        IType type = (IType) javaElement;
                        IMethod lastMethod = JDTUtils
                                .getLastMethodFromType(type);
                        String createToString = JDTUtils.createToString(type,
                                lastMethod, document, project,
                                canUseToStringBuilder);
                        String code = JDTUtils.createIndentedCode(
                                createToString, lastMethod, document, project);

                        // �I�t�Z�b�g�ʒu���v�Z����B
                        int endOffSet = JDTUtils.getMemberEndOffset(lastMethod,
                                document);

                        edit.addChild(new InsertEdit(endOffSet, code)); // �I�t�Z�b�g�ʒu�ɁA�}������B
                    }
                    subMonitor.worked(1);
                }
                edit.apply(document); // apply all edits
            } catch (BadLocationException e) {
                e.printStackTrace();
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
