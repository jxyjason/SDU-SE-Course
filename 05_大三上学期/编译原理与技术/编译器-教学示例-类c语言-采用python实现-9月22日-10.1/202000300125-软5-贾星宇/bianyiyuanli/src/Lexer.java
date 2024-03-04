import java.util.*;

/*
1.空格、tab
2.数字
3.ID：变量名、关键字
4.符号：算术运算符、关系运算符、逻辑运算符、赋值运算符、标点符
*/

class TokenType {
    static String[] keyWords = {"int", "bool", "if", "else", "return", "main", "float", "true", "false", "while"};
    static String[] calculateOptions = {"+", "-", "*", "/", "%", "++", "--"};
    static String[] relationshipOptions = {"==", "!=", ">", ">=", "<", "<="};
    static String[] logicOptions = {"&&", "||", "!"};
    static String[] punctuator = {"(", ")", "[", "]", "{", "}", ",", ";", "."};


    static String TK_INT = "TK_INT";
    static String TK_WHILE = "TK_WHILE";
    static String TK_FLOAT = "TK_FLOAT";
    static String TK_BOOL = "TK_BOOL";
    static String TK_TRUE = "TK_TRUE";
    static String TK_FALSE = "TK_FALSE";
    static String TK_IF = "TK_IF";
    static String TK_ELSE = "TK_ELSE";
    static String TK_RETURN = "TK_RETURN";
    static String TK_MAIN = "TK_MAIN";
    static String TK_EOF = "TK_EOF";
    static String IDENTITY = "IDENTITY";
    static String INTEGER_CONST = "INTEGER_CONST";

    static String TK_PLUS = "+";
    static String TK_MINUS = "-";
    static String TK_MULTY = "*";
    static String TK_EXCEPT = "/";
    static String TK_MOLD = "%";
    static String TK_SELF_ADD = "++";
    static String TK_SELF_MINUS = "--";

    static String TK_EQUAL = "==";
    static String TK_NOT_EQUAL = "!=";
    static String TK_GREAT_THAN = ">";
    static String TK_LESS_THAN = "<";
    static String TK_GREAT_EQUAL = ">=";
    static String TK_LESS_EQUAL = "<=";

    static String TK_AND = "&&";
    static String TK_OR = "||";
    static String TK_NOT = "!";
    static String TK_ASSIGN = "=";

    static String TK_LEFT_BRACKET = "(";
    static String TK_RIGHT_BRACKET = ")";
    static String TK_LEFT_SQUARE_BRACKET = "[";
    static String TK_RIGHT_SQUARE_BRACKET = "]";
    static String TK_LEFT_BRACE = "{";
    static String TK_RIGHT_BRACE = "}";
    static String TK_COMMA = ",";
    static String TK_SEMI = ";";
    static String TK_DOT = ".";


}

class Error {
    public static int nowLine = 1;
    public static int nowColumn = 1;
    public static String text;

    public static void showError(String msg) {
        System.err.println("There is an error in line " + nowLine + ", column " + nowColumn + ", and it says: " + msg + " :");
        int count = 1;
        int i = 0;//表示此行开始为第几个字符
        while (count < nowLine) {
            if (text.charAt(i) != '\n') i++;
            else {
                count++;
                i++;
            }
        }
        int j = i;
        while (text.charAt(j) != '\n') {
            System.out.print(text.charAt(j));
            j++;
            if (j >= text.length()) break;
        }
        System.out.println();
        j = i + nowColumn - 2;
        for (int k = i; k < j; k++) {
            System.out.print("~");
        }
        System.out.println("^");

        System.exit(0);
    }
}

class Token {
    String type;
    String value;
    int line;
    int column;
    int width;

    public Token(String type, String value, int line, int column, int width) {
        this.type = type;
        this.value = value;
        this.line = line;
        this.column = column;
        this.width = width;
    }
}

public class Lexer {
    String text;
    int position;
    char currentChar;
    List<Token> tokenList;

