package com.example.notificacion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Encuentra el botón por su ID en el diseño
        guardar = findViewById(R.id.btnActualizar);

        // Configura el Listener del botón para crear una notificación al hacer clic
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Llama a la función para crear y mostrar una notificación
                createNotification();
            }
        });
    }

    // Función para crear y mostrar una notificación
    private void createNotification() {
        String id = "mensaje";

        // Obtiene una instancia del servicio NotificationManager para manejar notificaciones
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Comprueba si el servicio es nulo y sale de la función si lo es
        if (notificationManager == null) {
            return;
        }

        // Crea un objeto NotificationCompat.Builder para configurar y construir la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id);

        // Si la versión de Android es Oreo (API nivel 26) o superior
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // Crea un canal de notificación con un ID, nombre y nivel de importancia alta
            NotificationChannel notificationChannel = new NotificationChannel(id, "nuevo", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setShowBadge(true); // Muestra una insignia en la notificación
            notificationManager.createNotificationChannel(notificationChannel); // Crea el canal de notificación
        }

        // Configura los detalles de la notificación usando el builder
        builder.setAutoCancel(true) // Cancela automáticamente la notificación al hacer clic en ella
                .setWhen(System.currentTimeMillis()) // Establece la marca de tiempo de la notificación
                .setContentTitle("CREANDO REGISTRO") // Título de la notificación
                .setSmallIcon(R.mipmap.ic_launcher_foreground) // Ícono pequeño de la notificación
                .setColor(0xFF8A2BE2)
                .setContentText("Su registro se ha creado correctamente.") // Texto del contenido de la notificación
                .setContentIntent(sendNotification()) // Acción al hacer clic en la notificación
                .setContentInfo("nuevo"); // Información adicional de la notificación

        // Genera un número aleatorio para ser utilizado como el ID de la notificación
        Random random = new Random();
        int id_notification = random.nextInt(8000);

        // Utiliza el NotificationManager para mostrar la notificación construida
        notificationManager.notify(id_notification, builder.build());
    }

    // Función que crea y devuelve un PendingIntent para la acción de la notificación
    public PendingIntent sendNotification() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        // Devuelve un PendingIntent que abrirá la actividad MainActivity con el intent configurado
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
