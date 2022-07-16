package example.antlr.ast;

import example.antlr.AstVisitor;
import example.antlr.CAstVisitor;

public class VariableNode extends AstNode {

    public enum Type {
        UNKNOWN, INT, BOOLEAN
    }

    public Type type;
    public String name;

    public VariableNode(String name) {
        super();
        this.type = Type.UNKNOWN;
        this.name = name;
    }

    public VariableNode(Type type, String name) {
        super();
        this.type = type;
        this.name = name;
    }

    @Override
    public <T> T accept(AstVisitor<? extends T> visitor) {
        return ((CAstVisitor<? extends T>)visitor).visitVariableNode(this);
    }
}
