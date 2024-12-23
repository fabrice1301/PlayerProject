package eafc.peruwelz.playerproject.validation;

public class StringValidation extends AbstractValidation {

    private int maxLength;

    public StringValidation(String ruleMessage, boolean mandatory) {
        super(ruleMessage, mandatory);
        this.maxLength = Integer.MAX_VALUE;
    }
    public StringValidation setMaxLength(int maxLength){
        this.maxLength = maxLength;
        return this;
    }
    public StringValidation setRegularExp(String regExp){
        setRegExp(regExp);
        return this;
    }

    @Override
    public boolean isValid(String value) {
        return ((!value.isEmpty() || !isMandatory())
                && value.length() <= this.maxLength && value.matches(getRegExp()));
    }
}
