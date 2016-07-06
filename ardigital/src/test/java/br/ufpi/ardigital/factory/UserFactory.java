package br.ufpi.ardigital.factory;

import br.ufpi.ardigital.model.User;

public class UserFactory {

	public static User criaUsuarioValido() {
		return new User("ar_user1", "armestrado2016");
	}

	public static User criaUsuarioInvalido() {
		return new User("invalido", "invalido");
	}

}
