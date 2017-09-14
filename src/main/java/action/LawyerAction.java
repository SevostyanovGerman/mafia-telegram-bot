package action;

import player.Player;

public class LawyerAction implements Action {

    @Override
    public String runNight(Player player) {
        player.setHasLawyerProtection(true);
    }
}
