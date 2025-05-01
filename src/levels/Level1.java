package levels;
import entities.*;
import entities.collectibles.Coin;

import java.util.ArrayList;
import java.util.Properties;

/**
 * The first level of the game.
 */
public class Level1 extends Level {
    /** Default constructor for level 1.
     *
     * @param game_props Properties file containing game information.
     * @param message_props Properties file containing information about text.
     */
    public Level1(Properties game_props, Properties message_props) {
        super(game_props, message_props);

        String level_file = game_props.getProperty("level1File");
        final ArrayList<String[]> LEVEL_INFO = main.IOUtils.readCsv(level_file);
        // Save info for easier access
        for (String[] line: LEVEL_INFO) {
            int x = Integer.parseInt(line[1]);
            int y = Integer.parseInt(line[2]);
            switch (line[0]) {
                case "PLATFORM":
                    platform = new Platform(x, y);
                    break;
                case "PLAYER":
                    player = new Player(x, y);
                    break;
                case "COIN":
                    coins.add(new Coin(x, y));
                    break;
                case "ENEMY":
                    enemies.add(new Enemy(x, y));
                    break;
                case "END_FLAG":
                    endFlag = new EndFlag(x, y);
                    break;
            }
        }
    }
}
