package br.ufrn.eaj.tads.rafael.dao;


import br.ufrn.eaj.tads.rafael.model.Consulta;
import br.ufrn.eaj.tads.rafael.model.Medico;
import br.ufrn.eaj.tads.rafael.model.Pessoa;
import br.ufrn.eaj.tads.rafael.model.Usuario;
import br.ufrn.eaj.tads.rafael.util.DatabaseUtil;
import br.ufrn.eaj.tads.rafael.model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {

    public boolean insertConsulta(Consulta consulta) {
        String sql = "INSERT INTO consulta (id_medico, id_cliente, data_consulta, observacoes) VALUES (?, ?, ?, ?)";
        try {
        	Connection connection = DatabaseUtil.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, consulta.getMedico().getId());
            stmt.setInt(2, consulta.getCliente().getId());
            stmt.setTimestamp(3, Timestamp.valueOf(consulta.getDataConsulta()));
            stmt.setString(4, consulta.getObservacoes());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        consulta.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateConsulta(Consulta consulta) {
        String sql = "UPDATE consulta SET id_medico = ?, id_cliente = ?, data_consulta = ?, observacoes = ? WHERE id = ?";
        try {
        	Connection connection = DatabaseUtil.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, consulta.getMedico().getId());
            stmt.setInt(2, consulta.getCliente().getId());
            stmt.setTimestamp(3, Timestamp.valueOf(consulta.getDataConsulta()));
            stmt.setString(4, consulta.getObservacoes());
            stmt.setInt(5, consulta.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteConsulta(int id) {
        String sql = "DELETE FROM consulta WHERE id = ?";
        try {
        	Connection connection = DatabaseUtil.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<Consulta> listAllConsultas() {
    	 List<Consulta> consultas = new ArrayList<>();
    	 String query = "SELECT " +
    			    "c.id AS consulta_id, " +
    			    "c.data_consulta, " +
    			    "c.observacoes, " +
    			    "m.id AS medico_id, " +
    			    "p1.nome AS medico_nome, " +
    			    "p1.telefone AS medico_telefone, " +
    			    "p1.data_nascimento AS medico_data_nascimento, " +
    			    "u.email AS medico_email, " +
    			    "m.crm, " +
    			    "array_agg(e.id) AS especialidades, " +
    			    "cl.id AS cliente_id, " +
    			    "p2.nome AS cliente_nome, " +
    			    "p2.telefone AS cliente_telefone, " +
    			    "p2.data_nascimento AS cliente_data_nascimento, " +
    			    "cl.cpf AS cliente_cpf " +
    			    "FROM consulta c " +
    			    "JOIN medico m ON c.id_medico = m.id " +
    			    "JOIN pessoa p1 ON m.id_pessoa = p1.id " +
    			    "JOIN usuario u ON m.id_usuario = u.id " +
    			    "JOIN medico_especialidade me ON m.id = me.id_medico " +
    			    "JOIN especialidade e ON me.id_especialidade = e.id " +
    			    "JOIN cliente cl ON c.id_cliente = cl.id " +
    			    "JOIN pessoa p2 ON cl.id_pessoa = p2.id " +
    			    "GROUP BY c.id, m.id, p1.id, u.id, cl.id, p2.id " +
    			    "ORDER BY c.id";

         try {
        	 Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);

             while (rs.next()) {
                 Consulta consulta = new Consulta();
                 consulta.setId(rs.getInt("consulta_id"));
                 consulta.setDataConsulta(rs.getTimestamp("data_consulta").toLocalDateTime());
                 consulta.setObservacoes(rs.getString("observacoes"));

                 Medico medico = new Medico();
                 medico.setId(rs.getInt("medico_id"));
                 medico.setCrm(rs.getString("crm"));
                 
                 Array especialidades = rs.getArray("especialidades");
				 for (Integer idEspecialidade : (Integer[]) especialidades.getArray()) {
					 medico.getEspecialidades().add(new EspecialidadeDAO().readById(idEspecialidade));
				 }
                 
                 
                 Usuario usuarioMedico = new Usuario();
                 usuarioMedico.setEmail(rs.getString("medico_email"));
                 
                 Pessoa pessoaMedico = new Pessoa();
                 pessoaMedico.setNome(rs.getString("medico_nome"));
                 pessoaMedico.setTelefone(rs.getString("medico_telefone"));
                 pessoaMedico.setDataNascimento(rs.getDate("medico_data_nascimento").toLocalDate());
                 
                 medico.setPessoa(pessoaMedico);
                 medico.setUsuario(usuarioMedico);
                 
                 Cliente cliente = new Cliente();
                 cliente.setId(rs.getInt("cliente_id"));
                 cliente.setCpf(rs.getString("cliente_cpf"));
                 
                 Pessoa pessoaCliente = new Pessoa();
                 pessoaCliente.setNome(rs.getString("cliente_nome"));
                 pessoaCliente.setTelefone(rs.getString("cliente_telefone"));
                 pessoaCliente.setDataNascimento(rs.getDate("cliente_data_nascimento").toLocalDate());
                 
                 cliente.setPessoa(pessoaCliente);
                 
                 consulta.setMedico(medico);
                 consulta.setCliente(cliente);

                 consultas.add(consulta);
             }
			} catch (SQLException e) {
				e.printStackTrace();
			}
         
         return consultas;
    }
}


