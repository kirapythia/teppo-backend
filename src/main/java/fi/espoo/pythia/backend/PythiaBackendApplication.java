/**
 * When posting multipart file add size by tweaking both 
 *  1)src/main/resources application.propeties and 
 *  2)PythiaBackendApplication.class Tomcat setMaxPostSize Bean  (this class)
 */
package fi.espoo.pythia.backend;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PythiaBackendApplication {

	// 10485760 bytes
	private int maxPostSize10MB = 10 * 1024 * 1024;
	//104857600 bytes
	private int maxPostSize100MB = 10 * 10 * 1024 * 1024;
	// 1048576000 bytes 
	private int maxPostSize1GB = 10 * 10 * 1024 * 1024;
	// 104857600000 bytes
	private int maxPostSize10GB = 10 * 10 * 10 * 1024 * 1024;

	public static void main(String[] args) {
		SpringApplication.run(PythiaBackendApplication.class, args);
	}


	// Tomcat setMaxPostSize Bean
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() throws Exception {
		return (ConfigurableEmbeddedServletContainer container) -> {
			if (container instanceof TomcatEmbeddedServletContainerFactory) {
				TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
				tomcat.addConnectorCustomizers((connector) -> {
					// 1 GB
					connector.setMaxPostSize(maxPostSize1GB); 
				});
			}
		};
	}

}
