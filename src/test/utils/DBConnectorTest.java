package utils;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class DBConnectorTest {

	private DBConnector dbConnector;
	private Connection connection;
	
	@Before
	public void before() {
		dbConnector = new MySQLConnector();
	}
	
	@Test
	public void connectionWasMade() throws ClassNotFoundException, SQLException {
		connection = dbConnector.getConnection();
		assertNotNull(connection);
	}

}
