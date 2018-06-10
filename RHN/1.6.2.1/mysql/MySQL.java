package net.polishgames.rhenowar.util.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import net.polishgames.rhenowar.util.Password;
import net.polishgames.rhenowar.util.RhenowarObject;
import net.polishgames.rhenowar.util.Util;

public final class MySQL extends RhenowarObject implements AutoCloseable
{
	
	protected static final Map<ConnectionData, Connection> connections = new HashMap<ConnectionData, Connection>();
	
	private final Connection connection;
	private final Statement statementBasic;
	
	public MySQL(final ConnectionData data) throws SQLException
	{
		if(MySQL.connections.containsKey(Util.nonNull(data)))
		{
			this.connection = MySQL.connections.get(data);
			this.statementBasic = null;
		}
		else if(data.hasURL() && data.hasUser() && data.hasPassword() && !data.getURL().startsWith("jdbc:sqlite:"))
		{
			String url = data.getURL();
			final String[] urlSplit = url.split("://");
			if(url.startsWith("jdbc:") && urlSplit.length > 0)
				url = data.getURL().split("://")[1];
			this.connection = DriverManager.getConnection("jdbc:mysql://" + (data.hasDBName() ? url.split("/")[0] : url) + "?zeroDateTimeBehavior=convertToNull&useSSL=false", data.getUser(), data.getPassword().toString());
			MySQL.connections.put(data, this.giveConnection());
			if(data.hasDBName())
			{
				this.statementBasic = this.giveStatement();
				this.doExecute("CREATE DATABASE IF NOT EXISTS `" + data.getDBName() + "`;");
				this.doExecute("USE " + data.getDBName() + ";");
			}
			else
				this.statementBasic = null;
		}
		else
		{
			Util.doThrowIAE();
			this.connection = null;
			this.statementBasic = null;
		}
	}
	
	public MySQL(final String url, final String user, final Password password, final String dbname) throws SQLException
	{
		this(new ConnectionData(url, user, password, dbname));
	}
	
	public MySQL(final String url, final String user, final Password password) throws SQLException
	{
		this(new ConnectionData(url, user, password));
	}
	
	private ResultSet result;
	private volatile boolean closed;
	
	public final Connection giveConnection()
	{
		return Util.nonNull(this.connection);
	}
	
	public final Statement giveStatementBasic()
	{
		return this.statementBasic;
	}
	
	public final Statement giveStatement() throws SQLException
	{
		if(this.isClosed())
			return null;
		return this.giveConnection().createStatement();
	}
	
	public final PreparedStatement giveStatementPrepare(final String command) throws SQLException
	{
		if(this.isClosed())
			return null;
		return this.giveConnection().prepareStatement(Util.nonEmpty(command));
	}
	
	public final PreparedStatement giveStatementPrepare(final Query query) throws SQLException
	{
		if(this.isClosed())
			return null;
		return this.giveStatementPrepare(Util.nonNull(query).giveCommand());
	}
	
	public final ResultSet giveResult()
	{
		final ResultSet result = this.result;
		this.result = null;
		return result;
	}
	
	public final List<List<Value<?>>> giveResultList(final ResultSet result, Argument... columns) throws SQLException
	{
		if(this.isClosed() | Util.nonNull(result).isClosed())
			return null;
		final List<List<Value<?>>> list = new ArrayList<List<Value<?>>>();
		if(columns.length <= 0)
		{
			final ResultSetMetaData metadata = result.getMetaData();
			columns = new Argument[metadata.getColumnCount()];
			for(int i = 0; i < columns.length; i++)
			{
				final int column = i + 1;
				columns[i] = new Argument(metadata.getColumnLabel(column), TypeData.valueOf(metadata.getColumnType(column)));
			}
		}
		while(result.next())
		{
			final List<Value<?>> values = new ArrayList<Value<?>>();
			for(int i = 0; i < columns.length; i++)
			{
				final String name = columns[i].giveName();
				values.add(Value.valueOf(name, result.getObject(name)));
			}
			if(values.size() > 0)
				list.add(values);
		}
		result.close();
		return list;
	}
	
	public final List<List<Value<?>>> giveResultList(final ResultSet result, final List<Argument> columns) throws SQLException
	{
		return this.giveResultList(result, Util.nonNull(columns).toArray(new Argument[columns.size()]));
	}
	
	public final List<List<Value<?>>> giveResultList(final Argument... columns) throws SQLException
	{
		if(this.hasResult())
			return this.giveResultList(this.giveResult(), columns);
		else
			return null;
	}
	
	public final List<List<Value<?>>> giveResultList(final List<Argument> columns) throws SQLException
	{
		return this.giveResultList(Util.nonNull(columns).toArray(new Argument[columns.size()]));
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("connection", this.giveConnection());
		map.put("statement", this.giveStatementBasic());
		map.put("closed", this.closed);
		map.put("result", this.result);
		return map;
	}
	
