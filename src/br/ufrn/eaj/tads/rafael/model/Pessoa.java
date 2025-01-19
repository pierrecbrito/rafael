package br.ufrn.eaj.tads.rafael.model;

import java.time.LocalDate;

public class Pessoa {
    private int id;             
    private String nome;         
    private String telefone;     
    private LocalDate dataNascimento; 

    
    public Pessoa(int id, String nome, String telefone, LocalDate dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
    }
    
    public Pessoa() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", dataNascimento='" + dataNascimento + '\'' +
                '}';
    }

	public Object nomeProperty() {
		return this.getNome();
	}
}

