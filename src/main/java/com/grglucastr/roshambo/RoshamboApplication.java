package com.grglucastr.roshambo;

import com.grglucastr.roshambo.model.Move;
import com.grglucastr.roshambo.model.Player;
import com.grglucastr.roshambo.model.Strategy;
import com.grglucastr.roshambo.repository.v1.MoveRepository;
import com.grglucastr.roshambo.repository.v1.PlayerRepository;
import com.grglucastr.roshambo.repository.v1.StrategyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class RoshamboApplication implements CommandLineRunner {

	@Autowired
	private StrategyRepository strategyRepository;

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private MoveRepository moveRepository;

	public static void main(String[] args) {
		SpringApplication.run(RoshamboApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		getStrategies().forEach(strategy -> {
			strategyRepository.add(strategy);
		});

		getPlayers().forEach(player ->{
			playerRepository.add(player);
		});

		getMoves().forEach(move -> {
			moveRepository.add(move);
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

	public List<Player> getPlayers(){
		List<Player> players = Arrays.asList(
				new Player(1, "Player1"),
				new Player(2, "Player2"),
				new Player(3, "Player3"),
				new Player(4, "Player4")
		);
		return players;
	}

	public Set<Move> getMoves(){
		Set<Move> moves = new HashSet<>();
		moves.add(new Move(1, getPlayers().get(0), getStrategies().get(0)));
		moves.add(new Move(2, getPlayers().get(1), getStrategies().get(1)));
		moves.add(new Move(3, getPlayers().get(2), getStrategies().get(2)));
		moves.add(new Move(4, getPlayers().get(3), getStrategies().get(3)));
		return moves;
	}
}
