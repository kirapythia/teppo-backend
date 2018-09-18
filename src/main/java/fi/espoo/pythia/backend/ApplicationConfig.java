/**
 * TODO
 * Denial of service. Send notifications (e-mail )+ DB -inserts no more that x times per minute
 * Connection pool configuration
 */

package fi.espoo.pythia.backend;

import java.util.Properties;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 * @author jukkak
 * 
 *         See also
 *         http://www.baeldung.com/the-persistence-layer-with-spring-and-jpa and
 *         http://www.baeldung.com/spring-data-jpa-multiple-databases
 */

/**
 * @author saara
 * 
 * SELECT usename,valuntil FROM pg_user;
 * ALTER USER pythiaservice VALID UNTIL 'infinity';
 * 
 */
@Configuration
@EnableConfigurationProperties
@EnableJpaRepositories(basePackages = "fi.espoo.pythia.backend.repos")
@EnableTransactionManagement
public class ApplicationConfig {

	// bb1w1g6xo4mi3ad.c1gsadouzuf9.eu-west-1.rds.amazonaws.com
	@Bean
	public DataSource dataSource() {

		String runningEnvironment = System.getenv("TEPPO_ENV");
		// TODO: This url leads to "old" rds, change to Voltti RDS when it is available
		String url = "jdbc:postgresql://bb1w1g6xo4mi3ad.c1gsadouzuf9.eu-west-1.rds.amazonaws.com";
		// TODO: localhost is now used by default, use Voltti RDS by default when it is available
		// if (runningEnvironment != null && runningEnvironment.equals("local")) {
		if (runningEnvironment == null || runningEnvironment.equals("local")) {
			url = "jdbc:postgresql://localhost";
		}
		String port = "5433";
		DataSource dataSource = DataSourceBuilder.create().url(url + ":"+port+"/teppo")
				.driverClassName("org.postgresql.Driver").username("tepposervice").password("tepposervice").build();

		return dataSource;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {

		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.POSTGRESQL);

		adapter.setShowSql(true);
		adapter.setGenerateDdl(false);

		adapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
		return adapter;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(Environment env) {

		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan("fi.espoo.pythia.backend.repos.entities");

		Properties jpaProperties = new Properties();
		
		
		// Configures the used database dialect. This allows Hibernate to create
		// SQL
		// that is optimized for the used database.
		jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

		// Specifies the action that is invoked to the database when the
		// Hibernate
		// SessionFactory is created or closed.
		jpaProperties.put("hibernate.hbm2ddl.auto", "false");

		// Configures the naming strategy that is used when Hibernate creates
		// new database objects and schema elements
		jpaProperties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");

		// If the value of this property is true, Hibernate writes all SQL
		// statements to the console.
		jpaProperties.put("hibernate.show_sql", "true");

		// If the value of this property is true, Hibernate will format the SQL
		// that is written to the console.
		jpaProperties.put("hibernate.format_sql", "true");

		// spring.jpa.properties.hibernate.default_schema=project

		jpaProperties.put("hibernate.default_schema", "project");
		
		

		entityManagerFactoryBean.setJpaProperties(jpaProperties);

		return entityManagerFactoryBean;
	}
	
	
	
	
	

	@Bean
	JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

}
