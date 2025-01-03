package automation.testing.taskmanager.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class CommentDBStorage {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void saveComment(Comment comment) {

		StringBuilder query = new StringBuilder();
        query.append("INSERT INTO comment (content, created_on, updated_on, task_id, user_id) VALUES ('")
             .append(comment.getContent()).append("', '")
             .append(comment.getCreatedOn()).append("', '")
             .append(comment.getUpdatedOn()).append("', ")
             .append(comment.getTaskId()).append(", ")
             .append(comment.getUserId()).append(")");
        
		jdbcTemplate.execute(query.toString());
	}
	
	public List<Comment> getCommentsByTaskId(Long taskId) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM comment WHERE task_id = ").append(taskId);

        return jdbcTemplate.query(query.toString(), (rs, rowNum) -> {
        	Comment comment = new Comment();
            comment.setId(rs.getLong("id"));
            comment.setContent(rs.getString("content"));
            comment.setCreatedOn(rs.getTimestamp("created_on").toLocalDateTime());
            comment.setUpdatedOn(rs.getTimestamp("updated_on").toLocalDateTime());
            comment.setUserId(rs.getLong("user_id"));
            comment.setTaskId(rs.getLong("task_id"));
            return comment;
        });
    }
	
	public void deleteCommentById(Long commentId) {
	    StringBuilder query = new StringBuilder();
	    query.append("DELETE FROM comment WHERE id = ").append(commentId);
	    jdbcTemplate.update(query.toString());
	}
}
