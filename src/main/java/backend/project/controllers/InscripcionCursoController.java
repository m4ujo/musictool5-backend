package backend.project.controllers;

import backend.project.entities.User;
import backend.project.repositories.CursoRepository;
import backend.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class InscripcionCursoController {

    @Autowired
    UserService userService;

    @PostMapping("/inscripcion-curso")
    public ResponseEntity<User> createUser(@RequestBody Long idCurso) throws Exception {
        User user = userService.inscribirCurso(idCurso);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

}
