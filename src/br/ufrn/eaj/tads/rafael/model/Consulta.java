package br.ufrn.eaj.tads.rafael.model;

import java.time.LocalDateTime;

public class Consulta {
	private int id;
    private Medico medico;
    private Cliente cliente;
    private LocalDateTime dataConsulta;
    private String observacoes;
    
    public Consulta() {}
    
	public Consulta(int id, Medico medico, Cliente cliente, LocalDateTime dataConsulta, String observacoes) {
		this.id = id;
		this.medico = medico;
		this.cliente = cliente;
		this.dataConsulta = dataConsulta;
		this.observacoes = observacoes;
	}
	
	public String getNomePaciente() {
		return cliente.getPessoa().getNome();
	}
	
	public String getNomeMedico() {
		return medico.getNome();
	}
	
	public String getEspecialidade() {
		return medico.getEspecialidade();
	}
	
	public String getDiaHora() {
		return dataConsulta.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
	}
	
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(LocalDateTime dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    
    @Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Consulta consulta = (Consulta) obj;

		return id == consulta.id;
	}
    
    @Override
	public String toString() {
		return "Consulta [id=" + id + ", medico=" + medico.toString() + ", cliente=" + cliente.toString()
				+ ", dataConsulta=" + dataConsulta + ", observacoes=" + observacoes + "]";
	}
}
