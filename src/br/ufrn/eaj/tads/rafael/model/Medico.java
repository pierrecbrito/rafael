package br.ufrn.eaj.tads.rafael.model;

import java.util.ArrayList;
import java.util.List;

public class Medico {
    private int id;
    private Pessoa pessoa;
    private Usuario usuario;
    private String crm;
    private List<Especialidade> especialidades;

    // Construtor padrão
    public Medico() {
    	this.especialidades = new ArrayList<Especialidade>();
    }

    // Construtor com parâmetros (sem especialidades)
    public Medico(int id, Pessoa pessoa, Usuario usuario, String crm) {
        this.id = id;
        this.pessoa = pessoa;
        this.usuario = usuario;
        this.crm = crm;
        this.especialidades = new ArrayList<Especialidade>();
    }
    
	public String getNome() {
    	return pessoa.getNome();
    }
	
	public String getEspecialidade() {
		String especialidade = this.getEspecialidades().getFirst().getNome();
		return especialidade;
	}
	
	public String getEmail() {
		return usuario.getEmail();
	}

    // Construtor com parâmetros (incluindo especialidades)
    public Medico(int id, Pessoa pessoa, Usuario usuario, String crm, List<Especialidade> especialidades) {
        this.id = id;
        this.pessoa = pessoa;
        this.usuario = usuario;
        this.crm = crm;
        this.especialidades = especialidades;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public List<Especialidade> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<Especialidade> especialidades) {
        this.especialidades = especialidades;
    }
    
	public void addEspecialidade(Especialidade especialidade) {
		this.especialidades.add(especialidade);
	}
	
	public void removeEspecialidade(Especialidade especialidade) {
		this.especialidades.remove(especialidade);
	}
	
	public void clearEspecialidades() {
		this.especialidades.clear();
	}

    @Override
    public String toString() {
        return "Medico{" +
                "id=" + id +
                ", pessoa=" + pessoa +
                ", usuario=" + usuario +
                ", crm='" + crm + '\'' +
                ", especialidades=" + especialidades +
                '}';
    }

}
