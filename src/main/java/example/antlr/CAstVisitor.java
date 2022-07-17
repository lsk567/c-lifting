package example.antlr;

import java.util.List;

/** Modeled after CVisitor.java */
public interface CAstVisitor<T> extends AstVisitor<T> {
    
	T visitAstNode(CAst.AstNode node);
	T visitAstNodeUnary(CAst.AstNodeUnary node);
	T visitAstNodeBinary(CAst.AstNodeBinary node);
	T visitAstNodeDynamic(CAst.AstNodeDynamic node);
    T visitAssignmentNode(CAst.AssignmentNode node);
	T visitIfBlockNode(CAst.IfBlockNode node);
	T visitIfBodyNode(CAst.IfBodyNode node);
	T visitLiteralNode(CAst.LiteralNode node);
	T visitLogicalAndNode(CAst.LogicalAndNode node);
	T visitLogicalOrNode(CAst.LogicalOrNode node);
	T visitOpaqueNode(CAst.OpaqueNode node);
	T visitStatementSequenceNode(CAst.StatementSequenceNode node);
	T visitVariableNode(CAst.VariableNode node);

	T visitAdditionNode(CAst.AdditionNode node);
	T visitSubtractionNode(CAst.SubtractionNode node);
	T visitMultiplicationNode(CAst.MultiplicationNode node);
	T visitDivisionNode(CAst.DivisionNode node);

	/** Used for converting an AST into If Normal Form. */
	T visitAstNode(CAst.AstNode node, List<CAst.AstNode> nodeList);
	T visitAstNodeUnary(CAst.AstNodeUnary node, List<CAst.AstNode> nodeList);
	T visitAstNodeBinary(CAst.AstNodeBinary node, List<CAst.AstNode> nodeList);
	T visitAstNodeDynamic(CAst.AstNodeDynamic node, List<CAst.AstNode> nodeList);
    T visitAssignmentNode(CAst.AssignmentNode node, List<CAst.AstNode> nodeList);
	T visitIfBlockNode(CAst.IfBlockNode node, List<CAst.AstNode> nodeList);
	T visitIfBodyNode(CAst.IfBodyNode node, List<CAst.AstNode> nodeList);
	T visitLiteralNode(CAst.LiteralNode node, List<CAst.AstNode> nodeList);
	T visitLogicalAndNode(CAst.LogicalAndNode node, List<CAst.AstNode> nodeList);
	T visitLogicalOrNode(CAst.LogicalOrNode node, List<CAst.AstNode> nodeList);
	T visitOpaqueNode(CAst.OpaqueNode node, List<CAst.AstNode> nodeList);
	T visitStatementSequenceNode(CAst.StatementSequenceNode node, List<CAst.AstNode> nodeList);
	T visitVariableNode(CAst.VariableNode node, List<CAst.AstNode> nodeList);

	T visitAdditionNode(CAst.AdditionNode node, List<CAst.AstNode> nodeList);
	T visitSubtractionNode(CAst.SubtractionNode node, List<CAst.AstNode> nodeList);
	T visitMultiplicationNode(CAst.MultiplicationNode node, List<CAst.AstNode> nodeList);
	T visitDivisionNode(CAst.DivisionNode node, List<CAst.AstNode> nodeList);
}
