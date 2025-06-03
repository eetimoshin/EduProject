package src.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PluginController {

    @Autowired
    private PluginLoader pluginLoader;

    @GetMapping("/plugin")
    public String showForm() {
        return "plugin/form";
    }

    @PostMapping("/plugin")
    public String loadPlugin(@RequestParam String className, Model model) {
        try {
            String result = pluginLoader.load(className);
            model.addAttribute("message", result);
        } catch (Exception e) {
            model.addAttribute("message", "Ошибка: данного класса не найдено!");
        }
        return "plugin/form";
    }
}

