package authentification.remote;

import authentification.dto.request.CreateSessionRequest;
import authentification.dto.request.ExtendSessionRequest;
import authentification.dto.request.SessionInfoRequest;

import authentification.dto.response.SessionResponse;
import authentification.dto.response.SessionListResponse;
import authentification.dto.response.SessionStatsResponse;

import authentification.dto.exception.AuthenticationException;
import authentification.dto.exception.SessionExpiredException;
import authentification.dto.exception.UserNotFoundException;

import javax.ejb.Remote;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Interface remote pour la gestion des sessions dans SenMarket
 * Cette interface expose les services de gestion de session pour les clients distants
 * 
 * @author SenMarket Team
 * @version 1.0
 */
@Remote
public interface SessionRemote {
    
    /**
     * Crée une nouvelle session pour un utilisateur authentifié
     * 
     * @param request Requête de création de session avec informations utilisateur et appareil
     * @return Réponse avec détails de la session créée
     * @throws UserNotFoundException Si l'utilisateur n'existe pas
     * @throws AuthenticationException Pour toute autre erreur
     */
    SessionResponse createSession(CreateSessionRequest request) throws AuthenticationException;
    
    /**
     * Termine une session active
     * 
     * @param sessionId Identifiant de la session à terminer
     * @param reason Raison de la fermeture (optionnel)
     * @return Confirmation de fermeture de session
     * @throws SessionExpiredException Si la session est déjà expirée
     * @throws AuthenticationException Pour toute autre erreur
     */
    SessionResponse terminateSession(String sessionId, String reason) throws AuthenticationException;
    
    /**
     * Valide l'existence et la validité d'une session
     * 
     * @param sessionId Identifiant de la session à valider
     * @return Informations détaillées de la session si valide
     * @throws SessionExpiredException Si la session est expirée ou invalide
     * @throws AuthenticationException Pour toute autre erreur
     */
    SessionResponse validateSession(String sessionId) throws AuthenticationException;
    
    /**
     * Prolonge la durée de vie d'une session active
     * 
     * @param request Requête d'extension avec sessionId et nouvelle durée
     * @return Session mise à jour avec nouvelle date d'expiration
     * @throws SessionExpiredException Si la session est expirée
     * @throws AuthenticationException Pour toute autre erreur
     */
    SessionResponse extendSession(ExtendSessionRequest request) throws AuthenticationException;
    
    /**
     * Met à jour la dernière activité d'une session
     * 
     * @param sessionId Identifiant de la session
     * @param activityType Type d'activité (PAGE_VIEW, API_CALL, etc.)
     * @param activityDetails Détails de l'activité (optionnel)
     * @return Session mise à jour
     * @throws SessionExpiredException Si la session est expirée
     * @throws AuthenticationException Pour toute autre erreur
     */
    SessionResponse updateSessionActivity(String sessionId, String activityType, String activityDetails) 
            throws AuthenticationException;
    
    /**
     * Récupère toutes les sessions actives d'un utilisateur
     * 
     * @param userId Identifiant de l'utilisateur
     * @param includeExpired Inclure les sessions expirées récemment
     * @return Liste des sessions avec détails
     * @throws UserNotFoundException Si l'utilisateur n'existe pas
     * @throws AuthenticationException Pour toute autre erreur
     */
    SessionListResponse getUserSessions(String userId, boolean includeExpired) throws AuthenticationException;
    
    /**
     * Termine toutes les sessions d'un utilisateur sauf la session courante
     * 
     * @param userId Identifiant de l'utilisateur
     * @param currentSessionId Session à préserver (optionnel)
     * @param reason Raison de la fermeture des sessions
     * @return Nombre de sessions fermées
     * @throws UserNotFoundException Si l'utilisateur n'existe pas
     * @throws AuthenticationException Pour toute autre erreur
     */
    int terminateAllUserSessions(String userId, String currentSessionId, String reason) 
            throws AuthenticationException;
    
    /**
     * Termine toutes les sessions suspectes (connexions inhabituelles)
     * 
     * @param userId Identifiant de l'utilisateur
     * @param trustedSessionId Session de confiance à préserver
     * @return Nombre de sessions suspectes fermées
     * @throws UserNotFoundException Si l'utilisateur n'existe pas
     * @throws AuthenticationException Pour toute autre erreur
     */
    int terminateSuspiciousSessions(String userId, String trustedSessionId) throws AuthenticationException;
    
