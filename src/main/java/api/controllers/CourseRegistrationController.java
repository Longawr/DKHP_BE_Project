package api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api.DAO.CourseRegistrationDAO;
import api.POJO.CourseRegistration;

@RestController
@RequestMapping("/courseregistrations")
public class CourseRegistrationController {

    @Autowired
    private CourseRegistrationDAO crgDAO;

    @GetMapping
    public List<CourseRegistration> getCourseRegistrationList(){
        return crgDAO.getCourseRegistrationList();
    }

    @GetMapping("/id")
    public CourseRegistration getCourseRegistrationByID(@RequestParam int id, @RequestParam int semesterId, @RequestParam int year){
        return crgDAO.getCourseRegistrationByID(id, semesterId, year);
    }

    @GetMapping("/semester")
    public CourseRegistration getCourseRegistrationBySemester(@RequestParam int semesterId, @RequestParam int year) {
        return crgDAO.getCourseRegistrationBySemester(semesterId, year);
    }

    @PostMapping("/delete")
    public boolean removeCourseRegistrationByID(@RequestParam int id, @RequestParam int semesterId, @RequestParam int year) {
        return crgDAO.removeCourseRegistrationByID(id, semesterId, year);
    }

    @PostMapping("/save")
    public boolean addCourseRegistration(@RequestBody CourseRegistration temp) {
        return crgDAO.addCourseRegistration(temp);
    }
}
