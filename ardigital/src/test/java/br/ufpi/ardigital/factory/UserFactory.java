package br.ufpi.ardigital.factory;

import br.ufpi.ardigital.model.User;
import br.ufpi.ardigital.util.Constant;

public class UserFactory {

	public static User criaUsuarioValido() {
		return new User(Constant.UserName, Constant.UserPassword);
	}
	
	public static User criaUsuarioAdminValido() {
		return new User("ar_user6", "armestrado2016");
	}
	
	public static User createIvalidLoginUser() {
		return new User("invalid", "armestrado2016");
	}

	public static User createIvalidPasswordUser() {
		return new User("ar_user1", "invalid");
	}
	
	public static User criaUsuarioInvalido() {
		return new User("invalid", "invalid");
	}

}
