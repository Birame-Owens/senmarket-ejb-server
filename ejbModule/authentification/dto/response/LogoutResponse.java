package authentification.dto.response;

import authentification.base.dto.BaseResponse;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

/**
 * DTO pour les réponses de déconnexion utilisateur
 * Contient les informations sur la déconnexion et les actions effectuées
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public class LogoutResponse extends BaseResponse {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Identifiant de l'utilisateur déconnecté
     */
    private String userId;
    
    /**
     * Email de l'utilisateur déconnecté
     */
    private String userEmail;
    
    /**
     * Identifiant de la session fermée
     */
    private String sessionId;
    
    /**
     * Date et heure de la déconnexion
     */
    private Timestamp logoutDate;
    
    /**
     * Type de déconnexion
     */
    private LogoutType logoutType;
    
    /**
     * Raison de la déconnexion
     */
    private String logoutReason;
    
    /**
     * Durée totale de la session en secondes
     */
    private Long sessionDurationSeconds;
    
    /**
     * Durée de la session formatée (ex: "2h 15m 30s")
     */
    private String sessionDurationFormatted;
    
    /**
     * Indique si c'était une déconnexion de toutes les sessions
     */
    private boolean logoutAllSessions = false;
    
    /**
     * Nombre total de sessions fermées
     */
    private Integer totalSessionsClosed = 1;
    
    /**
     * Liste des appareils/sessions fermés
     */
    private List<String> closedDevices;
    
    /**
     * Indique si les tokens ont été révoqués
     */
    private boolean tokensRevoked = true;
    
    /**
     * Nombre de tokens révoqués
     */
    private Integer revokedTokensCount;
    
    /**
     * Indique si un email de notification a été envoyé
     */
    private boolean notificationSent = false;
    
    /**
     * Statistiques de session
     */
    private SessionStatistics sessionStats;
    
    /**
     * URL de redirection après déconnexion
     */
    private String redirectUrl;
    
    /**
     * Message de remerciement personnalisé
     */
    private String thankYouMessage;
    
    /**
     * Recommandations de sécurité
     */
    private List<String> securityRecommendations;
    
    /**
     * Prochaine action recommandée
     */
    private String nextRecommendedAction;
    
    /**
     * Indique si une activité suspecte a été détectée
     */
    private boolean suspiciousActivityDetected = false;
    
    /**
     * Détails de l'activité suspecte
     */
    private String suspiciousActivityDetails;
    
    /**
     * Énumération des types de déconnexion
     */
    public enum LogoutType {
        VOLUNTARY("Déconnexion volontaire"),
        TIMEOUT("Déconnexion par timeout"),
        FORCED_ADMIN("Déconnexion forcée par administrateur"),
        FORCED_SECURITY("Déconnexion forcée pour sécurité"),
        SESSION_EXPIRED("Session expirée"),
        PASSWORD_CHANGED("Déconnexion suite changement mot de passe"),
        ACCOUNT_SUSPENDED("Déconnexion suite suspension de compte"),
        SIMULTANEOUS_LOGIN("Déconnexion suite connexion simultanée"),
        SYSTEM_MAINTENANCE("Déconnexion pour maintenance système");
        
        private final String description;
        
        LogoutType(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    /**
     * Classe pour les statistiques de session
     */
    public static class SessionStatistics {
        private Integer pagesVisited;
        private Integer actionsPerformed;
        private Timestamp firstActivity;
        private Timestamp lastActivity;
        private String mostVisitedPage;
        private Integer apiCallsCount;
        
        // Constructeurs
        public SessionStatistics() {}
        
        public SessionStatistics(Integer pagesVisited, Integer actionsPerformed) {
            this.pagesVisited = pagesVisited;
            this.actionsPerformed = actionsPerformed;
        }
        
        // Getters et Setters
        public Integer getPagesVisited() {
            return pagesVisited;
        }
        
        public void setPagesVisited(Integer pagesVisited) {
            this.pagesVisited = pagesVisited;
        }
        
        public Integer getActionsPerformed() {
            return actionsPerformed;
        }
        
        public void setActionsPerformed(Integer actionsPerformed) {
            this.actionsPerformed = actionsPerformed;
        }
        
        public Timestamp getFirstActivity() {
            return firstActivity;
        }
        
        public void setFirstActivity(Timestamp firstActivity) {
            this.firstActivity = firstActivity;
        }
        
        public Timestamp getLastActivity() {
            return lastActivity;
        }
        
        public void setLastActivity(Timestamp lastActivity) {
            this.lastActivity = lastActivity;
        }
        
        public String getMostVisitedPage() {
            return mostVisitedPage;
        }
        
        public void setMostVisitedPage(String mostVisitedPage) {
            this.mostVisitedPage = mostVisitedPage;
        }
        
        public Integer getApiCallsCount() {
            return apiCallsCount;
        }
        
        public void setApiCallsCount(Integer apiCallsCount) {
            this.apiCallsCount = apiCallsCount;
        }
        
        @Override
        public String toString() {
            return "SessionStatistics{" +
                    "pagesVisited=" + pagesVisited +
                    ", actionsPerformed=" + actionsPerformed +
                    ", firstActivity=" + firstActivity +
                    ", lastActivity=" + lastActivity +
                    ", mostVisitedPage='" + mostVisitedPage + '\'' +
                    ", apiCallsCount=" + apiCallsCount +
                    '}';
        }
    }
    
    // Constructeur par défaut
    public LogoutResponse() {
        super();
        this.logoutDate = new Timestamp(System.currentTimeMillis());
        this.closedDevices = new ArrayList<>();
        this.securityRecommendations = new ArrayList<>();
    }
    
    /**
     * Constructeur de succès
     */
    public LogoutResponse(boolean success, String message) {
        this();
        this.setSuccess(success);
        this.setMessage(message);
    }
    
    /**
     * Méthodes statiques pour créer des réponses typées
     */
    
    /**
     * Crée une réponse de déconnexion volontaire réussie
     */
    public static LogoutResponse voluntarySuccess(String userId, String sessionId) {
        LogoutResponse response = new LogoutResponse(true, "Déconnexion réussie");
        response.setUserId(userId);
        response.setSessionId(sessionId);
        response.setLogoutType(LogoutType.VOLUNTARY);
        response.setLogoutReason("Déconnexion demandée par l'utilisateur");
        response.setThankYouMessage("Merci d'avoir utilisé SenMarket. À bientôt !");
        response.setNextRecommendedAction("Vous pouvez fermer votre navigateur en toute sécurité");
        return response;
    }
    
    /**
     * Crée une réponse de déconnexion de toutes les sessions
     */
    public static LogoutResponse logoutAllSessionsSuccess(String userId, int totalSessions, 
                                                        List<String> deviceList) {
        LogoutResponse response = voluntarySuccess(userId, null);
        response.setLogoutAllSessions(true);
        response.setTotalSessionsClosed(totalSessions);
        response.setClosedDevices(deviceList);
        response.setMessage("Déconnexion de toutes les sessions réussie");
        response.setLogoutReason("Déconnexion de tous les appareils demandée par l'utilisateur");
        response.addSecurityRecommendation("Changez votre mot de passe si vous suspectez une activité non autorisée");
        return response;
    }
    
    /**
     * Crée une réponse de déconnexion par timeout
     */
    public static LogoutResponse timeoutLogout(String userId, String sessionId, 
                                             long inactivityMinutes) {
        LogoutResponse response = new LogoutResponse(true, 
            "Session fermée pour inactivité (" + inactivityMinutes + " minutes)");
        response.setUserId(userId);
        response.setSessionId(sessionId);
        response.setLogoutType(LogoutType.TIMEOUT);
        response.setLogoutReason("Inactivité de " + inactivityMinutes + " minutes");
        response.setThankYouMessage("Votre session a été fermée automatiquement pour votre sécurité");
        response.setNextRecommendedAction("Reconnectez-vous pour continuer");
        return response;
    }
    
    /**
     * Crée une réponse de déconnexion forcée par administrateur
     */
    public static LogoutResponse adminForcedLogout(String userId, String sessionId, 
                                                 String adminReason, String adminId) {
        LogoutResponse response = new LogoutResponse(true, "Déconnexion forcée par l'administrateur");
        response.setUserId(userId);
        response.setSessionId(sessionId);
        response.setLogoutType(LogoutType.FORCED_ADMIN);
        response.setLogoutReason("Action administrative : " + adminReason);
        response.setNotificationSent(true);
        response.setNextRecommendedAction("Contactez l'administrateur pour plus d'informations");
        response.addMetadonnee("adminId", adminId);
        return response;
    }
    
    /**
     * Crée une réponse de déconnexion pour sécurité
     */
    public static LogoutResponse securityLogout(String userId, String sessionId, 
                                              String securityReason) {
        LogoutResponse response = new LogoutResponse(true, 
            "Déconnexion pour raisons de sécurité");
        response.setUserId(userId);
        response.setSessionId(sessionId);
        response.setLogoutType(LogoutType.FORCED_SECURITY);
        response.setLogoutReason("Sécurité : " + securityReason);
        response.setSuspiciousActivityDetected(true);
        response.setSuspiciousActivityDetails(securityReason);
        response.setNotificationSent(true);
        response.addSecurityRecommendation("Changez immédiatement votre mot de passe");
        response.addSecurityRecommendation("Activez l'authentification à deux facteurs");
        response.addSecurityRecommendation("Vérifiez l'historique de vos connexions");
        response.setNextRecommendedAction("Sécurisez votre compte immédiatement");
        return response;
    }
    
    /**
     * Crée une réponse de déconnexion suite changement de mot de passe
     */
    public static LogoutResponse passwordChangedLogout(String userId, String sessionId) {
        LogoutResponse response = new LogoutResponse(true, 
            "Déconnexion suite au changement de mot de passe");
        response.setUserId(userId);
        response.setSessionId(sessionId);
        response.setLogoutType(LogoutType.PASSWORD_CHANGED);
        response.setLogoutReason("Changement de mot de passe effectué");
        response.setLogoutAllSessions(true);
        response.setThankYouMessage("Votre mot de passe a été mis à jour avec succès");
        response.setNextRecommendedAction("Reconnectez-vous avec votre nouveau mot de passe");
        return response;
    }
    
    /**
     * Crée une réponse de déconnexion pour connexion simultanée
     */
    public static LogoutResponse simultaneousLoginLogout(String userId, String sessionId, 
                                                       String newDeviceInfo) {
        LogoutResponse response = new LogoutResponse(true, 
            "Déconnexion suite à une nouvelle connexion");
        response.setUserId(userId);
        response.setSessionId(sessionId);
        response.setLogoutType(LogoutType.SIMULTANEOUS_LOGIN);
        response.setLogoutReason("Nouvelle connexion depuis : " + newDeviceInfo);
        response.setThankYouMessage("Une nouvelle connexion a été détectée");
        response.setNextRecommendedAction("Vérifiez si cette connexion est autorisée");
        response.addSecurityRecommendation("Si ce n'était pas vous, changez votre mot de passe");
        return response;
    }
    
    /**
     * Crée une réponse d'échec de déconnexion
     */
    public static LogoutResponse failure(String userId, String reason) {
        LogoutResponse response = new LogoutResponse(false, "Échec de la déconnexion");
        response.setUserId(userId);
        response.setLogoutReason("Erreur : " + reason);
        response.setNextRecommendedAction("Réessayez ou contactez le support");
        return response;
    }
    
    /**
     * Calcule et formate la durée de session
     */
    public void calculateSessionDuration(Timestamp sessionStart) {
        if (sessionStart != null && logoutDate != null) {
            long durationMs = logoutDate.getTime() - sessionStart.getTime();
            this.sessionDurationSeconds = durationMs / 1000;
            this.sessionDurationFormatted = formatDuration(this.sessionDurationSeconds);
        }
    }
    
    /**
     * Formate une durée en secondes vers un format lisible
     */
    private String formatDuration(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;
        
        StringBuilder formatted = new StringBuilder();
        if (hours > 0) {
            formatted.append(hours).append("h ");
        }
        if (minutes > 0) {
            formatted.append(minutes).append("m ");
        }
        if (secs > 0 || formatted.length() == 0) {
            formatted.append(secs).append("s");
        }
        
        return formatted.toString().trim();
    }
    
    /**
     * Ajoute un appareil fermé
     */
    public void addClosedDevice(String deviceInfo) {
        if (this.closedDevices == null) {
            this.closedDevices = new ArrayList<>();
        }
        this.closedDevices.add(deviceInfo);
    }
    
    /**
     * Ajoute une recommandation de sécurité
     */
    public void addSecurityRecommendation(String recommendation) {
        if (this.securityRecommendations == null) {
            this.securityRecommendations = new ArrayList<>();
        }
        this.securityRecommendations.add(recommendation);
    }
    
    /**
     * Indique si la déconnexion nécessite une attention particulière
     */
    public boolean requiresAttention() {
        return suspiciousActivityDetected || 
               logoutType == LogoutType.FORCED_SECURITY ||
               logoutType == LogoutType.FORCED_ADMIN ||
               logoutType == LogoutType.ACCOUNT_SUSPENDED;
    }
    
    /**
     * Génère un résumé de la déconnexion
     */
    public String generateLogoutSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Déconnexion ").append(getSuccess() ? "réussie" : "échouée");
        summary.append(" - Type: ").append(logoutType != null ? logoutType.getDescription() : "N/A");
        
        if (sessionDurationFormatted != null) {
            summary.append(" - Durée: ").append(sessionDurationFormatted);
        }
        
        if (totalSessionsClosed > 1) {
            summary.append(" - Sessions fermées: ").append(totalSessionsClosed);
        }
        
        if (suspiciousActivityDetected) {
            summary.append(" - ACTIVITÉ SUSPECTE DÉTECTÉE");
        }
        
        return summary.toString();
    }
    
    // Getters et Setters
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getUserEmail() {
        return userEmail;
    }
    
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public Timestamp getLogoutDate() {
        return logoutDate;
    }
    
    public void setLogoutDate(Timestamp logoutDate) {
        this.logoutDate = logoutDate;
    }
    
    public LogoutType getLogoutType() {
        return logoutType;
    }
    
    public void setLogoutType(LogoutType logoutType) {
        this.logoutType = logoutType;
    }
    
    public String getLogoutReason() {
        return logoutReason;
    }
    
    public void setLogoutReason(String logoutReason) {
        this.logoutReason = logoutReason;
    }
    
    public Long getSessionDurationSeconds() {
        return sessionDurationSeconds;
    }
    
    public void setSessionDurationSeconds(Long sessionDurationSeconds) {
        this.sessionDurationSeconds = sessionDurationSeconds;
        if (sessionDurationSeconds != null) {
            this.sessionDurationFormatted = formatDuration(sessionDurationSeconds);
        }
    }
    
    public String getSessionDurationFormatted() {
        return sessionDurationFormatted;
    }
    
    public void setSessionDurationFormatted(String sessionDurationFormatted) {
        this.sessionDurationFormatted = sessionDurationFormatted;
    }
    
    public boolean isLogoutAllSessions() {
        return logoutAllSessions;
    }
    
    public void setLogoutAllSessions(boolean logoutAllSessions) {
        this.logoutAllSessions = logoutAllSessions;
    }
    
    public Integer getTotalSessionsClosed() {
        return totalSessionsClosed;
    }
    
    public void setTotalSessionsClosed(Integer totalSessionsClosed) {
        this.totalSessionsClosed = totalSessionsClosed;
    }
    
    public List<String> getClosedDevices() {
        return closedDevices;
    }
    
    public void setClosedDevices(List<String> closedDevices) {
        this.closedDevices = closedDevices;
    }
    
    public boolean isTokensRevoked() {
        return tokensRevoked;
    }
    
    public void setTokensRevoked(boolean tokensRevoked) {
        this.tokensRevoked = tokensRevoked;
    }
    
    public Integer getRevokedTokensCount() {
        return revokedTokensCount;
    }
    
    public void setRevokedTokensCount(Integer revokedTokensCount) {
        this.revokedTokensCount = revokedTokensCount;
    }
    
    public boolean isNotificationSent() {
        return notificationSent;
    }
    
    public void setNotificationSent(boolean notificationSent) {
        this.notificationSent = notificationSent;
    }
    
    public SessionStatistics getSessionStats() {
        return sessionStats;
    }
    
    public void setSessionStats(SessionStatistics sessionStats) {
        this.sessionStats = sessionStats;
    }
    
    public String getRedirectUrl() {
        return redirectUrl;
    }
    
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
    
    public String getThankYouMessage() {
        return thankYouMessage;
    }
    
    public void setThankYouMessage(String thankYouMessage) {
        this.thankYouMessage = thankYouMessage;
    }
    
    public List<String> getSecurityRecommendations() {
        return securityRecommendations;
    }
    
    public void setSecurityRecommendations(List<String> securityRecommendations) {
        this.securityRecommendations = securityRecommendations;
    }
    
    public String getNextRecommendedAction() {
        return nextRecommendedAction;
    }
    
    public void setNextRecommendedAction(String nextRecommendedAction) {
        this.nextRecommendedAction = nextRecommendedAction;
    }
    
    public boolean isSuspiciousActivityDetected() {
        return suspiciousActivityDetected;
    }
    
    public void setSuspiciousActivityDetected(boolean suspiciousActivityDetected) {
        this.suspiciousActivityDetected = suspiciousActivityDetected;
    }
    
    public String getSuspiciousActivityDetails() {
        return suspiciousActivityDetails;
    }
    
    public void setSuspiciousActivityDetails(String suspiciousActivityDetails) {
        this.suspiciousActivityDetails = suspiciousActivityDetails;
    }
    
    @Override
    public String toString() {
        return "LogoutResponse{" +
                "success=" + getSuccess() +
                ", message='" + getMessage() + '\'' +
                ", userId='" + userId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", logoutDate=" + logoutDate +
                ", logoutType=" + logoutType +
                ", logoutReason='" + logoutReason + '\'' +
                ", sessionDurationFormatted='" + sessionDurationFormatted + '\'' +
                ", logoutAllSessions=" + logoutAllSessions +
                ", totalSessionsClosed=" + totalSessionsClosed +
                ", tokensRevoked=" + tokensRevoked +
                ", suspiciousActivityDetected=" + suspiciousActivityDetected +
                '}';
    }
}