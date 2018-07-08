package domain;

public class UserStatus {
	String userStatusId;
	String userStatus;
	
	public UserStatus() {
		super();
	}
	
	public UserStatus(String userStatusId, String userStatus) /*throws IdException*/ {
		super();
	
        /* Don't see the point of this and it breaks get all for the service
        if(userStatusId.length() < 7)
	           throw new IdException("Id can't be less than 7 characters");
        */
        
		this.userStatusId = userStatusId;
		this.userStatus = userStatus;
		
	}
	
	public String getUserStatusId() {
		return userStatusId;
	}
	public void setUserStatusId(String userStatusId) throws IdException {
        if(userStatusId.length() < 7)
	           throw new IdException("Id can't be less than 7 characters");
        
		this.userStatusId = userStatusId;
	}
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	@Override
	public String toString() {
		return userStatus;
	}
}
