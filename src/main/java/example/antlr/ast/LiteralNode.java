package example.antlr.ast;

import java.util.List;

import example.antlr.AstVisitor;
import example.antlr.CAstVisitor;
public class LiteralNode extends AstNode implements Visitable {
    
    public String literal;
    
    public LiteralNode(String literal) {
        super();
        this.literal = literal;
    }

    @Override
    public <T> T accept(AstVisitor<? extends T> visitor) {
        return ((CAstVisitor<? extends T>)visitor).visitLiteralNode(this);
    }

    @Override
    public <T> T accept(AstVisitor<? extends T> visitor, List<AstNode> nodeList) {
        return ((CAstVisitor<? extends T>)visitor).visitLiteralNode(this, nodeList);
    }
}
