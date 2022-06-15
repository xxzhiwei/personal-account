//package com.aojiaodage;
//
//import com.aojiaodage.entity.Detail;
//import com.aojiaodage.enums.Services;
//import com.aojiaodage.repository.DataRepository;
//import com.aojiaodage.service.impl.*;
//import com.aojiaodage.util.CommandLineUtil;
//import com.aojiaodage.io.Reader;
//import com.aojiaodage.io.Writer;
//import com.aojiaodage.service.AccountService;
//
//import java.util.*;
//
//public class Application {
//    private final Map<Integer, AccountService> serviceMap = new HashMap<>();
//    private final List<Integer> menuIds;
//    private final Reader reader = new Reader();
//    private final Writer writer = new Writer();
//    private DataRepository dataRepository;
//
//    private void initServices() {
//        IncomeService incomeService = new IncomeService(Services.INCOME.getValue(), Services.INCOME.getDesc(), dataRepository);
//        CostService costService = new CostService(Services.COST.getValue(), Services.COST.getDesc(), dataRepository);
//        DetailService detailService = new DetailService(Services.DETAIL.getValue(), Services.DETAIL.getDesc(), dataRepository);
//        ExitService exitService = new ExitService(Services.EXIT.getValue(), Services.EXIT.getDesc());
//        serviceMap.put(incomeService.getId(), incomeService);
//        serviceMap.put(exitService.getId(), exitService);
//        serviceMap.put(costService.getId(), costService);
//        serviceMap.put(detailService.getId(), detailService);
//    }
//
//    private void initData() {
//        List<Detail> details = reader.read();
//        dataRepository = new DataRepository(details, writer);
//    }
//
//    public Application() {
//        initData();
//        initServices();
//        Set<Map.Entry<Integer, AccountService>> entrySet = serviceMap.entrySet();
//        menuIds = new ArrayList<>(serviceMap.size());
//        List<String> menus = new ArrayList<>(serviceMap.size());
//        for (Map.Entry<Integer, AccountService> entry : entrySet) {
//            AccountService service = entry.getValue();
//            menuIds.add(service.getId());
//            menus.add(service.getId() + ". " + service.getName());
//        }
//
//        // 菜单服务不显示
//        MenuService menuService = new MenuService(Services.MENU.getValue(), Services.MENU.getDesc(), menus);
//        serviceMap.put(menuService.getId(), menuService);
//    }
//
//    public static void main(String[] args) {
//        Application application = new Application();
//        Map<Integer, AccountService> serviceMap = application.serviceMap;
//
//        boolean continued = true;
//        boolean contained = true;
//        while (continued) {
//            serviceMap.get(Services.MENU.getValue()).execute(contained);
//            int id = CommandLineUtil.readNum();
//            contained = application.menuIds.contains(id);
//            if (!contained) {
//                continue;
//            }
//            AccountService service = serviceMap.get(id);
//            int code = service.execute();
//            if (service instanceof ExitService) {
//                if (code == 1) {
//                    continued = false;
//                }
//            }
//        }
//    }
//}
