package br.ufrn.eaj.tads.rafael.model;



public class Usuario {
    private Long id;
    private String email;
    private String senha;
    private Pessoa pessoa;
    
    public Usuario() {}
    
	public Usuario(Long id, String email, String senha, Pessoa pessoa) {
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.pessoa = pessoa;
	}
	
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	public Pessoa getPessoa() {
		return this.pessoa;
	}
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Usuário [id=" + id + ", email=" + email + ", Pessoa=" + this.pessoa.toString() + "]";
    }
}
