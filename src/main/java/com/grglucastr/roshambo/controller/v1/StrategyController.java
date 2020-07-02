package com.grglucastr.roshambo.controller.v1;

import com.grglucastr.roshambo.model.Strategy;
import com.grglucastr.roshambo.repository.v1.StrategyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/strategies")
public class StrategyController implements HTTPRequestable<Strategy>  {

    private StrategyRepository strategyRepository;

    @Autowired
    public StrategyController(StrategyRepository strategyRepository) {
        this.strategyRepository = strategyRepository;
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Strategy>> listAll() {
        return ResponseEntity.ok(strategyRepository.listAll());
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Strategy> getSingleOne(@PathVariable("id") Integer id) {
        Optional<Strategy> strategy = strategyRepository.findById(id);

        return strategy.map(p -> ResponseEntity.ok(p))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Strategy> addNew(@RequestBody Strategy newObj) {
        Strategy strategy = strategyRepository.add(newObj);
        return ResponseEntity.status(HttpStatus.CREATED).body(strategy);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Strategy> update(@PathVariable("id") Integer id, @RequestBody Strategy obj) {
        Optional<Strategy> found = strategyRepository.findById(id);
        if(found.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Strategy updateObj = found.get();
        updateObj.setName(obj.getName());
        handleStrengthsAndWeaknesses(obj.getStrengths(), updateObj.getStrengths());
        handleStrengthsAndWeaknesses(obj.getWeaknesses(), updateObj.getWeaknesses());

        strategyRepository.update(updateObj);
        return ResponseEntity.ok(updateObj);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        if(strategyRepository.removeById(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/{id}/weaknesses", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Strategy>> listWeaknesses(@PathVariable("id") Integer id) {
        Optional<Strategy> found = strategyRepository.findById(id);
        if(found.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(found.get().getWeaknesses().stream().collect(Collectors.toList()));
    }

    @GetMapping(value = "/{id}/strengths", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Strategy>> listStrengths(@PathVariable("id") Integer id) {
        Optional<Strategy> found = strategyRepository.findById(id);
        if(found.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(found.get().getStrengths().stream().collect(Collectors.toList()));
    }

    @PostMapping(value = "/{id}/weaknesses", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Strategy>> addWeaknesses(@PathVariable("id") Integer id, @RequestBody Set<Strategy> strategies) {
        Optional<Strategy> found = strategyRepository.findById(id);
        if(found.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Strategy updateObj = found.get();
        handleStrengthsAndWeaknesses(strategies, updateObj.getWeaknesses());

        strategyRepository.update(updateObj);
        return ResponseEntity.ok(updateObj.getWeaknesses().stream().collect(Collectors.toList()));
    }

    @DeleteMapping(value = "/{id}/weaknesses/{weaknessId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Strategy>> removeWeakness(@PathVariable("id") Integer id, @PathVariable("weaknessId") Integer weakId) {
        Optional<Strategy> found = strategyRepository.findById(id);
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
        return ResponseEntity.ok(updateObj.getWeaknesses().stream().collect(Collectors.toList()));
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
