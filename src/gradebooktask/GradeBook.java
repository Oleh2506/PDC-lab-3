package gradebooktask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GradeBook {
    private final String groupName;
    private final Teacher lecturer;
    private final Teacher assistant;
    private final Map<String, ArrayList<Integer>> gradesPerStudent;

    public GradeBook(String groupName, Teacher lecturer, Teacher assistant, int numOfStudents) {
        this.groupName = groupName;
        this.lecturer = lecturer;
        this.assistant = assistant;

        this.gradesPerStudent = new HashMap<>();
        for (int i = 0; i < numOfStudents; i++) {
            this.gradesPerStudent.put("Student " + (i + 1), new ArrayList<>());
        }
    }

    public void setGrade(String student, int grade) {
        ArrayList<Integer> grades = gradesPerStudent.get(student);
        synchronized (gradesPerStudent) {
            grades.add(grade);
        }
    }

    public void setGradeNaive(String student, int grade) {
        ArrayList<Integer> grades = gradesPerStudent.get(student);
        grades.add(grade);
    }

    public void printGradeBook() {
        System.out.format("Grade Book: %s; Lecturer: %s; Assistant: %s.\n", groupName, lecturer.toString(), assistant.toString());
        for (Map.Entry<String, ArrayList<Integer>> entry : gradesPerStudent.entrySet()) {
            System.out.println(entry.getKey() + "\t(" + entry.getValue().size() + " entries)\t:\t" + entry.getValue());
        }
    }

    public Teacher getLecturer() {
        return lecturer;
    }

    public Teacher getAssistant() {
        return assistant;
    }

    public int getStudentCount() {
        return gradesPerStudent.size();
    }
}
