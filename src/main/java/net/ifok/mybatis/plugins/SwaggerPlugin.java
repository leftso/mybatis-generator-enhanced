package net.ifok.mybatis.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import java.util.List;

/**
 * @Description:  添加swagger注解插件
 * @Author: leftso
 * @Date: 2020/12/1 11:23
 **/
public class SwaggerPlugin extends PluginAdapter {

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
        return true;
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
     * 添加Mapper接口注释
     *
     * @author leftso
     */
    private void addMapperInterfaceAnnotations(Interface interfaze) {
        // 添加Mapper的import
        interfaze.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Repository"));
        // 添加Mapper的注解
        interfaze.addAnnotation("@Repository");
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

}
