package automation.testing.taskmanager.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDBStorage {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void saveUser(User user) {

		StringBuilder query = new StringBuilder();

		query.append("INSERT INTO \"USER\" (first_name, last_name, email, created_on) ").append("VALUES ('")
				.append(user.getFirstName()).append("', '").append(user.getLastName()).append("', '")
				.append(user.getEmail()).append("', '").append(user.getCreatedOn().toString()).append("')");

		jdbcTemplate.execute(query.toString());
	}

	public List<User> getAllUsers() {

		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM \"USER\"");

		return jdbcTemplate.query(query.toString(), (RowMapper<User>) (rs, rowNum) -> {
			return mapUser(rs);
		});
	}

	public User getUserById(Long id) {

		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM \"USER\" WHERE id = ").append(id);
		
		try {
			return jdbcTemplate.queryForObject(query.toString(), (RowMapper<User>) (rs, rowNum) -> {
				return mapUser(rs);
			});
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public User getUserByEmail(String email) {

		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM \"USER\" WHERE email = '").append(email).append("'");;
		
		try {
			return jdbcTemplate.queryForObject(query.toString(), (RowMapper<User>) (rs, rowNum) -> {
				return mapUser(rs);
			});
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	private User mapUser(ResultSet rs) throws SQLException {
	    User user = new User();
	    user.setId(rs.getLong("id"));
	    user.setFirstName(rs.getString("first_name"));
	    user.setLastName(rs.getString("last_name"));
	    user.setEmail(rs.getString("email"));
	    user.setCreatedOn(rs.getTimestamp("created_on").toLocalDateTime());
	    return user;
	}
	
	public void deleteUserById(Long userId) {
	    StringBuilder query = new StringBuilder();
	    query.append("DELETE FROM \"USER\" WHERE id = ").append(userId);
	    jdbcTemplate.update(query.toString());
	}
	
	public void deleteAllUsers() {
	    StringBuilder query = new StringBuilder();
	    query.append("DELETE FROM \"USER\"");
	    jdbcTemplate.execute(query.toString());
	}
}
