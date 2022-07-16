package example.antlr.ast;

import example.antlr.AstVisitor;
import example.antlr.CAstVisitor;

import java.util.ArrayList;

public class AstNodeDynamic extends AstNode {

    public ArrayList<AstNode> children = new ArrayList<>();

    @Override
    public <T> T accept(AstVisitor<? extends T> visitor) {
        return ((CAstVisitor<? extends T>)visitor).visitAstNodeDynamic(this);
    }
}
