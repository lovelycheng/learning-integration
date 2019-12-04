package tech.compiler.lex;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import tech.compiler.model.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author chengtong
 * @date 2019/11/9 14:31
 */
public class LexParser {

    public List<Token> parseLex(String code) {

        if (code == null || code.length() == 0) {
            return new ArrayList<>();
        }

        List<Token> list = new ArrayList<>();

        for (int i = 0; i < code.length(); i++) {

        }
        return null;
    }

    private boolean isDigital(CharSequence chars) {
        String regex = "[0-9](1)";
        return Pattern.matches(regex,chars);
    }


}
