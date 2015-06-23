package com.automation.pageObjects;

import com.automation.utils.Gen;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


/*
 * Created by demidovskiy-r on 21.06.2015.
 */

public class HomePageTest extends TestBase {

	private enum GameAction {
		UP,
		DOWN,
		RIGHT,
		LEFT
	}

	private static final List<GameAction> gameActions = new ArrayList<GameAction>() {{add(GameAction.UP); add(GameAction.DOWN); add(GameAction.RIGHT); add(GameAction.LEFT);}};

	private HomePage homepage;

	private HomePage openHomePage() {
		return getSut().getPageNavigator().getHomePage();
	}

	private HomePage getHomepage() {
		return getSut().getPageCreator().getHomePage();
	}
	
	@BeforeClass
	public void initTheHomePage() {
		homepage = openHomePage().startNew2048TheGame();
		log.info("Starting new 2048 game is successful.");
	}

	@Test
	public void tryToSolveThe2048Game() {
		log.info("Start solving the game...");
		while (!homepage.isGameOver()) {
			GameAction action = gameActions.get(Gen.getInt(gameActions.size()));
			log.info("Current games field: \n" + homepage.getGamesField());
			switch (action) {
				case UP:
					log.info("Move up.");
					homepage.moveUp();
					break;
				case DOWN:
					log.info("Move down.");
					homepage.moveDown();
					break;
				case RIGHT:
					log.info("Move right.");
					homepage.moveRight();
					break;
				case LEFT:
					log.info("Move left.");
					homepage.moveLeft();
					break;
				default: throw new IllegalArgumentException("Unknown game action: " + action);
			}
		}
		log.info("Current score: " + homepage.getScore());
		log.info("Game is finished.");
		Assert.assertEquals(homepage.getGameOverMessage(), "Game over!");
		assertThat("Check message after finishing the game: ", homepage.getGameOverMessage(), equalTo("Game over!"));
	}
}