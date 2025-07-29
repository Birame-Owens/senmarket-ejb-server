package authentification.dto.exception;

/**
 * Exception spécifique pour les erreurs d'identifiants invalides
 * Cette exception est levée lorsque l'email/mot de passe fournis sont incorrects
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public class InvalidCredentialsException extends AuthenticationException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Code d'erreur spécifique à cette exception
     */
    public static final String ERROR_CODE = "AUTH002";
    
    /**
     * Champ qui a échoué lors de la validation
     */
    private String champInvalide;
    
    /**
     * Raison spécifique de l'invalidité
     */
    private String raisonInvalidite;
    
    /**
     * Nombre de tentatives échouées pour cet utilisateur
     */
    private Integer nombreTentativesEchouees;
    
    /**
     * Dernière tentative de connexion réussie
     */
    private java.sql.Timestamp dernierConnexionReussie;
    
    /**
     * Politique de mot de passe non respectée
     */
    private boolean politiqueMotDePasseNonRespectee = false;
    
    /**
     * Mot de passe expiré
     */
    private boolean motDePasseExpire = false;
    
    /**
     * Énumération des types d'invalidité
     */
    public enum TypeInvalidite {
        EMAIL_INCORRECT("Email incorrect ou inexistant"),
        MOT_DE_PASSE_INCORRECT("Mot de passe incorrect"),
        FORMAT_EMAIL_INVALIDE("Format d'email invalide"),
        MOT_DE_PASSE_EXPIRE("Mot de passe expiré"),
        COMPTE_DESACTIVE("Compte utilisateur désactivé"),
        IDENTIFIANTS_VIDES("Identifiants non fournis"),
        MOT_DE_PASSE_TROP_FAIBLE("Mot de passe ne respecte pas la politique de sécurité");
        
        private final String description;
        
        TypeInvalidite(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    /**
     * Constructeur par défaut
     */
    public InvalidCredentialsException() {
        super("Identifiants invalides");
        this.setCodeErreur(ERROR_CODE);
        this.raisonInvalidite = "Identifiants incorrects";
    }
    
    /**
     * Constructeur avec message personnalisé
     */
    public InvalidCredentialsException(String message) {
        super(message);
        this.setCodeErreur(ERROR_CODE);
    }
    
    /**
     * Constructeur avec message et cause
     */
    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
        this.setCodeErreur(ERROR_CODE);
    }
    
    /**
     * Constructeur avec champ invalide
     */
    public InvalidCredentialsException(String message, String champInvalide, 
                                     String raisonInvalidite) {
        super(message);
        this.setCodeErreur(ERROR_CODE);
        this.champInvalide = champInvalide;
        this.raisonInvalidite = raisonInvalidite;
    }
    
    /**
     * Constructeur complet
     */
    public InvalidCredentialsException(String message, String identifiantUtilisateur,
                                     String champInvalide, String raisonInvalidite,
                                     Integer nombreTentativesEchouees) {
        super(message, identifiantUtilisateur, null, null);
        this.setCodeErreur(ERROR_CODE);
        this.champInvalide = champInvalide;
        this.raisonInvalidite = raisonInvalidite;
        this.nombreTentativesEchouees = nombreTentativesEchouees;
    }
    
    /**
     * Méthodes statiques pour créer des exceptions typiques
     */
    
    /**
     * Exception pour email incorrect
     */
    public static InvalidCredentialsException emailIncorrect(String email) {
        String message = "L'adresse email '" + email + "' n'existe pas dans nos enregistrements";
        return new InvalidCredentialsException(message, "email", 
                                             TypeInvalidite.EMAIL_INCORRECT.getDescription());
    }
    
    /**
     * Exception pour mot de passe incorrect
     */
    public static InvalidCredentialsException motDePasseIncorrect(String email, 
                                                                Integer tentativesEchouees) {
        String message = "Mot de passe incorrect pour l'utilisateur : " + email;
        return new InvalidCredentialsException(message, email, "motDePasse",
                                             TypeInvalidite.MOT_DE_PASSE_INCORRECT.getDescription(),
                                             tentativesEchouees);
    }
    
    /**
     * Exception pour format email invalide
     */
    public static InvalidCredentialsException formatEmailInvalide(String email) {
        String message = "Le format de l'adresse email '" + email + "' est invalide";
        return new InvalidCredentialsException(message, "email",
                                             TypeInvalidite.FORMAT_EMAIL_INVALIDE.getDescription());
    }
    
    /**
     * Exception pour mot de passe expiré
     */
    public static InvalidCredentialsException motDePasseExpire(String email) {
        String message = "Le mot de passe de l'utilisateur '" + email + "' a expiré";
        InvalidCredentialsException exception = new InvalidCredentialsException(message, email, 
                                               "motDePasse", TypeInvalidite.MOT_DE_PASSE_EXPIRE.getDescription(), null);
        exception.setMotDePasseExpire(true);
        return exception;
    }
    
    /**
     * Exception pour compte désactivé
     */
    public static InvalidCredentialsException compteDesactive(String email) {
        String message = "Le compte utilisateur '" + email + "' est désactivé";
        return new InvalidCredentialsException(message, email, "compte",
                                             TypeInvalidite.COMPTE_DESACTIVE.getDescription(), null);
    }
    
    /**
     * Exception pour identifiants vides
     */
    public static InvalidCredentialsException identifiantsVides() {
        String message = "L'email et le mot de passe sont obligatoires";
        return new InvalidCredentialsException(message, null,
                                             TypeInvalidite.IDENTIFIANTS_VIDES.getDescription());
    }
    
    /**
     * Exception pour mot de passe trop faible
     */
    public static InvalidCredentialsException motDePasseTropFaible(String email) {
        String message = "Le mot de passe ne respecte pas la politique de sécurité";
        InvalidCredentialsException exception = new InvalidCredentialsException(message, email, 
                                               "motDePasse", TypeInvalidite.MOT_DE_PASSE_TROP_FAIBLE.getDescription(), null);
        exception.setPolitiqueMotDePasseNonRespectee(true);
        return exception;
    }
    
    /**
     * Exception pour combinaison email/mot de passe invalide
     */
    public static InvalidCredentialsException combinaisonInvalide(String email, 
                                                                Integer tentativesEchouees) {
        String message = "La combinaison email/mot de passe est incorrecte";
        return new InvalidCredentialsException(message, email, "credentials",
                                             "Combinaison email/mot de passe incorrecte", tentativesEchouees);
    }
    
    // Getters et Setters
    public String getChampInvalide() {
        return champInvalide;
    }
    
    public void setChampInvalide(String champInvalide) {
        this.champInvalide = champInvalide;
    }
    
    public String getRaisonInvalidite() {
        return raisonInvalidite;
    }
    
    public void setRaisonInvalidite(String raisonInvalidite) {
        this.raisonInvalidite = raisonInvalidite;
    }
    
    public Integer getNombreTentativesEchouees() {
        return nombreTentativesEchouees;
    }
    
    public void setNombreTentativesEchouees(Integer nombreTentativesEchouees) {
        this.nombreTentativesEchouees = nombreTentativesEchouees;
    }
    
    public java.sql.Timestamp getDernierConnexionReussie() {
        return dernierConnexionReussie;
    }
    
    public void setDernierConnexionReussie(java.sql.Timestamp dernierConnexionReussie) {
        this.dernierConnexionReussie = dernierConnexionReussie;
    }
    
    public boolean isPolitiqueMotDePasseNonRespectee() {
        return politiqueMotDePasseNonRespectee;
    }
    
    public void setPolitiqueMotDePasseNonRespectee(boolean politiqueMotDePasseNonRespectee) {
        this.politiqueMotDePasseNonRespectee = politiqueMotDePasseNonRespectee;
    }
    
    public boolean isMotDePasseExpire() {
        return motDePasseExpire;
    }
    
    public void setMotDePasseExpire(boolean motDePasseExpire) {
        this.motDePasseExpire = motDePasseExpire;
    }
    
    @Override
    public String toString() {
        return "InvalidCredentialsException{" +
                "message='" + getMessage() + '\'' +
                ", codeErreur='" + getCodeErreur() + '\'' +
                ", champInvalide='" + champInvalide + '\'' +
                ", raisonInvalidite='" + raisonInvalidite + '\'' +
                ", nombreTentativesEchouees=" + nombreTentativesEchouees +
                ", motDePasseExpire=" + motDePasseExpire +
                ", politiqueMotDePasseNonRespectee=" + politiqueMotDePasseNonRespectee +
                '}';
    }
}