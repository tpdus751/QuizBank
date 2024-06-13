package quizbank.model;

public class User {
	
	private String name;
	private String id;
	private String password;
	private String phoneNumber;
	private String address;
	private String email;
	
	
	
	public User(String name, String id, String password, String phoneNumber, String address, String email) {
		this.name = name;
		this.id = id;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.email = email;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}
	protected void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
