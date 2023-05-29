package api.controllers;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.DAO.AccountDAO;
import api.DAO.CourseDAO;
import api.DAO.CourseattendDAO;
import api.DAO.StudentDAO;
import api.POJO.Account;
import api.POJO.Course;
import api.POJO.Courseattend;
import api.POJO.Currentsemester;
import api.POJO.Semester;
import api.POJO.Student;

@RestController
@RequestMapping("/courseattends")
public class CourseattendController {

    @Autowired
    private CourseattendDAO caDAO;

    @Autowired
    private CourseDAO cDAO;
    @Autowired
    private AccountDAO aDAO;
    @Autowired
    private StudentDAO stDAO;

    @PostMapping("/get/{studentId}")
    public List<Courseattend> getCourseattendList(@PathVariable String studentId, @RequestBody Semester sem) {
        return caDAO.getCourseattendList(studentId, sem);
    }
    @GetMapping("/countbysemester/{studentId}")
    public int getCourseattendListCount(@PathVariable String studentId, @RequestBody Currentsemester sem) {
        return caDAO.getCourseattendListCount(studentId, sem);
    }

    @GetMapping("/countall/studentid/{studentId}")
    public int getCourseattendListCountByStudent(@PathVariable String studentId) {
        return caDAO.getCourseattendListCountByStudent(studentId);
    }

    @GetMapping("/id/{studentId}/{courseId}")
    public Courseattend getCourseattendByID(@PathVariable String studentId, @PathVariable int courseId) {
        return caDAO.getCourseattendByID(studentId, courseId);
    }

    @GetMapping("/courseid/{courseId}")
    public List<Courseattend> getCourseattendByCourseID(@PathVariable int courseId) {
        return caDAO.getCourseattendByCourseID(courseId);
    }

    @PostMapping("/delete/{studentId}/{id}")
    public void removeCourseattendByID(@PathVariable String studentId, @PathVariable int id, HttpServletRequest request) {
        String uname = GetUsernameByToken.getusername(request);
        Account user = aDAO.getAccountByID(uname);
        if (Objects.equals(user.getRole(), "SV")
                && !Objects.equals(studentId, uname)) {
            return;
        }
        caDAO.removeCourseattendByID(studentId, id);
        Course updateCourse = cDAO.getCourseByID(id);
        updateCourse.setRegisterSlot(cDAO.getCourseStudentCount(updateCourse.getId()));
        cDAO.updateCourse(updateCourse);
    }

    @PostMapping("/save")
    public void addCourseattend(@RequestBody Courseattend courseattend, HttpServletRequest request) {
        String uname = GetUsernameByToken.getusername(request);
        Student std = stDAO.getStudentByUsername(uname);

        if (Objects.equals(std.getAccountByAccount().getRole(), "SV")
            && !Objects.equals(courseattend.getStudentId(), std.getId())) {
            return;
        }
        caDAO.addCourseattend(courseattend);
        Course updateCourse = cDAO.getCourseByID(courseattend.getCourseId());
        updateCourse.setRegisterSlot(cDAO.getCourseStudentCount(updateCourse.getId()));
        cDAO.updateCourse(updateCourse);
    }


}
