package example.antlr;

import example.antlr.ast.*;

/** Modeled after CVisitor.java */
public interface CAstVisitor<T> extends AstVisitor<T> {
    
	T visitAstNode(AstNode node);
	T visitAstNodeUnary(AstNodeUnary node);
	T visitAstNodeBinary(AstNodeBinary node);
	T visitAstNodeDynamic(AstNodeDynamic node);
    T visitAssignmentNode(AssignmentNode node);
	T visitConstantNode(ConstantNode node);
	T visitOpaqueNode(OpaqueNode node);
	T visitStatementSequenceNode(StatementSequenceNode node);
	T visitVariableNode(VariableNode node);
}
