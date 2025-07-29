package authentification.base.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;

/**
 * Exception spécialisée pour les erreurs de validation dans l'application SenMarket
 * Cette exception gère les violations de contraintes, erreurs de format,
 * et autres problèmes de validation des données
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public class ValidationException extends SenMarketException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Préfixe pour les codes d'erreur de validation
     */
    public static final String ERROR_CODE_PREFIX = "VAL";
    
    /**
     * Type d'erreur par défaut pour les exceptions de validation
     */
    public static final String DEFAULT_ERROR_TYPE = "VALIDATION";
    
    /**
     * Liste des erreurs de validation
     */
    private List<ErreurValidation> erreursValidation;
    
    /**
     * Objet qui a échoué à la validation
     */
    private String objetValide;
    
    /**
     * Classe de l'objet validé
     */
    private String classeObjet;
    
    /**
     * Contexte de validation (formulaire, API, etc.)
     */
    private String contexteValidation;
    
    /**
     * Règles de validation appliquées
     */
    private List<String> reglesAppliquees;
    
    /**
     * Suggestions de correction
     */
    private List<String> suggestionsCorrection;
    
    /**
     * Indique si la validation peut être ignorée avec un avertissement
     */
    private boolean validationIgnorable = false;
    
    /**
     * Niveau de sévérité de la validation
     */
    private SeveriteValidation severite = SeveriteValidation.ERREUR;
    
    /**
     * Groupe de validation concerné
     */
    private String groupeValidation;
    
    /**
     * Énumération des types d'erreur de validation
     */
    public enum TypeErreurValidation {
        CHAMP_OBLIGATOIRE("Champ obligatoire manquant"),
        FORMAT_INVALIDE("Format de données invalide"),
        TAILLE_INVALIDE("Taille de données invalide"),
        VALEUR_INVALIDE("Valeur non autorisée"),
        CONTRAINTE_METIER("Contrainte métier non respectée"),
        COHERENCE_DONNEES("Incohérence entre les données"),
        DUPLICATION("Données dupliquées"),
        REFERENCE_INVALIDE("Référence invalide"),
        PLAGE_INVALIDE("Valeur hors plage autorisée"),
        PATTERN_NON_RESPECTE("Pattern non respecté");
        
        private final String description;
        
        TypeErreurValidation(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    /**
     * Énumération des niveaux de sévérité
     */
    public enum SeveriteValidation {
        INFO("Information"),
        AVERTISSEMENT("Avertissement"),
        ERREUR("Erreur"),
        CRITIQUE("Critique");
        
        private final String description;
        
        SeveriteValidation(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    /**
     * Classe pour représenter une erreur de validation individuelle
     */
    public static class ErreurValidation {
        private String champ;
        private Object valeurRejetee;
        private String messageErreur;
        private TypeErreurValidation typeErreur;
        private String codeErreur;
        private String valeurAttendue;
        private Map<String, Object> contexte;
        
        public ErreurValidation(String champ, Object valeurRejetee, String messageErreur, 
                              TypeErreurValidation typeErreur) {
            this.champ = champ;
            this.valeurRejetee = valeurRejetee;
            this.messageErreur = messageErreur;
            this.typeErreur = typeErreur;
            this.contexte = new HashMap<>();
        }
        
        // Getters et Setters
        public String getChamp() {
            return champ;
        }
        
        public void setChamp(String champ) {
            this.champ = champ;
        }
        
        public Object getValeurRejetee() {
            return valeurRejetee;
        }
        
        public void setValeurRejetee(Object valeurRejetee) {
            this.valeurRejetee = valeurRejetee;
        }
        
        public String getMessageErreur() {
            return messageErreur;
        }
        
        public void setMessageErreur(String messageErreur) {
            this.messageErreur = messageErreur;
        }
        
        public TypeErreurValidation getTypeErreur() {
            return typeErreur;
        }
        
        public void setTypeErreur(TypeErreurValidation typeErreur) {
            this.typeErreur = typeErreur;
        }
        
        public String getCodeErreur() {
            return codeErreur;
        }
        
        public void setCodeErreur(String codeErreur) {
            this.codeErreur = codeErreur;
        }
        
        public String getValeurAttendue() {
            return valeurAttendue;
        }
        
        public void setValeurAttendue(String valeurAttendue) {
            this.valeurAttendue = valeurAttendue;
        }
        
        public Map<String, Object> getContexte() {
            return contexte;
        }
        
        public void setContexte(Map<String, Object> contexte) {
            this.contexte = contexte;
        }
        
        public void addContexte(String cle, Object valeur) {
            this.contexte.put(cle, valeur);
        }
        
        @Override
        public String toString() {
            return "ErreurValidation{" +
                    "champ='" + champ + '\'' +
                    ", valeurRejetee=" + valeurRejetee +
                    ", messageErreur='" + messageErreur + '\'' +
                    ", typeErreur=" + typeErreur +
                    '}';
        }
    }
    
    /**
     * Constructeur par défaut
     */
    public ValidationException() {
        super();
        this.setTypeErreur(DEFAULT_ERROR_TYPE);
        this.setNiveauGravite(NiveauGravite.MOYEN);
        this.erreursValidation = new ArrayList<>();
        this.reglesAppliquees = new ArrayList<>();
        this.suggestionsCorrection = new ArrayList<>();
    }
    
    /**
     * Constructeur avec message
     */
    public ValidationException(String message) {
        super(message);
        this.setTypeErreur(DEFAULT_ERROR_TYPE);
        this.setNiveauGravite(NiveauGravite.MOYEN);
        this.erreursValidation = new ArrayList<>();
        this.reglesAppliquees = new ArrayList<>();
        this.suggestionsCorrection = new ArrayList<>();
    }
    
    /**
     * Constructeur avec message et cause
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
        this.setTypeErreur(DEFAULT_ERROR_TYPE);
        this.setNiveauGravite(NiveauGravite.MOYEN);
        this.erreursValidation = new ArrayList<>();
        this.reglesAppliquees = new ArrayList<>();
        this.suggestionsCorrection = new ArrayList<>();
    }
    
    /**
     * Constructeur avec erreur de validation unique
     */
    public ValidationException(String message, ErreurValidation erreurValidation) {
        this(message);
        this.erreursValidation.add(erreurValidation);
        this.setCodeErreur(ERROR_CODE_PREFIX + "001");
    }
    
    /**
     * Constructeur avec liste d'erreurs de validation
     */
    public ValidationException(String message, List<ErreurValidation> erreursValidation) {
        this(message);
        this.erreursValidation.addAll(erreursValidation);
        this.setCodeErreur(ERROR_CODE_PREFIX + "001");
    }
    
    /**
     * Constructeur à partir de violations Bean Validation
     */
    public ValidationException(Set<ConstraintViolation<?>> violations) {
        this();
        this.setCodeErreur(ERROR_CODE_PREFIX + "001");
        
        StringBuilder messageBuilder = new StringBuilder("Erreurs de validation: ");
        
        for (ConstraintViolation<?> violation : violations) {
            ErreurValidation erreur = new ErreurValidation(
                violation.getPropertyPath().toString(),
                violation.getInvalidValue(),
                violation.getMessage(),
                determinerTypeErreur(violation)
            );
            
            erreur.setCodeErreur(violation.getConstraintDescriptor().getAnnotation()
                               .annotationType().getSimpleName());
            
            this.erreursValidation.add(erreur);
            messageBuilder.append(erreur.getChamp()).append(" - ")
                         .append(erreur.getMessageErreur()).append("; ");
        }
        
        this.setMessageUtilisateur(messageBuilder.toString());
    }
    
    /**
     * Méthodes statiques pour créer des exceptions de validation courantes
     */
    
    /**
     * Exception pour champ obligatoire manquant
     */
    public static ValidationException champObligatoire(String champ) {
        String message = "Le champ '" + champ + "' est obligatoire";
        ErreurValidation erreur = new ErreurValidation(champ, null, message, 
                                                     TypeErreurValidation.CHAMP_OBLIGATOIRE);
        erreur.setCodeErreur("REQUIRED");
        
        ValidationException exception = new ValidationException(message, erreur);
        exception.addSuggestionCorrection("Veuillez renseigner le champ '" + champ + "'");
        return exception;
    }
    
    /**
     * Exception pour format invalide
     */
    public static ValidationException formatInvalide(String champ, Object valeur, 
                                                   String formatAttendu) {
        String message = "Le format du champ '" + champ + "' est invalide. " +
                        "Format attendu: " + formatAttendu;
        ErreurValidation erreur = new ErreurValidation(champ, valeur, message, 
                                                     TypeErreurValidation.FORMAT_INVALIDE);
        erreur.setCodeErreur("INVALID_FORMAT");
        erreur.setValeurAttendue(formatAttendu);
        
        ValidationException exception = new ValidationException(message, erreur);
        exception.addSuggestionCorrection("Utilisez le format: " + formatAttendu);
        return exception;
    }
    
    /**
     * Exception pour taille invalide
     */
    public static ValidationException tailleInvalide(String champ, Object valeur, 
                                                   int tailleMin, int tailleMax) {
        String message = "La taille du champ '" + champ + "' doit être entre " + 
                        tailleMin + " et " + tailleMax + " caractères";
        ErreurValidation erreur = new ErreurValidation(champ, valeur, message, 
                                                     TypeErreurValidation.TAILLE_INVALIDE);
        erreur.setCodeErreur("INVALID_SIZE");
        erreur.setValeurAttendue(tailleMin + "-" + tailleMax + " caractères");
        
        ValidationException exception = new ValidationException(message, erreur);
        exception.addSuggestionCorrection("Ajustez la longueur du texte");
        return exception;
    }
    
    /**
     * Exception pour email invalide
     */
    public static ValidationException emailInvalide(String email) {
        String message = "L'adresse email '" + email + "' n'est pas valide";
        ErreurValidation erreur = new ErreurValidation("email", email, message, 
                                                     TypeErreurValidation.FORMAT_INVALIDE);
        erreur.setCodeErreur("INVALID_EMAIL");
        erreur.setValeurAttendue("format: nom@domaine.com");
        
        ValidationException exception = new ValidationException(message, erreur);
        exception.addSuggestionCorrection("Vérifiez que l'email contient '@' et un domaine valide");
        return exception;
    }
    
    /**
     * Exception pour mot de passe faible
     */
    public static ValidationException motDePasseFaible(String motDePasse, List<String> exigences) {
        String message = "Le mot de passe ne respecte pas les exigences de sécurité";
        ErreurValidation erreur = new ErreurValidation("motDePasse", "***", message, 
                                                     TypeErreurValidation.CONTRAINTE_METIER);
        erreur.setCodeErreur("WEAK_PASSWORD");
        erreur.setValeurAttendue(String.join(", ", exigences));
        
        ValidationException exception = new ValidationException(message, erreur);
        for (String exigence : exigences) {
            exception.addSuggestionCorrection(exigence);
        }
        return exception;
    }
    
    /**
     * Exception pour valeur hors plage
     */
    public static ValidationException valeurHorsPlage(String champ, Object valeur, 
                                                    Object min, Object max) {
        String message = "La valeur du champ '" + champ + "' doit être entre " + min + " et " + max;
        ErreurValidation erreur = new ErreurValidation(champ, valeur, message, 
                                                     TypeErreurValidation.PLAGE_INVALIDE);
        erreur.setCodeErreur("OUT_OF_RANGE");
        erreur.setValeurAttendue(min + " - " + max);
        
        ValidationException exception = new ValidationException(message, erreur);
        exception.addSuggestionCorrection("Utilisez une valeur entre " + min + " et " + max);
        return exception;
    }
    
    /**
     * Exception pour données dupliquées
     */
    public static ValidationException donneesDupliquees(String champ, Object valeur) {
        String message = "La valeur '" + valeur + "' du champ '" + champ + "' existe déjà";
        ErreurValidation erreur = new ErreurValidation(champ, valeur, message, 
                                                     TypeErreurValidation.DUPLICATION);
        erreur.setCodeErreur("DUPLICATE_VALUE");
        
        ValidationException exception = new ValidationException(message, erreur);
        exception.addSuggestionCorrection("Utilisez une valeur unique pour ce champ");
        return exception;
    }
    
    /**
     * Exception pour incohérence de données
     */
    public static ValidationException incoherenceDonnees(String champ1, Object valeur1, 
                                                       String champ2, Object valeur2, 
                                                       String regleCoherence) {
        String message = "Incohérence entre les champs '" + champ1 + "' et '" + champ2 + 
                        "': " + regleCoherence;
        
        List<ErreurValidation> erreurs = new ArrayList<>();
        
        ErreurValidation erreur1 = new ErreurValidation(champ1, valeur1, message, 
                                                       TypeErreurValidation.COHERENCE_DONNEES);
        erreur1.setCodeErreur("DATA_INCONSISTENCY");
        erreur1.addContexte("champLie", champ2);
        erreur1.addContexte("valeurLiee", valeur2);
        erreurs.add(erreur1);
        
        ErreurValidation erreur2 = new ErreurValidation(champ2, valeur2, message, 
                                                       TypeErreurValidation.COHERENCE_DONNEES);
        erreur2.setCodeErreur("DATA_INCONSISTENCY");
        erreur2.addContexte("champLie", champ1);
        erreur2.addContexte("valeurLiee", valeur1);
        erreurs.add(erreur2);
        
        ValidationException exception = new ValidationException(message, erreurs);
        exception.addSuggestionCorrection("Vérifiez la cohérence: " + regleCoherence);
        return exception;
    }
    
    /**
     * Exception pour référence invalide
     */
    public static ValidationException referenceInvalide(String champ, Object referenceId, 
                                                      String typeReference) {
        String message = "Référence invalide pour le champ '" + champ + 
                        "': " + typeReference + " avec l'ID '" + referenceId + "' n'existe pas";
        ErreurValidation erreur = new ErreurValidation(champ, referenceId, message, 
                                                     TypeErreurValidation.REFERENCE_INVALIDE);
        erreur.setCodeErreur("INVALID_REFERENCE");
        erreur.addContexte("typeReference", typeReference);
        
        ValidationException exception = new ValidationException(message, erreur);
        exception.addSuggestionCorrection("Sélectionnez une référence valide");
        return exception;
    }
    
    /**
     * Méthodes utilitaires
     */
    
    /**
     * Ajoute une erreur de validation
     */
    public void addErreurValidation(ErreurValidation erreur) {
        if (this.erreursValidation == null) {
            this.erreursValidation = new ArrayList<>();
        }
        this.erreursValidation.add(erreur);
    }
    
    /**
     * Ajoute une erreur de validation simple
     */
    public void addErreurValidation(String champ, Object valeur, String message, 
                                  TypeErreurValidation type) {
        ErreurValidation erreur = new ErreurValidation(champ, valeur, message, type);
        addErreurValidation(erreur);
    }
    
    /**
     * Ajoute une suggestion de correction
     */
    public void addSuggestionCorrection(String suggestion) {
        if (this.suggestionsCorrection == null) {
            this.suggestionsCorrection = new ArrayList<>();
        }
        this.suggestionsCorrection.add(suggestion);
    }
    
    /**
     * Détermine le type d'erreur à partir d'une violation Bean Validation
     */
    private TypeErreurValidation determinerTypeErreur(ConstraintViolation<?> violation) {
        String annotationType = violation.getConstraintDescriptor().getAnnotation()
                                       .annotationType().getSimpleName();
        
        switch (annotationType) {
            case "NotNull":
            case "NotEmpty":
            case "NotBlank":
                return TypeErreurValidation.CHAMP_OBLIGATOIRE;
            case "Size":
            case "Length":
                return TypeErreurValidation.TAILLE_INVALIDE;
            case "Email":
                return TypeErreurValidation.FORMAT_INVALIDE;
            case "Pattern":
                return TypeErreurValidation.PATTERN_NON_RESPECTE;
            case "Min":
            case "Max":
            case "Range":
                return TypeErreurValidation.PLAGE_INVALIDE;
            default:
                return TypeErreurValidation.CONTRAINTE_METIER;
        }
    }
    
    /**
     * Vérifie si l'exception contient une erreur pour un champ spécifique
     */
    public boolean hasErreurPourChamp(String champ) {
        return erreursValidation.stream()
                              .anyMatch(erreur -> champ.equals(erreur.getChamp()));
    }
    
    /**
     * Récupère les erreurs pour un champ spécifique
     */
    public List<ErreurValidation> getErreursPourChamp(String champ) {
        return erreursValidation.stream()
                              .filter(erreur -> champ.equals(erreur.getChamp()))
                              .collect(java.util.stream.Collectors.toList());
    }
    
    /**
     * Génère un résumé des erreurs de validation
     */
    public String genererResumeErreurs() {
        if (erreursValidation.isEmpty()) {
            return "Aucune erreur de validation spécifique";
        }
        
        StringBuilder resume = new StringBuilder();
        resume.append("=== ERREURS DE VALIDATION ===\n");
        
        Map<String, List<ErreurValidation>> erreursParChamp = new HashMap<>();
        for (ErreurValidation erreur : erreursValidation) {
            erreursParChamp.computeIfAbsent(erreur.getChamp(), k -> new ArrayList<>())
                          .add(erreur);
        }
        
        for (Map.Entry<String, List<ErreurValidation>> entry : erreursParChamp.entrySet()) {
            resume.append("Champ '").append(entry.getKey()).append("':\n");
            for (ErreurValidation erreur : entry.getValue()) {
                resume.append("  - ").append(erreur.getMessageErreur()).append("\n");
                if (erreur.getValeurAttendue() != null) {
                    resume.append("    Attendu: ").append(erreur.getValeurAttendue()).append("\n");
                }
            }
        }
        
        if (!suggestionsCorrection.isEmpty()) {
            resume.append("\nSuggestions de correction:\n");
            for (String suggestion : suggestionsCorrection) {
                resume.append("  • ").append(suggestion).append("\n");
            }
        }
        
        return resume.toString();
    }
    
    // Getters et Setters
    public List<ErreurValidation> getErreursValidation() {
        return erreursValidation;
    }
    
    public void setErreursValidation(List<ErreurValidation> erreursValidation) {
        this.erreursValidation = erreursValidation;
    }
    
    public String getObjetValide() {
        return objetValide;
    }
    
    public void setObjetValide(String objetValide) {
        this.objetValide = objetValide;
    }
    
    public String getClasseObjet() {
        return classeObjet;
    }
    
    public void setClasseObjet(String classeObjet) {
        this.classeObjet = classeObjet;
    }
    
    public String getContexteValidation() {
        return contexteValidation;
    }
    
    public void setContexteValidation(String contexteValidation) {
        this.contexteValidation = contexteValidation;
    }
    
    public List<String> getReglesAppliquees() {
        return reglesAppliquees;
    }
    
    public void setReglesAppliquees(List<String> reglesAppliquees) {
        this.reglesAppliquees = reglesAppliquees;
    }
    
    public List<String> getSuggestionsCorrection() {
        return suggestionsCorrection;
    }
    
    public void setSuggestionsCorrection(List<String> suggestionsCorrection) {
        this.suggestionsCorrection = suggestionsCorrection;
    }
    
    public boolean isValidationIgnorable() {
        return validationIgnorable;
    }
    
    public void setValidationIgnorable(boolean validationIgnorable) {
        this.validationIgnorable = validationIgnorable;
    }
    
    public SeveriteValidation getSeverite() {
        return severite;
    }
    
    public void setSeverite(SeveriteValidation severite) {
        this.severite = severite;
    }
    
    public String getGroupeValidation() {
        return groupeValidation;
    }
    
    public void setGroupeValidation(String groupeValidation) {
        this.groupeValidation = groupeValidation;
    }
    
    @Override
    public String toString() {
        return "ValidationException{" +
                "message='" + getMessage() + '\'' +
                ", codeErreur='" + getCodeErreur() + '\'' +
                ", nombreErreurs=" + (erreursValidation != null ? erreursValidation.size() : 0) +
                ", severite=" + severite +
                ", objetValide='" + objetValide + '\'' +
                ", contexteValidation='" + contexteValidation + '\'' +
                '}';
    }
}