package com.hostel;

public class Allotment {

    private int id;
    private int studentId;
    private int roomId;
    private int bedNumber;
    private AllotmentStatus status;

    public Allotment(int id, int studentId, int roomId, int bedNumber,
                     AllotmentStatus status) {
        this.id = id;
        this.studentId = studentId;
        this.roomId = roomId;
        this.bedNumber = bedNumber;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getBedNumber() {
        return bedNumber;
    }

    public AllotmentStatus getStatus() {
        return status;
    }

    public void setStatus(AllotmentStatus status) {
        this.status = status;
    }
}