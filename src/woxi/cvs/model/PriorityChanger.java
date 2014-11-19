package woxi.cvs.model;

import java.io.Serializable;

/**
 * Class declared to load columns data during updation of priority logic
 */
public class PriorityChanger implements Serializable {

	private static final long serialVersionUID = 1L;
	private int task_id;
	private int priority_id;
	private String priority;
	private String priority_timestamp;

	
	public PriorityChanger() {
		super();
	}

	public PriorityChanger(int task_id, int priority_id, String priority,
			String priority_timestamp) {
		super();
		this.task_id = task_id;
		this.priority_id = priority_id;
		this.priority = priority;
		this.priority_timestamp = priority_timestamp;
	}

	public int getTask_id() {
		return task_id;
	}

	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}

	public int getPriority_id() {
		return priority_id;
	}

	public void setPriority_id(int priority_id) {
		this.priority_id = priority_id;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getPriority_timestamp() {
		return priority_timestamp;
	}

	public void setPriority_timestamp(String priority_timestamp) {
		this.priority_timestamp = priority_timestamp;
	}

}
