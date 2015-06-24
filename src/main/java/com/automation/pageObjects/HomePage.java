package com.automation.pageObjects;

import com.automation.webdriver.ExtendedWebDriver;
import com.automation.webdriver.controls.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import java.util.ArrayList;
import java.util.List;
import static com.thoughtworks.selenium.SeleneseTestBase.assertTrue;

/*
 * Created by demidovskiy-r on 21.06.2015.
 */
public class HomePage extends Page<HomePage> {
    private static final String PAGE_ROOT_NODE = ".container";
    private static List<GridSquare> firstColumn = new ArrayList<>();
    private static List<GridSquare> secondColumn = new ArrayList<>();
    private static List<GridSquare> thirdColumn = new ArrayList<>();
    private static List<GridSquare> fourthColumn = new ArrayList<>();
    private final static int gridSizeX = 4;
    private final static int gridSizeY = 4;
    private Button newGameBtn;

    public HomePage(ExtendedWebDriver web) {
        super(web);
    }

    @Override
    public void init() {
        newGameBtn = new Button(this, generateControlLocator(".restart-button"));
    }

    @Override
    public void load() {
        web.waitUntilElementAppearVisible(getPageLocator());
    }

    @Override
    public void unload() {
        web.waitUntilElementDisappear(getPageLocator());
    }

    @Override
    public void isLoaded() throws Error {
        assertTrue(web.isElementVisible(getPageLocator()));
    }

    public String getScore() {
        return web.findElement(generateControlLocator(".score-container")).getText();
    }

    public HomePage startNew2048TheGame() {
        clickNewGameButton();
        performGameAction(web.getActions().sendKeys(Keys.ENTER)); // to avoid focus in the address bar after getting the page
        return this;
    }

    public HomePage moveUp() {
        performGameAction(web.getActions().sendKeys(Keys.ARROW_UP));
        return this;
    }

    public HomePage moveDown() {
        performGameAction(web.getActions().sendKeys(Keys.ARROW_DOWN));
        return this;
    }

    public HomePage moveRight() {
        performGameAction(web.getActions().sendKeys(Keys.ARROW_RIGHT));
        return this;
    }

    public HomePage moveLeft() {
        performGameAction(web.getActions().sendKeys(Keys.ARROW_LEFT));
        return this;
    }

    public boolean isGameOver() {
        return web.isElementVisible(generateControlLocator(".game-over"));
    }

    public String getGameOverMessage() {
        return web.findElement(generateControlLocator(".game-over p")).getText();
    }

    public String getGamesField() {
        initGridColumns();
        StringBuilder sb = new StringBuilder("| ");
        for (int i = 0; i < gridSizeX; i++) {
            sb.append(firstColumn.get(i).getValue()).append(" | ").append(secondColumn.get(i).getValue()).append(" | ").append(thirdColumn.get(i).getValue()).append(" | ").append(fourthColumn.get(i).getValue()).append(" |");
            if (i != gridSizeX - 1)
                sb.append("\n| ");
        }
        return sb.toString();
    }

    private void initGridColumns() {
        List<GridSquare> gridSquares = getGridSquares();
        for (int i = 0; i < gridSquares.size(); i += gridSizeY) {
            List<GridSquare> gridColumn = gridSquares.subList(i, i + gridSizeY);
            switch (i) {
                case 0: firstColumn = gridColumn; break;
                case gridSizeY: secondColumn = gridColumn; break;
                case gridSizeY * 2: thirdColumn = gridColumn; break;
                case gridSizeY * 3: fourthColumn = gridColumn; break;
                default: throw new IllegalArgumentException("Invalid switcher: " + i);
            }
        }
    }

    private List<GridSquare> getGridSquares() {
        List<GridSquare> gridSquares = new ArrayList<>();
        for (int i = 1; i <= gridSizeX; i++)
            for (int j = 1; j <= gridSizeY; j++)
                gridSquares.add(new GridSquare(web, i, j));
        return gridSquares;
    }

    private static class GridSquare {
        private ExtendedWebDriver web;
        private int x;
        private int y;
        private String value;

        public GridSquare(ExtendedWebDriver web, int x,  int y) {
            this.web = web;
            this.x = x;
            this.y = y;
            value = initValue();
        }

        public String getValue() {
            return value;
        }

        private String initValue() {
            if (web.isElementVisible(getGridMergedSquareLocator()))
                return web.findElement(getGridMergedSquareLocator()).getText();
            else if (web.isElementVisible(getGridSquareLocator()))
                return web.findElement(getGridSquareLocator()).getText();
            else
                return "0";
        }

        private By getGridMergedSquareLocator() {
            return By.cssSelector(String.format(".tile-position-%s-%s.tile-merged .tile-inner", x, y));
        }

        private By getGridSquareLocator() {
            return By.cssSelector(String.format(".tile-position-%s-%s .tile-inner", x, y));
        }
    }

    private void clickNewGameButton() {
        newGameBtn.click();
    }

    private void performGameAction(Actions actions) {
        actions.build().perform();
    }

  /* defining locators block*/

    protected String getPageRootNode() {
        return PAGE_ROOT_NODE;
    }

    protected By generateControlLocator(String partialLocator) {
        return By.cssSelector(String.format("%s %s", getPageRootNode(), partialLocator));
    }

    private By getPageLocator() {
        return By.cssSelector(getPageRootNode());
    }
}