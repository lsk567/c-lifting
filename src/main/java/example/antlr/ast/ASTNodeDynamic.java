package example.antlr.ast;

import java.util.List;

import example.antlr.AstVisitor;
import example.antlr.CAstVisitor;

import java.util.ArrayList;

public class AstNodeDynamic extends AstNode implements Visitable {

    public ArrayList<AstNode> children = new ArrayList<>();

    @Override
    public <T> T accept(AstVisitor<? extends T> visitor) {
        return ((CAstVisitor<? extends T>)visitor).visitAstNodeDynamic(this);
    }

    @Override
    public <T> T accept(AstVisitor<? extends T> visitor, List<AstNode> nodeList) {
        return ((CAstVisitor<? extends T>)visitor).visitAstNodeDynamic(this, nodeList);
    }
}
