/*******************************************************************************
 * Copyright (c) 2006 Masatomi KINO.
 * All rights reserved. 
 * $Id$
 *******************************************************************************/
//�쐬��: 2006/12/17
package nu.mine.kino.dbunit.ant;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.dataset.excel.XlsDataSet;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class ExcelExportTask extends Task {
    private String driver;

    private String url;

    private String userId;

    private String password;

    private String schema;

    private Export export;

    public Export createExport() {
        if (export == null) {
            export = new Export();
        }
        return export;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.tools.ant.Task#execute()
     */
    public void execute() throws BuildException {
        IDatabaseConnection con = null;
        try {
            con = getConnection();
            IDataSet dataset = con.createDataSet();
            ITableIterator iterator = dataset.iterator();
            System.out.println("-------------------");
            while (iterator.next()) {
                System.out.println(iterator.getTableMetaData().getTableName());
            }
            System.out.println("-------------------");
            XlsDataSet.write(dataset, new FileOutputStream(export.getDest()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BuildException(e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private IDatabaseConnection getConnection() throws Exception {
        System.out.println("driver   : " + driver);
        System.out.println("url      : " + url);
        System.out.println("userId   : " + userId);
        System.out.println("password : " + password);
        System.out.println("schema   : " + schema);
        System.out.println("export   : " + new File(export.getDest()).getAbsolutePath());
        Class driverClass = Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, userId,
                password);
        return new DatabaseConnection(connection, schema);
    }

    /**
     * @param driver
     *            driver ��ݒ�B
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * @param export
     *            export ��ݒ�B
     */
    public void setExport(Export export) {
        this.export = export;
    }

    /**
     * @param password
     *            password ��ݒ�B
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param schema
     *            schema ��ݒ�B
     */
    public void setSchema(String schema) {
        this.schema = schema;
    }

    /**
     * @param url
     *            url ��ݒ�B
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @param userId
     *            userId ��ݒ�B
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
