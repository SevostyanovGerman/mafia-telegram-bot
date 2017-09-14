package action;

import player.Player;

public class ManiacAction implements Action {

    @Override
    public String runNight(Player player) {
        player.setAlive(false);
        return "Маньяк может уснуть";
    }
}
