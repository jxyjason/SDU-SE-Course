# import argparse
import string
import sys
from enum import Enum


_SHOULD_LOG_SCOPE = False  # see '--scope' command line option
parameter_registers=['rdi', 'rsi', 'rdx', 'rcx', 'r8', 'r9']

# global variable "Offset.sum" used for code-generatioin
class Offset:
    sum = -999

# global variable "Count.i" used for "if" statement
class Count():
    i = 0

# The input source file can be opened in any time,
# to show error
class Inputfile():
    buffer = []
    name = ''

class ErrorCode(Enum):
    UNEXPECTED_TOKEN = 'Unexpected token'


class Error(Exception):
    lineno = 1
    column = 1

    @classmethod
    def show_error_at(self, lineno, pos, error_list='some error'):
        line = Inputfile.buffer[lineno - 1]
        print(f"{Inputfile.name}:line {lineno}  --->", file=sys.stderr)
        print(f"{line.rstrip()}", file=sys.stderr)
        print("%{pos}s".format(pos=pos) % "^", end=' ', file=sys.stderr)
        print(f"{error_list}", file=sys.stderr)
        sys.exit(1)


class LexerError(Error):
    pass


class ParserError(Error):
    pass


class SemanticError(Error):
    pass




##################################################################################################
#
#  LEXER
#
##################################################################################################

class TokenType(Enum):
    # single-character token types
    TK_PLUS          = '+'
    TK_MINUS         = '-'
    TK_MUL           = '*'
    TK_DIV           = '/'
    TK_NEG           = 'unary-'
    TK_LT            = '<'
    TK_GT            = '>'
    TK_EQ            = '=='
    TK_NE            = '!='
    TK_GE            = '>='
    TK_LE            = '<='
    TK_LPAREN        = '('
    TK_RPAREN        = ')'
    TK_LBRACE        = '{'
    TK_RBRACE        = '}'
    TK_LBRACK        = '['
    TK_RBRACK        = ']'
    TK_COMMA         = ','
    TK_SEMICOLON     = ';'
    # block of reserved words
    TK_RETURN        = 'return'
    TK_INT           = 'int'
    TK_IF            = 'if'
    TK_THEN          = 'then'
    TK_ELSE          = 'else'
    # misc
    TK_IDENT         = 'IDENT'
    TK_INTEGER_CONST = 'INTEGER_CONST'
    TK_ASSIGN        = '='
    TK_EOF           = 'EOF'

    @classmethod
    def members(cls):
        return cls._value2member_map_

class Token:
    def __init__(self, type, value, lineno=None, column=None, width=None):
        self.type = type
        self.value = value
        self.lineno = lineno
        self.column = column
        self.width = width


