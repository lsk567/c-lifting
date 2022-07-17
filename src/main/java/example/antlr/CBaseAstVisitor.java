package example.antlr;

import java.util.List;

/** Modeled after CBaseVisitor.java */
public class CBaseAstVisitor<T> extends AbstractAstVisitor<T> implements CAstVisitor<T> {
    
	/** 
	 * These default implementations are not meant to be used.
	 * They should be overriden by the child class.
	 * In theory, this base visitor can be deleted?
	 * Let's keep it here for now for consistency.
	 */
	@Override
	public T visitAstNode(CAst.AstNode node) {
		System.out.print("[visitAstNode] ");
		System.out.println("Hi, I am " + node);
		return null;
	}

	@Override
	public T visitAstNodeUnary(CAst.AstNodeUnary node) {
		System.out.print("[visitAstNodeUnary] ");
		System.out.println("Hi, I am " + node);
		T result = visit(node.child);
		return null;
	}

	@Override
	public T visitAstNodeBinary(CAst.AstNodeBinary node) {
		System.out.print("[visitAstNodeBinary] ");
		System.out.println("Hi, I am " + node);
		T leftResult = visit(node.left);
		T rightResult = visit(node.right);
		// Aggregate results...
		return null;
	}

	@Override
	public T visitAstNodeDynamic(CAst.AstNodeDynamic node) {
		System.out.print("[visitAstNodeDynamic] ");
		System.out.println("Hi, I am " + node);
		for (CAst.AstNode n : node.children) {
			T result = visit(n);
		}
		return null;
	}

    @Override
	public T visitAssignmentNode(CAst.AssignmentNode node) {
		// The default implementation reuses visitAstNodeBinary(node).
		System.out.print("[visitAssignmentNode] ");
		return visitAstNodeBinary(node);
	}

	@Override
	public T visitIfBlockNode(CAst.IfBlockNode node) {
		System.out.print("[visitIfBlockNode] ");
		return visitAstNodeBinary(node);
	}

	@Override
	public T visitIfBodyNode(CAst.IfBodyNode node) {
		System.out.print("[visitIfBodyNode] ");
		return visitAstNodeBinary(node);
	}

	@Override
	public T visitLiteralNode(CAst.LiteralNode node) {
		System.out.println("Hi, I am " + node + " with literal " + node.literal);
		return null;
	}

	@Override
	public T visitLogicalAndNode(CAst.LogicalAndNode node) {
		System.out.print("[visitLogicalAndNode] ");
		return visitAstNodeBinary(node);
	}

	@Override
	public T visitLogicalOrNode(CAst.LogicalOrNode node) {
		System.out.print("[visitLogicalOrNode] ");
		return visitAstNodeBinary(node);
	}

	@Override
	public T visitOpaqueNode(CAst.OpaqueNode node) {
		System.out.print("[visitOpaqueNode] ");
		return visitAstNode(node);
	}

	@Override
	public T visitStatementSequenceNode(CAst.StatementSequenceNode node) {
		System.out.print("[visitStatementSequenceNode] ");
		return visitAstNodeDynamic(node);
	}

	@Override
	public T visitVariableNode(CAst.VariableNode node) {
		System.out.println("Hi, I am " + node + ": (" + node.type + ", " + node.name + ")");
		return null;
	}

	//// With one more parameter.
	@Override
	public T visitAstNode(CAst.AstNode node, List<CAst.AstNode> nodeList) {
		return visitAstNode(node);
	}

	@Override
	public T visitAstNodeUnary(CAst.AstNodeUnary node, List<CAst.AstNode> nodeList) {
		return visitAstNodeUnary(node);
	}

	@Override
	public T visitAstNodeBinary(CAst.AstNodeBinary node, List<CAst.AstNode> nodeList) {
		return visitAstNodeBinary(node);
	}

	@Override
	public T visitAstNodeDynamic(CAst.AstNodeDynamic node, List<CAst.AstNode> nodeList) {
		return visitAstNodeDynamic(node);
	}

	@Override
	public T visitAssignmentNode(CAst.AssignmentNode node, List<CAst.AstNode> nodeList) {
		return visitAssignmentNode(node);
	}

	@Override
	public T visitIfBlockNode(CAst.IfBlockNode node, List<CAst.AstNode> nodeList) {
		return visitIfBlockNode(node);
	}

	@Override
	public T visitIfBodyNode(CAst.IfBodyNode node, List<CAst.AstNode> nodeList) {
		return visitIfBodyNode(node);
	}

	@Override
	public T visitLiteralNode(CAst.LiteralNode node, List<CAst.AstNode> nodeList) {
		return visitLiteralNode(node);
	}

	@Override
	public T visitLogicalAndNode(CAst.LogicalAndNode node, List<CAst.AstNode> nodeList) {
		return visitLogicalAndNode(node);
	}

	@Override
	public T visitLogicalOrNode(CAst.LogicalOrNode node, List<CAst.AstNode> nodeList) {
		return visitLogicalOrNode(node);
	}

	@Override
	public T visitOpaqueNode(CAst.OpaqueNode node, List<CAst.AstNode> nodeList) {
		return visitOpaqueNode(node);
	}

	@Override
	public T visitStatementSequenceNode(CAst.StatementSequenceNode node, List<CAst.AstNode> nodeList) {
		return visitStatementSequenceNode(node);
	}

	@Override
	public T visitVariableNode(CAst.VariableNode node, List<CAst.AstNode> nodeList) {
		return visitVariableNode(node);
	}
}
