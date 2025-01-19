package br.ufrn.eaj.tads.rafael.model;

public class Especialidade {
    private int id;
    private String nome;

    // Construtor padrão
    public Especialidade() {}

    // Construtor com parâmetros
    public Especialidade(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // Getters e Setters
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
    
    @Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Especialidade especialidade = (Especialidade) o;

		return id == especialidade.id;
	}

    @Override
    public String toString() {
        return "Especialidade{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
