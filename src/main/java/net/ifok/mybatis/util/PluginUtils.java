package net.ifok.mybatis.util;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;

import java.util.List;
import java.util.Objects;

/**
 * @Description:  工具类
 * @Author: leftso
 * @Date: 2020/12/2 15:12
 **/
public class PluginUtils {


    /**
     * 判断当前字段是否主键
     *
     * @param introspectedColumn 当前字段
     * @return true | false
     */
    public static boolean isPrimaryKey(IntrospectedColumn introspectedColumn){
        if (Objects.isNull(introspectedColumn)){
            return false;
        }
        IntrospectedTable introspectedTable = introspectedColumn.getIntrospectedTable();
        if (Objects.isNull(introspectedTable)){
            return false;
        }
        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        if (Objects.isNull(primaryKeyColumns)||primaryKeyColumns.size()==0){
            return false;
        }

        for (IntrospectedColumn primaryKeyColumn : primaryKeyColumns) {
            String actualColumnName = introspectedColumn.getActualColumnName();
            if (Objects.equals(actualColumnName,primaryKeyColumn.getActualColumnName())){
                return true;
            }
        }
        return false;
    }
}
