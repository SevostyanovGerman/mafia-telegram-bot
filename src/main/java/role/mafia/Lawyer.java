package role.mafia;

import action.Action;
import role.Role;

@Mafia
public class Lawyer implements Role {

    @Override
    public String getMorningText() {
        return "";
    }

    @Override
    public String getNightText() {
        return "Кому ночью дает протекцию адвокат?";
    }

    @Override
    public Action getAction() {
        return null;
    }

}
