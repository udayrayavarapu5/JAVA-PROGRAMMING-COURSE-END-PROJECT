# College Student Record Management and Ranking System
JAVA PROGRAMMING (22ITC02N) - Course-End Project

A console application that stores student records and ranks them.

| Requirement | Where it is used |
|---|---|
| ArrayList | `StudentService.students` - master list |
| HashMap | `StudentService.studentMap` - search by roll number |
| TreeSet | `StudentService.getRankList(...)` - sorted rank lists |
| Comparable | `Student.compareTo` - rank by percentage |
| Comparator | `Main` - rank by name / roll number / total marks |
| Serialization + File I/O | `FileHandler` - saves to `students.dat` |

## Run (JDK 8 or later)
```
javac *.java
java Main
```
Records are saved to `students.dat` in the folder you run from, and loaded automatically next time.
