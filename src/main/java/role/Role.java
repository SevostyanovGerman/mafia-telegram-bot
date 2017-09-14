package role;

import player.Player;

public interface Role {

    default String getMorningText() { return ""; }

    String getNightText();

    String getRoleName();

	String runNight(Player player);

    default String runDay(Player player) { return ""; }
}
