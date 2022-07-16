package example.antlr.ast;

import java.util.ArrayList;

public class AstNodeDynamic extends AstNode {

    public ArrayList<AstNode> children = new ArrayList<>();

    public AstNodeDynamic() {
        super();
    }
}
