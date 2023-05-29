/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.controllers;

import static org.springframework.http.HttpStatus.FORBIDDEN;

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
import api.POJO.Account;

/**
 *
 * @author aliez
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountDAO aDAO;

    @GetMapping("/id/{username}")
    public ResponseEntity<Account> getByID(@PathVariable String username, HttpServletRequest request){
        String uname = GetUsernameByToken.getusername(request);

        Account user = aDAO.getAccountByID(uname);
        user.setPassword("");
        Account acc = aDAO.getAccountByID(username);
        acc.setPassword("");

        if (Objects.equals(user.getRole(), "SV") && !Objects.equals(user.getAccountId(), username)){
            user.setAccountId("Cút đi tml");
            return ResponseEntity.status(FORBIDDEN).body(user);
        }
        return ResponseEntity.ok().body(acc);
    }

    @PostMapping("/save")
    public boolean saveAccount(@RequestBody Account account){
        return aDAO.addAccount(account);
    }

    @PostMapping("/update")
    public boolean updateAccount(@RequestBody Account account, HttpServletRequest request){
        String uname = GetUsernameByToken.getusername(request);
        Account user = aDAO.getAccountByID(uname);

        if (Objects.equals(user.getRole(), "SV")
                && Objects.equals(account.getAccountId(), uname)){
            return aDAO.updateAccount(account);
        }
        return false;
    }

    @PostMapping("/delete/{name}")
    public boolean deleteAccount(@PathVariable String name){
        return aDAO.removeAccountByID(name);
    }
}
