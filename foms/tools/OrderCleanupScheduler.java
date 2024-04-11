package foms.tools;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.List;

import foms.controller.OrdersController;

public class OrderCleanupScheduler {
    public static void startOrderCleanupTask() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable cancelExpiredOrdersTask = () -> {
            List<String> canceledOrderIds = OrdersController.cancelExpiredOrders();
            if (!canceledOrderIds.isEmpty()) {
                System.out.println("\nThe following orders have been canceled due to expiration:");
                for (String orderId : canceledOrderIds) {
                    System.out.println(orderId);
                }
            }
        };

        // Schedule the task to run every minute
        scheduler.scheduleAtFixedRate(cancelExpiredOrdersTask, 0, 1, TimeUnit.MINUTES);
    }
}
