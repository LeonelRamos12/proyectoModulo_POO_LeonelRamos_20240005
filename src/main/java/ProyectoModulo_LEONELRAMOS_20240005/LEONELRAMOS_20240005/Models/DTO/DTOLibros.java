package ProyectoModulo_LEONELRAMOS_20240005.LEONELRAMOS_20240005.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DTOLibros {

    private Long id;

    @NotBlank
    private String titulo;

    @NotBlank
    private String isbn;

    private Long año_publicacion;

    @NotBlank
    private String genero;

    private Long autor_id;
}
