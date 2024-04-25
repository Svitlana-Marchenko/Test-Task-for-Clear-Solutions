package com.marchenko.clearsolutiontest.service;

import com.marchenko.clearsolutiontest.exceptions.NotFoundException;
import com.marchenko.clearsolutiontest.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    //Use of database is not necessary => this list is just imitated real data base
    private List<User> userList;
    public UserServiceImpl() {
        userList = new ArrayList<>();

        User user1 = new User(1L,"email1@email.com", "Anton", "Smith", LocalDate.of(2000, 12, 30), "Kyiv city", "+380912345678");
        User user2 = new User(2L,"email2@email.com", "Andrew", "Kondratenko", LocalDate.of(2002, 11, 3), "Lviv city", "+380002345678");
        User user3 = new User(3L,"email3@email.com", "Hanna", "Vovk", LocalDate.of(2004, 5, 25), "Kharkiv city", "+380012345678");
        User user4 = new User(4L,"email4@email.com", "Peter", "Levchenko", LocalDate.of(1995, 12, 1), "Kyiv city", "+380012345000");
        User user5 = new User(5L,"email5@email.com", "Jolanda", "Henhridy", LocalDate.of(2004, 2, 25), "Berlin city", "+380000000000");

        userList.addAll(Arrays.asList(user1,user2,user3,user4,user5));
    }


    @Override
    public User createUser(User user) {
        user.setId((long) LocalDateTime.now().getNano());
        userList.add(user);
        return user;
    }

    @Override
    public User updateUser(Long id, User user) {
        Optional<User> existingUserOptional = userList.stream().filter(u -> u.getId().equals(id)).findFirst();
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setEmail(user.getEmail());
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setBirthDate(user.getBirthDate());
            existingUser.setAddress(user.getAddress());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            return existingUser;
        } else {
            throw new NotFoundException("User not found with id: " + id);
        }
    }

    @Override
    public User updateSomeFieldsUser(Long id, User user) {
        Optional<User> existingUserOptional = userList.stream().filter(u -> u.getId().equals(id)).findFirst();
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            if (user.getEmail() != null) {
                existingUser.setEmail(user.getEmail());
            }
            if (user.getFirstName() != null) {
                existingUser.setFirstName(user.getFirstName());
            }
            if (user.getLastName() != null) {
                existingUser.setLastName(user.getLastName());
            }
            if (user.getBirthDate() != null) {
                existingUser.setBirthDate(user.getBirthDate());
            }
            if (user.getAddress() != null) {
                existingUser.setAddress(user.getAddress());
            }
            if (user.getPhoneNumber() != null) {
                existingUser.setPhoneNumber(user.getPhoneNumber());
            }
            return existingUser;
        } else {
            throw new NotFoundException("User not found with id: " + id);
        }
    }

    @Override
    public void deleteUserById(Long id) {
        boolean removed = userList.removeIf(user -> user.getId().equals(id));
        if (!removed) {
            throw new NotFoundException("User with id: " + id + " not found");
        }
    }

    @Override
    public List<User> getUsers() {
        return userList;
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> userOptional = userList.stream().filter(user -> user.getId().equals(id)).findFirst();
        return userOptional.orElseThrow(() -> new NotFoundException("User with id: " + id + " not found"));
    }

    @Override
    public List<User> findUsersByBirthDateRange(LocalDate from, LocalDate to) {
        return userList.stream()
                .filter(user -> (user.getBirthDate() != null && !user.getBirthDate().isBefore(from) && !user.getBirthDate().isAfter(to)))
                .collect(Collectors.toList());
    }

}
