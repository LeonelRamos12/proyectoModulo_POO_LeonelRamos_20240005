package ProyectoModulo_LEONELRAMOS_20240005.LEONELRAMOS_20240005.Service;


import ProyectoModulo_LEONELRAMOS_20240005.LEONELRAMOS_20240005.Entities.EntityLibros;
import ProyectoModulo_LEONELRAMOS_20240005.LEONELRAMOS_20240005.Exceptions.ExceptionLibroNoEncontrado;
import ProyectoModulo_LEONELRAMOS_20240005.LEONELRAMOS_20240005.Models.DTO.DTOLibros;
import ProyectoModulo_LEONELRAMOS_20240005.LEONELRAMOS_20240005.Repository.RepositoryLibros;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ServiceLibros {

    //inyeccion de la interface repository
    @Autowired
    private RepositoryLibros repo;

    //metodo para mostrar datos
    public List<DTOLibros> obtenerDatos() {
        List<EntityLibros> list = repo.findAll();
        return list.stream()
                .map(this::ConvertirADTO)
                .collect(Collectors.toList());
    }

    //metodo para convertir los datos en formato dto y poder mostrarlos
    private DTOLibros ConvertirADTO(EntityLibros entityLibros) {
        DTOLibros libros = new DTOLibros();
        libros.setId(entityLibros.getId());
        libros.setTitulo(entityLibros.getTitulo());
        libros.setIsbn(entityLibros.getIsbn());
        libros.setAño_publicacion(entityLibros.getAño_publicacion());
        libros.setGenero(entityLibros.getGenero());
        libros.setAutor_id(entityLibros.getAutor_id());
        return libros;
    }

    //metodo para insertar datos
    public DTOLibros insertarLibros(@Valid DTOLibros json) {
        if (json == null){
            throw new IllegalArgumentException("Los datos no pueden estar vacios");
        }
        try {
            EntityLibros ent = ConvertirAEntity(json);
            EntityLibros LibroGuardado = repo.save(ent);
            return ConvertirADTO(LibroGuardado);
        }
        catch (Exception e){
            log.error("error no controlado " + e.getMessage());
            throw new ExceptionLibroNoEncontrado("Error al registrar " + e.getMessage());
        }
    }

    //Metodo para convertir los datos a entidad y poder insertar datos
    private EntityLibros ConvertirAEntity(@Valid DTOLibros json) {
        EntityLibros libros = new EntityLibros();
        libros.setTitulo(json.getTitulo());
        libros.setIsbn(json.getIsbn());
        libros.setAño_publicacion(json.getAño_publicacion());
        libros.setGenero(json.getGenero());
        libros.setAutor_id(json.getAutor_id());
        return libros;
    }


    public DTOLibros actualizarLibro(Long id, @Valid DTOLibros json) {
        EntityLibros existente = repo.findById(id).orElseThrow(()-> new ExceptionLibroNoEncontrado("El libro no fue encontrado"));
        existente.setTitulo(json.getTitulo());
        existente.setIsbn(json.getIsbn());
        existente.setAño_publicacion(json.getAño_publicacion());
        existente.setGenero(json.getGenero());
        existente.setAutor_id(json.getAutor_id());
        EntityLibros guardado = repo.save(existente);
        return ConvertirADTO(guardado);
    }

    public boolean removerLibro(Long id) {
        try {
            EntityLibros existe = repo.findById(id).orElse(null);

            if (existe != null){
                repo.deleteById(id);
                return true;
            }
            else {
                return false;
            }
        }
        catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("no se encontro libro con el id " + id + " para ser eliminado ", 1);
        }
    }
}

