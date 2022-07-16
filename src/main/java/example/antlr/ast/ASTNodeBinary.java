package example.antlr.ast;

import example.antlr.AstVisitor;
import example.antlr.CAstVisitor;

public class AstNodeBinary extends AstNode implements Visitable {

    public AstNode left;
    public AstNode right;

    @Override
    public <T> T accept(AstVisitor<? extends T> visitor) {
        return ((CAstVisitor<? extends T>)visitor).visitAstNodeBinary(this);
    }
}
