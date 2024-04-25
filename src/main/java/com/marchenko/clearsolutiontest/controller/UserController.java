package com.marchenko.clearsolutiontest.controller;

import com.marchenko.clearsolutiontest.exceptions.NotFoundException;
import com.marchenko.clearsolutiontest.model.User;
import com.marchenko.clearsolutiontest.service.UserService;
import com.marchenko.clearsolutiontest.exceptions.ExceptionHelper;
import javax.validation.Valid;

import com.marchenko.clearsolutiontest.validation.AgeLimit;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid @AgeLimit User user,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        logger.debug("Creating new user: {}", user);
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(201).body(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        logger.debug("Getting user with id: {}", id);
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping()
    public ResponseEntity<List<User>> getUsersOrSearchByDateRange(
            @RequestParam(value = "from", required = false) String from,
            @RequestParam(value = "to", required = false) String to) {
        if (from != null && to != null) {
            logger.debug("Getting users with date range filter");
            LocalDate fromDate = LocalDate.parse(from);
            LocalDate toDate = LocalDate.parse(to);
            if (fromDate.isAfter(toDate)) {
                throw new ValidationException("'From' date cannot be after 'To' date");
            }
            List<User> users = userService.findUsersByBirthDateRange(fromDate, toDate);
            return ResponseEntity.ok(users);
        } else if (from != null || to != null) {
            throw new ValidationException("Both 'from' and 'to' dates are required for filtering");
        } else {
            logger.debug("Getting users");
            List<User> users = userService.getUsers();
            return ResponseEntity.ok(users);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUserFields(@PathVariable Long id,
                                                 @RequestBody User updatedUser) {
        logger.debug("Updating user fields user with id: {}", id);
        User user = userService.updateSomeFieldsUser(id, updatedUser);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateAllUserFields(@PathVariable Long id,
                                                    @Valid @RequestBody @AgeLimit User updatedUser,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ExceptionHelper.formErrorMessage(bindingResult);
            throw new ValidationException(message);
        }
        logger.debug("Updating user with id: {}", id);
        User user = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.debug("Deleting user with id: {}", id);
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        String errorMessage = "ERROR: " + e.getMessage();
        logger.error(errorMessage);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException e) {
        String errorMessage = "ERROR: " + e.getMessage();
        logger.error(errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        String errorMessage = "ERROR: " + e.getMessage();
        logger.error(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}