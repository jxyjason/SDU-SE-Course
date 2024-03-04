import jdk.nashorn.internal.ir.VarNode;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Objects;

public class Parser {

    Lexer lexer;
    Token currentToken;
    String currentFunctionName;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        this.currentToken = lexer.getNextToken();
        this.currentFunctionName = new String();
    }


    public void eat(String tokenType) {
        if (Objects.equals(currentToken.type, tokenType)) {
            currentToken = lexer.getNextToken();
        } else {
            Error.showError("PARSER ERROR IN TOKEN:" + currentToken.type + "  " + currentToken.value);
        }
    }

    //args = "(" (assign ("," assign)*)? ")"
//    public void args01Judge() {
//        if (currentToken.type.equals("TK_LEFT_BRACKET")) {
//            eat("TK_LEFT_BRACKET");
//            assignJudge();
//            while (currentToken.type.equals("TK_COMMA")) {
//                eat("TK_LEFT_BRACKET");
//                assignJudge();
//            }
//            eat("TK_RIGHT_BRACKET");
//        }
//    }


//     primary := "(" expression ")" | identifier args? | num | identifier "[" expression "]"
//     args := "(" (expression ("," expression)*)? ")"
    public AST_Node primaryJudge() {
        Token token = currentToken;
        if (currentToken.type.equals("TK_LEFT_BRACKET")) {
            eat("TK_LEFT_BRACKET");
            AST_Node node = expressionJudge();
            eat("TK_RIGHT_BRACKET");
            return node;
        } else if (currentToken.type.equals("IDENTITY")) {

            eat("IDENTITY");
            if (currentToken.type.equals("TK_LEFT_BRACKET")) {
                String function_name = token.value;
                eat("TK_LEFT_BRACKET");
                List<AST_Node> actual_parameter_nodes = new ArrayList<AST_Node>();
                if (!currentToken.type.equals("TK_RIGHT_BRACKET")){
                    AST_Node node = expressionJudge();
                    actual_parameter_nodes.add(node);
                }
                while (currentToken.type.equals("TK_COMMA")) {
                    eat("TK_COMMA");
                    AST_Node node1 = expressionJudge();
                    actual_parameter_nodes.add(node1);
                }
                eat("TK_RIGHT_BRACKET");

                return new FunctionCall_Node(currentFunctionName, actual_parameter_nodes, token);
            }
            if (currentToken.type.equals("TK_LEFT_SQUARE_BRACKET")){
                Token array_name_token = token;
                eat("TK_LEFT_SQUARE_BRACKET");
                AST_Node index = expressionJudge();
                if (currentToken.type.equals("TK_RIGHT_SQUARE_BRACKET"))
                    eat("TK_RIGHT_SQUARE_BRACKET");
                return new Var_array_item_node(array_name_token,index);
            }
            return new Var_Node(token);
        } else if (currentToken.type.equals("INTEGER_CONST")) {
            eat("INTEGER_CONST");
            return new Num_Node(token);
        } else if (currentToken.type.equals("TK_TRUE")) {
            eat("TK_TRUE");
            return new Num_Node(token);
        } else if (currentToken.type.equals("TK_FALSE")) {
            eat("TK_FALSE");
            return new Num_Node(token);
        } else {
            return null;
        }
    }

    //unary = ("+" | "-") unary | primary
    public AST_Node unaryJudge() {
        Token token = currentToken;
        if (currentToken.type.equals("TK_PLUS")) {
            eat("TK_PLUS");
            return new UnaryOp_Node(token, unaryJudge());
        } else if (currentToken.type.equals("TK_MINUS")) {
            eat("TK_MINUS");
            return new UnaryOp_Node(token, unaryJudge());
        } else {
            return primaryJudge();
        }
    }

    //mul_div = unary ("*" unary | "/" unary)*
    public AST_Node mul_divJudge() {
        AST_Node node = unaryJudge();
        while (currentToken.type.equals("TK_MULTY") || currentToken.type.equals("TK_EXCEPT")) {
            Token token = currentToken;
            if (currentToken.type.equals("TK_MULTY")) {
                eat("TK_MULTY");
                node = new BinaryOp_Node(node, token, unaryJudge());
            } else {
                eat("TK_EXCEPT");
                node = new BinaryOp_Node(node, token, unaryJudge());
            }
        }
        return node;
    }

    //add_sub = mul_div ("+" mul_div | "-" mul_div)*
    public AST_Node add_subJudge() {
        AST_Node node = mul_divJudge();
        while (currentToken.type.equals("TK_PLUS") || currentToken.type.equals("TK_MINUS")) {
            Token token = currentToken;
            if (currentToken.type.equals("TK_PLUS")) {
                eat("TK_PLUS");
                node = new BinaryOp_Node(node, token, mul_divJudge());
            } else {
                eat("TK_MINUS");
                node = new BinaryOp_Node(node, token, mul_divJudge());
            }
        }
        return node;
    }

    //relational = add_sub ("<" add_sub | "<=" add_sub | ">" add_sub | ">=" add_sub)*
    public AST_Node relationalJudge() {
        AST_Node node = add_subJudge();
        while (currentToken.type.equals("TK_LESS_THAN") || currentToken.type.equals("TK_LESS_EQUAL") || currentToken.type.equals("TK_GREAT_THAN") || currentToken.type.equals("TK_GREAT_EQUAL")) {
            Token token = currentToken;
            if (currentToken.type.equals("TK_LESS_THAN")) {
                eat("TK_LESS_THAN");
                node = new BinaryOp_Node(node, token, add_subJudge());
            } else if (currentToken.type.equals("TK_LESS_EQUAL")) {
                eat("TK_LESS_EQUAL");
                node = new BinaryOp_Node(node, token, add_subJudge());
            } else if (currentToken.type.equals("TK_GREAT_THAN")) {
                eat("TK_GREAT_THAN");
                node = new BinaryOp_Node(node, token, add_subJudge());
            } else if (currentToken.type.equals("TK_GREAT_EQUAL")) {
                eat("TK_GREAT_EQUAL");
                node = new BinaryOp_Node(node, token, add_subJudge());
            }
        }
        return node;
    }

    //equality = relational ("==" relational | "! =" relational)*
    public AST_Node equalityJudge() {
        AST_Node node = relationalJudge();
        while (currentToken.type.equals("TK_EQUAL") || currentToken.type.equals("TK_NOT_EQUAL")) {
            Token token = currentToken;
            if (currentToken.type.equals("TK_EQUAL")) {
                eat("TK_EQUAL");
                node = new BinaryOp_Node(node, token, relationalJudge());
            } else if (currentToken.type.equals("TK_NOT_EQUAL")) {
                eat("TK_NOT_EQUAL");
                node = new BinaryOp_Node(node, token, relationalJudge());
            }
        }
        return node;
    }

    //logic = equality ("&&" equality | "||" equality | "!" equality)*
    public AST_Node logicJudge() {
        AST_Node node = equalityJudge();
        while (currentToken.type.equals("TK_AND") || currentToken.type.equals("TK_OR") || currentToken.type.equals("TK_NOT")) {
            Token token = currentToken;
            if (currentToken.type.equals("TK_AND")) {
                eat("TK_AND");
                node = new BinaryOp_Node(node, token, equalityJudge());
            } else if (currentToken.type.equals("TK_OR")) {
                eat("TK_OR");
                node = new BinaryOp_Node(node, token, equalityJudge());
            } else if (currentToken.type.equals("TK_NOT")) {
                eat("TK_NOT");
                node = new BinaryOp_Node(node, token, equalityJudge());
            }
        }
        return node;
    }

    //assign = logic ("=" expression)?
    public AST_Node assignJudge() {
        AST_Node node = logicJudge();
        Token token = currentToken;
        if (currentToken.type.equals("TK_ASSIGN")) {
            eat("TK_ASSIGN");
            node = new Assign_Node(node, token, expressionJudge());
        }
        return node;
    }

    //expression = assign
    public AST_Node expressionJudge() {
        return assignJudge();
    }

    //expression_statement = expression? ";"
    public AST_Node expression_statementJudge() {
        Token token = currentToken;
        AST_Node node = null;
        if (currentToken.type.equals("TK_SEMI")) {
            eat("TK_SEMI");
        } else {
            node = expressionJudge();
            eat("TK_SEMI");
        }
        return node;
    }

    // variable_declaration := type_specification identifier ("," indentifier)* ";"
    //                           | type_specification identifier "[" num "]" ("=" "{" (num)? ("," num)* "}")? ";"
    public List<AST_Node> variable_declarationJudge() {
        Type_Node basictype_node = (Type_Node)type_specificationJudge();
        List<AST_Node> variable_nodes = new ArrayList<>();
        while (!currentToken.type.equals("TK_SEMI")){
            if (currentToken.type.equals("IDENTITY")){
                Token token = currentToken;
                eat("IDENTITY");
                if (currentToken.type.equals("TK_LEFT_SQUARE_BRACKET")){
                    List<String> array_items = new ArrayList<>();
                    eat("TK_LEFT_SQUARE_BRACKET");
                    String array_size = new String();
                    if (currentToken.type.equals("INTEGER_CONST")){
                        array_size = currentToken.value;
                        eat("INTEGER_CONST");
                        if (currentToken.type.equals("TK_RIGHT_SQUARE_BRACKET")){
                            eat("TK_RIGHT_SQUARE_BRACKET");
                        }
                    }
                    if (currentToken.type.equals("TK_ASSIGN")){
                        eat("TK_ASSIGN");
                        if (currentToken.type.equals("TK_LEFT_BRACE")){
                            eat("TK_LEFT_BRACE");
                            while (!currentToken.type.equals("TK_RIGHT_BRACE")){
                                if (currentToken.type.equals("INTEGER_CONST")){
                                    array_items.add(currentToken.value);
                                    eat("INTEGER_CONST");
                                }
                                if (currentToken.type.equals("TK_COMMA")){
                                    eat("TK_COMMA");
                                    if (currentToken.type.equals("INTEGER_CONST")){
                                        array_items.add(currentToken.value);
                                        eat("INTEGER_CONST");
                                    }else {
                                        Error.showError("array item error");
                                    }
                                }
                            }
                            eat("TK_RIGHT_BRACE");
                            Var_Node var_node = new Var_Node(token,new Var_Node_In_Array_Value_J(array_size,array_items));
                            AST_Node node = new VarDecl_Node(basictype_node,var_node);
                            variable_nodes.add(node);
                        }
                    }
                }else{
                    Var_Node var_node = new Var_Node(token);
                    VarDecl_Node node = new VarDecl_Node(basictype_node,var_node);
                    variable_nodes.add(node);
                    if (currentToken.type.equals("TK_COMMA"))
                        eat("TK_COMMA");
                    while (!currentToken.type.equals("TK_SEMI")){
                        if (currentToken.type.equals("IDENTITY")){
                            var_node = new Var_Node(currentToken);
                            node = new VarDecl_Node(basictype_node,var_node);
                            eat("IDENTITY");
                            variable_nodes.add(node);
                            if (currentToken.type.equals("TK_COMMA"))
                                eat("TK_COMMA");
                        }
                    }
                }

            }
        }

        eat("TK_SEMI");
        return variable_nodes;
    }

    //statement = expression_statement
    //                    | "return" expression_statement
    //                    | block
    //                    | "if" "(" expression ")" statement ("else" statement)?
    //                    | "while" "(" expression ")" statement

    public AST_Node statementJudge() {
        Token token = currentToken;
        if (currentToken.type.equals("TK_RETURN")) {
            eat("TK_RETURN");
            return new Return_Node(token,expression_statementJudge(),currentFunctionName);
        } else if (currentToken.type.equals("TK_IF")) {
            AST_Node condition=null,then_statement=null,else_statement=null;
            eat("TK_IF");
            eat("TK_LEFT_BRACKET");
            condition = expressionJudge();
            eat("TK_RIGHT_BRACKET");
            then_statement = statementJudge();
            if (currentToken.type.equals("TK_ELSE")) {
                eat("TK_ELSE");
                else_statement = statementJudge();
            }
            return new If_Node(condition,then_statement,else_statement);
        } else if (currentToken.type.equals("TK_WHILE")) {
            AST_Node condition=null,statement=null;
            eat("TK_WHILE");
            eat("TK_LEFT_BRACKET");
            condition = expressionJudge();
            eat("TK_RIGHT_BRACKET");
            statement = statementJudge();
            return new While_Node(condition,statement);
        } else if (currentToken.type.equals("TK_LEFT_BRACE")) {
            return blockJudge();
        } else {
            return expression_statementJudge();
        }
    }

    //compound_statement = (variable_declaration | statement)*
    public List<AST_Node> compound_statementJudge() {
        List<AST_Node> statement_nodes = new ArrayList<AST_Node>();
        while (!currentToken.type.equals("TK_RIGHT_BRACE") && !currentToken.type.equals("EOF")) {
            if (currentToken.type.equals("TK_INT") || currentToken.type.equals("TK_BOOL")) {
                List<AST_Node> variable_nodes = variable_declarationJudge();
                for (int i=0;i<variable_nodes.size();i+=1){
                    statement_nodes.add(variable_nodes.get(i));
                }
            } else {
                AST_Node node = statementJudge();
                if (node != null)
                    statement_nodes.add(node);
            }
        }
        return statement_nodes;
    }

    //block = "{" compound_statement "}"
    public AST_Node blockJudge() {
        if (currentToken.type.equals("TK_LEFT_BRACE")) {
            Token ltok = currentToken;
            eat("TK_LEFT_BRACE");
            List<AST_Node> statement_nodes = new ArrayList<AST_Node>();
            statement_nodes = compound_statementJudge();
            Token rtok = currentToken;
            eat("TK_RIGHT_BRACE");
            return new Block_Node(ltok,rtok,statement_nodes);
        } else {
            Error.showError("must begin with { ");
            return null;
        }
    }


    //type_specification = "int" | "bool"
    public AST_Node type_specificationJudge() {
        Token token = currentToken;
        if (currentToken.type.equals("TK_INT")) {
            eat("TK_INT");
        } else if (currentToken.type.equals("TK_BOOL")) {
            eat("TK_BOOL");
        } else {
            Error.showError("Type must be int or bool");
        }
        return new Type_Node(token);
    }

    //formal_parameter = type_specification identifier
    public AST_Node formal_parameterJudge() {
        Type_Node type_node = (Type_Node)type_specificationJudge();
        Var_Node parameter_node = new Var_Node(currentToken);
        if (currentToken.type.equals("IDENTITY")) {
            eat("IDENTITY");
            return new FormalParam_Node(type_node,parameter_node);
        } else {
            Error.showError("after type must be identity");
            return null;
        }
    }

    //formal_parameters = formal_parameter ("," formal_parameter)*
    public List<AST_Node> formal_parametersJudge() {
        List<AST_Node> formal_params = new ArrayList<AST_Node>();
        formal_params.add(formal_parameterJudge());
        while (currentToken.type.equals("TK_COMMA")) {
            eat("TK_COMMA");
            formal_params.add(formal_parameterJudge());
        }
        return formal_params;
    }

    //function_definition = type_specification identifier "(" formal_parameters* ")" block
    public AST_Node function_definitionJudge() {
        AST_Node type_node = type_specificationJudge();
        currentFunctionName = currentToken.value;
        if (currentToken.type.equals("IDENTITY")) {
            eat("IDENTITY");
        } else if (currentToken.type.equals("TK_MAIN")) {
            eat("TK_MAIN");
        }

        eat("TK_LEFT_BRACKET");

        List<AST_Node> formal_params = new ArrayList<AST_Node>();
        while (!currentToken.type.equals("TK_RIGHT_BRACKET")) {
            formal_params = formal_parametersJudge();
        }
        eat("TK_RIGHT_BRACKET");
        AST_Node block_node = blockJudge();
        return new FunctionDef_node(type_node,currentFunctionName,formal_params,block_node);
    }

    //program = function_definition*
    public List<AST_Node> program() {
        List<AST_Node> function_definition_nodes = new ArrayList<AST_Node>();
        while (!currentToken.type.equals("EOF")) {
            function_definition_nodes.add(function_definitionJudge());
        }
        if (!currentToken.type.equals("EOF")) Error.showError("unexpected error");
        return function_definition_nodes;
    }

}


