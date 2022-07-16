package example.antlr.ast;

import example.antlr.AstVisitor;
import example.antlr.CAstVisitor;
public class ConstantNode extends AstNode {
    
    public String constant;
    
    public ConstantNode(String constant) {
        super();
        this.constant = constant;
    }

    @Override
    public <T> T accept(AstVisitor<? extends T> visitor) {
        return ((CAstVisitor<? extends T>)visitor).visitConstantNode(this);
    }
}
