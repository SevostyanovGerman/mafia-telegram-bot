package player;

import role.Role;

public class Player {

    private String name;

    private boolean isAlive;

    private Role role;


    public Player(String name, Role role) {
        this.name = name;
        this.role = role;
        isAlive = true;
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
