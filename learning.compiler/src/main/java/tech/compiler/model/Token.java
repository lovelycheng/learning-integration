package tech.compiler.model;

/**
 * @author chengtong
 * @date 2019/11/9 15:01
 */
public class Token<gggZzz> {

    private TokenType type;
    private String text;

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public enum TokenType {
        INT,
        SYMBOL_ADD,
        SYMBOL_MULTIPLE,
        SYMBOL_MINUS,
        SYMBOL_DIVIDE,
        EOF
    }

}
