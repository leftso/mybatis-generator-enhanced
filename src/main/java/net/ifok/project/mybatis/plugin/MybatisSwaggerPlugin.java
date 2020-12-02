package net.ifok.project.mybatis.plugin;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Date;
import java.util.List;
import static net.ifok.project.mybatis.plugin.PluginUtils.*;

/**
 * @Description:  swagger插件
 * @Author: xq 
 * @Date: 2020/12/1 11:23
 **/
public class MybatisSwaggerPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return true;
    }
    /**
     * 生成实体类
     *
     * @author leftso
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        addModelClassAnnotations(topLevelClass, introspectedTable);
        addModelClassComment(topLevelClass, introspectedTable);
        return true;
    }

    /**
     * 生成字段注释和Swagger
     *
     * @author leftso
     */
    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                       IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        String remarks = introspectedColumn.getRemarks();
        // 生成字段注释
        genFieldComment(field, remarks);
        // 生成字段Swagger
        genFieldAnnotation(field,remarks, introspectedColumn);
        return true;
    }
    /**
     * 生成Mapper接口
     *
     * @author leftso
     */
    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        addMapperInterfaceAnnotations(interfaze);
        addMapperInterfaceComment(interfaze, introspectedTable);
        return true;
    }

    /**
     * 获取当前日期
     *
     * @author leftso
     */
    protected String getDateString() {
        return DateFormatUtils.format(new Date(), "yyyy-MM-dd");
    }

    /**
     * 添加实体注解
     *
     * @author leftso
     */
    private void addModelClassAnnotations(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        // 添加实体的import
        topLevelClass.addImportedType("io.swagger.annotations.ApiModel");
        topLevelClass.addImportedType("io.swagger.annotations.ApiModelProperty");
        // 添加实体的注解
        topLevelClass.addAnnotation("@ApiModel");
    }

    /**
     * 添加实体注释
     *
     * @author leftso
     */
    private void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        genJavaTypeComment(topLevelClass, introspectedTable);
    }

    /**
     * 添加Mapper接口注释
     *
     * @author leftso
     */
    private void addMapperInterfaceAnnotations(Interface interfaze) {
        // 添加Mapper的import
        interfaze.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Repository"));
        // 添加Mapper的注解
//        interfaze.addAnnotation("@Mapper");
        interfaze.addAnnotation("@Repository");
    }

    /**
     * 添加Mapper接口注释
     *
     * @author leftso
     */
    private void addMapperInterfaceComment(Interface interfaze, IntrospectedTable introspectedTable) {
        genJavaTypeComment(interfaze, introspectedTable);
    }

    /**
     * 为java类或接口生成文档注释
     *
     * @author leftso
     */
    private void genJavaTypeComment(JavaElement javaElement, IntrospectedTable introspectedTable) {
        String remarks = introspectedTable.getRemarks();
        javaElement.addJavaDocLine("/**");
        if (StringUtility.stringHasValue(remarks)) {
            String[] remarkLines = remarks.split(System.getProperty("line.separator"));
            for (String remarkLine : remarkLines) {
                javaElement.addJavaDocLine(" * " + remarkLine);
            }
        }
        javaElement.addJavaDocLine(" * ");
        javaElement.addJavaDocLine(" * " + introspectedTable.getFullyQualifiedTable());
        javaElement.addJavaDocLine(genAuthorComment());
        javaElement.addJavaDocLine(genDateComment());
        javaElement.addJavaDocLine(" */");
    }

    /**
     * 生成字段注释
     *
     * @author leftso
     */
    private void genFieldComment(Field field, String remarks) {
        field.addJavaDocLine("/**");
        if (StringUtility.stringHasValue(remarks)) {
            String[] remarkLines = remarks.split(System.getProperty("line.separator"));
            for (String remarkLine : remarkLines) {
                field.addJavaDocLine(" * " + remarkLine);
            }
        }
        field.addJavaDocLine(" */");
    }

    /**
     * 生成字段注解
     *
     * @author leftso
     */
    private void genFieldAnnotation(Field field, String remarks,IntrospectedColumn introspectedColumn) {
        //swagger 字段注解
        field.addAnnotation("@ApiModelProperty(value = \"" + remarks + "\")");
    }

    /**
     * 生成作者注释
     *
     * @author leftso
     */
    private String genAuthorComment() {
        return " * @author " + System.getProperties().getProperty("user.name");
    }

    /**
     * 生成日期注释
     *
     * @author leftso
     */
    private String genDateComment() {
        return " * @date " + getDateString();
    }

    /**
     * 生成方法注释
     *
     * @author leftso
     */
    private void genMethodComment(Method method, String comment) {
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * " + comment);
        method.addJavaDocLine(" */");
    }

}
