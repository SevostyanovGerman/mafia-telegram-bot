package role;

import org.apache.commons.lang.ArrayUtils;
import org.reflections.Reflections;
import player.Player;
import role.mafia.Mafia;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;

public class Inspector implements Role {

	@Override
	public String getNightText() {
		return null;
	}

	@Override
	public String getRoleName() {
		return "Инспектор";
	}

	@Override
	public String runNight(Player player) {

		return player.getName() + " " + isMafia(player) + " мафией.\n" + getRoleName() + " может уснуть";
	}


	private String isMafia(Player player){
		if (player.getClass().isAnnotationPresent(Mafia.class)){
			Mafia annotation = player.getClass().getAnnotation(Mafia.class);
			if (!annotation.isDon()){
				return "является";
			}
		}
		return "не является";
	}

}
