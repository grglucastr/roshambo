package com.grglucastr.roshambo.controller.v1;

import com.grglucastr.roshambo.model.RoshamboSession;
import com.grglucastr.roshambo.model.Strategy;
import com.grglucastr.roshambo.repository.v1.RoshamboSessionRepository;
import com.grglucastr.roshambo.repository.v1.StrategyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/roshambo-sessions/{sessionId}/strategies")
public class StrategyController extends BaseController  {

    private StrategyRepository strategyRepository;

    @Autowired
    public StrategyController(RoshamboSessionRepository roshamboRepository) {
        super(roshamboRepository);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Strategy>> listAll(@PathVariable("sessionId") Integer sessionId) {
        Optional<RoshamboSession> optSession = getSession(sessionId);

        return optSession.map(session -> ResponseEntity.ok(session.getStrategies()))
                .orElse(ResponseEntity.notFound().build());
    }

    
    @GetMapping(value = "/{strategyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Strategy> getSingleOne(@PathVariable("sessionId") Integer sessionId,
                                                 @PathVariable("strategyId") Integer strategyId) {

        Optional<RoshamboSession> optSession = getSession(sessionId);
        if(optSession.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        strategyRepository = new StrategyRepository(optSession.get().getStrategies());
        Optional<Strategy> optStrategy = strategyRepository.findById(strategyId);

        return optStrategy.map(p -> ResponseEntity.ok(p))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Strategy> addNew(@PathVariable("sessionId") Integer sessionId,
                                           @RequestBody Strategy newObj) {

        Optional<RoshamboSession> optSession = getSession(sessionId);
        if(optSession.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        strategyRepository = new StrategyRepository(optSession.get().getStrategies());
        Strategy strategy = strategyRepository.add(newObj);
        optSession.get().getStrategies().add(strategy);

        return ResponseEntity.status(HttpStatus.CREATED).body(strategy);
    }

    @PutMapping(value = "/{strategyId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Strategy> update(@PathVariable("sessionId") Integer sessionId,
                                           @PathVariable("strategyId") Integer strategyId,
                                           @RequestBody Strategy obj) {

        Optional<RoshamboSession> optSession = getSession(sessionId);
        if(optSession.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        strategyRepository = new StrategyRepository(optSession.get().getStrategies());
        Optional<Strategy> found = strategyRepository.findById(strategyId);
        if(found.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Strategy updateObj = found.get();
        updateObj.setName(obj.getName());

        strategyRepository.update(updateObj);
        optSession.get().getStrategies().add(updateObj);

        return ResponseEntity.ok(updateObj);
    }

    
    @DeleteMapping(value = "/{strategyId}")
    public ResponseEntity delete(@PathVariable("sessionId") Integer sessionId,
                                 @PathVariable("strategyId") Integer strategyId) {

        Optional<RoshamboSession> optSession = getSession(sessionId);
        if(optSession.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        strategyRepository = new StrategyRepository(optSession.get().getStrategies());
        Optional<Strategy> found = strategyRepository.findById(strategyId);
        if(found.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        if(strategyRepository.removeById(strategyId)){
            return ResponseEntity.noContent().build();
        }

        optSession.get().getStrategies().clear();
        optSession.get().getStrategies().addAll(strategyRepository.listAll());

        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/{strategyId}/weaknesses", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Strategy>> listWeaknesses(@PathVariable("sessionId") Integer sessionId,
                                                         @PathVariable("strategyId") Integer strategyId) {

        Optional<RoshamboSession> optSession = getSession(sessionId);
        if(optSession.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        strategyRepository = new StrategyRepository(optSession.get().getStrategies());
        Optional<Strategy> found = strategyRepository.findById(strategyId);
        if(found.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(found.get().getWeaknesses());
    }

    @GetMapping(value = "/{strategyId}/strengths", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Strategy>> listStrengths(@PathVariable("sessionId") Integer sessionId,
                                                       @PathVariable("strategyId") Integer strategyId) {

        Optional<RoshamboSession> optSession = getSession(sessionId);
        if(optSession.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        strategyRepository = new StrategyRepository(optSession.get().getStrategies());
        Optional<Strategy> found = strategyRepository.findById(strategyId);
        if(found.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(found.get().getStrengths());
    }

    @PostMapping(value = "/{strategyId}/weaknesses", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Strategy>> addWeaknesses(@PathVariable("sessionId") Integer sessionId,
                                                        @PathVariable("strategyId") Integer strategyId,
                                                        @RequestBody Set<Strategy> strategies) {
        Optional<RoshamboSession> optSession = getSession(sessionId);
        if(optSession.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        strategyRepository = new StrategyRepository(optSession.get().getStrategies());
        Optional<Strategy> found = strategyRepository.findById(strategyId);
        if(found.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Strategy updateObj = found.get();
        handleStrengthsAndWeaknesses(strategies, updateObj.getWeaknesses());

        strategyRepository.update(updateObj);
        optSession.get().getStrategies().addAll(strategyRepository.listAll());
        return ResponseEntity.ok(updateObj.getWeaknesses());
    }

    @DeleteMapping(value = "/{strategyId}/weaknesses/{weaknessId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Strategy>> removeWeakness(@PathVariable("sessionId") Integer sessionId,
                                                        @PathVariable("strategyId") Integer strategyId,
                                                        @PathVariable("weaknessId") Integer weakId) {
        Optional<RoshamboSession> optSession = getSession(sessionId);
        if(optSession.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        strategyRepository = new StrategyRepository(optSession.get().getStrategies());
        Optional<Strategy> found = strategyRepository.findById(strategyId);
        if(found.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Strategy updateObj = found.get();
        Set<Strategy> weaknesses = updateObj
                .getWeaknesses()
                .stream()
                .filter(weak -> weak.getId() != weakId)
                .collect(Collectors.toSet());

        updateObj.getWeaknesses().clear();
        updateObj.getWeaknesses().addAll(weaknesses);

        strategyRepository.update(updateObj);
        optSession.get().getStrategies().addAll(strategyRepository.listAll());

        return ResponseEntity.ok(updateObj.getWeaknesses());
    }

    @PostMapping(value = "/{strategyId}/strengths", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Strategy>> addStrengths(@PathVariable("sessionId") Integer sessionId,
                                                      @PathVariable("strategyId") Integer strategyId,
                                                      @RequestBody Set<Strategy> strategies) {

        Optional<RoshamboSession> optSession = getSession(sessionId);
        if(optSession.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        strategyRepository = new StrategyRepository(optSession.get().getStrategies());
        Optional<Strategy> found = strategyRepository.findById(strategyId);
        if(found.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Strategy updateObj = found.get();
        handleStrengthsAndWeaknesses(strategies, updateObj.getStrengths());

        strategyRepository.update(updateObj);
        optSession.get().getStrategies().addAll(strategyRepository.listAll());
        return ResponseEntity.ok(updateObj.getStrengths());
    }

    @DeleteMapping(value = "/{strategyId}/strengths/{strengthId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Strategy>> removeStrength(@PathVariable("sessionId") Integer sessionId,
                                                        @PathVariable("strategyId") Integer strategyId,
                                                        @PathVariable("strengthId") Integer strengthId) {
        Optional<RoshamboSession> optSession = getSession(sessionId);
        if(optSession.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        strategyRepository = new StrategyRepository(optSession.get().getStrategies());
        Optional<Strategy> found = strategyRepository.findById(strategyId);
        if(found.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Strategy updateObj = found.get();
        Set<Strategy> strengths = updateObj
                .getStrengths()
                .stream()
                .filter(strength -> strength.getId() != strengthId)
                .collect(Collectors.toSet());

        updateObj.getStrengths().clear();
        updateObj.getStrengths().addAll(strengths);

        strategyRepository.update(updateObj);
        optSession.get().getStrategies().addAll(strategyRepository.listAll());

        return ResponseEntity.ok(updateObj.getStrengths());
    }

    private void handleStrengthsAndWeaknesses(Set<Strategy> sourceStrategies, Set<Strategy> destinationStrategies) {
        sourceStrategies.forEach(strategy -> {
            if(strategy.getName() == null || strategy.getName().isEmpty() || strategy.getName().isBlank()){
                Optional<Strategy> foundStrategy = strategyRepository.findById(strategy.getId());
                if(foundStrategy.isPresent()){
                    destinationStrategies.add(foundStrategy.get());
                }
            } else{
                destinationStrategies.add(strategy);
            }
        });
    }
}
