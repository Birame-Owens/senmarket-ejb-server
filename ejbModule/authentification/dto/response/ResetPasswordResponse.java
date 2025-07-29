package authentification.dto.response;

import authentification.base.dto.BaseResponse;
import java.sql.Timestamp;

/**
 * DTO pour les réponses de réinitialisation de mot de passe
 * Gère à la fois les réponses d'initiation et de finalisation
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public class ResetPasswordResponse extends BaseResponse {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Identifiant de l'utilisateur concerné
     */
    private String userId;
    
    /**
     * Email de l'utilisateur
     */
    private String email;
    
    /**
     * Token de réinitialisation (masqué pour sécurité)
     */
    private String resetTokenId;
    
    /**
     * Date d'expiration du token de réinitialisation
     */
    private Timestamp tokenExpirationDate;
    
    /**
     * Durée de validité du token en minutes
     */
    private Integer tokenValidityMinutes;
    
    /**
     * Méthode de livraison utilisée (EMAIL, SMS)
     */
    private String deliveryMethod;
    
    /**
     * Adresse de livraison (email ou téléphone masqué)
     */
    private String deliveryAddress;
    
    /**
     * Indique si l'email/SMS a été envoyé avec succès
     */
    private boolean deliverySuccessful = true;
    
    /**
     * Type d'opération effectuée
     */
    private OperationType operationType;
    
    /**
     * Indique si le mot de passe a été changé avec succès
     */
    private boolean passwordChanged = false;
    
    /**
     * Indique si toutes les sessions ont été invalidées
     */
    private boolean sessionsInvalidated = false;
    
    /**
     * Nombre de sessions invalidées
     */
    private Integer invalidatedSessionsCount;
    
    /**
     * Indique si l'utilisateur a été connecté automatiquement
     */
    private boolean autoLoggedIn = false;
    
    /**
     * Token d'accès (si connexion automatique)
     */
    private String accessToken;
    
    /**
     * Durée du token d'accès en secondes
     */
    private Integer accessTokenExpiresIn;
    
    /**
     * Instructions pour l'utilisateur
     */
    private String userInstructions;
    
    /**
     * Lien vers la page de connexion
     */
    private String loginUrl;
    
    /**
     * Lien vers l'aide/support
     */
    private String supportUrl;
    
    /**
     * Prochaine étape recommandée
     */
    private String nextStep;
    
    /**
     * Avertissements de sécurité
     */
    private String securityWarning;
    
    /**
     * Énumération des types d'opération
     */
    public enum OperationType {
        INITIATE("Initiation de réinitialisation"),
        COMPLETE("Finalisation de réinitialisation"),
        VERIFY_TOKEN("Vérification de token");
        
        private final String description;
        
        OperationType(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    // Constructeur par défaut
    public ResetPasswordResponse() {
        super();
    }
    
    /**
     * Constructeur de succès
     */
    public ResetPasswordResponse(boolean success, String message) {
        super(success, message);
    }
    
    /**
     * Méthodes statiques pour créer des réponses typées
     */
    
    /**
     * Crée une réponse d'initiation réussie
     */
    public static ResetPasswordResponse initiateSuccess(String email, String deliveryMethod, 
                                                      Integer validityMinutes) {
        ResetPasswordResponse response = new ResetPasswordResponse(true, 
            "Instructions de réinitialisation envoyées avec succès");
        response.setEmail(email);
        response.setDeliveryMethod(deliveryMethod);
        response.setTokenValidityMinutes(validityMinutes);
        response.setOperationType(OperationType.INITIATE);
        response.setDeliveryAddress(maskEmail(email));
        response.setUserInstructions("Consultez vos " + 
            (deliveryMethod.equals("EMAIL") ? "emails" : "SMS") + 
            " pour les instructions de réinitialisation");
        response.setTokenExpirationDate(new Timestamp(System.currentTimeMillis() + 
                                                     (validityMinutes * 60 * 1000L)));
        return response;
    }
    
    /**
     * Crée une réponse d'initiation avec SMS
     */
    public static ResetPasswordResponse initiateSuccessWithSMS(String email, String phoneNumber, 
                                                             Integer validityMinutes) {
        ResetPasswordResponse response = initiateSuccess(email, "SMS", validityMinutes);
        response.setDeliveryAddress(maskPhoneNumber(phoneNumber));
        response.setUserInstructions("Un code de réinitialisation a été envoyé au " + 
                                   maskPhoneNumber(phoneNumber));
        return response;
    }
    
    /**
     * Crée une réponse de finalisation réussie
     */
    public static ResetPasswordResponse completeSuccess(String email) {
        ResetPasswordResponse response = new ResetPasswordResponse(true, 
            "Mot de passe réinitialisé avec succès");
        response.setEmail(email);
        response.setOperationType(OperationType.COMPLETE);
        response.setPasswordChanged(true);
        response.setSessionsInvalidated(true);
        response.setUserInstructions("Votre mot de passe a été mis à jour. " +
                                   "Vous pouvez maintenant vous connecter avec votre nouveau mot de passe");
        response.setNextStep("Se connecter avec le nouveau mot de passe");
        return response;
    }
    
    /**
     * Crée une réponse de finalisation avec connexion automatique
     */
    public static ResetPasswordResponse completeSuccessWithAutoLogin(String email, String userId,
                                                                   String accessToken, 
                                                                   Integer tokenExpiresIn) {
        ResetPasswordResponse response = completeSuccess(email);
        response.setUserId(userId);
        response.setAutoLoggedIn(true);
        response.setAccessToken(accessToken);
        response.setAccessTokenExpiresIn(tokenExpiresIn);
        response.setUserInstructions("Mot de passe réinitialisé et connexion automatique effectuée");
        response.setNextStep("Continuer avec votre session");
        return response;
    }
    
    /**
     * Crée une réponse de vérification de token réussie
     */
    public static ResetPasswordResponse verifyTokenSuccess(String email) {
        ResetPasswordResponse response = new ResetPasswordResponse(true, 
            "Token de réinitialisation valide");
        response.setEmail(email);
        response.setOperationType(OperationType.VERIFY_TOKEN);
        response.setUserInstructions("Vous pouvez maintenant saisir votre nouveau mot de passe");
        response.setNextStep("Saisir le nouveau mot de passe");
        return response;
    }
    
    /**
     * Crée une réponse d'échec de livraison
     */
    public static ResetPasswordResponse deliveryFailure(String email, String deliveryMethod, 
                                                      String reason) {
        ResetPasswordResponse response = new ResetPasswordResponse(false, 
            "Échec d'envoi des instructions de réinitialisation");
        response.setEmail(email);
        response.setDeliveryMethod(deliveryMethod);
        response.setOperationType(OperationType.INITIATE);
        response.setDeliverySuccessful(false);
        response.setUserInstructions("Impossible d'envoyer les instructions. " + reason);
        response.setNextStep("Contacter le support ou réessayer plus tard");
        return response;
    }
    
    /**
     * Crée une réponse avec avertissement de sécurité
     */
    public static ResetPasswordResponse withSecurityWarning(ResetPasswordResponse baseResponse, 
                                                          String warning) {
        baseResponse.setSecurityWarning(warning);
        return baseResponse;
    }
    
    /**
     * Masque une adresse email pour l'affichage
     */
    private static String maskEmail(String email) {
        if (email == null || !email.contains("@")) return email;
        
        String[] parts = email.split("@");
        String username = parts[0];
        String domain = parts[1];
        
        String maskedUsername;
        if (username.length() <= 2) {
            maskedUsername = "*".repeat(username.length());
        } else {
            maskedUsername = username.charAt(0) + "*".repeat(username.length() - 2) + 
                           username.charAt(username.length() - 1);
        }
        
        return maskedUsername + "@" + domain;
    }
    
    /**
     * Masque un numéro de téléphone pour l'affichage
     */
    private static String maskPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() <= 4) return phoneNumber;
        
        String cleaned = phoneNumber.replaceAll("[^0-9+]", "");
        if (cleaned.length() <= 4) return phoneNumber;
        
        return cleaned.substring(0, Math.min(3, cleaned.length())) + 
               "*".repeat(Math.max(0, cleaned.length() - 6)) + 
               cleaned.substring(Math.max(3, cleaned.length() - 3));
    }
    
    /**
     * Indique si c'est une réponse d'initiation
     */
    public boolean isInitiateResponse() {
        return OperationType.INITIATE.equals(operationType);
    }
    
    /**
     * Indique si c'est une réponse de finalisation
     */
    public boolean isCompleteResponse() {
        return OperationType.COMPLETE.equals(operationType);
    }
    
    /**
     * Indique si c'est une réponse de vérification
     */
    public boolean isVerifyResponse() {
        return OperationType.VERIFY_TOKEN.equals(operationType);
    }
    
    /**
     * Calcule le temps restant avant expiration du token en minutes
     */
    public long getTokenTimeToExpirationMinutes() {
        if (tokenExpirationDate == null) return 0;
        long now = System.currentTimeMillis();
        long expiration = tokenExpirationDate.getTime();
        return Math.max(0, (expiration - now) / (1000 * 60));
    }
    
    /**
     * Indique si le token expire bientôt (dans les 10 prochaines minutes)
     */
    public boolean isTokenExpiringSoon() {
        return getTokenTimeToExpirationMinutes() <= 10;
    }
    
    // Getters et Setters
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getResetTokenId() {
        return resetTokenId;
    }
    
    public void setResetTokenId(String resetTokenId) {
        this.resetTokenId = resetTokenId;
    }
    
    public Timestamp getTokenExpirationDate() {
        return tokenExpirationDate;
    }
    
    public void setTokenExpirationDate(Timestamp tokenExpirationDate) {
        this.tokenExpirationDate = tokenExpirationDate;
    }
    
    public Integer getTokenValidityMinutes() {
        return tokenValidityMinutes;
    }
    
    public void setTokenValidityMinutes(Integer tokenValidityMinutes) {
        this.tokenValidityMinutes = tokenValidityMinutes;
    }
    
    public String getDeliveryMethod() {
        return deliveryMethod;
    }
    
    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }
    
    public String getDeliveryAddress() {
        return deliveryAddress;
    }
    
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
    
    public boolean isDeliverySuccessful() {
        return deliverySuccessful;
    }
    
    public void setDeliverySuccessful(boolean deliverySuccessful) {
        this.deliverySuccessful = deliverySuccessful;
    }
    
    public OperationType getOperationType() {
        return operationType;
    }
    
    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }
    
    public boolean isPasswordChanged() {
        return passwordChanged;
    }
    
    public void setPasswordChanged(boolean passwordChanged) {
        this.passwordChanged = passwordChanged;
    }
    
    public boolean isSessionsInvalidated() {
        return sessionsInvalidated;
    }
    
    public void setSessionsInvalidated(boolean sessionsInvalidated) {
        this.sessionsInvalidated = sessionsInvalidated;
    }
    
    public Integer getInvalidatedSessionsCount() {
        return invalidatedSessionsCount;
    }
    
    public void setInvalidatedSessionsCount(Integer invalidatedSessionsCount) {
        this.invalidatedSessionsCount = invalidatedSessionsCount;
    }
    
    public boolean isAutoLoggedIn() {
        return autoLoggedIn;
    }
    
    public void setAutoLoggedIn(boolean autoLoggedIn) {
        this.autoLoggedIn = autoLoggedIn;
    }
    
    public String getAccessToken() {
        return accessToken;
    }
    
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public Integer getAccessTokenExpiresIn() {
        return accessTokenExpiresIn;
    }
    
    public void setAccessTokenExpiresIn(Integer accessTokenExpiresIn) {
        this.accessTokenExpiresIn = accessTokenExpiresIn;
    }
    
    public String getUserInstructions() {
        return userInstructions;
    }
    
    public void setUserInstructions(String userInstructions) {
        this.userInstructions = userInstructions;
    }
    
    public String getLoginUrl() {
        return loginUrl;
    }
    
    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }
    
    public String getSupportUrl() {
        return supportUrl;
    }
    
    public void setSupportUrl(String supportUrl) {
        this.supportUrl = supportUrl;
    }
    
    public String getNextStep() {
        return nextStep;
    }
    
    public void setNextStep(String nextStep) {
        this.nextStep = nextStep;
    }
    
    public String getSecurityWarning() {
        return securityWarning;
    }
    
    public void setSecurityWarning(String securityWarning) {
        this.securityWarning = securityWarning;
    }
    
    @Override
    public String toString() {
        return "ResetPasswordResponse{" +
                "success=" + getSuccess() +
                ", message='" + getMessage() + '\'' +
                ", email='" + email + '\'' +
                ", operationType=" + operationType +
                ", deliveryMethod='" + deliveryMethod + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", deliverySuccessful=" + deliverySuccessful +
                ", passwordChanged=" + passwordChanged +
                ", sessionsInvalidated=" + sessionsInvalidated +
                ", autoLoggedIn=" + autoLoggedIn +
                ", tokenValidityMinutes=" + tokenValidityMinutes +
                ", userInstructions='" + userInstructions + '\'' +
                ", nextStep='" + nextStep + '\'' +
                '}';
    }
}