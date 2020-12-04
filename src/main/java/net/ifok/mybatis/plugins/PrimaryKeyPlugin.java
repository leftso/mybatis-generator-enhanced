package net.ifok.mybatis.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.util.List;
import java.util.Objects;

/**
 * @Description:  insert 返回主键给实体类
 * @Author: leftso
 * @Date: 2020/12/2 15:35
 * @version mybatis-generator-maven-plugin:1.4.0
 **/
public class PrimaryKeyPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean clientBasicInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Options"));
        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        if (Objects.nonNull(primaryKeyColumns)&&primaryKeyColumns.size()>0){
            //取第一个，多个暂时无法实现
            for (IntrospectedColumn primaryKeyColumn : primaryKeyColumns) {
                boolean identity = primaryKeyColumn.isAutoIncrement();
                if (identity){
                    String actualColumnName = primaryKeyColumn.getActualColumnName();
                    String camelizeName = JavaBeansUtil.getCamelCaseString(actualColumnName,false);
                    method.addAnnotation("@Options(useGeneratedKeys = true,keyProperty = \"record."+camelizeName+"\"," +
                            "keyColumn = \""+actualColumnName+"\")");
                    //只处理第一个匹配的自增长主键
                    break;
                }

            }
        }
        return true;
    }
}