class Lexer:
    def __init__(self, text):
        # client string input, e.g. "4 + 2 * 3 - 6 / 2"
        self.text = text
        # self.pos is an index into self.text
        self.pos = 0
        self.current_char = self.text[self.pos]
        # # list of tokens
        self.tokens = []

    def advance(self):
        """Advance the `pos` pointer and set the `current_char` variable."""
        if self.current_char == '\n':
            Error.lineno += 1
            Error.column = 0

        self.pos += 1
        if self.pos > len(self.text) - 1:
            self.current_char = None  # Indicates end of input
        else:
            self.current_char = self.text[self.pos]
            Error.column += 1


    def skip_whitespace(self):
        while self.current_char is not None and self.current_char.isspace():
            self.advance()

    # Returns true if c is valid as the first character of an identifier.
    def _is_ident1(self, c):
        return ('a' <= c and c <= 'z') or ('A' <= c and c <= 'Z') or c == '_'
    # Returns true if c is valid as a non-first character of an identifier.
    def _is_ident2(self, c):
        return self._is_ident1(c) or ('0' <= c and c <= '9')

    def number(self):
        """Return a (multidigit) integer or float consumed from the input."""

        # Create a new token
        token = Token(type=None, value=None)
        old_column = Error.column

        result = ''
        while self.current_char is not None and self.current_char.isdigit():
            result += self.current_char
            self.advance()

        token.type = TokenType.TK_INTEGER_CONST
        token.value = int(result)
        token.lineno = Error.lineno
        token.column = Error.column
        token.width = Error.column - old_column
        return token


    # Read a punctuator token from p and returns
    def read_punct(self, p):
        if p.startswith("==", self.pos) or \
            p.startswith("!=", self.pos) or \
            p.startswith("<=", self.pos) or \
            p.startswith(">=", self.pos):
            return 2
        return self.current_char in string.punctuation

    def get_next_token(self):
        """Lexical analyzer (also known as scanner or tokenizer)

        This method is responsible for breaking a sentence
        apart into tokens. One token at a time.
        """
        while self.current_char is not None:
            # Skip whitespace characters
            if self.current_char.isspace():
                self.skip_whitespace()
                continue

            # Numeric literal
            if self.current_char.isdigit():
                return self.number()

            # Identifier(Beginning with a - z or A - Z or _, not digits, not punctutors other than _)
            if (self._is_ident1(self.current_char)):
                # Create a new token
                token = Token(type=None, value=None)
                old_column = Error.column
                result = self.current_char
                self.advance()
                while (self._is_ident2(self.current_char)):
                    result += self.current_char
                    self.advance()
                # if keyword, not common identifier
                if result in TokenType.members():
                    # get enum member by value, e.g.
                    token.type = TokenType(result)
                    token.value = token.type.value  # e.g. 'return', etc
                    token.lineno = Error.lineno
                    token.column = Error.column
                    token.width = Error.column - old_column
                    return token
                # if not keyword, but identifier
                else:
                    token.type = TokenType.TK_IDENT
                    token.value = result
                    token.lineno = Error.lineno
                    token.column = Error.column
                    token.width = Error.column - old_column
                    return token

            # Punctuators
            # two-characters punctuator
            if self.read_punct(self.text) == 2:
                # Create a new token
                token = Token(type=None, value=None)
                # create a token with two-characters lexeme as its value
                token.type = TokenType(self.text[self.pos:self.pos+2])
                token.value=token_type.value,  # e.g. '!=', '==', etc
                token.lineno = Error.lineno
                token.column = Error.column
                token.width = 2
                self.advance()
                self.advance()
                return token
            # single-character punctuator
            elif self.current_char in TokenType.members():
                # Create a new token
                token = Token(type=None, value=None)
                # get enum member by value, e.g.
                # TokenType('+') --> TokenType.PLUS
                 # create a token with a single-character lexeme as its value
                token.type = TokenType(self.current_char)
                token.value=token.type.value,  # e.g. '+', '-', etc
                token.lineno = Error.lineno
                token.column = Error.column
                token.width = 1
                self.advance()
                return token
            # no enum member with value equal to self.current_char
            else:
                Error.show_error_at(Error.lineno, Error.column, "invalid token")

        # EOF (end-of-file) token indicates that there is no more
        # input left for lexical analysis
        return Token(type=TokenType.TK_EOF, value=None)


    def gather_all_tokens(self):
        token = self.get_next_token()
        self.tokens.append(token)
        while token.type != TokenType.TK_EOF:
            token = self.get_next_token()
            self.tokens.append(token)
        return self.tokens

##################################################################################################
#
#   AST_Node type:
#
##################################################################################################

class AST_Node:
    pass

class UnaryOp_Node(AST_Node):
    def __init__(self, op, right):
        self.token = self.op = op
        self.right = right

class If_Node(AST_Node):
    def __init__(self, condition, then_statement, else_statement):
        self.condition = condition
        self.then_statement = then_statement
        self.else_statement = else_statement

class Return_Node(AST_Node):
    def __init__(self, tok, right, function_name):
        self.token = tok
        self.right = right
        self.function_name = function_name

class Block_Node(AST_Node):
    def __init__(self, ltok, rtok, statement_nodes):
        self.ltoken = ltok
        self.rtoken = rtok
        self.statement_nodes = statement_nodes


class BinaryOp_Node(AST_Node):
    def __init__(self, left, op, right):
        self.left = left
        self.token = self.op = op
        self.right = right


class Assign_Node(AST_Node):
    def __init__(self, left, op, right):
        self.left = left
        self.token = self.op = op
        self.right = right

class FunctionCall_Node(AST_Node):
    def __init__(self, function_name, actual_parameter_nodes, token):
        self.function_name = function_name
        self.actual_parameter_nodes = actual_parameter_nodes
        self.token = token


class Num_Node(AST_Node):
    def __init__(self, token):
        self.token = token
        self.value = token.value


