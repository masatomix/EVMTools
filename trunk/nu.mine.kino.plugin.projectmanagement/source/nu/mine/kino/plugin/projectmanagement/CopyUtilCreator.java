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
package nu.mine.kino.plugin.projectmanagement;

import java.util.List;

import nu.mine.kino.plugin.projectmanagement.copyutils.sheetdata.IDetailInformation;
import nu.mine.kino.plugin.projectmanagement.copyutils.sheetdata.IUtilInformation;
import nu.mine.kino.plugin.projectmanagement.sheetdata.IClassInformation;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class CopyUtilCreator extends BaseGenerator {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(CopyUtilCreator.class);

    public CopyUtilCreator(IJavaProject javaProject) {
        super(javaProject);
    }

    /**
     * �ڂ��ւ���񂩂�AJavaBeans�� ICompilationUnit �𐶐�����
     * 
     * @param info
     * @return
     * @throws CoreException
     */
    public ICompilationUnit create(IUtilInformation info) throws CoreException {
        logger.debug("create(ClassInformation) - start"); //$NON-NLS-1$

        // �t�B�[���h��JavaProject���C���X�^���X����A���擾�B
        IPackageFragmentRoot root = getSourceDir(getJavaProject());
        String pkg = info.getPackageName();
        // �p�b�P�[�W�ւ̃|�C���^�擾
        IPackageFragment pack = root.getPackageFragment(pkg);
        // �p�b�P�[�W�����݂��Ȃ�������쐬����
        if (!pack.exists()) {
            pack = root.createPackageFragment(pkg, true,
                    new NullProgressMonitor());
        }

        // �\�[�X�R�[�h�̐����J�n�B
        String mainStatement = createMain(info);
        String destClassName = !Utils.isEmpty(info.getDestIFName()) ? info
                .getDestIFName() : info.getDestClassName();
        String targetClassName = info.getSourceIFName() + "2" + destClassName;

        if (!Utils.isEmpty(info.getClassName())) { // �N���X���������I�Ɏw�肳��Ă���ꍇ�B
            targetClassName = info.getClassName();
        }

        ICompilationUnit cu = pack.createCompilationUnit(targetClassName
                + ".java", mainStatement, true, new NullProgressMonitor()); //$NON-NLS-1$
        // package����
        // cu.createPackageDeclaration(pkg, new NullProgressMonitor());

        IType type = cu.getType(targetClassName);

        createMethod(type, info);
        // �����o���B
        cu.save(new NullProgressMonitor(), true);

        logger.debug("create(ClassInformation) - end"); //$NON-NLS-1$
        return cu;
    }

    private String createMain(IUtilInformation clazz) throws CoreException {
        // �N���X��񂩂�AVelocity�Ń��C���������쐬����B
        return executeVelocity("copyutil_main.vm", new String[] { "util" }, //$NON-NLS-1$ //$NON-NLS-2$
                new IUtilInformation[] { clazz });
    }

    /**
     * �ڂ��ւ������̃\�[�X�R�[�h�𐶐�����B�s���ƁB
     * 
     * @param type
     * @param info
     * @param destClassName
     * @return
     */
    private String createCopyStatement(IType type, IDetailInformation info,
            String destClassName) {

        // dest.setFundName(source.getFundName()); ���������̂��o�͂������B
        StringBuffer buf = new StringBuffer();
        buf.append("((");
        buf.append(destClassName);
        buf.append(")");
        buf.append("dest).set");
        buf.append(Utils.createPropertyMethodName(info.getDestFieldName()));
        String logic = info.getLogic();
        if (Utils.isEmpty(logic)) {
            buf.append("( ");
            buf.append("source.get");
            buf.append(Utils.createPropertyMethodName(info.getSourceFieldName()));
            buf.append("() );");
        } else {
            buf.append("( ");
            buf.append(logic);
            buf.append(" );");
        }
        return new String(buf);
    }

    private void createMethod(IType type, IUtilInformation util)
            throws CoreException {
        StringBuffer buf = new StringBuffer();
        String destClassName = util.getDestClassName();

        List<IDetailInformation> fieldInformations = util.getDetails();
        for (IDetailInformation info : fieldInformations) {
            buf.append(createCopyStatement(type, info, destClassName));
            buf.append("\n");
        }
        String methodStatement = executeVelocity(
                "copyutil_method.vm", new String[] { "util", "copyStatement" }, new Object[] { util, new String(buf) }); //$NON-NLS-1$ //$NON-NLS-2$
        type.createMethod(methodStatement, null, true,
                new NullProgressMonitor());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nu.mine.kino.plugin.beacon.generator.BaseGenerator#create(nu.mine.kino
     * .plugin.beacon.generator.sheetdata.IClassInformation)
     */
    @Override
    public ICompilationUnit create(IClassInformation info) throws CoreException {
        throw new UnsupportedOperationException();
    }

}