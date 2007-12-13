/*******************************************************************************
 * Copyright (c) 2006 Masatomi KINO.
 * All rights reserved. 
 * $Id$
 *******************************************************************************/
//�쐬��: 2007/12/08
package nu.mine.kino.utils.db;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Masatomi KINO
 * @version $Revision$
 * @spring.bean id = "nu.mine.kino.utils.db.FKChecker"
 */
public class OracleFKCheckerImpl implements FKChecker {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(OracleFKCheckerImpl.class);

    private DataSource dataSource;

    private final String SELECT_SQL = "select TABLE_NAME,CONSTRAINT_NAME,STATUS from USER_CONSTRAINTS where CONSTRAINT_TYPE =\'R\'";

    private final String SELECT_SQL2 = "select TABLE_NAME,CONSTRAINT_NAME,STATUS from USER_CONSTRAINTS where CONSTRAINT_TYPE =\'R\' and TABLE_NAME=?";

    public boolean exist(String tableName, String fkName) {
        JdbcTemplate jt = new JdbcTemplate(dataSource);
        // SQL�����s���āA�f�[�^�擾�BList����Map���Row��s�B
        List<Map<String, String>> tableList = jt.queryForList(SELECT_SQL);

        // null�Ƃ��A�T�C�Y0��������
        if (tableList == null || tableList.size() <= 0) {
            return false;
        }

        // �e�s��TABLE_NAME�J�����ƈ����̃e�[�u�����̔�r�B
        for (Map<String, String> map : tableList) {
            String dbTableName = map.get("TABLE_NAME");

            // �����̃e�[�u�����Ɠ����e�[�u��������������AFK�����r
            if (dbTableName.equals(tableName)) {
                String dbFkName = map.get("CONSTRAINT_NAME");
                if (dbFkName.equals(fkName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Map<String, String>> findAll() {
        return new JdbcTemplate(dataSource).queryForList(SELECT_SQL);
    }

    public List<String> findFKNameByTableName(String tableName) {
        List<Map<String, String>> tableList = new JdbcTemplate(dataSource)
                .queryForList(SELECT_SQL2, new Object[] { tableName });
        List<String> ans = new ArrayList<String>();
        // null�Ƃ��A�T�C�Y0��������
        if (tableList == null || tableList.size() <= 0) {
            return ans;
        }

        for (Map<String, String> map : tableList) {
            ans.add(map.get("CONSTRAINT_NAME"));
        }
        return ans;
    }

    /**
     * @param dataSource
     *            dataSource ��ݒ�B
     * @spring.property ref = "dataSource"
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Map<String, String>> findRecordByTableName(String tableName) {
        return new JdbcTemplate(dataSource).queryForList(SELECT_SQL2,
                new Object[] { tableName });
    }
}
