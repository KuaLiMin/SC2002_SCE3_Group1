package foms.tools;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.List;

import foms.controller.OrdersController;

public class OrderCleanupScheduler {
    public static void startOrderCleanupTask() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable removeExpiredOrdersTask = () -> {
            List<String> removedOrderIds = OrdersController.removeExpiredOrders();
            if (!removedOrderIds.isEmpty()) {
                // System.out.println("\nThe following orders have been removed due to expiration:");
                for (String orderId : removedOrderIds) {
                    System.out.println(orderId);
                }
            } else {
                // System.out.println("\nNo expired orders removed at this time.");
            }
        };

        Runnable removeCompletedOrdersTask = () -> {
            List<String> removedOrderIds = OrdersController.removeCompletedOrders();
            if (!removedOrderIds.isEmpty()) {
                // System.out.println("\nThe following orders have been removed after collection:");
                for (String orderId : removedOrderIds) {
                    System.out.println(orderId);
                }
            } else {
                // System.out.println("\nNo completed orders removed at this time.");
            }
        };

        // Schedule the task to run every minute
        scheduler.scheduleAtFixedRate(removeExpiredOrdersTask, 0, 1, TimeUnit.MINUTES);
        scheduler.scheduleAtFixedRate(removeCompletedOrdersTask, 0, 5, TimeUnit.MINUTES);
    }
}
