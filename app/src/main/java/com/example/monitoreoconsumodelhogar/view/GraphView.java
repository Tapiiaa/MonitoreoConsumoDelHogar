package com.example.monitoreoconsumodelhogar.view;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class GraphView extends View {

    private double[] kwhData;
    private Paint paint;

    public GraphView(Context context, double[] kwhData) {
        super(context);
        this.kwhData = kwhData;
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(8f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Dimensiones del canvas
        int width = getWidth();
        int height = getHeight();

        // Definir los márgenes
        int paddingLeft = 50;
        int paddingBottom = 50;

        // Escalar los datos
        double maxKwh = getMaxValue(kwhData);
        double scale = (height - paddingBottom) / maxKwh;

        int dataLength = kwhData.length;
        float stepX = (width - paddingLeft) / (float) dataLength;

        // Dibujar las líneas del gráfico
        for (int i = 0; i < dataLength - 1; i++) {
            float startX = paddingLeft + i * stepX;
            float startY = (float) (height - paddingBottom - kwhData[i] * scale);
            float stopX = paddingLeft + (i + 1) * stepX;
            float stopY = (float) (height - paddingBottom - kwhData[i + 1] * scale);
            canvas.drawLine(startX, startY, stopX, stopY, paint);
        }
    }

    private double getMaxValue(double[] data) {
        double max = data[0];
        for (double value : data) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}
