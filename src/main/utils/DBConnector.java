package utils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This interface is responsible for providing a connection to the database
 * 
 * @author Filip K.
 */
public interface DBConnector {

	/**
	 * This method provides connection to databases
	 * @return 
	 * 		Connection or null if unable to connect to the database
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */

	Connection getConnection() throws SQLException, ClassNotFoundException;
	
	/**
	 * Close connection
	 * @throws SQLException
	 */
	void closeConnection() throws SQLException;
}
