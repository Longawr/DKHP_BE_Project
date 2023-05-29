package api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.DAO.TeacherDAO;
import api.POJO.Teacher;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherDAO tDAO;

    @GetMapping
    public List<Teacher> getTeacherList() {
        return tDAO.getTeacherList();
    }

    @GetMapping("/id/{teacherID}")
    public Teacher getTeacherByID(@PathVariable String teacherID) {
        return tDAO.getTeacherByID(teacherID);
    }

    @PostMapping("/delete/{teacherID}")
    public boolean removeTeacherByID(@PathVariable String teacherID) {
        return tDAO.removeTeacherByID(teacherID);
    }

    @PostMapping("/save")
    public boolean addTeacher(@RequestBody Teacher tch) {
        return tDAO.addTeacher(tch);
    }

    @PutMapping
    public void updateTeacher(@RequestBody Teacher tch) {
        tDAO.updateTeacher(tch);
    }

    @GetMapping("/username/{username}")
    public Teacher getTeacherByUsername(@PathVariable String username) {
        return tDAO.getTeacherByUsername(username);
    }
}
