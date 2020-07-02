package com.grglucastr.roshambo;

import com.grglucastr.roshambo.model.Strategy;
import com.grglucastr.roshambo.repository.v1.StrategyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class RoshamboApplication implements CommandLineRunner {

	@Autowired
	private StrategyRepository strategyRepository;

	public static void main(String[] args) {
		SpringApplication.run(RoshamboApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		getStrategies().forEach(strategy -> {
			strategyRepository.add(strategy);
		});
	}

	public List<Strategy> getStrategies(){

		Strategy rock = new Strategy(1, "Rock");
		Strategy paper = new Strategy(2, "Paper");
		Strategy scissor = new Strategy(3, "Scissor");
		Strategy spock = new Strategy(4, "Spock");
		Strategy lizard = new Strategy(5, "Lizard");

		rock.getStrengths().add(scissor);
		rock.getStrengths().add(lizard);
		rock.getStrengths().add(rock);

		rock.getWeaknesses().add(paper);
		rock.getWeaknesses().add(spock);
		rock.getWeaknesses().add(rock);


		paper.setMultipleStrengths(rock, spock);
		paper.setMultipleWeaknesses(scissor, lizard);

		scissor.setMultipleStrengths(paper, lizard);
		scissor.setMultipleWeaknesses(rock, spock);

		spock.setMultipleStrengths(rock, scissor);
		spock.setMultipleWeaknesses(paper, lizard);

		lizard.setMultipleStrengths(paper, spock);
		lizard.setMultipleWeaknesses(rock, scissor);

		return Arrays.asList(rock, paper, scissor, spock, lizard);
	}
}