abstract class AST_Node {
    public abstract void accept(NodeVisitor visitor);
}

class UnaryOp_Node extends AST_Node {
    Token token;
    Token op;
    AST_Node right;

    public UnaryOp_Node(Token op, AST_Node right) {
        this.token = op;
        this.op = op;
        this.right = right;
    }

    public void accept(NodeVisitor visitor){
        visitor.visit_UnaryOp_Node(this);
    }

}

class If_Node extends AST_Node {
    AST_Node condition;
    AST_Node then_statement;
    AST_Node else_statement;

    public If_Node(AST_Node condition, AST_Node then_statement, AST_Node else_statement) {
        this.condition = condition;
        this.then_statement = then_statement;
        this.else_statement = else_statement;
    }

    public void accept(NodeVisitor visitor){
        visitor.visit_If_Node(this);
    }

}

class While_Node extends AST_Node {
    AST_Node condition, statement;

    public While_Node(AST_Node condition, AST_Node statement) {
        this.condition = condition;
        this.statement = statement;
    }

    public void accept(NodeVisitor visitor){
        visitor.visit_While_Node(this);
    }
}

class Return_Node extends AST_Node {
    AST_Node right;
    Token tok;
    String function_name;

    public Return_Node(Token tok, AST_Node right, String function_name) {
        this.tok = tok;
        this.right = right;
        this.function_name = function_name;
    }

