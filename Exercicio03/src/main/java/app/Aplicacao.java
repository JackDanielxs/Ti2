package app;

import static spark.Spark.*;
import service.PersonagemService;

public class Aplicacao {
	
	private static PersonagemService personagemService = new PersonagemService();
	
    public static void main(String[] args) {
        port(6789);
        
        staticFiles.location("/public");
        
        post("/personagem/insert", (request, response) -> personagemService.insert(request, response));

        get("/personagem/:id", (request, response) -> personagemService.get(request, response));
        
        get("/personagem/list/:orderby", (request, response) -> personagemService.getAll(request, response));

        get("/personagem/update/:id", (request, response) -> personagemService.getToUpdate(request, response));
        
        post("/personagem/update/:id", (request, response) -> personagemService.update(request, response));
           
        get("/personagem/delete/:id", (request, response) -> personagemService.delete(request, response)); 
    }
}