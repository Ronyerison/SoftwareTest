package br.ufpi.ardigital.factory;

import br.ufpi.ardigital.model.User;
import br.ufpi.ardigital.util.Config;

public class UserFactory {

	public static User createCommonUser() {
		return new User(Config.COMMON_USER_NAME, Config.COMMON_USER_PASSWORD);
	}
	
	public static User createCommonUser2() {
		return new User(Config.COMMON_USER_NAME_2, Config.COMMON_USER_PASSWORD_2);
	}
	
	public static User createAdministratorUser() {
		return new User(Config.ADMINISTRATOR_USER_NAME, Config.ADMINISTRATOR_USER_PASSWORD);
	}
	
	public static User createIvalidLoginUser() {
		return new User("invalid", Config.COMMON_USER_PASSWORD);
	}

	public static User createIvalidPasswordUser() {
		return new User(Config.COMMON_USER_NAME, "invalid");
	}
	
	public static User createInvalidUser() {
		return new User("invalid", "invalid");
	}

}
