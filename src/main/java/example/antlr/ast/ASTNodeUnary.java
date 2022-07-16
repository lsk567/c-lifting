package example.antlr.ast;

import example.antlr.AstVisitor;
import example.antlr.CAstVisitor;
public class AstNodeUnary extends AstNode {

    public AstNode child;

    @Override
    public <T> T accept(AstVisitor<? extends T> visitor) {
        return ((CAstVisitor<? extends T>)visitor).visitAstNodeUnary(this);
    }
}
