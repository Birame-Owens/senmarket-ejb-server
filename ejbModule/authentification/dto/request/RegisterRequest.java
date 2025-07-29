package authentification.dto.request;

import authentification.base.dto.BaseRequest;
import javax.validation.constraints.*;
import java.sql.Date;
import java.util.Map;
import java.util.HashMap;

/**
 * DTO pour les requêtes d'inscription d'un nouvel utilisateur
 * Contient toutes les informations nécessaires pour créer un compte
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public class RegisterRequest extends BaseRequest {
    
    private static final long serialVersionUID = 1L;
    
    // Informations personnelles obligatoires
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 100, message = "Le prénom ne peut pas dépasser 100 caractères")
    private String prenom;
    
    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 100, message = "Le nom ne peut pas dépasser 100 caractères")
    private String nom;
    
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    @Size(max = 150, message = "L'email ne peut pas dépasser 150 caractères")
    private String email;
    
    // Mot de passe (optionnel pour OAuth)
    @Size(min = 8, max = 255, message = "Le mot de passe doit contenir entre 8 et 255 caractères")
    private String motDePasse;
    
    @Size(min = 8, max = 255, message = "La confirmation doit contenir entre 8 et 255 caractères")
    private String confirmationMotDePasse;
    
    // Informations optionnelles
    @Pattern(regexp = "^\\+?[0-9\\s\\-\\(\\)]{8,20}$", message = "Format de téléphone invalide")
    @Size(max = 20, message = "Le téléphone ne peut pas dépasser 20 caractères")
    private String telephone;
    
    @Past(message = "La date de naissance doit être dans le passé")
    private Date dateNaissance;
    
    @Pattern(regexp = "^(M|F|Autre)$", message = "Le genre doit être M, F ou Autre")
    private String genre;
    
    // Profil utilisateur
    @NotBlank(message = "Le code profil est obligatoire")
    private String codeProfil = "CLIENT"; // Valeur par défaut
    
    // Informations d'adresse
    private String adresseLigne1;
    private String adresseLigne2;
    private String ville;
    private String region;
    private String codePostal;
    private String codePays;
    
    // Préférences
    private String languePreferee = "fr";
    private String fuseauHoraire;
    private Map<String, Object> preferences;
    
    // Authentification externe (OAuth)
    private String googleId;
    private String facebookId;
    private String appleId;
    private String tokenOAuth;
    private String typeOAuth; // "google", "facebook", "apple"
    
    // Consentements et acceptations
    @NotNull(message = "L'acceptation des conditions générales est obligatoire")
    private Boolean accepteConditionsGenerales = false;
    
    private Boolean acceptePolitiqueConfidentialite = false;
    private Boolean accepteNewsletter = false;
    private Boolean accepteNotificationsEmail = true;
    private Boolean accepteNotificationsSMS = false;
    
    // Informations de parrainage
    private String codeParrainage;
    private String emailParrain;
    
    // Vérification et sécurité
    private String captchaToken;
    private String codeVerificationTelephone;
    
    // Métadonnées d'inscription
    private String sourceInscription = "web"; // "web", "mobile", "api", "invitation"
    private String canalAcquisition; // "organic", "social", "email", "advertising"
    private String campagneMarketing;
    
    // Constructeurs
    public RegisterRequest() {
        super();
        this.preferences = new HashMap<>();
    }
    
    public RegisterRequest(String prenom, String nom, String email, String motDePasse) {
        this();
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.confirmationMotDePasse = motDePasse;
    }
    
    /**
     * Constructeur pour inscription OAuth
     */
    public RegisterRequest(String prenom, String nom, String email, String oauthId, String typeOAuth) {
        this();
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.typeOAuth = typeOAuth;
        
        switch (typeOAuth.toLowerCase()) {
            case "google":
                this.googleId = oauthId;
                break;
            case "facebook":
                this.facebookId = oauthId;
                break;
            case "apple":
                this.appleId = oauthId;
                break;
        }
    }
    
    /**
     * Méthodes de validation spécifiques
     */
    @Override
    public boolean isValid() {
        if (!super.isValid()) {
            return false;
        }
        
        // Vérifications de base
        if (prenom == null || prenom.trim().isEmpty() ||
            nom == null || nom.trim().isEmpty() ||
            email == null || email.trim().isEmpty()) {
            return false;
        }
        
        // Vérification acceptation conditions générales
        if (!Boolean.TRUE.equals(accepteConditionsGenerales)) {
            return false;
        }
        
        // Vérification mot de passe pour inscription standard
        if (!isOAuthRegistration()) {
            if (motDePasse == null || motDePasse.trim().isEmpty()) {
                return false;
            }
            
            // Vérification correspondance mots de passe
            if (!motDePasse.equals(confirmationMotDePasse)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Vérifie si c'est une inscription via OAuth
     */
    public boolean isOAuthRegistration() {
        return typeOAuth != null && !typeOAuth.trim().isEmpty() &&
               (googleId != null || facebookId != null || appleId != null);
    }
    
    /**
     * Vérifie si les mots de passe correspondent
     */
    public boolean passwordsMatch() {
        if (motDePasse == null && confirmationMotDePasse == null) {
            return true; // Pas de mot de passe = OK pour OAuth
        }
        return motDePasse != null && motDePasse.equals(confirmationMotDePasse);
    }
    
    /**
     * Retourne l'identifiant OAuth selon le type
     */
    public String getOAuthId() {
        if (typeOAuth == null) {
            return null;
        }
        
        switch (typeOAuth.toLowerCase()) {
            case "google":
                return googleId;
            case "facebook":
                return facebookId;
            case "apple":
                return appleId;
            default:
                return null;
        }
    }
    
    /**
     * Vérifie si l'utilisateur a fourni des informations d'adresse
     */
    public boolean hasAddressInfo() {
        return adresseLigne1 != null && !adresseLigne1.trim().isEmpty() &&
               ville != null && !ville.trim().isEmpty();
    }
    
    /**
     * Vérifie si l'utilisateur a un parrain
     */
    public boolean hasReferral() {
        return (codeParrainage != null && !codeParrainage.trim().isEmpty()) ||
               (emailParrain != null && !emailParrain.trim().isEmpty());
    }
    
    /**
     * Calcule un score de complétude du profil (0-100)
     */
    public int getProfileCompletionScore() {
        int score = 0;
        int totalFields = 15;
        
        // Champs obligatoires (30 points)
        if (prenom != null && !prenom.trim().isEmpty()) score += 2;
        if (nom != null && !nom.trim().isEmpty()) score += 2;
        if (email != null && !email.trim().isEmpty()) score += 2;
        
        // Champs recommandés (50 points)
        if (telephone != null && !telephone.trim().isEmpty()) score += 3;
        if (dateNaissance != null) score += 3;
        if (genre != null && !genre.trim().isEmpty()) score += 2;
        if (hasAddressInfo()) score += 4;
        if (ville != null && !ville.trim().isEmpty()) score += 2;
        if (codePays != null && !codePays.trim().isEmpty()) score += 2;
        
        // Champs optionnels (20 points)
        if (languePreferee != null && !languePreferee.trim().isEmpty()) score += 1;
        if (fuseauHoraire != null && !fuseauHoraire.trim().isEmpty()) score += 1;
        if (accepteNewsletter != null && accepteNewsletter) score += 1;
        if (preferences != null && !preferences.isEmpty()) score += 1;
        if (hasReferral()) score += 1;
        
        return Math.min(100, (score * 100) / totalFields);
    }
    
    /**
     * Ajoute une préférence utilisateur
     */
    public void addPreference(String cle, Object valeur) {
        if (this.preferences == null) {
            this.preferences = new HashMap<>();
        }
        this.preferences.put(cle, valeur);
    }
    
    /**
     * Nettoie les données sensibles pour les logs
     */
    public RegisterRequest sanitizeForLogging() {
        RegisterRequest sanitized = new RegisterRequest();
        
        // Copie les données non sensibles
        sanitized.setPrenom(this.prenom);
        sanitized.setNom(this.nom);
        sanitized.setEmail(this.email);
        sanitized.setTelephone(this.telephone);
        sanitized.setGenre(this.genre);
        sanitized.setCodeProfil(this.codeProfil);
        sanitized.setVille(this.ville);
        sanitized.setRegion(this.region);
        sanitized.setCodePays(this.codePays);
        sanitized.setLanguePreferee(this.languePreferee);
        sanitized.setTypeOAuth(this.typeOAuth);
        sanitized.setSourceInscription(this.sourceInscription);
        sanitized.setCanalAcquisition(this.canalAcquisition);
        
        // Copie des acceptations
        sanitized.setAccepteConditionsGenerales(this.accepteConditionsGenerales);
        sanitized.setAcceptePolitiqueConfidentialite(this.acceptePolitiqueConfidentialite);
        sanitized.setAccepteNewsletter(this.accepteNewsletter);
        
        // Copie des métadonnées de base
        sanitized.setRequestId(this.getRequestId());
        sanitized.setTimestamp(this.getTimestamp());
        sanitized.setLangue(this.getLangue());
        sanitized.setAdresseIp(this.getAdresseIp());
        
        // NE PAS copier: motDePasse, confirmationMotDePasse, tokens OAuth, codes de vérification
        return sanitized;
    }
    
    // Getters et Setters
    public String getPrenom() {
        return prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom != null ? prenom.trim() : null;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom != null ? nom.trim() : null;
    }
    
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
    
    public String getConfirmationMotDePasse() {
        return confirmationMotDePasse;
    }
    
    public void setConfirmationMotDePasse(String confirmationMotDePasse) {
        this.confirmationMotDePasse = confirmationMotDePasse;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone != null ? telephone.trim() : null;
    }
    
    public Date getDateNaissance() {
        return dateNaissance;
    }
    
    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public String getCodeProfil() {
        return codeProfil;
    }
    
    public void setCodeProfil(String codeProfil) {
        this.codeProfil = codeProfil;
    }
    
    public String getAdresseLigne1() {
        return adresseLigne1;
    }
    
    public void setAdresseLigne1(String adresseLigne1) {
        this.adresseLigne1 = adresseLigne1;
    }
    
    public String getAdresseLigne2() {
        return adresseLigne2;
    }
    
    public void setAdresseLigne2(String adresseLigne2) {
        this.adresseLigne2 = adresseLigne2;
    }
    
    public String getVille() {
        return ville;
    }
    
    public void setVille(String ville) {
        this.ville = ville;
    }
    
    public String getRegion() {
        return region;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }
    
    public String getCodePostal() {
        return codePostal;
    }
    
    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }
    
    public String getCodePays() {
        return codePays;
    }
    
    public void setCodePays(String codePays) {
        this.codePays = codePays;
    }
    
    public String getLanguePreferee() {
        return languePreferee;
    }
    
    public void setLanguePreferee(String languePreferee) {
        this.languePreferee = languePreferee;
    }
    
    public String getFuseauHoraire() {
        return fuseauHoraire;
    }
    
    public void setFuseauHoraire(String fuseauHoraire) {
        this.fuseauHoraire = fuseauHoraire;
    }
    
    public Map<String, Object> getPreferences() {
        return preferences;
    }
    
    public void setPreferences(Map<String, Object> preferences) {
        this.preferences = preferences;
    }
    
    public String getGoogleId() {
        return googleId;
    }
    
    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }
    
    public String getFacebookId() {
        return facebookId;
    }
    
    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }
    
    public String getAppleId() {
        return appleId;
    }
    
    public void setAppleId(String appleId) {
        this.appleId = appleId;
    }
    
    public String getTokenOAuth() {
        return tokenOAuth;
    }
    
    public void setTokenOAuth(String tokenOAuth) {
        this.tokenOAuth = tokenOAuth;
    }
    
    public String getTypeOAuth() {
        return typeOAuth;
    }
    
    public void setTypeOAuth(String typeOAuth) {
        this.typeOAuth = typeOAuth;
    }
    
    public Boolean getAccepteConditionsGenerales() {
        return accepteConditionsGenerales;
    }
    
    public void setAccepteConditionsGenerales(Boolean accepteConditionsGenerales) {
        this.accepteConditionsGenerales = accepteConditionsGenerales;
    }
    
    public Boolean getAcceptePolitiqueConfidentialite() {
        return acceptePolitiqueConfidentialite;
    }
    
    public void setAcceptePolitiqueConfidentialite(Boolean acceptePolitiqueConfidentialite) {
        this.acceptePolitiqueConfidentialite = acceptePolitiqueConfidentialite;
    }
    
    public Boolean getAccepteNewsletter() {
        return accepteNewsletter;
    }
    
    public void setAccepteNewsletter(Boolean accepteNewsletter) {
        this.accepteNewsletter = accepteNewsletter;
    }
    
    public Boolean getAccepteNotificationsEmail() {
        return accepteNotificationsEmail;
    }
    
    public void setAccepteNotificationsEmail(Boolean accepteNotificationsEmail) {
        this.accepteNotificationsEmail = accepteNotificationsEmail;
    }
    
    public Boolean getAccepteNotificationsSMS() {
        return accepteNotificationsSMS;
    }
    
    public void setAccepteNotificationsSMS(Boolean accepteNotificationsSMS) {
        this.accepteNotificationsSMS = accepteNotificationsSMS;
    }
    
    public String getCodeParrainage() {
        return codeParrainage;
    }
    
    public void setCodeParrainage(String codeParrainage) {
        this.codeParrainage = codeParrainage;
    }
    
    public String getEmailParrain() {
        return emailParrain;
    }
    
    public void setEmailParrain(String emailParrain) {
        this.emailParrain = emailParrain;
    }
    
    public String getCaptchaToken() {
        return captchaToken;
    }
    
    public void setCaptchaToken(String captchaToken) {
        this.captchaToken = captchaToken;
    }
    
    public String getCodeVerificationTelephone() {
        return codeVerificationTelephone;
    }
    
    public void setCodeVerificationTelephone(String codeVerificationTelephone) {
        this.codeVerificationTelephone = codeVerificationTelephone;
    }
    
    public String getSourceInscription() {
        return sourceInscription;
    }
    
    public void setSourceInscription(String sourceInscription) {
        this.sourceInscription = sourceInscription;
    }
    
    public String getCanalAcquisition() {
        return canalAcquisition;
    }
    
    public void setCanalAcquisition(String canalAcquisition) {
        this.canalAcquisition = canalAcquisition;
    }
    
    public String getCampagneMarketing() {
        return campagneMarketing;
    }
    
    public void setCampagneMarketing(String campagneMarketing) {
        this.campagneMarketing = campagneMarketing;
    }
    
    @Override
    public String toString() {
        return "RegisterRequest{" +
                "prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", codeProfil='" + codeProfil + '\'' +
                ", ville='" + ville + '\'' +
                ", isOAuth=" + isOAuthRegistration() +
                ", typeOAuth='" + typeOAuth + '\'' +
                ", completionScore=" + getProfileCompletionScore() + "%" +
                ", accepteConditions=" + accepteConditionsGenerales +
                ", sourceInscription='" + sourceInscription + '\'' +
                ", requestId='" + getRequestId() + '\'' +
                '}';
    }
}