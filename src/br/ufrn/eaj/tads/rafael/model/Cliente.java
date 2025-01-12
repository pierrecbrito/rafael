package br.ufrn.eaj.tads.rafael.model;

import java.time.format.DateTimeFormatter;

public class Cliente {
	private int id;
	private Pessoa pessoa;
	private String cpf;
	
	public Cliente() {}
	
	public Cliente(int id, Pessoa pessoa, String cpf) {
		this.id = id;
		this.pessoa = pessoa;
		this.cpf = cpf;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getCpf() {
		return this.cpf;
	}
	
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	public Pessoa getPessoa() {
		return this.pessoa;
	}
	
	public String getNome() {
        return pessoa.getNome();
    }

    public String getTelefone() {
        return pessoa.getTelefone();
    }

    public String getDataNascimento() {
        return pessoa.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
	
	@Override
	public String toString() {
		return "Cliente [id=" + id + ", pessoa=" + pessoa.toString() + ", cpf=" + cpf + "]";
	}
	
}
