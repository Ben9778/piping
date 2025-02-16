package test;

import annotation.AppTest;
import annotation.StartTest;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestExecutor {
    /**
     * execute all the test methods annotated with @StartTest in the TestApplication class
     */
    protected void scanAndExecute() throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        Class<TestApplication> clazz = test.TestApplication.class;
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof AppTest) {
                Method[] methods = clazz.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(StartTest.class)) {
                        System.out.println("Executing test method: " + method.getName());
                        method.setAccessible(true);
                        method.invoke(clazz.getConstructor().newInstance());
                    }
                }
            }
        }
    }
}
