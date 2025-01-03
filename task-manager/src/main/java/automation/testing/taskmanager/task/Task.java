package automation.testing.taskmanager.task;

import java.time.LocalDateTime;

public class Task {

	private Long id;
	private String description;
	private boolean isCompleted;
	private LocalDateTime createdOn;
	private Long userId;
	
	public Task(String description, boolean isCompleted, Long userId) {
		this.description = description;
		this.isCompleted = isCompleted;
		this.userId = userId;
	}
	
	public Task() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
