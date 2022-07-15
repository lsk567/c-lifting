package example.antlr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import example.antlr.CParser.BlockItemContext;
import example.antlr.CParser.DeclarationSpecifierContext;
import example.antlr.CParser.DeclaratorContext;
import example.antlr.CParser.DirectDeclaratorContext;
import example.antlr.CParser.InitDeclaratorContext;
import example.antlr.CParser.PrimaryExpressionContext;
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

        System.out.println(stmtSeq.children);

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
                    "Marking the declaration as opaque."
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
                    "Marking the declaration as opaque."
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
                    "Marking the declaration as opaque."
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
                    "more than 1 declarators are detected on a single line,",
                    "e.g. \"int x = 1, y = 2;\" is not yet analyzable.",
                    "Marking the declaration as opaque."
                ));
                return new OpaqueNode();
            }

            // Get the variable name from the declarator.
            DeclaratorContext decl = initDeclList.get(0).declarator();
            if (decl.pointer() != null) {
                System.out.println(String.join(" ", 
                    "Warning (line " + ctx.getStart().getLine() + "):",
                    "pointers are currently not supported,",
                    "e.g. \"int *x;\" is not yet analyzable.",
                    "Marking the declaration as opaque."
                ));
                return new OpaqueNode();
            }
            if (decl.gccDeclaratorExtension().size() > 0) {
                System.out.println(String.join(" ", 
                    "Warning (line " + ctx.getStart().getLine() + "):",
                    "GCC declarator extensions are currently not supported,",
                    "e.g. \"__asm\" and \"__attribute__\" are not yet analyzable.",
                    "Marking the declaration as opaque."
                ));
                return new OpaqueNode();
            }
            DirectDeclaratorContext directDecl = decl.directDeclarator();
            if (directDecl.Identifier() == null) {
                System.out.println(String.join(" ", 
                    "Warning (line " + ctx.getStart().getLine() + "):",
                    "the variable identifier is missing.",
                    "Marking the declaration as opaque."
                ));
                return new OpaqueNode();
            }

            // Extract the name of the variable.
            String name = directDecl.Identifier().getText();             
            // Create a variable AST node.
            VariableNode variable = new VariableNode(type, name);


            //// Convert the initializer to a value.

            // Make sure that there is an initializer.
            InitDeclaratorContext initDecl = initDeclList.get(0);
            if (initDecl.initializer() == null) {
                System.out.println(String.join(" ", 
                    "Warning (line " + ctx.getStart().getLine() + "):",
                    "the initializer is missing,",
                    "e.g. \"int x;\" is not yet analyzable.",
                    "Marking the declaration as opaque."
                ));
                return new OpaqueNode();

                // FIXME: Use UninitVariableNode to perform special encoding.
                // return new UninitVariableNode(type, name);
            }

            // Extract the primaryExpression from the initializer.
            if (initDecl.initializer().assignmentExpression() == null
                || initDecl.initializer().assignmentExpression()
                    .conditionalExpression() == null) {
                System.out.println(String.join(" ", 
                    "Warning (line " + ctx.getStart().getLine() + "):",
                    "assignmentExpression or conditionalExpression is missing.",
                    "Marking the declaration as opaque."
                ));
                return new OpaqueNode();
            }

            PrimaryExpressionContext primaryExpr;
            ASTNode initializerNode;
            try {
                // FIXME: This is cutting some corners with interpreting
                // the program. Currently, inline arithmetic operations
                // will not be handled and have no warnings at all.
                // This part needs more work.
                primaryExpr = initDecl.initializer().assignmentExpression()
                                .conditionalExpression().logicalOrExpression()
                                .logicalAndExpression(0).inclusiveOrExpression(0)
                                .exclusiveOrExpression(0).andExpression(0)
                                .equalityExpression(0).relationalExpression(0)
                                .shiftExpression(0).additiveExpression(0)
                                .multiplicativeExpression(0).castExpression(0)
                                .unaryExpression().postfixExpression()
                                .primaryExpression();
            } catch (NullPointerException e) {
                System.out.println(String.join(" ", 
                    "Warning (line " + ctx.getStart().getLine() + "):",
                    "unable to extract a primary expression from the initializer.",
                    "Marking the declaration as opaque."
                ));
                return new OpaqueNode();
            }
            if (primaryExpr.Identifier() != null) {
                initializerNode = new VariableNode(primaryExpr.Identifier().getText());
            } else if (primaryExpr.Constant() != null) {
                initializerNode = new ConstantNode(primaryExpr.Constant().getText());
            } else {
                System.out.println(String.join(" ", 
                    "Warning (line " + ctx.getStart().getLine() + "):",
                    "only identifier and constant are supported on the RHS of the declaration.",
                    "Marking the declaration as opaque."
                ));
                return new OpaqueNode();
            }

            // Finally return the assignment node.
            AssignmentNode assignmentNode = new AssignmentNode();
            assignmentNode.left = variable;
            assignmentNode.right = initializerNode;
            return assignmentNode;
        }
        // Return OpaqueNode as default.
        return new OpaqueNode();
    }
}
