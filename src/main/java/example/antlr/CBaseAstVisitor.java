package example.antlr;

import example.antlr.ast.*;

/** Modeled after CBaseVisitor.java */
public class CBaseAstVisitor<T> extends AbstractAstVisitor<T> implements CAstVisitor<T> {
    
	/** 
	 * These default implementations are not meant to be used.
	 * They should be overriden by the child class.
	 * In theory, this base visitor can be deleted.
	 * Let's keep it here for now for consistency.
	 */
	@Override public T visitAstNode(AstNode node) { return visitChildren(node); }
	@Override public T visitAstNodeUnary(AstNodeUnary node) { return visitChildren(node); }
	@Override public T visitAstNodeBinary(AstNodeBinary node) { return visitChildren(node); }
	@Override public T visitAstNodeDynamic(AstNodeDynamic node) { return visitChildren(node); }
    @Override public T visitAssignmentNode(AssignmentNode node) { return visitChildren(node); }
	@Override public T visitConstantNode(ConstantNode node) { return visitChildren(node); }
	@Override public T visitOpaqueNode(OpaqueNode node) { return visitChildren(node); }
	@Override public T visitStatementSequenceNode(StatementSequenceNode node) { return visitChildren(node); }
	@Override public T visitVariableNode(VariableNode node) { return visitChildren(node); }
}
