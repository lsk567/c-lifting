package example.antlr;

import java.util.List;

import example.antlr.ast.*;

/** Modeled after CVisitor.java */
public interface CAstVisitor<T> extends AstVisitor<T> {
    
	T visitAstNode(AstNode node);
	T visitAstNodeUnary(AstNodeUnary node);
	T visitAstNodeBinary(AstNodeBinary node);
	T visitAstNodeDynamic(AstNodeDynamic node);
    T visitAssignmentNode(AssignmentNode node);
	T visitIfBlockNode(IfBlockNode node);
	T visitIfBodyNode(IfBodyNode node);
	T visitLiteralNode(LiteralNode node);
	T visitLogicalAndNode(LogicalAndNode node);
	T visitLogicalOrNode(LogicalOrNode node);
	T visitOpaqueNode(OpaqueNode node);
	T visitStatementSequenceNode(StatementSequenceNode node);
	T visitVariableNode(VariableNode node);

	T visitAstNode(AstNode node, List<AstNode> nodeList);
	T visitAstNodeUnary(AstNodeUnary node, List<AstNode> nodeList);
	T visitAstNodeBinary(AstNodeBinary node, List<AstNode> nodeList);
	T visitAstNodeDynamic(AstNodeDynamic node, List<AstNode> nodeList);
    T visitAssignmentNode(AssignmentNode node, List<AstNode> nodeList);
	T visitIfBlockNode(IfBlockNode node, List<AstNode> nodeList);
	T visitIfBodyNode(IfBodyNode node, List<AstNode> nodeList);
	T visitLiteralNode(LiteralNode node, List<AstNode> nodeList);
	T visitLogicalAndNode(LogicalAndNode node, List<AstNode> nodeList);
	T visitLogicalOrNode(LogicalOrNode node, List<AstNode> nodeList);
	T visitOpaqueNode(OpaqueNode node, List<AstNode> nodeList);
	T visitStatementSequenceNode(StatementSequenceNode node, List<AstNode> nodeList);
	T visitVariableNode(VariableNode node, List<AstNode> nodeList);
}
