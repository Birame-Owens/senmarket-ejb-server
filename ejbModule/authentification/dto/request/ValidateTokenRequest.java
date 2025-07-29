package authentification.dto.request;

import authentification.base.dto.BaseRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * DTO pour les requêtes de validation de token d'authentification
 * Permet de vérifier la validité et récupérer les informations d'un token
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public class ValidateTokenRequest extends BaseRequest {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Token à valider
     */
    @NotBlank(message = "Le token est obligatoire")
    @Size(min = 10, max = 1024, message = "Le token doit contenir entre 10 et 1024 caractères")
    private String token;
    
    /**
     * Type de token à valider
     */
    private TokenType tokenType = TokenType.ACCESS_TOKEN;
    
    /**
     * Indique si on veut récupérer les informations détaillées
     */
    private boolean includeUserInfo = false;
    
    /**
     * Indique si on veut récupérer les informations de session
     */
    private boolean includeSessionInfo = false;
    
    /**
     * Indique si on veut récupérer les permissions
     */
    private boolean includePermissions = false;
    
    /**
     * Indique si la validation doit mettre à jour l'activité de la session
     */
    private boolean updateActivity = true;
    
    /**
     * Ressource pour laquelle on valide le token (optionnel)
     */
    private String targetResource;
    
    /**
     * Action demandée sur la ressource (optionnel)
     */
    private String requiredAction;
    
    /**
     * Scope/permissions requises (optionnel)
     */
    private String requiredScope;
    
    /**
     * Adresse IP du client qui fait la validation
     */
    private String clientIpAddress;
    
    /**
     * User agent du client
     */
    private String clientUserAgent;
    
    /**
     * Indique si on accepte les tokens expirés récemment (grace period)
     */
    private boolean allowGracePeriod = false;
    
    /**
     * Durée de grâce en secondes (si allowGracePeriod = true)
     */
    private Integer gracePeriodSeconds = 300; // 5 minutes par défaut
    
    /**
     * Indique si la validation est stricte (vérifie IP, User-Agent, etc.)
     */
    private boolean strictValidation = false;
    
    /**
     * Énumération des types de token
     */
    public enum TokenType {
        ACCESS_TOKEN("Token d'accès"),
        REFRESH_TOKEN("Token de renouvellement"),
        SESSION_TOKEN("Token de session"),
        API_TOKEN("Token d'API"),
        VERIFICATION_TOKEN("Token de vérification"),
        RESET_TOKEN("Token de réinitialisation"),
        ACTIVATION_TOKEN("Token d'activation"),
        TWO_FACTOR_TOKEN("Token 2FA"),
        TEMPORARY_TOKEN("Token temporaire");
        
        private final String description;
        
        TokenType(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    /**
     * Énumération des niveaux de validation
     */
    public enum ValidationLevel {
        BASIC("Validation de base - existence et expiration"),
        STANDARD("Validation standard - avec permissions"),
        STRICT("Validation stricte - avec contexte complet"),
        PARANOID("Validation paranoïaque - tous les contrôles");
        
        private final String description;
        
        ValidationLevel(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    /**
     * Niveau de validation demandé
     */
    private ValidationLevel validationLevel = ValidationLevel.STANDARD;
    
    // Constructeur par défaut
    public ValidateTokenRequest() {
        super();
    }
    
    /**
     * Constructeur avec token
     */
    public ValidateTokenRequest(String token) {
        this();
        this.token = token;
    }
    
    /**
     * Constructeur avec token et type
     */
    public ValidateTokenRequest(String token, TokenType tokenType) {
        this(token);
        this.tokenType = tokenType;
    }
    
    /**
     * Constructeur complet
     */
    public ValidateTokenRequest(String token, TokenType tokenType, boolean includeUserInfo) {
        this(token, tokenType);
        this.includeUserInfo = includeUserInfo;
    }
    
    /**
     * Méthodes statiques pour créer des requêtes typées
     */
    
    /**
     * Crée une requête de validation basique
     */
    public static ValidateTokenRequest basic(String token) {
        ValidateTokenRequest request = new ValidateTokenRequest(token);
        request.setValidationLevel(ValidationLevel.BASIC);
        return request;
    }
    
    /**
     * Crée une requête de validation avec informations utilisateur
     */
    public static ValidateTokenRequest withUserInfo(String token) {
        ValidateTokenRequest request = new ValidateTokenRequest(token);
        request.setIncludeUserInfo(true);
        request.setIncludeSessionInfo(true);
        return request;
    }
    
    /**
     * Crée une requête de validation avec permissions
     */
    public static ValidateTokenRequest withPermissions(String token, String resource, String action) {
        ValidateTokenRequest request = new ValidateTokenRequest(token);
        request.setIncludePermissions(true);
        request.setTargetResource(resource);
        request.setRequiredAction(action);
        return request;
    }
    
    /**
     * Crée une requête de validation stricte
     */
    public static ValidateTokenRequest strict(String token, String clientIp, String userAgent) {
        ValidateTokenRequest request = new ValidateTokenRequest(token);
        request.setValidationLevel(ValidationLevel.STRICT);
        request.setStrictValidation(true);
        request.setClientIpAddress(clientIp);
        request.setClientUserAgent(userAgent);
        request.setIncludeUserInfo(true);
        request.setIncludeSessionInfo(true);
        request.setIncludePermissions(true);
        return request;
    }
    
    /**
     * Crée une requête de validation pour refresh token
     */
    public static ValidateTokenRequest refreshToken(String refreshToken) {
        ValidateTokenRequest request = new ValidateTokenRequest(refreshToken, TokenType.REFRESH_TOKEN);
        request.setUpdateActivity(false); // Ne pas mettre à jour l'activité pour les refresh tokens
        request.setAllowGracePeriod(true);
        return request;
    }
    
    /**
     * Crée une requête de validation pour API token
     */
    public static ValidateTokenRequest apiToken(String apiToken, String resource) {
        ValidateTokenRequest request = new ValidateTokenRequest(apiToken, TokenType.API_TOKEN);
        request.setTargetResource(resource);
        request.setIncludePermissions(true);
        request.setStrictValidation(true);
        return request;
    }
    
    /**
     * Crée une requête de validation pour token de vérification
     */
    public static ValidateTokenRequest verificationToken(String token) {
        ValidateTokenRequest request = new ValidateTokenRequest(token, TokenType.VERIFICATION_TOKEN);
        request.setUpdateActivity(false);
        request.setAllowGracePeriod(false); // Pas de grâce pour les tokens de vérification
        return request;
    }
    
    /**
     * Valide la cohérence de la requête
     */
    public boolean isValid() {
        if (token == null || token.trim().isEmpty()) return false;
        
        // Vérification spécifique selon le type de token
        switch (tokenType) {
            case REFRESH_TOKEN:
                // Les refresh tokens ne doivent généralement pas mettre à jour l'activité
                return true;
            case VERIFICATION_TOKEN:
            case RESET_TOKEN:
            case ACTIVATION_TOKEN:
                // Ces tokens ne doivent pas avoir de grâce period
                return !allowGracePeriod;
            default:
                return true;
        }
    }
    
    /**
     * Indique si la validation nécessite des informations de contexte
     */
    public boolean requiresContext() {
        return strictValidation || validationLevel == ValidationLevel.STRICT || 
               validationLevel == ValidationLevel.PARANOID;
    }
    
    /**
     * Indique si on doit vérifier les permissions
     */
    public boolean shouldCheckPermissions() {
        return includePermissions || targetResource != null || requiredAction != null;
    }
    
    /**
     * Configure la requête pour une validation complète
     */
    public ValidateTokenRequest withFullValidation() {
        this.includeUserInfo = true;
        this.includeSessionInfo = true;
        this.includePermissions = true;
        this.updateActivity = true;
        this.validationLevel = ValidationLevel.STRICT;
        return this;
    }
    
    /**
     * Configure la requête pour une validation rapide
     */
    public ValidateTokenRequest withQuickValidation() {
        this.includeUserInfo = false;
        this.includeSessionInfo = false;
        this.includePermissions = false;
        this.updateActivity = false;
        this.validationLevel = ValidationLevel.BASIC;
        return this;
    }
    
    // Getters et Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public TokenType getTokenType() {
        return tokenType;
    }
    
    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }
    
    public boolean isIncludeUserInfo() {
        return includeUserInfo;
    }
    
    public void setIncludeUserInfo(boolean includeUserInfo) {
        this.includeUserInfo = includeUserInfo;
    }
    
    public boolean isIncludeSessionInfo() {
        return includeSessionInfo;
    }
    
    public void setIncludeSessionInfo(boolean includeSessionInfo) {
        this.includeSessionInfo = includeSessionInfo;
    }
    
    public boolean isIncludePermissions() {
        return includePermissions;
    }
    
    public void setIncludePermissions(boolean includePermissions) {
        this.includePermissions = includePermissions;
    }
    
    public boolean isUpdateActivity() {
        return updateActivity;
    }
    
    public void setUpdateActivity(boolean updateActivity) {
        this.updateActivity = updateActivity;
    }
    
    public String getTargetResource() {
        return targetResource;
    }
    
    public void setTargetResource(String targetResource) {
        this.targetResource = targetResource;
    }
    
    public String getRequiredAction() {
        return requiredAction;
    }
    
    public void setRequiredAction(String requiredAction) {
        this.requiredAction = requiredAction;
    }
    
    public String getRequiredScope() {
        return requiredScope;
    }
    
    public void setRequiredScope(String requiredScope) {
        this.requiredScope = requiredScope;
    }
    
    public String getClientIpAddress() {
        return clientIpAddress;
    }
    
    public void setClientIpAddress(String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }
    
    public String getClientUserAgent() {
        return clientUserAgent;
    }
    
    public void setClientUserAgent(String clientUserAgent) {
        this.clientUserAgent = clientUserAgent;
    }
    
    public boolean isAllowGracePeriod() {
        return allowGracePeriod;
    }
    
    public void setAllowGracePeriod(boolean allowGracePeriod) {
        this.allowGracePeriod = allowGracePeriod;
    }
    
    public Integer getGracePeriodSeconds() {
        return gracePeriodSeconds;
    }
    
    public void setGracePeriodSeconds(Integer gracePeriodSeconds) {
        this.gracePeriodSeconds = gracePeriodSeconds;
    }
    
    public boolean isStrictValidation() {
        return strictValidation;
    }
    
    public void setStrictValidation(boolean strictValidation) {
        this.strictValidation = strictValidation;
    }
    
    public ValidationLevel getValidationLevel() {
        return validationLevel;
    }
    
    public void setValidationLevel(ValidationLevel validationLevel) {
        this.validationLevel = validationLevel;
    }
    
    @Override
    public String toString() {
        return "ValidateTokenRequest{" +
                "token='" + (token != null ? "***" : null) + '\'' +
                ", tokenType=" + tokenType +
                ", includeUserInfo=" + includeUserInfo +
                ", includeSessionInfo=" + includeSessionInfo +
                ", includePermissions=" + includePermissions +
                ", updateActivity=" + updateActivity +
                ", targetResource='" + targetResource + '\'' +
                ", requiredAction='" + requiredAction + '\'' +
                ", requiredScope='" + requiredScope + '\'' +
                ", clientIpAddress='" + clientIpAddress + '\'' +
                ", allowGracePeriod=" + allowGracePeriod +
                ", gracePeriodSeconds=" + gracePeriodSeconds +
                ", strictValidation=" + strictValidation +
                ", validationLevel=" + validationLevel +
                '}';
    }
}