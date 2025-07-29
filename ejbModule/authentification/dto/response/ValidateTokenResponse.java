package authentification.dto.response;

import authentification.base.dto.BaseResponse;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * DTO pour les réponses de validation de token
 * Contient les informations de validité et les détails du token/session
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public class ValidateTokenResponse extends BaseResponse {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Indique si le token est valide
     */
    private boolean tokenValid = false;
    
    /**
     * Type de token validé
     */
    private String tokenType;
    
    /**
     * Identifiant de l'utilisateur associé au token
     */
    private String userId;
    
    /**
     * Email de l'utilisateur
     */
    private String userEmail;
    
    /**
     * Nom complet de l'utilisateur
     */
    private String userFullName;
    
    /**
     * Profil/rôle de l'utilisateur
     */
    private String userProfile;
    
    /**
     * Identifiant de la session
     */
    private String sessionId;
    
    /**
     * Date d'expiration du token
     */
    private Timestamp tokenExpirationDate;
    
    /**
     * Temps restant avant expiration en secondes
     */
    private Integer timeToExpirationSeconds;
    
    /**
     * Date d'expiration de la session
     */
    private Timestamp sessionExpirationDate;
    
    /**
     * Dernière activité de la session
     */
    private Timestamp lastActivity;
    
    /**
     * Scope/permissions accordées avec ce token
     */
    private String scope;
    
    /**
     * Liste des permissions spécifiques
     */
    private List<String> permissions;
    
    /**
     * Informations détaillées de l'utilisateur
     */
    private Map<String, Object> userInfo;
    
    /**
     * Informations détaillées de la session
     */
    private Map<String, Object> sessionInfo;
    
    /**
     * Statut de la validation
     */
    private ValidationStatus validationStatus;
    
    /**
     * Raison de l'invalidité (si token invalide)
     */
    private String invalidityReason;
    
    /**
     * Indique si le token expire bientôt
     */
    private boolean expiringSoon = false;
    
    /**
     * Seuil d'expiration en secondes (pour expiringSoon)
     */
    private Integer expiringSoonThreshold = 300; // 5 minutes
    
    /**
     * Indique si l'activité de la session a été mise à jour
     */
    private boolean activityUpdated = false;
    
    /**
     * Informations sur l'appareil de la session
     */
    private String deviceInfo;
    
    /**
     * Adresse IP de la session
     */
    private String sessionIpAddress;
    
    /**
     * Localisation approximative de la session
     */
    private String sessionLocation;
    
    /**
     * Indique si la session est suspecte
     */
    private boolean sessionSuspicious = false;
    
    /**
     * Raisons de suspicion (si applicable)
     */
    private List<String> suspicionReasons;
    
    /**
     * Actions recommandées pour l'utilisateur
     */
    private List<String> recommendedActions;
    
    /**
     * Métadonnées additionnelles
     */
    private Map<String, Object> metadata;
    
    /**
     * Énumération des statuts de validation
     */
    public enum ValidationStatus {
        VALID("Token valide"),
        EXPIRED("Token expiré"),
        INVALID_FORMAT("Format de token invalide"),
        INVALID_SIGNATURE("Signature de token invalide"),
        REVOKED("Token révoqué"),
        USER_INACTIVE("Utilisateur inactif"),
        SESSION_EXPIRED("Session expirée"),
        INSUFFICIENT_PERMISSIONS("Permissions insuffisantes"),
        SUSPICIOUS_ACTIVITY("Activité suspecte détectée");
        
        private final String description;
        
        ValidationStatus(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    // Constructeur par défaut
    public ValidateTokenResponse() {
        super();
        this.permissions = new ArrayList<>();
        this.userInfo = new HashMap<>();
        this.sessionInfo = new HashMap<>();
        this.suspicionReasons = new ArrayList<>();
        this.recommendedActions = new ArrayList<>();
        this.metadata = new HashMap<>();
    }
    
    /**
     * Constructeur de base
     */
    public ValidateTokenResponse(boolean tokenValid, ValidationStatus status) {
        this();
        this.tokenValid = tokenValid;
        this.validationStatus = status;
        this.setSuccess(tokenValid);
        this.setMessage(status.getDescription());
    }
    
    /**
     * Méthodes statiques pour créer des réponses typées
     */
    
    /**
     * Crée une réponse de token valide
     */
    public static ValidateTokenResponse valid(String userId, String sessionId, 
                                            Timestamp expirationDate) {
        ValidateTokenResponse response = new ValidateTokenResponse(true, ValidationStatus.VALID);
        response.setUserId(userId);
        response.setSessionId(sessionId);
        response.setTokenExpirationDate(expirationDate);
        response.calculateTimeToExpiration();
        response.setActivityUpdated(true);
        return response;
    }
    
    /**
     * Crée une réponse de token valide avec informations complètes
     */
    public static ValidateTokenResponse validWithFullInfo(String userId, String userEmail, 
                                                        String userFullName, String sessionId,
                                                        Timestamp expirationDate, List<String> permissions) {
        ValidateTokenResponse response = valid(userId, sessionId, expirationDate);
        response.setUserEmail(userEmail);
        response.setUserFullName(userFullName);
        response.setPermissions(permissions);
        return response;
    }
    
    /**
     * Crée une réponse de token expiré
     */
    public static ValidateTokenResponse expired(String userId, Timestamp expiredAt) {
        ValidateTokenResponse response = new ValidateTokenResponse(false, ValidationStatus.EXPIRED);
        response.setUserId(userId);
        response.setTokenExpirationDate(expiredAt);
        response.setInvalidityReason("Le token a expiré le " + expiredAt);
        response.addRecommendedAction("Renouveler le token avec le refresh token");
        response.addRecommendedAction("Se reconnecter si nécessaire");
        return response;
    }
    
    /**
     * Crée une réponse de token invalide
     */
    public static ValidateTokenResponse invalid(String reason) {
        ValidateTokenResponse response = new ValidateTokenResponse(false, ValidationStatus.INVALID_FORMAT);
        response.setInvalidityReason(reason);
        response.addRecommendedAction("Vérifier le format du token");
        response.addRecommendedAction("Se reconnecter pour obtenir un nouveau token");
        return response;
    }
    
    /**
     * Crée une réponse de token révoqué
     */
    public static ValidateTokenResponse revoked(String userId, String reason) {
        ValidateTokenResponse response = new ValidateTokenResponse(false, ValidationStatus.REVOKED);
        response.setUserId(userId);
        response.setInvalidityReason("Token révoqué : " + reason);
        response.addRecommendedAction("Se reconnecter pour obtenir un nouveau token");
        return response;
    }
    
    /**
     * Crée une réponse pour activité suspecte
     */
    public static ValidateTokenResponse suspicious(String userId, String sessionId, 
                                                 List<String> suspicionReasons) {
        ValidateTokenResponse response = new ValidateTokenResponse(false, 
                                        ValidationStatus.SUSPICIOUS_ACTIVITY);
        response.setUserId(userId);
        response.setSessionId(sessionId);
        response.setSessionSuspicious(true);
        response.setSuspicionReasons(suspicionReasons);
        response.addRecommendedAction("Vérifier l'activité récente de votre compte");
        response.addRecommendedAction("Changer votre mot de passe si nécessaire");
        response.addRecommendedAction("Activer l'authentification à deux facteurs");
        return response;
    }
    
    /**
     * Crée une réponse avec permissions insuffisantes
     */
    public static ValidateTokenResponse insufficientPermissions(String userId, String requiredPermission) {
        ValidateTokenResponse response = new ValidateTokenResponse(false, 
                                        ValidationStatus.INSUFFICIENT_PERMISSIONS);
        response.setUserId(userId);
        response.setInvalidityReason("Permission requise : " + requiredPermission);
        response.addRecommendedAction("Contacter un administrateur pour obtenir les permissions");
        return response;
    }
    
    /**
     * Calcule le temps restant avant expiration
     */
    public void calculateTimeToExpiration() {
        if (tokenExpirationDate != null) {
            long now = System.currentTimeMillis();
            long expiration = tokenExpirationDate.getTime();
            this.timeToExpirationSeconds = (int) Math.max(0, (expiration - now) / 1000);
            this.expiringSoon = this.timeToExpirationSeconds <= expiringSoonThreshold;
        }
    }
    
    /**
     * Ajoute une permission
     */
    public void addPermission(String permission) {
        if (this.permissions == null) {
            this.permissions = new ArrayList<>();
        }
        if (!this.permissions.contains(permission)) {
            this.permissions.add(permission);
        }
    }
    
    /**
     * Ajoute une information utilisateur
     */
    public void addUserInfo(String key, Object value) {
        if (this.userInfo == null) {
            this.userInfo = new HashMap<>();
        }
        this.userInfo.put(key, value);
    }
    
    /**
     * Ajoute une information de session
     */
    public void addSessionInfo(String key, Object value) {
        if (this.sessionInfo == null) {
            this.sessionInfo = new HashMap<>();
        }
        this.sessionInfo.put(key, value);
    }
    
    /**
     * Ajoute une raison de suspicion
     */
    public void addSuspicionReason(String reason) {
        if (this.suspicionReasons == null) {
            this.suspicionReasons = new ArrayList<>();
        }
        this.suspicionReasons.add(reason);
    }
    
    /**
     * Ajoute une action recommandée
     */
    public void addRecommendedAction(String action) {
        if (this.recommendedActions == null) {
            this.recommendedActions = new ArrayList<>();
        }
        this.recommendedActions.add(action);
    }
    
    /**
     * Ajoute une métadonnée
     */
    public void addMetadata(String key, Object value) {
        if (this.metadata == null) {
            this.metadata = new HashMap<>();
        }
        this.metadata.put(key, value);
    }
    
    /**
     * Vérifie si l'utilisateur a une permission spécifique
     */
    public boolean hasPermission(String permission) {
        return permissions != null && permissions.contains(permission);
    }
    
    /**
     * Indique si le token nécessite un renouvellement
     */
    public boolean needsRenewal() {
        return expiringSoon || timeToExpirationSeconds <= 600; // 10 minutes
    }
    
    /**
     * Génère un résumé de la validation
     */
    public String generateValidationSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Token ").append(tokenValid ? "VALIDE" : "INVALIDE");
        
        if (tokenValid) {
            summary.append(" - Utilisateur: ").append(userFullName != null ? userFullName : userId);
            if (timeToExpirationSeconds != null) {
                summary.append(" - Expire dans: ").append(timeToExpirationSeconds).append("s");
            }
            if (permissions != null && !permissions.isEmpty()) {
                summary.append(" - Permissions: ").append(permissions.size());
            }
        } else {
            summary.append(" - Raison: ").append(invalidityReason);
        }
        
        if (sessionSuspicious) {
            summary.append(" - ACTIVITÉ SUSPECTE DÉTECTÉE");
        }
        
        return summary.toString();
    }
    
    // Getters et Setters
    public boolean isTokenValid() {
        return tokenValid;
    }
    
    public void setTokenValid(boolean tokenValid) {
        this.tokenValid = tokenValid;
    }
    
    public String getTokenType() {
        return tokenType;
    }
    
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    
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
    
    public String getUserFullName() {
        return userFullName;
    }
    
    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }
    
    public String getUserProfile() {
        return userProfile;
    }
    
    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }
    
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public Timestamp getTokenExpirationDate() {
        return tokenExpirationDate;
    }
    
    public void setTokenExpirationDate(Timestamp tokenExpirationDate) {
        this.tokenExpirationDate = tokenExpirationDate;
        calculateTimeToExpiration();
    }
    
    public Integer getTimeToExpirationSeconds() {
        return timeToExpirationSeconds;
    }
    
    public void setTimeToExpirationSeconds(Integer timeToExpirationSeconds) {
        this.timeToExpirationSeconds = timeToExpirationSeconds;
    }
    
    public Timestamp getSessionExpirationDate() {
        return sessionExpirationDate;
    }
    
    public void setSessionExpirationDate(Timestamp sessionExpirationDate) {
        this.sessionExpirationDate = sessionExpirationDate;
    }
    
    public Timestamp getLastActivity() {
        return lastActivity;
    }
    
    public void setLastActivity(Timestamp lastActivity) {
        this.lastActivity = lastActivity;
    }
    
    public String getScope() {
        return scope;
    }
    
    public void setScope(String scope) {
        this.scope = scope;
    }
    
    public List<String> getPermissions() {
        return permissions;
    }
    
    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
    
    public Map<String, Object> getUserInfo() {
        return userInfo;
    }
    
    public void setUserInfo(Map<String, Object> userInfo) {
        this.userInfo = userInfo;
    }
    
    public Map<String, Object> getSessionInfo() {
        return sessionInfo;
    }
    
    public void setSessionInfo(Map<String, Object> sessionInfo) {
        this.sessionInfo = sessionInfo;
    }
    
    public ValidationStatus getValidationStatus() {
        return validationStatus;
    }
    
    public void setValidationStatus(ValidationStatus validationStatus) {
        this.validationStatus = validationStatus;
    }
    
    public String getInvalidityReason() {
        return invalidityReason;
    }
    
    public void setInvalidityReason(String invalidityReason) {
        this.invalidityReason = invalidityReason;
    }
    
    public boolean isExpiringSoon() {
        return expiringSoon;
    }
    
    public void setExpiringSoon(boolean expiringSoon) {
        this.expiringSoon = expiringSoon;
    }
    
    public Integer getExpiringSoonThreshold() {
        return expiringSoonThreshold;
    }
    
    public void setExpiringSoonThreshold(Integer expiringSoonThreshold) {
        this.expiringSoonThreshold = expiringSoonThreshold;
    }
    
    public boolean isActivityUpdated() {
        return activityUpdated;
    }
    
    public void setActivityUpdated(boolean activityUpdated) {
        this.activityUpdated = activityUpdated;
    }
    
    public String getDeviceInfo() {
        return deviceInfo;
    }
    
    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
    
    public String getSessionIpAddress() {
        return sessionIpAddress;
    }
    
    public void setSessionIpAddress(String sessionIpAddress) {
        this.sessionIpAddress = sessionIpAddress;
    }
    
    public String getSessionLocation() {
        return sessionLocation;
    }
    
    public void setSessionLocation(String sessionLocation) {
        this.sessionLocation = sessionLocation;
    }
    
    public boolean isSessionSuspicious() {
        return sessionSuspicious;
    }
    
    public void setSessionSuspicious(boolean sessionSuspicious) {
        this.sessionSuspicious = sessionSuspicious;
    }
    
    public List<String> getSuspicionReasons() {
        return suspicionReasons;
    }
    
    public void setSuspicionReasons(List<String> suspicionReasons) {
        this.suspicionReasons = suspicionReasons;
    }
    
    public List<String> getRecommendedActions() {
        return recommendedActions;
    }
    
    public void setRecommendedActions(List<String> recommendedActions) {
        this.recommendedActions = recommendedActions;
    }
    
    public Map<String, Object> getMetadata() {
        return metadata;
    }
    
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
    
    @Override
    public String toString() {
        return "ValidateTokenResponse{" +
                "success=" + getSuccess() +
                ", message='" + getMessage() + '\'' +
                ", tokenValid=" + tokenValid +
                ", validationStatus=" + validationStatus +
                ", userId='" + userId + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", timeToExpirationSeconds=" + timeToExpirationSeconds +
                ", expiringSoon=" + expiringSoon +
                ", activityUpdated=" + activityUpdated +
                ", sessionSuspicious=" + sessionSuspicious +
                ", permissionsCount=" + (permissions != null ? permissions.size() : 0) +
                '}';
    }
}