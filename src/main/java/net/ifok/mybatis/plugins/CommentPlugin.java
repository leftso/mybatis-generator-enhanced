package net.ifok.mybatis.plugins;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Date;
import java.util.List;
/**
 * @Description:  字段、类注释插件。通过获取数据库字段的comment值作为字段的说明
 * @Author: leftso
 * @Date: 2020/12/3 9:16
 **/
public class CommentPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    /**
     * 生成实体类注释
     *
     * @author leftso
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        genJavaTypeComment(topLevelClass, introspectedTable);
        return true;
    }

    /**
     * 生成字段注释
     *
     * @author leftso
     */
    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                       IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        String remarks = introspectedColumn.getRemarks();
        // 生成字段注释
        genFieldComment(field, remarks);
        return true;
    }


    /**
     * 生成Mapper接口注释
     *
     * @author leftso
     */
    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        genJavaTypeComment(interfaze, introspectedTable);
        return true;
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
     * 获取当前日期
     *
     * @author leftso
     */
    protected String getDateString() {
        return DateFormatUtils.format(new Date(), "yyyy-MM-dd");
    }

}
