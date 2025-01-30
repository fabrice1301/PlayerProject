package eafc.peruwelz.playerproject.validation;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import java.util.List;

/**
 * Gestionnaire de validation des champs de saisie.
 * Cette classe permet d'appliquer des validations aux champs texte et d'activer/désactiver un bouton de soumission
 * en fonction de la validité des données saisies. Elle gère également l'affichage de messages d'erreur via des tooltips.
 */
public class ValidationManager {
    /** Instance unique de ValidationManager (Singleton). */
    private static ValidationManager instance;

    /** Style CSS utilisé pour l'affichage des erreurs dans les tooltips. */
    private String tooltipStyleError;

    /** Style CSS utilisé pour l'affichage des règles de validation dans les tooltips. */
    private String tooltipStyleDisplayRule;

    /** Délai avant l'affichage d'un tooltip (en secondes). */
    private int tooltipDelay;

    /** Couleur de fond utilisée pour indiquer une erreur. */
    private String backgroundErrorColor;

    /**
     * Constructeur privé pour initialiser les valeurs par défaut.
     * Définit le délai du tooltip, la couleur de fond de l'erreur, et les styles des tooltips.
     */
    private ValidationManager() {
        this.tooltipDelay = 3;
        this.backgroundErrorColor = "#f2c7db";
        this.tooltipStyleError = "-fx-background-color:" + backgroundErrorColor + ";"
                + "-fx-font-size: 12px; "
                + "-fx-text-fill: #0f0102;";
        this.tooltipStyleDisplayRule = "-fx-text-fill: #89ed80; "
                + "-fx-font-size: 12px; ";
    }

    /**
     * Récupère l'instance unique de ValidationManager (Singleton).
     * Si l'instance n'a pas encore été créée, elle est initialisée.
     *
     * @return L'instance unique de ValidationManager.
     */
    public static ValidationManager getInstance() {
        if (instance == null) {
            instance = new ValidationManager();
        }
        return instance;
    }

    /**
     * Classe interne représentant les données de validation d'un champ.
     * Chaque champ texte stocke un objet ValidationData dans son champ userData
     * pour indiquer si le champ est valide, et pour gérer l'état des tooltips et du bouton de soumission.
     */
    private static class ValidationData {
        /** Indique si le champ est valide ou non. */
        public boolean isValid;

        /** Liste des champs texte associés à cette validation. */
        public List<TextField> textFields;

        /** Le noeud du bouton de soumission à activer/désactiver. */
        public Node submitNode;

        /** Le tooltip associé au champ texte. */
        public Tooltip tooltip;

        /**
         * Constructeur de ValidationData.
         *
         * @param isValid Indique si la validation est valide ou non.
         */
        public ValidationData(boolean isValid) {
            this.isValid = isValid;
        }
    }

