package authentification.base.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DTO spécialisé pour les réponses d'erreur
 * Étend BaseResponse avec des informations spécifiques aux erreurs
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public class ErrorResponse extends BaseResponse {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Code d'erreur spécifique (ex: AUTH001, VALID002)
     */
    private String codeErreur;
    
    /**
     * Type d'erreur (VALIDATION, AUTHENTICATION, AUTHORIZATION, SYSTEM, etc.)
     */
    private String typeErreur;
    
    /**
     * Champ qui a causé l'erreur (pour les erreurs de validation)
     */
    private String champErreur;
    
    /**
     * Valeur qui a causé l'erreur
     */
    private String valeurErreur;
    
    /**
     * Stack trace de l'exception (en mode développement uniquement)
     */
    private String stackTrace;
    
    /**
     * Cause racine de l'erreur
     */
    private String causeRacine;
    
    /**
     * Suggestions pour résoudre l'erreur
     */
    private List<String> suggestions;
    
    /**
     * Détails techniques additionnels
     */
    private Map<String, Object> detailsTechniques;
    
    /**
     * Indique si l'erreur peut être réessayée
     */
    private boolean peutReessayer = false;
    
    /**
     * Délai recommandé avant de réessayer (en secondes)
     */
    private Integer delaiReessai;
    
    /**
     * URL de documentation pour cette erreur
     */
    private String urlDocumentation;
    
    /**
     * Niveau de gravité de l'erreur
     */
    private NiveauGravite niveauGravite = NiveauGravite.MOYEN;
    
    /**
     * Énumération des niveaux de gravité
     */
    public enum NiveauGravite {
        FAIBLE, MOYEN, ELEVE, CRITIQUE
    }
    
    /**
     * Énumération des types d'erreur courants
     */
    public enum TypeErreur {
        VALIDATION("VALIDATION"),
        AUTHENTICATION("AUTHENTICATION"), 
        AUTHORIZATION("AUTHORIZATION"),
        NOT_FOUND("NOT_FOUND"),
        CONFLICT("CONFLICT"),
        RATE_LIMIT("RATE_LIMIT"),
        SYSTEM("SYSTEM"),
        NETWORK("NETWORK"),
        TIMEOUT("TIMEOUT"),
        BUSINESS("BUSINESS");
        
        private final String code;
        
        TypeErreur(String code) {
            this.code = code;
        }
        
        public String getCode() {
            return code;
        }
    }
    
    // Constructeur par défaut
    public ErrorResponse() {
        super(false, null);
        this.suggestions = new ArrayList<>();
        this.detailsTechniques = new HashMap<>();
        this.setStatusCode("ERROR");
    }
    
    /**
     * Constructeur avec message d'erreur
     */
    public ErrorResponse(String message) {
        this();
        this.setMessage(message);
    }
    
    /**
     * Constructeur avec code et message d'erreur
     */
    public ErrorResponse(String codeErreur, String message) {
        this(message);
        this.codeErreur = codeErreur;
    }
    
    /**
     * Constructeur complet
     */
    public ErrorResponse(String codeErreur, String message, TypeErreur typeErreur) {
        this(codeErreur, message);
        this.typeErreur = typeErreur.getCode();
    }
    
    /**
     * Méthodes statiques pour créer des erreurs courantes
     */
    
    /**
     * Erreur de validation
     */
    public static ErrorResponse validationError(String champ, String valeur, String message) {
        ErrorResponse error = new ErrorResponse("VALID001", message, TypeErreur.VALIDATION);
        error.setChampErreur(champ);
        error.setValeurErreur(valeur);
        error.setNiveauGravite(NiveauGravite.MOYEN);
        return error;
    }
    
    /**
     * Erreur d'authentification
     */
    public static ErrorResponse authenticationError(String message) {
        ErrorResponse error = new ErrorResponse("AUTH001", message, TypeErreur.AUTHENTICATION);
        error.setNiveauGravite(NiveauGravite.ELEVE);
        error.addSuggestion("Vérifiez vos identifiants de connexion");
        error.addSuggestion("Assurez-vous que votre compte est actif");
        return error;
    }
    
    /**
     * Erreur d'autorisation
     */
    public static ErrorResponse authorizationError(String message) {
        ErrorResponse error = new ErrorResponse("AUTH002", message, TypeErreur.AUTHORIZATION);
        error.setNiveauGravite(NiveauGravite.ELEVE);
        error.addSuggestion("Contactez un administrateur pour obtenir les permissions nécessaires");
        return error;
    }
    
    /**
     * Erreur de ressource non trouvée
     */
    public static ErrorResponse notFoundError(String ressource, String identifiant) {
        String message = String.format("La ressource '%s' avec l'identifiant '%s' n'existe pas", ressource, identifiant);
        ErrorResponse error = new ErrorResponse("NOT_FOUND", message, TypeErreur.NOT_FOUND);
        error.setNiveauGravite(NiveauGravite.MOYEN);
        error.addDetailTechnique("ressource", ressource);
        error.addDetailTechnique("identifiant", identifiant);
        return error;
    }
    
    /**
     * Erreur de conflit (ex: email déjà utilisé)
     */
    public static ErrorResponse conflictError(String message) {
        ErrorResponse error = new ErrorResponse("CONFLICT", message, TypeErreur.CONFLICT);
        error.setNiveauGravite(NiveauGravite.MOYEN);
        return error;
    }
    
    /**
     * Erreur de limite de taux dépassée
     */
    public static ErrorResponse rateLimitError(int delaiReessai) {
        ErrorResponse error = new ErrorResponse("RATE_LIMIT", "Limite de requêtes dépassée", TypeErreur.RATE_LIMIT);
        error.setNiveauGravite(NiveauGravite.MOYEN);
        error.setPeutReessayer(true);
        error.setDelaiReessai(delaiReessai);
        error.addSuggestion("Attendez " + delaiReessai + " secondes avant de réessayer");
        return error;
    }
    
    /**
     * Erreur système interne
     */
    public static ErrorResponse systemError(String message) {
        ErrorResponse error = new ErrorResponse("SYS001", message, TypeErreur.SYSTEM);
        error.setNiveauGravite(NiveauGravite.CRITIQUE);
        error.addSuggestion("Contactez le support technique si le problème persiste");
        return error;
    }
    
    /**
     * Erreur de timeout
     */
    public static ErrorResponse timeoutError(String operation) {
        String message = String.format("L'opération '%s' a dépassé le délai autorisé", operation);
        ErrorResponse error = new ErrorResponse("TIMEOUT", message, TypeErreur.TIMEOUT);
        error.setNiveauGravite(NiveauGravite.ELEVE);
        error.setPeutReessayer(true);
        error.setDelaiReessai(30);
        error.addSuggestion("Réessayez l'opération dans quelques instants");
        return error;
    }
    
    /**
     * Méthodes utilitaires
     */
    public void addSuggestion(String suggestion) {
        if (this.suggestions == null) {
            this.suggestions = new ArrayList<>();
        }
        this.suggestions.add(suggestion);
    }
    
    public void addDetailTechnique(String cle, Object valeur) {
        if (this.detailsTechniques == null) {
            this.detailsTechniques = new HashMap<>();
        }
        this.detailsTechniques.put(cle, valeur);
    }
    
    /**
     * Convertit l'erreur en format pour logging
     */
    public String toLogFormat() {
        StringBuilder log = new StringBuilder();
        log.append("[").append(codeErreur).append("] ");
        log.append(getMessage());
        
        if (champErreur != null) {
            log.append(" | Champ: ").append(champErreur);
        }
        
        if (valeurErreur != null) {
            log.append(" | Valeur: ").append(valeurErreur);
        }
        
        if (causeRacine != null) {
            log.append(" | Cause: ").append(causeRacine);
        }
        
        return log.toString();
    }
    
    /**
     * Vérifie si l'erreur est critique
     */
    public boolean isCritique() {
        return NiveauGravite.CRITIQUE.equals(this.niveauGravite);
    }
    
    /**
     * Vérifie si l'erreur est liée à la sécurité
     */
    public boolean isSecurityRelated() {
        return TypeErreur.AUTHENTICATION.getCode().equals(this.typeErreur) ||
               TypeErreur.AUTHORIZATION.getCode().equals(this.typeErreur);
    }
    
    // Getters et Setters
    public String getCodeErreur() {
        return codeErreur;
    }
    
    public void setCodeErreur(String codeErreur) {
        this.codeErreur = codeErreur;
    }
    
    public String getTypeErreur() {
        return typeErreur;
    }
    
    public void setTypeErreur(String typeErreur) {
        this.typeErreur = typeErreur;
    }
    
    public String getChampErreur() {
        return champErreur;
    }
    
    public void setChampErreur(String champErreur) {
        this.champErreur = champErreur;
    }
    
    public String getValeurErreur() {
        return valeurErreur;
    }
    
    public void setValeurErreur(String valeurErreur) {
        this.valeurErreur = valeurErreur;
    }
    
    public String getStackTrace() {
        return stackTrace;
    }
    
    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
    
    public String getCauseRacine() {
        return causeRacine;
    }
    
    public void setCauseRacine(String causeRacine) {
        this.causeRacine = causeRacine;
    }
    
    public List<String> getSuggestions() {
        return suggestions;
    }
    
    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }
    
    public Map<String, Object> getDetailsTechniques() {
        return detailsTechniques;
    }
    
    public void setDetailsTechniques(Map<String, Object> detailsTechniques) {
        this.detailsTechniques = detailsTechniques;
    }
    
    public boolean isPeutReessayer() {
        return peutReessayer;
    }
    
    public void setPeutReessayer(boolean peutReessayer) {
        this.peutReessayer = peutReessayer;
    }
    
    public Integer getDelaiReessai() {
        return delaiReessai;
    }
    
    public void setDelaiReessai(Integer delaiReessai) {
        this.delaiReessai = delaiReessai;
    }
    
    public String getUrlDocumentation() {
        return urlDocumentation;
    }
    
    public void setUrlDocumentation(String urlDocumentation) {
        this.urlDocumentation = urlDocumentation;
    }
    
    public NiveauGravite getNiveauGravite() {
        return niveauGravite;
    }
    
    public void setNiveauGravite(NiveauGravite niveauGravite) {
        this.niveauGravite = niveauGravite;
    }
    
    @Override
    public String toString() {
        return "ErrorResponse{" +
                "codeErreur='" + codeErreur + '\'' +
                ", typeErreur='" + typeErreur + '\'' +
                ", message='" + getMessage() + '\'' +
                ", champErreur='" + champErreur + '\'' +
                ", niveauGravite=" + niveauGravite +
                ", peutReessayer=" + peutReessayer +
                ", suggestions=" + (suggestions != null ? suggestions.size() : 0) +
                '}';
    }
}