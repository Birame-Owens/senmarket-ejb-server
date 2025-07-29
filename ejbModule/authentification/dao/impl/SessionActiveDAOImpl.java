package authentification.dao.impl;

import authentification.base.dao.GenericDAOImpl;
import authentification.dao.interfaces.SessionActiveDAO;
import model.utilisateur.SessionsActives;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.List;

/**
 * Implémentation DAO pour l'entité SessionsActives
 * Fournit les opérations d'accès aux données pour les sessions actives
 * 
 * @author SenMarket Team
 * @version 1.0
 */
@Stateless
public class SessionActiveDAOImpl extends GenericDAOImpl<SessionsActives, Integer> implements SessionActiveDAO {
    
    @Override
    public SessionsActives findByToken(String tokenSession) {
        if (tokenSession == null || tokenSession.trim().isEmpty()) {
            return null;
        }
        
        try {
            String queryString = "SELECT s FROM SessionsActives s WHERE s.tokenSession = :token";
            TypedQuery<SessionsActives> query = entityManager.createQuery(queryString, SessionsActives.class);
            query.setParameter("token", tokenSession.trim());
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public SessionsActives findByRefreshToken(String tokenRefresh) {
        if (tokenRefresh == null || tokenRefresh.trim().isEmpty()) {
            return null;
        }
        
        try {
            String queryString = "SELECT s FROM SessionsActives s WHERE s.tokenRefresh = :tokenRefresh";
            TypedQuery<SessionsActives> query = entityManager.createQuery(queryString, SessionsActives.class);
            query.setParameter("tokenRefresh", tokenRefresh.trim());
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public List<SessionsActives> findByCompteId(Integer compteId) {
        if (compteId == null) {
            return List.of();
        }
        
        try {
            String queryString = "SELECT s FROM SessionsActives s WHERE s.comptesUtilisateur.id = :compteId " +
                               "ORDER BY s.derniereActivite DESC";
            TypedQuery<SessionsActives> query = entityManager.createQuery(queryString, SessionsActives.class);
            query.setParameter("compteId", compteId);
            return query.getResultList();
        } catch (Exception e) {
            return List.of();
        }
    }
    
    @Override
    public List<SessionsActives> findAllActiveSessions() {
        try {
            String queryString = "SELECT s FROM SessionsActives s WHERE s.estActif = true " +
                               "AND (s.expireLe IS NULL OR s.expireLe > :now) " +
                               "ORDER BY s.derniereActivite DESC";
            TypedQuery<SessionsActives> query = entityManager.createQuery(queryString, SessionsActives.class);
            query.setParameter("now", new Timestamp(System.currentTimeMillis()));
            return query.getResultList();
        } catch (Exception e) {
            return List.of();
        }
    }
    
    @Override
    public List<SessionsActives> findExpiredSessions() {
        try {
            String queryString = "SELECT s FROM SessionsActives s WHERE s.expireLe IS NOT NULL " +
                               "AND s.expireLe <= :now ORDER BY s.expireLe";
            TypedQuery<SessionsActives> query = entityManager.createQuery(queryString, SessionsActives.class);
            query.setParameter("now", new Timestamp(System.currentTimeMillis()));
            return query.getResultList();
        } catch (Exception e) {
            return List.of();
        }
    }
    
    @Override
    public List<SessionsActives> findByAdresseIp(String adresseIp) {
        if (adresseIp == null || adresseIp.trim().isEmpty()) {
            return List.of();
        }
        
        try {
            String queryString = "SELECT s FROM SessionsActives s WHERE s.adresseIp = :ip " +
                               "ORDER BY s.derniereActivite DESC";
            TypedQuery<SessionsActives> query = entityManager.createQuery(queryString, SessionsActives.class);
            query.setParameter("ip", adresseIp.trim());
            return query.getResultList();
        } catch (Exception e) {
            return List.of();
        }
    }
    
    @Override
    public List<SessionsActives> findByEmpreinteAppareil(String empreinteAppareil) {
        if (empreinteAppareil == null || empreinteAppareil.trim().isEmpty()) {
            return List.of();
        }
        
        try {
            String queryString = "SELECT s FROM SessionsActives s WHERE s.empreinteAppareil = :empreinte " +
                               "ORDER BY s.derniereActivite DESC";
            TypedQuery<SessionsActives> query = entityManager.createQuery(queryString, SessionsActives.class);
            query.setParameter("empreinte", empreinteAppareil.trim());
            return query.getResultList();
        } catch (Exception e) {
            return List.of();
        }
    }
    
    @Override
    public List<SessionsActives> findCreatedAfter(Timestamp date) {
        if (date == null) {
            return List.of();
        }
        
        try {
            String queryString = "SELECT s FROM SessionsActives s WHERE s.creeLe >= :date " +
                               "ORDER BY s.creeLe DESC";
            TypedQuery<SessionsActives> query = entityManager.createQuery(queryString, SessionsActives.class);
            query.setParameter("date", date);
            return query.getResultList();
        } catch (Exception e) {
            return List.of();
        }
    }
    
    @Override
    public boolean isTokenValid(String tokenSession) {
        if (tokenSession == null || tokenSession.trim().isEmpty()) {
            return false;
        }
        
        try {
            String queryString = "SELECT COUNT(s) FROM SessionsActives s WHERE s.tokenSession = :token " +
                               "AND s.estActif = true AND (s.expireLe IS NULL OR s.expireLe > :now)";
            TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
            query.setParameter("token", tokenSession.trim());
            query.setParameter("now", new Timestamp(System.currentTimeMillis()));
            return query.getSingleResult() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public boolean isTokenExpired(String tokenSession) {
        if (tokenSession == null || tokenSession.trim().isEmpty()) {
            return true;
        }
        
        try {
            String queryString = "SELECT s.expireLe FROM SessionsActives s WHERE s.tokenSession = :token";
            TypedQuery<Timestamp> query = entityManager.createQuery(queryString, Timestamp.class);
            query.setParameter("token", tokenSession.trim());
            Timestamp expiration = query.getSingleResult();
            
            if (expiration == null) {
                return false; // Session sans expiration
            }
            
            return expiration.before(new Timestamp(System.currentTimeMillis()));
        } catch (NoResultException e) {
            return true; // Token n'existe pas = expiré
        } catch (Exception e) {
            return true;
        }
    }
    
    @Override
    public int updateDerniereActivite(Integer sessionId) {
        if (sessionId == null) {
            return 0;
        }
        
        try {
            String queryString = "UPDATE SessionsActives s SET s.derniereActivite = :now WHERE s.id = :id";
            return executeUpdate(queryString, 
                    "now", new Timestamp(System.currentTimeMillis()),
                    "id", sessionId);
        } catch (Exception e) {
            return 0;
        }
    }
    
    @Override
    public int updateDerniereActiviteByToken(String tokenSession) {
        if (tokenSession == null || tokenSession.trim().isEmpty()) {
            return 0;
        }
        
        try {
            String queryString = "UPDATE SessionsActives s SET s.derniereActivite = :now WHERE s.tokenSession = :token";
            return executeUpdate(queryString, 
                    "now", new Timestamp(System.currentTimeMillis()),
                    "token", tokenSession.trim());
        } catch (Exception e) {
            return 0;
        }
    }
    
    @Override
    public int desactiverSession(Integer sessionId) {
        if (sessionId == null) {
            return 0;
        }
        
        try {
            String queryString = "UPDATE SessionsActives s SET s.estActif = false WHERE s.id = :id";
            return executeUpdate(queryString, "id", sessionId);
        } catch (Exception e) {
            return 0;
        }
    }
    
    @Override
    public int desactiverSessionByToken(String tokenSession) {
        if (tokenSession == null || tokenSession.trim().isEmpty()) {
            return 0;
        }
        
        try {
            String queryString = "UPDATE SessionsActives s SET s.estActif = false WHERE s.tokenSession = :token";
            return executeUpdate(queryString, "token", tokenSession.trim());
        } catch (Exception e) {
            return 0;
        }
    }
    
    @Override
    public int desactiverToutesSessionsCompte(Integer compteId) {
        if (compteId == null) {
            return 0;
        }
        
        try {
            String queryString = "UPDATE SessionsActives s SET s.estActif = false WHERE s.comptesUtilisateur.id = :compteId";
            return executeUpdate(queryString, "compteId", compteId);
        } catch (Exception e) {
            return 0;
        }
    }
    
    @Override
    public int supprimerSessionsExpirees() {
        try {
            String queryString = "DELETE FROM SessionsActives s WHERE s.expireLe IS NOT NULL AND s.expireLe <= :now";
            return executeUpdate(queryString, "now", new Timestamp(System.currentTimeMillis()));
        } catch (Exception e) {
            return 0;
        }
    }
    
    @Override
    public int supprimerSessionsInactives(Timestamp derniereActiviteAvant) {
        if (derniereActiviteAvant == null) {
            return 0;
        }
        
        try {
            String queryString = "DELETE FROM SessionsActives s WHERE s.derniereActivite < :seuil";
            return executeUpdate(queryString, "seuil", derniereActiviteAvant);
        } catch (Exception e) {
            return 0;
        }
    }
    
    @Override
    public long countSessionsActives(Integer compteId) {
        if (compteId == null) {
            return 0;
        }
        
        try {
            String queryString = "SELECT COUNT(s) FROM SessionsActives s WHERE s.comptesUtilisateur.id = :compteId " +
                               "AND s.estActif = true AND (s.expireLe IS NULL OR s.expireLe > :now)";
            TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
            query.setParameter("compteId", compteId);
            query.setParameter("now", new Timestamp(System.currentTimeMillis()));
            return query.getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }
    
    @Override
    public long countTotalSessionsActives() {
        try {
            String queryString = "SELECT COUNT(s) FROM SessionsActives s WHERE s.estActif = true " +
                               "AND (s.expireLe IS NULL OR s.expireLe > :now)";
            TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
            query.setParameter("now", new Timestamp(System.currentTimeMillis()));
            return query.getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }
    
    @Override
    public List<SessionsActives> findSessionsSuspectes(Integer compteId) {
        if (compteId == null) {
            return List.of();
        }
        
        try {
            // Sessions avec des IPs différentes dans les dernières 24h
            String queryString = "SELECT s FROM SessionsActives s WHERE s.comptesUtilisateur.id = :compteId " +
                               "AND s.estActif = true AND s.creeLe >= :depuis24h " +
                               "AND s.adresseIp NOT IN (" +
                               "    SELECT s2.adresseIp FROM SessionsActives s2 " +
                               "    WHERE s2.comptesUtilisateur.id = :compteId " +
                               "    AND s2.creeLe < :depuis24h " +
                               "    ORDER BY s2.derniereActivite DESC" +
                               ") ORDER BY s.creeLe DESC";
            
            TypedQuery<SessionsActives> query = entityManager.createQuery(queryString, SessionsActives.class);
            query.setParameter("compteId", compteId);
            
            // 24 heures en arrière
            long maintenant = System.currentTimeMillis();
            long depuis24h = maintenant - (24 * 60 * 60 * 1000);
            query.setParameter("depuis24h", new Timestamp(depuis24h));
            
            return query.getResultList();
        } catch (Exception e) {
            return List.of();
        }
    }
    
    @Override
    public int updateGeolocalisation(Integer sessionId, String pays, String ville) {
        if (sessionId == null) {
            return 0;
        }
        
        try {
            String queryString = "UPDATE SessionsActives s SET s.pays = :pays, s.ville = :ville WHERE s.id = :id";
            return executeUpdate(queryString, 
                    "pays", pays,
                    "ville", ville,
                    "id", sessionId);
        } catch (Exception e) {
            return 0;
        }
    }
    
    @Override
    public int prolongerSession(Integer sessionId, Timestamp nouvelleExpiration) {
        if (sessionId == null) {
            return 0;
        }
        
        try {
            String queryString = "UPDATE SessionsActives s SET s.expireLe = :expiration WHERE s.id = :id";
            return executeUpdate(queryString, 
                    "expiration", nouvelleExpiration,
                    "id", sessionId);
        } catch (Exception e) {
            return 0;
        }
    }
    
    @Override
    public List<SessionsActives> findSessionsByPeriode(Timestamp dateDebut, Timestamp dateFin) {
        if (dateDebut == null || dateFin == null) {
            return List.of();
        }
        
        try {
            String queryString = "SELECT s FROM SessionsActives s WHERE s.derniereActivite BETWEEN :debut AND :fin " +
                               "ORDER BY s.derniereActivite DESC";
            TypedQuery<SessionsActives> query = entityManager.createQuery(queryString, SessionsActives.class);
            query.setParameter("debut", dateDebut);
            query.setParameter("fin", dateFin);
            return query.getResultList();
        } catch (Exception e) {
            return List.of();
        }
    }
    
    @Override
    public long countSessionsByTypeAppareil(String typeAppareil) {
        if (typeAppareil == null || typeAppareil.trim().isEmpty()) {
            return 0;
        }
        
        try {
            String queryString = "SELECT COUNT(s) FROM SessionsActives s WHERE s.userAgent LIKE :typeAppareil " +
                               "AND s.estActif = true";
            TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
            query.setParameter("typeAppareil", "%" + typeAppareil.trim() + "%");
            return query.getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }
    
    @Override
    public int nettoyerSessions(int maxSessionsParCompte, long maxAgeInactif) {
        int totalSupprimes = 0;
        
        try {
            // 1. Supprimer les sessions expirées
            totalSupprimes += supprimerSessionsExpirees();
            
            // 2. Supprimer les sessions inactives
            long seuilInactivite = System.currentTimeMillis() - maxAgeInactif;
            totalSupprimes += supprimerSessionsInactives(new Timestamp(seuilInactivite));
            
            // 3. Limiter le nombre de sessions par compte (garder les plus récentes)
            String queryString = "DELETE FROM SessionsActives s WHERE s.id NOT IN (" +
                               "    SELECT s2.id FROM (" +
                               "        SELECT s3.id FROM SessionsActives s3 " +
                               "        WHERE s3.comptesUtilisateur.id = s.comptesUtilisateur.id " +
                               "        ORDER BY s3.derniereActivite DESC " +
                               "        LIMIT :maxSessions" +
                               "    ) s2" +
                               ")";
            totalSupprimes += executeUpdate(queryString, "maxSessions", maxSessionsParCompte);
            
            return totalSupprimes;
        } catch (Exception e) {
            return totalSupprimes;
        }
    }
    
    /**
     * Méthodes utilitaires additionnelles
     */
    
    /**
     * Trouve la session la plus récente d'un compte
     */
    public SessionsActives findLatestSessionByCompte(Integer compteId) {
        if (compteId == null) {
            return null;
        }
        
        try {
            String queryString = "SELECT s FROM SessionsActives s WHERE s.comptesUtilisateur.id = :compteId " +
                               "ORDER BY s.derniereActivite DESC";
            TypedQuery<SessionsActives> query = entityManager.createQuery(queryString, SessionsActives.class);
            query.setParameter("compteId", compteId);
            query.setMaxResults(1);
            List<SessionsActives> sessions = query.getResultList();
            return sessions.isEmpty() ? null : sessions.get(0);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Compte les sessions par pays
     */
    public long countSessionsByPays(String pays) {
        if (pays == null || pays.trim().isEmpty()) {
            return 0;
        }
        
        try {
            String queryString = "SELECT COUNT(s) FROM SessionsActives s WHERE s.pays = :pays AND s.estActif = true";
            TypedQuery<Long> query = entityManager.createQuery(queryString, Long.class);
            query.setParameter("pays", pays.trim());
            return query.getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }
}