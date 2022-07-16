package example.antlr;

import example.antlr.ast.*;

/** Modeled after CBaseVisitor.java */
public class CBaseAstVisitor<T> extends AbstractAstVisitor<T> implements CAstVisitor<T> {
    
	@Override public T visitAstNodeBinary(AstNodeBinary node) { return visitChildren(node); }
    @Override public T visitAssignmentNode(AssignmentNode node) { return visitChildren(node); }
	@Override public T visitConstantNode(ConstantNode node) { return visitChildren(node); }
	@Override public T visitOpaqueNode(OpaqueNode node) { return visitChildren(node); }
	@Override public T visitStatementSequenceNode(StatementSequenceNode node) { return visitChildren(node); }
	@Override public T visitVariableNode(VariableNode node) { return visitChildren(node); }
}