    public void accept(NodeVisitor visitor){
        visitor.visit_Return_Node(this);
    }
}

class Block_Node extends AST_Node {
    Token ltoken, rtoken;
    List<AST_Node> statement_nodes;

    public Block_Node(Token ltoken, Token rtoken, List<AST_Node> statement_nodes) {
        this.ltoken = ltoken;
        this.rtoken = rtoken;
        this.statement_nodes = statement_nodes;
    }
    public void accept(NodeVisitor visitor){
        visitor.visit_Block_Node(this);
    }
}

class BinaryOp_Node extends AST_Node {
    AST_Node left, right;
    Token op;
    Token token;

    public BinaryOp_Node(AST_Node left, Token op, AST_Node right) {
        this.left = left;
        this.op = this.token = op;
        this.right = right;
    }

    public void accept(NodeVisitor visitor){
        visitor.visit_BinaryOp_Node(this);
    }
}

class Assign_Node extends AST_Node {
    AST_Node left, right;
    Token token, op;

    public Assign_Node(AST_Node left, Token op, AST_Node right) {
        this.left = left;
        this.right = right;
        this.token = op;
        this.op = op;
    }

    public void accept(NodeVisitor visitor){
        visitor.visit_Assign_Node(this);
    }
}

class FunctionCall_Node extends AST_Node {
    String function_name;
    List<AST_Node> actual_parameter_node;
    Token token;

