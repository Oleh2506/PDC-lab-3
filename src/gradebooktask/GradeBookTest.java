package gradebooktask;

import java.util.ArrayList;

public class GradeBookTest {
    public static void main(String[] args) {
        final int ASSISTANT_GRADES_PER_STUDENT_COUNT = 8;
        final int LECTURER_GRADES_PER_STUDENT_COUNT = 2;

        Teacher lecturer = new Teacher("Lecturer 1");
        Teacher[] assistants = new Teacher[]{
                new Teacher("Assistant 1"),
                new Teacher("Assistant 2"),
                new Teacher("Assistant 3")
        };

        GradeBook[] gradeBooks = new GradeBook[]{
                new GradeBook("IP-11", lecturer, assistants[0], 11),
                new GradeBook("IP-12", lecturer, assistants[1], 12),
                new GradeBook("IP-13", lecturer, assistants[2], 13),
        };

        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < gradeBooks.length; i++) {
            int gradeBookIndex = i;
            for (int j = 0; j < ASSISTANT_GRADES_PER_STUDENT_COUNT; j++) {
                threads.add(new Thread(() -> assistants[gradeBookIndex].fillOutGradeBook(gradeBooks[gradeBookIndex])));
            }

            for (int j = 0; j < LECTURER_GRADES_PER_STUDENT_COUNT; j++) {
                threads.add(new Thread(() -> lecturer.fillOutGradeBook(gradeBooks[gradeBookIndex])));
            }
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException ignored) {
            }
        }

        for (GradeBook gb : gradeBooks) {
            gb.printGradeBook();
            System.out.println();
        }
    }
}
