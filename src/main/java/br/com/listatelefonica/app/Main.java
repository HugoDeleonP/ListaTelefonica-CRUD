package br.com.listatelefonica.app;

import br.com.listatelefonica.model.Contato;
import br.com.listatelefonica.dao.ContatoDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args){

        inicio();

    }

    public static void inicio(){

        boolean sair = false;

        System.out.println("=========================| Lista telefônica|=========================");
        System.out.println(" 1. Cadastrar contato: Nome, Telefone, Email, Observação.");
        System.out.println(" 2. Listar todos os contatos cadastrados.");
        System.out.println(" 3. Buscar contato por nome.");
        System.out.println(" 4. Atualizar dados de um contato (telefone, email, observação).");
        System.out.println(" 5. Remover contato.");
        System.out.println(" 6. Sair do sistema.");

        int opcao = input.nextInt();
        input.nextLine();

        switch (opcao){
            case 1 ->{
                cadastrarContato();
            }

            case 2 ->{
                listarContatos();
            }

            case 3 ->{
                listarContatos_nome();
            }

            case 4 ->{
                atualizarContato();
            }

            case 5 ->{
                deletarDados();
            }

            case 6 ->{
                sair = true;
                break;
            }
        }

        if(!sair){
            inicio();
        }

    }

    public static void cadastrarContato(){
        System.out.println("=======================| Cadastro de contato|=======================");
        System.out.println("Digite o nome do contato: ");
        String nome = input.nextLine();

        System.out.println("Digite o telefone do contato: ");
        String telefone = input.nextLine();

        System.out.println("Digite o email do contato: ");
        String email = input.nextLine();

        System.out.println("Digite uma observação: ");
        String observacao = input.nextLine();

        var contato = new Contato(nome, telefone, email, observacao);

        try{
            ContatoDAO.inserirContato(contato);
        } catch (SQLException e){
            System.out.println("Erro ao cadastrar contato: " + e.getMessage());
        }



    }

    public static void listarContatos(){
        System.out.println("============================| Contatos |============================");

        try{
            List<Contato> contatos = ContatoDAO.listarContatos();

            listagem(contatos);

        }
        catch (SQLException e){
            System.out.println("Erro na conexão com o banco de dados.");
            e.printStackTrace();
        }
    }

    public static void listarContatos_nome(){
        System.out.println("============================| Contatos |============================");
        System.out.println("Digite o nome do contato: ");
        String nome = input.nextLine();

        try{
            List<Contato> contatos = ContatoDAO.listarContatos_nome(nome);

            listagem(contatos);
        }
        catch (SQLException e){
            System.out.println("Erro na conexão com o banco de dados.");
            e.printStackTrace();
        }

    }

    public static List<Integer> listagem(List<Contato> contatos){

        List<Integer> idContatos = new ArrayList<>();

        for(Contato contatoUnit : contatos){
            System.out.println("ID: " + contatoUnit.getId());
            System.out.println("Nome: " +contatoUnit.getNome());
            System.out.println("Telefone: " + contatoUnit.getTelefone());
            System.out.println("Email: " + contatoUnit.getEmail());
            System.out.println("Observação: " + contatoUnit.getObservacao() + "\n");


            idContatos.add(contatoUnit.getId());
        }

        return idContatos;
    }

    public static void atualizarContato(){
        var contatoDao = new ContatoDAO();

        List<Contato> contatos = new ArrayList<>();
        List<Integer> idContatos = new ArrayList<>();

        try{
            contatos = ContatoDAO.listarContatos();
            idContatos = listagem(contatos);

        } catch (SQLException e){
            System.out.println("Erro na conexão com o banco de dados.");
            e.printStackTrace();
        }

        System.out.println("Digite o id do contato que deseja alterar: ");
        int idDigitado = input.nextInt();
        input.nextLine();

        if(idContatos.contains(idDigitado)){
            inserirDados(2, idDigitado);
        } else {
            System.out.println("Digite outro contato que esteja na lista.");
            atualizarContato();
        }
    }

    public static void inserirDados(int opcao, int id){

        var contatoDao = new ContatoDAO();

        System.out.println("Digite o nome do contato: ");
        String nome = input.nextLine();

        System.out.println("Digite o telefone do contato: ");
        String telefone = input.nextLine();

        System.out.println("Digite o email do contato: ");
        String email = input.nextLine();

        System.out.println("Digite uma observação: ");
        String observacao = input.nextLine();


        switch (opcao){

            case 1->{
                var contato = new Contato(nome, telefone, email, observacao);
                try{
                    contatoDao.inserirContato(contato);
                    System.out.println("Contato inserido com sucesso!");
                } catch (SQLException e) {
                    System.out.println("Erro no banco de dados!");
                    e.printStackTrace();
                }

            }

            case 2->{
                var contato = new Contato(id, nome, telefone, email, observacao);
                try{
                    contatoDao.atualizarContato(contato);
                    System.out.println("\nContato atualizado com sucesso!");
                } catch (SQLException e){
                    System.out.println("Erro no banco de dados!");
                    e.printStackTrace();
                }
            }

        }

    }

    public static void deletarDados(){
        var contatoDao = new ContatoDAO();

        List<Contato> contatos = new ArrayList<>();
        List<Integer> idContatos = new ArrayList<>();

        System.out.println("Digite o id do contato que queira deletar: ");
        int id = input.nextInt();
        input.nextLine();

        try{
            contatos = contatoDao.listarContatos();
            idContatos = listagem(contatos);

            if(idContatos.contains(id)){
                contatoDao.deletarContato(id);
            }
            else{
                System.out.println("Digite outro contato que esteja na lista.");
                deletarDados();
            }

        } catch (SQLException e) {
            System.out.println("Erro de conexão com o banco de dados.");
            e.printStackTrace();
        }




    }

}
