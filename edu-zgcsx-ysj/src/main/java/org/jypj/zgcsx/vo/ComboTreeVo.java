package org.jypj.zgcsx.vo;

import org.jypj.zgcsx.entity.Space;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;

/**
 * ComboTree实体模型
 *
 * @author QiCai
 */
public class ComboTreeVo {

    private String id;
    private String text;
    private List<ComboTreeVo> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<ComboTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<ComboTreeVo> children) {
        this.children = children;
    }

    /**
     * 构造ComboTree模型结构
     *
     * @param spaceManages
     * @return
     */
    public List<ComboTreeVo> createComboTreeVos(List<Space> spaceManages) {
        Map<String, List<Space>> mapCampusDatas = sameDatas(spaceManages, "campus", "campustext");
        LinkedHashMap<String, String> mapCampusNames = differentByField(spaceManages, "campus", "campustext");
        List<ComboTreeVo> comboTreeCampuses = new ArrayList<ComboTreeVo>();
        ComboTreeVo comboTreeCampus = null;
        for (Entry<String, String> campusEntry : mapCampusNames.entrySet()) {
            comboTreeCampus = new ComboTreeVo();
            comboTreeCampus.setId(campusEntry.getKey());
            comboTreeCampus.setText(campusEntry.getValue());
            Map<String, List<Space>> mapArchitectureDatas = sameDatas(mapCampusDatas.get(campusEntry.getKey()), "architecture", "architecturetext");
            LinkedHashMap<String, String> mapArchitectureNames = differentByField(mapCampusDatas.get(campusEntry.getKey()), "architecture", "architecturetext");
            List<ComboTreeVo> comboTreeArchitectures = new ArrayList<ComboTreeVo>();
            ComboTreeVo comboTreeArchitecture = null;
            for (Entry<String, String> architectureEntry : mapArchitectureNames.entrySet()) {
                comboTreeArchitecture = new ComboTreeVo();
                comboTreeArchitecture.setId(architectureEntry.getKey());
                comboTreeArchitecture.setText(architectureEntry.getValue());
                Map<String, List<Space>> mapFloorDatas = sameDatas(mapArchitectureDatas.get(architectureEntry.getKey()), "floorid", "flooridtext");
                LinkedHashMap<String, String> mapFloorNames = differentByField(mapArchitectureDatas.get(architectureEntry.getKey()), "floorid", "flooridtext");
                List<ComboTreeVo> comboTreefloors = new ArrayList<ComboTreeVo>();
                ComboTreeVo comboTreefloor = null;
                for (Entry<String, String> floorEntry : mapFloorNames.entrySet()) {
                    comboTreefloor = new ComboTreeVo();
                    comboTreefloor.setId(floorEntry.getKey());
                    comboTreefloor.setText(floorEntry.getValue());
                    LinkedHashMap<String, String> mapClassNames = differentByField(mapFloorDatas.get(floorEntry.getKey()), "id", "mctext");
                    List<ComboTreeVo> comboTreeclasses = new ArrayList<ComboTreeVo>();
                    ComboTreeVo comboTreeclass = null;
                    for (Entry<String, String> classEntry : mapClassNames.entrySet()) {
                        comboTreeclass = new ComboTreeVo();
                        comboTreeclass.setId(classEntry.getKey());
                        comboTreeclass.setText(classEntry.getValue());
                        comboTreeclasses.add(comboTreeclass);
                    }
                    comboTreefloor.setChildren(comboTreeclasses);
                    comboTreefloors.add(comboTreefloor);
                }
                comboTreeArchitecture.setChildren(comboTreefloors);
                comboTreeArchitectures.add(comboTreeArchitecture);
            }
            comboTreeCampus.setChildren(comboTreeArchitectures);
            comboTreeCampuses.add(comboTreeCampus);
        }
        return comboTreeCampuses;
    }

    /**
     * 在给定的集合内获得相同的数据
     *
     * @return
     */
    public Map<String, List<Space>> sameDatas(List<Space> spaceManages, String field, String fieldText) {
        Map<String, List<Space>> map = new HashMap<String, List<Space>>();
        LinkedHashMap<String, String> maps = differentByField(spaceManages, field, fieldText);
        Set<String> sets = maps.keySet();
        Iterator<String> it = sets.iterator();
        while (it.hasNext()) {
            String key = it.next();
            List<Space> spaceManagesItems = new ArrayList<Space>();
            for (Space spaceManage : spaceManages) {
                if (key.equals(getMethodByReflect(spaceManage, field))) {
                    spaceManagesItems.add(spaceManage);
                }
            }
            map.put(key, spaceManagesItems);
        }
        return map;
    }

    /**
     * @param spaceManages
     * @param field
     * @param fieldText
     * @return
     */
    public LinkedHashMap<String, String> differentByField(List<Space> spaceManages, String field, String fieldText) {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        for (Space spaceManage : spaceManages) {
            map.put(getMethodByReflect(spaceManage, field), getMethodByReflect(spaceManage, fieldText));
        }
        return map;
    }

    /**
     * 通过给定字段获取get方法的返回值
     *
     * @param vo
     * @param fieldStr
     * @return
     */
    public String getMethodByReflect(Object vo, String fieldStr) {
        Object getValue = null;
        try {
            Field[] fields = vo.getClass().getDeclaredFields();
            Field field = null;
            for (Field f : fields) {
                if (f.getName().equals(fieldStr)) {
                    field = f;
                    break;
                }
            }
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), vo.getClass());
            Method get = pd.getReadMethod();
            getValue = get.invoke(vo, new Object[]{});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return getValue.toString();
    }

} 
