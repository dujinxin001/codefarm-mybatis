/*
 *    Copyright 2010-2011 The myBatis Team
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.codefarm.mybatis.shard.transaction;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.transaction.Transaction;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.Assert;

/**
 * {@code SpringManagedTransaction} handles the lifecycle of a JDBC connection.
 * It retrieves a connection from Spring's transaction manager and returns it
 * back to it when it is no longer needed.
 * <p>
 * If Spring's transaction handling is active it will no-op all
 * commit/rollback/close calls assuming that the Spring transaction manager will
 * do the job.
 * <p>
 * If it is not it will behave like {@code JdbcTransaction}.
 * 
 * @version $Id: SpringManagedTransaction.java 4052 2011-11-25 21:47:27Z
 *          eduardo.macarron $
 */
public class ShardManagedTransaction implements Transaction
{
    
    private final Log logger = LogFactory.getLog(getClass());
    
    private final DataSource dataSource;
    
    private Connection connection;
    
    public ShardManagedTransaction(DataSource dataSource)
    {
        Assert.notNull(dataSource, "No DataSource specified");
        this.dataSource = dataSource;
    }
    
    public Connection getConnection() throws SQLException
    {
        if (this.connection == null)
        {
            openConnection();
        }
        return this.connection;
    }
    
    private void openConnection() throws SQLException
    {
        connection = ShardManagedTransactionManager.getConnection(dataSource);
        if (connection == null)
        {
            throw new SQLException("Cannot get Connection From DataSource!!");
        }
    }
    
    public void commit() throws SQLException
    {
        if (this.connection != null)
        {
            if (this.logger.isDebugEnabled())
            {
                this.logger.debug("Committing JDBC Connection ["
                        + this.connection + "]");
            }
            this.connection.commit();
        }
    }
    
    public void rollback() throws SQLException
    {
        if (this.connection != null)
        {
            if (this.logger.isDebugEnabled())
            {
                this.logger.debug("Rolling back JDBC Connection ["
                        + this.connection + "]");
            }
            this.connection.rollback();
        }
    }
    
    public void close() throws SQLException
    {
        DataSourceUtils.releaseConnection(connection, dataSource);//ShardManagedTransactionManager.closeConnection(dataSource);
    }
    
}