    public Lexer(String text, int position) {
        this.text = text;
        this.position = position;
        this.currentChar = text.charAt(position);
        this.tokenList = new ArrayList<Token>();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public char getCurrentChar() {
        return currentChar;
    }

    public void setCurrentChar(char currentChar) {
        this.currentChar = currentChar;
    }

    public List<Token> getTokenList() {
        return tokenList;
    }

    public void setTokenList(List<Token> tokenList) {
        this.tokenList = tokenList;
    }

    //是否变量名开头的命名规范
    boolean ifFirstInID(char c) {
        return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z') || c == '_';
    }

    //是否变量名中间的命名规范
    boolean ifOtherInID(char c) {
        return ifFirstInID(c) || ('0' <= c && c <= '9');
    }

    //是否数字
    boolean ifNumber(char c) {
        return ('0' <= c && c <= '9');
    }

    //判断是否为关键字,返回此关键字type
    String ifKeyWords(String theWord) {
        String result = new String();
        for (int i = 0; i < TokenType.keyWords.length; i++) {
            if (theWord.equals(TokenType.keyWords[i])) {
                result = "TK_" + TokenType.keyWords[i].toUpperCase();//such as TK_INT
                break;
            }
        }
        return result;
    }

    //是否算术运算符
    boolean ifCalculateOp(String theOp) {
        boolean result = false;
        for (int i = 0; i < TokenType.calculateOptions.length; i++) {
            if (TokenType.calculateOptions[i].equals(theOp)) result = true;
        }
        return result;
    }

    //是否关系运算符
    boolean ifRelationshipOp(String theOp) {
        boolean result = false;
        for (int i = 0; i < TokenType.relationshipOptions.length; i++) {
            if (TokenType.relationshipOptions[i].equals(theOp)) result = true;
        }
        return result;
    }

    //是否逻辑运算符
    boolean ifLogicOp(String theOp) {
        boolean result = false;
        for (int i = 0; i < TokenType.logicOptions.length; i++) {
            if (TokenType.logicOptions[i].equals(theOp)) result = true;
        }
        return result;
    }

    //是否标点符号
    boolean ifPunctuator(String theOp) {
        boolean result = false;
        for (int i = 0; i < TokenType.punctuator.length; i++) {
            if (TokenType.punctuator[i].equals(theOp)) result = true;
        }
        return result;
    }

    //是否赋值运算符
    boolean ifAssignOp(String theOp) {
        return theOp.equals("=");
    }

    //跳过空白符
    void skipWhitespace() {
        while (currentChar == ' ') {
            position++;
            if (position >= text.length()) {
                return;
            }
            currentChar = text.charAt(position);
            Error.nowColumn++;


        }
        while (currentChar == '\n') {
            position++;
            if (position >= text.length()) {
                return;
            }
            currentChar = text.charAt(position);
            Error.nowColumn = 1;
            Error.nowLine++;


        }
        while (currentChar == '\t') {
            position++;
            if (position >= text.length()) {
                return;
            }
            currentChar = text.charAt(position);
            Error.nowColumn += 4;


        }
    }

    //读取整数为token
    Token readNumber() {
        String theNum = new String();
//        if (currentChar == '-'){
//            theNum += "-";
//            position++;
//            currentChar = text.charAt(position);
//        }
        while (ifNumber(currentChar)) {
            theNum += currentChar;
            position++;
            if (position < text.length()) currentChar = text.charAt(position);
            else currentChar = ' ';
        }
        Error.nowColumn += theNum.length();
        return new Token(TokenType.INTEGER_CONST, theNum, Error.nowLine, Error.nowColumn, theNum.length());
    }

    //读取关键字或者变量名为token
    Token readIdOrKeyWords() {
        String theWords = new String();
        while (ifOtherInID(currentChar)) {
            theWords += currentChar;
            position++;
            if (position < text.length()) currentChar = text.charAt(position);
            else break;
        }
        if (!ifKeyWords(theWords).equals("")) {
            Error.nowColumn += theWords.length();
            return new Token(ifKeyWords(theWords), theWords, Error.nowLine, Error.nowColumn, theWords.length());
        } else {
            Error.nowColumn += theWords.length();
            return new Token(TokenType.IDENTITY, theWords, Error.nowLine, Error.nowColumn, theWords.length());
        }
    }

    //得到算术运算符token
    Token readCalculateOpToken(int len, String op) {
        String key = new String();
        if (len == 1) {
            position += 1;
            switch (op) {
                case "+":
                    key = "TK_PLUS";
                    break;
                case "-":
                    key = "TK_MINUS";
                    break;
                case "*":
                    key = "TK_MULTY";
                    break;
                case "/":
                    key = "TK_EXCEPT";
                    break;
                case "%":
                    key = "TK_MOLD";
                    break;
            }
        } else if (len == 2) {
            position += 2;
            switch (op) {
                case "++":
                    key = "TK_SELF_ADD";
                    break;
                case "--":
                    key = "TK_SELF_MINUS";
                    break;
            }
        }
        Error.nowColumn += len;
        return new Token(key, op, Error.nowLine, Error.nowColumn, len);
    }

    //得到关系运算符token
    Token readRelationOpToken(int len, String op) {
        String key = new String();
        if (len == 1) {
            position += 1;
            switch (op) {
                case ">":
                    key = "TK_GREAT_THAN";
                    break;
                case "<":
                    key = "TK_LESS_THAN";
                    break;
            }
        } else if (len == 2) {
            position += 2;
            switch (op) {
                case ">=":
                    key = "TK_GREAT_EQUAL";
                    break;
                case "<=":
                    key = "TK_LESS_EQUAL";
                    break;
                case "==":
                    key = "TK_EQUAL";
                    break;
                case "!=":
                    key = "TK_NOT_EQUAL";
                    break;
            }
        }
        Error.nowColumn += len;
        return new Token(key, op, Error.nowLine, Error.nowColumn, len);
    }

    //得到逻辑运算符token
    Token readLogicOpToken(int len, String op) {
        String key = new String();
        if (len == 1) {
            position += 1;
            switch (op) {
                case "!":
                    key = "TK_NOT";
                    break;
            }
        } else if (len == 2) {
            position += 2;
            switch (op) {
                case "&&":
                    key = "TK_AND";
                    break;
                case "||":
                    key = "TK_OR";
                    break;
            }
        }
        Error.nowColumn += len;
        return new Token(key, op, Error.nowLine, Error.nowColumn, len);
    }

    //得到赋值运算符token
    Token readAssignOpToken(int len, String op) {
        String key = new String();
        if (len == 1) {
            position += 1;
            switch (op) {
                case "=":
                    key = "TK_ASSIGN";
                    break;
            }
        } else if (len == 2) {
            position += 2;
        }
        Error.nowColumn += len;
        return new Token(key, op, Error.nowLine, Error.nowColumn, len);
    }

    //得到标点符token
    Token readPunctuatorToken(int len, String op) {
        String key = new String();
        if (len == 1) {
            position += 1;
            switch (op) {
                case "(":
                    key = "TK_LEFT_BRACKET";
                    break;
                case ")":
                    key = "TK_RIGHT_BRACKET";
                    break;
                case "[":
                    key = "TK_LEFT_SQUARE_BRACKET";
                    break;
                case "]":
                    key = "TK_RIGHT_SQUARE_BRACKET";
                    break;
                case "{":
                    key = "TK_LEFT_BRACE";
                    break;
                case "}":
                    key = "TK_RIGHT_BRACE";
                    break;
                case ",":
                    key = "TK_COMMA";
                    break;
                case ";":
                    key = "TK_SEMI";
                    break;
                case ".":
                    key = "TK_DOT";
                    break;
            }
        } else if (len == 2) {
            position += 2;
        }
        Error.nowColumn += len;
        return new Token(key, op, Error.nowLine, Error.nowColumn, len);
    }


    Token getNextToken() {
        if (position >= text.length()) {
            return new Token("EOF", "EOF", Error.nowLine, Error.nowColumn, 3);
        }
        currentChar = text.charAt(position);
        skipWhitespace();
        if (position >= text.length()) {
            return new Token("EOF", "EOF", Error.nowLine, Error.nowColumn, 3);
        }

        if (ifNumber(currentChar)) {//数字
            return readNumber();
        } else if (ifFirstInID(currentChar)) {//ID 或者关键字
            return readIdOrKeyWords();
        } else if (ifCalculateOp(String.valueOf(currentChar))) {//算数运算符
            if (position < text.length() - 1) {//如果不是最后一个，则有可能双目
                if (ifCalculateOp(String.valueOf(text.charAt(position) + text.charAt(position + 1)))) {//如果双目
                    return readCalculateOpToken(2, String.valueOf(text.charAt(position) + text.charAt(position + 1)));
                } else {
                    return readCalculateOpToken(1, String.valueOf(text.charAt(position)));
                }
            } else {
                return readCalculateOpToken(1, String.valueOf(text.charAt(position)));
            }
        } else if (ifRelationshipOp(String.valueOf(currentChar))) {//关系运算符
            if (position < text.length() - 1) {
                if (ifRelationshipOp(String.valueOf(text.charAt(position) + "" + text.charAt(position + 1)))) {//如果双目
                    return readRelationOpToken(2, String.valueOf(text.charAt(position) + "" + text.charAt(position + 1)));
                } else {
                    return readRelationOpToken(1, String.valueOf(text.charAt(position)));
                }
            } else {
                return readRelationOpToken(1, String.valueOf(text.charAt(position)));
            }
        } else if (ifLogicOp(String.valueOf(currentChar))) {//逻辑运算符
            if (position < text.length() - 1) {
                if (ifLogicOp(String.valueOf(text.charAt(position) + "" + text.charAt(position + 1)))) {//如果双目
                    return readLogicOpToken(2, String.valueOf(text.charAt(position) + "" + text.charAt(position + 1)));
                } else {
                    return readLogicOpToken(1, String.valueOf(text.charAt(position)));
                }
            } else {
                return readLogicOpToken(1, String.valueOf(text.charAt(position)));
            }
        } else if (ifAssignOp(String.valueOf(currentChar))) {//赋值运算符
            if (position < text.length() - 1) {
                if (ifAssignOp(String.valueOf(text.charAt(position) + "" + text.charAt(position + 1)))) {//如果双目
                    return readAssignOpToken(2, String.valueOf(text.charAt(position) + "" + text.charAt(position + 1)));
                } else {
                    return readAssignOpToken(1, String.valueOf(text.charAt(position)));
                }
            } else {
                return readAssignOpToken(1, String.valueOf(text.charAt(position)));
            }
        } else if (ifPunctuator(String.valueOf(currentChar))) {//标点符
            if (position < text.length() - 1) {
                if (ifPunctuator(String.valueOf(text.charAt(position) + "" + text.charAt(position + 1)))) {//如果双目
                    return readPunctuatorToken(2, String.valueOf(text.charAt(position) + "" + text.charAt(position + 1)));
                } else {
                    return readPunctuatorToken(1, String.valueOf(text.charAt(position)));
                }
            } else {
                return readPunctuatorToken(1, String.valueOf(text.charAt(position)));
            }
        } else {
            Error.showError("invalid token which is " + text.charAt(position));
            return new Token("TK_ERROR", "error", Error.nowLine, Error.nowColumn, 0);
        }
    }


}
