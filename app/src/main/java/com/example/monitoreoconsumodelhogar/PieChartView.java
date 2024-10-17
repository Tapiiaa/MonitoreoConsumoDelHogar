package com.example.monitoreoconsumodelhogar;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

public class PieChartView extends View {

    private double[] values;  // Valores de kWh
    private String[] labels;  // Nombres de las habitaciones o pasillos
    private Paint paint;
    private Paint labelPaint;
    private Paint linePaint;
    private int[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA};

    public PieChartView(Context context, double[] values, String[] labels) {
        super(context);
        this.values = values;
        this.labels = labels;

        // Pintura para las porciones del gráfico
        paint = new Paint();
        paint.setAntiAlias(true);

        // Pintura para las etiquetas
        labelPaint = new Paint();
        labelPaint.setColor(Color.BLACK);
        labelPaint.setTextSize(40f);
        labelPaint.setAntiAlias(true);

        // Pintura para las líneas de conexión
        linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2f);
        linePaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        int minDim = Math.min(width, height);
        float radius = minDim / 2.0f - 50;  // Un poco más pequeño para dejar espacio para las etiquetas

        float centerX = width / 2.0f;
        float centerY = height / 2.0f;

        RectF rectF = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

        double total = getTotalValues();
        float startAngle = 0f;

        for (int i = 0; i < values.length; i++) {
            // Calcular el ángulo de la porción
            float sweepAngle = (float) (values[i] / total * 360);
            paint.setColor(colors[i % colors.length]);
            canvas.drawArc(rectF, startAngle, sweepAngle, true, paint);

            // Calcular el ángulo medio de la porción para la posición de la etiqueta
            float angle = startAngle + sweepAngle / 2;
            float labelX = (float) (centerX + (radius + 50) * Math.cos(Math.toRadians(angle)));
            float labelY = (float) (centerY + (radius + 50) * Math.sin(Math.toRadians(angle)));

            // Dibujar la línea que conecta la porción con la etiqueta
            float lineStartX = (float) (centerX + radius * Math.cos(Math.toRadians(angle)));
            float lineStartY = (float) (centerY + radius * Math.sin(Math.toRadians(angle)));
            canvas.drawLine(lineStartX, lineStartY, labelX, labelY, linePaint);

            // Dibujar la etiqueta
            canvas.drawText(labels[i], labelX, labelY, labelPaint);

            // Mover el ángulo de inicio para la siguiente porción
            startAngle += sweepAngle;
        }
    }

    private double getTotalValues() {
        double total = 0;
        for (double value : values) {
            total += value;
        }
        return total;
    }
}
