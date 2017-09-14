package role;

import player.Player;

public class Lucky implements Role {

	private boolean isFirstNightKill = true;
	private boolean isFirstDayKill = true;

	@Override
	public String getNightText() {
		return "";
	}

	@Override
	public String getRoleName() {
		return "Счастливчик";
	}

	@Override
	public String runNight(Player player) {
		if (isFirstNightKill) {
			player.setAlive(true);
		}
		isFirstNightKill = false;
		return getRoleName() + " может уснуть";
	}

	@Override
	public String runDay(Player player) {
		if (isFirstDayKill) {
			player.setAlive(true);
		}
		isFirstDayKill = false;
		return "";
	}
}
