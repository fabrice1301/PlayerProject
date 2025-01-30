package eafc.peruwelz.playerproject.validation;

/**
 * Classe représentant une validation pour des chaînes de caractères.
 * Elle étend la classe {@link AbstractValidation} et permet de valider une chaîne de caractères
 * en fonction d'une longueur maximale et d'une expression régulière.
 */
public class StringValidation extends AbstractValidation {

    /** La longueur maximale autorisée pour la chaîne de caractères. */
    private int maxLength;

    /**
     * Constructeur de la classe StringValidation.
     *
     * @param ruleMessage Le message de la règle de validation.
     * @param mandatory Indique si la validation est obligatoire ou non.
     */
    public StringValidation(String ruleMessage, boolean mandatory) {
        super(ruleMessage, mandatory);
        this.maxLength = Integer.MAX_VALUE; // Par défaut, la longueur maximale est illimitée.
    }

    /**
     * Définit la longueur maximale autorisée pour la chaîne de caractères.
     *
     * @param maxLength La longueur maximale.
     * @return L'objet StringValidation actuel, permettant l'enchaînement des appels de méthode.
     */
    public StringValidation setMaxLength(int maxLength){
        this.maxLength = maxLength;
        return this; // Permet l'enchaînement des appels de méthode.
    }

    /**
     * Définit l'expression régulière utilisée pour valider la chaîne de caractères.
     *
     * @param regExp L'expression régulière à utiliser pour la validation.
     * @return L'objet StringValidation actuel, permettant l'enchaînement des appels de méthode.
     */
    public StringValidation setRegularExp(String regExp){
        setRegExp(regExp);
        return this; // Permet l'enchaînement des appels de méthode.
    }

    /**
     * Vérifie si la chaîne de caractères fournie est valide.
     * La validation vérifie que la chaîne n'est pas vide (ou est facultative si elle n'est pas obligatoire),
     * qu'elle respecte la longueur maximale et qu'elle correspond à l'expression régulière définie.
     *
     * @param value La chaîne de caractères à valider.
     * @return true si la chaîne est valide, false sinon.
     */
    @Override
    public boolean isValid(String value) {
        return ((!value.isEmpty() || !isMandatory())
                && value.length() <= this.maxLength && value.matches(getRegExp()));
    }
}
