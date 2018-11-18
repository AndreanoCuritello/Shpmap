package ru.r8digitaltech7.onlylemi.mapview.library.layer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import ru.r8digitaltech7.shoppingmap.R;

import java.util.List;

import ru.r8digitaltech7.onlylemi.mapview.library.MapView;
import ru.r8digitaltech7.onlylemi.mapview.library.utils.MapMath;

/**
 * MarkLayer
 *
 * @author: onlylemi
 */
public class MarkLayer extends MapBaseLayer {
    private static final String TAG = "MarkLayer";
    private List<PointF> marks;
    private List<String> marksName;
    /*TEST*/
    private List<PointF> nodes;
    private List<PointF> nodesContacts;
    /*-TEST*/
    private MarkIsClickListener listener;

    private Bitmap bmpMark, bmpMarkTouch, bmpContact, bmpContactTouch,bmpNode, bmpNodeTouch;

    private float radiusMark;
    private boolean isClickMark = false;
    private int num = -1;

    private Paint paint;

    public MarkLayer(MapView mapView) {
        this(mapView, null, null);
    }
    /*TEST*/
    public void setNodesContactstoMarkLayer(List<PointF> pnodesContacts){
    	this.nodesContacts = pnodesContacts;
    }
    public void setNodestoMarkLayer(List<PointF> pnodes){
    	this.nodes = pnodes;
    }
    /*-TEST*/

    public MarkLayer(MapView mapView, List<PointF> marks, List<String> marksName) {
    	
        super(mapView);
        Log.v(TAG, "MarkLayer(MapView mapView, List<PointF> marks, List<String> marksName)");
        this.marks = marks;
        this.marksName = marksName;

        initLayer();
    }

    private void initLayer() {
        radiusMark = setValue(10f);

        bmpMark = BitmapFactory.decodeResource(mapView.getResources(), R.mipmap.mark);
        bmpMarkTouch = BitmapFactory.decodeResource(mapView.getResources(), R.mipmap.mark_touch);
        
        bmpContact = BitmapFactory.decodeResource(mapView.getResources(), R.mipmap.contact);
        bmpContactTouch = BitmapFactory.decodeResource(mapView.getResources(), R.mipmap.contact);

        bmpNode = BitmapFactory.decodeResource(mapView.getResources(), R.mipmap.node);
        bmpNodeTouch = BitmapFactory.decodeResource(mapView.getResources(), R.mipmap.node);
        
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    public void onTouch(MotionEvent event) {
        if (marks != null) {
            if (!marks.isEmpty()) {
                float[] goal = mapView.convertMapXYToScreenXY(event.getX(), event.getY());
                for (int i = 0; i < marks.size(); i++) {
                    if (MapMath.getDistanceBetweenTwoPoints(goal[0], goal[1],
                            marks.get(i).x - bmpMark.getWidth() / 2, marks.get(i).y - bmpMark
                                    .getHeight() / 2) <= 50) {
                        num = i;
                        isClickMark = true;
                        break;
                    }

                    if (i == marks.size() - 1) {
                        isClickMark = false;
                    }
                }
            }

            if (listener != null && isClickMark) {
                listener.markIsClick(num);
                mapView.refresh();
            }
        }
    }

    @Override
    public void draw(Canvas canvas, Matrix currentMatrix, float currentZoom, float
            currentRotateDegrees) {
        if (isVisible && marks != null) {
            canvas.save();
           
                if (!nodes.isEmpty()) {
                for (int i = 0; i < nodes.size(); i++) {
                    PointF mark = nodes.get(i);
                    float[] goal = {mark.x, mark.y};
                    currentMatrix.mapPoints(goal);

                    paint.setColor(Color.RED);
                    paint.setTextSize(radiusMark);
                 
                    //mark ico
                   canvas.drawBitmap(bmpNode, goal[0] - bmpNode.getWidth() / 2,
                            goal[1] - bmpNode.getHeight() / 2, paint);
                    if (i == num && isClickMark) {
                        canvas.drawBitmap(bmpNodeTouch, goal[0] - bmpNodeTouch.getWidth() / 2,
                                goal[1] - bmpNodeTouch.getHeight(), paint);
                    }
                }
            }
             if (!nodesContacts.isEmpty()) {
                for (int i = 0; i < nodesContacts.size(); i++) {
                    PointF mark = nodesContacts.get(i);
                    float[] goal = {mark.x, mark.y};
                    currentMatrix.mapPoints(goal);

                    paint.setColor(Color.YELLOW);
                    paint.setTextSize(radiusMark);
                  
                    //mark ico
                    canvas.drawBitmap(bmpContact, goal[0] - bmpContact.getWidth() / 2,
                            goal[1] - bmpContact.getHeight() / 2, paint);
                    if (i == num && isClickMark) {
                        canvas.drawBitmap(bmpContactTouch, goal[0] - bmpContactTouch.getWidth() / 2,
                                goal[1] - bmpContactTouch.getHeight(), paint);
                    }
                }
            }
            if (!marks.isEmpty()) {
                for (int i = 0; i < marks.size(); i++) {
                    PointF mark = marks.get(i);
                    float[] goal = {mark.x, mark.y};
                    currentMatrix.mapPoints(goal);

                    paint.setColor(Color.BLACK);
                    paint.setTextSize(radiusMark);
                    //mark name
                    if (mapView.getCurrentZoom() > 1.0 && marksName != null
                            && marksName.size() == marks.size()) {
                        canvas.drawText(marksName.get(i), goal[0] - radiusMark, goal[1] -
                                radiusMark / 2, paint);
                    }
                    //mark ico
                    canvas.drawBitmap(bmpMark, goal[0] - bmpMark.getWidth() / 2,
                            goal[1] - bmpMark.getHeight() / 2, paint);
                    if (i == num && isClickMark) {
                        canvas.drawBitmap(bmpMarkTouch, goal[0] - bmpMarkTouch.getWidth() / 2,
                                goal[1] - bmpMarkTouch.getHeight(), paint);
                    }
                }
            }
            
            
            canvas.restore();
        }
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<PointF> getMarks() {
        return marks;
    }

    public void setMarks(List<PointF> marks) {
        this.marks = marks;
    }

    public List<String> getMarksName() {
        return marksName;
    }

    public void setMarksName(List<String> marksName) {
        this.marksName = marksName;
    }

    public boolean isClickMark() {
        return isClickMark;
    }

    public void setMarkIsClickListener(MarkIsClickListener listener) {
        this.listener = listener;
    }

    public interface MarkIsClickListener {
        void markIsClick(int num);
    }
}
