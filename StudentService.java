import java.util.*;

/**
 * Business logic.
 *  - ArrayList : master list of all students (insertion order)
 *  - HashMap   : roll number -> Student for fast lookup
 *  - TreeSet   : built on demand to produce sorted rank lists
 */
public class StudentService {
    private final ArrayList<Student> students = new ArrayList<>();
    private final HashMap<Integer, Student> studentMap = new HashMap<>();

    public boolean addStudent(Student s) {
        if (studentMap.containsKey(s.getRollNo())) {
            return false;                       // duplicate roll number
        }
        students.add(s);
        studentMap.put(s.getRollNo(), s);
        return true;
    }

    public Student searchByRollNo(int rollNo) {
        return studentMap.get(rollNo);          // O(1) on average
    }

    public boolean updateMarks(int rollNo, double[] newMarks) {
        Student s = studentMap.get(rollNo);
        if (s == null) return false;
        s.setMarks(newMarks);
        return true;
    }

    public boolean deleteStudent(int rollNo) {
        Student s = studentMap.remove(rollNo);
        return s != null && students.remove(s);
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public int count() {
        return students.size();
    }

    /** Rank list using the natural ordering (Comparable). */
    public TreeSet<Student> getRankList() {
        return new TreeSet<>(students);
    }

    /** Rank list using any Comparator. */
    public TreeSet<Student> getRankList(Comparator<Student> criteria) {
        TreeSet<Student> ranked = new TreeSet<>(criteria);
        ranked.addAll(students);
        return ranked;
    }

    /** Replace all data (used after loading from file). */
    public void setStudents(List<Student> loaded) {
        students.clear();
        studentMap.clear();
        for (Student s : loaded) addStudent(s);
    }
}
