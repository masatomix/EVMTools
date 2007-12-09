package nu.mine.kino.utils.db.ant;

import java.util.ArrayList;
import java.util.List;

import nu.mine.kino.utils.db.FKEnabler;

import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class FKEnablerTask extends Task {
    private String method;

    private List<Table> tables = new ArrayList<Table>();

    public Table createTable() {
        Table table = new Table();
        tables.add(table);
        return table;
    }

    public void execute() throws BuildException {

        // Spring���g���Ƃ��̋L�q�炵���B�ł�����ł�
        // �R�}���h���C�������Ant�ŃR�P��������B
        // ���ǈȉ�(�̂���ɉ�)�̃R�[�h��������OK�̂悤�ł��B
        // code to handle cnf issues with taskdef classloader
        // AntClassLoader2 antClassLoader = null;
        // Object obj = this.getClass().getClassLoader();
        // if (obj instanceof AntClassLoader2) {
        // antClassLoader = (AntClassLoader2) obj;
        // antClassLoader.setThreadContextLoader();
        // }
        // end code to handle classnotfound issue

        AntClassLoader antClassLoader = null;
        Object obj = this.getClass().getClassLoader();
        if (AntClassLoader.class.isAssignableFrom(obj.getClass())) {
            antClassLoader = (AntClassLoader) obj;
            antClassLoader.setThreadContextLoader();
        }

        if (method == null || "".equals(method)) {
            throw new BuildException("method�����͕K�{���ڂł��B");
        }

        FileSystemXmlApplicationContext context1 = new FileSystemXmlApplicationContext(
                new String[] { "beans_db.xml" });
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "beans.xml", "beans_auto.xml" }, context1);
        FKEnabler enabler = (FKEnabler) context
                .getBean("nu.mine.kino.utils.db.FKEnabler");

        if (method.equals("enableAll")) {
            enabler.enableAll();
        } else if (method.equals("disableAll")) {
            enabler.disableAll();
        } else if (method.equals("enable")) {
            if (tables == null || tables.size() <= 0) {
                throw new BuildException(
                        "method������enable���w�肵���ꍇ�͎q�v�ftable�Ńe�[�u������FK�����w�肷��K�v������܂��B");
            }
            for (Table table : tables) {
                enabler.enable(table.getName(), table.getFkname());
            }
        } else if (method.equals("disable")) {
            if (tables == null || tables.size() <= 0) {
                throw new BuildException(
                        "method������disable���w�肵���ꍇ�͎q�v�ftable�Ńe�[�u������FK�����w�肷��K�v������܂��B");
            }
            for (Table table : tables) {
                enabler.disable(table.getName(), table.getFkname());
            }
        } else {
            throw new BuildException(
                    "method�����̒l�ɕs���Ȓl���L�q����Ă��܂��B���݃T�|�[�g���Ă���l��enableAll,disableAll,enable,disable�݂̂ł��B");
        }
    }

    // /**
    // * @return method
    // */
    // public String getMethod() {
    // return method;
    // }

    /**
     * @param method
     *            method ��ݒ�B
     */
    public void setMethod(String method) {
        this.method = method;
    }

}