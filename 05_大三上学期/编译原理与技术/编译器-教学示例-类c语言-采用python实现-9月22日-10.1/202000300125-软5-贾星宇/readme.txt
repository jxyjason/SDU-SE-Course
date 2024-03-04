文件结构
-------------------------------------------------------------------------------------------------------------

202000300125-软5-贾星宇
|-bianyiyuanli
--|-.idea
--|-out
--|-src
-----|-Codegenerator.java
-----|-Lexer.java
-----|-Main.java
-----|-main.txt
-----|-NodeVisitor.java
-----|-Parser.java
-----|-SematicAnalyzer.java
--|-bianyiyuanli.iml
|-readme.txt
|-贾星宇-202000300125-实验报告.doc

-------------------------------------------------------------------------------------------------------------

代码运行环境：java 8+
输入：txt程序文件，修改Main.java文件第11行绝对路径为此文件路径，即可运行
输出：汇编代码

书写程序文件参考词法及语法：
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
词法：
     static String TK_INT = "TK_INT";
     static String TK_FLOAT = "TK_FLOAT";
     static String TK_BOOL = "TK_BOOL";
     static String TK_TRUE = "TK_TRUE";
     static String TK_FALSE = "TK_FALSE";
     static String TK_IF = "TK_IF";
     static String TK_ELSE = "TK_ELSE";
     static String TK_RETURN = "TK_RETURN";
     static String TK_MAIN = "TK_MAIN";

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


~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

语法：

program := function_definition*
        function_definition := type_specification identifier "(" formal_parameters? ")" block
        formal_parameters := formal_parameter ("," formal_parameter)*
        formal_parameter := type_specification identifier
        type_specification := "int" | "bool"
        block := "{" compound_statement "}"
        compound_statement := (variable_declaration | statement)*
        statement := expression-statement
                    | "return" expression-statement
                    | block
                    | "if" "(" expression ")" statement ("else" statement)?
                    | "while" "(" expression ")" statement
        variable_declaration := type_specification identifier ("," identifier)* ";"
                              | type_specification identifier "[" num "]" ("=" "{" (num)? ("," num)* "}")?
        expression-statement := expression? ";"
        expression := logic ("=" expression)?
        logic := equality ("&&" equality | "||" equality)*
        equality := relational ("==" relational | "! =" relational)*
        relational := add_sub ("<" add_sub | "<=" add_sub | ">" add_sub | ">=" add_sub)*
        add_sub := mul_div ("+" mul_div | "-" mul_div)*
        mul_div := unary ("*" unary | "/" unary)*
        unary := unary := ("+" | "-" | "!") unary | primary
        primary := "(" expression ")" | identifier args?| num identifier "[" expression "]"
        args := "(" (expression ("," expression)*)? ")"



——————————————————————————————————————————————