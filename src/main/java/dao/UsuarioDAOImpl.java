package dao;

import java.sql.*;
import java.util.*;
import jdbc.ConnectionProvider;
import model.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO {

	public int insert(Usuario usuario) {
		try {
			String sql2 = "SELECT tipo_atraccion_id FROM tipo_atraccion WHERE tipo_atraccion.nombre = ?";
			String sql = "INSERT INTO usuarios (nombre, tipo_preferido_id, monedas, tiempo) VALUES (?, ?, ?, ?)";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement2 = conn.prepareStatement(sql2);
			PreparedStatement statement = conn.prepareStatement(sql);
			statement2.setString(1, usuario.getTipoPreferido().toString());
			ResultSet resultadoId = statement2.executeQuery();
			statement.setString(1, usuario.getNombre());
			statement.setInt(2, resultadoId.getInt(1));
			statement.setInt(3, usuario.getMonedas());
			statement.setDouble(4, usuario.getTiempoDisponible());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int update(Usuario usuario) {
		try {
			String sql = "UPDATE usuario SET monedas = ?, tiempo = ? WHERE nombre = ?";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, usuario.getMonedas());
			statement.setDouble(2, usuario.getTiempoDisponible());
			statement.setString(3, usuario.getNombre());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

/*	public int delete(Usuario user) {
		try {
			String sql = "DELETE FROM USERS WHERE USERNAME = ?";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, user.getNombre());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
*/

	public Usuario findByUsername(String username) {
		try {
			String sql = "SELECT * FROM usuario WHERE nombre = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet resultados = statement.executeQuery();

			Usuario user = null;

			if (resultados.next()) {
				user = toUser(resultados);
			}

			return user;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	/*
	public int countAll() {
		try {
			String sql = "SELECT COUNT(1) AS TOTAL FROM USERS";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			resultados.next();
			int total = resultados.getInt("TOTAL");

			return total;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
*/
	/*
	public List<Usuario> findAll() {
		try {
			String sql = "SELECT * FROM USERS";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			List<Usuario> usuarios = new LinkedList<Usuario>();
			while (resultados.next()) {
				usuarios.add(toUser(resultados));
			}

			return usuarios;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
*/
	
	private Usuario toUser(ResultSet resultados) throws SQLException {
		return new Usuario(resultados.getInt(1), resultados.getString(2), resultados.getString(3), resultados.getInt(4), resultados.getDouble(5));
	}

}
