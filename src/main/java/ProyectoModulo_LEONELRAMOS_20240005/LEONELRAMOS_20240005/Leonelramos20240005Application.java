package ProyectoModulo_LEONELRAMOS_20240005.LEONELRAMOS_20240005;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Leonelramos20240005Application {
	public static void main(String[] args)
	{
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		dotenv.entries().forEach(entry->
				System.setProperty(entry.getKey(), entry.getValue())
		);

		SpringApplication.run(Leonelramos20240005Application.class, args);
	}

}
