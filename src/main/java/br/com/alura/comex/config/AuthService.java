package br.com.alura.comex.config;

import br.com.alura.comex.entity.Usuario;
import br.com.alura.comex.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            System.out.println("AUTH" + username);
            Usuario usuario = repository.findByNome(username);
            return usuario;
        } catch (Exception e) {
            System.out.println("AUTH ERRO" + e);
            throw new UsernameNotFoundException("Dados inválidos", e);
        }
    }
}
