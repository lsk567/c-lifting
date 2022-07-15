package example.antlr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import example.antlr.CParser.BlockItemContext;
import example.antlr.CParser.DeclarationSpecifierContext;
import example.antlr.CParser.InitDeclaratorContext;
import example.antlr.ast.*;
import example.antlr.ast.VariableNode.Type;

public class BuildAstVisitor extends CBaseVisitor<ASTNode> {
    private static final Logger LOG = LoggerFactory.getLogger(BuildAstVisitor.class);

    @Override
    public ASTNode visitBlockItemList(CParser.BlockItemListContext ctx) {
        StatementSequenceNode stmtSeq = new StatementSequenceNode();
        
        // Populate the children.
        for (BlockItemContext blockItem : ctx.blockItem()) {
            // System.out.println(blockItem);
            stmtSeq.children.add(visit(blockItem));
        }

        System.out.println(stmtSeq);

        return stmtSeq;
    }

    @Override
    public ASTNode visitBlockItem(CParser.BlockItemContext ctx) {
        if (ctx.statement() != null)
            return visit(ctx.statement());
        else
            return visit(ctx.declaration());
    }

    @Override
    public ASTNode visitDeclaration(CParser.DeclarationContext ctx) {
        if (ctx.declarationSpecifiers() != null 
            && ctx.initDeclaratorList() != null) {
            //// Extract type from declarationSpecifiers.
            List<DeclarationSpecifierContext> declSpecList 
                = ctx.declarationSpecifiers().declarationSpecifier();
            
            // Cannot handle more than 1 specifiers, e.g. static const int.
            // We can augment the analytical capability later.
            if (declSpecList.size() > 1) {
                System.out.println(String.join(" ", 
                    "Warning (line " + ctx.getStart().getLine() + "):",
                    "the analyzer cannot handle more than 1 specifiers,",
                    "e.g. static const int.",
                    "A conservative strategy is taken."
                ));
                return new OpaqueNode();
            }
            
            // Check if the declaration specifier is a type specifier: e.g. int or long.
            DeclarationSpecifierContext declSpec = declSpecList.get(0);
            if (declSpec.typeSpecifier() == null) {
                System.out.println(String.join(" ", 
                    "Warning (line " + ctx.getStart().getLine() + "):",
                    "only type specifiers are supported.",
                    "e.g. \"static const int\" is not analyzable.",
                    "A conservative strategy is taken."
                ));
                return new OpaqueNode();
            }
            
            // Check if the type specifier is what we currently support.
            // Right now we only support int, long, & double.
            VariableNode.Type type;
            ArrayList<String> supportedTypes = new ArrayList<String>(
                Arrays.asList(
                    "int",
                    "long",
                    "double"
                )
            );
            if (declSpec.typeSpecifier().Int() != null
                || declSpec.typeSpecifier().Long() != null
                || declSpec.typeSpecifier().Double() != null) 
                type = Type.INT;
            // Mark the declaration unanalyzable if the type is unsupported.
            else {
                System.out.println(String.join(" ", 
                    "Warning (line " + ctx.getStart().getLine() + "):",
                    "unsupported type detected at " + declSpec.typeSpecifier(),
                    "Only " + supportedTypes + " are supported.",
                    "A conservative strategy is taken."
                ));
                return new OpaqueNode();
            }

            //// Extract variable name and value from initDeclaratorList.
            List<InitDeclaratorContext> initDeclList
                = ctx.initDeclaratorList().initDeclarator();
            
            // Cannot handle more than 1 initDeclarator: e.g. x = 1, y = 2;
            // We can augment the analytical capability later.
            if (initDeclList.size() > 1) {
                System.out.println(String.join(" ", 
                    "Warning (line " + ctx.getStart().getLine() + "):",
                    "unsupported type detected at " + declSpec.typeSpecifier(),
                    "Only " + supportedTypes + " are supported.",
                    "A conservative strategy is taken."
                ));
                return new OpaqueNode();
            }

            // Make sure that there is an initializer
            String name; 


            // If initializer does not exist.
            // return new UninitVariableNode(type, name);
        }
        // Return OpaqueNode as default.
        return new OpaqueNode();
    }
}
