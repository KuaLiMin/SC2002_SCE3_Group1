package foms.tools;

import foms.controller.OrdersController;

import java.util.concurrent.*;
import java.util.List;

/**
 * The OrderCleanupScheduler class manages scheduled tasks for cleaning up expired orders in the
 * Food Ordering Management System (FOMS).
 * 
 * @author Charlton Siaw Qi Hen
 * @version 1.0
 * @since 2024-04-15
 */

public class OrderCleanupScheduler {

    /**
     * Starts the scheduled task to check for and cancel expired orders. The task is scheduled to run
     * at a fixed rate of once every minute, beginning immediately upon system startup.
     *
     * Each execution of the task queries the {@link OrdersController} for expired orders, cancels them,
     * and logs the IDs of orders that were canceled due to their expiration status. This method ensures
     * that users do not encounter orders that should no longer be active due to the passage of time.
     */
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
