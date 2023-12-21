package org.example.telegram.sendNotification;
import org.example.telegram.commands.RateDataPrint;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class SendNotification {
    public static void scheduledNotification(int t ) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Визначте час, коли потрібно відправити повідомлення
        Calendar scheduledTime = Calendar.getInstance();
        scheduledTime.set(Calendar.HOUR_OF_DAY, t); // Година (24-годинний формат)
        scheduledTime.set(Calendar.MINUTE, 0);       // Хвилина
        scheduledTime.set(Calendar.SECOND, 0);       // Секунда

        // Отримайте різницю в часі між поточним часом і визначеним часом
        long initialDelay = scheduledTime.getTimeInMillis() - System.currentTimeMillis();

        // Встановіть завдання для відправлення повідомлення
        scheduler.scheduleAtFixedRate(() -> {

        }, initialDelay, TimeUnit.DAYS.toMillis(1), TimeUnit.MILLISECONDS);
    }

}


