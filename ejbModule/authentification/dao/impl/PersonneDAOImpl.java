package authentification.dao.impl;



import dao.base.GenericDAOImpl;
import model.utilisateur.Personnes;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.List;

/**
 * Implémentation DAO pour l'entité Personnes
 * Fournit les opérations d'accès aux données pour les personnes
 * 
 * @author SenMarket Team
 * @version 1.0
 */
@Stateless
public class PersonnesDAOImpl extends GenericDAOImpl<Personnes, Integer> implements PersonnesDAO {
    
    @Override
    public Personnes findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }
        
        try {
            return findSingleByNamedQuery("Personnes.findByEmail", "email", email.trim().toLowerCase());
        } catch (Exception e) {
            // Log l'erreur si nécessaire
            return null;
        }
    }
    
    @Override
    public Personnes findByTelephone(String telephone) {
        if (telephone == null || telephone.trim().isEmpty()) {
            return null;
        }
        
        try {
            return findSingleByNamedQuery("Personnes.findByTelephone", "telephone", telephone.trim());
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public Personnes findByUuid(String uuid) {
        if (uuid == null || uuid.trim().isEmpty()) {
            return null;
        }
        
        try {
            String queryString = "SELECT p FROM Personnes p WHERE p.uuid = :uuid AND p.estActif = true";
            TypedQuery<Personnes> query = entityManager.createQuery(queryString, Personnes.class);
            query.setParameter("uuid", uuid);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public List<Personnes> findAllActive() {
        return findByNamedQuery("Personnes.findAll");
    }
    
    @Override
    public List<Personnes> findByProfil(Integer profilId) {
        if (profilId == null) {
            return List.of(); // Retourne une liste vide
        }
        
        return findByNamedQuery("Personnes.findByProfil", "profilId", profilId);
    }
    
    @Override
    public List<Personnes> searchByName(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            return List.of();
        }
        
        String searchPattern = "%" + nom.trim().toLowerCase() + "%";
        return findByNamedQuery("Personnes.searchByName", "name", searchPattern);
    }
    
    @Override
    public List<Personnes> findByVille(String ville) {
        if (ville == null || ville.trim().isEmpty()) {
            return List.of();
        }
        
        try {
            String queryString = "SELECT p FROM Personnes p WHERE LOWER(p.ville) = LOWER(:ville) AND p.estActif = true ORDER BY p.nomComplet";
            TypedQuery<Personnes> query = entityManager.createQuery(queryString, Personnes.class);
            query.setParameter("ville", ville.trim());
            return query.getResultList();
        } catch (Exception e) {
            return List.of();
        }
    }
    
    @Override
    public boolean emailExists(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        
        try {
            String queryString = "SELECT COUNT(p) FROM Personnes p WHERE LOWER(p.email) = LOWER(:email)";
            TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
            query.setParameter("email", email.trim());
            return query.getSingleResult() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public boolean telephoneExists(String telephone) {
        if (telephone == null || telephone.trim().isEmpty()) {
            return false;
        }
        
        try {
            String queryString = "SELECT COUNT(p) FROM Personnes p WHERE p.telephone = :telephone";
            TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
            query.setParameter("telephone", telephone.trim());
            return query.getSingleResult() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public long countByProfil(Integer profilId) {
        if (profilId == null) {
            return 0;
        }
        
        try {
            String queryString = "SELECT COUNT(p) FROM Personnes p WHERE p.profilsUtilisateur.id = :profilId AND p.estActif = true";
            TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
            query.setParameter("profilId", profilId);
            return query.getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }
    
    @Override
    public int deactivatePerson(Integer personneId) {
        if (personneId == null) {
            return 0;
        }
        
        try {
            String queryString = "UPDATE Personnes p SET p.estActif = false, p.modifieLe = :now WHERE p.id = :id";
            return executeUpdate(queryString, 
                    "now", new Timestamp(System.currentTimeMillis()),
                    "id", personneId);
        } catch (Exception e) {
            return 0;
        }
    }
    
    @Override
    public int activatePerson(Integer personneId) {
        if (personneId == null) {
            return 0;
        }
        
        try {
            String queryString = "UPDATE Personnes p SET p.estActif = true, p.modifieLe = :now WHERE p.id = :id";
            return executeUpdate(queryString, 
                    "now", new Timestamp(System.currentTimeMillis()),
                    "id", personneId);
        } catch (Exception e) {
            return 0;
        }
    }
    
    @Override
    public int updateLastActivity(Integer personneId) {
        if (personneId == null) {
            return 0;
        }
        
        try {
            String queryString = "UPDATE Personnes p SET p.modifieLe = :now WHERE p.id = :id";
            return executeUpdate(queryString, 
                    "now", new Timestamp(System.currentTimeMillis()),
                    "id", personneId);
        } catch (Exception e) {
            return 0;
        }
    }
    
    /**
     * Méthodes utilitaires additionnelles
     */
    
    /**
     * Trouve les personnes créées après une certaine date
     */
    public List<Personnes> findCreatedAfter(Timestamp date) {
        try {
            String queryString = "SELECT p FROM Personnes p WHERE p.creeLe >= :date AND p.estActif = true ORDER BY p.creeLe DESC";
            TypedQuery<Personnes> query = entityManager.createQuery(queryString, Personnes.class);
            query.setParameter("date", date);
            return query.getResultList();
        } catch (Exception e) {
            return List.of();
        }
    }
    
    /**
     * Compte les nouvelles inscriptions dans une période
     */
    public long countNewRegistrations(Timestamp dateDebut, Timestamp dateFin) {
        try {
            String queryString = "SELECT COUNT(p) FROM Personnes p WHERE p.creeLe BETWEEN :debut AND :fin";
            TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
            query.setParameter("debut", dateDebut);
            query.setParameter("fin", dateFin);
            return query.getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }
    
    /**
     * Trouve les personnes avec email mais sans téléphone
     */
    public List<Personnes> findWithEmailButNoPhone() {
        try {
            String queryString = "SELECT p FROM Personnes p WHERE p.email IS NOT NULL AND p.telephone IS NULL AND p.estActif = true";
            TypedQuery<Personnes> query = entityManager.createQuery(queryString, Personnes.class);
            return query.getResultList();
        } catch (Exception e) {
            return List.of();
        }
    }
}