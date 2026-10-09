package com.hostel;

import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static HostelService service = new HostelService();

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== HOSTEL ROOM ALLOTMENT SYSTEM =====");
            System.out.println("1. Register student");
            System.out.println("2. Admin menu");
            System.out.println("3. Student menu");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    registerStudent();
                    break;

                case "2":
                    showAdminMenu();
                    break;

                case "3":
                    showStudentMenu();
                    break;

                case "0":
                    System.out.println("Exiting application...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void registerStudent() {

    	System.out.print("Enter student name: ");
    	String name = scanner.nextLine();

    	System.out.print("Enter department: ");
    	String department = scanner.nextLine();

    	int id = service.getNextStudentId();

        Student student = new Student(id, name, department);

        service.addStudent(student);

        System.out.println("Student added successfully.");
    }

    public static void showAdminMenu() {

        while (true) {

            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. Add room");
            System.out.println("2. View rooms and beds");
            System.out.println("3. View students");
            System.out.println("4. Allot bed");
            System.out.println("5. Record checkout");
            System.out.println("6. View reports");
            System.out.println("0. Back");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    addRoom();
                    break;

                case "2":
                    service.showRooms();
                    break;

                case "3":
                    service.showStudents();
                    break;

                case "4":
                    allotBed();
                    break;

                case "5":
                    checkout();
                    break;

                case "6":
                    service.showReport();
                    break;

                case "0":
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void checkout() {

        System.out.print("Enter student ID: ");
        int studentId = Integer.parseInt(scanner.nextLine());

        service.checkout(studentId);
    }

	private static void allotBed() {
		// TODO Auto-generated method stub
    	System.out.print("Enter student ID: ");
        int studentId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter room ID: ");
        int roomId = Integer.parseInt(scanner.nextLine());

        service.allotBed(studentId, roomId);
    }
		

	public static void addRoom() {
	    System.out.print("Enter room number: ");
	    String roomNumber = scanner.nextLine();
	    int id = service.getNextRoomId();
	    Room room = new Room(id, roomNumber);
	    service.addRoom(room);
	}

    public static void showStudentMenu() {

        System.out.println("\n===== STUDENT MENU =====");

        System.out.print("Enter student ID: ");
        int studentId = Integer.parseInt(scanner.nextLine());
        System.out.println("Student ID: " + studentId);
        System.out.println("1. View my profile");
        System.out.println("2. View rooms");
        System.out.println("3. View my active allotment");
        System.out.println("0. Back");

        while (true) {

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {

            case "1":
                viewMyProfile(studentId);
                break;

                case "2":
                    service.showRooms();
                    break;

                case "3":
                    viewMyActiveAllotment(studentId);
                    break;

                case "0":
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void viewMyActiveAllotment(int studentId) {

        service.showActiveAllotment(studentId);
    }

	public static void viewMyProfile(int studentId) {

        service.showStudentProfile(studentId);
    }
}