package com.marchenko.clearsolutiontest.service;

import com.marchenko.clearsolutiontest.model.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    User createUser (User user);

    User updateUser (Long id, User user);

    User updateSomeFieldsUser (Long id, User user);

    void deleteUserById (Long id);

    List<User> getUsers ();

    User getUserById(Long id);

    List<User> findUsersByBirthDateRange(LocalDate from, LocalDate to);

}
