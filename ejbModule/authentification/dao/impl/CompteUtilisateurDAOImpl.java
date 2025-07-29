package authentification.dao.impl;

import authentification.base.dao.GenericDAOImpl;
import authentification.dao.interfaces.CompteUtilisateurDAO;
import model.utilisateur.ComptesUtilisateur;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.List;

/**
 * Implémentation DAO pour l'entité ComptesUtilisateur
 * Fournit les opérations d'accès aux données pour les comptes utilisateur
 * 
 * @author SenMarket Team
 * @version 1.0
 */
@Stateless
public class CompteUtilisateurDAOImpl extends GenericDAOImpl<ComptesUtilisateur, Integer> implements CompteUtilisateurDAO {
    
    @Override
    public ComptesUtilisateur findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }
        
        try {
            String queryString = "SELECT c FROM ComptesUtilisateur c WHERE LOWER(c.email) = LOWER(:email)";
            TypedQuery<ComptesUtilisateur> query = entityManager.createQuery(queryString, ComptesUtilisateur.class);
            query.setParameter("email", email.trim());
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public ComptesUtilisateur findByNomUtilisateur(String nomUtilisateur) {
        if (nomUtilisateur == null || nomUtilisateur.trim().isEmpty()) {
            return null;
        }
        
        try {
            String queryString = "SELECT c FROM ComptesUtilisateur c WHERE c.nomUtilisateur = :nomUtilisateur";
            TypedQuery<ComptesUtilisateur> query = entityManager.createQuery(queryString, ComptesUtilisateur.class);
            query.setParameter("nomUtilisateur", nomUtilisateur.trim());
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public ComptesUtilisateur findByGoogleId(String googleId) {
        if (googleId == null || googleId.trim().isEmpty()) {
            return null;
        }
        
        try {
            String queryString = "SELECT c FROM ComptesUtilisateur c WHERE c.googleId = :googleId";
            TypedQuery<ComptesUtilisateur> query = entityManager.createQuery(queryString, ComptesUtilisateur.class);
            query.setParameter("googleId", googleId.trim());
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public ComptesUtilisateur findByPersonneId(Integer personneId) {
        if (personneId == null) {
            return null;
        }
        
        try {
            String queryString = "SELECT c FROM ComptesUtilisateur c WHERE c.personnes.id = :personneId";
            TypedQuery<ComptesUtilisateur> query = entityManager.createQuery(queryString, ComptesUtilisateur.class);
            query.setParameter("personneId", personneId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public List<ComptesUtilisateur> findAllActive() {
        try {
            String queryString = "SELECT c FROM ComptesUtilisateur c WHERE c.estActif = true ORDER BY c.creeLe DESC";
            TypedQuery<ComptesUtilisateur> query = entityManager.createQuery(queryString, ComptesUtilisateur.class);
            return query.getResultList();
        } catch (Exception e) {
            return List.of();
        }
    }
    
    @Override
    public List<ComptesUtilisateur> findAllVerified() {
        try {
            String queryString = "SELECT c FROM ComptesUtilisateur c WHERE c.estVerifie = true ORDER BY c.emailVerifieLe DESC";
            TypedQuery<ComptesUtilisateur> query = entityManager.createQuery(queryString, ComptesUtilisateur.class);
            return query.getResultList();
        } catch (Exception e) {
            return List.of();
        }
    }
    
    @Override
    public List<ComptesUtilisateur> findCreatedAfter(Timestamp date) {
        if (date == null) {
            return List.of();
        }
        
        try {
            String queryString = "SELECT c FROM ComptesUtilisateur c WHERE c.creeLe >= :date ORDER BY c.creeLe DESC";
            TypedQuery<ComptesUtilisateur> query = entityManager.createQuery(queryString, ComptesUtilisateur.class);
            query.setParameter("date", date);
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
            String queryString = "SELECT COUNT(c) FROM ComptesUtilisateur c WHERE LOWER(c.email) = LOWER(:email)";
            TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
            query.setParameter("email", email.trim());
            return query.getSingleResult() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public boolean nomUtilisateurExists(String nomUtilisateur) {
        if (nomUtilisateur == null || nomUtilisateur.trim().isEmpty()) {
            return false;
        }
        
        try {
            String queryString = "SELECT COUNT(c) FROM ComptesUtilisateur c WHERE c.nomUtilisateur = :nomUtilisateur";
            TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
            query.setParameter("nomUtilisateur", nomUtilisateur.trim());
            return query.getSingleResult() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public int updateDerniereConnexion(Integer compteId, String adresseIp) {
        if (compteId == null) {
            return 0;
        }
        
        try {
            String queryString = "UPDATE ComptesUtilisateur c SET c.derniereConnexionLe = :now, " +
                               "c.derniereConnexionIp = :ip, c.modifieLe = :now WHERE c.id = :id";
            return executeUpdate(queryString, 
                    "now", new Timestamp(System.currentTimeMillis()),
                    "ip", adresseIp,
                    "id", compteId);
        } catch (Exception e) {
            return 0;
        }
    }
    
    @Override
    public int incrementTentativesEchec(Integer compteId) {
        if (compteId == null) {
            return 0;
        }
        
        try {
            String queryString = "UPDATE ComptesUtilisateur c SET c.tentativesEchec = c.tentativesEchec + 1, " +
                               "c.modifieLe = :now WHERE c.id = :id";
            return executeUpdate(queryString, 
                    "now", new Timestamp(System.currentTimeMillis()),
                    "id", compteId);
        } catch (Exception e) {
            return 0;
        }
    }
    
    @Override
    public int resetTentativesEchec(Integer compteId) {
        if (compteId == null) {
            return 0;
        }
        
        try {
            String queryString = "UPDATE ComptesUtilisateur c SET c.tentativesEchec = 0, " +
                               "c.verrouillJusqu = null, c.modifieLe = :now WHERE c.id = :id";
            return executeUpdate(queryString, 
                    "now", new Timestamp(System.currentTimeMillis()),
                    "id", compteId);
        } catch (Exception e) {
            return 0;
        }
    }
    
    @Override
    public int verrouillerCompte(Integer compteId, Timestamp verrouillageJusqu) {
        if (compteId == null || verrouillageJusqu == null) {
            return 0;
        }
        
        try {
            String queryString = "UPDATE ComptesUtilisateur c SET c.verrouillJusqu = :verrouillageJusqu, " +
                               "c.modifieLe = :now WHERE c.id = :id";
            return executeUpdate(queryString, 
                    "verrouillageJusqu", verrouillageJusqu,
                    "now", new Timestamp(System.currentTimeMillis()),
                    "id", compteId);
        } catch (Exception e) {
            return 0;
        }
    }
    
    @Override
    public int deverrouillerCompte(Integer compteId) {
        if (compteId == null) {
            return 0;
        }
        
        try {
            String queryString = "UPDATE ComptesUtilisateur c SET c.verrouillJusqu = null, " +
                               "c.tentativesEchec = 0, c.modifieLe = :now WHERE c.id = :id";
            return executeUpdate(queryString, 
                    "now", new Timestamp(System.currentTimeMillis()),
                    "id", compteId);
        } catch (Exception e) {
            return 0;
        }
    }
    
    @Override
    public int marquerEmailVerifie(Integer compteId) {
        if (compteId == null) {
            return 0;
        }
        
        try {
            String queryString = "UPDATE ComptesUtilisateur c SET c.emailVerifieLe = :now, " +
                               "c.estVerifie = true, c.codeVerification = null, " +
                               "c.codeExpireLe = null, c.modifieLe = :now WHERE c.id = :id";
            Timestamp now = new Timestamp(System.currentTimeMillis());
            return executeUpdate(queryString, 
                    "now", now,
                    "id", compteId);
        } catch (Exception e) {
            return 0;
        }
    }
    
    @Override
    public int updateCodeVerification(Integer compteId, String codeVerification, Timestamp dateExpiration) {
        if (compteId == null) {
            return 0;
        }
        
        try {
            String queryString = "UPDATE ComptesUtilisateur c SET c.codeVerification = :code, " +
                               "c.codeExpireLe = :expiration, c.modifieLe = :now WHERE c.id = :id";
            return executeUpdate(queryString, 
                    "code", codeVerification,
                    "expiration", dateExpiration,
                    "now", new Timestamp(System.currentTimeMillis()),
                    "id", compteId);
        } catch (Exception e) {
            return 0;
        }
    }
    
    @Override
    public int updateStatutActif(Integer compteId, boolean estActif) {
        if (compteId == null) {
            return 0;
        }
        
        try {
            String queryString = "UPDATE ComptesUtilisateur c SET c.estActif = :estActif, " +
                               "c.modifieLe = :now WHERE c.id = :id";
            return executeUpdate(queryString, 
                    "estActif", estActif,
                    "now", new Timestamp(System.currentTimeMillis()),
                    "id", compteId);
        } catch (Exception e) {
            return 0;
        }
    }
    
    @Override
    public List<ComptesUtilisateur> findComptesVerrouilles() {
        try {
            String queryString = "SELECT c FROM ComptesUtilisateur c WHERE c.verrouillJusqu IS NOT NULL " +
                               "AND c.verrouillJusqu > :now ORDER BY c.verrouillJusqu";
            TypedQuery<ComptesUtilisateur> query = entityManager.createQuery(queryString, ComptesUtilisateur.class);
            query.setParameter("now", new Timestamp(System.currentTimeMillis()));
            return query.getResultList();
        } catch (Exception e) {
            return List.of();
        }
    }
    
    @Override
    public long countNouveauxComptes(Timestamp dateDebut, Timestamp dateFin) {
        if (dateDebut == null || dateFin == null) {
            return 0;
        }
        
        try {
            String queryString = "SELECT COUNT(c) FROM ComptesUtilisateur c WHERE c.creeLe BETWEEN :debut AND :fin";
            TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
            query.setParameter("debut", dateDebut);
            query.setParameter("fin", dateFin);
            return query.getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }
    
    @Override
    public List<ComptesUtilisateur> findWithDoubleAuth() {
        try {
            String queryString = "SELECT c FROM ComptesUtilisateur c WHERE c.doubleAuthentificationActive = true " +
                               "ORDER BY c.modifieLe DESC";
            TypedQuery<ComptesUtilisateur> query = entityManager.createQuery(queryString, ComptesUtilisateur.class);
            return query.getResultList();
        } catch (Exception e) {
            return List.of();
        }
    }
    
    @Override
    public int updateGoogleTokens(Integer compteId, String accessToken, String refreshToken, Timestamp expireDate) {
        if (compteId == null) {
            return 0;
        }
        
        try {
            String queryString = "UPDATE ComptesUtilisateur c SET c.googleAccessToken = :accessToken, " +
                               "c.googleRefreshToken = :refreshToken, c.googleTokenExpireLe = :expireDate, " +
                               "c.modifieLe = :now WHERE c.id = :id";
            return executeUpdate(queryString, 
                    "accessToken", accessToken,
                    "refreshToken", refreshToken,
                    "expireDate", expireDate,
                    "now", new Timestamp(System.currentTimeMillis()),
                    "id", compteId);
        } catch (Exception e) {
            return 0;
        }
    }
    
    /**
     * Méthodes utilitaires additionnelles
     */
    
    /**
     * Vérifie si un compte est verrouillé
     */
    public boolean isCompteVerrouille(Integer compteId) {
        if (compteId == null) {
            return false;
        }
        
        try {
            String queryString = "SELECT COUNT(c) FROM ComptesUtilisateur c WHERE c.id = :id " +
                               "AND c.verrouillJusqu IS NOT NULL AND c.verrouillJusqu > :now";
            TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
            query.setParameter("id", compteId);
            query.setParameter("now", new Timestamp(System.currentTimeMillis()));
            return query.getSingleResult() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Compte les comptes actifs
     */
    public long countComptesActifs() {
        try {
            String queryString = "SELECT COUNT(c) FROM ComptesUtilisateur c WHERE c.estActif = true";
            TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
            return query.getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }
}