class Var_Node(AST_Node):
    """The Var node is constructed out of ID token."""
    def __init__(self, token):
        self.token = token
        self.value = token.value
        self.symbol = None

class Type(AST_Node):
    def __init__(self, token):
        self.token = token
        self.value = token.value

class VarDecl_Node(AST_Node):
    def __init__(self, type_node, var_node):
        self.type_node = type_node
        self.var_node = var_node


class FormalParam_Node(AST_Node):
    def __init__(self, type_node, parameter_node):
        self.type_node = type_node
        self.parameter_node = parameter_node
        self.parameter_symbol = None


class FunctionDef_Node(AST_Node):
    def __init__(self, type_node, function_name, formal_parameters, block_node):
        self.type_node = type_node
        self.function_name = function_name
        self.formal_parameters = formal_parameters
        self.block_node = block_node
        self.offset = 0

##################################################################################################
#
#  AST visitors (walkers)
#
##################################################################################################

class NodeVisitor:
    def visit(self, node):
        method_name = 'visit_' + type(node).__name__
        visitor = getattr(self, method_name, self.generic_visit)
        return visitor(node)

    def generic_visit(self, node):
        raise Exception('No visit_{} method'.format(type(node).__name__))


##################################################################################################
#
#  PARSER
#
##################################################################################################

