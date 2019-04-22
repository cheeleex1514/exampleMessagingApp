package models;

public class GuestClient {
	private String firstName, lastName, companyName;
	private int roomNumber;
	
	// private constructor for builder pattern
	private GuestClient() {}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	/* Inner class */
	public static class Builder{
		private String firstName, lastName, companyName;
		private int roomNumber;
		private GuestClient guest;
		
		public Builder() {}

		public Builder forGuestFirstName(String firstName) {
			this.firstName = firstName;
			
			return this;
		}

		public Builder withLastName(String lastName) {
			this.lastName = lastName;
			
			return this;
		}

		public Builder fromCompany(String companyName) {
			this.companyName = companyName;
			
			return this;
		}

		public Builder stayingAtRoomNumber(int roomNumber) {
			this.roomNumber = roomNumber;
			
			return this;
		}
		
		public GuestClient build() {
			this.guest = new GuestClient();
			this.guest.firstName = this.firstName;
			this.guest.lastName = this.lastName;
			this.guest.companyName = this.companyName;
			this.guest.roomNumber = this.roomNumber;

			return this.guest;
		}		
	}
}
