package example.antlr;

import example.antlr.ast.AstNode;

/** Modeled after ParseTreeVisitor.class */
public interface AstVisitor<T> {
    
    /** 
     * Visit an Ast, and return a user-defined result of the operation.
     * 
     * @param tree The {@link AstNode} to visit.
	 * @return The result of visiting the parse tree.
     */
    T visit(AstNode tree);

    /**
	 * Visit the children of a node, and return a user-defined result of the
	 * operation.
	 *
	 * @param node The {@link AstNode} whose children should be visited.
	 * @return The result of visiting the children of the node.
	 */
	T visitChildren(AstNode node);
}
