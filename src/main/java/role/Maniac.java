package role;


import player.Player;

public class Maniac implements Role {

	@Override
	public String getNightText() {
		return "Кого будет убивать маньяк этой ночью?";
	}

	@Override
	public String getRoleName() {
		return "Маньяк";
	}

	@Override
	public String runNight(Player player) {
		player.setAlive(false);
		return getRoleName() + " может уснуть";
	}

}
