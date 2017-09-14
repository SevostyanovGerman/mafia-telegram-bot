package role.mafia;

import player.Player;
import role.Role;

@Mafia
public class Lawyer implements Role {

    @Override
    public String getNightText() {
        return "Кому ночью дает протекцию адвокат?";
    }

    @Override
    public String getRoleName() {
        return "Адвокат";
    }

    @Override
    public String runNight(Player player) {
        player.setHasLawyerProtection(true);
        return getRoleName() + " может уснуть";
    }

}