class Parser:
    def __init__(self, lexer):
        self.lexer = lexer
        # set current token to the first token taken from the input
        self.current_token = self.get_next_token()
        self.current_function_name = ''

    def get_next_token(self):
        return self.lexer.get_next_token()

    def error(self, error_code, token):
        raise ParserError(
            error_code=error_code,
            token=token,
            message=f'{error_code.value} -> {token}',
        )

    def eat(self, token_type):
        # compare the current token type with the passed token
        # type and if they match then "eat" the current token
        # and assign the next token to the self.current_token,
        # otherwise raise an exception.
        if self.current_token.type == token_type:
            self.current_token = self.get_next_token()
        else:
            self.error(
                error_code=ErrorCode.UNEXPECTED_TOKEN,
                token=self.current_token,
            )

    # primary = "(" expr ")" | identifier args? | num
    # args = "(" (assign ("," assign)*)? ")"
    def primary(self):
        token = self.current_token

        # "(" expr ")"
        if token.type == TokenType.TK_LPAREN:
            self.eat(TokenType.TK_LPAREN)
            node = self.expression()
            self.eat(TokenType.TK_RPAREN)
            return node

        # identifier
        if token.type == TokenType.TK_IDENT:
            self.eat(TokenType.TK_IDENT)
            # Function call
            if self.current_token.type == TokenType.TK_LPAREN:
                function_name = token.value
                self.eat(TokenType.TK_LPAREN)
                actual_parameter_nodes = []
                if self.current_token.type != TokenType.TK_RPAREN:
                    node = self.assign()
                    actual_parameter_nodes.append(node)

                while self.current_token.type == TokenType.TK_COMMA:
                    self.eat(TokenType.TK_COMMA)
                    node = self.assign()
                    actual_parameter_nodes.append(node)
                self.eat(TokenType.TK_RPAREN)
                node = FunctionCall_Node(
                        function_name = function_name,
                        actual_parameter_nodes = actual_parameter_nodes,
                        token = token)
                return node
            # Variable
            return Var_Node(token)

        # num
        if token.type == TokenType.TK_INTEGER_CONST:
            self.eat(TokenType.TK_INTEGER_CONST)
            return Num_Node(token)

    #unary = ("+" | "-") unary
    #        | primary
    def unary(self):
        token = self.current_token
        if token.type == TokenType.TK_PLUS:
            self.eat(TokenType.TK_PLUS)
            return UnaryOp_Node(op=token, right=self.unary())
        elif token.type == TokenType.TK_MINUS:
            self.eat(TokenType.TK_MINUS)
            return UnaryOp_Node(op=token, right=self.unary())
        else:
            return self.primary()

    # mul_div = unary ("*" unary | "/" unary)*
    def mul_div(self):
        node = self.unary()
        while True:
            token = self.current_token
            if self.current_token.type == TokenType.TK_MUL:
                self.eat(TokenType.TK_MUL)
                node = BinaryOp_Node(left=node, op=token, right=self.unary())
                continue
            elif self.current_token.type == TokenType.TK_DIV:
                self.eat(TokenType.TK_DIV)
                node = BinaryOp_Node(left=node, op=token, right=self.unary())
                continue
            return node

    # add-sub = mul_div ("+" mul_div | "-" mul_div)*
    def add_sub(self):
        node = self.mul_div()
        while True:
            token = self.current_token
            if self.current_token.type == TokenType.TK_PLUS:
                self.eat(TokenType.TK_PLUS)
                node = BinaryOp_Node(left=node, op=token, right=self.mul_div())
                continue
            elif self.current_token.type == TokenType.TK_MINUS:
                self.eat(TokenType.TK_MINUS)
                node = BinaryOp_Node(left=node, op=token, right=self.mul_div())
                continue
            return node

    # relational = add_sub ("<" add_sub | "<=" add_sub | ">" add_sub | ">=" add_sub)*
    def relational(self):
        node = self.add_sub()
        while True:
            token = self.current_token
            if self.current_token.type == TokenType.TK_LT:
                self.eat(TokenType.TK_LT)
                node = BinaryOp_Node(left=node, op=token, right=self.add_sub())
                continue
            elif self.current_token.type == TokenType.TK_LE:
                self.eat(TokenType.TK_LE)
                node = BinaryOp_Node(left=node, op=token, right=self.add_sub())
                continue
            elif self.current_token.type == TokenType.TK_GT:
                self.eat(TokenType.TK_GT)
                node = BinaryOp_Node(left=node, op=token, right=self.add_sub())
                continue
            elif self.current_token.type == TokenType.TK_GE:
                self.eat(TokenType.TK_GE)
                node = BinaryOp_Node(left=node, op=token, right=self.add_sub())
                continue
            return node


    # equality = relational ("==" relational | "! =" relational)*
    def equality(self):
        node = self.relational()
        while True:
            token = self.current_token
            if self.current_token.type == TokenType.TK_EQ:
                self.eat(TokenType.TK_EQ)
                node = BinaryOp_Node(left=node, op=token, right=self.relational())
                continue
            elif self.current_token.type == TokenType.TK_NE:
                self.eat(TokenType.TK_NE)
                node = BinaryOp_Node(left=node, op=token, right=self.relational())
                continue
            return node

    # assign = equality ("=" assign)?
    def assign(self):
        node = self.equality()
        token = self.current_token
        if token.type == TokenType.TK_ASSIGN:
            self.eat(TokenType.TK_ASSIGN)
            node = Assign_Node(left=node, op=token, right = self.assign())
        return node

    # expression = assign
    def expression(self):
        node = self.assign()
        return node

    # expression-statement = expression? ";"
    def expression_statement(self):
        token = self.current_token
        node = None
        if token.type == TokenType.TK_SEMICOLON:
            self.eat(TokenType.TK_SEMICOLON)
        else:
            node = self.expression()
            if self.current_token.type == TokenType.TK_SEMICOLON:
                self.eat(TokenType.TK_SEMICOLON)
            else:
                Error.show_error_at(token.lineno, token.column-token.width+1, "expect \";\"")
        return node


    # statement = expression-statement
    #             | "return" expression-statement
    #             | block
    #             | "if" "(" expression ")" statement ("else" statement)?
    def statement(self):
        token = self.current_token
        # "return" expression-statement
        if token.type == TokenType.TK_RETURN:
            self.eat(TokenType.TK_RETURN)
            node = Return_Node(tok=token, right = self.expression_statement(),  \
                                  function_name = self.current_function_name)
            return node
        # block
        elif token.type == TokenType.TK_LBRACE:
            return self.block()
        # "if" "(" expression ")" statement ("else" statement)?
        elif token.type == TokenType.TK_IF:
            condition = None
            then_statement = None
            else_statement = None
            self.eat(TokenType.TK_IF)
            if self.current_token.type == TokenType.TK_LPAREN:
                self.eat(TokenType.TK_LPAREN)
                condition = self.expression()
                self.eat(TokenType.TK_RPAREN)
                if self.current_token.type == TokenType.TK_THEN:
                    self.eat(TokenType.TK_THEN)
                    then_statement = self.statement()
                    if self.current_token.type == TokenType.TK_ELSE:
                        self.eat(TokenType.TK_ELSE)
                        else_statement = self.statement()
            return If_Node(condition, then_statement, else_statement)
        else:
            # expression-statement
            return self.expression_statement()

    # type_specification = int  //TODO: REAL
    def type_specification(self):
        token = self.current_token
        if self.current_token.type == TokenType.TK_INT:
            self.eat(TokenType.TK_INT)
        # elif self.current_token.type == TokenType.REAL:
        #     self.eat(TokenType.REAL)
        node = Type(token)
        return node

    # variable_declaration = type_specification (indentifier ("=" expr)? ("," indentifier ("=" expr)?)*)? ";"
    def variable_declaration(self):
        type_node = self.type_specification()
        variable_nodes = []
        while self.current_token.type != TokenType.TK_SEMICOLON:
            if self.current_token.type == TokenType.TK_IDENT:
                var_node = Var_Node(self.current_token)
                node = VarDecl_Node(type_node, var_node)
                self.eat(TokenType.TK_IDENT)
                variable_nodes.append(node)
                if self.current_token.type == TokenType.TK_COMMA:
                    self.eat(TokenType.TK_COMMA)

        self.eat(TokenType.TK_SEMICOLON)
        return variable_nodes


    # compound_statement = (variable_declaration | statement)*
    def compound_statement(self):
        statement_nodes = []
        while self.current_token.type != TokenType.TK_RBRACE and \
                self.current_token.type != TokenType.TK_EOF:
            if self.current_token.type == TokenType.TK_INT:
                variable_nodes = self.variable_declaration()
                for eachnode in variable_nodes:
                    statement_nodes.append(eachnode)
            else:
                node = self.statement()
                if node is not None: # abandon "  ;", i.e., null statement
                    statement_nodes.append(node)
        return statement_nodes

    # block = "{" compound_statement "}"
    def block(self):
        if self.current_token.type == TokenType.TK_LBRACE:
            ltok = self.current_token  # "{"
            self.eat(TokenType.TK_LBRACE)
            statement_nodes = self.compound_statement()
            rtok = self.current_token  # "}"
            self.eat(TokenType.TK_RBRACE)
            return Block_Node(ltok, rtok, statement_nodes)

    # formal_parameter = type_specification identifier
    def formal_parameter(self):
        type_node = self.type_specification()
        parameter_node = Var_Node(self.current_token)
        self.eat(TokenType.TK_IDENT)
        return FormalParam_Node(type_node, parameter_node)


    # formal_parameters = formal_parameter (, formal_parameter)*
    def formal_parameters(self):
        formal_params = []
        formal_params.append(self.formal_parameter())
        while self.current_token.type != TokenType.TK_RPAREN:
            if self.current_token.type == TokenType.TK_COMMA:
                self.eat(TokenType.TK_COMMA)
                formal_params.append(self.formal_parameter())
            else:
                print(f"parameter list error")
                exit(1)
        return formal_params

    # function_definition= type_specification identifier "(" formal_parameters? ")" block
    def function_definition(self):
        type_node = self.type_specification()
        function_name = self.current_token.value
        self.eat(TokenType.TK_IDENT)

        if self.current_token.type == TokenType.TK_LPAREN:
            self.eat(TokenType.TK_LPAREN)
            formal_params = []
            if self.current_token.type != TokenType.TK_RPAREN:
                formal_params = self.formal_parameters()
            self.eat(TokenType.TK_RPAREN)

        self.current_function_name = function_name
        if self.current_token.type == TokenType.TK_LBRACE:
            block_node = self.block()
        else:
            Error.show_error_at(token.lineno, self.current_token.column-self.current_token.width, f"expect \"{TokenType.TK_LBRACE.value}\"")

        return FunctionDef_Node(type_node, function_name, formal_params, block_node)


    # program = function_definition*
    def parse(self):
        """
        program = function_definition*
        function_definition = type_specification identifier "(" formal_parameters? ")" block
        formal_parameters = formal_parameter ("," formal_parameter)*
        formal_parameter = type_specification identifier
        type_specification = "int"
        block = "{" compound_statement "}"
        compound_statement = (variable_declaration | statement)*
        statement = expression-statement
                    | "return" expression-statement
                    | block
                    | "if" "(" expression ")" statement ("else" statement)?
        variable_declaration = type_specification (identifier ("=" expr)? ("," identifier ("=" expr)?)*)? ";"
        expression-statement = expression? ";"
        expression = assign
        assign = equality ("=" assign)?
        equality = relational ("==" relational | "! =" relational)*
        relational = add_sub ("<" add_sub | "<=" add_sub | ">" add_sub | ">=" add_sub)*
        add_sub = mul_div ("+" mul_div | "-" mul_div)*
        mul_div = unary ("*" unary | "/" unary)*
        unary = ("+" | "-") primary | primary
        primary = "(" expr ")" | identifier args?| num
        args = "(" (assign ("," assign)*)? ")"
        """

        function_definition_nodes = []
        while self.current_token.type != TokenType.TK_EOF:
            node = self.function_definition()
            function_definition_nodes.append(node)
        if self.current_token.type != TokenType.TK_EOF:
            self.error(
                error_code=ErrorCode.UNEXPECTED_TOKEN,
                token=self.current_token
            )
        return function_definition_nodes



