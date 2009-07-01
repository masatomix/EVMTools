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
package nu.mine.kino.plugin.jdt.utils;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.TypeNameRequestor;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class JDTUtils {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(JDTUtils.class);

    public static boolean canUseThisClass(IJavaProject javaProject,
            String qualifiedClassName, IProgressMonitor monitor)
            throws JavaModelException {
        final boolean[] flag = new boolean[1];
        flag[0] = false;
        // �����Ώۂ́AIJavaProject�̃��[�g�ɂ��郂�m�S���B(jar�Ƃ�src�f�B���N�g���Ƃ��A�O���̃p�X���ʂ��Ă�jar�Ƃ�)
        IJavaSearchScope scope = SearchEngine
                .createJavaSearchScope(new IJavaElement[] { javaProject }); // �v���W�F�N�g���̃\�[�X��Ajar(�O���̂�)�������ΏہB
        // SearchEngine().searchAllTypeNames���g���ꍇ�̓t�b�N����N���X��TypeNameRequestor�B
        TypeNameRequestor nameRequestor = new TypeNameRequestor() {
            public void acceptType(int modifiers, char[] packageName,
                    char[] simpleTypeName, char[][] enclosingTypeNames,
                    String path) {
                flag[0] = true;
            }
        };

        String pkg = "";
        String clazzName = "";
        if (qualifiedClassName != null && qualifiedClassName.indexOf('.') > -1) {
            int lastIndex = qualifiedClassName.lastIndexOf('.');
            pkg = qualifiedClassName.substring(0, lastIndex);
            clazzName = qualifiedClassName.substring(lastIndex + 1);
        } else {
            pkg = "";
            clazzName = qualifiedClassName;
        }
        logger.debug("canUseThisClass() - pkg: " + pkg);
        logger.debug("canUseThisClass() - class: " + clazzName);
        new SearchEngine().searchAllTypeNames(pkg.toCharArray(),
                SearchPattern.R_EXACT_MATCH, clazzName.toCharArray(),
                SearchPattern.R_EXACT_MATCH, IJavaSearchConstants.CLASS, scope,
                nameRequestor, IJavaSearchConstants.WAIT_UNTIL_READY_TO_SEARCH,
                monitor);
        return flag[0];
    }

    /**
     * �^��񂩂�AtoString���쐬����
     * 
     * @param type
     * @param lineDelim
     * @param project
     * @param canUseToStringBuilder
     * @return
     * @throws JavaModelException
     * @throws BadLocationException
     */
    public static String createToString(IType type, String lineDelim,
            IJavaProject project, boolean canUseToStringBuilder)
            throws JavaModelException {
        StringBuffer buf = new StringBuffer();
        IField[] fields = type.getFields();
        buf.append(lineDelim);
        buf.append("@Override");
        buf.append(lineDelim);
        buf.append("public String toString(){");
        buf.append(lineDelim);
        // �t�B�[���h���Ȃ��ꍇsuper.toString�ɂ����Ⴄ�B
        if (fields == null || fields.length == 0) {
            buf.append("return super.toString()");
        }
        // ToStringBuilder���g����ꍇ�́A�t�B�[���h����ׂ�B
        else if (canUseToStringBuilder) {
            buf.append("return new ToStringBuilder(this)");
            for (IField method : fields) {
                String elementName = method.getElementName();
                buf.append(".append(\"");
                buf.append(elementName);
                buf.append("\",");
                buf.append(elementName);
                buf.append(")");
            }
            buf.append(".toString()");
        }
        // ToStringBuilder�͎g���Ȃ����ǁA�t�B�[���h������ꍇ�́A�t�B�[���h����ׂ�B
        else {
            buf.append("StringBuffer buf = new StringBuffer();");
            buf.append(lineDelim);
            // buf.append(indentString);
            buf.append("buf");
            for (IField method : fields) {
                String elementName = method.getElementName();
                buf.append(".append(\" ");
                buf.append(elementName);
                buf.append(": \"+");
                buf.append(elementName);
                buf.append(")");
            }
            buf.append(";");
            buf.append(lineDelim);
            buf.append("return new String(buf)");
        }
        buf.append(";");
        buf.append(lineDelim);
        buf.append("}");
        return new String(buf);
    }

    // public static String createIndentedCode(String code, int
    // memberStartOffset,
    // IDocument document, IJavaProject project)
    // throws JavaModelException, BadLocationException {
    // String lineDelim = TextUtilities.getDefaultLineDelimiter(document); //
    // �f���~�^���擾�B
    // String tmp = addLineDelim(code, lineDelim);
    //
    // // int memberStartOffset = getMemberStartOffset(member, document);
    // // ���̃I�t�Z�b�g�̈ʒu�̃C���f���g��������������Ă���
    // String indentString = getIndentString(project, document,
    // memberStartOffset);
    // // code�ɉ��s�R�[�h���āA����ɐ�̃C���f���g������
    // String indentedCode = MyStrings.changeIndent(tmp, 0, project,
    // indentString, lineDelim);
    // return indentedCode;
    // }

    // public static String addLineDelim(String code, String lineDelim) {
    // String temp = code;
    // if (temp != null) {
    // temp = lineDelim + temp;
    // }
    // return temp;
    // }

    // public static String getIndentString(IJavaProject project,
    // IDocument document, int memberStartOffset)
    // throws BadLocationException {
    // // �I�t�Z�b�g�ʒu�̍s���B
    // IRegion region = document.getLineInformationOfOffset(memberStartOffset);
    // // �I�t�Z�b�g��Region���킽���āA���̍s���擾���Ă�?
    // // �Y������s�B}�܂łłȂ��āA��s�B
    // String line = document.get(region.getOffset(), region.getLength());
    // String indentString = MyStrings.getIndentString(project, line);
    // return indentString;
    // }

    // /**
    // * �����̃��\�b�h���̃I�t�Z�b�g�ʒu(�擪)��Ԃ����\�b�h�B
    // * �R�����g���Ƃ�������ꍇ�́A�R�����g�����͌��̈ʒu(�z���g�̃��\�b�h�̐擪�ʒu)��Ԃ��B
    // *
    // * @param method
    // * @param document
    // * @return
    // * @throws JavaModelException
    // */
    // public static int getMemberStartOffset(IMethod method, IDocument
    // document)
    // throws JavaModelException {
    // ISourceRange sourceRange = method.getSourceRange();
    // int memberStartOffset = sourceRange.getOffset();
    //
    // // �ʏ�͏�̃I�t�Z�b�g��Ԃ��̂Ŗ��Ȃ����ǁA�R�����g�Ƃ�������ƁA�R�����g��肤���ɂ������Ⴄ�B
    // // �z���g�Ƀ��\�b�h�̒���ɂ���ꍇ�́A���̂悤�ɂ���K�v������݂������B
    // TokenScanner scanner = new TokenScanner(document, method
    // .getJavaProject());
    //
    // try {
    // int nextStartOffset = scanner.getNextStartOffset(memberStartOffset,
    // true);
    // return nextStartOffset;
    // // return scanner.getNextStartOffset(memberStartOffset, true);
    // // read
    // // to
    // // the
    // // first real non
    // // comment token
    // } catch (CoreException e) {
    // // ignore
    // }
    // return memberStartOffset;
    // }
    //
    // /**
    // * �����̃��\�b�h���̃I�t�Z�b�g�ʒu(�I���)��Ԃ����\�b�h�B
    // *
    // * @param method
    // * @param document
    // * @return
    // * @throws JavaModelException
    // */
    // public static int getMemberEndOffset(IMethod method, IDocument document)
    // throws JavaModelException {
    // ISourceRange sourceRange = method.getSourceRange();
    // int memberEndOffset = sourceRange.getOffset() + sourceRange.getLength();
    // return memberEndOffset;
    // }
    //
    // /**
    // * IType����A�Ō�̃��\�b�h���擾���郁�\�b�h�B�Ȃ��ꍇ��NULL���Ԃ邪�A���̏ꍇ�̏������ۑ�B
    // *
    // * @param unit
    // * @return �Ō�̃��\�b�h�B
    // */
    // public static IMethod getLastMethodFromType(IType type) {
    // logger.debug("getLastMethodFromType(IType) - start");
    // try {
    // // ���\�b�h�ꗗ���擾�B
    // IMethod[] methods = type.getMethods();
    // if (methods != null && methods.length > 0) {
    // IMethod lastMethod = methods[methods.length - 1];
    // return lastMethod;
    // }
    // } catch (JavaModelException e) {
    // logger.error("getLastMethodFromType(IType)", e);
    // JDTUtilsPlugin.logException(e);
    // }
    // logger.debug("getLastMethodFromType(IType) - end");
    // logger.warn("getLastMethodFromType(IType) - ���\�b�h���Ȃ�����(�L�t`;)");
    // return null;
    // }

    /**
     * {@link ICompilationUnit}�������ɂ��āA�q�v�f��Ԃ����\�b�h�B �q�v�f�́A�p�b�P�[�W��A�N���X�Ȃǂ�����B��̓I�ɂ�
     * {@link IPackageDeclaration}�Ƃ��A{@link IType}�ȂǁB
     * 
     * @param unit
     * @return
     */
    public static IJavaElement[] getChildren(ICompilationUnit unit) {
        try {
            if (!unit.isStructureKnown()) {
                return null;
            }
            // �܂��͎q�v�f���擾�B�擾�����̂́A�p�b�P�[�W�錾�Ƃ��A�N���X�^�Ƃ�
            // �N���X��������`����Ă���ꍇ�����邵�B
            IJavaElement[] elements = unit.getChildren();
            return elements;
        } catch (JavaModelException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static IJavaElement getJavaElement(ExecutionEvent event)
            throws ExecutionException {
        ISelection selection = HandlerUtil.getActiveMenuSelectionChecked(event);
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection sselection = (IStructuredSelection) selection;
            Object firstElement = sselection.getFirstElement();
            if (firstElement instanceof IJavaElement) {
                IJavaElement element = (IJavaElement) firstElement;
                return element;
            } // �R�R�́A�\�[�X�R�[�h�̗v�f��I�����đJ�ڂ�����AUnit ����Ȃ���IJavaElement���킽���Ă���]�B
            else {
                logger.debug("getJavaElement(ExecutionEvent)"
                        + firstElement.getClass().getName());
            }
        }
        return null;
    }

    public static void openEditor(ICompilationUnit unit) {
        try {
            // open the editor, forces the creation of a working copy
            IEditorPart editor = JavaUI.openInEditor(unit);
        } catch (PartInitException e) {
            JDTUtilsPlugin.logException(e);
        } catch (JavaModelException e) {
            JDTUtilsPlugin.logException(e);
        }
    }

}
