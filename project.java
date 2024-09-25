import java.util.*;

class User {
    private String username;
    private String password;
    private String role; // "Student" or "Instructor"

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public boolean login(String password) {
        return this.password.equals(password);
    }

    public String getProfileDetails() {
        return username + " (" + role + ")";
    }
}

class Student extends User {
    private List<Course> enrolledCourses;

    public Student(String username, String password) {
        super(username, password, "Student");
        enrolledCourses = new ArrayList<>();
    }

    public void enrollCourse(Course course) {
        enrolledCourses.add(course);
    }

    public void viewCourses() {
        for (Course course : enrolledCourses) {
            System.out.println(course.getCourseDetails());
        }
    }
}

class Instructor extends User {
    private List<Course> createdCourses;

    public Instructor(String username, String password) {
        super(username, password, "Instructor");
        createdCourses = new ArrayList<>();
    }

    public void createCourse(Course course) {
        createdCourses.add(course);
    }
}

class Course {
    private String courseId;
    private String title;
    private String description;
    private List<Assignment> assignments;
    private List<Student> enrolledStudents;

    public Course(String courseId, String title, String description) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        assignments = new ArrayList<>();
        enrolledStudents = new ArrayList<>();
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    public String getCourseDetails() {
        return title + ": " + description;
    }
}

class Assignment {
    private String assignmentId;
    private String title;
    private String description;

    public Assignment(String assignmentId, String title, String description) {
        this.assignmentId = assignmentId;
        this.title = title;
        this.description = description;
    }

    public String getAssignmentDetails() {
        return title + ": " + description;
    }
}

public class Main {
    private static List<User> users = new ArrayList<>();
    private static List<Course> courses = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Sample data for quick testing
        Instructor instructor1 = new Instructor("john_doe", "password123");
        Student student1 = new Student("jane_smith", "pass456");
        users.add(instructor1);
        users.add(student1);

        // Adding a sample course
        Course javaCourse = new Course("C101", "Java Programming", "Learn the basics of Java.");
        courses.add(javaCourse);
        instructor1.createCourse(javaCourse);

        // Simple login flow
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User loggedInUser = authenticate(username, password);
        if (loggedInUser != null) {
            System.out.println("Login successful! Welcome, " + loggedInUser.getProfileDetails());
            if (loggedInUser instanceof Student) {
                studentMenu((Student) loggedInUser);
            } else if (loggedInUser instanceof Instructor) {
                instructorMenu((Instructor) loggedInUser);
            }
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private static User authenticate(String username, String password) {
        for (User user : users) {
            if (user.getProfileDetails().contains(username) && user.login(password)) {
                return user;
            }
        }
        return null;
    }

    private static void studentMenu(Student student) {
        System.out.println("You are a student. Enrolling in the Java course...");
        student.enrollCourse(courses.get(0));  // Automatically enroll in the first course for simplicity
        System.out.println("Courses enrolled:");
        student.viewCourses();
    }

    private static void instructorMenu(Instructor instructor) {
        System.out.println("You are an instructor. Viewing your created courses...");
        instructor.createCourse(courses.get(0));  // Simple: view or re-create the first course
        System.out.println("Course created: " + courses.get(0).getCourseDetails());
    }
}
