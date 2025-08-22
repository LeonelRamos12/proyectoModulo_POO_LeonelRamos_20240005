package ProyectoModulo_LEONELRAMOS_20240005.LEONELRAMOS_20240005.Repository;


import ProyectoModulo_LEONELRAMOS_20240005.LEONELRAMOS_20240005.Entities.EntityLibros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryLibros extends JpaRepository<EntityLibros, Long> {
}
