package service;

import java.util.Scanner;
import java.io.File;
import java.util.List;
import Dao.UsuarioDAO;
import models.Usuario;
import spark.Request;
import spark.Response;



public class UsuarioService {

	private UsuarioDAO UsuarioDAO = new UsuarioDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_CODIGO = 1;
	private final int FORM_ORDERBY_NOME = 2;
	private final int FORM_ORDERBY_IDADE = 3;
	
	
	public UsuarioService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Usuario(), FORM_ORDERBY_NOME);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Usuario(), orderBy);
	}

	
	public void makeForm(int tipo, Usuario Usuario, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umUsuario = "";
		if(tipo != FORM_INSERT) {
			umUsuario += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umUsuario += "\t\t<tr>";
			umUsuario += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/Usuario/list/1\">Novo Usuario</a></b></font></td>";
			umUsuario += "\t\t</tr>";
			umUsuario += "\t</table>";
			umUsuario += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/usuario/";
			String name, email, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Usuario";
				email = "exemplo@email.com";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + Usuario.getCodigo();
				name = "Atualizar Usuario (ID " + Usuario.getCodigo() + ")";
				email = Usuario.getEmail();
				buttonLabel = "Atualizar";
			}
			umUsuario += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umUsuario += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umUsuario += "\t\t<tr>";
			umUsuario += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umUsuario += "\t\t</tr>";
			umUsuario += "\t\t<tr>";
			umUsuario += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umUsuario += "\t\t</tr>";
			umUsuario += "\t\t<tr>";
			umUsuario += "\t\t\t<td>&nbsp;Email: <input class=\"input--register\" type=\"text\" name=\"email\" value=\""+ email +"\"></td>";
			umUsuario += "\t\t\t<td>Idade: <input class=\"input--register\" type=\"text\" name=\"idade\" value=\""+ Usuario.getIdade() +"\"></td>";
			umUsuario += "\t\t\t<td>Codigo: <input class=\"input--register\" type=\"text\" name=\"codigo\" value=\""+ Usuario.getCodigo() +"\"></td>";
			umUsuario += "\t\t</tr>";
			umUsuario += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umUsuario += "\t\t</tr>";
			umUsuario += "\t</table>";
			umUsuario += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umUsuario += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umUsuario += "\t\t<tr>";
			umUsuario += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Usuario (ID " + Usuario.getCodigo() + ")</b></font></td>";
			umUsuario += "\t\t</tr>";
			umUsuario += "\t\t<tr>";
			umUsuario += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umUsuario += "\t\t</tr>";
			umUsuario += "\t\t<tr>";
			umUsuario += "\t\t\t<td>&nbsp;Descrição: "+ Usuario.getEmail() +"</td>";
			umUsuario += "\t\t\t<td>Idade: "+ Usuario.getIdade() +"</td>";
			umUsuario += "\t\t\t<td>Codigo: "+ Usuario.getCodigo() +"</td>";
			umUsuario += "\t\t</tr>";
			umUsuario += "\t\t\t<td>&nbsp;</td>";
			umUsuario += "\t\t</tr>";
			umUsuario += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-Usuario>", umUsuario);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Usuarios</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/Usuario/list/" + FORM_ORDERBY_CODIGO + "\"><b>Codigo</b></a></td>\n" +
        		"\t<td><a href=\"/Usuario/list/" + FORM_ORDERBY_NOME + "\"><b>Nome</b></a></td>\n" +
        		"\t<td><a href=\"/Usuario/list/" + FORM_ORDERBY_IDADE + "\"><b>Idade</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Usuario> Usuarios;
		if (orderBy == FORM_ORDERBY_CODIGO) {                 	Usuarios = UsuarioDAO.getOrderByCodigo();
		} else if (orderBy == FORM_ORDERBY_NOME) {		Usuarios = UsuarioDAO.getOrderByNome();
		} else if (orderBy == FORM_ORDERBY_IDADE) {			Usuarios = UsuarioDAO.getOrderByIdade();
		} else {											Usuarios = UsuarioDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Usuario p : Usuarios) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getCodigo() + "</td>\n" +
            		  "\t<td>" + p.getNome() + "</td>\n" +
            		  "\t<td>" + p.getIdade() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/Usuario/" + p.getCodigo() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/Usuario/update/" + p.getCodigo() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteUsuario('" + p.getCodigo() + "', '" + p.getNome() + "', '" + p.getIdade() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-Usuario>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String nome = request.queryParams("nome");
		String email = request.queryParams("email");
		int idade = Integer.parseInt(request.queryParams("idade"));
		int codigo = Integer.parseInt(request.queryParams("codigo"));
		
		String resp = "";
		
		Usuario Usuario = new Usuario(codigo, nome, email, idade);
		
		if(UsuarioDAO.insert(Usuario) == true) {
            resp = "Usuario (" + nome + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Usuario (" + nome + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" codigo=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int codigo = Integer.parseInt(request.params(":codigo"));		
		Usuario Usuario = (Usuario) UsuarioDAO.get(codigo);
		
		if (Usuario != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, Usuario, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Usuario " + codigo + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hiden\" codigo=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hiden\" codigo=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":codigo"));		
		Usuario Usuario = (Usuario) UsuarioDAO.get(id);
		
		if (Usuario != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, Usuario, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Usuario " + id + " não encontrado.";
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
	
	@SuppressWarnings("null")
	public Object update(Request request, Response response) {
        int codigo = Integer.parseInt(request.params(":codigo"));
		Usuario Usuario = UsuarioDAO.get(codigo);
        String resp = "";       

        if (Usuario != null) {
        	Usuario.setEmail(request.queryParams("email"));
        	Usuario.setNome(request.queryParams("nome"));
        	Usuario.setIdade(Integer.parseInt(request.queryParams("idade")));
        	Usuario.setCodigo(Integer.parseInt(request.queryParams("codigo")));
        	
        	UsuarioDAO.update(Usuario);
        	response.status(200); // success
            resp = "Usuario (codigo " + Usuario.getCodigo() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Usuario (codigo \\" + Usuario.getCodigo() + "\\) não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":codigo"));
        Usuario Usuario = UsuarioDAO.get(id);
        String resp = "";       

        if (Usuario != null) {
            UsuarioDAO.delete(id);
            response.status(200); // success
            resp = "Usuario (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Usuario (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}