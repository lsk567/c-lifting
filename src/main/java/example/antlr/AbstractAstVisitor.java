package example.antlr;

import example.antlr.ast.AstNode;

/** Modeled after AbstractParseTreeVisitor.class */
public abstract class AbstractAstVisitor<T> implements AstVisitor<T> {
    
    @Override
    public T visit(AstNode tree) {
		return tree.accept(this);
	}
}
