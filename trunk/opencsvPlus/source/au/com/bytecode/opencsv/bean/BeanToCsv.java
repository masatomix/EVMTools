/******************************************************************************
 * Copyright (c) 2009 Masatomi KINO and others. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *      Masatomi KINO - initial API and implementation
 * $Id$
 ******************************************************************************/
//�쐬��: 2009/06/08
package au.com.bytecode.opencsv.bean;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * @author Masatomi KINO
 * @version $Revision$
 */
public class BeanToCsv {
    public BeanToCsv() {
    }

    public void writeAll(HeaderColumnNameMappingStrategy strategy,
            CSVWriter out, List<?> beanList) {

        Map<String, String> map = createMap(strategy);

        Set<String> keySet = map.keySet();
        String[] header = keySet.toArray(new String[keySet.size()]);
        // �w�b�_���Ɠ���[i]�ԖڂɁA���\�b�h�����Z�b�g�B
        String[] methodNames = new String[keySet.size()];
        for (int i = 0; i < header.length; i++) {
            methodNames[i] = map.get(header[i]);
        }

        List<String[]> writeList = new LinkedList<String[]>();
        writeList.add(header);
        // ������Bean���A�J��Ԃ��B
        for (Object bean : beanList) {
            String[] values = convert(bean, methodNames);
            writeList.add(values);
        }

        // CSVWriter out = new CSVWriter(writer, '\t');
        out.writeAll(writeList);
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] write(HeaderColumnNameMappingStrategy strategy, Object bean) {

        Map<String, String> map = createMap(strategy);
        Set<String> keySet = map.keySet();
        String[] header = keySet.toArray(new String[keySet.size()]);
        // �w�b�_���Ɠ���[i]�ԖڂɁA���\�b�h�����Z�b�g�B
        String[] methodNames = new String[keySet.size()];
        for (int i = 0; i < header.length; i++) {
            methodNames[i] = map.get(header[i]);
        }

        String[] values = convert(bean, methodNames);
        return values;
    }

    private Map<String, String> createMap(
            HeaderColumnNameMappingStrategy strategy) {
        Map<String, String> map = null;
        if (strategy instanceof HeaderColumnNameTranslateMappingStrategy) {
            map = ((HeaderColumnNameTranslateMappingStrategy) strategy)
                    .getColumnMapping();
        } else if (strategy instanceof ColumnPositionMappingStrategy) {
            // �J�����w���Strategy�Ȃ�Akey=column,value=column���ă}�b�s���O�����삷��
            String[] columnMapping = ((ColumnPositionMappingStrategy) strategy)
                    .getColumnMapping();
            map = new LinkedHashMap<String, String>();
            for (String key : columnMapping) {
                map.put(key, key);
            }
        } else {
            // ����ȊO��������Astrategy�̃N���X�t�@�C���̂��΂������Ă݂�B
            Class type = strategy.getType();
            map = MapUtils.createMapping(null, type);
        }
        return map;
    }

    /**
     * ������JavaBeans�ƈ����̃��\�b�h�����ŁA
     * 
     * @param bean
     * @param methodNames
     */
    private String[] convert(Object bean, String[] methodNames) {
        // ���\�b�h���A�J��Ԃ��B
        String[] result = new String[methodNames.length];
        for (int i = 0; i < methodNames.length; i++) {
            try {
                result[i] = BeanUtils.getSimpleProperty(bean, methodNames[i]);
                // System.out.println(methodNames[i] + " : " + result[i]);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                // TODO �����������ꂽ catch �u���b�N
                e.printStackTrace();
            }
        }
        return result;
    }

}
