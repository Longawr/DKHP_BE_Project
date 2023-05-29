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

import api.DAO.SubjectDAO;
import api.POJO.Subject;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    @Autowired
    private SubjectDAO sjDAO;

    @GetMapping
    public List<Subject> getSubjectList() {
        return sjDAO.getSubjectList();
    }

    @GetMapping("/id/{subjectID}")
    public Subject getSubjectByID(@PathVariable String subjectID) {
        return sjDAO.getSubjectByID(subjectID);
    }

    @PostMapping("/delete/{subjectID}")
    public boolean removeSubjectByID(@PathVariable String subjectID) {
        return sjDAO.removeSubjectByID(subjectID);
    }

    @PostMapping("/save")
    public boolean addSubject(@RequestBody Subject sub) {
        return sjDAO.addSubject(sub);
    }

    @PutMapping
    public void updateSubject(Subject sub) {
        sjDAO.updateSubject(sub);
    }
}
