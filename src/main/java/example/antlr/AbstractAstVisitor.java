package example.antlr;

import java.util.List;

import example.antlr.ast.AstNode;

/** Modeled after AbstractParseTreeVisitor.class */
public abstract class AbstractAstVisitor<T> implements AstVisitor<T> {
    
    @Override
    public T visit(AstNode tree) {
      return tree.accept(this);
    }

    @Override
    public T visit(AstNode tree, List<AstNode> nodeList) {
      return tree.accept(this, nodeList);
    }
}