##################################################################################################
#
#  SYMBOLS, TABLES, SEMANTIC ANALYSIS
#
##################################################################################################

class Symbol:
    def __init__(self, name, type=None):
        self.name = name
        self.type = type

class Function_Symbol(Symbol):
    def __init__(self, name, formal_params=None):
        super().__init__(name)
        # a list of VarSymbol objects
        self.formal_parameters = [] if formal_params is None else formal_params
        # a reference to procedure's body (AST sub-tree)
        self.block_ast = None


class Var_Symbol(Symbol):
    def __init__(self, var_name, var_type, var_offset):
        self.name = var_name           # variable name
        self.type = var_type
        self.offset = var_offset       # offset from RBP
        self.symbol = None

class Parameter_Symbol(Symbol):
    def __init__(self, parameter_name, parameter_type, parameter_offset):
        self.name = parameter_name           # parameter name
        self.type = parameter_type
        self.offset = parameter_offset       # offset from RBP

class ScopedSymbolTable:
    def __init__(self, scope_name, scope_level, enclosing_scope=None):
        self._symbols = {}
        self.scope_name = scope_name
        self.scope_level = scope_level
        self.enclosing_scope = enclosing_scope

    def insert(self, symbol):
        self._symbols[symbol.name] = symbol

    def lookup(self, name, current_scope_only=False):
        # self.log(f'Lookup: {name}. (Scope name: {self.scope_name})')
        # 'symbol' is either an instance of the Symbol class or None
        symbol = self._symbols.get(name)

        if symbol is not None:
            return symbol

        if current_scope_only:
            return None

        # recursively go up the chain and lookup the name
        if self.enclosing_scope is not None:
            return self.enclosing_scope.lookup(name)


