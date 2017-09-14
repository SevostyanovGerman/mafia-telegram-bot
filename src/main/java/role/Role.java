package role;

import action.Action;
import player.Player;

public interface Role {

    default String getMorningText() { return ""; }

    String getNightText();

    Action getAction();

    String getRoleName();

	String runNight(Player player);

	void runDay(Player player);
}
