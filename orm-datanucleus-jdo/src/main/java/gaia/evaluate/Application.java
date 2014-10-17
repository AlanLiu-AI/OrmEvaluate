package gaia.evaluate;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.JDOHelper;
import javax.jdo.Transaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import gaia.evaluate.model.IResult;


public class Application {
	static Log logger = LogFactory.getLog(Application.class);

	private static final String PERSISTENCE_UNIT_NAME = "orm-jdo-datanucleus";
	private static PersistenceManagerFactory factory;

	public static void main(String[] args) throws Exception {
            execute(PERSISTENCE_UNIT_NAME);
	}

	private static void execute(String pu) throws Exception {            
            factory = JDOHelper.getPersistenceManagerFactory(pu);

            Class<?> resultClass = gaia.evaluate.datanucleus.model.Result.class;
            IResult result;
            Object resultId = null;
                
            // persistence context #1
            PersistenceManager pm = factory.getPersistenceManager();
            Transaction tx=pm.currentTransaction();

            try {
                tx.begin();                
                
                result = (IResult) resultClass.newInstance();
                
                result.setDescription("This is my description on datanucleus.");
                result.setComments("This is my comments on datanucleus.");
                
                pm.makePersistent(result);
                
                tx.commit();
                
                resultId = pm.getObjectId(result);
            } catch (InstantiationException | IllegalAccessException e) {
                System.out.println("Exception persisting data : " + e.getMessage());
            } finally {
                if (tx.isActive()) {
                    tx.rollback();
                }
                pm.close();
            }


            // persistence context #2 (avoid Level 1 cache)
            pm = factory.getPersistenceManager();
            tx=pm.currentTransaction();
            try {
                tx.begin(); 
                
                result = (IResult)pm.getObjectById(resultId);
                
                logger.info("description: " + result.getDescription());
                logger.info("comments: " + result.getComments());

                tx.commit();
            } catch (Exception e) {
                System.out.println("Exception persisting data : " + e.getMessage());
            } finally {
                if (tx.isActive()) {
                    tx.rollback();
                }
                pm.close();
            }

            factory.close();
	}
}
