package action;

import player.Player;

public interface Action {

    String runNight(Player player);
    default String runDay(Player player) { return ""; }
}
