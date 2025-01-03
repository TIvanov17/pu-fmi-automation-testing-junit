package automation.testing.taskmanager.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskDBStorage {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void saveTask(Task task) {

		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO task (description, is_completed, created_on, user_id) ")
				.append("VALUES ('")
				.append(task.getDescription()).append("', '")
				.append(task.isCompleted()).append("', '")
				.append(task.getCreatedOn().toString())
				.append("', ")
				.append(task.getUserId())
				.append(")");

		jdbcTemplate.execute(query.toString());
	}

	public List<Task> getAllTasks() {

		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM task");

		return jdbcTemplate.query(query.toString(), (RowMapper<Task>) (rs, rowNum) -> {

			Task task = new Task();
			task.setId(rs.getLong("id"));
			task.setDescription(rs.getString("description"));
			task.setIsCompleted(rs.getBoolean("is_completed"));
			task.setCreatedOn(rs.getTimestamp("created_on").toLocalDateTime());
			task.setUserId(rs.getLong("user_id"));

			return task;
		});
	}

	public Task getTaskById(Long id) {

		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM task WHERE id = ").append(id);
		
		try {
			return jdbcTemplate.queryForObject(query.toString(), (RowMapper<Task>) (rs, rowNum) -> {
				Task task = new Task();
				task.setId(rs.getLong("id"));
				task.setDescription(rs.getString("description"));
				task.setIsCompleted(rs.getBoolean("is_completed"));
				task.setCreatedOn(rs.getTimestamp("created_on").toLocalDateTime());
				task.setUserId(rs.getLong("user_id"));
				return task;
			});
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public void deleteAllTasks() {
	    StringBuilder query = new StringBuilder();
	    query.append("DELETE FROM task");
	    jdbcTemplate.update(query.toString());
	}
	
}
