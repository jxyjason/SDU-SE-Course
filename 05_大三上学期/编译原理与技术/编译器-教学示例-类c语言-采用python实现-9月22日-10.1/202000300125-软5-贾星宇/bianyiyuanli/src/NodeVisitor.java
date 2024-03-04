public abstract class NodeVisitor {
    public abstract void visit_UnaryOp_Node(UnaryOp_Node node);

    public abstract void visit_Return_Node(Return_Node node);

    public abstract void visit_BinaryOp_Node(BinaryOp_Node node);

    public abstract void visit_Assign_Node(Assign_Node node);

    public abstract void visit_If_Node(If_Node node);

    public abstract void visit_While_Node(While_Node node);

    public abstract void visit_Block_Node(Block_Node node);

    public abstract void visit_Num_Node(Num_Node node);

    public abstract void visit_Var_Node(Var_Node node);

    public abstract void visit_Type_Node(Type_Node node);

    public abstract void visit_VarDecl_Node(VarDecl_Node node);

    public abstract void visit_FormalParam_Node(FormalParam_Node node);

    public abstract void visit_FunctionDef_Node(FunctionDef_node node);

    public abstract void visit_FunctionCall_Node(FunctionCall_Node node);
    public abstract void visit_Var_array_item_Node(Var_array_item_node node);



}
