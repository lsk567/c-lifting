package example.antlr;

import example.antlr.ast.AstNode;

/** Modeled after AbstractParseTreeVisitor.class */
public abstract class AbstractAstVisitor<T> implements AstVisitor<T> {
    
    @Override
    public T visit(AstNode tree) {
		return tree.accept(this);
	}

    @Override
    public T visitChildren(AstNode tree) {
        System.out.println("The default implementation is not safe for use!");
        return null;
    }
}
