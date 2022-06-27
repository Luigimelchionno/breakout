package main.school;

import main.school.data.DataException;
import main.school.data.abstractions.CourseRepository;
import main.school.data.abstractions.EditionRepository;
import main.school.data.abstractions.InstructorRepository;
import main.school.data.implementations.InFileCourseRepository;
import main.school.data.implementations.InMemoryCourseRepository;
import main.school.data.implementations.InMemoryEditionRepository;
import main.school.data.implementations.InMemoryInstructorRepository;
import main.school.model.*;
import main.school.services.AbstractSchoolService;
import main.school.services.SchoolService;
import main.school.ui.Console;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Program {

    public static void main(String[] args) {

        //dobbiamo capirne il perchè di Dependency Injection!!!

        //CourseRepository cr = new InMemoryCourseRepository();

        CourseRepository cr = new InFileCourseRepository("/home/luigi/IdeaProjects/breakout1/src/main/school/data/implementations/file.csv");

        EditionRepository er = new InMemoryEditionRepository();
        InstructorRepository ir = new InMemoryInstructorRepository();
        AbstractSchoolService asc = new SchoolService(cr, er, ir);

        Course c1 = new Course("ciccio",120, Sector.GRAPHICS, Level.ADVANCED);
        Course c2 = new Course("pippo",200, Sector.OFFICE, Level.GURU);

        Instructor i1 = new Instructor("caio", "asdknais",
                                        LocalDate.of(1995,02,25),
                                        "huhisa", new ArrayList<Sector>(List.of(Sector.OFFICE, Sector.GRAPHICS)));

        Edition e1 = new Edition(0L, c1, LocalDate.of(1975,05,20),
                                    LocalDate.of(2000,05,20),
                                   500.0, i1);

        try {
            cr.addCourse(c1);
            cr.addCourse(c2);

            ir.addInstructor(i1);

            er.addCourseEdition(e1);

        } catch (DataException e) {
            System.out.println("Unable to initialize data.");
            System.out.println(e.getMessage());
            return;
        }

        Console c = new Console(asc);
        c.start();
    }
}