    public FunctionCall_Node(String function_name, List<AST_Node> actual_parameter_node, Token token) {
        this.function_name = function_name;
        this.actual_parameter_node = actual_parameter_node;
        this.token = token;
    }

    public void accept(NodeVisitor visitor){
        visitor.visit_FunctionCall_Node(this);
    }
}

class Num_Node extends AST_Node {
    Token token;
    String value;

    public Num_Node(Token token) {
        this.token = token;
        this.value = token.value;
    }

    public void accept(NodeVisitor visitor){
        visitor.visit_Num_Node(this);
    }
}

class Var_Node_In_Array_Value_J{
    String size;
    List<String> value;

    public Var_Node_In_Array_Value_J(String size, List<String> value) {
        this.size = size;
        this.value = value;
    }
}

class Var_Node extends AST_Node {
    Token token;
    String name;

    Var_Node_In_Array_Value_J value_array = null;
    String array = null;

    Symbol symbol = null;

    public Var_Node(Token token) {
        this.token = token;
        this.name = token.value;
    }

    public Var_Node(Token token,Var_Node_In_Array_Value_J j) {
        this.token = token;
        this.name = token.value;
        this.value_array = j;
    }

    public void accept(NodeVisitor visitor){
        visitor.visit_Var_Node(this);
    }
}

