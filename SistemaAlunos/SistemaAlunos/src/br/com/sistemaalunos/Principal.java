package br.com.sistemaalunos;

import java.util.List;
import java.util.Scanner;

public class Principal {

    private static final Scanner scanner = new Scanner(System.in);
    private static final AlunoDao dao = new AlunoDao();

    public static void main(String[] args) {
        int opcao;

        do {
            System.out.println("\n****** MENU PRINCIPAL *******");
            System.out.println("1) Cadastrar Aluno");
            System.out.println("2) Mostrar Todos os Alunos");
            System.out.println("3) Mostrar Aluno Pelo RA");
            System.out.println("4) Alterar Aluno Pelo RA");
            System.out.println("5) Excluir Aluno Pelo RA");
            System.out.println("6) Finalizar Sistema");
            System.out.print("Digite a opção desejada: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1:
                    cadastrarAluno();
                    break;
                case 2:
                    mostrarTodos();
                    break;
                case 3:
                    mostrarPorRa();
                    break;
                case 4:
                    alterarAluno();
                    break;
                case 5:
                    excluirAluno();
                    break;
                case 6:
                    System.out.println("Sistema finalizado!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 6);

        scanner.close();
    }

    private static int lerInteiro() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Digite um número inteiro válido: ");
            }
        }
    }

    private static void cadastrarAluno() {
        System.out.print("Digite o RA: ");
        int ra = lerInteiro();

        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine().trim();

        if (nome.isEmpty()) {
            System.out.println("Nome não pode ficar vazio.");
            return;
        }

        if (dao.cadastrar(new Aluno(ra, nome))) {
            System.out.println("Aluno cadastrado com sucesso!");
        } else {
            System.out.println("Falha no cadastro.");
        }
    }

    private static void mostrarTodos() {
        List<Aluno> alunos = dao.listarTodos();

        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }

        for (Aluno aluno : alunos) {
            System.out.println(aluno);
        }
    }

    private static void mostrarPorRa() {
        System.out.print("Digite o RA: ");
        int ra = lerInteiro();

        Aluno aluno = dao.buscarPorRa(ra);

        if (aluno != null) {
            System.out.println(aluno);
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }

    private static void alterarAluno() {
        System.out.print("Digite o RA do aluno: ");
        int ra = lerInteiro();

        Aluno aluno = dao.buscarPorRa(ra);

        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        System.out.println("Aluno atual: " + aluno);
        System.out.print("Digite o novo nome: ");
        String nome = scanner.nextLine().trim();

        if (nome.isEmpty()) {
            System.out.println("Nome não pode ficar vazio.");
            return;
        }

        aluno.setNome(nome);

        if (dao.alterar(aluno)) {
            System.out.println("Aluno alterado com sucesso!");
        } else {
            System.out.println("Falha na alteração.");
        }
    }

    private static void excluirAluno() {
        System.out.print("Digite o RA do aluno: ");
        int ra = lerInteiro();

        Aluno aluno = dao.buscarPorRa(ra);

        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        System.out.println("Aluno encontrado: " + aluno);
        System.out.print("Confirma a exclusão? (S/N): ");

        String resposta = scanner.nextLine().trim();

        if (resposta.equalsIgnoreCase("S")) {
            if (dao.excluir(ra)) {
                System.out.println("Aluno excluído com sucesso!");
            } else {
                System.out.println("Falha na exclusão.");
            }
        } else {
            System.out.println("Exclusão cancelada.");
        }
    }
}
