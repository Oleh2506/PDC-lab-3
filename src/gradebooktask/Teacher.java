package gradebooktask;

import java.util.Random;

public class Teacher {
    private final String name;

    public Teacher(String name) {
        this.name = name;
    }

    public void enterGrade(GradeBook gradeBook, String student, int grade) {
         if (gradeBook.getAssistant() == this || gradeBook.getLecturer() == this) {
             gradeBook.setGrade(student, grade);
         };
    }

    public void fillOutGradeBook(GradeBook gradeBook) {
        Random random = new Random();
        final int MAX_GRADE = 100, MIN_GRADE = 60;
        for (int i = 0; i < gradeBook.getStudentCount(); i++) {
            this.enterGrade(gradeBook, "Student " + (i + 1), random.nextInt(MAX_GRADE - MIN_GRADE + 1) + MIN_GRADE);
        }
    }

    public String toString() {
        return name;
    }
}