class Var_array_item_node extends AST_Node{
    Token token;
    AST_Node index;
    Symbol symbol;
    String array = "Yes";

    public Var_array_item_node(Token token, AST_Node index) {
        this.token = token;
        this.index = index;
    }

    public void accept(NodeVisitor visitor){
        visitor.visit_Var_array_item_Node(this);
    }
}

class Type_Node extends AST_Node {
    Token token;
    String value;

    public Type_Node(Token token) {
        this.token = token;
        this.value = token.value;
    }

    public void accept(NodeVisitor visitor){
        visitor.visit_Type_Node(this);
    }
}

class VarDecl_Node extends AST_Node {
    Type_Node type_node;
    Var_Node var_node;

    public VarDecl_Node(Type_Node type_node, Var_Node var_node) {
        this.type_node = type_node;
        this.var_node = var_node;
    }

    public void accept(NodeVisitor visitor){
        visitor.visit_VarDecl_Node(this);
    }
}

class FormalParam_Node extends AST_Node {
    Type_Node type_node;
    Var_Node parameter_node;
    Symbol parameter_symbol = null;

    public FormalParam_Node(Type_Node type_node, Var_Node parameter_node) {
        this.type_node = type_node;
        this.parameter_node = parameter_node;
    }

    public void accept(NodeVisitor visitor){
        visitor.visit_FormalParam_Node(this);
    }
}

class FunctionDef_node extends AST_Node {
    AST_Node type_node;
    String function_name;
    List<AST_Node> formal_parameters;
    AST_Node block_node;
    int offset = 0;

    public FunctionDef_node(AST_Node type_node, String function_name, List<AST_Node> formal_parameters, AST_Node block_node) {
        this.type_node = type_node;
        this.function_name = function_name;
        this.formal_parameters = formal_parameters;
        this.block_node = block_node;
    }

    public void accept(NodeVisitor visitor){
        visitor.visit_FunctionDef_Node(this);
    }
}






























