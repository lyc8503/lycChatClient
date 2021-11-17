package socket;

public class PublicMessage {
	public String byuser;
	public String message;
	public String date;
	public String email;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getByuser() {
		return byuser;
	}
	public void setByuser(String byuser) {
		this.byuser = byuser;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
