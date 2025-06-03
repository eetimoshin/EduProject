package src.loader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class PluginLoader {

    @Value("${class-loader.base-path}")
    private String basePath;

    public String load(String className) throws Exception {
        Path path = Paths.get(basePath, className + ".class");
        byte[] classBytes = Files.readAllBytes(path);

        ClassLoader loader = new ClassLoader() {
            @Override
            protected Class<?> findClass(String name) {
                return defineClass(className, classBytes, 0, classBytes.length);
            }
        };

        Class<?> clazz = loader.loadClass(className);
        Object instance = clazz.getDeclaredConstructor().newInstance();
        Method method = clazz.getMethod("getMessage");

        return (String) method.invoke(instance);
    }
}


