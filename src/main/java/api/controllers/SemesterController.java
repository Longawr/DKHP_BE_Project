package api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api.DAO.SemesterDAO;
import api.POJO.Semester;

@RestController
@RequestMapping("/semesters")
public class SemesterController {

    @Autowired
    private SemesterDAO sDAO;

    @GetMapping
    public List<Semester> getSemesterList(){
        return sDAO.getSemesterList();
    }

    @GetMapping("/id")
    public Semester getSemesterByID(@RequestParam int year, @RequestParam int id){
        return sDAO.getSemesterByID(year,id);
    }

    @PostMapping("/delete")
    public boolean removeSemesterByID(@RequestParam int year, @RequestParam int id) {
        return sDAO.removeSemesterByID(year,id);
    }

    @PostMapping("/save")
    public boolean addSemester(@RequestBody Semester sem) {
        return sDAO.addSemester(sem);
    }
}
