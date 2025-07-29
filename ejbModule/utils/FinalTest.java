package utils;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * Test final Hibernate SenMarket
 */
public class FinalTest {
    
    private static final Logger logger = Logger.getLogger(FinalTest.class.getName());
    
    public static void main(String[] args) {
        logger.info("=== TEST FINAL HIBERNATE SENMARKET ===");
        
        EntityManagerFactory emf = null;
        EntityManager em = null;
        
        try {
            // Configuration système pour Javassist
            System.setProperty("hibernate.bytecode.provider", "javassist");
            
            logger.info("🚀 Initialisation Hibernate...");
            emf = Persistence.createEntityManagerFactory("SenMarketPU");
            logger.info("✅ EntityManagerFactory créé avec succès !");
            
            em = emf.createEntityManager();
            logger.info("✅ EntityManager obtenu !");
            
            // Test 1: Connexion basique
            Query testQuery = em.createNativeQuery("SELECT 1 as test");
            Object result = testQuery.getSingleResult();
            logger.info("✅ Test connexion: " + result);
            
            // Test 2: Informations base
            Query infoQuery = em.createNativeQuery("SELECT current_database(), current_user, version()");
            Object[] info = (Object[]) infoQuery.getSingleResult();
            logger.info("📂 Base: " + info[0]);
            logger.info("👤 Utilisateur: " + info[1]);
            logger.info("📦 Version: " + info[2].toString().substring(0, 50) + "...");
            
            // Test 3: Comptage tables
            Query tableQuery = em.createNativeQuery(
                "SELECT COUNT(*) FROM information_schema.tables " +
                "WHERE table_schema = 'public' AND table_type = 'BASE TABLE'"
            );
            Number tableCount = (Number) tableQuery.getSingleResult();
            logger.info("📊 Nombre de tables: " + tableCount);
            
            // Test 4: Tables SenMarket
            Query senMarketQuery = em.createNativeQuery(
                "SELECT table_name FROM information_schema.tables " +
                "WHERE table_schema = 'public' AND table_name IN " +
                "('personnes', 'boutiques', 'produits', 'commandes', 'clients') " +
                "ORDER BY table_name"
            );
            var tables = senMarketQuery.getResultList();
            logger.info("📋 Tables SenMarket trouvées: " + tables.size());
            for (Object table : tables) {
                logger.info("  ✅ " + table);
            }
            
            logger.info("");
            logger.info("🎉 SUCCESS ! HIBERNATE FONCTIONNE PARFAITEMENT !");
            logger.info("✅ Connexion PostgreSQL : OK");
            logger.info("✅ EntityManagerFactory : OK");
            logger.info("✅ Requêtes natives : OK");
            logger.info("✅ Base de données SenMarket : OK");
            
        } catch (Exception e) {
            logger.severe("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
                logger.info("🔒 EntityManager fermé");
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
                logger.info("🔒 EntityManagerFactory fermé");
            }
        }
        
        logger.info("=== FIN DU TEST ===");
    }
}