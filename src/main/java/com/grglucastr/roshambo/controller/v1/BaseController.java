package com.grglucastr.roshambo.controller.v1;

import com.grglucastr.roshambo.model.RoshamboSession;
import com.grglucastr.roshambo.repository.v1.RoshamboSessionRepository;

import java.util.Optional;

public abstract class BaseController {
    protected RoshamboSessionRepository roshamboRepository;

    public BaseController(RoshamboSessionRepository roshamboRepository) {
        this.roshamboRepository = roshamboRepository;
    }

    public Optional<RoshamboSession> getSession(Integer id){
        return roshamboRepository.findById(id);
    }
}
