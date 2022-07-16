package example.antlr.ast;

import example.antlr.AstVisitor;
import example.antlr.CAstVisitor;

public class AstNode implements Visitable {

    @Override
    public <T> T accept(AstVisitor<? extends T> visitor) {
        return ((CAstVisitor<? extends T>)visitor).visitAstNode(this);
    }
}
