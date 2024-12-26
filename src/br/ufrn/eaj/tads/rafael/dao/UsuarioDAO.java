package br.ufrn.eaj.tads.rafael.dao;

import br.ufrn.eaj.tads.rafael.model.Usuario;
import br.ufrn.eaj.tads.rafael.util.AlertUtil;
import br.ufrn.eaj.tads.rafael.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void inserir(Usuario usuario) {
        String sql = "INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)";

        try {
        	Connection connection = DatabaseUtil.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.executeUpdate();

        } catch (SQLException e) {
            AlertUtil.showAlert("Erro ao tentar se conectar com o banco: " + e.getMessage());
        }
    }

    public void atualizar(Usuario usuario) {
        String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE id = ?";

        try {
        	Connection connection = DatabaseUtil.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setLong(4, usuario.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            AlertUtil.showAlert("Erro ao tentar se conectar com o banco: " + e.getMessage());
        }
    }

    public void deletar(Long id) {
        String sql = "DELETE FROM usuario WHERE id = ?";

        try {
        	Connection connection = DatabaseUtil.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
        	AlertUtil.showAlert("Erro ao tentar se conectar com o banco: " + e.getMessage());
        }
    }

    public Usuario buscarPorId(Long id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        Usuario usuario = null;

        try {
        	Connection connection = DatabaseUtil.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getLong("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
            }

        } catch (SQLException e) {
           AlertUtil.showAlert("Erro ao tentar se conectar com o banco: " + e.getMessage());
        }

        return usuario;
    }

    public List<Usuario> buscarTodos() {
        String sql = "SELECT * FROM usuario";
        List<Usuario> usuarios = new ArrayList<>();

        try {
        	Connection connection = DatabaseUtil.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getLong("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            AlertUtil.showAlert("Erro ao tentar se conectar com o banco: " + e.getMessage());
        }

        return usuarios;
    }
    
	public Usuario logar(String email, String senha) {
        String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
        Usuario usuario = null;

        try {
        	Connection connection = DatabaseUtil.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            
            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getLong("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
            }

        } catch (SQLException e) {
            AlertUtil.showAlert("Erro ao tentar se conectar com o banco: " + e.getMessage());
        }
        
        return usuario;
	}
}
