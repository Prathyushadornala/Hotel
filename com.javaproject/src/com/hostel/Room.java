package com.hostel;

public class Room {

    private int id;
    private String roomNumber;

    public Room(int id, String roomNumber) {
        this.id = id;
        this.roomNumber = roomNumber;
    }

    public int getId() {
        return id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void showDetails() {
        System.out.println("Room ID: " + id);
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Beds: 4");
    }
}