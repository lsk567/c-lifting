package example.antlr;

import example.antlr.ast.*;

/** Modeled after CVisitor.java */
public interface CAstVisitor<T> extends AstVisitor<T> {
    
	T visitAstNodeBinary(AstNodeBinary node);
    T visitAssignmentNode(AssignmentNode node);
	T visitConstantNode(ConstantNode node);
	T visitOpaqueNode(OpaqueNode node);
	T visitStatementSequenceNode(StatementSequenceNode node);
	T visitVariableNode(VariableNode node);
}
