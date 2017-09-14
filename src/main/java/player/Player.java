package player;

import action.Action;
import role.Role;

import java.util.*;

public class Player {

    private String name;

    private Role role;

    private Map<Action, Player> tonightActions = new HashMap<>();

	private boolean isAlive;

	private boolean hasLawyerProtection;

	private boolean isLoved;

	public Player(String name, Role role) {
        this.name = name;
        this.role = role;
        isAlive = true;
    }

    public void executeActions() {
    	Map<Action, Player> actionsMap = getTonightActions();
    	Set<Action> actions = actionsMap.keySet();
		//TODO сортировка действий по Order'у
		for (Action action : actions) {
			action.runNight(this);
		}
	}

	public boolean isLoved() {
		return isLoved;
	}

	public void setLoved(boolean loved) {
		isLoved = loved;
	}

	public Map<Action, Player> getTonightActions() {
		return tonightActions;
	}

	public void setTonightActions(Map<Action, Player> tonightActions) {
		this.tonightActions = tonightActions;
	}

	public boolean isHasLawyerProtection() {
		return hasLawyerProtection;
	}

	public void setHasLawyerProtection(boolean hasLawyerProtection) {
		this.hasLawyerProtection = hasLawyerProtection;
	}

	public void setName(String name) {
        this.name = name;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