##################################################################################################
#
#  SEMANTIC-ANALYZER
#
##################################################################################################

class SemanticAnalyzer(NodeVisitor):
    def __init__(self):
        self.current_scope = None
        global_scope = ScopedSymbolTable(
            scope_name='global',
            # global scope, level is 0,
            # in which are global variables, and functions
            scope_level=0,
            enclosing_scope=self.current_scope,  # None
        )
        self.current_scope = global_scope


    def log(self, msg):
        if _SHOULD_LOG_SCOPE:
            print(msg)



    def visit_UnaryOp_Node(self, node):
        pass

    def visit_Return_Node(self, node):
        self.visit(node.right)

    def visit_BinaryOp_Node(self, node):
        self.visit(node.left)
        self.visit(node.right)

    def visit_Assign_Node(self, node):
        # make sure the left side of assign is a varible
        if node.left.token.type != TokenType.TK_IDENT:
            print(f"the left side of assign is not a variable", file=sys.stderr)
        self.visit(node.left)
        self.visit(node.right)

    def visit_If_Node(self, node):
        self.visit(node.condition)
        if node.then_statement is not None:
            self.visit(node.then_statement)
        if node.else_statement is not None:
            self.visit(node.else_statement)

    def visit_Block_Node(self, node):
        block_name= self.current_scope.scope_name + f' block' + \
                                   f"{self.current_scope.scope_level + 1}"
        self.log(f'ENTER scope: {block_name}')
        block_scope = ScopedSymbolTable(
            scope_name= self.current_scope.scope_name + f' block' + \
                                   f"{self.current_scope.scope_level + 1}",
            scope_level=self.current_scope.scope_level + 1,
            enclosing_scope=self.current_scope
        )
        self.current_scope = block_scope
        for eachnode in node.statement_nodes:
            self.visit(eachnode)

        self.current_scope = self.current_scope.enclosing_scope
        self.log(f'LEAVE scope: {block_name}')

    def visit_Num_Node(self, node):
        pass

    def visit_Var_Node(self, node):
        var_name = node.value
        var_symbol = self.current_scope.lookup(var_name)
        if var_symbol is None:
            print(f"semantic error, var not declared", file=sys.stderr)
            sys.exit(1)
        else:
            node.symbol = var_symbol

    def visit_VarDecl_Node(self, node):
        var_name = node.var_node.value
        var_type = node.type_node.value
        Offset.sum += 8
        var_offset = -Offset.sum
        var_symbol = Var_Symbol(var_name, var_type, var_offset)
        self.current_scope.insert(var_symbol)


    def visit_FormalParam_Node(self, node):
        parameter_name = node.parameter_node.value
        parameter_type = node.type_node.value
        Offset.sum += 8
        parameter_offset = -Offset.sum
        parameter_symbol = Parameter_Symbol(parameter_name, parameter_type, parameter_offset)
        self.current_scope.insert(parameter_symbol)
        node.parameter_symbol = parameter_symbol

    def visit_FunctionDef_Node(self, node):
        # leon: initialize the offset for each function
        Offset.sum = 0
        function_name = node.function_name
        function_symbol = Function_Symbol(function_name)
        self.current_scope.insert(function_symbol)

        # self.log(f'ENTER scope: {function_name}')
        function_scope = ScopedSymbolTable(
            scope_name=function_name,
            scope_level=self.current_scope.scope_level + 1,
            enclosing_scope=self.current_scope
        )
        self.current_scope = function_scope


        # Insert formal_parameters into the function scope
        for eachparam in node.formal_parameters:
            self.visit(eachparam)

        self.visit(node.block_node) # visit function block

        node.offset = Offset.sum

        self.current_scope = self.current_scope.enclosing_scope
        # self.log(f'LEAVE scope: {function_name}')

        # accessed by the interpreter when executing procedure call
        function_symbol.block_ast = node.block_node

    def visit_FunctionCall_Node(self, node):
        pass

    def semantic_analyze(self, tree):
        # Traverse the AST to construct symbol table.
        for node in tree:
            if node is not None:
                self.visit(node)



