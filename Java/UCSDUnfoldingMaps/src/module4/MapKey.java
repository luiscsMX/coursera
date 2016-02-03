package module4;

import processing.core.PApplet;
import processing.core.PShape;

import java.util.ArrayList;
import java.util.List;

public class MapKey extends PApplet {
    private float width;
    private float height;
    private String keyTitle;
    private List<MapKeyEntry> mapKeyEntries;

    private static final int entryHeight = 20;
    private static final int keyEntriesStartOffset = 20;

    public class MapKeyEntry extends PApplet {
        private String label;
        private int color;
        private int radius;

        public MapKeyEntry(String label, int color, int radius) {
            this.label = label;
            this.color = color;
            this.radius = radius;
        }

        public void DrawMarker(float x, float y) {
            PShape markerShape = createShape(SPHERE, x, y, this.radius, this.radius);
            shape(markerShape);
        }

        public String GetLabel() {
            return this.label;
        }
    }

    public MapKey(String title, float x1, float y1, float x2, float y2) {
        this.keyTitle = title;
        this.width = width;
        this.height = height;

        mapKeyEntries = new ArrayList<>();
    }

    public void AddKeyEntry(String text, int color, int radius) {
        MapKeyEntry mapKeyEntry = new MapKeyEntry(text, color, radius);
        mapKeyEntries.add(mapKeyEntry);
    }

    public void Draw(float x, float y) {
        PShape keyBox = createShape(RECT, x, y, this.width, this.height);
        fill(color(255, 255, 255));
        shape(keyBox);

        for (int i = 0; i < mapKeyEntries.size(); i++) {
            MapKeyEntry keyEntry = mapKeyEntries.get(i);
            float currentY = y + keyEntriesStartOffset + (i * entryHeight);
            keyEntry.DrawMarker(x + 10, currentY);

            fill(color(0, 0, 0));
            text(keyEntry.GetLabel(), x + 25, currentY);
        }
    }
}
