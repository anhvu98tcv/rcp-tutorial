package rcptutorial;

import java.security.Timestamp;

public class EmployeeEntity {

	private String id;
    private String name;
    private String email;
    private Timestamp dateOfBirth;
    private Timestamp joinedDate;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Timestamp getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Timestamp dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Timestamp getJoinedDate() {
		return joinedDate;
	}
	public void setJoinedDate(Timestamp joinedDate) {
		this.joinedDate = joinedDate;
	}
    
    
    
}
