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

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.corext.dom.TokenScanner;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class JDTUtils {

    /**
     * �^��񂩂�AtoString���쐬����
     * 
     * @param type
     * @return
     */
    public static String createToString(IType type) {
        return "hoge";
    }

    public static String createIndentedCode(String code, IMethod member,
            IDocument document, IJavaProject project)
            throws JavaModelException, BadLocationException {
        String lineDelim = TextUtilities.getDefaultLineDelimiter(document); // �f���~�^���擾�B
        String tmp = addLineDelim(code, lineDelim);

        int memberStartOffset = getMemberStartOffset(member, document);
        // ���̃I�t�Z�b�g�̈ʒu�̃C���f���g��������������Ă���
        String indentString = getIndentString(project, document,
                memberStartOffset);
        // code�ɉ��s�R�[�h���āA����ɐ�̃C���f���g������
        String indentedCode = MyStrings.changeIndent(tmp, 0, project,
                indentString, lineDelim);
        return indentedCode;
    }

    public static String addLineDelim(String code, String lineDelim) {
        String temp = code;
        if (temp != null) {
            temp = lineDelim + temp;
        }
        return temp;
    }

    public static String getIndentString(IJavaProject project,
            IDocument document, int memberStartOffset)
            throws BadLocationException {
        // �I�t�Z�b�g�ʒu�̍s���B
        IRegion region = document.getLineInformationOfOffset(memberStartOffset);
        // �I�t�Z�b�g��Region���킽���āA���̍s���擾���Ă�?
        // �Y������s�B}�܂łłȂ��āA��s�B
        String line = document.get(region.getOffset(), region.getLength());
        String indentString = MyStrings.getIndentString(project, line);
        return indentString;
    }

    /**
     * �����̃��\�b�h���̃I�t�Z�b�g�ʒu(�擪)��Ԃ����\�b�h�B
     * �R�����g���Ƃ�������ꍇ�́A�R�����g�����͌��̈ʒu(�z���g�̃��\�b�h�̐擪�ʒu)��Ԃ��B
     * 
     * @param method
     * @param document
     * @return
     * @throws JavaModelException
     */
    public static int getMemberStartOffset(IMethod method, IDocument document)
            throws JavaModelException {
        ISourceRange sourceRange = method.getSourceRange();
        int memberStartOffset = sourceRange.getOffset();

        // �ʏ�͏�̃I�t�Z�b�g��Ԃ��̂Ŗ��Ȃ����ǁA�R�����g�Ƃ�������ƁA�R�����g��肤���ɂ������Ⴄ�B
        // �z���g�Ƀ��\�b�h�̒���ɂ���ꍇ�́A���̂悤�ɂ���K�v������݂������B
        TokenScanner scanner = new TokenScanner(document, method
                .getJavaProject());

        try {
            int nextStartOffset = scanner.getNextStartOffset(memberStartOffset,
                    true);
            return nextStartOffset;
            // return scanner.getNextStartOffset(memberStartOffset, true);
            // read
            // to
            // the
            // first real non
            // comment token
        } catch (CoreException e) {
            // ignore
        }
        return memberStartOffset;
    }

    /**
     * �����̃��\�b�h���̃I�t�Z�b�g�ʒu(�I���)��Ԃ����\�b�h�B
     * 
     * @param method
     * @param document
     * @return
     * @throws JavaModelException
     */
    public static int getMemberEndOffset(IMethod method, IDocument document)
            throws JavaModelException {
        ISourceRange sourceRange = method.getSourceRange();
        int memberEndOffset = sourceRange.getOffset() + sourceRange.getLength();
        return memberEndOffset;
    }

    /**
     * IType����A�Ō�̃��\�b�h���擾���郁�\�b�h�B
     * 
     * @param unit
     * @return
     */
    public static IMethod getLastMethodFromUnit(IType type) {
        try {
            // ���\�b�h�ꗗ���擾�B
            IMethod[] methods = type.getMethods();
            IMethod lastMethod = methods[methods.length - 1];
            return lastMethod;
        } catch (JavaModelException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static IJavaElement[] unit2IJavaElements(ICompilationUnit unit) {
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

    public static ICompilationUnit getCompilationUnit(ExecutionEvent event)
            throws ExecutionException {
        ISelection selection = HandlerUtil.getActiveMenuSelectionChecked(event);
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection sselection = (IStructuredSelection) selection;
            Object firstElement = sselection.getFirstElement();
            if (firstElement instanceof ICompilationUnit) {
                ICompilationUnit unit = (ICompilationUnit) firstElement;
                return unit;
            } // �R�R�́A�\�[�X�R�[�h�̗v�f��I�����đJ�ڂ�����AUnit ����Ȃ���IJavaElement���킽���Ă���]�B
            else {
                System.out.println(firstElement.getClass().getName());
            }
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
                System.out.println(firstElement.getClass().getName());
            }
        }
        return null;
    }
}
