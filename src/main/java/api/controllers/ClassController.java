package api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.DAO.ClassDAO;
import api.POJO.Classname;

@RestController
@RequestMapping("/classes")
public class ClassController {

    @Autowired
    private ClassDAO cDAO;

    @GetMapping
    public List<Classname> getAll(){return cDAO.getClassList();}

    @GetMapping("/total/{id}")
    public Long getTotalCount(@PathVariable String id){
        return cDAO.getClassMemberCount(id);
    }

    @GetMapping("/male/{id}")
    public Long getMaleCount(@PathVariable String id){
        return cDAO.getClassMaleCount(id);
    }

    @GetMapping("/female/{id}")
    public Long getFemaleCount(@PathVariable String id){
        return cDAO.getClassFemaleCount(id);
    }

    @PostMapping("/save")
    public boolean save(@RequestBody Classname cls){
        return cDAO.addClass(cls);
    }

    @PostMapping("/update")
    public void update(@RequestBody Classname cls){
        cDAO.updateClass(cls);
    }

    @PostMapping("/delete/{id}")
    public boolean delete(@PathVariable String id){
        return cDAO.removeClassByID(id);
    }
}
