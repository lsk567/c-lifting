package example.antlr.ast;

import java.util.List;

import example.antlr.AstVisitor;
import example.antlr.CAstVisitor;

/**
 * AST node for an if block.
 * The left node is the condition.
 * The right node is the if body.
 */
public class IfBlockNode extends AstNodeBinary implements Visitable {
    
    public AstNode getCondition() {
        return this.left;
    }

    public void setCondition(AstNode node) {
        this.left = node;
    }

    @Override
    public <T> T accept(AstVisitor<? extends T> visitor) {
        return ((CAstVisitor<? extends T>)visitor).visitIfBlockNode(this);
    }

    @Override
    public <T> T accept(AstVisitor<? extends T> visitor, List<AstNode> nodeList) {
        return ((CAstVisitor<? extends T>)visitor).visitIfBlockNode(this, nodeList);
    }
}
