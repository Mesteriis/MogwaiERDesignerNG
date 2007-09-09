package de.mogwai.erdesignerng.dialect.mysql;

import java.sql.Connection;
import java.sql.SQLException;

import de.mogwai.erdesignerng.dialect.Dialect;
import de.mogwai.erdesignerng.dialect.JDBCReverseEngineeringStrategy;
import de.mogwai.erdesignerng.exception.ReverseEngineeringException;
import de.mogwai.erdesignerng.model.Model;

/**
 * @author $Author: mirkosertic $
 * @version $Date: 2007-07-08 10:29:38 $
 */
public class MySQLReverseEngineeringStrategy extends
		JDBCReverseEngineeringStrategy {

	public MySQLReverseEngineeringStrategy(Dialect aDialect) {
		super(aDialect);
	}

	/**
	 * Reverse engineer the existing schemas.
	 * 
	 * @param aModel
	 * @param aConnection
	 * @throws SQLException
	 * @throws ReverseEngineeringException
	 */
	protected void reverseEngineerSchemas(Model aModel, String aSchemaName,
			Connection aConnection) throws SQLException,
			ReverseEngineeringException {

		reverseEnginnerTables(aModel, null, aConnection);
	}
}