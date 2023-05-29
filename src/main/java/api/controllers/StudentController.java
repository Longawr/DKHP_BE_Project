package api.controllers;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.DAO.AccountDAO;
import api.DAO.StudentDAO;
import api.POJO.Account;
import api.POJO.Student;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentDAO stDAO;
    @Autowired
    private AccountDAO aDAO;

    @GetMapping("/id/{studentID}")
    public ResponseEntity<Student> getStudentByID(@PathVariable String studentID, HttpServletRequest request) {
        String uname = GetUsernameByToken.getusername(request);

        Student user = stDAO.getStudentByUsername(uname);
        Student std = stDAO.getStudentByID(studentID);
        if(std != null) {
            std.getAccountByAccount().setPassword("");

            if (Objects.equals(user.getAccountByAccount().getRole(), "SV")
                    && !Objects.equals(user.getId(), studentID)) {
                Student response = new Student();
                response.setId("cút đi tml");
                return ResponseEntity.status(FORBIDDEN).body(response);
            }
        }
        return ResponseEntity.ok().body(std);
    }

    @GetMapping("/name/{username}")
    public ResponseEntity<Student> getStudentByUsername(@PathVariable String username, HttpServletRequest request) {
        String uname = GetUsernameByToken.getusername(request);

        Account user = aDAO.getAccountByID(uname);
        Student std = stDAO.getStudentByUsername(username);
        if(std != null) {
            std.getAccountByAccount().setPassword("");

            if (Objects.equals(user.getRole(), "SV")
                    && !Objects.equals(user.getAccountId(), username)) {
                Student response = new Student();
                response.setId("cút đi tml");
                return ResponseEntity.status(FORBIDDEN).body(response);
            }
        }
        return ResponseEntity.ok().body(std);
    }

    @PostMapping("/delete/{studentID}")
    public boolean removeStudentByID(@PathVariable String studentID) {
        return stDAO.removeStudentByID(studentID);
    }

    @PostMapping("/save")
    public boolean addStudent(@RequestBody Student st) {
        return stDAO.addStudent(st);
    }

    @PostMapping("/update")
    public void updateStudent(@RequestBody Student st, HttpServletRequest request) {
        String uname = GetUsernameByToken.getusername(request);
        Student user = stDAO.getStudentByUsername(uname);
        if (Objects.equals(user.getAccountByAccount().getRole(), "SV")
                && !Objects.equals(user.getId(), st.getId())) {
            return;
        }
        stDAO.updateStudent(st);
    }

    @GetMapping("/classid/{classId}")
    public List<Student> getStudentListByClass(@PathVariable String classId) {
        return stDAO.getStudentListByClass(classId);
    }

}
