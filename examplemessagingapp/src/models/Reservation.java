package models;

public class Reservation {
	private int roomNumber, startTimestamp, endTimestamp;
	
	public Reservation(int roomNumber, int startTimestamp, int endTimestamp)
	{
		this.roomNumber = roomNumber;
		this.startTimestamp = startTimestamp;
		this.endTimestamp = endTimestamp;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public int getStartTimestamp() {
		return startTimestamp;
	}

	public void setStartTimestamp(int startTimestamp) {
		this.startTimestamp = startTimestamp;
	}

	public int getEndTimestamp() {
		return endTimestamp;
	}

	public void setEndTimestamp(int endTimestamp) {
		this.endTimestamp = endTimestamp;
	}
}
