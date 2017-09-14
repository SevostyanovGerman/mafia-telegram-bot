package player;

import role.Role;

public class Player {

    private String name;

    private Role role;

	private boolean isAlive;

	private boolean hasLawyerProtection;

	private boolean isLoved;

	public Player(String name, Role role) {
        this.name = name;
        this.role = role;
        isAlive = true;
    }

	public boolean isLoved() {
		return isLoved;
	}

	public void setLoved(boolean loved) {
		isLoved = loved;
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
