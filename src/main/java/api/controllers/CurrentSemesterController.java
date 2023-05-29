package api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.DAO.CurrentSemesterDAO;
import api.POJO.Currentsemester;

@RestController
@RequestMapping("/currentsemester")
public class CurrentSemesterController {

    @Autowired
    private CurrentSemesterDAO csDAO;

    @GetMapping
    public Currentsemester getCurrentSemester(){
        return csDAO.getCurrentSemester();
    }

    @PostMapping
    public void addCurrrentSemester(@RequestBody Currentsemester sem){
        csDAO.addCurrrentSemester(sem);
    }

    @DeleteMapping
    public void removeCurrrentSemester(){
        csDAO.removeCurrrentSemester();
    }
}
