package br.ufrn.eaj.tads.rafael.dao;

import br.ufrn.eaj.tads.rafael.model.Especialidade;
import br.ufrn.eaj.tads.rafael.model.Medico;
import br.ufrn.eaj.tads.rafael.model.Pessoa;
import br.ufrn.eaj.tads.rafael.model.Usuario;
import br.ufrn.eaj.tads.rafael.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {

    public Medico addMedico(Medico medico) {
        String sqlPessoa = "INSERT INTO pessoa (nome, telefone, data_nascimento) VALUES (?, ?, ?)";
        String sqlUsuario = "INSERT INTO usuario (email, senha, id_pessoa) VALUES (?, ?, ?)";
        String sqlMedico = "INSERT INTO medico (crm, id_pessoa, id_usuario) VALUES (?, ?, ?)";
        String sqlEspecialidade = "INSERT INTO medico_especialidade (id_medico, id_especialidade) VALUES (?, ?)";

        try {
        	Connection connection = DatabaseUtil.getConnection();
            // Inserir pessoa
            PreparedStatement stmtPessoa = connection.prepareStatement(sqlPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
            stmtPessoa.setString(1, medico.getPessoa().getNome());
            stmtPessoa.setString(2, medico.getPessoa().getTelefone());
            stmtPessoa.setDate(3, Date.valueOf(medico.getPessoa().getDataNascimento()));
            stmtPessoa.executeUpdate();

            ResultSet rsPessoa = stmtPessoa.getGeneratedKeys();
            rsPessoa.next();
            int idPessoa = rsPessoa.getInt(1);

            // Inserir usuário
            PreparedStatement stmtUsuario = connection.prepareStatement(sqlUsuario, PreparedStatement.RETURN_GENERATED_KEYS);
            stmtUsuario.setString(1, medico.getUsuario().getEmail());
            stmtUsuario.setString(2, medico.getUsuario().getSenha());
            stmtUsuario.setInt(3, idPessoa);
            stmtUsuario.executeUpdate();

            ResultSet rsUsuario = stmtUsuario.getGeneratedKeys();
            rsUsuario.next();
            int idUsuario = rsUsuario.getInt(1);

            // Inserir médico
            PreparedStatement stmtMedico = connection.prepareStatement(sqlMedico, PreparedStatement.RETURN_GENERATED_KEYS);
            stmtMedico.setString(1, medico.getCrm());
            stmtMedico.setInt(2, idPessoa);
            stmtMedico.setInt(3, idUsuario);
            stmtMedico.executeUpdate();

            ResultSet rsMedico = stmtMedico.getGeneratedKeys();
            rsMedico.next();
            int idMedico = rsMedico.getInt(1);
            medico.setId(idMedico);

            // Inserir especialidades
            if (medico.getEspecialidades() != null) {
                for (Especialidade especialidade : medico.getEspecialidades()) {
                    PreparedStatement stmtEspecialidade = connection.prepareStatement(sqlEspecialidade);
                    stmtEspecialidade.setInt(1, idMedico);
                    stmtEspecialidade.setInt(2, especialidade.getId());
                    stmtEspecialidade.executeUpdate();
                }
            }

            return medico;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Medico> getAllMedicos() {
        String sql = "SELECT m.id AS medico_id, m.crm, p.id AS pessoa_id, p.nome, p.telefone, p.data_nascimento, u.id AS usuario_id, u.email " +
                     "FROM medico m " +
                     "JOIN pessoa p ON m.id_pessoa = p.id " +
                     "JOIN usuario u ON m.id_usuario = u.id";

        List<Medico> medicos = new ArrayList<>();

        try {
        	
        	Connection connection = DatabaseUtil.getConnection();
        	 PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Medico medico = new Medico();
                medico.setId(rs.getInt("medico_id"));
                medico.setCrm(rs.getString("crm"));

                Pessoa pessoa = new Pessoa();
                pessoa.setId(rs.getInt("pessoa_id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setTelefone(rs.getString("telefone"));
                pessoa.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                medico.setPessoa(pessoa);

                Usuario usuario = new Usuario();
                usuario.setId(rs.getLong("usuario_id"));
                usuario.setEmail(rs.getString("email"));
                medico.setUsuario(usuario);

                medico.setEspecialidades(getEspecialidadesByMedico(medico.getId()));
                medicos.add(medico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicos;
    }

    private List<Especialidade> getEspecialidadesByMedico(int medicoId) {
        String sql = "SELECT e.id, e.nome " +
                     "FROM medico_especialidade me " +
                     "JOIN especialidade e ON me.id_especialidade = e.id " +
                     "WHERE me.id_medico = ?";

        List<Especialidade> especialidades = new ArrayList<>();

        try {
        	Connection connection = DatabaseUtil.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, medicoId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Especialidade especialidade = new Especialidade();
                especialidade.setId(rs.getInt("id"));
                especialidade.setNome(rs.getString("nome"));
                especialidades.add(especialidade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return especialidades;
    }

    public boolean deleteMedico(Medico medico) {
    	String sqlMedicoEspecialidade  = "DELETE FROM medico_especialidade WHERE id_medico = ?";
    	String sqlMedico  = "DELETE FROM medico WHERE id = ?";
    	String sqlMedicoUsuario = "DELETE FROM usuario WHERE id_pessoa = ?";
    	String sqlMedicoPessoa = "DELETE FROM pessoa WHERE id = ?";
    	

        try {
        	Connection connection = DatabaseUtil.getConnection();
        	
            PreparedStatement stmt = connection.prepareStatement(sqlMedicoEspecialidade);
            stmt.setInt(1, medico.getId());
            stmt.executeUpdate();
            
            stmt = connection.prepareStatement(sqlMedico);
            stmt.setInt(1, medico.getId());
            stmt.executeUpdate();
            
            stmt = connection.prepareStatement(sqlMedicoUsuario);
            stmt.setInt(1, medico.getPessoa().getId());
            stmt.executeUpdate();
            
            stmt = connection.prepareStatement(sqlMedicoPessoa);
            stmt.setInt(1, medico.getPessoa().getId());
            stmt.executeUpdate();
            
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateMedico(Medico medico) {
        String sqlPessoa = "UPDATE pessoa SET nome = ?, telefone = ?, data_nascimento = ? WHERE id = ?";
        String sqlUsuario = "UPDATE usuario SET email = ? WHERE id = ?";
        String sqlMedico = "UPDATE medico SET crm = ? WHERE id = ?";
        String sqlDeleteEspecialidades = "DELETE FROM medico_especialidade WHERE id_medico = ?";
        String sqlInsertEspecialidade = "INSERT INTO medico_especialidade (id_medico, id_especialidade) VALUES (?, ?)";

        try {
            Connection connection = DatabaseUtil.getConnection();

            // Update pessoa
            PreparedStatement stmtPessoa = connection.prepareStatement(sqlPessoa);
            stmtPessoa.setString(1, medico.getPessoa().getNome());
            stmtPessoa.setString(2, medico.getPessoa().getTelefone());
            stmtPessoa.setDate(3, Date.valueOf(medico.getPessoa().getDataNascimento()));
            stmtPessoa.setInt(4, medico.getPessoa().getId());
            stmtPessoa.executeUpdate();

            
            PreparedStatement stmtUsuario = connection.prepareStatement(sqlUsuario);
            stmtUsuario.setString(1, medico.getUsuario().getEmail());
            stmtUsuario.setLong(2, medico.getUsuario().getId());
            stmtUsuario.executeUpdate();

            
            PreparedStatement stmtMedico = connection.prepareStatement(sqlMedico);
            stmtMedico.setString(1, medico.getCrm());
            stmtMedico.setInt(2, medico.getId());
            stmtMedico.executeUpdate();

            
            PreparedStatement stmtDeleteEspecialidades = connection.prepareStatement(sqlDeleteEspecialidades);
            stmtDeleteEspecialidades.setInt(1, medico.getId());
            stmtDeleteEspecialidades.executeUpdate();

            
            if (medico.getEspecialidades() != null) {
                for (Especialidade especialidade : medico.getEspecialidades()) {
                    PreparedStatement stmtInsertEspecialidade = connection.prepareStatement(sqlInsertEspecialidade);
                    stmtInsertEspecialidade.setInt(1, medico.getId());
                    stmtInsertEspecialidade.setInt(2, especialidade.getId());
                    stmtInsertEspecialidade.executeUpdate();
                }
            }
            
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
