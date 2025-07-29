package authentification.dto.request;

import authentification.base.dto.BaseRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * DTO pour les requêtes de renouvellement de token d'authentification
 * Contient le refresh token pour obtenir un nouveau token d'accès
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public class RefreshTokenRequest extends BaseRequest {
    
    private static final long serialVersionUID = 1L;
    
    @NotBlank(message = "Le refresh token est obligatoire")
    @Size(min = 10, max = 512, message = "Le refresh token doit contenir entre 10 et 512 caractères")
    private String refreshToken;
    
    /**
     * Identifiant de l'utilisateur (optionnel pour validation supplémentaire)
     */
    private String userId;
    
    /**
     * Identifiant de la session courante
     */
    private String sessionId;
    
    /**
     * Indique si le nouveau token doit prolonger la session
     */
    private boolean extendSession = true;
    
    /**
     * Durée souhaitée pour le nouveau token (en minutes)
     * Si null, utilise la durée par défaut
     */
    private Integer tokenDurationMinutes;
    
    /**
     * Scope/permissions demandées pour le nouveau token
     */
    private String requestedScope;
    
    /**
     * Indique si on veut également un nouveau refresh token
     */
    private boolean renewRefreshToken = false;
    
    /**
     * Type d'appareil qui demande le renouvellement
     */
    private String deviceType;
    
    /**
     * Informations sur l'appareil
     */
    private String deviceInfo;
    
    /**
     * Indique si la requête provient d'une application mobile
     */
    private boolean isMobileApp = false;
    
    // Constructeur par défaut
    public RefreshTokenRequest() {
        super();
    }
    
    /**
     * Constructeur avec refresh token
     */
    public RefreshTokenRequest(String refreshToken) {
        this();
        this.refreshToken = refreshToken;
    }
    
    /**
     * Constructeur complet
     */
    public RefreshTokenRequest(String refreshToken, String userId, String sessionId) {
        this(refreshToken);
        this.userId = userId;
        this.sessionId = sessionId;
    }
    
    // Getters et Setters
    public String getRefreshToken() {
        return refreshToken;
    }
    
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
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
    
    public boolean isExtendSession() {
        return extendSession;
    }
    
    public void setExtendSession(boolean extendSession) {
        this.extendSession = extendSession;
    }
    
    public Integer getTokenDurationMinutes() {
        return tokenDurationMinutes;
    }
    
    public void setTokenDurationMinutes(Integer tokenDurationMinutes) {
        this.tokenDurationMinutes = tokenDurationMinutes;
    }
    
    public String getRequestedScope() {
        return requestedScope;
    }
    
    public void setRequestedScope(String requestedScope) {
        this.requestedScope = requestedScope;
    }
    
    public boolean isRenewRefreshToken() {
        return renewRefreshToken;
    }
    
    public void setRenewRefreshToken(boolean renewRefreshToken) {
        this.renewRefreshToken = renewRefreshToken;
    }
    
    public String getDeviceType() {
        return deviceType;
    }
    
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
    
    public String getDeviceInfo() {
        return deviceInfo;
    }
    
    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
    
    public boolean isMobileApp() {
        return isMobileApp;
    }
    
    public void setMobileApp(boolean mobileApp) {
        isMobileApp = mobileApp;
    }
    
    @Override
    public String toString() {
        return "RefreshTokenRequest{" +
                "refreshToken='" + (refreshToken != null ? "***" : null) + '\'' +
                ", userId='" + userId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", extendSession=" + extendSession +
                ", tokenDurationMinutes=" + tokenDurationMinutes +
                ", requestedScope='" + requestedScope + '\'' +
                ", renewRefreshToken=" + renewRefreshToken +
                ", deviceType='" + deviceType + '\'' +
                ", isMobileApp=" + isMobileApp +
                '}';
    }
}