package role;

import action.Action;
import action.ManiacAction;

public class Maniac implements Role {

	private static final ManiacAction maniacAction = new ManiacAction();

	@Override
	public String getNightText() {
		return "Кого будет убивать маньяк этой ночью?";
	}

	@Override
	public Action getAction() {
		return maniacAction;
	}

	@Override
	public String getRoleName() {
		return "Маньяк";
	}
}
