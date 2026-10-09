package com.hostel;

import java.util.ArrayList;
import java.util.HashSet;

public class HostelService {
    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Room> rooms = new ArrayList<>();
	private ArrayList<Allotment> allotments = new ArrayList<>();
    private HashSet<String> roomNumbers = new HashSet<>();
    
    public HostelService() {
        loadStudents();
        loadRooms();
        loadAllotments();
    }



	public void addStudent(Student student) {
        students.add(student);
        saveStudents();
    }
    
    public void addRoom(Room room) {
    	String roomNumber = room.getRoomNumber().trim().toUpperCase(java.util.Locale.ROOT);
        if (roomNumbers.contains(roomNumber)) {
            System.out.println("Room already exists.");
            return;
        }

        rooms.add(room);
        roomNumbers.add(roomNumber);
        saveRooms();
        System.out.println("Room added successfully.");
    }

    public void showStudents() {

        if (students.isEmpty()) {
            System.out.println("No records found.");
            return;
        }

        for (Student student : students) {
            student.showProfile();
            System.out.println();
        }
    }

    public void showRooms() {

        if (rooms.isEmpty()) {
            System.out.println("No records found.");
            return;
        }

        for (Room room : rooms) {

            System.out.println("Room ID: " + room.getId());
            System.out.println("Room Number: " + room.getRoomNumber());

            int[] occupants = new int[4];

            for (Allotment allotment : allotments) {

                if (allotment.getRoomId() == room.getId()
                        && allotment.getStatus() == AllotmentStatus.ACTIVE) {

                    int bedIndex = allotment.getBedNumber() - 1;
                    occupants[bedIndex] = allotment.getStudentId();
                }
            }

            for (int i = 0; i < occupants.length; i++) {

                if (occupants[i] == 0) {
                    System.out.println("Bed " + (i + 1) + ": Empty");
                } else {
                    System.out.println("Bed " + (i + 1)
                            + ": Student ID " + occupants[i]);
                }
            }

            System.out.println();
        }
    }

public void allotBed(int studentId, int roomId) {

    boolean studentExists = false;
    boolean roomExists = false;

    for (Student student : students) {
        if (student.getId() == studentId) {
            studentExists = true;
            break;
        }
    }

    for (Room room : rooms) {
        if (room.getId() == roomId) {
            roomExists = true;
            break;
        }
    }

    if (!studentExists) {
        System.out.println("Student not found.");
        return;
    }

    if (!roomExists) {
        System.out.println("Room not found.");
        return;
    }

    for (Allotment allotment : allotments) {
        if (allotment.getStudentId() == studentId
                && allotment.getStatus() == AllotmentStatus.ACTIVE) {

            System.out.println("Student already has an active allotment.");
            return;
        }
    }

    int[] occupants = new int[4];

    for (Allotment allotment : allotments) {

        if (allotment.getRoomId() == roomId
                && allotment.getStatus() == AllotmentStatus.ACTIVE) {

            int bedIndex = allotment.getBedNumber() - 1;
            occupants[bedIndex] = allotment.getStudentId();
        }
    }

    int freeBed = -1;

    for (int i = 0; i < occupants.length; i++) {

        if (occupants[i] == 0) {
            freeBed = i + 1;
            break;
        }
    }

    if (freeBed == -1) {
        System.out.println("Room is full.");
        return;
    }

    int allotmentId = allotments.size() + 1;

    Allotment allotment = new Allotment(
            allotmentId,
            studentId,
            roomId,
            freeBed,
            AllotmentStatus.ACTIVE
    );

    allotments.add(allotment);
    saveAllotments();
    System.out.println("Bed allotted successfully.");
    System.out.println("Bed Number: " + freeBed);
}



public void checkout(int studentId) {

    for (Allotment allotment : allotments) {

        if (allotment.getStudentId() == studentId
                && allotment.getStatus() == AllotmentStatus.ACTIVE) {

        	allotment.setStatus(AllotmentStatus.CHECKED_OUT);

        	saveAllotments();

        	System.out.println("Checkout recorded successfully.");
            return;
        }
    }

    System.out.println("No active allotment found for this student.");
}

public void showStudentProfile(int studentId) {

    for (Student student : students) {

        if (student.getId() == studentId) {
            student.showProfile();
            return;
        }
    }

    System.out.println("Student not found.");
}

public void showActiveAllotment(int studentId) {

    for (Allotment allotment : allotments) {

        if (allotment.getStudentId() == studentId
                && allotment.getStatus() == AllotmentStatus.ACTIVE) {

            System.out.println("Student ID: " + allotment.getStudentId());
            System.out.println("Room ID: " + allotment.getRoomId());
            System.out.println("Bed Number: " + allotment.getBedNumber());
            System.out.println("Status: " + allotment.getStatus());

            return;
        }
    }

    System.out.println("No active allotment found.");
}


public void showReport() {

    int totalRooms = rooms.size();
    int totalBeds = totalRooms * 4;

    int occupiedBeds = 0;
    int activeStudents = 0;

    for (Allotment allotment : allotments) {

        if (allotment.getStatus() == AllotmentStatus.ACTIVE) {
            occupiedBeds++;
            activeStudents++;
        }
    }

    int freeBeds = totalBeds - occupiedBeds;

    String report =
            "===== HOSTEL OCCUPANCY REPORT =====\n" +
            "Total Rooms: " + totalRooms + "\n" +
            "Total Beds: " + totalBeds + "\n" +
            "Occupied Beds: " + occupiedBeds + "\n" +
            "Free Beds: " + freeBeds + "\n" +
            "Students with Active Allotment: " + activeStudents + "\n";

    System.out.println(report);

    FileManager.writeToFile("data/report.txt", report);
}

public void saveStudents() {

    StringBuilder data = new StringBuilder();

    for (Student student : students) {

        data.append(student.getId())
            .append("|")
            .append(student.getName())
            .append("|")
            .append(student.getDepartment())
            .append("\n");
    }

    FileManager.writeToFile("data/students.txt", data.toString());
}

public int getNextStudentId() {
    return students.size() + 1;
}

public int getNextRoomId() {
    return rooms.size() + 1;
}

public void saveRooms() {
    StringBuilder data = new StringBuilder();

    for (Room room : rooms) {
        data.append(room.getId())
            .append("|")
            .append(room.getRoomNumber())
            .append("\n");
    }

    FileManager.writeToFile("data/rooms.txt", data.toString());
}

public void saveAllotments() {
    StringBuilder data = new StringBuilder();

    for (Allotment allotment : allotments) {
        data.append(allotment.getId())
            .append("|")
            .append(allotment.getStudentId())
            .append("|")
            .append(allotment.getRoomId())
            .append("|")
            .append(allotment.getBedNumber())
            .append("|")
            .append(allotment.getStatus())
            .append("\n");
    }

    FileManager.writeToFile("data/allotments.txt", data.toString());
}

public void loadStudents() {
    try {
        java.io.BufferedReader reader =
                new java.io.BufferedReader(
                        new java.io.FileReader("data/students.txt"));

        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\|");

            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            String department = parts[2];

            students.add(new Student(id, name, department));
        }

        reader.close();

    } catch (java.io.IOException e) {
        // File may not exist on the first run.
    }
}

public void loadRooms() {
    try {
        java.io.BufferedReader reader =
                new java.io.BufferedReader(
                        new java.io.FileReader("data/rooms.txt"));

        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\|");

            int id = Integer.parseInt(parts[0]);
            String roomNumber = parts[1];

            rooms.add(new Room(id, roomNumber));
            roomNumbers.add(roomNumber);
        }

        reader.close();

    } catch (java.io.IOException e) {
        // File may not exist on the first run.
    }
}

public void loadAllotments() {
    try {
        java.io.BufferedReader reader =
                new java.io.BufferedReader(
                        new java.io.FileReader("data/allotments.txt"));

        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\|");

            int id = Integer.parseInt(parts[0]);
            int studentId = Integer.parseInt(parts[1]);
            int roomId = Integer.parseInt(parts[2]);
            int bedNumber = Integer.parseInt(parts[3]);
            AllotmentStatus status =
                    AllotmentStatus.valueOf(parts[4]);

            allotments.add(new Allotment(
                    id,
                    studentId,
                    roomId,
                    bedNumber,
                    status
            ));
        }

        reader.close();

    } catch (java.io.IOException e) {
        // File may not exist on the first run.
    }
}
}