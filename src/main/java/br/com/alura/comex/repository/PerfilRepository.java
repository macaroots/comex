package br.com.alura.comex.repository;

import br.com.alura.comex.entity.Perfil;
import br.com.alura.comex.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

}
