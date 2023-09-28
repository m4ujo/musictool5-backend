package backend.project.servicesimpl;

import backend.project.entities.Curso;
import backend.project.entities.User;
import backend.project.repositories.CursoRepository;
import backend.project.repositories.UserRepository;
import backend.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CursoRepository cursoRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User inscribirCurso(Long idCurso) throws Exception {
        try {
            User user = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
            Curso curso = cursoRepository.findById(idCurso).get();
            user.getCourses().add(curso);
            return userRepository.save(user);
        }
        catch (Exception ex){
            throw new Exception("");
        }
    }
}
