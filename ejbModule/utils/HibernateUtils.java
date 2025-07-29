package utils;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * HibernateUtils pour test standalone (sans conteneur EJB)
 * Utilise JPA standard avec EntityManagerFactory
 */
public class HibernateUtils {
    
    private static final Logger logger = Logger.getLogger(HibernateUtils.class.getName());
    private static EntityManagerFactory entityManagerFactory;
    
    // Initialisation statique pour tests standalone
    static {
        try {
            logger.info("🚀 Initialisation EntityManagerFactory standalone...");
            
            // Force Javassist avant Hibernate
            System.setProperty("hibernate.bytecode.provider", "javassist");
            System.setProperty("hibernate.bytecode.use_reflection_optimizer", "false");
            
            entityManagerFactory = Persistence.createEntityManagerFactory("SenMarketPU");
            logger.info("✅ EntityManagerFactory initialisé avec succès !");
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "❌ Erreur initialisation EntityManagerFactory", e);
            throw new ExceptionInInitializerError(e);
        }
    }
    
    /**
     * Obtient un EntityManager pour test standalone
     */
    public static EntityManager getEntityManager() {
        if (entityManagerFactory == null) {
            throw new RuntimeException("EntityManagerFactory non initialisé");
        }
        return entityManagerFactory.createEntityManager();
    }
    
    /**
     * Test de connexion standalone
     */
    public static boolean testConnection() {
        EntityManager em = null;
        try {
            logger.info("Test de connexion à la base de données...");
            em = getEntityManager();
            
            Query query = em.createNativeQuery("SELECT 1 as test");
            Object result = query.getSingleResult();
            
            logger.info("✅ Connexion réussie ! Test: " + result);
            return true;
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "❌ Test de connexion échoué", e);
            return false;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    
    /**
     * Récupère les informations de base de données
     */
    public static DatabaseInfo getDatabaseInfo() {
        EntityManager em = null;
        try {
            logger.info("Récupération des informations de la base...");
            em = getEntityManager();
            
            DatabaseInfo info = new DatabaseInfo();
            
            // Informations de base
            Query infoQuery = em.createNativeQuery(
                "SELECT current_database(), current_user, current_schema(), version()"
            );
            Object[] result = (Object[]) infoQuery.getSingleResult();
            
            info.databaseName = (String) result[0];
            info.currentUser = (String) result[1];
            info.currentSchema = (String) result[2];
            info.version = (String) result[3];
            
            // Nombre de tables
            Query tablesQuery = em.createNativeQuery(
                "SELECT COUNT(*) FROM information_schema.tables " +
                "WHERE table_schema = 'public' AND table_type = 'BASE TABLE'"
            );
            info.tableCount = ((Number) tablesQuery.getSingleResult()).intValue();
            
            // Tables SenMarket principales
            Query senMarketQuery = em.createNativeQuery(
                "SELECT table_name FROM information_schema.tables " +
                "WHERE table_schema = 'public' AND table_name IN " +
                "('personnes', 'boutiques', 'produits', 'commandes', 'clients', 'profils_utilisateur') " +
                "ORDER BY table_name"
            );
            @SuppressWarnings("unchecked")
            List<String> tables = senMarketQuery.getResultList();
            info.senMarketTables = tables;
            
            logger.info("✅ Informations récupérées avec succès !");
            return info;
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "❌ Erreur récupération infos", e);
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    
    /**
     * Vérification des tables SenMarket
     */
    public static boolean testSenMarketTables() {
        try {
            DatabaseInfo info = getDatabaseInfo();
            if (info != null && info.senMarketTables != null) {
                logger.info("📊 Tables SenMarket trouvées: " + info.senMarketTables.size());
                return info.senMarketTables.size() >= 4;
            }
            return false;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Erreur vérification tables SenMarket", e);
            return false;
        }
    }
    
    /**
     * Fermeture propre pour tests standalone
     */
    public static void shutdown() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            logger.info("🔒 Fermeture EntityManagerFactory...");
            entityManagerFactory.close();
            logger.info("✅ EntityManagerFactory fermé avec succès !");
        }
    }
    
    /**
     * Classe pour les informations de base de données
     */
    public static class DatabaseInfo {
        public String version;
        public String databaseName;
        public String currentUser;
        public String currentSchema;
        public int tableCount;
        public List<String> senMarketTables;
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("=== INFORMATIONS BASE DE DONNÉES (Standalone) ===\n");
            sb.append("Version PostgreSQL : ").append(version).append("\n");
            sb.append("Base de données : ").append(databaseName).append("\n");
            sb.append("Utilisateur : ").append(currentUser).append("\n");
            sb.append("Schéma : ").append(currentSchema).append("\n");
            sb.append("Nombre total de tables : ").append(tableCount).append("\n");
            sb.append("Tables SenMarket : ").append(senMarketTables).append("\n");
            sb.append("================================================");
            return sb.toString();
        }
    }
}