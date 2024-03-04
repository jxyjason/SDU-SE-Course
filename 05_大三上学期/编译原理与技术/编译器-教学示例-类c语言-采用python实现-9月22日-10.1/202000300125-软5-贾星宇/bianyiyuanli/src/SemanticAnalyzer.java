import com.sun.corba.se.spi.orbutil.fsm.FSM;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

class Offset {
    public static int sum = -999;
}

class Count {
    public static int i = 0;
}


public class SemanticAnalyzer extends NodeVisitor {
    public static boolean _SHOULD_LOG_SCOPE = false;
    ScopedSymbolTable current_scope;

    public SemanticAnalyzer() {
        current_scope = null;
        ScopedSymbolTable global_scope = new ScopedSymbolTable("global", 0, current_scope);
        current_scope = global_scope;
    }

    public void log(String msg) {
        if (_SHOULD_LOG_SCOPE)
            System.out.println(msg);
    }

    public void visit_UnaryOp_Node(UnaryOp_Node node) {
        node.right.accept(this);
    }

    public void visit_Return_Node(Return_Node node) {
        node.right.accept(this);
    }

    public void visit_BinaryOp_Node(BinaryOp_Node node) {
        node.left.accept(this);
        node.right.accept(this);
    }

    public void visit_Assign_Node(Assign_Node node) {
        node.left.accept(this);
        node.right.accept(this);
    }

    public void visit_If_Node(If_Node node) {
        node.condition.accept(this);
        if (node.then_statement != null)
            node.then_statement.accept(this);
        if (node.else_statement != null)
            node.else_statement.accept(this);
    }

    public void visit_While_Node(While_Node node) {
        node.condition.accept(this);
        if (node.statement != null)
            node.statement.accept(this);
    }

    public void visit_Block_Node(Block_Node node) {
        String block_name = this.current_scope.scope_name + " block" + (current_scope.scope_level + 1);
        log("ENTER scope:" + block_name);
        ScopedSymbolTable block_scope = new ScopedSymbolTable(block_name, current_scope.scope_level + 1, current_scope);
        current_scope = block_scope;
        for (int i = 0; i < node.statement_nodes.size(); i++) {
            node.statement_nodes.get(i).accept(this);
        }
        current_scope = current_scope.enclosing_scope;
        log("LEAVE scope:" + block_name);
    }

    public void visit_Num_Node(Num_Node node) {

    }

    public void visit_Var_Node(Var_Node node) {
        String var_name = node.name;
        Symbol var_symbol = current_scope.lookup(var_name);
        if (var_symbol == null) {
            Error.showError("semantic error, var \"" + var_name + " \" not declarde!");
        } else {
            node.symbol = var_symbol;
        }
    }

    public void visit_Type_Node(Type_Node node) {

    }

    public void visit_VarDecl_Node(VarDecl_Node node) {
        String var_name = node.var_node.name;
        String var_type = node.type_node.value;

        if (node.var_node.value_array != null) {
            Offset.sum += 8 * Integer.parseInt(node.var_node.value_array.size);
            int var_offset = -Offset.sum;
            Symbol var_symbol = new Var_Symbol(var_name, var_type, var_offset);
            node.var_node.symbol = var_symbol;
            current_scope.insert(var_symbol);
        } else {
            Offset.sum += 8;
            int var_offset = -Offset.sum;
            Symbol var_symbol = new Var_Symbol(var_name, var_type, var_offset);
            this.current_scope.insert(var_symbol);
        }


    }

    public void visit_FormalParam_Node(FormalParam_Node node) {
        String parameter_name = node.parameter_node.name;
        String parameter_type = node.type_node.value;
        Offset.sum += 8;
        int parameter_offset = -Offset.sum;
        Symbol parameter_symbol = new Parameter_Symbol(parameter_name, parameter_type, parameter_offset);
        current_scope.insert(parameter_symbol);
        node.parameter_symbol = parameter_symbol;
    }

    public void visit_FunctionDef_Node(FunctionDef_node node) {
        Offset.sum = 0;
        String function_name = node.function_name;
        Function_Symbol function_symbol = new Function_Symbol(function_name);
        current_scope.insert(function_symbol);

        ScopedSymbolTable function_scope = new ScopedSymbolTable(function_name, current_scope.scope_level + 1,
                current_scope);
        current_scope = function_scope;

        for (int i = 0; i < node.formal_parameters.size(); i++) {
            node.formal_parameters.get(i).accept(this);
        }

        node.block_node.accept(this);
        node.offset = Offset.sum;
        current_scope = current_scope.enclosing_scope;
        function_symbol.block_ast = node.block_node;
    }

    public void visit_FunctionCall_Node(FunctionCall_Node node) {

    }

    public void visit_Var_array_item_Node(Var_array_item_node node) {
        String array_name = node.token.value;
        Symbol array_symbol = current_scope.lookup(array_name);
        if (array_symbol == null) {
            Error.showError("semantic error, array variable not declared");
        } else {
            node.symbol = array_symbol;
            node.index.accept(this);
        }
    }

    public void semantic_analyze(List<AST_Node> tree) {
        for (AST_Node ast_node : tree) {
            if (ast_node != null)
                ast_node.accept(this);
        }
    }


}


//symbol
class Symbol {
    String name;
    String type = null;

    public Symbol(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Symbol(String name) {
        this.name = name;
    }
}

class Function_Symbol extends Symbol {
    List<Var_Symbol> formal_parameters = new ArrayList<Var_Symbol>();
    AST_Node block_ast = null;

    public Function_Symbol(String name, List<Var_Symbol> formal_params) {
        super(name);
        this.formal_parameters = formal_params;
    }

    public Function_Symbol(String name) {
        super(name);
    }
}

class Var_Symbol extends Symbol {
    int offset;
    Symbol symbol = null;

    public Var_Symbol(String name, String type, int offset) {
        super(name, type);
        this.offset = offset;
    }
}


class Parameter_Symbol extends Symbol {
    int offset;

    public Parameter_Symbol(String name, String type, int offset) {
        super(name, type);
        this.offset = offset;
    }
}


//symbol table
class ScopedSymbolTable {
    Dictionary<String, Symbol> _symbols = new Hashtable<>();
    String scope_name;
    int scope_level;
    ScopedSymbolTable enclosing_scope = null;

    public ScopedSymbolTable(String scope_name, int scope_level, ScopedSymbolTable enclosing_scope) {
        this.scope_name = scope_name;
        this.scope_level = scope_level;
        this.enclosing_scope = enclosing_scope;
    }

    public ScopedSymbolTable(String scope_name, int scope_level) {
        this.scope_name = scope_name;
        this.scope_level = scope_level;
    }

    public void insert(Symbol symbol) {
        this._symbols.put(symbol.name, symbol);
    }

    public Symbol lookup(String name) {
        boolean current_scope_only = false;
        Symbol symbol = this._symbols.get(name);
        if (symbol != null)
            return symbol;
        if (current_scope_only)
            return null;
        if (enclosing_scope != null)
            return this.enclosing_scope.lookup(name);
        return null;
    }

    public Symbol lookup(String name, boolean current_scope_only) {
        Symbol symbol = this._symbols.get(name);
        if (symbol != null)
            return symbol;
        if (current_scope_only)
            return null;
        if (enclosing_scope != null)
            return this.enclosing_scope.lookup(name);
        return null;
    }

}































