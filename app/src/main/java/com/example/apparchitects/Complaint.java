package com.example.apparchitects;

public class Complaint {
    protected int id;
    protected String roomNumber;
    protected String description;
    protected String status;

    public Complaint(int id, String roomNumber, String description, String status ) {
        this.id=id;
        this.roomNumber = roomNumber;
        this.description = description;
        this.status = status;
    }

    public int getid() {
        return id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status =status;
    }

}

