package com.techelevator.controller;

import com.techelevator.dao.ProfileDao;
import com.techelevator.dao.GoalDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Goals;
import com.techelevator.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@PreAuthorize("isAuthenticated()")
public class GoalsController {

    private GoalDao goalDao;

    private UserDao userDao;

    public GoalsController(GoalDao goalDao, UserDao userDao){
        this.goalDao = goalDao;
        this.userDao = userDao;
    }

    @GetMapping("/myGoals")//WORKS
    public List<Goals> listAllGoals(Principal principal){
        User user = userDao.getUserByUsername(principal.getName());
        int userId = user.getId();
        return goalDao.list(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/goals/create")//WORKS
    public Goals create(@RequestBody Goals goal){
        try {
            return goalDao.createGoal(goal);
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found.");
        }
    }

    @PutMapping("/goals/update")//WORKS
    public Goals update(@RequestBody Goals goal){
        return goalDao.updateGoal(goal);
    }

    @GetMapping("/goals/time")//WORKS but needs this in the URL -----> ?fromDate= &toDate=
    public List<Goals> getProgressOverTime(@RequestParam(value = "fromDate") String fromDate, @RequestParam(value = "toDate") String toDate, Principal principal){
        User user = userDao.getUserByUsername(principal.getName());
        int userId = user.getId();
        return goalDao.getGoalsByTimeframe(userId, fromDate, toDate);
    }

    @GetMapping("/goals/{goalId}")//WORKS
    public Goals getGoalById(@PathVariable int goalId){
        Goals progress = goalDao.getGoalByGoalId(goalId);
        return progress;
    }

    @GetMapping("/goals/date")//WORKS but needs this in the URL -----> ?date=
    public List<Goals> getGoalsByDate(@RequestParam(value = "date") String date, Principal principal){
        User user = userDao.getUserByUsername(principal.getName());
        int userId = user.getId();
        return goalDao.getGoalsByDate(userId, date);
    }


}
