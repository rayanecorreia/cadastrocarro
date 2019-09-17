package br.edu.ifal.cadastrocarro;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class Controlador{
    @Autowired
    CarroRepositorio repo;

    @RequestMapping("/")
    public ModelAndView index(){
        return new ModelAndView("index.html");
    }

    @RequestMapping("/formulario")
    public ModelAndView formulario(Carro a){
    	  ModelAndView retorno = new ModelAndView("formulario.html");
          retorno.addObject("carro", a);
          return retorno;
    }

  
    @RequestMapping("/cadastro")
        public ModelAndView cadastroAluno(Carro carro, RedirectAttributes redirect) {
            repo.save(carro);
            ModelAndView retorno = new ModelAndView("redirect:/Listar_carros");
            return retorno;
    }
    

    @RequestMapping("/Listar_carros")
    public ModelAndView listarAlunos() {
    ModelAndView retorno = new ModelAndView("Listar_carros.html");
        Iterable<Carro> carros = repo.findAll();
        retorno.addObject("carros", carros);
       return retorno;
    }
    
    @RequestMapping("/excluir_carro/{idCarro}")
    public ModelAndView excluirAlunos(@PathVariable("idCarro") Long carroID, RedirectAttributes redirect) {
        Optional<Carro> opcao = repo.findById(carroID);
        ModelAndView retorno = new ModelAndView("redirect:/Listar_carros");        
        if(opcao.isPresent()){
            Carro a = opcao.get();
            repo.deleteById(a.getId());            
            return retorno;
        } else {
            redirect.addFlashAttribute("mensagem", "Carro não existe no Banco de Dados.");
            return retorno;
        }
        
    }

    @RequestMapping("/atualizar_carro/{idCarro}")
    public ModelAndView atualizar(@PathVariable("idCarro") Long carroID) {
        Optional<Carro> opcao = repo.findById(carroID);
        ModelAndView retorno = new ModelAndView("formulario.html");
        if(opcao.isPresent()){
            Carro a = opcao.get();
            retorno.addObject("carro", a);
            return retorno;
        }
        return retorno;
    }
    }
