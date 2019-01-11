import org.dom4j.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;


public class XMLConverter {

    public static Document XMLFile(Object obj) throws Exception {
        Class clazz = obj.getClass();
        if (!clazz.isAnnotationPresent(XMLObject.class)) {
            throw new Exception("Class hasn't annotation");
        }

        Document document = DocumentHelper.createDocument();
        Element root = document.addElement(clazz.getSimpleName());

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(XMLTag.class)) {
                if (field.get(obj).getClass().isAnnotationPresent(XMLObject.class)) {
                    Element newElement = XMLFile(field.get(obj)).getRootElement();
                    root.add(newElement);
                    continue;
                }
                String TagName = field.getAnnotation(XMLTag.class).name();
                if (TagName.equals("DEFAULT")) {
                    TagName = field.getName();
                }
               // if(OnlyTeg(root, TagName)){
                root.addElement(TagName).addText((field.get(obj).toString()));
                //else throw new Exception("Tag was created");
            }

            if (field.isAnnotationPresent(XMLAttribute.class)) {
                String TagName = field.getAnnotation(XMLAttribute.class).tag();
                String AttributeName = field.getAnnotation(XMLAttribute.class).name();
                if (TagName.equals("UNDEFINED")) {
                    TagName = field.getName();
                    root.addAttribute(TagName, AttributeName);
                    continue;
                }
                if (AttributeName.equals("UNDEFINED")) {
                    root.element(TagName).addAttribute(field.getName(), "Unknow");
                    continue;
                }
                root.element(TagName).addAttribute(field.getName(), AttributeName);

            }
        }

            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getParameterCount() > 0) {
                    throw new Exception("Method have parameters");
                }
                if (method.getGenericReturnType() == void.class) {
                    throw new Exception("Method return void");
                }
                method.setAccessible(true);
                if (method.isAnnotationPresent(XMLTag.class)) {
                    String TagName = method.getAnnotation(XMLTag.class).name();
                    if (TagName.equals("DEFAULT")) {
                        TagName = method.getName();
                        if(TagName.startsWith("get",0)){
                            String s = TagName.replaceAll("get","");
                            TagName = s;
                        }
                    }
                    root.addElement(TagName).addText((method.invoke(obj).toString()));
                }

                if (method.isAnnotationPresent(XMLAttribute.class)) {
                    String TagName = method.getAnnotation(XMLAttribute.class).tag();
                    String AttributeName = method.getAnnotation(XMLAttribute.class).name();

                    if (TagName.equals("UNDEFINED")) {
                        TagName = method.getName();
                        root.addAttribute(TagName, AttributeName);
                        continue;
                    }
                    if (AttributeName.equals("UNDEFINED")) {
                        root.element(TagName).addAttribute(method.getName(),"Unknow");
                        continue;
                    }
                    root.element(TagName).addAttribute(method.getName(),AttributeName);
                }
            }

        return document;
    }
    /*static boolean OnlyTeg(Element root, String name){
        Iterator Itr = root.elementIterator();
       System.out.println( root.elements().);

        while (Itr.hasNext()){
         //  Itr.next().toString();
            if(name.equals(Itr.next())){
                return false;
            }
        }
        return true;
    }*/
}
