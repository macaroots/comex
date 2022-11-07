package br.com.alura.comex.repository;

import br.com.alura.comex.entity.Pedido;
import br.com.alura.comex.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByEmail(String email);
}
