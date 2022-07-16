package example.antlr.ast;

import example.antlr.AstVisitor;
import example.antlr.CAstVisitor;

/**
 * An Ast node that indicates the code
 * represented by this node is unanalyzable.
 */
public class OpaqueNode extends AstNode {
    @Override
    public <T> T accept(AstVisitor<? extends T> visitor) {
        return ((CAstVisitor<? extends T>)visitor).visitOpaqueNode(this);
    }
}
