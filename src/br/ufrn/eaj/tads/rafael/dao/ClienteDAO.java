package br.ufrn.eaj.tads.rafael.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.eaj.tads.rafael.model.Cliente;
import br.ufrn.eaj.tads.rafael.model.Pessoa;
import br.ufrn.eaj.tads.rafael.util.DatabaseUtil;

public class ClienteDAO {

    public List<Cliente> getAllClientesWithUsuarios() {
    	List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT c.id, c.cpf, p.id AS pessoa_id, p.nome, p.telefone, p.data_nascimento " +
                     "FROM cliente c " +
                     "JOIN pessoa p ON c.id_pessoa = p.id";

        try {
        	
        	Connection connection = DatabaseUtil.getConnection();
        	PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setCpf(rs.getString("cpf"));

                Pessoa pessoa = new Pessoa();
                pessoa.setId(rs.getInt("pessoa_id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setTelefone(rs.getString("telefone"));
                pessoa.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());

                cliente.setPessoa(pessoa);
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

	public Cliente addCliente(Cliente cliente) {
		String sql = "INSERT INTO pessoa (nome, telefone, data_nascimento) VALUES (?, ?, ?)";
		String sql2 = "INSERT INTO cliente (cpf, id_pessoa) VALUES (?, ?)";

		try {
			Connection connection = DatabaseUtil.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			PreparedStatement stmt2 = connection.prepareStatement(sql2);
			
			stmt.setString(1, cliente.getPessoa().getNome());
			stmt.setString(2, cliente.getPessoa().getTelefone());
			stmt.setDate(3, java.sql.Date.valueOf(cliente.getPessoa().getDataNascimento()));
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			int idPessoa = rs.getInt(1);

			stmt2.setString(1, cliente.getCpf());
			stmt2.setInt(2, idPessoa);
			stmt2.executeUpdate();
			int idCliente = rs.getInt(1);
			cliente.setId(idCliente);
			return cliente;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public boolean updateCliente(Cliente cliente) {
	    String sqlPessoa = "UPDATE pessoa SET nome = ?, data_nascimento = ?, telefone = ? WHERE id = ?";
	    String sqlCliente = "UPDATE cliente SET cpf = ? WHERE id = ?";

	    try {
	    	Connection conn = DatabaseUtil.getConnection();
	        PreparedStatement pstmtPessoa = conn.prepareStatement(sqlPessoa);
	        PreparedStatement pstmtCliente = conn.prepareStatement(sqlCliente);

	        // Update Pessoa
	        Pessoa pessoa = cliente.getPessoa();
	        pstmtPessoa.setString(1, pessoa.getNome());
	        pstmtPessoa.setDate(2, java.sql.Date.valueOf(pessoa.getDataNascimento()));
	        pstmtPessoa.setString(3, pessoa.getTelefone());
	        pstmtPessoa.setInt(4, pessoa.getId());
	        pstmtPessoa.executeUpdate();

	        // Update Cliente
	        pstmtCliente.setString(1, cliente.getCpf());
	        pstmtCliente.setInt(2, cliente.getId());
	        pstmtCliente.executeUpdate();

	        return true;
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	        return false;
	    }
	}


	public boolean deleteCliente(Cliente cliente) {
		String sql = "DELETE FROM cliente WHERE id = ?";
        try {
            Connection connection = DatabaseUtil.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cliente.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
}
