package foms.tools;

import foms.controller.OrdersController;

import java.util.concurrent.*;
import java.util.List;

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
