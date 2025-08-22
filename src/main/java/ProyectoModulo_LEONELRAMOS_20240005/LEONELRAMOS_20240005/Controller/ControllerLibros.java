package ProyectoModulo_LEONELRAMOS_20240005.LEONELRAMOS_20240005.Controller;


import ProyectoModulo_LEONELRAMOS_20240005.LEONELRAMOS_20240005.Exceptions.ExceptionDatosDuplicados;
import ProyectoModulo_LEONELRAMOS_20240005.LEONELRAMOS_20240005.Exceptions.ExceptionLibroNoEncontrado;
import ProyectoModulo_LEONELRAMOS_20240005.LEONELRAMOS_20240005.Models.DTO.DTOLibros;
import ProyectoModulo_LEONELRAMOS_20240005.LEONELRAMOS_20240005.Service.ServiceLibros;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ApiLibros")
public class ControllerLibros {

    //inyeccion de la clase de service
    @Autowired
    private ServiceLibros service;

    //endPoint para mostrar datos
    @GetMapping("/ObtenerLibros")
    public List<DTOLibros> ObtenerDatos(){
        return service.obtenerDatos();
    }

    //endpoint para agregar datos
    @PostMapping("/AgregarLibros")
    public ResponseEntity<?> nuevoLibro(
            @Valid @RequestBody DTOLibros json, HttpServletRequest request
    ){
        try {
            DTOLibros respuesta = service.insertarLibros(json);
            if (respuesta == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "message", "los datos estan vacios",
                        "status", "error",
                        "errorType","Validation Type"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "success",
                    "message", "Libro fue creado exitosamente"
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error interno no controlado",
                    "detail", e.getMessage()
            ));
        }
    }

    @PutMapping("/EditarLibros/{id}")
    public ResponseEntity<?> ModificarLibro(
            @PathVariable Long id,
            @Valid @RequestBody DTOLibros json,
            BindingResult bin
    ){
        if (bin.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bin.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));

            return ResponseEntity.badRequest().body(errores);
        }
        try{
            DTOLibros dtoLibros = service.actualizarLibro(id, json);
            return ResponseEntity.ok(json);
        }
        catch (ExceptionLibroNoEncontrado e){
            return ResponseEntity.notFound().build();
        }
        catch (ExceptionDatosDuplicados e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "error", "Datos duplicados",
                    "message", "los datos no pueden estar duplicados"
            ));
        }
    }

    @DeleteMapping("/EliminarLibro/{id}")
    public ResponseEntity<?> EliminarLibro(
            @PathVariable Long id
    ){
        try {
            if (!service.removerLibro(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                        "message", "Libro no encontrado"
                  ).body(Map.of(
                          "status", "error",
                        "TimeStamp", Instant.now().toString()
                ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "status", "proceso completado",
                    "Message", "Libro eliminado"

            ));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "error",
                    "Message", "error no controlado"
            ));
        }
    }
}









