package role;

import action.Action;
import action.LuckyAction;

public class Inspector implements Role {

	private static final LuckyAction luckyAction = new LuckyAction();


	@Override
	public String getNightText() {
		return null;
	}

	@Override
	public Action getAction() {
		return null;
	}

	@Override
	public String getRoleName() {
		return "Инспектор";
	}
}