##################################################################################################
#
#  CODE-GENERATOR
#
##################################################################################################

class Codegenerator(NodeVisitor):
    # Round up `n` to the nearest multiple of `align`. For instance,
    # align_to(5, 8) returns 8 and align_to(11, 8) returns 16.
    def align_to(self, n, align):
      return int((n + align - 1) / align) * align

    def visit_UnaryOp_Node(self, node):
        self.visit(node.right)
        if node.op.type == TokenType.TK_MINUS:
            print(f"  neg %rax")

    def visit_Return_Node(self, node):
        self.visit(node.right)
        if node.token.type == TokenType.TK_RETURN:
            print(f"  jmp .{node.function_name}.return")

    def visit_BinaryOp_Node(self, node):
        self.visit(node.right)
        print(f"  push %rax")
        self.visit(node.left)
        print(f"  pop %rdi")
        if node.op.type == TokenType.TK_PLUS:
            print(f"  add %rdi, %rax")
        elif node.op.type == TokenType.TK_MINUS:
            print(f"  sub %rdi, %rax")
        elif node.op.type == TokenType.TK_MUL:
            print(f"  imul %rdi, %rax")
        elif node.op.type == TokenType.TK_DIV:
            print(f"  cqo")
            print(f"  idiv %rdi")
        elif node.op.type == TokenType.TK_EQ:
            print(f"  cmp %rdi, %rax")
            print(f"  sete %al")
            print(f"  movzb %al, %rax")
        elif node.op.type == TokenType.TK_NE:
            print(f"  cmp %rdi, %rax")
            print(f"  setne %al")
            print(f"  movzb %al, %rax")
        elif node.op.type == TokenType.TK_LT:
            print(f"  cmp %rdi, %rax")
            print(f"  setl %al")
            print(f"  movzb %al, %rax")
        elif node.op.type == TokenType.TK_GT:
            print(f"  cmp %rdi, %rax")
            print(f"  setg %al")
            print(f"  movzb %al, %rax")
        elif node.op.type == TokenType.TK_LE:
            print(f"  cmp %rdi, %rax")
            print(f"  setle %al")
            print(f"  movzb %al, %rax")
        elif node.op.type == TokenType.TK_GE:
            print(f"  cmp %rdi, %rax")
            print(f"  setge %al")
            print(f"  movzb %al, %rax")


    def visit_Assign_Node(self, node):
        if node.left.token.type == TokenType.TK_IDENT:
            # var is left-value
            var_offset = node.left.symbol.offset
            print(f"  lea {var_offset}(%rbp), %rax")
            # left-value
            print(f"  push %rax")

            self.visit(node.right)
            print(f"  pop %rdi")
            print(f"  mov %rax, (%rdi)")
        else:
            error("not an lvalue");

    def visit_Num_Node(self, node):
        print(f"  mov ${node.value}, %rax")

    def visit_If_Node(self, node):
        Count.i += 1
        self.visit(node.condition)
        print(f"  cmp $0, %rax")
        print(f"  je  .L.else.{Count.i}")
        if node.then_statement is not None:
            self.visit(node.then_statement)
        print(f"  jmp .L.end.{Count.i}")
        print(f".L.else.{Count.i}:")
        if node.else_statement is not None:
            self.visit(node.else_statement)
        print(f".L.end.{Count.i}:")


    def visit_Block_Node(self, node):
        for eachnode in node.statement_nodes:
            self.visit(eachnode)
        # self.log(f'LEAVE scope: {block_name}')

    def visit_Var_Node(self, node):
        # var is right-value
        var_offset = node.symbol.offset
        print(f"  lea {var_offset}(%rbp), %rax")
        # right-value
        print(f"  mov (%rax), %rax")


    def visit_VarDecl_Node(self, node):
        pass

    def visit_FormalParam_Node(self, node):
        pass

    def visit_FunctionCall_Node(self, node):
        nparams = 0
        for eachnode in node.actual_parameter_nodes:
            self.visit(eachnode)
            print(f"  push %rax")
            nparams += 1
        for i in range(nparams, 0, -1):
            print(f"  pop %{parameter_registers[i-1]}")

        print(f"  mov $0, %rax")
        print(f"  call {node.function_name}")

    def visit_FunctionDef_Node(self, node):
        # leon: initialize the offset for each function
        Offset.sum = 0
        print(f"  .text")
        print(f"  .globl {node.function_name}")
        print(f"{node.function_name}:")
        # Prologue
        print(f"  push %rbp")
        print(f"  mov %rsp, %rbp")
        stack_size = self.align_to(node.offset, 16)
        print(f"  sub ${stack_size}, %rsp")

        i = 0
        for eachparam in node.formal_parameters:
            parameter_offset = eachparam.parameter_symbol.offset
            print(f"  mov %{parameter_registers[i]}, {parameter_offset}(%rbp)")
            i += 1

        # Visit function block
        self.visit(node.block_node)

        print(f".{node.function_name}.return:")
        # Epilogue
        print(f"  mov %rbp, %rsp")
        print(f"  pop %rbp")
        print(f"  ret")


    def code_generate(self, tree):
        # Traverse the AST to emit assembly.
        for node in tree:
            if node is not None:
                self.visit(node)



##################################################################################################
#
#  DRIVER
#
##################################################################################################

def main():
    # parser = argparse.ArgumentParser(
    #     description='cbypython - Simple C-like Compiler'
    # )
    # parser.add_argument('inputfile', help='C-like source file')
    # args = parser.parse_args()

    # Inputfile.name = args.inputfile
    # if Inputfile.name == '-':
    #     Inputfile.buffer = sys.stdin.readlines()
    # else:
    #     Inputfile.buffer = open(args.inputfile, 'r').readlines()

    # 读入源程序
    Inputfile.buffer = open("tmpc", 'r').readlines()
    Inputfile_plain_text = ''
    for line in Inputfile.buffer:
        Inputfile_plain_text += line

    # 词法分析
    lexer = Lexer(Inputfile_plain_text)

    # for eachtok in lexer.gather_all_tokens():
    #     print(eachtok.value)

    # 语法分析
    parser = Parser(lexer)
    tree = parser.parse()

    # 语义分析
    semantic_analyzer = SemanticAnalyzer()
    semantic_analyzer.semantic_analyze(tree)

    # 代码生成
    code_generator= Codegenerator()
    code_generator.code_generate(tree)

if __name__ == '__main__':
    main()
