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
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;
import org.eclipse.jdt.core.search.TypeNameRequestor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
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

            IJavaProject javaProject = javaElements[0].getJavaProject();
            String prefix = "org.apache.commons.lang.builder.ToStringBuilder";
            // �����Ώۂ́AIJavaProject�̃��[�g�ɂ��郂�m�S���B(jar�Ƃ�src�f�B���N�g���Ƃ��A�O���̃p�X���ʂ��Ă�jar�Ƃ�)
            // IPackageFragmentRoot[] roots = javaProject
            // .getAllPackageFragmentRoots();
            IJavaSearchScope scope = SearchEngine
                    .createJavaSearchScope(new IJavaElement[] { javaProject }); // �v���W�F�N�g���̃\�[�X��Ajar(�O���̂�)�������ΏہB

            // �N���X���������P�B
            // new SearchEngine().search���g���ꍇ�̓t�b�N����N���X��SearchRequestor�B
            SearchRequestor requestor = new SearchRequestor() {
                public void acceptSearchMatch(SearchMatch match)
                        throws CoreException {
                    Object javaElement = match.getElement();
                    System.out.println(javaElement);
                    if (javaElement instanceof IType) {
                        IType type = (IType) javaElement;
                        String typeName = type.getFullyQualifiedName();
                    }
                }
            };
            // http://help.eclipse.org/ganymede/index.jsp?topic=/org.eclipse.jdt.doc.isv/reference/api/org/eclipse/jdt/core/search/class-use/SearchPattern.html
            // ��Ƃ��āAprefix�̓N���X���̏ꍇ�́Ajava.lang.Object�Ƃ��ARunnable�Ƃ��AList<String>
            // �Ƃ��A�炵���B
            SearchPattern pattern = SearchPattern.createPattern(prefix,
                    IJavaSearchConstants.CLASS, // �N���X�����B
                    IJavaSearchConstants.DECLARATIONS, // �錾��T���B
                    SearchPattern.R_EXACT_MATCH); // ���m�Ƀ}�b�`�B
            SearchParticipant participant = SearchEngine
                    .getDefaultSearchParticipant();
            new SearchEngine().search(pattern,
                    new SearchParticipant[] { participant }, scope, requestor,
                    monitor);
            System.out.println("end.");

            // //////////
            // �N���X���������Q�B
            // new
            // SearchEngine().searchAllTypeNames���g���ꍇ�̓t�b�N����N���X��TypeNameRequestor�B
            TypeNameRequestor nameRequestor = new TypeNameRequestor() {
                public void acceptType(int modifiers, char[] packageName,
                        char[] simpleTypeName, char[][] enclosingTypeNames,
                        String path) {
                    System.out.println("------");
                    System.out.println(new String(packageName));
                    System.out.println(new String(simpleTypeName));
                    for (int j = 0; j < enclosingTypeNames.length; j++) {
                        System.out.println(new String(enclosingTypeNames[j]));
                    }
                    System.out.println(path);
                    System.out.println("------");
                }
            };

            String pkg = "org.apache.commons.lang.builder";
            String clazzName = "ToStringBuilder";
            new SearchEngine().searchAllTypeNames(pkg.toCharArray(),
                    SearchPattern.R_EXACT_MATCH, clazzName.toCharArray(),
                    SearchPattern.R_EXACT_MATCH, IJavaSearchConstants.CLASS,
                    scope, nameRequestor,
                    IJavaSearchConstants.WAIT_UNTIL_READY_TO_SEARCH, monitor);
            System.out.println("end.");
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
                        String code = JDTUtils
                                .createIndentedCode(JDTUtils.createToString(
                                        type, lastMethod, document, project),
                                        lastMethod, document, project);

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
