package example.antlr;

import java.util.List;

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
		System.out.print("[visitAstNode] ");
		System.out.println("Hi, I am " + node);
		return null;
	}

	@Override
	public T visitAstNodeUnary(AstNodeUnary node) {
		System.out.print("[visitAstNodeUnary] ");
		System.out.println("Hi, I am " + node);
		T result = visit(node.child);
		return null;
	}

	@Override
	public T visitAstNodeBinary(AstNodeBinary node) {
		System.out.print("[visitAstNodeBinary] ");
		System.out.println("Hi, I am " + node);
		T leftResult = visit(node.left);
		T rightResult = visit(node.right);
		// Aggregate results...
		return null;
	}

	@Override
	public T visitAstNodeDynamic(AstNodeDynamic node) {
		System.out.print("[visitAstNodeDynamic] ");
		System.out.println("Hi, I am " + node);
		for (AstNode n : node.children) {
			T result = visit(n);
		}
		return null;
	}

    @Override
	public T visitAssignmentNode(AssignmentNode node) {
		// The default implementation reuses visitAstNodeBinary(node).
		System.out.print("[visitAssignmentNode] ");
		return visitAstNodeBinary(node);
	}

	@Override
	public T visitIfBlockNode(IfBlockNode node) {
		System.out.print("[visitIfBlockNode] ");
		return visitAstNodeBinary(node);
	}

	@Override
	public T visitIfBodyNode(IfBodyNode node) {
		System.out.print("[visitIfBodyNode] ");
		return visitAstNodeBinary(node);
	}

	@Override
	public T visitLiteralNode(LiteralNode node) {
		System.out.println("Hi, I am " + node + " with literal " + node.literal);
		return null;
	}

	@Override
	public T visitLogicalAndNode(LogicalAndNode node) {
		System.out.print("[visitLogicalAndNode] ");
		return visitAstNodeBinary(node);
	}

	@Override
	public T visitLogicalOrNode(LogicalOrNode node) {
		System.out.print("[visitLogicalOrNode] ");
		return visitAstNodeBinary(node);
	}

	@Override
	public T visitOpaqueNode(OpaqueNode node) {
		System.out.print("[visitOpaqueNode] ");
		return visitAstNode(node);
	}

	@Override
	public T visitStatementSequenceNode(StatementSequenceNode node) {
		System.out.print("[visitStatementSequenceNode] ");
		return visitAstNodeDynamic(node);
	}

	@Override
	public T visitVariableNode(VariableNode node) {
		System.out.println("Hi, I am " + node + ": (" + node.type + ", " + node.name + ")");
		return null;
	}

	//// With one more parameter.
	@Override
	public T visitAstNode(AstNode node, List<AstNode> nodeList) {
		return visitAstNode(node);
	}

	@Override
	public T visitAstNodeUnary(AstNodeUnary node, List<AstNode> nodeList) {
		return visitAstNodeUnary(node);
	}

	@Override
	public T visitAstNodeBinary(AstNodeBinary node, List<AstNode> nodeList) {
		return visitAstNodeBinary(node);
	}

	@Override
	public T visitAstNodeDynamic(AstNodeDynamic node, List<AstNode> nodeList) {
		return visitAstNodeDynamic(node);
	}

	@Override
	public T visitAssignmentNode(AssignmentNode node, List<AstNode> nodeList) {
		return visitAssignmentNode(node);
	}

	@Override
	public T visitIfBlockNode(IfBlockNode node, List<AstNode> nodeList) {
		return visitIfBlockNode(node);
	}

	@Override
	public T visitIfBodyNode(IfBodyNode node, List<AstNode> nodeList) {
		return visitIfBodyNode(node);
	}

	@Override
	public T visitLiteralNode(LiteralNode node, List<AstNode> nodeList) {
		return visitLiteralNode(node);
	}

	@Override
	public T visitLogicalAndNode(LogicalAndNode node, List<AstNode> nodeList) {
		return visitLogicalAndNode(node);
	}

	@Override
	public T visitLogicalOrNode(LogicalOrNode node, List<AstNode> nodeList) {
		return visitLogicalOrNode(node);
	}

	@Override
	public T visitOpaqueNode(OpaqueNode node, List<AstNode> nodeList) {
		return visitOpaqueNode(node);
	}

	@Override
	public T visitStatementSequenceNode(StatementSequenceNode node, List<AstNode> nodeList) {
		return visitStatementSequenceNode(node);
	}

	@Override
	public T visitVariableNode(VariableNode node, List<AstNode> nodeList) {
		return visitVariableNode(node);
	}
}
