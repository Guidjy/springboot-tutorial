package com.compact.todosimple.services;

import com.compact.todosimple.models.User;
import com.compact.todosimple.models.enums.ProfileEnum;
import com.compact.todosimple.repositories.UserRepository;
import com.compact.todosimple.services.exceptions.DataBindingViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired  // "meio que um construtor do service"
    private UserRepository userRepository;

    public User findById(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException(
                "Usuário não encontrado. id:" + id + ", Tipo: " + User.class.getName()
        ));
    }

    @Transactional  // usado para inserções no banco. "Ou faz tudo, ou não faz nada. Não atualiza dados parcialmente"
    public User create(User obj) {
        // garante que um novo usuário será criado
        obj.setId(null);
        // salva a senha encriptada
        obj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        // transforma stream em lista de usuários de tipo 2 (USER)
        obj.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));

        obj = this.userRepository.save(obj);
        return obj;
    }

    @Transactional
    public User update(User obj) {
        // garante que esse usuario existe
        User newObj = findById(obj.getId());

        newObj.setPassword(obj.getPassword());
        newObj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        return this.userRepository.save(newObj);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir póis hà entidades relacionadas.");
        }
    }
}
