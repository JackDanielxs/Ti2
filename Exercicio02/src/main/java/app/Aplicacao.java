package app;

import java.util.*;

import dao.PersonagemDAO;
import model.Personagem;

public class Aplicacao {
	
	public static void main(String[] args) throws Exception {
		
		PersonagemDAO personagemDAO = new PersonagemDAO();
		
		Scanner sc = new Scanner(System.in);
        int opcao;
        int order;
        int ids;
        boolean sucesso;
        boolean sure;
        String cad1;
        int cad2;
        Personagem aux = new Personagem();
        
        do {
            System.out.println("\n===== Manipulando personagens de Naruto =====");
            System.out.println("1 - Listar personagens cadastrados");
            System.out.println("2 - Cadastrar novo personagem");
            System.out.println("3 - Editar um personagem");
            System.out.println("4 - Deletar um personagem");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");
            
            opcao = sc.nextInt();
            sc.nextLine();
            
            switch (opcao) {
                case 1:
                	System.out.println("1 - Ordenar por Id");
                    System.out.println("2 - Ordenar por Nome");
                    System.out.println("3 - Ordenar por Clã");
                    System.out.println("4 - Ordenar por Rank");
                    System.out.println("5 - Ordenar por Naturezas de Chakra");
                    System.out.print("Escolha uma opção: ");
                    
                	order = sc.nextInt();
                	sc.nextLine();
                	List<Personagem> per = new ArrayList<Personagem>();
                	
                	switch (order) {
                    case 1 -> per = personagemDAO.getOrderById();
                    case 2 -> per = personagemDAO.getOrderByNome();
                    case 3 -> per = personagemDAO.getOrderByCla();
                    case 4 -> per = personagemDAO.getOrderByRank();
                    case 5 -> per = personagemDAO.getOrderByNaturezas();
                    default -> System.out.println("Opção inválida! Tente novamente.");
                }
                	per.forEach(p -> System.out.println(p.toString()));
                    break;
                    
                case 2:
                	aux.setId(personagemDAO.recuperarId());
                	System.out.println("Nome: ");
                	aux.setNome(sc.nextLine());
                	System.out.println("Clã: ");
                	aux.setCla(sc.nextLine());
                	System.out.println("Rank: ");
                	aux.setRank(sc.nextLine());
                	System.out.println("Naturezas: ");
                	aux.setNaturezas(sc.nextInt());
                	sc.nextLine();
                	sucesso = personagemDAO.insert(aux);
                	System.out.println(sucesso ? "Personagem cadastrado." : "Erro ao cadastrar personagem.");
                	break;

                case 3:
                    System.out.println("Insira o Id do personagem a ser editado: ");
                    ids = sc.nextInt();
                    sc.nextLine();
                    aux = personagemDAO.get(ids);
                    if(aux != null) {
                    	System.out.println(aux.toString());
                    }
                    else {
                    	System.out.println("Personagem não encontrado.");
                    	break;
                    }
                    System.out.println("Novo nome: ");
                    cad1 = sc.nextLine();
                    aux.setNome(cad1);
                    System.out.println("Novo clã: ");
                    cad1 = sc.nextLine();
                    aux.setCla(cad1);
                    System.out.println("Novo rank: ");
                    cad1 = sc.nextLine();
                    aux.setRank(cad1);
                    System.out.println("Novas naturezas: ");
                    cad2 = sc.nextInt();
                    sc.nextLine();
                    aux.setNaturezas(cad2);
                    sucesso = personagemDAO.update(aux);
                    System.out.println(sucesso ? "Personagem editado." : "Erro ao editar personagem.");
                    break;
                    
                case 4:
                    System.out.println("Insira o Id do personagem a ser deletado: ");
                    ids = sc.nextInt();
                    sc.nextLine();
                    aux = personagemDAO.get(ids);
                    if(aux != null) {
                    	System.out.println(aux.toString());
                    }
                    else {
                    	System.out.println("Personagem não encontrado.");
                    	break;
                    }
                    System.out.println("Deletar " + aux.getNome() + "? Digite 1 para confirmar e 0 para cancelar -> ");
                    sure = sc.nextInt() == 1 ? true : false;
                    if(sure) {
                    	sucesso = personagemDAO.delete(ids);
                        System.out.println(sucesso ? "Personagem deletado." : "Erro ao deletar personagem.");
                    }
                    else {
                        System.out.println("Ação cancelada.");
                    }
                    break;
                    
                case 5:
                    System.out.println("Saindo do programa...");
                    break;
                    
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 5);
        
        sc.close();	
	}
}
