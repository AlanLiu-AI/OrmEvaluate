package gaia.evaluate;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import gaia.evaluate.model.IResult;

public class Application {
	static Log logger = LogFactory.getLog(Application.class);

	private static final String PERSISTENCE_UNIT_NAME = "orm-jpa-eclipselink";
	
	private static final String DATABASE_JDBC_URL = "jdbc:postgresql://localhost:5432/gaiatestdb?stringtype=unspecified";
	private static final String DATABASE_USERNAME = "postgres";
	private static final String DATABASE_PASSWORD = "password";

	private static EntityManagerFactory factory;

	public static void main(String[] args) throws Exception {
            execute(PERSISTENCE_UNIT_NAME);
	}

	private static void execute(String pu) throws Exception {
            factory = Persistence.createEntityManagerFactory(pu, getProperties());

            // persistence context #1
            EntityManager em = factory.createEntityManager();

            em.getTransaction().begin();

            Class<?> resultClass = gaia.evaluate.eclipselink.model.Result.class;

            IResult result = (IResult) resultClass.newInstance();
            
            result.setDescription("This is my description on eclipselink.");
            result.setComments("This is my comments on eclipselink.");

            result.getMetadata()
                            .put("title",
                                            "'Refactoring:\\ Improving\\ the\\ Design\\ of\\ Existing\\ Code'");
            result.getMetadata().put("creator", "Martin\\ Fowler");
            result.getMetadata().put("comment", "This\\ is\\ a\\ comment.");
            em.persist(result);
            em.getTransaction().commit();

            em.close();

            // persistence context #2 (avoid Level 1 cache)
            em = factory.createEntityManager();

            result = (IResult) em.find(resultClass, result.getId());
            
            logger.info("description: " + result.getDescription());
            logger.info("comments: " + result.getComments());
            
            Map<String, String> metadata = result.getMetadata();
            for (Entry<String, String> entry : metadata.entrySet()) {
                    logger.info("metadata: " + entry.getKey() + ": " + entry.getValue());
            }

            em.close();

            factory.close();
	}

	private static Properties getProperties() {
		Properties properties = new Properties();
		properties.put("javax.persistence.jdbc.driver", "org.postgresql.Driver");
		properties.put("javax.persistence.database-major-version", "9");
		properties.put("javax.persistence.database-minor-version", "3");
		properties.put("javax.persistence.jdbc.url", DATABASE_JDBC_URL);
		properties.put("javax.persistence.jdbc.user", DATABASE_USERNAME);
		properties.put("javax.persistence.jdbc.password", DATABASE_PASSWORD);
		properties.put("javax.persistence.database-product-name", "PostgreSQL");
		properties.put("javax.persistence.schema-generation.database.action", "drop-and-create");
		properties.put("javax.persistence.schema-generation.create-database-schemas", "true");
		return properties;
	}
}
