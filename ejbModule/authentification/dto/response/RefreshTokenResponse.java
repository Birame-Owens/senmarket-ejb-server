package authentification.dto.response;

import authentification.base.dto.BaseResponse;
import java.sql.Timestamp;
import java.util.Map;

/**
 * DTO pour les réponses de renouvellement de token d'authentification
 * Contient le nouveau token d'accès et les informations associées
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public class RefreshTokenResponse extends BaseResponse {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Nouveau token d'accès
     */
    private String accessToken;
    
    /**
     * Type de token (Bearer, JWT, etc.)
     */
    private String tokenType = "Bearer";
    
    /**
     * Durée de vie du token en secondes
     */
    private Integer expiresIn;
    
    /**
     * Date d'expiration du token
     */
    private Timestamp expirationDate;
    
    /**
     * Nouveau refresh token (si renouvelé)
     */
    private String refreshToken;
    
    /**
     * Durée de vie du refresh token en secondes
     */
    private Integer refreshExpiresIn;
    
    /**
     * Date d'expiration du refresh token
     */
    private Timestamp refreshExpirationDate;
    
    /**
     * Scope/permissions accordées avec ce token
     */
    private String scope;
    
    /**
     * Identifiant de l'utilisateur
     */
    private String userId;
    
    /**
     * Identifiant de la session
     */
    private String sessionId;
    
    /**
     * Indique si la session a été prolongée
     */
    private boolean sessionExtended;
    
    /**
     * Nouvelle date d'expiration de la session
     */
    private Timestamp sessionExpirationDate;
    
    /**
     * Informations sur l'utilisateur (si demandées)
     */
    private Map<String, Object> userInfo;
    
    /**
     * Métadonnées du token
     */
    private Map<String, Object> tokenMetadata;
    
    /**
     * Indique si c'est le dernier renouvellement possible
     */
    private boolean isLastRefresh = false;
    
    /**
     * Nombre de renouvellements restants
     */
    private Integer remainingRefreshes;
    
    /**
     * Avertissements de sécurité
     */
    private String securityWarning;
    
    /**
     * Indique si une action utilisateur est requise bientôt
     */
    private boolean actionRequired = false;
    
    /**
     * Message d'action requise
     */
    private String actionMessage;
    
    /**
     * URL vers laquelle rediriger pour l'action requise
     */
    private String actionUrl;
    
    // Constructeur par défaut
    public RefreshTokenResponse() {
        super();
    }
    
    /**
     * Constructeur de succès
     */
    public RefreshTokenResponse(String accessToken, Integer expiresIn) {
        super(true, "Token renouvelé avec succès");
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.expirationDate = new Timestamp(System.currentTimeMillis() + (expiresIn * 1000L));
    }
    
    /**
     * Constructeur complet
     */
    public RefreshTokenResponse(String accessToken, String refreshToken, 
                              Integer expiresIn, String scope) {
        this(accessToken, expiresIn);
        this.refreshToken = refreshToken;
        this.scope = scope;
    }
    
    /**
     * Méthodes statiques pour créer des réponses typées
     */
    
    /**
     * Crée une réponse de succès simple
     */
    public static RefreshTokenResponse success(String accessToken, Integer expiresIn) {
        return new RefreshTokenResponse(accessToken, expiresIn);
    }
    
    /**
     * Crée une réponse de succès avec nouveau refresh token
     */
    public static RefreshTokenResponse successWithNewRefreshToken(String accessToken, 
                                                                String newRefreshToken,
                                                                Integer accessExpiresIn,
                                                                Integer refreshExpiresIn) {
        RefreshTokenResponse response = new RefreshTokenResponse(accessToken, accessExpiresIn);
        response.setRefreshToken(newRefreshToken);
        response.setRefreshExpiresIn(refreshExpiresIn);
        response.setRefreshExpirationDate(new Timestamp(System.currentTimeMillis() + 
                                                       (refreshExpiresIn * 1000L)));
        return response;
    }
    
    /**
     * Crée une réponse avec avertissement de sécurité
     */
    public static RefreshTokenResponse successWithWarning(String accessToken, Integer expiresIn, 
                                                        String warning) {
        RefreshTokenResponse response = new RefreshTokenResponse(accessToken, expiresIn);
        response.setSecurityWarning(warning);
        return response;
    }
    
    /**
     * Crée une réponse avec action requise
     */
    public static RefreshTokenResponse successWithActionRequired(String accessToken, Integer expiresIn,
                                                               String actionMessage, String actionUrl) {
        RefreshTokenResponse response = new RefreshTokenResponse(accessToken, expiresIn);
        response.setActionRequired(true);
        response.setActionMessage(actionMessage);
        response.setActionUrl(actionUrl);
        return response;
    }
    
    /**
     * Crée une réponse pour dernier renouvellement
     */
    public static RefreshTokenResponse lastRefresh(String accessToken, Integer expiresIn, 
                                                 String reason) {
        RefreshTokenResponse response = new RefreshTokenResponse(accessToken, expiresIn);
        response.setLastRefresh(true);
        response.setRemainingRefreshes(0);
        response.setActionRequired(true);
        response.setActionMessage("Ceci est votre dernier renouvellement de token. " + reason);
        return response;
    }
    
    /**
     * Vérifie si le token expire bientôt (dans les 5 prochaines minutes)
     */
    public boolean isExpiringSoon() {
        if (expirationDate == null) return false;
        long now = System.currentTimeMillis();
        long expiration = expirationDate.getTime();
        return (expiration - now) <= 300000; // 5 minutes en millisecondes
    }
    
    /**
     * Calcule le temps restant avant expiration en secondes
     */
    public long getTimeToExpirationSeconds() {
        if (expirationDate == null) return 0;
        long now = System.currentTimeMillis();
        long expiration = expirationDate.getTime();
        return Math.max(0, (expiration - now) / 1000);
    }
    
    /**
     * Indique si un nouveau refresh token a été fourni
     */
    public boolean hasNewRefreshToken() {
        return refreshToken != null && !refreshToken.trim().isEmpty();
    }
    
    /**
     * Ajoute des informations utilisateur
     */
    public void addUserInfo(String key, Object value) {
        if (this.userInfo == null) {
            this.userInfo = new java.util.HashMap<>();
        }
        this.userInfo.put(key, value);
    }
    
    /**
     * Ajoute des métadonnées au token
     */
    public void addTokenMetadata(String key, Object value) {
        if (this.tokenMetadata == null) {
            this.tokenMetadata = new java.util.HashMap<>();
        }
        this.tokenMetadata.put(key, value);
    }
    
    // Getters et Setters
    public String getAccessToken() {
        return accessToken;
    }
    
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public String getTokenType() {
        return tokenType;
    }
    
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    
    public Integer getExpiresIn() {
        return expiresIn;
    }
    
    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
        if (expiresIn != null) {
            this.expirationDate = new Timestamp(System.currentTimeMillis() + (expiresIn * 1000L));
        }
    }
    
    public Timestamp getExpirationDate() {
        return expirationDate;
    }
    
    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
        if (expirationDate != null) {
            long now = System.currentTimeMillis();
            this.expiresIn = (int) ((expirationDate.getTime() - now) / 1000);
        }
    }
    
    public String getRefreshToken() {
        return refreshToken;
    }
    
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    
    public Integer getRefreshExpiresIn() {
        return refreshExpiresIn;
    }
    
    public void setRefreshExpiresIn(Integer refreshExpiresIn) {
        this.refreshExpiresIn = refreshExpiresIn;
    }
    
    public Timestamp getRefreshExpirationDate() {
        return refreshExpirationDate;
    }
    
    public void setRefreshExpirationDate(Timestamp refreshExpirationDate) {
        this.refreshExpirationDate = refreshExpirationDate;
    }
    
    public String getScope() {
        return scope;
    }
    
    public void setScope(String scope) {
        this.scope = scope;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public boolean isSessionExtended() {
        return sessionExtended;
    }
    
    public void setSessionExtended(boolean sessionExtended) {
        this.sessionExtended = sessionExtended;
    }
    
    public Timestamp getSessionExpirationDate() {
        return sessionExpirationDate;
    }
    
    public void setSessionExpirationDate(Timestamp sessionExpirationDate) {
        this.sessionExpirationDate = sessionExpirationDate;
    }
    
    public Map<String, Object> getUserInfo() {
        return userInfo;
    }
    
    public void setUserInfo(Map<String, Object> userInfo) {
        this.userInfo = userInfo;
    }
    
    public Map<String, Object> getTokenMetadata() {
        return tokenMetadata;
    }
    
    public void setTokenMetadata(Map<String, Object> tokenMetadata) {
        this.tokenMetadata = tokenMetadata;
    }
    
    public boolean isLastRefresh() {
        return isLastRefresh;
    }
    
    public void setLastRefresh(boolean lastRefresh) {
        isLastRefresh = lastRefresh;
    }
    
    public Integer getRemainingRefreshes() {
        return remainingRefreshes;
    }
    
    public void setRemainingRefreshes(Integer remainingRefreshes) {
        this.remainingRefreshes = remainingRefreshes;
    }
    
    public String getSecurityWarning() {
        return securityWarning;
    }
    
    public void setSecurityWarning(String securityWarning) {
        this.securityWarning = securityWarning;
    }
    
    public boolean isActionRequired() {
        return actionRequired;
    }
    
    public void setActionRequired(boolean actionRequired) {
        this.actionRequired = actionRequired;
    }
    
    public String getActionMessage() {
        return actionMessage;
    }
    
    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }
    
    public String getActionUrl() {
        return actionUrl;
    }
    
    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }
    
    @Override
    public String toString() {
        return "RefreshTokenResponse{" +
                "success=" + getSuccess() +
                ", message='" + getMessage() + '\'' +
                ", accessToken='" + (accessToken != null ? "***" : null) + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", expiresIn=" + expiresIn +
                ", expirationDate=" + expirationDate +
                ", refreshToken='" + (refreshToken != null ? "***" : null) + '\'' +
                ", scope='" + scope + '\'' +
                ", userId='" + userId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", sessionExtended=" + sessionExtended +
                ", isLastRefresh=" + isLastRefresh +
                ", remainingRefreshes=" + remainingRefreshes +
                ", actionRequired=" + actionRequired +
                '}';
    }
}