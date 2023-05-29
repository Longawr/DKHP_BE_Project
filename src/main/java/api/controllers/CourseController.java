package api.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api.DAO.CourseDAO;
import api.POJO.Course;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseDAO crDAO;
    @GetMapping("/semester")
    public List<Course> getCourseListBySemester(@RequestParam int id, @RequestParam int year){
        return crDAO.getCourseListBySemester(id, year);
    }

    @GetMapping("/studentcount/{id}")
    public int getCourseStudentCount(@PathVariable int id){
        return crDAO.getCourseStudentCount(id);
    }

    @PostMapping("/delete/{id}")
    public boolean removeCourseByID(@PathVariable int id){
        return crDAO.removeCourseByID(id);
    }

    @PostMapping("/save")
    public void addCourse(@RequestBody Course temp){
        crDAO.addCourse(temp);
    }

    @PostMapping("/update")
    public void updateCourse(@RequestBody Course temp, HttpServletRequest request){
        crDAO.updateCourse(temp);
    }

    @GetMapping("/subjectid/{subjectId}")
    public Course getUniqueCourseBySubject(@PathVariable String subjectId){
        return crDAO.getUniqueCourseBySubject(subjectId);
    }
}