    /**
     * Récupère les détails complets d'une session
     * 
     * @param request Requête avec sessionId et niveau de détail souhaité
     * @return Informations complètes de la session
     * @throws SessionExpiredException Si la session est expirée
     * @throws AuthenticationException Pour toute autre erreur
     */
    SessionResponse getSessionDetails(SessionInfoRequest request) throws AuthenticationException;
    
    /**
     * Change le type de session (par exemple de temporaire à persistante)
     * 
     * @param sessionId Identifiant de la session
     * @param newType Nouveau type de session (TEMPORARY, PERSISTENT, REMEMBER_ME)
     * @param newDuration Nouvelle durée en minutes (optionnel)
     * @return Session mise à jour
     * @throws SessionExpiredException Si la session est expirée
     * @throws AuthenticationException Pour toute autre erreur
     */
    SessionResponse changeSessionType(String sessionId, String newType, Integer newDuration) 
            throws AuthenticationException;
    
    /**
     * Verrouille une session temporairement
     * Utile en cas d'activité suspecte
     * 
     * @param sessionId Identifiant de la session
     * @param lockDurationMinutes Durée du verrouillage en minutes
     * @param reason Raison du verrouillage
     * @return Session mise à jour avec statut verrouillé
     * @throws SessionExpiredException Si la session est expirée
     * @throws AuthenticationException Pour toute autre erreur
     */
    SessionResponse lockSession(String sessionId, int lockDurationMinutes, String reason) 
            throws AuthenticationException;
    
    /**
     * Déverrouille une session précédemment verrouillée
     * 
     * @param sessionId Identifiant de la session
     * @param unlockCode Code de déverrouillage (si requis)
     * @return Session déverrouillée
     * @throws SessionExpiredException Si la session est expirée
     * @throws AuthenticationException Pour toute autre erreur
     */
    SessionResponse unlockSession(String sessionId, String unlockCode) throws AuthenticationException;
    
    /**
     * Ajoute des métadonnées à une session
     * 
     * @param sessionId Identifiant de la session
     * @param metadata Métadonnées à ajouter (clé-valeur)
     * @return Session mise à jour
     * @throws SessionExpiredException Si la session est expirée
     * @throws AuthenticationException Pour toute autre erreur
     */
    SessionResponse addSessionMetadata(String sessionId, Map<String, Object> metadata) 
            throws AuthenticationException;
    
    /**
     * Supprime des métadonnées d'une session
     * 
     * @param sessionId Identifiant de la session
     * @param metadataKeys Clés des métadonnées à supprimer
     * @return Session mise à jour
     * @throws SessionExpiredException Si la session est expirée
     * @throws AuthenticationException Pour toute autre erreur
     */
    SessionResponse removeSessionMetadata(String sessionId, List<String> metadataKeys) 
            throws AuthenticationException;
    
    /**
     * Récupère les statistiques globales des sessions
     * 
     * @param fromDate Date de début pour les statistiques
     * @param toDate Date de fin pour les statistiques
     * @return Statistiques détaillées des sessions
     * @throws AuthenticationException Pour toute erreur
     */
    SessionStatsResponse getSessionStatistics(Timestamp fromDate, Timestamp toDate) 
            throws AuthenticationException;
    
    /**
     * Récupère les statistiques de session d'un utilisateur spécifique
     * 
     * @param userId Identifiant de l'utilisateur
     * @param fromDate Date de début pour les statistiques
     * @param toDate Date de fin pour les statistiques
     * @return Statistiques de session de l'utilisateur
     * @throws UserNotFoundException Si l'utilisateur n'existe pas
     * @throws AuthenticationException Pour toute autre erreur
     */
    SessionStatsResponse getUserSessionStatistics(String userId, Timestamp fromDate, Timestamp toDate) 
            throws AuthenticationException;
    
    /**
     * Nettoie les sessions expirées de la base de données
     * Méthode d'administration pour l'optimisation
     * 
     * @param olderThanDays Supprimer les sessions expirées depuis plus de X jours
     * @return Nombre de sessions nettoyées
     * @throws AuthenticationException Pour toute erreur
     */
    int cleanupExpiredSessions(int olderThanDays) throws AuthenticationException;
    
