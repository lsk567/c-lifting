package example.antlr.ast;

import java.util.List;

import example.antlr.AstVisitor;
import example.antlr.CAstVisitor;

/**
 * AST node for an if block.
 * The left node is the then branch.
 * The right node is the else branch.
 */
public class IfBodyNode extends AstNodeBinary implements Visitable {
    
    @Override
    public <T> T accept(AstVisitor<? extends T> visitor) {
        return ((CAstVisitor<? extends T>)visitor).visitIfBodyNode(this);
    }

    @Override
    public <T> T accept(AstVisitor<? extends T> visitor, List<AstNode> nodeList) {
        return ((CAstVisitor<? extends T>)visitor).visitIfBodyNode(this, nodeList);
    }
}
