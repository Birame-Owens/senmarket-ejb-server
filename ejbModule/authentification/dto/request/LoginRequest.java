package authentification.dto.request;

import authentification.base.dto.BaseRequest;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * DTO pour les requêtes de connexion utilisateur
 * Contient les informations nécessaires pour l'authentification
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public class LoginRequest extends BaseRequest {
    
    private static final long serialVersionUID = 1L;
    
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    @Size(max = 150, message = "L'email ne peut pas dépasser 150 caractères")
    private String email;
    
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, max = 255, message = "Le mot de passe doit contenir entre 6 et 255 caractères")
    private String motDePasse;
    
    /**
     * Option "Se souvenir de moi" pour une session prolongée
     */
    private boolean seSouvenirDeMoi = false;
    
    /**
     * Type d'authentification demandé
     * Valeurs possibles: "standard", "oauth_google", "oauth_facebook"
     */
    private String typeAuthentification = "standard";
    
    /**
     * Token d'authentification externe (pour OAuth)
     */
    private String tokenExterne;
    
    /**
     * Nom de l'appareil pour identification dans les sessions
     */
    private String nomAppareil;
    
    /**
     * Indique si l'utilisateur accepte les conditions d'utilisation
     */
    private boolean accepteConditions = true;
    
    /**
     * Code de vérification 2FA (si activé)
     */
    private String codeVerification2FA;
    
    /**
     * Fuseau horaire du client
     */
    private String fuseauHoraireClient;
    
    // Constructeurs
    public LoginRequest() {
        super();
    }
    
    public LoginRequest(String email, String motDePasse) {
        super();
        this.email = email;
        this.motDePasse = motDePasse;
    }
    
    public LoginRequest(String email, String motDePasse, boolean seSouvenirDeMoi) {
        this(email, motDePasse);
        this.seSouvenirDeMoi = seSouvenirDeMoi;
    }
    
    /**
     * Constructeur pour authentification OAuth
     */
    public LoginRequest(String email, String tokenExterne, String typeAuthentification) {
        super();
        this.email = email;
        this.tokenExterne = tokenExterne;
        this.typeAuthentification = typeAuthentification;
    }
    
    /**
     * Méthodes de validation spécifiques
     */
    @Override
    public boolean isValid() {
        if (!super.isValid()) {
            return false;
        }
        
        // Validation email et mot de passe pour auth standard
        if ("standard".equals(typeAuthentification)) {
            return email != null && !email.trim().isEmpty() && 
                   motDePasse != null && !motDePasse.trim().isEmpty();
        }
        
        // Validation pour OAuth
        if (typeAuthentification != null && typeAuthentification.startsWith("oauth_")) {
            return email != null && !email.trim().isEmpty() && 
                   tokenExterne != null && !tokenExterne.trim().isEmpty();
        }
        
        return false;
    }
    
    /**
     * Vérifie si c'est une authentification OAuth
     */
    public boolean isOAuth() {
        return typeAuthentification != null && typeAuthentification.startsWith("oauth_");
    }
    
    /**
     * Vérifie si c'est une authentification Google
     */
    public boolean isGoogleAuth() {
        return "oauth_google".equals(typeAuthentification);
    }
    
    /**
     * Vérifie si l'authentification 2FA est requise
     */
    public boolean requires2FA() {
        return codeVerification2FA != null && !codeVerification2FA.trim().isEmpty();
    }
    
    /**
     * Nettoie les données sensibles pour les logs
     */
    public LoginRequest sanitizeForLogging() {
        LoginRequest sanitized = new LoginRequest();
        sanitized.setEmail(this.email);
        sanitized.setTypeAuthentification(this.typeAuthentification);
        sanitized.setSeSouvenirDeMoi(this.seSouvenirDeMoi);
        sanitized.setNomAppareil(this.nomAppareil);
        sanitized.setAccepteConditions(this.accepteConditions);
        sanitized.setFuseauHoraireClient(this.fuseauHoraireClient);
        
        // Copie des métadonnées de base sans les données sensibles
        sanitized.setRequestId(this.getRequestId());
        sanitized.setTimestamp(this.getTimestamp());
        sanitized.setLangue(this.getLangue());
        sanitized.setAdresseIp(this.getAdresseIp());
        sanitized.setTypeAppareil(this.getTypeAppareil());
        sanitized.setPays(this.getPays());
        sanitized.setVille(this.getVille());
        
        // NE PAS copier: motDePasse, tokenExterne, codeVerification2FA
        return sanitized;
    }
    
    // Getters et Setters
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email != null ? email.trim().toLowerCase() : null;
    }
    
    public String getMotDePasse() {
        return motDePasse;
    }
    
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
    
    public boolean isSeSouvenirDeMoi() {
        return seSouvenirDeMoi;
    }
    
    public void setSeSouvenirDeMoi(boolean seSouvenirDeMoi) {
        this.seSouvenirDeMoi = seSouvenirDeMoi;
    }
    
    public String getTypeAuthentification() {
        return typeAuthentification;
    }
    
    public void setTypeAuthentification(String typeAuthentification) {
        this.typeAuthentification = typeAuthentification;
    }
    
    public String getTokenExterne() {
        return tokenExterne;
    }
    
    public void setTokenExterne(String tokenExterne) {
        this.tokenExterne = tokenExterne;
    }
    
    public String getNomAppareil() {
        return nomAppareil;
    }
    
    public void setNomAppareil(String nomAppareil) {
        this.nomAppareil = nomAppareil;
    }
    
    public boolean isAccepteConditions() {
        return accepteConditions;
    }
    
    public void setAccepteConditions(boolean accepteConditions) {
        this.accepteConditions = accepteConditions;
    }
    
    public String getCodeVerification2FA() {
        return codeVerification2FA;
    }
    
    public void setCodeVerification2FA(String codeVerification2FA) {
        this.codeVerification2FA = codeVerification2FA;
    }
    
    public String getFuseauHoraireClient() {
        return fuseauHoraireClient;
    }
    
    public void setFuseauHoraireClient(String fuseauHoraireClient) {
        this.fuseauHoraireClient = fuseauHoraireClient;
    }
    
    @Override
    public String toString() {
        return "LoginRequest{" +
                "email='" + email + '\'' +
                ", typeAuthentification='" + typeAuthentification + '\'' +
                ", seSouvenirDeMoi=" + seSouvenirDeMoi +
                ", nomAppareil='" + nomAppareil + '\'' +
                ", requires2FA=" + requires2FA() +
                ", requestId='" + getRequestId() + '\'' +
                '}';
    }
}