package action;

import player.Player;

public class ManiacAction implements Action {

    @Override
    public void run(Player player) {
        player.setAlive(false);
    }
}
