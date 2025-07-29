package authentification.dao.interfaces;

import authentification.base.dao.GenericDAO;
import model.utilisateur.SessionsActives;
import java.sql.Timestamp;
import java.util.List;

/**
 * Interface DAO pour l'entité SessionsActives
 * Définit les opérations spécifiques à la gestion des sessions actives
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public interface SessionActiveDAO extends GenericDAO<SessionsActives, Integer> {
    
    /**
     * Trouve une session par son token
     * @param tokenSession Le token de session
     * @return La session trouvée ou null
     */
    SessionsActives findByToken(String tokenSession);
    
    /**
     * Trouve une session par son token de rafraîchissement
     * @param tokenRefresh Le token de rafraîchissement
     * @return La session trouvée ou null
     */
    SessionsActives findByRefreshToken(String tokenRefresh);
    
    /**
     * Trouve toutes les sessions actives d'un compte
     * @param compteId L'ID du compte utilisateur
     * @return Liste des sessions actives pour ce compte
     */
    List<SessionsActives> findByCompteId(Integer compteId);
    
    /**
     * Trouve toutes les sessions actives et non expirées
     * @return Liste des sessions valides
     */
    List<SessionsActives> findAllActiveSessions();
    
    /**
     * Trouve toutes les sessions expirées
     * @return Liste des sessions expirées
     */
    List<SessionsActives> findExpiredSessions();
    
    /**
     * Trouve les sessions par adresse IP
     * @param adresseIp L'adresse IP
     * @return Liste des sessions pour cette IP
     */
    List<SessionsActives> findByAdresseIp(String adresseIp);
    
    /**
     * Trouve les sessions par empreinte d'appareil
     * @param empreinteAppareil L'empreinte de l'appareil
     * @return Liste des sessions pour cet appareil
     */
    List<SessionsActives> findByEmpreinteAppareil(String empreinteAppareil);
    
    /**
     * Trouve les sessions créées après une certaine date
     * @param date La date de référence
     * @return Liste des sessions créées après cette date
     */
    List<SessionsActives> findCreatedAfter(Timestamp date);
    
    /**
     * Vérifie si un token de session existe et est valide
     * @param tokenSession Le token à vérifier
     * @return true si le token est valide, false sinon
     */
    boolean isTokenValid(String tokenSession);
    
    /**
     * Vérifie si un token est expiré
     * @param tokenSession Le token à vérifier
     * @return true si le token est expiré, false sinon
     */
    boolean isTokenExpired(String tokenSession);
    
    /**
     * Met à jour la dernière activité d'une session
     * @param sessionId L'ID de la session
     * @return Nombre de lignes affectées
     */
    int updateDerniereActivite(Integer sessionId);
    
    /**
     * Met à jour la dernière activité par token
     * @param tokenSession Le token de session
     * @return Nombre de lignes affectées
     */
    int updateDerniereActiviteByToken(String tokenSession);
    
    /**
     * Désactive une session (logout)
     * @param sessionId L'ID de la session
     * @return Nombre de lignes affectées
     */
    int desactiverSession(Integer sessionId);
    
    /**
     * Désactive une session par token
     * @param tokenSession Le token de session
     * @return Nombre de lignes affectées
     */
    int desactiverSessionByToken(String tokenSession);
    
    /**
     * Désactive toutes les sessions d'un compte
     * @param compteId L'ID du compte
     * @return Nombre de lignes affectées
     */
    int desactiverToutesSessionsCompte(Integer compteId);
    
    /**
     * Supprime les sessions expirées
     * @return Nombre de sessions supprimées
     */
    int supprimerSessionsExpirees();
    
    /**
     * Supprime les sessions inactives depuis X temps
     * @param derniereActiviteAvant Seuil de dernière activité
     * @return Nombre de sessions supprimées
     */
    int supprimerSessionsInactives(Timestamp derniereActiviteAvant);
    
    /**
     * Compte les sessions actives pour un compte
     * @param compteId L'ID du compte
     * @return Nombre de sessions actives
     */
    long countSessionsActives(Integer compteId);
    
    /**
     * Compte toutes les sessions actives du système
     * @return Nombre total de sessions actives
     */
    long countTotalSessionsActives();
    
    /**
     * Trouve les sessions suspectes (plusieurs IP différentes)
     * @param compteId L'ID du compte
     * @return Liste des sessions potentiellement suspectes
     */
    List<SessionsActives> findSessionsSuspectes(Integer compteId);
    
    /**
     * Met à jour les informations de géolocalisation
     * @param sessionId L'ID de la session
     * @param pays Le pays
     * @param ville La ville
     * @return Nombre de lignes affectées
     */
    int updateGeolocalisation(Integer sessionId, String pays, String ville);
    
    /**
     * Prolonge l'expiration d'une session
     * @param sessionId L'ID de la session
     * @param nouvelleExpiration La nouvelle date d'expiration
     * @return Nombre de lignes affectées
     */
    int prolongerSession(Integer sessionId, Timestamp nouvelleExpiration);
    
    /**
     * Trouve les sessions par période d'activité
     * @param dateDebut Date de début
     * @param dateFin Date de fin
     * @return Liste des sessions actives dans cette période
     */
    List<SessionsActives> findSessionsByPeriode(Timestamp dateDebut, Timestamp dateFin);
    
    /**
     * Compte les sessions par appareil
     * @param typeAppareil Le type d'appareil (mobile, desktop, tablet)
     * @return Nombre de sessions pour ce type d'appareil
     */
    long countSessionsByTypeAppareil(String typeAppareil);
    
    /**
     * Nettoie les sessions selon les critères de rétention
     * @param maxSessionsParCompte Nombre maximum de sessions par compte
     * @param maxAgeInactif Age maximum d'inactivité en millisecondes
     * @return Nombre de sessions nettoyées
     */
    int nettoyerSessions(int maxSessionsParCompte, long maxAgeInactif);
}