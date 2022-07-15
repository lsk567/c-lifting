package example.antlr;

import java.io.IOException;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import example.antlr.CParser.BlockItemListContext;
import example.antlr.ast.ASTNode;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) {
        LOG.info("Running the example...");

        try {
            CLexer lexer = new CLexer(CharStreams.fromFileName("examples/if.c"));
        
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            CParser parser = new CParser(tokens);
            BlockItemListContext parseTree = parser.blockItemList();
            BuildAstVisitor visitor = new BuildAstVisitor();
            ASTNode ast = visitor.visitBlockItemList(parseTree);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
