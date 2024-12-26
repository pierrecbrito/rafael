package br.ufrn.eaj.tads.rafael.util;

import br.ufrn.eaj.tads.rafael.model.Usuario;

public class Session {
	private static Session instance;
    private Usuario usuario;
    
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    
	public void invalidate() {
		this.usuario = null;
	}
}
