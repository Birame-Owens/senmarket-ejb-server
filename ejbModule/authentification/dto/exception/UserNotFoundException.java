package authentification.dto.exception;

import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

/**
 * Exception spécifique pour les utilisateurs non trouvés
 * Cette exception est levée lorsqu'un utilisateur recherché n'existe pas dans le système
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public class UserNotFoundException extends AuthenticationException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Code d'erreur spécifique à cette exception
     */
    public static final String ERROR_CODE = "AUTH005";
    
    /**
     * Identifiant recherché (email, ID, nom d'utilisateur)
     */
    private String identifiantRecherche;
    
    /**
     * Type d'identifiant utilisé pour la recherche
     */
    private TypeIdentifiant typeIdentifiantRecherche;
    
    /**
     * Critères de recherche utilisés
     */
    private List<String> criteresRecherche;
    
    /**
     * Suggestions d'utilisateurs similaires
     */
    private List<String> suggestionsSimilaires;
    
    /**
     * Indique si l'utilisateur a pu exister par le passé
     */
    private boolean utilisateurSupprime = false;
    
    /**
     * Date de suppression si applicable
     */
    private Timestamp dateSuppression;
    
    /**
     * Raison de la non-existence
     */
    private RaisonNonExistence raisonNonExistence;
    
    /**
     * Action suggérée à l'utilisateur
     */
    private ActionSuggereRecherche actionSuggeree;
    
    /**
     * URL d'inscription pour nouveaux utilisateurs
     */
    private String urlInscription;
    
    /**
     * URL de récupération de compte
     */
    private String urlRecuperationCompte;
    
    /**
     * Domaine de l'email si format email invalide
     */
    private String domaineEmailSuggere;
    
    /**
     * Informations de debug pour la recherche
     */
    private String informationsDebogage;
    
    /**
     * Énumération des types d'identifiant
     */
    public enum TypeIdentifiant {
        EMAIL("Adresse email"),
        ID_UTILISATEUR("Identifiant utilisateur"),
        NOM_UTILISATEUR("Nom d'utilisateur"),
        TELEPHONE("Numéro de téléphone"),
        IDENTIFIANT_EXTERNE("Identifiant externe (OAuth)"),
        TOKEN_RESET("Token de réinitialisation"),
        TOKEN_ACTIVATION("Token d'activation");
        
        private final String description;
        
        TypeIdentifiant(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    /**
     * Énumération des raisons de non-existence
     */
    public enum RaisonNonExistence {
        JAMAIS_EXISTE("L'utilisateur n'a jamais existé"),
        COMPTE_SUPPRIME("Le compte a été supprimé"),
        COMPTE_DESACTIVE("Le compte est désactivé"),
        IDENTIFIANT_INVALIDE("Format d'identifiant invalide"),
        DONNEES_CORROMPUES("Données utilisateur corrompues"),
        MIGRATION_DONNEES("Données perdues lors d'une migration"),
        EXPIRATION_TOKEN("Token de recherche expiré"),
        ACCES_RESTREINT("Accès restreint à cet utilisateur");
        
        private final String description;
        
        RaisonNonExistence(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    /**
     * Énumération des actions suggérées
     */
    public enum ActionSuggereRecherche {
        CREER_COMPTE("Créer un nouveau compte"),
        VERIFIER_ORTHOGRAPHE("Vérifier l'orthographe"),
        CONTACTER_SUPPORT("Contacter le support"),
        RECUPERER_COMPTE("Récupérer le compte"),
        UTILISER_AUTRE_IDENTIFIANT("Utiliser un autre identifiant"),
        DEMANDER_REACTIVATION("Demander la réactivation du compte");
        
        private final String description;
        
        ActionSuggereRecherche(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    /**
     * Constructeur par défaut
     */
    public UserNotFoundException() {
        super("Utilisateur non trouvé");
        this.setCodeErreur(ERROR_CODE);
        this.criteresRecherche = new ArrayList<>();
        this.suggestionsSimilaires = new ArrayList<>();
        this.raisonNonExistence = RaisonNonExistence.JAMAIS_EXISTE;
        this.actionSuggeree = ActionSuggereRecherche.VERIFIER_ORTHOGRAPHE;
    }
    
    /**
     * Constructeur avec message personnalisé
     */
    public UserNotFoundException(String message) {
        super(message);
        this.setCodeErreur(ERROR_CODE);
        this.criteresRecherche = new ArrayList<>();
        this.suggestionsSimilaires = new ArrayList<>();
    }
    
    /**
     * Constructeur avec message et cause
     */
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.setCodeErreur(ERROR_CODE);
        this.criteresRecherche = new ArrayList<>();
        this.suggestionsSimilaires = new ArrayList<>();
    }
    
    /**
     * Constructeur avec identifiant recherché
     */
    public UserNotFoundException(String message, String identifiantRecherche, 
                               TypeIdentifiant typeIdentifiant) {
        super(message);
        this.setCodeErreur(ERROR_CODE);
        this.identifiantRecherche = identifiantRecherche;
        this.typeIdentifiantRecherche = typeIdentifiant;
        this.criteresRecherche = new ArrayList<>();
        this.suggestionsSimilaires = new ArrayList<>();
    }
    
    /**
     * Constructeur complet
     */
    public UserNotFoundException(String message, String identifiantRecherche,
                               TypeIdentifiant typeIdentifiant, RaisonNonExistence raisonNonExistence,
                               ActionSuggereRecherche actionSuggeree) {
        super(message, identifiantRecherche, null, null);
        this.setCodeErreur(ERROR_CODE);
        this.identifiantRecherche = identifiantRecherche;
        this.typeIdentifiantRecherche = typeIdentifiant;
        this.raisonNonExistence = raisonNonExistence;
        this.actionSuggeree = actionSuggeree;
        this.criteresRecherche = new ArrayList<>();
        this.suggestionsSimilaires = new ArrayList<>();
    }
    
    /**
     * Méthodes statiques pour créer des exceptions typiques
     */
    
    /**
     * Exception pour email non trouvé
     */
    public static UserNotFoundException emailNonTrouve(String email) {
        String message = "Aucun compte n'est associé à l'adresse email '" + email + 
                        "'. Vérifiez l'orthographe ou créez un nouveau compte.";
        UserNotFoundException exception = new UserNotFoundException(message, email, 
                                         TypeIdentifiant.EMAIL, RaisonNonExistence.JAMAIS_EXISTE,
                                         ActionSuggereRecherche.VERIFIER_ORTHOGRAPHE);
        exception.addCritereRecherche("email=" + email);
        
        // Suggestion de domaine email si faute de frappe probable
        if (email.contains("gmail") && !email.contains("gmail.com")) {
            exception.setDomaineEmailSuggere("gmail.com");
            exception.addSuggestionSimilaire(email.replaceAll("gmail.*", "gmail.com"));
        }
        
        return exception;
    }
    
    /**
     * Exception pour ID utilisateur non trouvé
     */
    public static UserNotFoundException idUtilisateurNonTrouve(Long idUtilisateur) {
        String message = "L'utilisateur avec l'ID '" + idUtilisateur + "' n'existe pas.";
        UserNotFoundException exception = new UserNotFoundException(message, idUtilisateur.toString(), 
                                         TypeIdentifiant.ID_UTILISATEUR, RaisonNonExistence.JAMAIS_EXISTE,
                                         ActionSuggereRecherche.CONTACTER_SUPPORT);
        exception.addCritereRecherche("id=" + idUtilisateur);
        return exception;
    }
    
    /**
     * Exception pour compte supprimé
     */
    public static UserNotFoundException compteSupprime(String email, Timestamp dateSuppression) {
        String message = "Le compte associé à '" + email + "' a été supprimé le " + 
                        dateSuppression + ". Contactez le support pour une éventuelle récupération.";
        UserNotFoundException exception = new UserNotFoundException(message, email, 
                                         TypeIdentifiant.EMAIL, RaisonNonExistence.COMPTE_SUPPRIME,
                                         ActionSuggereRecherche.CONTACTER_SUPPORT);
        exception.setUtilisateurSupprime(true);
        exception.setDateSuppression(dateSuppression);
        exception.addCritereRecherche("email=" + email);
        return exception;
    }
    
    /**
     * Exception pour compte désactivé
     */
    public static UserNotFoundException compteDesactive(String email) {
        String message = "Le compte associé à '" + email + 
                        "' est désactivé. Demandez la réactivation ou contactez le support.";
        UserNotFoundException exception = new UserNotFoundException(message, email, 
                                         TypeIdentifiant.EMAIL, RaisonNonExistence.COMPTE_DESACTIVE,
                                         ActionSuggereRecherche.DEMANDER_REACTIVATION);
        exception.addCritereRecherche("email=" + email);
        return exception;
    }
    
    /**
     * Exception pour nom d'utilisateur non trouvé
     */
    public static UserNotFoundException nomUtilisateurNonTrouve(String nomUtilisateur) {
        String message = "Le nom d'utilisateur '" + nomUtilisateur + "' n'existe pas.";
        UserNotFoundException exception = new UserNotFoundException(message, nomUtilisateur, 
                                         TypeIdentifiant.NOM_UTILISATEUR, RaisonNonExistence.JAMAIS_EXISTE,
                                         ActionSuggereRecherche.VERIFIER_ORTHOGRAPHE);
        exception.addCritereRecherche("nomUtilisateur=" + nomUtilisateur);
        return exception;
    }
    
    /**
     * Exception pour téléphone non trouvé
     */
    public static UserNotFoundException telephoneNonTrouve(String telephone) {
        String message = "Aucun compte n'est associé au numéro de téléphone '" + telephone + "'.";
        UserNotFoundException exception = new UserNotFoundException(message, telephone, 
                                         TypeIdentifiant.TELEPHONE, RaisonNonExistence.JAMAIS_EXISTE,
                                         ActionSuggereRecherche.UTILISER_AUTRE_IDENTIFIANT);
        exception.addCritereRecherche("telephone=" + telephone);
        return exception;
    }
    
    /**
     * Exception pour token de réinitialisation invalide
     */
    public static UserNotFoundException tokenResetInvalide(String token) {
        String message = "Le lien de réinitialisation est invalide ou a expiré. " +
                        "Demandez un nouveau lien de réinitialisation.";
        UserNotFoundException exception = new UserNotFoundException(message, token, 
                                         TypeIdentifiant.TOKEN_RESET, RaisonNonExistence.EXPIRATION_TOKEN,
                                         ActionSuggereRecherche.RECUPERER_COMPTE);
        exception.addCritereRecherche("tokenReset=" + token.substring(0, Math.min(10, token.length())) + "...");
        return exception;
    }
    
    /**
     * Exception pour token d'activation invalide
     */
    public static UserNotFoundException tokenActivationInvalide(String token) {
        String message = "Le lien d'activation est invalide ou a expiré. " +
                        "Demandez un nouveau lien d'activation.";
        UserNotFoundException exception = new UserNotFoundException(message, token, 
                                         TypeIdentifiant.TOKEN_ACTIVATION, RaisonNonExistence.EXPIRATION_TOKEN,
                                         ActionSuggereRecherche.CONTACTER_SUPPORT);
        exception.addCritereRecherche("tokenActivation=" + token.substring(0, Math.min(10, token.length())) + "...");
        return exception;
    }
    
    /**
     * Exception pour identifiant OAuth non trouvé
     */
    public static UserNotFoundException identifiantOAuthNonTrouve(String identifiantExterne, 
                                                                String fournisseur) {
        String message = "Aucun compte n'est lié à ce profil " + fournisseur + 
                        ". Créez un compte ou liez votre profil " + fournisseur + " à un compte existant.";
        UserNotFoundException exception = new UserNotFoundException(message, identifiantExterne, 
                                         TypeIdentifiant.IDENTIFIANT_EXTERNE, RaisonNonExistence.JAMAIS_EXISTE,
                                         ActionSuggereRecherche.CREER_COMPTE);
        exception.addCritereRecherche("fournisseurOAuth=" + fournisseur);
        exception.addCritereRecherche("identifiantExterne=" + identifiantExterne);
        return exception;
    }
    
    /**
     * Exception pour recherche avec critères multiples
     */
    public static UserNotFoundException rechercheCriteresMultiples(List<String> criteres) {
        String message = "Aucun utilisateur ne correspond aux critères de recherche spécifiés.";
        UserNotFoundException exception = new UserNotFoundException(message, null, null, 
                                         RaisonNonExistence.JAMAIS_EXISTE, ActionSuggereRecherche.VERIFIER_ORTHOGRAPHE);
        
        for (String critere : criteres) {
            exception.addCritereRecherche(critere);
        }
        return exception;
    }
    
    /**
     * Exception pour accès restreint
     */
    public static UserNotFoundException accesRestreint(String identifiant, String raisonRestriction) {
        String message = "L'accès à cet utilisateur est restreint. Raison : " + raisonRestriction;
        UserNotFoundException exception = new UserNotFoundException(message, identifiant, 
                                         TypeIdentifiant.EMAIL, RaisonNonExistence.ACCES_RESTREINT,
                                         ActionSuggereRecherche.CONTACTER_SUPPORT);
        exception.setInformationsDebogage("Restriction: " + raisonRestriction);
        return exception;
    }
    /**
     * Ajoute un critère de recherche
     */
    public void addCritereRecherche(String critere) {
        if (this.criteresRecherche == null) {
            this.criteresRecherche = new ArrayList<>();
        }
        if (!this.criteresRecherche.contains(critere)) {
            this.criteresRecherche.add(critere);
        }
    }
    
    /**
     * Ajoute une suggestion similaire
     */
    public void addSuggestionSimilaire(String suggestion) {
        if (this.suggestionsSimilaires == null) {
            this.suggestionsSimilaires = new ArrayList<>();
        }
        if (!this.suggestionsSimilaires.contains(suggestion)) {
            this.suggestionsSimilaires.add(suggestion);
        }
    }
    
    /**
     * Vérifie si l'exception concerne un type d'identifiant spécifique
     */
    public boolean isTypeIdentifiant(TypeIdentifiant type) {
        return this.typeIdentifiantRecherche == type;
    }
    
    /**
     * Génère un message d'aide basé sur le type d'identifiant
     */
    public String genererMessageAide() {
        if (typeIdentifiantRecherche == null) {
            return "Vérifiez les informations saisies et réessayez.";
        }
        
        switch (typeIdentifiantRecherche) {
            case EMAIL:
                return "Vérifiez l'orthographe de votre adresse email ou créez un nouveau compte.";
            case NOM_UTILISATEUR:
                return "Vérifiez l'orthographe de votre nom d'utilisateur.";
            case TELEPHONE:
                return "Vérifiez le format de votre numéro de téléphone.";
            case TOKEN_RESET:
                return "Demandez un nouveau lien de réinitialisation de mot de passe.";
            case TOKEN_ACTIVATION:
                return "Demandez un nouveau lien d'activation de compte.";
            case IDENTIFIANT_EXTERNE:
                return "Créez un compte ou liez votre profil à un compte existant.";
            default:
                return "Contactez le support pour obtenir de l'aide.";
        }
    }
    
    // Getters et Setters
    public String getIdentifiantRecherche() {
        return identifiantRecherche;
    }
    
    public void setIdentifiantRecherche(String identifiantRecherche) {
        this.identifiantRecherche = identifiantRecherche;
    }
    
    public TypeIdentifiant getTypeIdentifiantRecherche() {
        return typeIdentifiantRecherche;
    }
    
    public void setTypeIdentifiantRecherche(TypeIdentifiant typeIdentifiantRecherche) {
        this.typeIdentifiantRecherche = typeIdentifiantRecherche;
    }
    
    public List<String> getCriteresRecherche() {
        return criteresRecherche;
    }
    
    public void setCriteresRecherche(List<String> criteresRecherche) {
        this.criteresRecherche = criteresRecherche;
    }
    
    public List<String> getSuggestionsSimilaires() {
        return suggestionsSimilaires;
    }
    
    public void setSuggestionsSimilaires(List<String> suggestionsSimilaires) {
        this.suggestionsSimilaires = suggestionsSimilaires;
    }
    
    public boolean isUtilisateurSupprime() {
        return utilisateurSupprime;
    }
    
    public void setUtilisateurSupprime(boolean utilisateurSupprime) {
        this.utilisateurSupprime = utilisateurSupprime;
    }
    
    public Timestamp getDateSuppression() {
        return dateSuppression;
    }
    
    public void setDateSuppression(Timestamp dateSuppression) {
        this.dateSuppression = dateSuppression;
    }
    
    public RaisonNonExistence getRaisonNonExistence() {
        return raisonNonExistence;
    }
    
    public void setRaisonNonExistence(RaisonNonExistence raisonNonExistence) {
        this.raisonNonExistence = raisonNonExistence;
    }
    
    public ActionSuggereRecherche getActionSuggeree() {
        return actionSuggeree;
    }
    
    public void setActionSuggeree(ActionSuggereRecherche actionSuggeree) {
        this.actionSuggeree = actionSuggeree;
    }
    
    public String getUrlInscription() {
        return urlInscription;
    }
    
    public void setUrlInscription(String urlInscription) {
        this.urlInscription = urlInscription;
    }
    
    public String getUrlRecuperationCompte() {
        return urlRecuperationCompte;
    }
    
    public void setUrlRecuperationCompte(String urlRecuperationCompte) {
        this.urlRecuperationCompte = urlRecuperationCompte;
    }
    
    public String getDomaineEmailSuggere() {
        return domaineEmailSuggere;
    }
    
    public void setDomaineEmailSuggere(String domaineEmailSuggere) {
        this.domaineEmailSuggere = domaineEmailSuggere;
    }
    
    public String getInformationsDebogage() {
        return informationsDebogage;
    }
    
    public void setInformationsDebogage(String informationsDebogage) {
        this.informationsDebogage = informationsDebogage;
    }
    
    @Override
    public String toString() {
        return "UserNotFoundException{" +
                "message='" + getMessage() + '\'' +
                ", codeErreur='" + getCodeErreur() + '\'' +
                ", identifiantRecherche='" + identifiantRecherche + '\'' +
                ", typeIdentifiantRecherche=" + typeIdentifiantRecherche +
                ", raisonNonExistence=" + raisonNonExistence +
                ", actionSuggeree=" + actionSuggeree +
                ", utilisateurSupprime=" + utilisateurSupprime +
                ", criteresRecherche=" + criteresRecherche +
                ", suggestionsSimilaires=" + suggestionsSimilaires +
                '}';
    }
}