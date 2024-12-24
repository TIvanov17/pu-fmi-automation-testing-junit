package task;

public class Task {

	private String description;
	private boolean isCompleted;
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setIsCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public boolean getIsCompleted() {
		return this.isCompleted;
	}
}
