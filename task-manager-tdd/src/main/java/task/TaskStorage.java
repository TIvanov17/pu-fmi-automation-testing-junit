package task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.GeneralApplicationException;
import user.User;

public class TaskStorage {

	private Map<String, List<Task>> storage;
	
	public TaskStorage() {
		storage = new HashMap<>();
	}
	
	public void registerUser(User user) {
		storage.put(user.getEmail(), new ArrayList<>());
	}
	
	public boolean existUser(User user) {
		return storage.containsKey(user.getEmail());
	}
	
	public void addTaskToUser(User user, Task task) {
		
		List<Task> tasks = getTasks(user);
		// няма такъв user
		if(tasks == null) {
			throw new GeneralApplicationException(
					"Потребителят не е регистриран"
			);
		}
	
		tasks.add(task);
		storage.put(user.getEmail(), tasks);
	}
	
	public List<Task> getTasks(User user) {
		List<Task> tasks = storage.get(user.getEmail());
		return tasks;
	}
}
