package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import model.Player;

/**
 * This repository class allows make CRUD operations on database
 * @author Filip K.
 */
public class Repository {

	private static final String INSERT = "INSERT INTO Players(login, password) VALUES (?, ?)";
	private static final String SELECT = "SELECT * FROM Players WHERE login LIKE ?";
	private static final String UPDATE = "UPDATE Players SET points = ? WHERE login = ?";
	private static final String DELETE = "DELETE FROM Players WHERE login = ?";
	
	private static final String COLUMN_LOGIN = "login";
	private static final String COLUMN_PASSWORD = "password";
	private static final String COLUMN_POINTS = "points";
	
	private DBConnector connector;
	
	public Repository(DBConnector dbConnector) {
		connector = dbConnector;
	}
	
	/**
	 * Save the given Player
	 * @param player
	 * 			must not be null
	 * @return
	 * 		true if Player saved in database, false otherwise
	 */
	public boolean savePlayer(Player player) {
		if(player == null)
			return false;
		boolean isInserted = false;
		
		try(Connection connection = connector.getConnection(); 
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
			
			preparedStatement.setString(1, player.getLogin());
			preparedStatement.setString(2, player.getPassword());
			isInserted = preparedStatement.executeUpdate() > 0 ? true : false;
			
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return isInserted;
	}
	
	/**
	 * Retrieves Player by login
	 * @param login
	 * 			must not be null
	 * @return
	 * 		Optional which holds the value
	 */
	public Optional<Player> findPlayer(String login) {
		Player player = null;
		
		try(Connection connection = connector.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT)) {
			
			preparedStatement.setString(1, login);
			ResultSet resultSet = preparedStatement.executeQuery();
			player = getPlayer(resultSet);
			
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(player);
	}
	
	/**
	 * Update player points
	 * @param points
	 * 			must be greater than 0
	 * @param login
	 * 			must not be null
	 */
	public boolean updatePoints(int points, String login) {
		if(points < 0 || login == null)
			return false;
		boolean isUpdated = false;
		try(Connection connection = connector.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
			
			preparedStatement.setInt(1, points);
			preparedStatement.setString(2, login);
			isUpdated = preparedStatement.executeUpdate() > 0 ? true : false;
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return isUpdated;
	}
	
	/**
	 * Delete player form database
	 * @param login
	 * 			must not be null
	 */
	public void deletePlayer(String login) {
		try(Connection connection = connector.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
			
			preparedStatement.setString(1, login);
			preparedStatement.executeUpdate();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	private Player getPlayer(ResultSet resultSet) throws SQLException {
		while (resultSet.next()) {
			String login = resultSet.getString(COLUMN_LOGIN);
			String password = resultSet.getString(COLUMN_PASSWORD);
			int points = Integer.parseInt(resultSet.getString(COLUMN_POINTS));
			return new Player(login, password, points);
		}
		return null;
	}
	
}
