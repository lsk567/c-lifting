package example.antlr;

import java.util.List;

import example.antlr.ast.AstNode;

/** Modeled after ParseTreeVisitor.class */
public interface AstVisitor<T> {
    
    /** 
     * Visit an AST, and return a user-defined result of the operation.
     * 
     * @param tree The {@link AstNode} to visit.
	 * @return The result of visiting the parse tree.
     */
    T visit(AstNode tree);

    /** 
     * Visit an AST with a list of other AST nodes holding some information,
     * and return a user-defined result of the operation.
     * 
     * @param tree The {@link AstNode} to visit.
     * @param nodeList A list of {@link AstNode} passed down the recursive call.
	 * @return The result of visiting the parse tree.
     */
    T visit(AstNode tree, List<AstNode> nodeList);
}