    /**
     * Force l'expiration de sessions inactives
     * 
     * @param inactiveMinutes Sessions inactives depuis plus de X minutes
     * @return Nombre de sessions expirées
     * @throws AuthenticationException Pour toute erreur
     */
    int forceExpireInactiveSessions(int inactiveMinutes) throws AuthenticationException;
    
    /**
     * Migre une session vers un nouvel utilisateur
     * Utile lors de la fusion de comptes
     * 
     * @param sessionId Identifiant de la session
     * @param newUserId Nouvel identifiant utilisateur
     * @param reason Raison de la migration
     * @return Session migrée
     * @throws SessionExpiredException Si la session est expirée
     * @throws UserNotFoundException Si le nouvel utilisateur n'existe pas
     * @throws AuthenticationException Pour toute autre erreur
     */
    SessionResponse migrateSession(String sessionId, String newUserId, String reason) 
            throws AuthenticationException;
    
    /**
     * Clone une session existante avec nouveaux paramètres
     * 
     * @param originalSessionId Session à cloner
     * @param newDeviceInfo Informations du nouvel appareil
     * @param duration Durée de la nouvelle session
     * @return Nouvelle session clonée
     * @throws SessionExpiredException Si la session originale est expirée
     * @throws AuthenticationException Pour toute autre erreur
     */
    SessionResponse cloneSession(String originalSessionId, String newDeviceInfo, Integer duration) 
            throws AuthenticationException;
    
    /**
     * Vérifie s'il y a des sessions concurrentes suspectes
     * 
     * @param userId Identifiant de l'utilisateur
     * @param currentSessionId Session courante à exclure de la vérification
     * @return true s'il y a des sessions suspectes détectées
     * @throws UserNotFoundException Si l'utilisateur n'existe pas
     * @throws AuthenticationException Pour toute autre erreur
     */
    boolean detectSuspiciousActivity(String userId, String currentSessionId) throws AuthenticationException;
    
    /**
     * Active le mode sécurisé pour une session
     * Augmente les vérifications de sécurité
     * 
     * @param sessionId Identifiant de la session
     * @param securityLevel Niveau de sécurité (LOW, MEDIUM, HIGH, CRITICAL)
     * @return Session avec mode sécurisé activé
     * @throws SessionExpiredException Si la session est expirée
     * @throws AuthenticationException Pour toute autre erreur
     */
    SessionResponse enableSecureMode(String sessionId, String securityLevel) throws AuthenticationException;
    
    /**
     * Désactive le mode sécurisé d'une session
     * 
     * @param sessionId Identifiant de la session
     * @param confirmationCode Code de confirmation pour désactiver
     * @return Session avec mode sécurisé désactivé
     * @throws SessionExpiredException Si la session est expirée
     * @throws AuthenticationException Pour toute autre erreur
     */
    SessionResponse disableSecureMode(String sessionId, String confirmationCode) throws AuthenticationException;
    
    /**
     * Récupère l'historique des activités d'une session
     * 
     * @param sessionId Identifiant de la session
     * @param limit Nombre maximum d'activités à retourner
     * @return Liste des activités de la session
     * @throws SessionExpiredException Si la session est expirée
     * @throws AuthenticationException Pour toute autre erreur
     */
    List<Map<String, Object>> getSessionActivityHistory(String sessionId, int limit) 
            throws AuthenticationException;
    
    /**
     * Marque une session comme suspecte
     * 
     * @param sessionId Identifiant de la session
     * @param reason Raison de la suspicion
     * @param severity Gravité (LOW, MEDIUM, HIGH, CRITICAL)
     * @return Session marquée comme suspecte
     * @throws SessionExpiredException Si la session est expirée
     * @throws AuthenticationException Pour toute autre erreur
     */
    SessionResponse flagSessionAsSuspicious(String sessionId, String reason, String severity) 
            throws AuthenticationException;
    
    /**
     * Supprime le marquage de suspicion d'une session
     * 
     * @param sessionId Identifiant de la session
     * @param clearanceReason Raison de la levée de suspicion
     * @return Session avec suspicion levée
     * @throws SessionExpiredException Si la session est expirée
     * @throws AuthenticationException Pour toute autre erreur
     */
    SessionResponse clearSuspiciousFlag(String sessionId, String clearanceReason) throws AuthenticationException;
}