    /**
     * Associe une validation à un champ texte.
     * La validation est effectuée chaque fois que la valeur du champ change, et les tooltips sont affichés en fonction de l'état de validation.
     *
     * @param textField Le champ texte à valider.
     * @param validator L'objet de validation à utiliser pour ce champ.
     */
    public void setValidation(TextField textField, AbstractValidation validator) {
        String style = textField.getStyle();
        textField.setPromptText(validator.getRuleMessage());
        textField.setUserData(new ValidationData(validator.isValid(textField.getText())));

        // Écouteur pour les changements de texte
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            ValidationData validationData = (ValidationData) textField.getUserData();
            validationData.isValid = validator.isValid(newValue);

            // Met à jour le style du champ en fonction de la validité des données
            if (!validationData.isValid) {
                textField.setStyle("-fx-background-color: " + backgroundErrorColor + "; ");
            } else {
                textField.setStyle(style);
            }

            boolean allValid = validationData.isValid;
            // Vérifie l'état de tous les autres champs pour activer/désactiver le bouton de soumission
            for (TextField tf : validationData.textFields) {
                if (!allValid) break;
                if (tf != textField) {
                    ValidationData vd = (ValidationData) tf.getUserData();
                    allValid = allValid && vd.isValid;
                }
            }
            validationData.submitNode.setDisable(!allValid);
        });

        // Écouteur pour les changements de focus
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            ValidationData validationData = (ValidationData) textField.getUserData();

            // Affiche la règle de validation lorsque le champ reçoit le focus
            if (newValue) {
                textField.setTooltip(null);
                validationData.tooltip = new Tooltip(validator.getRuleMessage());
                validationData.tooltip.setStyle(tooltipStyleDisplayRule);

                // Positionne le tooltip sous le TextField
                double x = textField.localToScreen(textField.getBoundsInLocal()).getMinX();
                double y = textField.localToScreen(textField.getBoundsInLocal()).getMaxY();
                validationData.tooltip.show(textField, x, y + 2);
            } else { // Perte du focus
                if (validationData.tooltip != null) {
                    validationData.tooltip.hide();
                }
                if (!validationData.isValid) {
                    Tooltip tooltip = new Tooltip(validator.getRuleMessage());
                    tooltip.setStyle(tooltipStyleError);
                    tooltip.setShowDuration(Duration.seconds(tooltipDelay));
                    textField.setTooltip(tooltip);
                    textField.setStyle("-fx-background-color: " + backgroundErrorColor + "; ");
                    tooltip.setShowDelay(new Duration(0));
                } else {
                    textField.setStyle(style);
                    textField.setTooltip(null);
                }
            }
        });
    }

    /**
     * Définit le bouton de soumission qui sera activé/désactivé en fonction de la validité des champs texte associés.
     *
     * @param node Le bouton de soumission à activer/désactiver.
     * @param textfields Les champs texte associés à la validation.
     */
    public void setSubmitButton(Node node, TextField... textfields) {
        boolean allValid = true;
        for (TextField textField : textfields) {
            ValidationData validationData = (ValidationData) textField.getUserData();
            validationData.textFields = List.of(textfields);
            validationData.submitNode = node;
            allValid = allValid && validationData.isValid;
        }
        node.setDisable(!allValid);
    }

    // Getters et Setters pour les propriétés de style

    /**
     * Définit le style du tooltip d'erreur utilisé pour afficher les messages d'erreur dans les champs texte.
     * Le style personnalisé sera appliqué lors de l'affichage des tooltips indiquant une erreur de validation.
     *
     * @param tooltipStyleError Le style CSS à appliquer au tooltip d'erreur.
     */
    public void setTooltipStyleError(String tooltipStyleError) {
        this.tooltipStyleError = tooltipStyleError;
    }

    /**
     * Définit le style du tooltip utilisé pour afficher les règles de validation dans les champs texte.
     * Ce style est utilisé lorsque l'utilisateur entre un champ texte et que la règle de validation s'affiche sous forme de tooltip.
     *
     * @param tooltipStyleDisplayRule Le style CSS à appliquer au tooltip des règles de validation.
     */
    public void setTooltipStyleDisplayRule(String tooltipStyleDisplayRule) {
        this.tooltipStyleDisplayRule = tooltipStyleDisplayRule;
    }

    /**
     * Définit le délai d'affichage des tooltips de validation.
     * Le délai spécifie combien de temps après la perte de focus un tooltip d'erreur s'affiche pour un champ texte.
     *
     * @param tooltipDelay Le délai avant l'affichage du tooltip d'erreur (en secondes).
     */
    public void setTooltipDelay(int tooltipDelay) {
        this.tooltipDelay = tooltipDelay;
    }

    /**
     * Définit la couleur de fond utilisée pour indiquer une erreur dans les champs texte.
     * La couleur est utilisée pour modifier l'apparence de l'arrière-plan des champs qui contiennent des données invalides.
     *
     * @param backgroundErrorColor La couleur de fond (en format hexadécimal) à utiliser pour les erreurs de validation.
     */
    public void setBackgroundErrorColor(String backgroundErrorColor) {
        this.backgroundErrorColor = backgroundErrorColor;
    }

}
