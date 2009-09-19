/*******************************************************************************
 * Copyright (c) 2006 Masatomi KINO.
 * All rights reserved. 
 * $Id$
 *******************************************************************************/
//�쐬��: 2007/12/08
package nu.mine.kino.utils.db;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Masatomi KINO
 * @version $Revision$
 * @spring.bean id = "nu.mine.kino.utils.db.FKEnablerTarget"
 */
public class OracleFKEnablerImpl implements FKEnabler {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(OracleFKEnablerImpl.class);

    private DataSource dataSource;

    private FKChecker fkChecker;

    private final String ALTER_SQL_TEMPLATE;

    private final String SELECT_SQL;

    public OracleFKEnablerImpl() {
        StringBuffer buf = new StringBuffer();
        // ALTER���̑g�ݗ���
        buf.append("ALTER TABLE ");
        buf.append("{0}");
        buf.append(" ");
        buf.append("{2}");
        buf.append(" ");
        buf.append("CONSTRAINT ");
        buf.append("{1}");
        ALTER_SQL_TEMPLATE = new String(buf);
        SELECT_SQL = "select TABLE_NAME,CONSTRAINT_NAME,STATUS from USER_CONSTRAINTS where CONSTRAINT_TYPE =\'R\'";
    }

    public void enableAll() {
        logger.debug("enableAll() - start");

        JdbcTemplate jt = new JdbcTemplate(dataSource);
        List<Map<String, String>> tableList = jt.queryForList(SELECT_SQL);
        for (Map<String, String> map : tableList) {
            String tableName = map.get("TABLE_NAME");
            String fkName = map.get("CONSTRAINT_NAME");
            // String status = map.get("STATUS");
            enable(tableName, fkName);
        }
        logger.debug("enableAll() - end");
    }

    public void disableAll() {
        logger.debug("disableAll() - start");

        JdbcTemplate jt = new JdbcTemplate(dataSource);
        List<Map<String, String>> tableList = jt.queryForList(SELECT_SQL);
        for (Map<String, String> map : tableList) {
            String tableName = map.get("TABLE_NAME");
            String fkName = map.get("CONSTRAINT_NAME");
            disable(tableName, fkName);
        }
        logger.debug("disableAll() - end");
    }

    /**
     * @param jt
     * @param tableName
     * @param fkName
     */
    public void enable(String tableName, String fkName) {
        doAlter(tableName, fkName, "ENABLE");
    }

    public void disable(String tableName, String fkName) {
        doAlter(tableName, fkName, "DISABLE");
    }

    /**
     * @param tableName
     * @param fkName
     */
    private void doAlter(String tableName, String fkName, String isEnable) {
        logger.debug("doAlter(String, String, String) - start " + isEnable);

        if (!fkChecker.exist(tableName, fkName)) {
            logger.warn("table='" + tableName + "',FK='" + fkName
                    + "'�͊Y�����R�[�h�Ȃ�");
            logger.debug("doAlter(String, String, String) - end");
            return;
        }
        JdbcTemplate jt = new JdbcTemplate(dataSource);
        Object[] parameters = new Object[] { tableName, fkName, isEnable };
        MessageFormat form = new MessageFormat(ALTER_SQL_TEMPLATE);
        String altSql = form.format(parameters);
        logger.info("���sSQL: " + altSql);
        jt.execute(altSql);

        logger.debug("doAlter(String, String, String) - end");
    }

    /**
     * @param dataSource
     *            dataSource ��ݒ�B
     * @spring.property ref = "dataSource"
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * @param fkChecker
     *            fkChecker ��ݒ�
     * @spring.property ref="nu.mine.kino.utils.db.FKChecker"
     */
    public void setFkChecker(FKChecker fkChecker) {
        this.fkChecker = fkChecker;
    }

    // private void print() {
    // logger.debug("print() - start");
    //
    // JdbcTemplate jt = new JdbcTemplate(dataSource);
    // List<Map<String, String>> tableList = jt.queryForList(SELECT_SQL);
    // for (Map<String, String> map : tableList) {
    // String tableName = map.get("TABLE_NAME");
    // String fkName = map.get("CONSTRAINT_NAME");
    // String status = map.get("STATUS");
    // logger.debug(tableName + " : " + fkName + " : " + status);
    // // System.out.println(tableName + " : " + fkName + " : " + status);
    // }
    //
    // logger.debug("print() - end");
    // }

}
