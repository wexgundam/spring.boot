package org.mose.springboot.system.service;

import org.mose.springboot.metronic.modal.SidebarItem;
import org.mose.springboot.spring.ResourceConfiguration;
import org.mose.springboot.system.dao.IModuleRepository;
import org.mose.springboot.system.modal.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Description: 模块服务
 *
 * @Author: 靳磊
 * @Date: 2017/8/18 14:43
 */
@Service
public class ModuleService {
    /**
     * 模块数据获取对象
     */
    @Autowired
    private IModuleRepository moduleRepository;
    /**
     * 系统资源配置
     */
    @Autowired
    ResourceConfiguration resourceConfiguration;

    /**
     * 获取所有模块并按照树形组织排序
     *
     * @return
     */
    public List<Module> getModules2() {
        return moduleRepository.queryAll();
    }

    /**
     * 获取所有模块并按照树形组织排序
     *
     * @return
     */
    public List<Module> getModules() {
        List<Module> modules = new ArrayList<>();
        List<Module> allModules = moduleRepository.queryAll();
        for (Module module : allModules) {
            if (module.getParent() == null) {
                findChildren(allModules, module);
                modules.add(module);
            }
        }
        Collections.sort(modules, Comparator.comparingInt(Module::getDisplayOrder));
        return modules;
    }

    /**
     * 获取给定模块的子模块
     *
     * @param modules
     * @param module
     */
    private void findChildren(List<Module> modules, Module module) {
        List<Module> children = new ArrayList<>();
        for (Module child : modules) {
            if (module.equals(child.getParent())) {
                findChildren(modules, child);
                children.add(child);
            }
        }
        Collections.sort(children, Comparator.comparingInt(Module::getDisplayOrder));
        module.setChildren(children.isEmpty() ? null : children);
    }

    /**
     * 获取全部模块，并生成侧边菜单模型
     *
     * @return
     */
    public List<SidebarItem> createSidebarItems() {
        List<SidebarItem> sidebarItems = new ArrayList<>();
        for (Module module : getModules()) {
            sidebarItems.add(createSidebarItem(null, module));
        }
        return sidebarItems;
    }

    /**
     * 根据给定模块生成侧边菜单项
     *
     * @param parentSidebarItem
     * @param module
     * @return
     */
    private SidebarItem createSidebarItem(SidebarItem parentSidebarItem, Module module) {
        SidebarItem sidebarItem = new SidebarItem();
        sidebarItem.setId(module.getId());
        sidebarItem.setName(module.getName());
        sidebarItem.setUrl(resourceConfiguration.getDynamicResourceServerUrl() + "/system/module/index.htm");
        sidebarItem.setIcon(module.getIcon());
        sidebarItem.setOrder(module.getDisplayOrder());
        sidebarItem.setParent(module.getParent() == null ? null : parentSidebarItem);
        if (module.getChildren() != null && !module.getChildren().isEmpty()) {
            List<SidebarItem> children = new ArrayList<>();
            for (Module child : module.getChildren()) {
                children.add(createSidebarItem(sidebarItem, child));
            }
            sidebarItem.setChildren(children);
        }
        return sidebarItem;
    }
}
