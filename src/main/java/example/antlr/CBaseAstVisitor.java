package example.antlr;

import example.antlr.ast.*;

/** Modeled after CBaseVisitor.java */
public class CBaseAstVisitor<T> extends AbstractAstVisitor<T> implements CAstVisitor<T> {
    
	/** 
	 * These default implementations are not meant to be used.
	 * They should be overriden by the child class.
	 * In theory, this base visitor can be deleted?
	 * Let's keep it here for now for consistency.
	 */
	@Override
	public T visitAstNode(AstNode node) {
		System.out.println("Hi, I am " + node);
		return null;
	}

	@Override
	public T visitAstNodeUnary(AstNodeUnary node) {
		System.out.println("Hi, I am " + node);
		T result = visit(node.child);
		return null;
	}

	@Override
	public T visitAstNodeBinary(AstNodeBinary node) {
		System.out.println("Hi, I am " + node);
		T leftResult = visit(node.left);
		T rightResult = visit(node.right);
		// Aggregate results...
		return null;
	}

	@Override
	public T visitAstNodeDynamic(AstNodeDynamic node) {
		System.out.println("Hi, I am " + node);
		for (AstNode n : node.children) {
			T result = visit(n);
		}
		return null;
	}

    @Override
	public T visitAssignmentNode(AssignmentNode node) {
		System.out.println("Hi, I am " + node);
		T leftResult = visit(node.left);
		T rightResult = visit(node.right);
		// Aggregate results...
		return null;
	}

	@Override
	public T visitConstantNode(ConstantNode node) {
		System.out.println("Hi, I am " + node + " with constant " + node.constant);
		return null;
	}

	@Override
	public T visitOpaqueNode(OpaqueNode node) {
		System.out.println("Hi, I am " + node);
		return null;
	}

	@Override
	public T visitStatementSequenceNode(StatementSequenceNode node) {
		System.out.println("Hi, I am " + node);
		for (AstNode n : node.children) {
			T result = visit(n);
		}
		return null;
	}

	@Override
	public T visitVariableNode(VariableNode node) {
		System.out.println("Hi, I am " + node + ": (" + node.type + ", " + node.name + ")");
		return null;
	}
}
