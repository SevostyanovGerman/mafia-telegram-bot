package role;

import action.Action;

public interface Role {

    String getMorningText();

    String getNightText();

    Action getAction();
}
