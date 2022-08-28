package ru.otus.logging;

import ru.otus.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ProxyLog {
    private ProxyLog() {
    }

    public static <T> T createProxedClass(Class<T> clazz) {
        Class<?>[] interfaces = clazz.getInterfaces();
        Class<?> anInterface = interfaces[0];
        InvocationHandler handler = new DemoInvocationHandler(clazz);
        return (T) Proxy.newProxyInstance(ProxyLog.class.getClassLoader(),
                new Class<?>[]{anInterface}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final Class<?> proxedClass;
        private final Object newInstance;
        private final List<Method> annotatedMethods;

        DemoInvocationHandler(Class<?> proxedClass) {
            this.proxedClass = proxedClass;
            try {
                this.newInstance = proxedClass.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException
                     | InvocationTargetException  | NoSuchMethodException e)  {
                throw new RuntimeException(e);
            }
            Class<?> anInterface = proxedClass.getInterfaces()[0];

            this.annotatedMethods = Arrays.stream(proxedClass.getMethods())
                    .filter(m -> m.isAnnotationPresent(Log.class))
                    .map(m -> {
                        try {
                            return anInterface.getMethod(m.getName(), m.getParameterTypes());
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toList();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (annotatedMethods.contains(method)) {
                final String parametersString = Arrays.stream(args)
                        .map(Object::toString)
                        .collect(Collectors.joining(", ", "", ""));
                System.out.println("executed method:" + method.getName() + ", params: " + parametersString);
            }
            return method.invoke(newInstance, args);
        }
    }
}
