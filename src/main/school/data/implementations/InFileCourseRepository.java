package main.school.data.implementations;

import main.school.data.DataException;
import main.school.data.abstractions.CourseRepository;
import main.school.model.Course;
import main.school.model.Level;
import main.school.model.Sector;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class InFileCourseRepository implements CourseRepository{
    private static long courseId;
    private static final int  idPosition = 0;
    private static final int  titlePosition = 1;
    private static final int  hoursPosition = 2;
    private static final int  sectorPosition = 3;
    private static final int  levelPosition = 4;
    private static final String  delimiter = ",";
    private File file;
    private String path;
    public InFileCourseRepository(String path){

        this.file = new File(path);
        this.path = path;
    }
    @Override
    public void addCourse(Course course) throws DataException {
        course.setId(++courseId);
        String courseInstance = course.getId() + "," + course.getTitle() + "," + course.getHours() + "," + course.getSector() + "," + course.getLevel()+ "\n";
        try {
            Files.write(Paths.get(this.path), courseInstance.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new DataException(e.getMessage(), e);
        }
    }

    @Override
    public List<Course> getCoursesByTitleLike(String title) throws DataException {
         try ( FileReader fr = new FileReader(file);
              BufferedReader br = new BufferedReader(fr))
        {
            String line = "";
            String[] tempArray;
            List<Course> courses = new ArrayList<>();

            while((line = br.readLine()) != null) {
                tempArray = line.split(delimiter);
                if(tempArray[titlePosition].contains(title)){
                   Course course =new Course(tempArray[titlePosition], Integer.parseInt(tempArray[hoursPosition]), Sector.valueOf(tempArray[sectorPosition]), Level.valueOf(tempArray[levelPosition]));
                   course.setId(Integer.parseInt(tempArray[idPosition]));
                   courses.add(course);
                }
            }
            return courses;
        } catch (FileNotFoundException e) {
            throw new DataException(e.getMessage(), e);
        } catch (IOException e) {
            throw new DataException(e.getMessage(), e);
        }

    }

    @Override
    public List<Course> getAllCourses() throws DataException {
        try ( FileReader fr = new FileReader(file);
              BufferedReader br = new BufferedReader(fr))
        {   String line = "";
            String[] tempArray;
            List<Course> courses = new ArrayList<>();

            while((line = br.readLine()) != null) {
                tempArray = line.split(delimiter);
                Course course =new Course(tempArray[titlePosition], Integer.parseInt(tempArray[hoursPosition]), Sector.valueOf(tempArray[sectorPosition]), Level.valueOf(tempArray[levelPosition]));
                course.setId(Integer.parseInt(tempArray[idPosition]));
                courses.add(course);

            }
            return courses;
         }catch (FileNotFoundException e) {
              throw new DataException(e.getMessage(), e);
         }catch (IOException e) {
              throw new DataException(e.getMessage(), e);
        }
    }
}