	public final boolean hasResult()
	{
		return this.result != null;
	}
	
	public final boolean hasStatementBasic()
	{
		return this.giveStatementBasic() != null;
	}
	
	public final MySQL doExecute(final StatementType type, final String command) throws SQLException
	{
		if(this.isClosed() || !this.hasStatementBasic())
			return null;
		else
			return this.doExecuteStatement(this.giveStatementBasic(), type, command);
	}
	
	public final MySQL doExecute(final Query query) throws SQLException
	{
		if(this.isClosed() || !this.hasStatementBasic())
			return null;
		else
			return this.doExecuteStatement(this.giveStatementBasic(), query);
	}
	
	public final MySQL doExecute(final boolean select, final String command) throws SQLException
	{
		if(this.isClosed() || !this.hasStatementBasic())
			return null;
		else
			return this.doExecuteStatement(this.giveStatementBasic(), select, command);
	}
	
	public final MySQL doExecute(final String command) throws SQLException
	{
		if(this.isClosed() || !this.hasStatementBasic())
			return null;
		else
			return this.doExecuteStatement(this.giveStatementBasic(), command);
	}
	
	public final MySQL doExecuteStatement(final Statement statement, final StatementType type, final String command) throws SQLException
	{
		if(this.isClosed())
			return null;
		if(Util.nonNull(statement) instanceof PreparedStatement)
			return this.doExecuteStatementPrepare((PreparedStatement) statement, type);
		else
		{
			Util.nonEmpty(command);
			if(Util.nonNull(type) == StatementType.SELECT)
				this.result = statement.executeQuery(command);
			else
				statement.executeUpdate(command);
			return this;
		}
	}
	
	public final MySQL doExecuteStatement(final Statement statement, final Query query) throws SQLException
	{
		if(this.isClosed())
			return null;
		if(Util.nonNull(statement) instanceof PreparedStatement)
			return this.doExecuteStatementPrepare((PreparedStatement) statement, query);
		else
			return this.doExecuteStatement(statement, Util.nonNull(query).giveType(), query.giveCommand());
	}
	
	public final MySQL doExecuteStatement(final Statement statement, final boolean select, final String command) throws SQLException
	{
		if(this.isClosed())
			return null;
		if(Util.nonNull(statement) instanceof PreparedStatement)
			return this.doExecuteStatementPrepare((PreparedStatement) statement, select);
		else
			return this.doExecuteStatement(statement, select ? StatementType.SELECT : StatementType.UPDATE, command);
	}
	
	public final MySQL doExecuteStatement(final Statement statement, final String command) throws SQLException
	{
		if(this.isClosed())
			return null;
		if(Util.nonNull(statement) instanceof PreparedStatement)
			return this.doExecuteStatementPrepare((PreparedStatement) statement);
		else
		{
			statement.execute(Util.nonEmpty(command));
			return this;
		}
	}
	
	public final MySQL doExecuteStatementPrepare(final PreparedStatement statement, final StatementType type) throws SQLException
	{
		if(this.isClosed())
			return null;
		Util.nonNull(statement);
		if(Util.nonNull(type) == StatementType.SELECT)
			this.result = statement.executeQuery();
		else
			statement.executeUpdate();
		return this;
	}
	
	public final MySQL doExecuteStatementPrepare(final PreparedStatement statement, final Query query) throws SQLException
	{
		if(this.isClosed())
			return null;
		return this.doExecuteStatementPrepare(statement, Util.nonNull(query).giveType());
	}
	
	public final MySQL doExecuteStatementPrepare(final PreparedStatement statement, final boolean select) throws SQLException
	{
		if(this.isClosed())
			return null;
		return this.doExecuteStatementPrepare(statement, select ? StatementType.SELECT : StatementType.UPDATE);
	}
	
	public final MySQL doExecuteStatementPrepare(final PreparedStatement statement) throws SQLException
	{
		if(this.isClosed())
			return null;
		Util.nonNull(statement).execute();
		return this;
	}

	@Override
	public final void close() throws SQLException
	{
		if(!this.closed)
		{
			if(this.hasResult())
				this.giveResult().close();
			if(this.hasStatementBasic())
				this.close(this.giveStatementBasic());
			if(!this.giveConnection().isClosed())
				this.giveConnection().close();
			for(final ConnectionData data : new HashSet<ConnectionData>(MySQL.connections.keySet()))
				if(this.giveConnection().equals(Util.nonNull(MySQL.connections.get(data))))
					MySQL.connections.remove(data);
			this.result = null;
			this.closed = true;
		}
	}
	
	public final void close(final Statement statement) throws SQLException
	{
		if(!(this.isClosed() | Util.nonNull(statement).isClosed()))
			statement.close();
	}
	
	public final boolean isClosed() throws SQLException
	{
		return this.closed || this.giveConnection().isClosed();
	}
	
}
