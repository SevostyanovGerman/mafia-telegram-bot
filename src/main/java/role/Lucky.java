package role;

import action.Action;
import action.LuckyAction;
import player.Player;

public class Lucky implements Role {

	private boolean isFirstNightKill = true;
	private boolean isFirstDayKill = true;
	private static final LuckyAction luckyAction = new LuckyAction();

	@Override
	public String getNightText() {
		return "";
	}

	@Override
	public Action getAction() {
		return luckyAction;
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
	public void runDay(Player player) {
		if (isFirstDayKill) {
			player.setAlive(true);
		}
		isFirstDayKill = false;
	}
}
