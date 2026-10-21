
package br.com.sistemaalunos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoDao {

    // Cadastrar aluno
    public boolean cadastrar(Aluno aluno) {
        String sql = "INSERT INTO aluno (ra, nome) VALUES (?, ?)";

        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, aluno.getRa());
            ps.setString(2, aluno.getNome());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
            return false;
        }
    }

    // Mostrar todos os alunos
    public List<Aluno> listarTodos() {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT ra, nome FROM aluno ORDER BY ra";

        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                alunos.add(new Aluno(
                    rs.getInt("ra"),
                    rs.getString("nome")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar: " + e.getMessage());
        }

        return alunos;
    }

    // Buscar aluno pelo RA
    public Aluno buscarPorRa(int ra) {
        String sql = "SELECT ra, nome FROM aluno WHERE ra = ?";

        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, ra);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Aluno(
                        rs.getInt("ra"),
                        rs.getString("nome")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar: " + e.getMessage());
        }

        return null;
    }

    // Alterar aluno
    public boolean alterar(Aluno aluno) {
        String sql = "UPDATE aluno SET nome = ? WHERE ra = ?";

        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, aluno.getNome());
            ps.setInt(2, aluno.getRa());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao alterar: " + e.getMessage());
            return false;
        }
    }

    // Excluir aluno
    public boolean excluir(int ra) {
        String sql = "DELETE FROM aluno WHERE ra = ?";

        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, ra);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
            return false;
        }
    }
}
