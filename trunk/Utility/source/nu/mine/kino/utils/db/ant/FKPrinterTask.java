package nu.mine.kino.utils.db.ant;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nu.mine.kino.utils.common.ApplicationContextManager;
import nu.mine.kino.utils.db.FKChecker;

import org.apache.log4j.Logger;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.springframework.context.ApplicationContext;

public class FKPrinterTask extends Task {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(FKPrinterTask.class);

    private String method;

    private List<Table> tables = new ArrayList<Table>();

    public Table createTable() {
        Table table = new Table();
        tables.add(table);
        return table;
    }

    public void execute() throws BuildException {
        if (method == null || "".equals(method)) {
            throw new BuildException("method�����͕K�{���ڂł��B");
        }
        Thread currentThread = Thread.currentThread();
        ClassLoader oldClassLoader = currentThread.getContextClassLoader();
        try {
            currentThread.setContextClassLoader(this.getClass()
                    .getClassLoader());
            ApplicationContext context = ApplicationContextManager
                    .getInstance().getContext();
            FKChecker checker = (FKChecker) context
                    .getBean("nu.mine.kino.utils.db.FKChecker");

            // ////////////////////////////////////////////////////////////////////////////////
            if (method.equals("echoAll")) {
                System.out.println("---- FK����`����Ă���e�[�u�����ꗗ���܂��B ----");
                echoAll(checker);
                System.out.println("---- FK����`����Ă���e�[�u�����ꗗ���܂����B ----");
            } else if (method.equals("echo")) {
                // ////////////////////////////////////////////////////////////////////////////////
                if (tables == null || tables.size() <= 0) {
                    throw new BuildException(
                            "method������enable���w�肵���ꍇ�͎q�v�ftable�Ńe�[�u������FK�����w�肷��K�v������܂��B");
                }
                for (Table table : tables) {
                    // fk�������Ă�������A���݃`�F�b�N�B�Ȃ�������e�[�u��������FK���ɍs��
                    String fkName = table.getFkname();
                    // ////////////////////////////////////////////////////////////////////////////////
                    if (fkName != null && !"".equals(fkName)) {
                        echoExist(checker, table);
                    } else {
                        String tableName = table.getName();
                        System.out.println("---- " + tableName
                                + " �e�[�u����FK�����ꗗ���܂��B ----");
                        echoInfo(checker, tableName);
                        System.out.println("---- �ꗗ���܂����B ----");
                    }
                }
            } else {
                throw new BuildException(
                        "method�����̒l�ɕs���Ȓl���L�q����Ă��܂��B���݃T�|�[�g���Ă���l��echoAll,echo �݂̂ł��B");
            }
        } finally {
            currentThread.setContextClassLoader(oldClassLoader);
        }

    }

    /**
     * �����̃e�[�u�����̏������X�g����
     * 
     * @param checker
     * @param tableName
     */
    private void echoInfo(FKChecker checker, String tableName) {
        List<Map<String, String>> name = checker
                .findRecordByTableName(tableName);
        for (Map<String, String> map : name) {
            String dbFKName = map.get("CONSTRAINT_NAME");
            String dbStatus = map.get("STATUS");
            Object[] parameters = new Object[] { tableName, dbFKName, dbStatus };
            String temp = "Table={0}, FK={1}, STATUS={2}";
            MessageFormat form = new MessageFormat(temp);
            String result = form.format(parameters);
            logger.debug(result);
            System.out.println(result);
        }

    }

    /**
     * �����̃e�[�u���̃e�[�u�����AFK�������݂��邩�����X�g����
     * 
     * @param checker
     * @param table
     */
    private void echoExist(FKChecker checker, Table table) {
        String tableName = table.getName();
        String fkName = table.getFkname();
        Object[] parameters = new Object[] { tableName, fkName };
        String temp = "Table={0}, FK={1}";
        MessageFormat form = new MessageFormat(temp);
        String result = form.format(parameters);
        System.out.println(result + "�����݂��܂����H: "
                + checker.exist(tableName, fkName));
    }

    /**
     * FK����`����Ă���e�[�u�����ꗗ����B
     * 
     * @param checker
     */
    private void echoAll(FKChecker checker) {
        List<Map<String, String>> name = checker.findAll();
        for (Map<String, String> map : name) {
            String tableName = map.get("TABLE_NAME");
            String fkName = map.get("CONSTRAINT_NAME");
            String status = map.get("STATUS");
            Object[] parameters = new Object[] { tableName, fkName, status };
            // String temp = "table={0},FK='{1}'";// ''�̓t�H�[�}�b�g����Ȃ��B
            String temp = "Table={0}, FK={1}, STATUS={2}";
            MessageFormat form = new MessageFormat(temp);
            String result = form.format(parameters);
            logger.debug(result);
            System.out.println(result);
        }
    }

    /**
     * @param method
     *            method ��ݒ�B
     */
    public void setMethod(String method) {
        this.method = method;
    }

}