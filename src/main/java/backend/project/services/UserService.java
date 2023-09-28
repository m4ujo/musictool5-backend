package backend.project.services;

import backend.project.entities.User;

public interface UserService {

    public User save(User user);

    public User inscribirCurso(Long idCurso) throws Exception;

}
