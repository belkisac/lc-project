package com.example.dateproject.controllers;

import com.example.dateproject.models.Event;
import com.example.dateproject.models.User;
import com.example.dateproject.models.data.EventDao;
import com.example.dateproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


@RestController
public class EventController {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/events", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody List<Event> getEvents() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        return eventDao.findByUserId(user.getId());
    }

}
