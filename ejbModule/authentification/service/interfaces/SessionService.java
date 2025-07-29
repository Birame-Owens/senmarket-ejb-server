package authentification.service.interfaces;



import authentification.dto.response.SessionResponse;
import authentification.base.dto.PageableResponse;
import model.utilisateur.SessionsActives;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Interface du service de gestion des sessions
 * Définit les opérations de création, validation et gestion des sessions utilisateur
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public interface SessionService {
    
    /**
     * Obtient toutes les sessions d'un utilisateur avec pagination
     * @param compteId ID du compte utilisateur
     * @param includeInactive Inclure les sessions inactives
     * @param page Numéro de page (0-based)
     * @param taille Taille de la page
     * @return Sessions paginées
     */
    PageableResponse<SessionResponse> getUserSessions(Integer compteId, boolean includeInactive, int page, int taille);
    
    /**
     * Obtient les sessions par adresse IP
     * @param adresseIP Adresse IP
     * @param page Numéro de page (0-based)
     * @param taille Taille de la page
     * @return Sessions paginées
     */
    PageableResponse<SessionResponse> getSessionsByIP(String adresseIP, int page, int taille);
    
    /**
     * Obtient les sessions par empreinte d'appareil
     * @param empreinteAppareil Empreinte de l'appareil
     * @param page Numéro de page (0-based)
     * @param taille Taille de la page
     * @return Sessions paginées
     */
    PageableResponse<SessionResponse> getSessionsByDeviceFingerprint(String empreinteAppareil, int page, int taille);
    
    /**
     * Obtient les sessions créées dans une période
     * @param dateDebut Date de début
     * @param dateFin Date de fin
     * @param page Numéro de page (0-based)
     * @param taille Taille de la page
     * @return Sessions paginées
     */
    PageableResponse<SessionResponse> getSessionsByDateRange(Timestamp dateDebut, Timestamp dateFin, int page, int taille);
    
    /**
     * Obtient les sessions expirées
     * @param page Numéro de page (0-based)
     * @param taille Taille de la page
     * @return Sessions expirées paginées
     */
    PageableResponse<SessionResponse> getExpiredSessions(int page, int taille);
    
    /**
     * Supprime les sessions expirées
     * @return Nombre de sessions supprimées
     */
    int cleanExpiredSessions();
    
    /**
     * Supprime les sessions inactives depuis X temps
     * @param heuresInactivite Nombre d'heures d'inactivité
     * @return Nombre de sessions supprimées
     */
    int cleanInactiveSessions(int heuresInactivite);
    
    /**
     * Nettoie automatiquement les sessions selon les critères de rétention
     * @param maxSessionsParUtilisateur Nombre maximum de sessions par utilisateur
     * @param maxHeuresInactivite Age maximum d'inactivité en heures
     * @return Nombre de sessions nettoyées
     */
    int performSessionMaintenance(int maxSessionsParUtilisateur, int maxHeuresInactivite);
    
    /**
     * Compte les sessions actives totales
     * @return Nombre de sessions actives
     */
    long countActiveSessions();
    
    /**
     * Compte les sessions actives d'un utilisateur
     * @param compteId ID du compte utilisateur
     * @return Nombre de sessions actives
     */
    long countUserActiveSessions(Integer compteId);
    
    /**
     * Compte les sessions par type d'appareil
     * @param typeAppareil Type d'appareil (mobile, desktop, tablet)
     * @return Nombre de sessions
     */
    long countSessionsByDeviceType(String typeAppareil);
    
    /**
     * Obtient les statistiques globales des sessions
     * @return Map avec les statistiques
     */
    Map<String, Object> getSessionStatistics();
    
    /**
     * Obtient les statistiques des sessions d'un utilisateur
     * @param compteId ID du compte utilisateur
     * @return Map avec les statistiques
     */
    Map<String, Object> getUserSessionStatistics(Integer compteId);
    
    /**
     * Détecte les sessions suspectes d'un utilisateur
     * @param compteId ID du compte utilisateur
     * @return Liste des sessions suspectes
     */
    List<SessionResponse> detectSuspiciousSessions(Integer compteId);
    
    /**
     * Marque une session comme suspecte
     * @param sessionId ID de la session
     * @param raisonSuspicion Raison de la suspicion
     * @return true si le marquage a réussi
     */
    boolean markSessionAsSuspicious(Integer sessionId, String raisonSuspicion);
    
    /**
     * Vérifie si une session est suspecte
     * @param sessionId ID de la session
     * @return true si la session est suspecte
     */
    boolean isSessionSuspicious(Integer sessionId);
    
    /**
     * Met à jour les informations de géolocalisation d'une session
     * @param sessionId ID de la session
     * @param pays Pays
     * @param ville Ville
     * @param coordonneesGPS Coordonnées GPS
     * @return true si la mise à jour a réussi
     */
    boolean updateSessionGeolocation(Integer sessionId, String pays, String ville, String coordonneesGPS);
    
    /**
     * Met à jour les informations d'appareil d'une session
     * @param sessionId ID de la session
     * @param appareilInfo Nouvelles informations d'appareil
     * @return true si la mise à jour a réussi
     */
    boolean updateSessionDeviceInfo(Integer sessionId, Map<String, Object> appareilInfo);
    
    /**
     * Obtient l'historique d'activité d'une session
     * @param sessionId ID de la session
     * @param page Numéro de page (0-based)
     * @param taille Taille de la page
     * @return Historique d'activité paginé
     */
    PageableResponse<Object> getSessionActivityHistory(Integer sessionId, int page, int taille);
    
    /**
     * Enregistre une activité dans une session
     * @param sessionId ID de la session
     * @param typeActivite Type d'activité
     * @param detailsActivite Détails de l'activité
     * @return true si l'enregistrement a réussi
     */
    boolean logSessionActivity(Integer sessionId, String typeActivite, Map<String, Object> detailsActivite);
    
    /**
     * Vérifie si un utilisateur a dépassé la limite de sessions simultanées
     * @param compteId ID du compte utilisateur
     * @param limiteMaximale Limite maximale autorisée
     * @return true si la limite est dépassée
     */
    boolean hasExceededSessionLimit(Integer compteId, int limiteMaximale);
    
    /**
     * Force la déconnexion des sessions les plus anciennes d'un utilisateur
     * @param compteId ID du compte utilisateur
     * @param nombreASupprimer Nombre de sessions à supprimer
     * @return Nombre de sessions supprimées
     */
    int forceLogoutOldestSessions(Integer compteId, int nombreASupprimer);
    
    /**
     * Obtient la session la plus récente d'un utilisateur
     * @param compteId ID du compte utilisateur
     * @return SessionResponse la plus récente ou null
     */
    SessionResponse getLatestUserSession(Integer compteId);
    
    /**
     * Vérifie si un token peut être rafraîchi
     * @param tokenRefresh Token de rafraîchissement
     * @return true si le token peut être rafraîchi
     */
    boolean canRefreshToken(String tokenRefresh);
    
    /**
     * Obtient les métadonnées d'une session
     * @param sessionId ID de la session
     * @return Map avec les métadonnées
     */
    Map<String, Object> getSessionMetadata(Integer sessionId);
    
    /**
     * Met à jour les métadonnées d'une session
     * @param sessionId ID de la session
     * @param metadata Nouvelles métadonnées
     * @return true si la mise à jour a réussi
     */
    boolean updateSessionMetadata(Integer sessionId, Map<String, Object> metadata);
    
    /**
     * Archive une session terminée
     * @param sessionId ID de la session à archiver
     * @return true si l'archivage a réussi
     */
    boolean archiveSession(Integer sessionId);
    
    /**
     * Obtient les sessions archivées d'un utilisateur
     * @param compteId ID du compte utilisateur
     * @param page Numéro de page (0-based)
     * @param taille Taille de la page
     * @return Sessions archivées paginées
     */
    PageableResponse<Object> getArchivedSessions(Integer compteId, int page, int taille);
    
    /**
     * Génère un rapport de sécurité des sessions
     * @param compteId ID du compte utilisateur (null = global)
     * @param periodeJours Période en jours
     * @return Rapport de sécurité
     */
    Map<String, Object> generateSecurityReport(Integer compteId, int periodeJours);
    
    /**
     * Notifie une nouvelle connexion à l'utilisateur
     * @param compteId ID du compte utilisateur
     * @param sessionInfo Informations de la nouvelle session
     * @return true si la notification a été envoyée
     */
    boolean notifyNewLogin(Integer compteId, SessionResponse sessionInfo);
    
    /**
     * Obtient les préférences de session d'un utilisateur
     * @param compteId ID du compte utilisateur
     * @return Préférences de session
     */
    Map<String, Object> getUserSessionPreferences(Integer compteId);
    
    /**
     * Met à jour les préférences de session d'un utilisateur
     * @param compteId ID du compte utilisateur
     * @param preferences Nouvelles préférences
     * @return true si la mise à jour a réussi
     */
    boolean updateUserSessionPreferences(Integer compteId, Map<String, Object> preferences);
    
    /**
     * Vérifie la validité globale du système de sessions
     * @return Rapport de santé du système de sessions
     */
    Map<String, Object> checkSessionSystemHealth();
}
     * Crée une nouvelle session pour un utilisateur
     * @param compteId ID du compte utilisateur
     * @param adresseIP Adresse IP de connexion
     * @param userAgent User agent du navigateur
     * @param appareilInfo Informations sur l'appareil
     * @param dureeSessionMinutes Durée de session en minutes (null = session permanente)
     * @return SessionResponse créée
     */
    SessionResponse createSession(Integer compteId, String adresseIP, String userAgent, 
                                 Map<String, Object> appareilInfo, Integer dureeSessionMinutes);
    
    /**
     * Obtient une session par son token
     * @param tokenSession Token de session
     * @return SessionResponse ou null si non trouvée
     */
    SessionResponse getSessionByToken(String tokenSession);
    
    /**
     * Obtient une session par son token de rafraîchissement
     * @param tokenRefresh Token de rafraîchissement
     * @return SessionResponse ou null si non trouvée
     */
    SessionResponse getSessionByRefreshToken(String tokenRefresh);
    
    /**
     * Obtient une session par son ID
     * @param sessionId ID de la session
     * @return SessionResponse ou null si non trouvée
     */
    SessionResponse getSessionById(Integer sessionId);
    
    /**
     * Valide un token de session
     * @param tokenSession Token à valider
     * @return true si le token est valide et actif
     */
    boolean validateToken(String tokenSession);
    
    /**
     * Vérifie si un token est expiré
     * @param tokenSession Token à vérifier
     * @return true si le token est expiré
     */
    boolean isTokenExpired(String tokenSession);
    
    /**
     * Met à jour la dernière activité d'une session
     * @param tokenSession Token de session
     * @return true si la mise à jour a réussi
     */
    boolean updateSessionActivity(String tokenSession);
    
    /**
     * Met à jour la dernière activité par ID de session
     * @param sessionId ID de la session
     * @return true si la mise à jour a réussi
     */
    boolean updateSessionActivityById(Integer sessionId);
    
    /**
     * Prolonge l'expiration d'une session
     * @param tokenSession Token de session
     * @param dureeSupplementaireMinutes Durée supplémentaire en minutes
     * @return true si la prolongation a réussi
     */
    boolean extendSession(String tokenSession, int dureeSupplementaireMinutes);
    
    /**
     * Invalide une session (déconnexion)
     * @param tokenSession Token de session à invalider
     * @return true si l'invalidation a réussi
     */
    boolean invalidateSession(String tokenSession);
    
    /**
     * Invalide une session par son ID
     * @param sessionId ID de la session à invalider
     * @return true si l'invalidation a réussi
     */
    boolean invalidateSessionById(Integer sessionId);
    
    /**
     * Invalide toutes les sessions d'un utilisateur
     * @param compteId ID du compte utilisateur
     * @return Nombre de sessions invalidées
     */
    int invalidateAllUserSessions(Integer compteId);
    
    /**
     * Invalide toutes les sessions d'un utilisateur sauf une
     * @param compteId ID du compte utilisateur
     * @param sessionIdToKeep ID de la session à préserver
     * @return Nombre de sessions invalidées
     */
    int invalidateAllUserSessionsExcept(Integer compteId, Integer sessionIdToKeep);
    
    /**
     * Génère un nouveau token de session
     * @param compteId ID du compte utilisateur
     * @param sessionId ID de la session
     * @return Nouveau token de session
     */
    String generateSessionToken(Integer compteId, Integer sessionId);
    
    /**
     * Génère un nouveau token de rafraîchissement
     * @param compteId ID du compte utilisateur
     * @param sessionId ID de la session
     * @return Nouveau token de rafraîchissement
     */
    String generateRefreshToken(Integer compteId, Integer sessionId);
    
    /**
     * Rafraîchit un token de session
     * @param tokenRefresh Token de rafraîchissement
     * @return Nouveau token de session ou null si échec
     */a
    String refreshSessionToken(String tokenRefresh);
    
    /**
     * Obtient toutes les sessions actives d'un utilisateur
     * @param compteId ID du compte utilisateur
     * @return Liste des sessions actives
     */
    