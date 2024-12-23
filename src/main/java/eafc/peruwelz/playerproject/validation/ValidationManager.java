package eafc.peruwelz.playerproject.validation;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import java.util.List;

public class ValidationManager {
    private static ValidationManager instance;
    private String tooltipStyleError;
    private String tooltipStyleDisplayRule;
    private int tooltipDelay;
    private String backgroundErrorColor;
    private ValidationManager() {
            this.tooltipDelay = 3;
            this.backgroundErrorColor = "#f2c7db";
            this.tooltipStyleError = "-fx-background-color:"+ backgroundErrorColor +";"
                    + "-fx-font-size: 12px; "
                    + "-fx-text-fill: #0f0102;";
            this.tooltipStyleDisplayRule = "-fx-text-fill: #89ed80; "
                    + "-fx-font-size: 12px; ";
    }

    public static ValidationManager getInstance() {
        if (instance == null) {
            instance = new ValidationManager();
        }
        return instance;
    }
    /**
     * Classe interne
     * Un objet de type ValidationData est stocké
     * dans chaque textfield, dans le champ userData
     * Cela permet de stocker le booléen qui indique si le champ est valide,
     * de faire en sorte que chaque textfield puisse atteindre tous les autres.
     * A chaque changement de valeur d'un textfield, on consulte l'état de tous
     * les autres testfield pour déterminer si submitNode doit être activé
     */
    private static class ValidationData {
        public boolean isValid;
        public List<TextField> textFields;
        public Node submitNode;
        public Tooltip tooltip;
        public ValidationData(boolean isValid) {
            this.isValid = isValid;
        }
    }

    /**
     * Associe un objet valideur à un champ texte     *
     *
     * @param textField Le node qui permet la saisie du texte
     * @param validator Le valideur
     */
    public void setValidation(TextField textField, AbstractValidation validator) {
        String style = textField.getStyle();
        textField.setPromptText(validator.getRuleMessage());
        textField.setUserData(new ValidationData(validator.isValid(textField.getText())));
        // A chaque changement de valeur, la couleur de fond du textfield
        // change si la donnée n'est pas valide
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            // setUserData permet de stocker un objet de type quelconque (object)
            // dans le textfield
            ValidationData validationData = (ValidationData) textField.getUserData();
            validationData.isValid = validator.isValid(newValue);
            if (!validationData.isValid) {
                textField.setStyle("-fx-background-color: " + backgroundErrorColor + "; ");
            }
            else {
                textField.setStyle(style);
            }
            boolean allValid = validationData.isValid;
            // pour évaluer si le bouton de submit (submitNode)
            // doit être actif ou pas
            for (TextField tf : validationData.textFields) {
                if (!allValid) break;
                if (tf != textField) {
                    ValidationData vd = (ValidationData) tf.getUserData();
                    allValid = allValid && vd.isValid;
                }
            }
            validationData.submitNode.setDisable(!allValid);
        });
        // Changement de Focus
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {

            ValidationData validationData = (ValidationData) textField.getUserData();
            // PRISE DU FOCUS : on montre la règle permettant d'encoder une donnée
            // valide
            if (newValue) {
                textField.setTooltip(null);
                validationData.tooltip = new Tooltip(validator.getRuleMessage());
                validationData.tooltip.setStyle(tooltipStyleDisplayRule);
                // Positionner le tooltip sous le TextField
                // ce tooltip est "flottant" et n'est pas lié au textfield
                double x = textField.localToScreen(textField.getBoundsInLocal()).getMinX();
                double y = textField.localToScreen(textField.getBoundsInLocal()).getMaxY();
                validationData.tooltip.show(textField, x, y+2);

            } else { // PERTE DU FOCUS
                // si la donnée n'est pas valide, le tooltip est attaché au textfield
                if (validationData.tooltip != null)
                    validationData.tooltip.hide();
                if (!validationData.isValid) {
                    Tooltip tooltip = new Tooltip(validator.getRuleMessage());
                    tooltip.setStyle(tooltipStyleError);
                    tooltip.setShowDuration(Duration.seconds(tooltipDelay));
                    textField.setTooltip(tooltip);
                    textField.setStyle("-fx-background-color: "+ backgroundErrorColor +"; ");
                    tooltip.setShowDelay(new Duration(0));
                } else {
                    textField.setStyle(style);
                    textField.setTooltip(null);
                }
            }
        });
    }

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

    public void setTooltipStyleError(String tooltipStyleError) {
        this.tooltipStyleError = tooltipStyleError;
    }

    public void setTooltipStyleDisplayRule(String tooltipStyleDisplayRule) {
        this.tooltipStyleDisplayRule = tooltipStyleDisplayRule;
    }

    public void setTooltipDelay(int tooltipDelay) {
        this.tooltipDelay = tooltipDelay;
    }

    public void setBackgroundErrorColor(String backgroundErrorColor) {
        this.backgroundErrorColor = backgroundErrorColor;
    }
}