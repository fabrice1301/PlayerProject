package eafc.peruwelz.playerproject.validation;

import javafx.util.Callback;

public abstract class AbstractValidation {
    private String ruleMessage;
    private boolean mandatory;
    private String regExp;
    public AbstractValidation(String ruleMessage, boolean mandatory) {

        setMandatory(mandatory);
        setRuleMessage(ruleMessage);
        setRegExp("^.*$");
    }

    public abstract boolean isValid(String value);
    public String getRuleMessage() {
        return ruleMessage;
    }

    public void setRuleMessage(String ruleMessage) {
        this.ruleMessage = ruleMessage;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public String getRegExp() {
        return regExp;
    }
    public void setRegExp(String regExp) {
        this.regExp = regExp;
    }

}
