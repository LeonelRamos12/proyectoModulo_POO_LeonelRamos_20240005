package ProyectoModulo_LEONELRAMOS_20240005.LEONELRAMOS_20240005;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Leonelramos20240005Application.class);
	}

}
