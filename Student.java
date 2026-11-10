import java.io.Serializable;

/**
 * Model class. Implements Serializable (so it can be saved to a file)
 * and Comparable (natural ordering = highest percentage first).
 */
public class Student implements Serializable, Comparable<Student> {
    private static final long serialVersionUID = 1L;

    private final int rollNo;
    private String name;
    private String department;
    private double[] marks;

    public Student(int rollNo, String name, String department, double[] marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.department = department;
        this.marks = marks;
    }

    public int getRollNo()        { return rollNo; }
    public String getName()       { return name; }
    public String getDepartment() { return department; }
    public double[] getMarks()    { return marks; }

    public void setMarks(double[] marks) { this.marks = marks; }

    public double getTotal() {
        double sum = 0;
        for (double m : marks) sum += m;
        return sum;
    }

    public double getPercentage() {
        return marks.length == 0 ? 0 : getTotal() / marks.length;
    }

    public String getGrade() {
        double p = getPercentage();
        if (p >= 90) return "O";
        if (p >= 80) return "A+";
        if (p >= 70) return "A";
        if (p >= 60) return "B";
        if (p >= 50) return "C";
        return "F";
    }

    /** Natural ordering: higher percentage first; roll number breaks ties
     *  so that TreeSet does not treat equal-percentage students as duplicates. */
    @Override
    public int compareTo(Student other) {
        int result = Double.compare(other.getPercentage(), this.getPercentage());
        return (result != 0) ? result : Integer.compare(this.rollNo, other.rollNo);
    }

    @Override
    public String toString() {
        return String.format("%-8d %-18s %-8s %8.1f %8.2f%%  %-3s",
                rollNo, name, department, getTotal(), getPercentage(), getGrade());
    }
}
