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
package nu.mine.kino.plugin.beangenerator;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import nu.mine.kino.utils.beangenerator.sheetdata.ClassInformation;
import nu.mine.kino.utils.beangenerator.sheetdata.FieldInformation;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class JavaBeansCreator {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(JavaBeansCreator.class);

    private final IJavaProject javaProject;

    public JavaBeansCreator(IJavaProject javaProject) {
        this.javaProject = javaProject;
        // Velocity������
        URL entry = Activator.getDefault().getBundle().getEntry("/");
        try {
            String pluginDirectory = FileLocator.resolve(entry).getPath();
            File file = new File(pluginDirectory, "lib");
            Properties p = new Properties();
            p.setProperty("file.resource.loader.path", file.getAbsolutePath());
            Velocity.init(p);
        } catch (IOException e) {
            logger.error("JavaBeansCreator()", e);
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("JavaBeansCreator()", e);
            e.printStackTrace();
        }
    }

    public ICompilationUnit create(ClassInformation info) throws CoreException {
        // �t�B�[���h��JavaProject���C���X�^���X����A���擾�B
        IPackageFragmentRoot root = getSourceDir(javaProject);
        String pkg = info.getPackageName();
        // �p�b�P�[�W�ւ̃|�C���^�擾
        IPackageFragment pack = root.getPackageFragment(pkg);
        // �p�b�P�[�W�����݂��Ȃ�������쐬����
        if (!pack.exists()) {
            pack = root.createPackageFragment(pkg, true,
                    new NullProgressMonitor());
        }

        // �\�[�X�R�[�h�̐����J�n�B
        StringBuffer buf = new StringBuffer();
        buf.append(createMain(info));
        ICompilationUnit cu = pack.createCompilationUnit(info.getClassName()
                + ".java", buf.toString(), true, new NullProgressMonitor());
        cu.createPackageDeclaration(pkg, new NullProgressMonitor());

        IType type = cu.getType(info.getClassName());

        // �t�B�[���h����
        createField(type, info);

        // ���\�b�h�̐���
        createMethod(type, info);

        cu.save(new NullProgressMonitor(), true);

        return cu;
    }

    private String executeVelocity(String vm, String name, Object obj) {
        StringBuffer buf = new StringBuffer();
        try {
            VelocityContext context = new VelocityContext();
            context.put(name, obj);
            StringWriter out = new StringWriter();
            Template template = Velocity.getTemplate(vm, "MS932");

            template.merge(context, out);
            buf.append(out.toString());
            out.flush();
        } catch (ResourceNotFoundException e) {
            // TODO �����������ꂽ catch �u���b�N
            e.printStackTrace();
        } catch (ParseErrorException e) {
            // TODO �����������ꂽ catch �u���b�N
            e.printStackTrace();
        } catch (Exception e) {
            // TODO �����������ꂽ catch �u���b�N
            e.printStackTrace();
        }
        return new String(buf);

    }

    private String createMain(ClassInformation clazz) {
        // �N���X��񂩂�AVelocity�Ń��C���������쐬����B
        return executeVelocity("main.vm", "class", clazz);
    }

    /**
     * @param type
     */
    private void createField(IType type, ClassInformation info) {
        logger.debug("createField() start");
        StringBuffer buf = new StringBuffer();
        try {
            List<FieldInformation> fieldInformations = info
                    .getFieldInformations();
            for (FieldInformation field : fieldInformations) {
                String result = executeVelocity("field.vm", "field", field);
                buf.append(result);
            }
            type.createField(buf.toString(), null, true,
                    new NullProgressMonitor());
        } catch (JavaModelException e) {
            e.printStackTrace();
        }
        logger.debug("createField() end");
    }

    private void createMethod(IType type, ClassInformation info) {
        logger.debug("createMethod() start");

        // �쐬�����A�t�B�[���h�݂̂�JavaBeans����͂���B
        analyzeSource(type.getCompilationUnit());
        for (FieldDeclaration element : newFieldList) {
            System.out.println("-----");
            System.out.println(element.getType());
            System.out.println(element.getProperty("aa"));
        }

        // StringBuffer buf = new StringBuffer();
        // try {
        // List<FieldInformation> fieldInformations = info
        // .getFieldInformations();
        // for (FieldInformation field : fieldInformations) {
        // String result = executeVelocity("method.vm", "field", field);
        // buf.append(result);
        // }
        // type.createField(buf.toString(), null, true,
        // new NullProgressMonitor());
        // } catch (JavaModelException e) {
        // e.printStackTrace();
        // }
        logger.debug("createMethod() end");
    }

    private IPackageFragmentRoot getSourceDir(IJavaProject javaProject) {
        IPackageFragmentRoot root = null;
        try {
            IPackageFragmentRoot[] roots = javaProject
                    .getPackageFragmentRoots();
            for (IPackageFragmentRoot rootTmp : roots) {
                if (rootTmp.getKind() == IPackageFragmentRoot.K_SOURCE) {
                    // �������邩���ˁB��Ɍ��܂������ŕԂ����Ⴄ�B
                    root = rootTmp;
                    return root;
                }
            }
        } catch (JavaModelException e) {
            e.printStackTrace();
        }
        return root;
    }

    private void analyzeSource(ICompilationUnit unit) {
        logger.debug("analyzeSource() start");

        // ASTParser�̐���
        ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setResolveBindings(true);
        parser.setSource(unit);
        // ���N���X��͗p��AST(���ۍ\����)�̐���
        CompilationUnit org = (CompilationUnit) parser
                .createAST(new NullProgressMonitor());

        // Visitor�K�p
        org.accept(new FieldASTVisitor());
        logger.debug("analyzeSource() end");
    }

    List<FieldDeclaration> newFieldList = new ArrayList<FieldDeclaration>();

    private class FieldASTVisitor extends ASTVisitor {
        @Override
        public boolean visit(FieldDeclaration node) {
            newFieldList.add(node);
            return super.visit(node);
        }
        @Override
        public boolean visit(ImportDeclaration node) {
            node.getName();
            return super.visit(node);
        }

    }
}
