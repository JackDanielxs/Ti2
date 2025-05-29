package service;

import java.util.Scanner;
import java.io.File;
import java.util.List;
import dao.PersonagemDAO;
import model.Personagem;
import spark.Request;
import spark.Response;

public class PersonagemService {

	private PersonagemDAO personagemDAO = new PersonagemDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_NOME = 2;
	private final int FORM_ORDERBY_CLA = 3;
	private final int FORM_ORDERBY_RANK = 4;
	private final int FORM_ORDERBY_NATUREZAS = 5;
	
	public PersonagemService() {
		makeForm();
	}

	public void makeForm() {
		makeForm(FORM_INSERT, new Personagem(), FORM_ORDERBY_NOME);
	}

	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Personagem(), orderBy);
	}
	
	public void makeForm(int tipo, Personagem personagem, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umPersonagem = "";
		if(tipo != FORM_INSERT) {
			umPersonagem += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umPersonagem += "\t\t<tr>";
			umPersonagem += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/personagem/list/1\">Novo Personagem</a></b></font></td>";
			umPersonagem += "\t\t</tr>";
			umPersonagem += "\t</table>";
			umPersonagem += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/personagem/";
			String name, nome, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Personagem";
				nome = "leite, pão, ...";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + personagem.getId();
				name = "Atualizar Personagem (ID " + personagem.getId() + ")";
				nome = personagem.getNome();
				buttonLabel = "Atualizar";
			}
			umPersonagem += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umPersonagem += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umPersonagem += "\t\t<tr>";
			umPersonagem += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umPersonagem += "\t\t</tr>";
			umPersonagem += "\t\t<tr>";
			umPersonagem += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umPersonagem += "\t\t</tr>";
			umPersonagem += "\t\t<tr>";
			umPersonagem += "\t\t\t<td>&nbsp;Descrição: <input class=\"input--register\" type=\"text\" name=\"descricao\" value=\""+ nome +"\"></td>";
			umPersonagem += "\t\t\t<td>Clã: <input class=\"input--register\" type=\"text\" name=\"cla\" value=\""+ personagem.getCla() +"\"></td>";
			umPersonagem += "\t\t\t<td>Rank: <input class=\"input--register\" type=\"text\" name=\"rank\" value=\""+ personagem.getRank() +"\"></td>";
			umPersonagem += "\t\t</tr>";
			umPersonagem += "\t\t<tr>";
			umPersonagem += "\t\t\t<td>&nbsp;Naturezas: <input class=\"input--register\" type=\"number\" name=\"naturezas\" value=\""+ personagem.getNaturezas() + "\"></td>";
			umPersonagem += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umPersonagem += "\t\t</tr>";
			umPersonagem += "\t</table>";
			umPersonagem += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umPersonagem += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umPersonagem += "\t\t<tr>";
			umPersonagem += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Personagem (ID " + personagem.getId() + ")</b></font></td>";
			umPersonagem += "\t\t</tr>";
			umPersonagem += "\t\t<tr>";
			umPersonagem += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umPersonagem += "\t\t</tr>";
			umPersonagem += "\t\t<tr>";
			umPersonagem += "\t\t\t<td>&nbsp;Nome: "+ personagem.getNome() +"</td>";
			umPersonagem += "\t\t\t<td>Clã: "+ personagem.getCla() +"</td>";
			umPersonagem += "\t\t\t<td>Rank: "+ personagem.getRank() +"</td>";
			umPersonagem += "\t\t</tr>";
			umPersonagem += "\t\t<tr>";
			umPersonagem += "\t\t\t<td>&nbsp;Naturezas: "+ personagem.getNaturezas() + "</td>";
			umPersonagem += "\t\t\t<td>&nbsp;</td>";
			umPersonagem += "\t\t</tr>";
			umPersonagem += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-PERSONAGEM>", umPersonagem);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Personagens</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/personagem/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/personagem/list/" + FORM_ORDERBY_NOME + "\"><b>Nome</b></a></td>\n" +
        		"\t<td><a href=\"/personagem/list/" + FORM_ORDERBY_CLA + "\"><b>Clã</b></a></td>\n" +
				"\t<td><a href=\"/personagem/list/" + FORM_ORDERBY_RANK + "\"><b>Rank</b></a></td>\n" +
				"\t<td><a href=\"/personagem/list/" + FORM_ORDERBY_NATUREZAS + "\"><b>Naturezas</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Personagem> personagens;
		if (orderBy == FORM_ORDERBY_ID) {                 	personagens = personagemDAO.getOrderById();
		} else if (orderBy == FORM_ORDERBY_NOME) {			personagens = personagemDAO.getOrderByNome();
		} else if (orderBy == FORM_ORDERBY_CLA) {			personagens = personagemDAO.getOrderByCla();
		} else if (orderBy == FORM_ORDERBY_RANK) {			personagens = personagemDAO.getOrderByRank();					
		} else if (orderBy == FORM_ORDERBY_NATUREZAS){		personagens = personagemDAO.getOrderByNaturezas();
		} else{												personagens = personagemDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Personagem p : personagens) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getId() + "</td>\n" +
            		  "\t<td>" + p.getNome() + "</td>\n" +
            		  "\t<td>" + p.getCla() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/personagem/" + p.getId() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/personagem/update/" + p.getId() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeletePersonagem('" + p.getId() + "', '" + p.getNome() + "', '" + p.getCla() + "', '" + p.getNaturezas() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-PERSONAGEM>", list);		
	}
	
	public Object insert(Request request, Response response) {

		String nome = request.queryParams("nome");
		String cla = request.queryParams("cla");
		String rank = request.queryParams("rank");
		int naturezas = Integer.parseInt(request.queryParams("naturezas"));
		
		String resp = "";
		
		Personagem personagem = new Personagem(-1, nome, cla, rank, naturezas);
		
		if(personagemDAO.insert(personagem) == true) {
            resp = "Personagem (" + nome + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Personagem (" + nome + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Personagem personagem = (Personagem) personagemDAO.get(id);
		
		if (personagem != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, personagem, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Personagem " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Personagem personagem = (Personagem) personagemDAO.get(id);
		
		if (personagem != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, personagem, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Personagem " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		Personagem personagem = personagemDAO.get(id);
        String resp = "";       

        if (personagem != null) {
        	personagem.setNome(request.queryParams("nome"));
        	personagem.setCla(request.queryParams("cla"));
        	personagem.setRank(request.queryParams("rank"));
        	personagem.setNaturezas(Integer.parseInt(request.queryParams("naturezas")));
        	personagemDAO.update(personagem);
        	response.status(200); // success
            resp = "Personagem (ID " + personagem.getId() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Personagem (ID \" + personagem.getId() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Personagem personagem = personagemDAO.get(id);
        String resp = "";       

        if (personagem != null) {
            personagemDAO.delete(id);
            response.status(200); // success
            resp = "Personagem (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Personagem (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}