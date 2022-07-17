package example.antlr.ast;

import java.util.List;

import example.antlr.AstVisitor;
import example.antlr.CAstVisitor;

/**
 * An Ast node that indicates the code
 * represented by this node is unanalyzable.
 */
public class OpaqueNode extends AstNode implements Visitable {
    
    @Override
    public <T> T accept(AstVisitor<? extends T> visitor) {
        return ((CAstVisitor<? extends T>)visitor).visitOpaqueNode(this);
    }

    @Override
    public <T> T accept(AstVisitor<? extends T> visitor, List<AstNode> nodeList) {
        return ((CAstVisitor<? extends T>)visitor).visitOpaqueNode(this, nodeList);
    }
}
