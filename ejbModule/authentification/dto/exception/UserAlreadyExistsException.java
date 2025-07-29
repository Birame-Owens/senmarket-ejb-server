package authentification.dto.exception;

import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

/**
 * Exception spécifique pour les utilisateurs qui existent déjà
 * Cette exception est levée lors d'une tentative de création d'un compte avec un email déjà utilisé
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public class UserAlreadyExistsException extends AuthenticationException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Code d'erreur spécifique à cette exception
     */
    public static final String ERROR_CODE = "AUTH004";
    
    /**
     * Email qui existe déjà
     */
    private String emailExistant;
    
    /**
     * Date d'inscription de ce compte existant
     */
    private Timestamp dateInscriptionExistante;
    
    /**
     * Statut du compte existant (actif, inactif, suspendu)
     */
    private String statutCompteExistant;
    
    /**
     * Champs en conflit lors de la création
     */
    private List<String> champsEnConflit;
    
    /**
     * Identifiant unique du compte existant (masqué)
     */
    private String identifiantCompteExistant;
    
    /**
     * Dernière connexion du compte existant
     */
    private Timestamp derniereConnexionExistante;
    
    /**
     * Indique si le compte existant est vérifié
     */
    private boolean compteExistantVerifie = true;
    
    /**
     * Suggestion d'action pour l'utilisateur
     */
    private ActionSuggere actionSuggeree;
    
    /**
     * URL de récupération de mot de passe
     */
    private String urlRecuperationMotDePasse;
    
    /**
     * URL de connexion
     */
    private String urlConnexion;
    
    /**
     * Type de conflit détecté
     */
    private TypeConflit typeConflit;
    
    /**
     * Informations partielles du profil existant (pour reconnaissance)
     */
    private String informationsPartielles;
    
    /**
     * Énumération des types de conflit
     */
    public enum TypeConflit {
        EMAIL_DUPLIQUE("Adresse email déjà utilisée"),
        TELEPHONE_DUPLIQUE("Numéro de téléphone déjà utilisé"),
        NOM_UTILISATEUR_DUPLIQUE("Nom d'utilisateur déjà pris"),
        IDENTIFIANT_EXTERNE_DUPLIQUE("Identifiant externe déjà associé"),
        PROFIL_COMPLET_DUPLIQUE("Profil complet déjà existant");
        
        private final String description;
        
        TypeConflit(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    /**
     * Énumération des actions suggérées
     */
    public enum ActionSuggere {
        SE_CONNECTER("Se connecter avec ce compte"),
        RECUPERER_MOT_DE_PASSE("Récupérer le mot de passe"),
        UTILISER_AUTRE_EMAIL("Utiliser une autre adresse email"),
        CONTACTER_SUPPORT("Contacter le support client"),
        FUSIONNER_COMPTES("Demander la fusion des comptes"),
        ACTIVER_COMPTE("Activer le compte existant");
        
        private final String description;
        
        ActionSuggere(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    /**
     * Constructeur par défaut
     */
    public UserAlreadyExistsException() {
        super("Un compte existe déjà avec ces informations");
        this.setCodeErreur(ERROR_CODE);
        this.champsEnConflit = new ArrayList<>();
        this.typeConflit = TypeConflit.EMAIL_DUPLIQUE;
        this.actionSuggeree = ActionSuggere.SE_CONNECTER;
    }
    
    /**
     * Constructeur avec message personnalisé
     */
    public UserAlreadyExistsException(String message) {
        super(message);
        this.setCodeErreur(ERROR_CODE);
        this.champsEnConflit = new ArrayList<>();
    }
    
    /**
     * Constructeur avec message et cause
     */
    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
        this.setCodeErreur(ERROR_CODE);
        this.champsEnConflit = new ArrayList<>();
    }
    
    /**
     * Constructeur avec email existant
     */
    public UserAlreadyExistsException(String message, String emailExistant, 
                                    TypeConflit typeConflit) {
        super(message);
        this.setCodeErreur(ERROR_CODE);
        this.emailExistant = emailExistant;
        this.typeConflit = typeConflit;
        this.champsEnConflit = new ArrayList<>();
        this.champsEnConflit.add("email");
    }
    
    /**
     * Constructeur complet
     */
    public UserAlreadyExistsException(String message, String emailExistant,
                                    TypeConflit typeConflit, ActionSuggere actionSuggeree,
                                    Timestamp dateInscriptionExistante, String statutCompteExistant) {
        super(message, emailExistant, null, null);
        this.setCodeErreur(ERROR_CODE);
        this.emailExistant = emailExistant;
        this.typeConflit = typeConflit;
        this.actionSuggeree = actionSuggeree;
        this.dateInscriptionExistante = dateInscriptionExistante;
        this.statutCompteExistant = statutCompteExistant;
        this.champsEnConflit = new ArrayList<>();
    }
    
    /**
     * Méthodes statiques pour créer des exceptions typiques
     */
    
    /**
     * Exception pour email déjà utilisé (compte actif)
     */
    public static UserAlreadyExistsException emailDejaUtilise(String email, 
                                                            Timestamp dateInscription) {
        String message = "Un compte existe déjà avec l'adresse email '" + email + 
                        "'. Connectez-vous ou récupérez votre mot de passe.";
        UserAlreadyExistsException exception = new UserAlreadyExistsException(message, email, 
                                              TypeConflit.EMAIL_DUPLIQUE, ActionSuggere.SE_CONNECTER,
                                              dateInscription, "ACTIF");
        exception.setCompteExistantVerifie(true);
        exception.addChampEnConflit("email");
        return exception;
    }
    
    /**
     * Exception pour email existant mais compte inactif
     */
    public static UserAlreadyExistsException emailExistantCompteInactif(String email) {
        String message = "Un compte existe avec l'email '" + email + 
                        "' mais il n'est pas activé. Vérifiez votre boîte email.";
        UserAlreadyExistsException exception = new UserAlreadyExistsException(message, email, 
                                              TypeConflit.EMAIL_DUPLIQUE, ActionSuggere.ACTIVER_COMPTE,
                                              null, "INACTIF");
        exception.setCompteExistantVerifie(false);
        exception.addChampEnConflit("email");
        return exception;
    }
    
    /**
     * Exception pour compte suspendu
     */
    public static UserAlreadyExistsException emailExistantCompteSuspendu(String email, 
                                                                       String raisonSuspension) {
        String message = "Un compte existe avec l'email '" + email + 
                        "' mais il est suspendu. Raison : " + raisonSuspension + 
                        ". Contactez le support.";
        UserAlreadyExistsException exception = new UserAlreadyExistsException(message, email, 
                                              TypeConflit.EMAIL_DUPLIQUE, ActionSuggere.CONTACTER_SUPPORT,
                                              null, "SUSPENDU");
        exception.addChampEnConflit("email");
        return exception;
    }
    
    /**
     * Exception pour numéro de téléphone déjà utilisé
     */
    public static UserAlreadyExistsException telephoneDejaUtilise(String telephone, 
                                                                String emailAssocie) {
        String message = "Ce numéro de téléphone est déjà associé à un autre compte.";
        UserAlreadyExistsException exception = new UserAlreadyExistsException(message, emailAssocie, 
                                              TypeConflit.TELEPHONE_DUPLIQUE);
        exception.addChampEnConflit("telephone");
        exception.setActionSuggeree(ActionSuggere.UTILISER_AUTRE_EMAIL);
        return exception;
    }
    
    /**
     * Exception pour nom d'utilisateur déjà pris
     */
    public static UserAlreadyExistsException nomUtilisateurDejaUtilise(String nomUtilisateur) {
        String message = "Le nom d'utilisateur '" + nomUtilisateur + "' est déjà pris. " +
                        "Choisissez un autre nom d'utilisateur.";
        UserAlreadyExistsException exception = new UserAlreadyExistsException(message, null, 
                                              TypeConflit.NOM_UTILISATEUR_DUPLIQUE);
        exception.addChampEnConflit("nomUtilisateur");
        exception.setActionSuggeree(ActionSuggere.UTILISER_AUTRE_EMAIL);
        return exception;
    }
    
    /**
     * Exception pour identifiant OAuth déjà associé
     */
    public static UserAlreadyExistsException identifiantOAuthDejaAssocie(String email, 
                                                                       String fournisseurOAuth) {
        String message = "Ce compte " + fournisseurOAuth + " est déjà associé à l'adresse '" + 
                        email + "'. Connectez-vous avec " + fournisseurOAuth + ".";
        UserAlreadyExistsException exception = new UserAlreadyExistsException(message, email, 
                                              TypeConflit.IDENTIFIANT_EXTERNE_DUPLIQUE);
        exception.addChampEnConflit("identifiantExterne");
        exception.setActionSuggeree(ActionSuggere.SE_CONNECTER);
        return exception;
    }
    
    /**
     * Exception pour profil complet identique
     */
    public static UserAlreadyExistsException profilCompletIdentique(String email, 
                                                                  String prenomNom) {
        String message = "Un profil identique existe déjà ('" + prenomNom + 
                        "' - '" + email + "'). Souhaitez-vous fusionner les comptes ?";
        UserAlreadyExistsException exception = new UserAlreadyExistsException(message, email, 
                                              TypeConflit.PROFIL_COMPLET_DUPLIQUE);
        exception.addChampEnConflit("email");
        exception.addChampEnConflit("prenom");
        exception.addChampEnConflit("nom");
        exception.setActionSuggeree(ActionSuggere.FUSIONNER_COMPTES);
        exception.setInformationsPartielles(prenomNom);
        return exception;
    }
    
    /**
     * Ajoute un champ en conflit
     */
    public void addChampEnConflit(String champ) {
        if (this.champsEnConflit == null) {
            this.champsEnConflit = new ArrayList<>();
        }
        if (!this.champsEnConflit.contains(champ)) {
            this.champsEnConflit.add(champ);
        }
    }
    
    /**
     * Vérifie si un champ spécifique est en conflit
     */
    public boolean hasChampEnConflit(String champ) {
        return this.champsEnConflit != null && this.champsEnConflit.contains(champ);
    }
    
    // Getters et Setters
    public String getEmailExistant() {
        return emailExistant;
    }
    
    public void setEmailExistant(String emailExistant) {
        this.emailExistant = emailExistant;
    }
    
    public Timestamp getDateInscriptionExistante() {
        return dateInscriptionExistante;
    }
    
    public void setDateInscriptionExistante(Timestamp dateInscriptionExistante) {
        this.dateInscriptionExistante = dateInscriptionExistante;
    }
    
    public String getStatutCompteExistant() {
        return statutCompteExistant;
    }
    
    public void setStatutCompteExistant(String statutCompteExistant) {
        this.statutCompteExistant = statutCompteExistant;
    }
    
    public List<String> getChampsEnConflit() {
        return champsEnConflit;
    }
    
    public void setChampsEnConflit(List<String> champsEnConflit) {
        this.champsEnConflit = champsEnConflit;
    }
    
    public String getIdentifiantCompteExistant() {
        return identifiantCompteExistant;
    }
    
    public void setIdentifiantCompteExistant(String identifiantCompteExistant) {
        this.identifiantCompteExistant = identifiantCompteExistant;
    }
    
    public Timestamp getDerniereConnexionExistante() {
        return derniereConnexionExistante;
    }
    
    public void setDerniereConnexionExistante(Timestamp derniereConnexionExistante) {
        this.derniereConnexionExistante = derniereConnexionExistante;
    }
    
    public boolean isCompteExistantVerifie() {
        return compteExistantVerifie;
    }
    
    public void setCompteExistantVerifie(boolean compteExistantVerifie) {
        this.compteExistantVerifie = compteExistantVerifie;
    }
    
    public ActionSuggere getActionSuggeree() {
        return actionSuggeree;
    }
    
    public void setActionSuggeree(ActionSuggere actionSuggeree) {
        this.actionSuggeree = actionSuggeree;
    }
    
    public String getUrlRecuperationMotDePasse() {
        return urlRecuperationMotDePasse;
    }
    
    public void setUrlRecuperationMotDePasse(String urlRecuperationMotDePasse) {
        this.urlRecuperationMotDePasse = urlRecuperationMotDePasse;
    }
    
    public String getUrlConnexion() {
        return urlConnexion;
    }
    
    public void setUrlConnexion(String urlConnexion) {
        this.urlConnexion = urlConnexion;
    }
    
    public TypeConflit getTypeConflit() {
        return typeConflit;
    }
    
    public void setTypeConflit(TypeConflit typeConflit) {
        this.typeConflit = typeConflit;
    }
    
    public String getInformationsPartielles() {
        return informationsPartielles;
    }
    
    public void setInformationsPartielles(String informationsPartielles) {
        this.informationsPartielles = informationsPartielles;
    }
    
    @Override
    public String toString() {
        return "UserAlreadyExistsException{" +
                "message='" + getMessage() + '\'' +
                ", codeErreur='" + getCodeErreur() + '\'' +
                ", emailExistant='" + emailExistant + '\'' +
                ", typeConflit=" + typeConflit +
                ", actionSuggeree=" + actionSuggeree +
                ", statutCompteExistant='" + statutCompteExistant + '\'' +
                ", champsEnConflit=" + champsEnConflit +
                ", compteExistantVerifie=" + compteExistantVerifie +
                '}';
    }
}