package api;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import api.DAO.AccountDAO;
import api.DAO.CurrentSemesterDAO;
import api.DAO.SemesterDAO;
import api.DAO.TeacherDAO;
import api.POJO.Account;
import api.POJO.Currentsemester;
import api.POJO.Semester;
import api.POJO.Teacher;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run (AccountDAO aDAO, TeacherDAO tDAO, SemesterDAO sDAO, CurrentSemesterDAO csDAO){
		return args -> {
			if(aDAO.getAccountList().isEmpty()){
				Account acc = new Account("admin", "nimda", "GV");
				aDAO.addAccount(acc);
				tDAO.addTeacher(new Teacher("admin", "Admin", "Admin", "Nam", acc));
				sDAO.addSemester(new Semester(2, 2021, Date.valueOf(
						LocalDate.of(2022, 2, 15))
						, Date.valueOf(LocalDate.of(2022, 6, 15))));
				csDAO.addCurrrentSemester(new Currentsemester(2, 2021));
			}
		};
	}
}

